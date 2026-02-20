# MVCC（多版本并发控制）完整教程

## 目录
1. [什么是 MVCC？](#1-什么是-mvcc)
2. [MVCC 的三大核心组件](#2-mvcc-的三大核心组件)
3. [隐藏字段详解](#3-隐藏字段详解)
4. [事务ID（trx_id）详解](#4-事务idtrx_id详解)
5. [ReadView（读视图）详解](#5-readview读视图详解)
6. [可见性判断规则](#6-可见性判断规则)
7. [完整的查询流程](#7-完整的查询流程)
8. [隔离级别对 ReadView 的影响](#8-隔离级别对-readview-的影响)
9. [快照读 vs 当前读](#9-快照读-vs-当前读)
10. [锁的分类](#10-锁的分类)
11. [幻读问题](#11-幻读问题)
12. [间隙锁详解](#12-间隙锁详解)
13. [间隙锁的范围规则](#13-间隙锁的范围规则)
14. [MVCC 完整工作原理总结](#14-mvcc-完整工作原理总结)

---

## 1. 什么是 MVCC？

### 基本概念

**MVCC = Multi-Version Concurrency Control（多版本并发控制）**

### 最简单的理解

想象一下图书馆的场景：

**传统方式（加锁）：**
- 小明在看一本书（读）
- 小红想修改这本书（写）
- 小红必须等小明看完才能修改
- **问题：读和写互相阻塞，效率低**

**MVCC 方式：**
- 小明在看这本书的第1版（读）
- 小红创建第2版来修改（写）
- 小明继续看第1版，小红修改第2版
- **优点：读和写不互相阻塞，可以并发进行**

### 核心思想

**MVCC 通过保存数据的多个历史版本，让读操作和写操作可以同时进行，互不干扰。**

---

## 2. MVCC 的三大核心组件

### 1. 隐藏字段（Hidden Fields）

每一行数据都有两个隐藏的字段：

```
user 表面上：
| id | name  | age |
|----|-------|-----|
| 1  | Alice | 20  |

实际存储（带隐藏字段）：
| id | name  | age | trx_id | roll_ptr |
|----|-------|-----|--------|----------|
| 1  | Alice | 20  | 100    | 0x1234   |
```

- **trx_id**：哪个事务最后修改了这行数据
- **roll_ptr**：指向这行数据的上一个版本（像链表的指针）

### 2. undo log（历史版本记录）

保存数据的历史版本：

```
当前版本：age = 20, trx_id = 100
         ↓ (roll_ptr 指向)
历史版本：age = 10, trx_id = 99
         ↓ (roll_ptr 指向)
历史版本：age = 5,  trx_id = 98
```

这就是"版本链"，通过 roll_ptr 连接起来。

### 3. ReadView（读视图）

当你执行 SELECT 查询时，会创建一个"快照"：

```
ReadView 记录：
- 当前有哪些事务正在运行（未提交）
- 我应该能看到哪些版本的数据
- 我不应该看到哪些版本的数据
```

---

## 3. 隐藏字段详解

### 初始状态

假设有一行用户数据：

```
| id | name  | age | trx_id | roll_ptr |
|----|-------|-----|--------|----------|
| 1  | Alice | 10  | 98     | NULL     |
```

- `trx_id = 98`：表示事务98创建了这条记录
- `roll_ptr = NULL`：没有更早的版本

### 第一次修改

事务99 执行：`UPDATE user SET age = 15 WHERE id = 1`

**发生了什么？**

1. **先保存旧版本到 undo log**：
```
undo log 区域：
[版本1] age = 10, trx_id = 98
```

2. **然后更新当前记录**：
```
当前记录：
| id | name  | age | trx_id | roll_ptr      |
|----|-------|-----|--------|---------------|
| 1  | Alice | 15  | 99     | → 指向版本1   |
```

- `trx_id` 改为 99（最后修改的事务）
- `roll_ptr` 指向 undo log 中的版本1

### 第二次修改

事务100 执行：`UPDATE user SET age = 20 WHERE id = 1`

**又发生了什么？**

1. **保存当前版本到 undo log**：
```
undo log 区域：
[版本2] age = 15, trx_id = 99, roll_ptr → 指向版本1
[版本1] age = 10, trx_id = 98
```

2. **更新当前记录**：
```
当前记录：
| id | name  | age | trx_id | roll_ptr      |
|----|-------|-----|--------|---------------|
| 1  | Alice | 20  | 100    | → 指向版本2   |
```

### 形成版本链

现在完整的版本链是：

```
当前记录：age = 20, trx_id = 100
    ↓ (roll_ptr)
版本2：age = 15, trx_id = 99
    ↓ (roll_ptr)
版本1：age = 10, trx_id = 98
    ↓
   NULL（最早的版本）
```

### 关键点

1. 每次修改都会把**旧值**保存到 undo log
2. 当前记录的 `trx_id` 记录**最后修改的事务**
3. `roll_ptr` 像链表指针，连接所有历史版本
4. 通过 `roll_ptr` 可以找到这行数据的所有历史

---

## 4. 事务ID（trx_id）详解

### 什么是事务ID？

事务ID 就像每个事务的"身份证号码"，是一个**递增的整数**。

```
事务A → trx_id = 100
事务B → trx_id = 101
事务C → trx_id = 102
...
```

越晚开始的事务，ID 越大。

### 何时分配事务ID？

**重要规则：只有在第一次修改数据时，才分配事务ID。**

#### 例子1：只读事务（不分配ID）
```sql
BEGIN;
  SELECT * FROM user;     -- 不分配 trx_id
  SELECT * FROM order;    -- 不分配 trx_id
COMMIT;

-- 全程没有事务ID
```

#### 例子2：先读后写
```sql
BEGIN;
  SELECT * FROM user;              -- 不分配 trx_id（还没修改数据）
  
  UPDATE user SET age = 20;        -- 此时分配 trx_id = 100
  
  INSERT INTO log VALUES(...);     -- 使用同一个 trx_id = 100
  DELETE FROM temp WHERE id = 1;   -- 使用同一个 trx_id = 100
COMMIT;
```

### 哪些操作会分配事务ID？

```
✅ 会分配事务ID：
- INSERT（插入数据）
- UPDATE（更新数据）
- DELETE（删除数据）

❌ 不会分配事务ID：
- SELECT（查询数据）
- SELECT FOR UPDATE（加锁查询，但也不分配ID）
```

### 同一个事务多次修改

```sql
BEGIN;  -- 准备开始事务
  UPDATE user SET age = 10;   -- 第1次修改，分配 trx_id = 100
  UPDATE user SET age = 20;   -- 第2次修改，还是用 trx_id = 100
  UPDATE user SET age = 30;   -- 第3次修改，还是用 trx_id = 100
COMMIT;
```

**关键点：同一个事务内的所有修改，都使用同一个 trx_id。**

版本链会变成：
```
当前：age = 30, trx_id = 100
  ↓
版本1：age = 20, trx_id = 100  ← 注意：trx_id 相同
  ↓
版本2：age = 10, trx_id = 100  ← 注意：trx_id 相同
  ↓
版本3：age = 5,  trx_id = 99   ← 上一个事务的版本
```

### 小结

1. 事务ID 是递增的整数
2. **只有修改数据（INSERT/UPDATE/DELETE）时才分配**
3. **查询操作不分配事务ID**
4. 同一个事务的所有修改用同一个 trx_id

---

## 5. ReadView（读视图）详解

### 什么是 ReadView？

ReadView 就像是拍了一张"快照"，记录了**在这一时刻，数据库的事务状态**。

当你执行 `SELECT` 时，会创建一个 ReadView，用来判断：
- 哪些版本的数据我能看到
- 哪些版本的数据我看不到

### ReadView 包含哪些信息？

ReadView 记录了4个关键信息：

```
ReadView {
  m_ids: [100, 102, 105]     // 当前活跃的事务ID列表
  min_trx_id: 100            // 最小的活跃事务ID
  max_trx_id: 106            // 下一个要分配的事务ID
  creator_trx_id: 103        // 创建这个ReadView的事务ID
}
```

#### 1. m_ids（活跃事务列表）
**哪些事务正在执行，还没提交？**

```
时刻T1：
- 事务100：正在执行（未提交）
- 事务101：已提交
- 事务102：正在执行（未提交）
- 事务103：正在执行（我自己）
- 事务104：已提交
- 事务105：正在执行（未提交）

m_ids = [100, 102, 103, 105]  // 未提交的事务
```

#### 2. min_trx_id（最小活跃事务ID）
```
min_trx_id = 100  // m_ids 中最小的那个
```

#### 3. max_trx_id（下一个事务ID）
```
max_trx_id = 106  // 如果现在有新事务开始，会分配106
```

#### 4. creator_trx_id（创建者ID）
```
creator_trx_id = 103  // 我自己的事务ID
```

### 简单例子理解

假设现在是这个时间点：

```
事务时间线：
95  96  97  98  99  100 101 102 103 104 105
✓   ✓   ✓   ✓   ✓   ⏳  ✓   ⏳  🔍  ✓   ⏳
已  已  已  已  已  进   已  进  我  已  进
提  提  提  提  提  行   提  行  自  提  行
交  交  交  交  交  中   交  中  己  交  中

下一个要分配的ID：106
```

此时事务103创建 ReadView：

```
ReadView {
  m_ids: [100, 102, 103, 105]    // 正在进行中的事务
  min_trx_id: 100                 // 最小的进行中事务
  max_trx_id: 106                 // 下一个要分配的ID
  creator_trx_id: 103             // 我自己
}
```

### ReadView 的作用

**用这4个信息，来判断版本链中的每个版本是否可见。**

---

## 6. 可见性判断规则

当我们执行查询时，会沿着版本链，逐个检查每个版本的 `trx_id`，判断是否可见。

### 判断规则（按顺序检查）

假设我们正在检查某个版本的 `trx_id`：

```
ReadView {
  m_ids: [100, 102, 105]
  min_trx_id: 100
  max_trx_id: 106
  creator_trx_id: 103
}
```

#### 规则1：如果 trx_id == creator_trx_id
**这是我自己的修改**

```
检查版本：trx_id = 103
判断：103 == 103（我自己）
结果：✅ 可见
```

**原因：自己的修改当然能看到**

#### 规则2：如果 trx_id < min_trx_id
**这个事务在我开始前就已经提交了**

```
检查版本：trx_id = 99
判断：99 < 100（min_trx_id）
结果：✅ 可见
```

**原因：比所有活跃事务都早，肯定已经提交了**

#### 规则3：如果 trx_id >= max_trx_id
**这个事务在我开始后才开始的**

```
检查版本：trx_id = 106
判断：106 >= 106（max_trx_id）
结果：❌ 不可见
```

**原因：在我创建 ReadView 之后才开始的事务，我不应该看到**

#### 规则4：如果 min_trx_id <= trx_id < max_trx_id
**需要进一步判断：在不在 m_ids 里**

**情况A：trx_id 在 m_ids 中**
```
检查版本：trx_id = 102
判断：100 <= 102 < 106，且 102 在 [100, 102, 105] 中
结果：❌ 不可见
```
**原因：在 m_ids 中，说明是未提交的事务**

**情况B：trx_id 不在 m_ids 中**
```
检查版本：trx_id = 101
判断：100 <= 101 < 106，但 101 不在 [100, 102, 105] 中
结果：✅ 可见
```
**原因：不在 m_ids 中，说明已经提交了**

### 完整示例

#### 场景设置
```
ReadView {
  m_ids: [100, 102, 105]       // 未提交的事务
  min_trx_id: 100
  max_trx_id: 106
  creator_trx_id: 103          // 我自己
}

版本链：
版本A：age = 50, trx_id = 106
版本B：age = 40, trx_id = 105
版本C：age = 35, trx_id = 103
版本D：age = 30, trx_id = 102
版本E：age = 20, trx_id = 101
版本F：age = 10, trx_id = 99
```

#### 逐个检查

**检查版本A（trx_id = 106）：**
```
106 >= 106（max_trx_id）
→ 规则3：❌ 不可见（在我之后开始的）
→ 继续检查下一个版本
```

**检查版本B（trx_id = 105）：**
```
100 <= 105 < 106
105 在 m_ids [100, 102, 105] 中
→ 规则4A：❌ 不可见（未提交）
→ 继续检查下一个版本
```

**检查版本C（trx_id = 103）：**
```
103 == 103（creator_trx_id）
→ 规则1：✅ 可见（自己的修改）
→ 停止检查，返回 age = 35
```

**最终结果：读到 age = 35**

### 判断规则总结

```
if (trx_id == creator_trx_id) {
    return 可见;  // 自己的修改
}

if (trx_id < min_trx_id) {
    return 可见;  // 早已提交
}

if (trx_id >= max_trx_id) {
    return 不可见;  // 在我之后开始
}

// min_trx_id <= trx_id < max_trx_id
if (trx_id 在 m_ids 中) {
    return 不可见;  // 未提交
} else {
    return 可见;  // 已提交
}
```

---

## 7. 完整的查询流程

### 场景设置

**时间线：**
```
T1: 事务98  INSERT user(id=1, age=10)  ✓ 已提交
T2: 事务99  UPDATE user SET age=20     ✓ 已提交
T3: 事务100 UPDATE user SET age=30     ⏳ 进行中（未提交）
T4: 事务101 UPDATE user SET age=40     ✓ 已提交
T5: 事务102 UPDATE user SET age=50     ⏳ 进行中（未提交）
T6: 事务103 BEGIN（我要查询了！）
```

### 步骤1：版本链的状态

```
当前记录（最新版本）：
| id | age | trx_id | roll_ptr    |
|----|-----|--------|-------------|
| 1  | 50  | 102    | → 指向版本1 |

undo log 中的版本链：
版本1：age=40, trx_id=101, roll_ptr → 指向版本2
版本2：age=30, trx_id=100, roll_ptr → 指向版本3
版本3：age=20, trx_id=99,  roll_ptr → 指向版本4
版本4：age=10, trx_id=98,  roll_ptr → NULL
```

完整版本链：
```
当前：age=50, trx_id=102
  ↓
版本1：age=40, trx_id=101
  ↓
版本2：age=30, trx_id=100
  ↓
版本3：age=20, trx_id=99
  ↓
版本4：age=10, trx_id=98
```

### 步骤2：事务103 执行查询

```sql
-- 事务103
BEGIN;
  SELECT age FROM user WHERE id = 1;
```

### 步骤3：创建 ReadView

**此时的事务状态：**
```
事务98：✓ 已提交
事务99：✓ 已提交
事务100：⏳ 进行中
事务101：✓ 已提交
事务102：⏳ 进行中
事务103：🔍 我自己（只读，没有 trx_id）
```

**生成 ReadView：**
```
ReadView {
  m_ids: [100, 102]           // 未提交的事务
  min_trx_id: 100
  max_trx_id: 104             // 下一个要分配的ID
  creator_trx_id: 0           // 我是只读事务，没有ID
}
```

### 步骤4：遍历版本链，判断可见性

#### 检查当前版本（age=50, trx_id=102）
```
trx_id = 102
判断：
  102 == 0 ? ❌ (不是自己)
  102 < 100 ? ❌ (不满足)
  102 >= 104 ? ❌ (不满足)
  100 <= 102 < 104 ? ✅
    102 在 m_ids[100, 102] 中? ✅
    → 不可见（未提交）

继续检查下一个版本 ↓
```

#### 检查版本1（age=40, trx_id=101）
```
trx_id = 101
判断：
  101 == 0 ? ❌
  101 < 100 ? ❌
  101 >= 104 ? ❌
  100 <= 101 < 104 ? ✅
    101 在 m_ids[100, 102] 中? ❌
    → 可见！（已提交）

✅ 找到可见版本，停止搜索
```

**返回结果：age = 40**

### 步骤5：完整流程图

```
START
  ↓
执行 SELECT age FROM user WHERE id = 1
  ↓
创建 ReadView: {m_ids:[100,102], min:100, max:104, creator:0}
  ↓
读取当前记录，获取 roll_ptr
  ↓
检查版本：age=50, trx_id=102
  ↓
可见吗？❌ (在 m_ids 中)
  ↓
通过 roll_ptr 找到版本1
  ↓
检查版本：age=40, trx_id=101
  ↓
可见吗？✅ (不在 m_ids 中)
  ↓
返回 age = 40
  ↓
END
```

### 小结

完整查询流程：
1. 执行 SELECT
2. 创建 ReadView（记录当前事务状态）
3. 读取当前记录，获取版本链入口
4. 沿着 roll_ptr 遍历版本链
5. 用 ReadView 判断每个版本的可见性
6. 返回第一个可见的版本

---

## 8. 隔离级别对 ReadView 的影响

MySQL 有两个主要的隔离级别使用 MVCC，它们的区别就在于**何时创建 ReadView**。

### 两种隔离级别

#### 1. READ COMMITTED (RC) - 读已提交
**每次查询都创建新的 ReadView**

#### 2. REPEATABLE READ (RR) - 可重复读
**第一次查询创建 ReadView，后续查询复用**

### 对比例子

**场景设置：**
```
初始：age = 10, trx_id = 98

T1: 事务100 开始
T2: 事务100 UPDATE age = 20（未提交）
T3: 事务101 开始查询
T4: 事务100 提交
T5: 事务101 再次查询
```

### READ COMMITTED (RC) 模式

```sql
-- 设置隔离级别为 RC
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;

-- 事务101
BEGIN;
  -- T3: 第1次查询
  SELECT age FROM user WHERE id = 1;
  
  创建 ReadView1:
  {
    m_ids: [100]        // 事务100 未提交
    min_trx_id: 100
    max_trx_id: 102
  }
  
  检查版本：
  - age=20, trx_id=100 → 在 m_ids 中 → ❌ 不可见
  - age=10, trx_id=98  → < min_trx_id → ✅ 可见
  
  结果：age = 10
  
  -- 事务100 提交了！
  
  -- T5: 第2次查询
  SELECT age FROM user WHERE id = 1;
  
  创建 ReadView2（新的！）:
  {
    m_ids: []           // 事务100 已提交，没有未提交事务
    min_trx_id: 102
    max_trx_id: 102
  }
  
  检查版本：
  - age=20, trx_id=100 → < min_trx_id(102) → ✅ 可见
  
  结果：age = 20  ← 变了！
COMMIT;
```

**RC 特点：每次查询看到最新的已提交数据**

### REPEATABLE READ (RR) 模式

```sql
-- 设置隔离级别为 RR（MySQL 默认）
SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ;

-- 事务101
BEGIN;
  -- T3: 第1次查询
  SELECT age FROM user WHERE id = 1;
  
  创建 ReadView1:
  {
    m_ids: [100]        // 事务100 未提交
    min_trx_id: 100
    max_trx_id: 102
  }
  
  检查版本：
  - age=20, trx_id=100 → 在 m_ids 中 → ❌ 不可见
  - age=10, trx_id=98  → < min_trx_id → ✅ 可见
  
  结果：age = 10
  
  -- 事务100 提交了！
  
  -- T5: 第2次查询
  SELECT age FROM user WHERE id = 1;
  
  复用 ReadView1（还是旧的！）:
  {
    m_ids: [100]        // 快照不变
    min_trx_id: 100
    max_trx_id: 102
  }
  
  检查版本：
  - age=20, trx_id=100 → 在 m_ids 中 → ❌ 不可见
  - age=10, trx_id=98  → < min_trx_id → ✅ 可见
  
  结果：age = 10  ← 没变！
COMMIT;
```

**RR 特点：在同一个事务内，多次查询结果一致（可重复读）**

### 对比总结

| 隔离级别 | ReadView 创建时机 | 第1次查询 | 第2次查询 | 特点 |
|---------|------------------|----------|----------|------|
| RC | 每次查询创建新的 | age = 10 | age = 20 | 能读到其他事务新提交的数据 |
| RR | 第1次创建，后续复用 | age = 10 | age = 10 | 保证同一事务内读取一致 |

### 完整示例：三次查询

```sql
-- 隔离级别：REPEATABLE READ

-- 事务A
BEGIN;  -- trx_id = 100
  UPDATE user SET age = 20 WHERE id = 1;
  -- 未提交

-- 事务B
BEGIN;  -- 只读事务
  -- 第1次查询
  SELECT age FROM user WHERE id = 1;
  -- 创建 ReadView: {m_ids: [100], ...}
  -- 结果：age = 10（看不到事务A）
  
-- 事务A 提交
COMMIT;

-- 事务C
BEGIN;  -- trx_id = 101
  UPDATE user SET age = 30 WHERE id = 1;
  -- 未提交

-- 事务B（继续）
  -- 第2次查询
  SELECT age FROM user WHERE id = 1;
  -- 复用 ReadView: {m_ids: [100], ...}
  -- 结果：age = 10（还是看不到！）
  
-- 事务C 提交
COMMIT;

-- 事务B（继续）
  -- 第3次查询
  SELECT age FROM user WHERE id = 1;
  -- 复用 ReadView: {m_ids: [100], ...}
  -- 结果：age = 10（依然是10！）
  
COMMIT;
```

**在 RR 模式下，事务B 从头到尾看到的都是 age = 10**

### 如果是 RC 模式呢？

```sql
-- 隔离级别：READ COMMITTED

-- 同样的操作顺序

-- 事务B
BEGIN;
  SELECT age FROM user WHERE id = 1;  -- age = 10
  -- 事务A 提交
  SELECT age FROM user WHERE id = 1;  -- age = 20 ← 变了！
  -- 事务C 提交
  SELECT age FROM user WHERE id = 1;  -- age = 30 ← 又变了！
COMMIT;
```

### 小结

1. **RC（读已提交）**
   - 每次查询创建新 ReadView
   - 能读到最新的已提交数据
   - 可能出现"不可重复读"

2. **RR（可重复读）**
   - 第一次查询创建 ReadView，后续复用
   - 保证事务内读取一致
   - 解决"不可重复读"问题

---

## 9. 快照读 vs 当前读

### 加锁查询 vs 普通查询

#### 普通查询（快照读）
```sql
SELECT * FROM user WHERE id = 1;
```
- **不加锁**
- 使用 MVCC 读取历史版本
- 可能读到"旧数据"
- **会创建 ReadView**

#### 加锁查询（当前读）
```sql
-- 排他锁（FOR UPDATE）
SELECT * FROM user WHERE id = 1 FOR UPDATE;

-- 共享锁（LOCK IN SHARE MODE）
SELECT * FROM user WHERE id = 1 LOCK IN SHARE MODE;
```
- **加锁**（防止其他事务修改）
- 直接读取最新版本
- 读到"最新数据"
- **不创建 ReadView**

### 关键区别示例

```sql
-- 初始数据：age = 10

-- 事务A
BEGIN;  -- trx_id = 100
  UPDATE user SET age = 20 WHERE id = 1;
  -- 未提交！

-- 事务B
BEGIN;
  -- 普通查询（快照读）
  SELECT age FROM user WHERE id = 1;
  -- 结果：age = 10（读到旧版本，因为事务A未提交）
  
  -- 加锁查询（当前读）
  SELECT age FROM user WHERE id = 1 FOR UPDATE;
  -- 结果：阻塞！等待事务A提交或回滚
  -- 如果事务A提交了，会读到 age = 20（最新版本）
```

### 为什么加锁查询不用 MVCC？

加锁查询通常用于**即将要修改数据**的场景：

```sql
BEGIN;
  -- 先查询当前余额
  SELECT balance FROM account WHERE id = 1 FOR UPDATE;
  -- 必须读最新值！如果读旧版本，计算就错了
  
  -- 基于最新值进行修改
  UPDATE account SET balance = balance - 100 WHERE id = 1;
COMMIT;
```

如果用普通查询（快照读），可能读到旧的 balance，导致计算错误。

### 对比总结

| 维度 | 快照读 | 当前读 |
|-----|-------|--------|
| SQL | `SELECT` | `SELECT ... FOR UPDATE`<br>`UPDATE/DELETE/INSERT` |
| 读取方式 | 通过 MVCC 读历史版本 | 直接读最新版本 |
| 是否加锁 | 否 | 是（排他锁或共享锁） |
| 是否创建 ReadView | 是 | 否 |
| 读到的数据 | 可能是旧版本 | 一定是最新版本 |
| 阻塞情况 | 不阻塞 | 可能阻塞（等待锁） |

---

## 10. 锁的分类

把数据库的一行数据想象成一本书：

### 排他锁（Exclusive Lock）
```sql
SELECT * FROM user WHERE id = 1 FOR UPDATE;
```

**像是：我要独占这本书**
- 我拿到这本书后，别人**不能看、也不能改**
- 我可以看，也可以改
- 只有我用完（提交或回滚），别人才能拿

**场景：**
```sql
BEGIN;
  -- 查询账户余额（加排他锁）
  SELECT balance FROM account WHERE id = 1 FOR UPDATE;
  -- 此时其他事务不能读写这行数据
  
  -- 修改余额
  UPDATE account SET balance = balance - 100 WHERE id = 1;
COMMIT;  -- 释放锁
```

### 共享锁（Shared Lock）
```sql
SELECT * FROM user WHERE id = 1 LOCK IN SHARE MODE;
```

**像是：我要看这本书，但允许别人一起看**
- 我拿到这本书后，别人**可以看、但不能改**
- 多个人可以同时持有共享锁（一起看）
- 任何人想改，必须等所有人都放下书

**场景：**
```sql
BEGIN;
  -- 查询商品库存（加共享锁）
  SELECT stock FROM product WHERE id = 1 LOCK IN SHARE MODE;
  -- 其他事务可以读，但不能修改库存
  
  -- 基于库存判断是否可以下单
  IF stock > 0 THEN
    INSERT INTO orders ...
  END IF;
COMMIT;  -- 释放锁
```

### 三种查询方式对比

#### 1. 普通查询（不加锁）
```sql
SELECT * FROM user WHERE id = 1;
```
- ✅ 我可以看
- ✅ 别人可以看
- ✅ 别人可以改
- 📖 我看的是历史版本（MVCC）

#### 2. 共享锁查询
```sql
SELECT * FROM user WHERE id = 1 LOCK IN SHARE MODE;
```
- ✅ 我可以看
- ✅ 别人可以看
- ❌ 别人不能改（阻塞）
- 📖 我看的是最新版本

#### 3. 排他锁查询
```sql
SELECT * FROM user WHERE id = 1 FOR UPDATE;
```
- ✅ 我可以看和改
- ❌ 别人不能看（阻塞）
- ❌ 别人不能改（阻塞）
- 📖 我看的是最新版本

### 实际例子：并发转账

#### 没有锁的问题
```sql
-- 账户余额：balance = 100

-- 事务A
BEGIN;
  SELECT balance FROM account WHERE id = 1;  -- 读到 100
  -- 计算：100 - 50 = 50
  UPDATE account SET balance = 50 WHERE id = 1;
COMMIT;

-- 事务B（同时执行）
BEGIN;
  SELECT balance FROM account WHERE id = 1;  -- 也读到 100
  -- 计算：100 - 30 = 70
  UPDATE account SET balance = 70 WHERE id = 1;
COMMIT;

-- 结果：最终余额是 70（错误！应该是 20）
-- 事务A的修改丢失了！
```

#### 使用排他锁解决
```sql
-- 事务A
BEGIN;
  SELECT balance FROM account WHERE id = 1 FOR UPDATE;  -- 加排他锁，读到 100
  UPDATE account SET balance = 50 WHERE id = 1;
COMMIT;  -- 释放锁

-- 事务B
BEGIN;
  SELECT balance FROM account WHERE id = 1 FOR UPDATE;  -- 阻塞！等待事务A完成
  -- 事务A提交后，读到 50
  UPDATE account SET balance = 20 WHERE id = 1;
COMMIT;

-- 结果：最终余额是 20（正确！）
```

### 锁的作用总结

| 锁类型 | 我能读 | 我能写 | 别人能读 | 别人能写 | 用途 |
|-------|-------|-------|---------|---------|------|
| 无锁（普通查询）| ✅ | ❌ | ✅ | ✅ | 只是查看数据 |
| 共享锁 | ✅ | ❌ | ✅ | ❌ | 确保数据不被修改 |
| 排他锁 | ✅ | ✅ | ❌ | ❌ | 准备修改数据 |

**核心作用：防止并发修改时的数据错误。**

### 锁的完整分类

**锁分为两个维度：**

#### 维度1：锁的性质
- **共享锁（S锁）**：多个事务可以同时读
- **排他锁（X锁）**：只有一个事务可以读写

#### 维度2：锁的范围
- **行锁（Record Lock）**：锁定一行数据
- **间隙锁（Gap Lock）**：锁定行之间的"间隙"
- **临键锁（Next-Key Lock）**：行锁 + 间隙锁

**它们是两个不同的维度！可以组合！**

### `FOR UPDATE` 到底加什么锁？

**`FOR UPDATE` 会加排他锁，但具体锁的范围取决于查询条件。**

#### 情况1：精确匹配主键（只加行锁）

```sql
-- user 表有主键 id
BEGIN;
  SELECT * FROM user WHERE id = 10 FOR UPDATE;
  -- 加锁：排他锁 + 行锁
  -- 只锁 id=10 这一行
  -- 其他事务可以插入 id=9 或 id=11
COMMIT;
```

**图示：**
```
记录：  ... | id=9 | [id=10 被锁] | id=11 | ...
               ↑                    ↑
            可以插入              可以插入
```

#### 情况2：范围查询（加间隙锁 + 行锁）

```sql
-- user 表有索引 age
BEGIN;
  SELECT * FROM user WHERE age > 20 FOR UPDATE;
  -- 加锁：排他锁 + 临键锁（行锁+间隙锁）
  -- 锁定现有的记录 + age > 20 的"间隙"
  -- 其他事务无法插入 age > 20 的新记录
COMMIT;
```

**图示：**
```
记录：age=18 | age=20 | [age=25 被锁] | [间隙被锁：age>25] | ∞
                          ↑              ↑
                      现有记录被锁      间隙被锁，无法插入
```

### 锁的完整分类表

| 锁的性质（谁能访问） | 锁的范围（锁多大） | 示例 |
|-------------------|-----------------|------|
| 共享锁（S锁） | 行锁 | `SELECT ... LOCK IN SHARE MODE` + 主键 |
| 共享锁（S锁） | 间隙锁 | `SELECT ... LOCK IN SHARE MODE` + 范围 |
| 排他锁（X锁） | 行锁 | `SELECT ... FOR UPDATE` + 主键 |
| 排他锁（X锁） | 间隙锁 | `SELECT ... FOR UPDATE` + 范围 |
| 排他锁（X锁） | 临键锁 | `SELECT ... FOR UPDATE` + 范围（RR模式）|

### 实际使用频率

#### 普通查询（最常用）⭐⭐⭐⭐⭐
```sql
SELECT * FROM user WHERE id = 1;
```
**使用频率：95%+**

大部分场景都用普通查询：
- 展示用户列表
- 查看订单详情
- 统计报表
- 搜索商品

#### 排他锁查询（偶尔使用）⭐⭐
```sql
SELECT * FROM user WHERE id = 1 FOR UPDATE;
```
**使用频率：3-4%**

典型场景：
1. 电商下单（防止超卖）
2. 账户转账
3. 秒杀活动
4. 发号器/生成唯一编号

#### 共享锁查询（很少使用）⭐
```sql
SELECT * FROM user WHERE id = 1 LOCK IN SHARE MODE;
```
**使用频率：<1%**

在实际工作中几乎不用。

---

## 11. 幻读问题

### 什么是幻读？

**幻读：同一个事务中，前后两次查询，结果集的数量不一致。**

像是出现了"幻影"一样，突然多了或少了几行数据。

### 幻读的例子

```sql
-- 隔离级别：REPEATABLE READ

-- 事务A
BEGIN;
  -- 第1次查询：查询年龄 > 20 的用户
  SELECT * FROM user WHERE age > 20;
  -- 结果：0 行
  
-- 事务B
BEGIN;
  INSERT INTO user (id, name, age) VALUES (10, 'Bob', 25);
COMMIT;  -- 提交了

-- 事务A（继续）
  -- 第2次查询：再次查询年龄 > 20 的用户
  SELECT * FROM user WHERE age > 20;
  -- 结果：还是 0 行（RR 模式下，看不到新插入的）
  
  -- 但是！如果我尝试插入
  INSERT INTO user (id, name, age) VALUES (10, 'Alice', 30);
  -- 报错：主键冲突！id=10 已存在
  
  -- 明明查不到，但却不能插入？这就是"幻读"
COMMIT;
```

### 为什么会出现幻读？

**MVCC 只对普通的 SELECT 有效，对 INSERT/UPDATE/DELETE 无效。**

```sql
-- 事务A
BEGIN;
  -- 快照读（使用 MVCC）
  SELECT * FROM user WHERE age > 20;
  -- ReadView 看不到事务B插入的数据
  -- 结果：0 行
  
  -- 当前读（不使用 MVCC）
  INSERT INTO user VALUES (10, 'Alice', 30);
  -- 直接检查最新数据，发现 id=10 已存在
  -- 报错！
```

### MVCC 能否解决幻读？

**不能完全解决！**

#### 场景1：连续的快照读
```sql
BEGIN;
  SELECT * FROM user WHERE age > 20;  -- 0 行
  -- 事务B 插入并提交
  SELECT * FROM user WHERE age > 20;  -- 还是 0 行
COMMIT;
```
**✅ 没有幻读（RR 模式下，ReadView 复用）**

#### 场景2：快照读 + 当前读
```sql
BEGIN;
  SELECT * FROM user WHERE age > 20;  -- 0 行（快照读）
  -- 事务B 插入并提交
  SELECT * FROM user WHERE age > 20 FOR UPDATE;  -- 1 行（当前读）
COMMIT;
```
**❌ 出现幻读（当前读看到了新数据）**

#### 场景3：快照读 + 写操作
```sql
BEGIN;
  SELECT * FROM user WHERE age > 20;  -- 0 行（快照读）
  -- 事务B 插入并提交
  INSERT INTO user VALUES (10, ...);  -- 报错（当前读发现冲突）
COMMIT;
```
**❌ 出现幻读（明明查不到，却不能插入）**

### 如何解决幻读？

#### 方法1：使用当前读（加锁）
```sql
BEGIN;
  -- 使用 FOR UPDATE 锁定范围
  SELECT * FROM user WHERE age > 20 FOR UPDATE;
  -- 锁住了符合条件的范围（间隙锁）
  
  -- 事务B 的插入会被阻塞
  -- INSERT INTO user VALUES (10, 'Bob', 25);  -- 等待...
  
  -- 再次查询
  SELECT * FROM user WHERE age > 20 FOR UPDATE;
  -- 结果一致，没有幻读
COMMIT;
```

#### 方法2：间隙锁（Gap Lock）
MySQL 的 RR 模式下，会自动使用间隙锁：

```sql
BEGIN;
  SELECT * FROM user WHERE age > 20 FOR UPDATE;
  -- 不仅锁定现有的记录
  -- 还锁定 age > 20 这个"范围"（间隙）
  -- 其他事务无法在这个范围内插入新数据
COMMIT;
```

### 完整对比

| 问题类型 | RC 模式 | RR 模式（MVCC） | RR 模式（加锁） |
|---------|---------|----------------|----------------|
| 脏读 | ✅ 解决 | ✅ 解决 | ✅ 解决 |
| 不可重复读 | ❌ 未解决 | ✅ 解决 | ✅ 解决 |
| 幻读（快照读） | ❌ 未解决 | ✅ 解决 | ✅ 解决 |
| 幻读（当前读） | ❌ 未解决 | ❌ 未解决 | ✅ 解决 |

### 幻读的本质

```
MVCC 的局限性：
- 快照读：使用 ReadView，能防止幻读
- 当前读：直接读最新数据，无法防止幻读

解决方案：
- 如果只用快照读（普通 SELECT），RR 能防止幻读
- 如果用当前读（FOR UPDATE/INSERT/UPDATE/DELETE），需要加锁
```

---

## 12. 间隙锁详解

### 什么是间隙锁？

**间隙锁：锁住的不是已存在的记录，而是记录之间的"空位"，防止别人在这个空位插入新数据。**

### 用停车位来理解

想象一个停车场：

```
停车位编号：1    2    3    4    5    6    7
实际情况：  [车A] [空] [车B] [空] [空] [车C] [空]
```

#### 行锁 = 锁住已停的车
```sql
SELECT * FROM car WHERE id = 1 FOR UPDATE;
-- 锁住车A，别人不能移动车A
-- 但可以在空位2停新车
```

#### 间隙锁 = 锁住空位
```sql
SELECT * FROM car WHERE id > 1 AND id < 3 FOR UPDATE;
-- 锁住空位2，别人不能在空位2停新车
-- 虽然空位2 本来就没车！
```

### 数据库中的间隙锁

#### 初始数据
```
user 表（按 age 排序）：
| id | age |
|----|-----|
| 1  | 10  |
| 2  | 30  |
| 3  | 50  |
```

**间隙在哪里？**
```
(-∞, 10) | age=10 | (10, 30) | age=30 | (30, 50) | age=50 | (50, +∞)
   ↑        行1       ↑         行2       ↑        行3        ↑
 间隙1              间隙2               间隙3              间隙4
```

### 例子1：锁住间隙

```sql
BEGIN;
  -- 查询 age = 30 的记录
  SELECT * FROM user WHERE age = 30 FOR UPDATE;
  
  -- 加了什么锁？
  -- 1. 行锁：锁住 age=30 这一行（id=2）
  -- 2. 间隙锁：锁住 (10, 30) 和 (30, 50) 这两个间隙
```

**锁定情况：**
```
(-∞, 10) | age=10 | [间隙锁:(10,30)] | [行锁:age=30] | [间隙锁:(30,50)] | age=50 | (50, +∞)
```

**此时其他事务：**
```sql
-- 事务B
INSERT INTO user (id, age) VALUES (10, 5);   -- ✅ 成功（在间隙1，没锁）
INSERT INTO user (id, age) VALUES (11, 15);  -- ❌ 阻塞（在间隙2，被锁）
INSERT INTO user (id, age) VALUES (12, 25);  -- ❌ 阻塞（在间隙2，被锁）
INSERT INTO user (id, age) VALUES (13, 30);  -- ❌ 阻塞（在间隙2或3，被锁）
INSERT INTO user (id, age) VALUES (14, 35);  -- ❌ 阻塞（在间隙3，被锁）
INSERT INTO user (id, age) VALUES (15, 40);  -- ❌ 阻塞（在间隙3，被锁）
INSERT INTO user (id, age) VALUES (16, 55);  -- ✅ 成功（在间隙4，没锁）
```

### 例子2：为什么需要间隙锁？

#### 没有间隙锁（会出现幻读）

```sql
-- 初始数据：只有 age=10 和 age=50

-- 事务A
BEGIN;
  SELECT * FROM user WHERE age BETWEEN 20 AND 40;
  -- 结果：0 行（没有符合条件的）
  
-- 事务B
BEGIN;
  INSERT INTO user (id, age) VALUES (10, 30);
COMMIT;  -- 提交成功

-- 事务A（继续）
  SELECT * FROM user WHERE age BETWEEN 20 AND 40;
  -- 如果没有间隙锁，可能读到 1 行（幻读！）
COMMIT;
```

#### 有间隙锁（防止幻读）

```sql
-- 初始数据：只有 age=10 和 age=50

-- 事务A
BEGIN;
  SELECT * FROM user WHERE age BETWEEN 20 AND 40 FOR UPDATE;
  -- 结果：0 行
  -- 加锁：锁住间隙 (10, 50)
  
-- 事务B
BEGIN;
  INSERT INTO user (id, age) VALUES (10, 30);
  -- ❌ 阻塞！因为 age=30 在间隙 (10, 50) 内
  
-- 事务A（继续）
  SELECT * FROM user WHERE age BETWEEN 20 AND 40;
  -- 结果：还是 0 行（没有幻读）
COMMIT;

-- 事务B 的插入被唤醒，继续执行
COMMIT;
```

### 例子3：间隙锁的精确范围

#### 数据
```
| id | age |
|----|-----|
| 1  | 10  |
| 2  | 20  |
| 3  | 30  |
```

#### 查询1：精确匹配
```sql
SELECT * FROM user WHERE age = 20 FOR UPDATE;

加锁：
- 行锁：age=20
- 间隙锁：(10, 20) 和 (20, 30)

阻塞的插入：
- age=15  ❌（在间隙 10-20）
- age=20  ❌（等于20，行锁）
- age=25  ❌（在间隙 20-30）
- age=5   ✅（不在锁定范围）
- age=35  ✅（不在锁定范围）
```

#### 查询2：范围查询
```sql
SELECT * FROM user WHERE age > 20 FOR UPDATE;

加锁：
- 行锁：age=30
- 间隙锁：(20, 30) 和 (30, +∞)

阻塞的插入：
- age=25  ❌（在间隙 20-30）
- age=30  ❌（等于30，行锁）
- age=100 ❌（在间隙 30-∞）
- age=15  ✅（不在锁定范围）
```

### 关键点理解

#### 1. 间隙锁锁的是"不存在的位置"
```
数据：age=10, age=30

SELECT * FROM user WHERE age = 20 FOR UPDATE;
-- age=20 这行数据根本不存在！
-- 但会锁住"20可能出现的位置"（间隙 10-30）
-- 防止别人插入 age=20
```

#### 2. 间隙锁的目的
**防止其他事务在锁定范围内插入新数据，从而防止幻读。**

#### 3. 间隙锁的触发条件（RR 隔离级别下）
```
✅ 会加间隙锁：
- 范围查询：WHERE age > 20
- 不等查询：WHERE age != 20
- BETWEEN：WHERE age BETWEEN 10 AND 30
- 索引查询未命中：WHERE age = 25（不存在）

❌ 不加间隙锁：
- 精确匹配主键：WHERE id = 10（唯一索引）
```

### 图解总结

```
数据：  age=10     age=30     age=50

间隙：
        ↓          ↓          ↓          ↓
      (-∞,10)  (10,30)    (30,50)    (50,+∞)
      间隙1     间隙2      间隙3      间隙4

执行：SELECT * FROM user WHERE age = 30 FOR UPDATE;

锁定：
        ↓          ↓          ↓          ↓
      (-∞,10)  [锁间隙2]  [锁行+锁间隙3]  (50,+∞)
                  ↑          ↑          ↑
             不能插入11-29  不能插入30  不能插入31-49
```

---

## 13. 间隙锁的范围规则

间隙锁的范围取决于**索引**和**查询条件**。

### 核心规则：基于索引

**间隙锁是在索引上加的，范围由索引的"相邻记录"决定。**

### 前提条件
```
假设 age 字段有索引，数据如下：
| id | age |
|----|-----|
| 1  | 10  |
| 2  | 20  |
| 3  | 30  |
| 4  | 40  |
| 5  | 50  |

在索引上的顺序：
10 -> 20 -> 30 -> 40 -> 50
```

### 规则1：等值查询，记录存在

```sql
SELECT * FROM user WHERE age = 30 FOR UPDATE;
```

**查找过程：**
1. 在索引上找到 age=30
2. 确定相邻记录：前一个是 20，后一个是 40

**加锁范围：**
- 行锁：age=30
- 间隙锁：**(20, 30)** 和 **(30, 40)**

```
10 -> 20 -> [间隙:(20,30)] -> [行锁:30] -> [间隙:(30,40)] -> 40 -> 50
```

**阻塞的插入：**
```sql
INSERT age=25;  -- ❌ 阻塞（在间隙 20-30）
INSERT age=30;  -- ❌ 阻塞（行锁）
INSERT age=35;  -- ❌ 阻塞（在间隙 30-40）
INSERT age=15;  -- ✅ 成功（不在锁定范围）
INSERT age=45;  -- ✅ 成功（不在锁定范围）
```

### 规则2：等值查询，记录不存在

```sql
SELECT * FROM user WHERE age = 25 FOR UPDATE;
```

**查找过程：**
1. 在索引上找不到 age=25
2. 确定它应该在哪里：在 20 和 30 之间

**加锁范围：**
- 无行锁（记录不存在）
- 间隙锁：**(20, 30)** （只锁这一个间隙）

```
10 -> 20 -> [间隙锁:(20,30)] -> 30 -> 40 -> 50
```

**阻塞的插入：**
```sql
INSERT age=25;  -- ❌ 阻塞（正好在间隙内）
INSERT age=22;  -- ❌ 阻塞（在间隙 20-30）
INSERT age=29;  -- ❌ 阻塞（在间隙 20-30）
INSERT age=15;  -- ✅ 成功
INSERT age=35;  -- ✅ 成功
```

### 规则3：范围查询（> 或 >=）

```sql
SELECT * FROM user WHERE age > 30 FOR UPDATE;
```

**查找过程：**
1. 找到所有 age > 30 的记录：40, 50
2. 确定范围的起点：30 是边界

**加锁范围：**
- 行锁：age=40, age=50
- 间隙锁：**(30, 40)**, **(40, 50)**, **(50, +∞)**

```
10 -> 20 -> 30 -> [间隙:(30,40)] -> [行锁:40] -> [间隙:(40,50)] -> [行锁:50] -> [间隙:(50,+∞)]
```

**阻塞的插入：**
```sql
INSERT age=35;  -- ❌ 阻塞
INSERT age=45;  -- ❌ 阻塞
INSERT age=100; -- ❌ 阻塞（锁到无穷大）
INSERT age=25;  -- ✅ 成功
```

### 规则4：范围查询（< 或 <=）

```sql
SELECT * FROM user WHERE age < 30 FOR UPDATE;
```

**加锁范围：**
- 行锁：age=10, age=20
- 间隙锁：**(-∞, 10)**, **(10, 20)**, **(20, 30)**

```
[间隙:(-∞,10)] -> [行锁:10] -> [间隙:(10,20)] -> [行锁:20] -> [间隙:(20,30)] -> 30 -> 40 -> 50
```

### 规则5：BETWEEN 查询

```sql
SELECT * FROM user WHERE age BETWEEN 20 AND 40 FOR UPDATE;
```

**加锁范围：**
- 行锁：age=20, age=30, age=40
- 间隙锁：**(10, 20)**, **(20, 30)**, **(30, 40)**, **(40, 50)**

```
10 -> [间隙:(10,20)] -> [行锁:20] -> [间隙:(20,30)] -> [行锁:30] -> [间隙:(30,40)] -> [行锁:40] -> [间隙:(40,50)] -> 50
```

**规律：锁定范围是从第一个匹配记录的前一个间隙，到最后一个匹配记录的后一个间隙。**

### 规则总结：如何确定间隙范围？

#### 步骤1：在索引上定位查询范围
```
找到满足条件的第一条记录和最后一条记录
```

#### 步骤2：找相邻记录
```
- 第一条记录的前一条
- 最后一条记录的后一条
```

#### 步骤3：确定间隙
```
间隙 = (前一条记录的值, 后一条记录的值)
```

### 实际例子：详细推导

#### 数据
```
索引顺序：5, 10, 20, 30, 40, 50, 60
```

#### 查询1：`WHERE age = 30`
```
步骤1：找到 age=30
步骤2：找相邻
  - 前一条：20
  - 后一条：40
步骤3：确定间隙
  - (20, 30)
  - (30, 40)

锁定：行锁[30] + 间隙锁(20,30) + 间隙锁(30,40)
```

#### 查询2：`WHERE age = 25`（不存在）
```
步骤1：找不到 age=25，确定它应该在 20-30 之间
步骤2：相邻记录
  - 前一条：20
  - 后一条：30
步骤3：确定间隙
  - (20, 30)

锁定：间隙锁(20,30)
```

#### 查询3：`WHERE age > 30`
```
步骤1：找到所有满足的记录：40, 50, 60
步骤2：找边界
  - 第一条是 40，前一条是 30
  - 最后一条是 60，后一条是 +∞
步骤3：确定间隙
  - (30, 40)  -- 第一条记录前的间隙
  - (40, 50)  -- 记录之间的间隙
  - (50, 60)  -- 记录之间的间隙
  - (60, +∞)  -- 最后一条记录后的间隙

锁定：行锁[40,50,60] + 间隙锁(30,40)+(40,50)+(50,60)+(60,+∞)
```

#### 查询4：`WHERE age BETWEEN 20 AND 40`
```
步骤1：找到满足的记录：20, 30, 40
步骤2：找边界
  - 第一条是 20，前一条是 10
  - 最后一条是 40，后一条是 50
步骤3：确定间隙
  - (10, 20)  -- 第一条前
  - (20, 30)  -- 记录之间
  - (30, 40)  -- 记录之间
  - (40, 50)  -- 最后一条后

锁定：行锁[20,30,40] + 间隙锁(10,20)+(20,30)+(30,40)+(40,50)
```

### 特殊情况

#### 情况1：查询范围内没有记录
```sql
-- 数据：10, 50
SELECT * FROM user WHERE age BETWEEN 20 AND 40 FOR UPDATE;

没有满足条件的记录
确定位置：应该在 10-50 之间
锁定：间隙锁 (10, 50)
```

#### 情况2：唯一索引/主键（不加间隙锁）
```sql
SELECT * FROM user WHERE id = 10 FOR UPDATE;

id 是主键（唯一索引）
只加行锁，不加间隙锁
原因：主键值唯一，不可能插入相同值，不需要间隙锁
```

### 口诀记忆

```
1. 等值存在：锁行 + 前后间隙
2. 等值不存在：锁所在间隙
3. 范围查询：锁所有行 + 前后间隙到边界
4. 主键查询：只锁行，不锁间隙
```

---

## 14. MVCC 完整工作原理总结

### 一、MVCC 的核心目标

**让读写操作互不阻塞，提高并发性能。**

```
传统方式：读和写互相阻塞
MVCC：读取历史版本，写操作创建新版本
结果：读写并发进行
```

### 二、MVCC 的三大组件

#### 1. 隐藏字段
```
每行记录包含：
- trx_id：最后修改此行的事务ID
- roll_ptr：指向上一个版本的指针（undo log）
```

#### 2. undo log（版本链）
```
保存数据的历史版本，通过 roll_ptr 连接成链：
当前版本 → 版本1 → 版本2 → ... → 最早版本
```

#### 3. ReadView（读视图）
```
记录事务状态的快照：
- m_ids：活跃事务列表
- min_trx_id：最小活跃事务ID
- max_trx_id：下一个要分配的事务ID
- creator_trx_id：创建者的事务ID
```

### 三、完整工作流程

```
开始
  ↓
执行 UPDATE/DELETE
  ↓
1. 分配事务ID（如果还没有）
  ↓
2. 保存旧版本到 undo log
  ↓
3. 更新当前记录（修改 trx_id 和 roll_ptr）
  ↓
4. 形成版本链
  ↓
执行 SELECT（快照读）
  ↓
5. 创建 ReadView（RC 每次创建，RR 复用）
  ↓
6. 读取当前记录
  ↓
7. 检查 trx_id 的可见性
  ↓
8. 不可见？通过 roll_ptr 找上一个版本
  ↓
9. 重复步骤7，直到找到可见版本
  ↓
10. 返回结果
  ↓
结束
```

### 四、可见性判断规则

```python
def is_visible(trx_id, ReadView):
    # 规则1：自己的修改
    if trx_id == ReadView.creator_trx_id:
        return True  # 可见
    
    # 规则2：很早就提交了
    if trx_id < ReadView.min_trx_id:
        return True  # 可见
    
    # 规则3：在我之后才开始
    if trx_id >= ReadView.max_trx_id:
        return False  # 不可见
    
    # 规则4：在活跃范围内
    if ReadView.min_trx_id <= trx_id < ReadView.max_trx_id:
        if trx_id in ReadView.m_ids:
            return False  # 未提交，不可见
        else:
            return True   # 已提交，可见
```

### 五、隔离级别的影响

#### READ COMMITTED (RC)
```
特点：每次 SELECT 创建新的 ReadView
结果：能读到最新提交的数据
问题：可能出现不可重复读

时间线：
BEGIN
  SELECT age;  -- 10（创建 ReadView1）
  -- 其他事务提交，age 变为 20
  SELECT age;  -- 20（创建 ReadView2，读到新数据）
COMMIT
```

#### REPEATABLE READ (RR)
```
特点：第一次 SELECT 创建 ReadView，后续复用
结果：同一事务内读取一致
问题：快照读能解决幻读，当前读不能

时间线：
BEGIN
  SELECT age;  -- 10（创建 ReadView1）
  -- 其他事务提交，age 变为 20
  SELECT age;  -- 10（复用 ReadView1，读到旧数据）
COMMIT
```

### 六、快照读 vs 当前读

| 维度 | 快照读 | 当前读 |
|-----|-------|--------|
| SQL | `SELECT` | `SELECT ... FOR UPDATE`<br>`UPDATE/DELETE/INSERT` |
| 读取方式 | 通过 MVCC 读历史版本 | 直接读最新版本 |
| 是否加锁 | 否 | 是（排他锁或共享锁） |
| 是否创建 ReadView | 是 | 否 |
| 读到的数据 | 可能是旧版本 | 一定是最新版本 |
| 阻塞情况 | 不阻塞 | 可能阻塞（等待锁） |

### 七、MVCC 的局限性

#### 1. 只对快照读有效
```sql
BEGIN;
  SELECT * FROM user;  -- 快照读，使用 MVCC ✅
  SELECT * FROM user FOR UPDATE;  -- 当前读，不使用 MVCC ❌
COMMIT;
```

#### 2. 无法完全解决幻读
```sql
BEGIN;
  SELECT * FROM user WHERE age > 20;  -- 0 行（快照读）
  -- 其他事务插入 age=25
  INSERT INTO user VALUES (10, 'Alice', 30);  -- 报错（当前读发现冲突）
COMMIT;
```

**解决方案：使用当前读 + 间隙锁**
```sql
BEGIN;
  SELECT * FROM user WHERE age > 20 FOR UPDATE;  -- 加间隙锁
  -- 其他事务的插入会被阻塞
  INSERT INTO user VALUES (10, 'Alice', 30);  -- 成功
COMMIT;
```

### 八、事务ID的分配规则

```
分配时机：
✅ INSERT - 第一次插入时分配
✅ UPDATE - 第一次更新时分配
✅ DELETE - 第一次删除时分配

不分配时机：
❌ SELECT - 普通查询不分配
❌ SELECT FOR UPDATE - 加锁查询也不分配

特点：
- 同一事务只分配一个 trx_id
- 只读事务不分配 trx_id（creator_trx_id = 0）
```

### 九、锁的分类与组合

#### 性质维度
```
- 共享锁（S锁）：允许多个事务同时读
- 排他锁（X锁）：只允许一个事务读写
```

#### 范围维度
```
- 行锁：锁定一行记录
- 间隙锁：锁定记录之间的间隙
- 临键锁：行锁 + 间隙锁
```

#### 组合关系
```
FOR UPDATE：排他锁 + (行锁 或 临键锁)
  - 主键查询 → 只加行锁
  - 范围查询 → 加临键锁（行锁+间隙锁）

LOCK IN SHARE MODE：共享锁 + (行锁 或 临键锁)
```

### 十、间隙锁范围规则

```
1. 等值存在：锁行 + 前后间隙
2. 等值不存在：锁所在间隙
3. 范围查询：锁所有行 + 前后间隙到边界
4. 主键查询：只锁行，不锁间隙
```

### 十一、知识体系图

```
MVCC（多版本并发控制）
├── 目标：读写并发，互不阻塞
├── 组件
│   ├── 隐藏字段（trx_id, roll_ptr）
│   ├── undo log（版本链）
│   └── ReadView（读视图）
├── 工作流程
│   ├── 写操作：生成版本链
│   └── 读操作：通过 ReadView 判断可见性
├── 隔离级别
│   ├── RC：每次创建 ReadView
│   └── RR：首次创建，后续复用
├── 读取方式
│   ├── 快照读：使用 MVCC
│   └── 当前读：不使用 MVCC，需要加锁
└── 局限性
    ├── 只对快照读有效
    └── 当前读需要加锁防止幻读
```

### 十二、关键要点回顾

1. **MVCC 通过保存多版本实现读写并发**
2. **版本链通过 undo log 和 roll_ptr 构建**
3. **ReadView 决定哪些版本可见**
4. **RC 每次创建 ReadView，RR 复用 ReadView**
5. **快照读使用 MVCC，当前读不使用**
6. **事务ID 只在修改数据时分配**
7. **间隙锁防止幻读，范围由索引相邻记录决定**

---

## 常见面试问题

### Q1: 在 MVCC 里，undo log 和版本链里的事务 id，它对应的事务都是已提交的吗？

**答：不是的。**

版本链里的事务 ID 对应的事务可能有三种状态：
1. **未提交的事务**：正在执行中
2. **已提交的事务**：已经 COMMIT
3. **已回滚的事务**：已经 ROLLBACK

MVCC 通过 ReadView 的可见性规则来判断哪些版本可见：
- 如果事务 ID 在 `m_ids` 中，说明未提交，不可见
- 如果事务 ID 不在 `m_ids` 中且小于 `max_trx_id`，说明已提交，可见

### Q2: 如果我的语句是 `begin: update money = 10; update money = 20;` 此时会生成 2 个事务吗？

**答：不会。**

这是 **1个事务**，但会生成 **2个版本**（2条 undo log 记录）。

- 事务 ID 在第一次 UPDATE 时分配（假设是 100）
- 两次 UPDATE 都使用同一个 trx_id = 100
- 每次 UPDATE 都会在 undo log 中生成一个新版本

版本链：
```
当前：money=20, trx_id=100
  ↓
版本1：money=10, trx_id=100  ← 同一个事务ID
  ↓
版本2：money=5,  trx_id=99
```

### Q3: 事务ID 在什么时候分配？SELECT 操作会分配事务ID吗？

**答：事务ID 只在第一次修改数据时分配。**

**会分配事务ID：**
- INSERT
- UPDATE
- DELETE

**不会分配事务ID：**
- SELECT（普通查询）
- SELECT FOR UPDATE（加锁查询）

只读事务不会分配事务ID，在 ReadView 中 `creator_trx_id = 0`。

### Q4: 读操作会创建 ReadView，但是写操作不会创建 ReadView 吗？

**答：是的。**

- **SELECT（快照读）**：会创建 ReadView
- **SELECT FOR UPDATE（当前读）**：不创建 ReadView
- **UPDATE/DELETE/INSERT**：不创建 ReadView

写操作使用"当前读"机制，直接读取最新版本，不需要 ReadView。

### Q5: 间隙锁和 `FOR UPDATE` 的关系是什么？

**答：`FOR UPDATE` 一定加排他锁，是否加间隙锁取决于查询条件。**

- **精确匹配主键**：只加行锁
  ```sql
  SELECT * FROM user WHERE id = 10 FOR UPDATE;
  ```

- **范围查询**：加临键锁（行锁 + 间隙锁）
  ```sql
  SELECT * FROM user WHERE age > 20 FOR UPDATE;
  ```

间隙锁的目的是防止幻读，锁定记录之间的"空位"，防止其他事务插入新数据。

---

## 总结

恭喜你完成了 MVCC 的完整学习！

现在你已经掌握了：
- ✅ MVCC 的基本概念和实现原理
- ✅ 版本链、ReadView、可见性判断
- ✅ RC 和 RR 隔离级别的区别
- ✅ 快照读和当前读
- ✅ 幻读问题和间隙锁的解决方案
- ✅ 锁的分类和使用场景

**建议下一步：**
1. 动手实践：在 MySQL 中测试不同场景
2. 画图理解：自己画版本链和 ReadView
3. 深入学习：研究 InnoDB 的源码实现

祝你学习愉快！
