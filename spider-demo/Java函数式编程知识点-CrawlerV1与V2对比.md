# Java 爬虫知识点总结：CrawlerV1 与 V2 对比

> **作者备注**：本文档总结了 GitHub 爬虫项目的两种实现方式，涵盖传统命令式编程和 Java 8 函数式编程的对比。

---

## 📋 目录

1. [项目概述](#项目概述)
2. [核心 API 知识点](#核心-api-知识点)
3. [CrawlerV1：传统命令式](#crawlerv1传统命令式)
4. [CrawlerV2：函数式编程](#crawlerv2函数式编程)
5. [两种方式对比](#两种方式对比)
6. [Stream API 详解](#stream-api-详解)
7. [常见问题与解决](#常见问题与解决)
8. [知识点速查表](#知识点速查表)

---

## 项目概述

### 功能
从 GitHub 仓库获取前 N 个打开的 Pull Request，并保存为 CSV 文件。

### 输出格式
```csv
number,author,title
77874,jub0bs,internal/stringslite: use bytealg.MakeNoZero in Clone
77862,feizaizheli,syscall: replace panic with error return in Accept4 on FreeBSD
```

---

## 核心 API 知识点

### 1. StringBuilder - 高效字符串拼接

**为什么需要 StringBuilder？**

```java
// ❌ 低效：每次 + 都创建新的 String 对象
String result = "";
for (int i = 0; i < 1000; i++) {
    result = result + i + ",";  // 创建 1000 个临时对象
}

// ✅ 高效：StringBuilder 内部用可变数组
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i).append(",");  // 只在最后转成 String
}
String result = sb.toString();
```

**关键方法**：
- `new StringBuilder(初始内容)` - 创建并初始化
- `.append(内容)` - 追加内容，返回自身（支持链式调用）
- `.toString()` - 转换为 String

---

### 2. File 与 Path 转换

```java
// 旧 API (Java 1.0)：File 类
File file = new File("pulls.csv");

// 新 API (Java 7+)：Path 类，功能更强大
Path path = file.toPath();  // File → Path 转换

// 使用新 API 写文件
Files.write(path, bytes);
```

**为什么要转换？**
- `Files.write()` 方法需要 `Path` 类型参数
- `Path` API 提供更多现代化的文件操作功能

---

### 3. 字符串转字节数组

```java
String text = "Hello世界";

// 使用默认字符集（通常是 UTF-8）
byte[] bytes = text.getBytes();
// 结果：[72, 101, 108, 108, 111, -28, -72, -106, -25, -107, -116]

// 显式指定字符集（推荐）
byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
```

**为什么需要字节数组？**
- 文件、网络传输等底层操作都基于**字节**
- 字符需要通过**字符编码**转换为字节

---

### 4. Files.write() 的两种重载

```java
// 方式1：写入字节数组
Files.write(Path path, byte[] bytes)

// 方式2：写入字符串列表（每个元素一行，自动添加换行符）
Files.write(Path path, Iterable<? extends CharSequence> lines)
```

**使用示例**：
```java
// V1 使用方式1
Files.write(csvFile.toPath(), CsvContent.toString().getBytes());

// V2 使用方式2
Files.write(csvFile.toPath(), lines);  // lines 是 List<String>
```

---

## CrawlerV1：传统命令式

### 完整代码

```java
public static void savePullRequestsToCSV(String repo, int count, File csvFile) throws IOException {
    System.out.println("正在处理仓库：" + repo);
    GitHub github = GitHub.connectAnonymously();
    GHRepository repository = github.getRepository(repo);
    
    // 使用迭代器，只取前 count 条
    Iterator<GHPullRequest> it = repository.getPullRequests(GHIssueState.OPEN).iterator();
    StringBuilder CsvContent = new StringBuilder("number,author,title\n");
    int actualCount = 0;
    
    while (it.hasNext() && actualCount < count) {
        GHPullRequest pr = it.next();
        int number = pr.getNumber();
        String author = pr.getUser().getLogin();
        String title = pr.getTitle();
        CsvContent.append(number).append(",").append(author).append(",").append(title).append("\n");
        actualCount++;
    }
    
    Files.write(csvFile.toPath(), CsvContent.toString().getBytes());
}
```

### 核心知识点

#### 1. Iterator 迭代器

```java
Iterator<GHPullRequest> it = collection.iterator();
while (it.hasNext()) {
    GHPullRequest pr = it.next();
    // 处理 pr
}
```

**特点**：
- 惰性加载，不会一次性加载所有数据
- 适合处理大量数据或分页数据
- 可以手动控制何时停止迭代

#### 2. StringBuilder 链式调用

```java
CsvContent.append(number)
          .append(",")
          .append(author)
          .append(",")
          .append(title)
          .append("\n");
```

每个 `append()` 都返回 `this`，所以可以连续调用。

#### 3. 数据转换流程

```
StringBuilder → String → byte[] → 文件
    ↓            ↓         ↓
.toString() .getBytes() Files.write()
```

### 优点
- **易理解**：逻辑清晰，适合初学者
- **性能好**：只迭代需要的数据，不加载全部
- **控制精细**：可以随时中断、添加条件判断

### 缺点
- **代码冗长**：需要手动管理循环、计数器
- **可读性一般**：需要仔细阅读循环体才能理解意图

---

## CrawlerV2：函数式编程

### 完整代码

```java
public static void savePullRequestsToCSV(String repo, int count, File csvFile) throws IOException {
    System.out.println("正在处理仓库：" + repo);
    GitHub github = GitHub.connectAnonymously();
    GHRepository repository = github.getRepository(repo);
    
    // 使用 Stream API
    List<String> lines = repository.getPullRequests(GHIssueState.OPEN)
        .stream()
        .limit(count)
        .map(CrawlerV2::getLine)
        .collect(Collectors.toList());
    
    lines.add(0, "number,author,title");
    Files.write(csvFile.toPath(), lines);
}

public static String getLine(GHPullRequest pullRequest) {
    try {
        return pullRequest.getNumber() + "," + 
               pullRequest.getUser().getLogin() + "," + 
               pullRequest.getTitle();
    } catch (IOException e) {
        throw new UncheckedIOException(e);
    }
}
```

### 核心知识点

#### 1. Stream API 流式处理

```java
collection.stream()         // 1. 打开流
    .limit(10)              // 2. 中间操作：限制数量
    .map(转换函数)           // 3. 中间操作：转换
    .collect(收集器);        // 4. 终端操作：收集结果
```

**类比**：Stream 就像水流管道

```
数据源 → stream() → filter → map → collect → 结果
[数据]   打开水龙头  过滤    加工   关闭收集   [容器]
```

#### 2. 方法引用 `::`

```java
// 方法引用（简洁）
.map(CrawlerV2::getLine)

// 等价的 Lambda 表达式
.map(pr -> CrawlerV2.getLine(pr))

// 等价的匿名内部类（Java 7 之前）
.map(new Function<GHPullRequest, String>() {
    @Override
    public String apply(GHPullRequest pr) {
        return CrawlerV2.getLine(pr);
    }
})
```

**方法引用的几种形式**：
- `类名::静态方法` → `CrawlerV2::getLine`
- `对象::实例方法` → `System.out::println`
- `类名::实例方法` → `String::toLowerCase`
- `类名::new` → `ArrayList::new`（构造方法引用）

#### 3. UncheckedIOException 异常包装

**为什么需要？**

```java
// ❌ Stream 的 map() 不允许抛出检查型异常
.map(pr -> pr.getTitle())  // 编译错误：Unhandled IOException

// ✅ 包装成非检查型异常
.map(pr -> {
    try {
        return pr.getTitle();
    } catch (IOException e) {
        throw new UncheckedIOException(e);  // 运行时异常，不需要声明
    }
})
```

**异常类型**：
- **检查型异常（Checked）**：IOException、SQLException，必须 try-catch 或声明 throws
- **非检查型异常（Unchecked）**：RuntimeException 子类，不需要显式处理

#### 4. List 操作

```java
List<String> lines = new ArrayList<>();
lines.add("first");           // 追加到末尾
lines.add(0, "header");       // 插入到指定位置（0 = 开头）
lines.addAll(otherList);      // 追加整个集合
```

### 优点
- **简洁优雅**：一眼看出"取10个、转换、收集"
- **可组合**：轻松添加 `.filter()` `.sorted()` 等操作
- **声明式**：描述"要什么"而不是"怎么做"
- **并行化**：改成 `.parallelStream()` 就能多线程处理

### 缺点
- **学习曲线**：需要理解函数式编程概念
- **调试困难**：链式调用不容易打断点
- **性能陷阱**：如果不注意，可能触发不必要的全量加载

---

## 两种方式对比

### 功能对比表

| 特性 | CrawlerV1（命令式） | CrawlerV2（函数式） |
|------|-------------------|-------------------|
| **循环方式** | `Iterator` + `while` | `.stream()` |
| **限制数量** | `actualCount < count` | `.limit(count)` |
| **数据转换** | 循环内手动拼接 | `.map(CrawlerV2::getLine)` |
| **结果收集** | `StringBuilder` | `.collect(Collectors.toList())` |
| **写入文件** | `getBytes()` | 直接写 `List<String>` |
| **代码行数** | ~20 行 | ~8 行 |
| **易理解性** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **简洁度** | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ |

### 性能对比

```java
// V1：只迭代 10 次，性能最优
while (it.hasNext() && actualCount < 10) { ... }

// V2：理论上也只处理 10 个（Stream 的惰性求值）
.limit(10).map(...).collect(...)
```

**注意**：如果不小心写成这样，会导致性能问题：

```java
// ❌ 错误：先 toList() 再 stream()，会加载全部数据
List<GHPullRequest> all = repository.getPullRequests(GHIssueState.OPEN).toList();
all.stream().limit(10)...

// ✅ 正确：直接在 PagedIterable 上 stream()
repository.getPullRequests(GHIssueState.OPEN).stream().limit(10)...
```

### 等价代码对比

```java
// ===== V1 核心逻辑 =====
Iterator<GHPullRequest> it = prs.iterator();
StringBuilder csv = new StringBuilder("header\n");
int count = 0;
while (it.hasNext() && count < 10) {
    GHPullRequest pr = it.next();
    csv.append(pr.getNumber() + "," + pr.getUser().getLogin() + "\n");
    count++;
}
Files.write(path, csv.toString().getBytes());

// ===== V2 核心逻辑 =====
List<String> lines = prs.stream()
    .limit(10)
    .map(pr -> pr.getNumber() + "," + pr.getUser().getLogin())
    .collect(Collectors.toList());
lines.add(0, "header");
Files.write(path, lines);
```

---

## Stream API 详解

### 1. 为什么必须先 `.stream()`？

**设计原因：职责分离**

```java
// Collection（集合）：负责「存储」数据
List<String> names = new ArrayList<>();
names.add("Alice");
names.get(0);

// Stream（流）：负责「处理」数据
names.stream()
    .filter(...)
    .map(...)
    .collect(...);
```

- `List` 从 Java 1.2 就存在，添加新方法会破坏兼容性
- `Stream` 是 Java 8 引入的新 API，专门负责数据处理
- 职责单一原则：List 管存储，Stream 管处理

### 2. Stream 的"流"是什么意思？

#### 物理意义：数据流过管道

```
源数据 → filter → map → sorted → collect → 结果
 [1,2,3,4,5]
    ↓ 过滤
  [2,4]
    ↓ 转换
  [4,8]
    ↓ 收集
 List[4,8]
```

#### 编程意义：函数式管道

```java
List<Integer> result = numbers.stream()  // 打开水龙头
    .filter(n -> n % 2 == 0)              // 管道1：过滤
    .map(n -> n * 2)                      // 管道2：转换
    .collect(Collectors.toList());        // 关闭水龙头
```

### 3. Stream 方法分类

| 类型 | 方法 | 返回值 | 说明 |
|------|------|--------|------|
| **中间操作** | `map()` | `Stream` | 转换元素 |
| | `filter()` | `Stream` | 过滤元素 |
| | `sorted()` | `Stream` | 排序 |
| | `limit()` | `Stream` | 限制数量 |
| | `skip()` | `Stream` | 跳过前 N 个 |
| | `distinct()` | `Stream` | 去重 |
| **终端操作** | `collect()` | 集合/值 | 收集结果 |
| | `forEach()` | `void` | 遍历 |
| | `count()` | `long` | 计数 |
| | `findFirst()` | `Optional` | 找第一个 |
| | `anyMatch()` | `boolean` | 是否有匹配 |

**关键特性**：
- **中间操作**：返回 Stream，可以链式调用，**惰性求值**（不会立即执行）
- **终端操作**：返回具体结果，触发整个流的执行

### 4. 惰性求值（Lazy Evaluation）

```java
Stream<Integer> stream = list.stream()
    .filter(n -> {
        System.out.println("过滤: " + n);
        return n > 5;
    })
    .map(n -> {
        System.out.println("转换: " + n);
        return n * 2;
    });

// 👆 这里什么都不会打印！

stream.collect(Collectors.toList());  // 终端操作，开始执行
// 现在才打印：过滤: 1, 过滤: 2, ...
```

**好处**：优化执行，避免不必要的计算

```java
list.stream()
    .filter(复杂判断)
    .map(复杂计算)
    .limit(1)           // 只要 1 个结果
    .collect(...);

// Stream 只处理到找到第 1 个满足条件的元素就停止
```

### 5. collect() 和 Collectors

**collect() 的作用**：把流收集成集合

```java
Stream<String> stream = ...;

// 收集成 List
stream.collect(Collectors.toList());

// 收集成 Set（自动去重）
stream.collect(Collectors.toSet());

// 拼接成字符串
stream.collect(Collectors.joining(","));
// 结果："a,b,c"

// 拼接成字符串（带前缀/后缀）
stream.collect(Collectors.joining(", ", "[", "]"));
// 结果："[a, b, c]"

// 分组
stream.collect(Collectors.groupingBy(String::length));
// 结果：{1=[a, b], 5=[apple]}

// 计数
stream.collect(Collectors.counting());
// 结果：5L
```

**为什么需要 Collectors？**

```java
// collect() 方法签名（简化版）
<R> R collect(Collector<T, A, R> collector)
```

- `Collector` 定义了如何收集数据
- `Collectors` 提供了常用的收集器实现

### 6. 完整示例：链式操作

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// 找出长度 > 4 的名字，转大写，按字母排序
List<String> result = names.stream()
    .filter(name -> name.length() > 4)     // [Alice, Charlie, David]
    .map(String::toUpperCase)              // [ALICE, CHARLIE, DAVID]
    .sorted()                              // [ALICE, CHARLIE, DAVID]
    .collect(Collectors.toList());

System.out.println(result);  // [ALICE, CHARLIE, DAVID]
```

---

## 常见问题与解决

### 1. SSL 握手失败

**错误信息**：
```
javax.net.ssl.SSLHandshakeException: Remote host terminated the handshake
```

**原因**：GitHub API 只接受 TLS 1.2+，部分 Java 8 环境默认协议不兼容。

**解决方案**：
```java
System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
```

### 2. Read timed out

**错误信息**：
```
java.net.SocketTimeoutException: Read timed out
```

**原因**：访问 api.github.com 超时（默认超时时间太短）。

**解决方案**：
```java
System.setProperty("sun.net.client.defaultConnectTimeout", "60000");  // 60秒
System.setProperty("sun.net.client.defaultReadTimeout", "60000");     // 60秒
```

### 3. 分页请求导致超时

**错误写法**：
```java
// ❌ 会拉取所有页的数据，可能超时
List<GHPullRequest> all = repository.getPullRequests(GHIssueState.OPEN);
```

**正确写法**：
```java
// ✅ V1：用 Iterator 只取前 N 个
Iterator<GHPullRequest> it = repository.getPullRequests(GHIssueState.OPEN).iterator();
while (it.hasNext() && count < 10) { ... }

// ✅ V2：用 Stream 的 limit()
repository.getPullRequests(GHIssueState.OPEN)
    .stream()
    .limit(10)
    ...
```

### 4. Stream 中处理 IOException

**问题**：Stream 的 map/filter 不能抛出检查型异常

**解决方案**：包装成 UncheckedIOException

```java
.map(pr -> {
    try {
        return pr.getTitle();
    } catch (IOException e) {
        throw new UncheckedIOException(e);
    }
})

// 或提取成方法
.map(CrawlerV2::getLine)

public static String getLine(GHPullRequest pr) {
    try {
        return pr.getNumber() + "," + pr.getUser().getLogin();
    } catch (IOException e) {
        throw new UncheckedIOException(e);
    }
}
```

---

## 知识点速查表

### Java 基础 API

| API | 作用 | 示例 |
|-----|------|------|
| `StringBuilder` | 高效拼接字符串 | `sb.append("text")` |
| `File.toPath()` | File 转 Path | `file.toPath()` |
| `String.getBytes()` | 字符串转字节数组 | `"hello".getBytes()` |
| `Files.write(path, bytes)` | 写入字节数组 | `Files.write(path, data)` |
| `Files.write(path, lines)` | 写入字符串列表 | `Files.write(path, List.of("a"))` |

### Stream API

| 操作 | 方法 | 示例 |
|------|------|------|
| 创建流 | `.stream()` | `list.stream()` |
| 限制数量 | `.limit(n)` | `.limit(10)` |
| 过滤 | `.filter(条件)` | `.filter(x -> x > 5)` |
| 转换 | `.map(函数)` | `.map(String::toUpperCase)` |
| 排序 | `.sorted()` | `.sorted()` |
| 收集成 List | `.collect(Collectors.toList())` | - |
| 拼接字符串 | `.collect(Collectors.joining(","))` | - |
| 遍历 | `.forEach(System.out::println)` | - |
| 计数 | `.count()` | - |

### 方法引用

| 形式 | 示例 | 等价 Lambda |
|------|------|-------------|
| 静态方法 | `类名::方法` | `x -> 类名.方法(x)` |
| 实例方法 | `对象::方法` | `x -> 对象.方法(x)` |
| 类的实例方法 | `类名::方法` | `(obj, x) -> obj.方法(x)` |
| 构造方法 | `类名::new` | `x -> new 类名(x)` |

### 常用 Collectors

```java
Collectors.toList()                    // → List
Collectors.toSet()                     // → Set（去重）
Collectors.joining(",")                // → String "a,b,c"
Collectors.joining(", ", "[", "]")    // → String "[a, b, c]"
Collectors.groupingBy(分组函数)        // → Map<K, List<V>>
Collectors.counting()                  // → Long 计数
```

---

## 学习建议

### 对于初学者
1. **先掌握 V1（命令式）**：易理解，概念清晰
2. **理解每个 API 的作用**：StringBuilder、Iterator、Files.write
3. **逐步过渡到 V2（函数式）**：从简单的 Stream 操作开始

### 对于进阶学习
1. **深入理解 Stream 的惰性求值**
2. **学习更多 Collectors 方法**（groupingBy、partitioningBy 等）
3. **尝试并行流 parallelStream()**
4. **学习 Optional 处理空值**

### 实践建议
- 先用 V1 实现功能，确保逻辑正确
- 再用 V2 重构，体会两种风格的差异
- 根据团队习惯和代码可读性选择合适的风格

---

## 记忆口诀

### Stream API 流程
```
源头 stream() 开管道，
中间 map/filter 加工好，
终端 collect 关水闸，
Collectors 告诉用啥装。
```

### StringBuilder 使用
```
拼接字符串别用加，
StringBuilder 效率佳，
append 追加 toString 转，
链式调用更优雅。
```

---

**最后更新**：2026-03-03  
**相关代码**：`CrawlerV1.java`、`CrawlerV2.java`
