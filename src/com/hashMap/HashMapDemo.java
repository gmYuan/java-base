package com.hashMap;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Collection;

public class HashMapDemo {
	public static void main(String[] args) {
		Map<String, Double> map = new HashMap<>();

		// P1 size + isEmpty
		System.out.println("map size是: " + map.size());
		System.out.println("map is empty的结果是: " + map.isEmpty());

		// P2 put / putAll/ putIfAbsent

		// put 用于 增/改 元素
		System.out.println("------put方法------");
		Double oldVal = map.put("abc", 123.0);
		System.out.println("put方法返回的上一次结果是: " + oldVal);
		map.put("def", 7.7);
		oldVal = map.put("abc", 2.5);
		System.out.println("put方法返回的第2次的 上一次结果是: " + oldVal);
		System.out.println("map 是: " + map);

		// putIfAbsent 用于 增 元素
		System.out.println("------putIfAbsent 方法------");
		oldVal = map.putIfAbsent("abc", 3.7);
		System.out.println("putIfAbsent方法返回的上一次结果是: " + oldVal);
		oldVal = map.putIfAbsent("ddd", 5.0);
		System.out.println("第2次 putIfAbsent方法返回的上一次结果是: " + oldVal);
		System.out.println("get方法返回的ddd 结果是: " + map.get("ddd"));
		System.out.println("map 是: " + map);

		// putAll: 批量新增，一般不建议使用； 要保证类型限制
		System.out.println("------putAll 方法------");
		Map<String, Double> map2 = new HashMap<>();
		map2.put("eee", 8.8);
		map2.put("fff", 9.9);
		// putAll和上面的put一样， 会覆盖 相同的 key 对应的 value
		map2.put("ddd", 6.9);
		map.putAll(map2);
		System.out.println("map 是: " + map);


		// P3 get / getOrDefault
		System.out.println("------get------");
		// 返回指定 key 对应的 value； 如果 key 不存在，返回 null
		System.out.println("get方法返回的ddd 结果是: " + map.get("ddd"));
		System.out.println("get方法返回的sss 结果是: " + map.get("sss"));

		// getOrDefault: 返回指定 key 对应的 value； 如果 key 不存在，返回默认值
		System.out.println("------getOrDefault------");
		System.out.println("getOrDefault方法返回的ddd 结果是: " + map.getOrDefault("ddd", 1.0));
		System.out.println("getOrDefault方法返回的sss 结果是: " + map.getOrDefault("sss", 3.8));
		System.out.println("get方法返回的sss 结果是: " + map.get("sss"));


		// P4 containsKey / containsValue

		// containsKey: 判断是否包含指定的 key
		System.out.println("------containsKey------");
		System.out.println("containsKey方法返回的ddd 结果是: " + map.containsKey("ddd"));
		System.out.println("containsKey方法返回的sss 结果是: " + map.containsKey("sss"));

		// containsValue: 判断是否包含指定的 value
		System.out.println("------containsValue------");
		System.out.println("map 是: " + map);
		System.out.println("containsValue方法返回的8.8 结果是: " + map.containsValue(8.8));
		System.out.println("containsValue方法返回的10.0 结果是: " + map.containsValue(10.0));

		// P5 remove: 删除指定的 key 对应的映射关系, 返回的是删除的 value;
		// 如果 key 不存在，返回 null
		System.out.println("------remove------");
		System.out.println("map 是: " + map);
		System.out.println("remove方法返回的fff 结果是: " + map.remove("fff"));
		System.out.println("map 是: " + map);

		// remove(key, value): 删除指定的 key 对应的映射关系, 返回的是删除的 value;
		// 如果 key 不存在，返回 false
		// 如果 key 存在，但是 value 不匹配，返回 false
		// 如果 key 存在，并且 value 匹配，返回 true
		boolean isRemoved = map.remove("abc", 1.5);
		System.out.println("remove方法返回的结果是: " + isRemoved);
		System.out.println("map 是: " + map);


		// P6 replace: 替换指定 key 对应的 value，【代替 put 做更新操作】
		// replace(key, oldValue, newValue): 替换指定 key 对应的 value, 如果 key 不存在，返回 false
		// 如果 key 存在，但是 oldValue 不匹配，返回 false
		// 如果 key 存在，并且 oldValue 匹配，返回 true
		System.out.println("------replace------");
		System.out.println("map 是: " + map);
		Double oldVal2 = map.replace("abc", 3.12);
		System.out.println("replace了abc返回的结果是: " + oldVal2);
		System.out.println("map 是: " + map);


		// P7: keySet/ values/ entrySet
		// keySet: 返回所有的 key 对应的 Set 集合
		System.out.println("------keySet------");
		System.out.println("map 是: " + map);
		Set<String> keySet = map.keySet();
		System.out.println("keySet 是: " + keySet);

		// values: 返回所有的 value 对应的 Collection 集合
		System.out.println("------values------");
		System.out.println("map 是: " + map);
		Collection<Double> values = map.values();
		System.out.println("values 是: " + values);

		// entrySet: 返回所有的 key-value 对应的 Set 集合
		System.out.println("------entrySet------");
		System.out.println("map 是: " + map);
		Set<Map.Entry<String, Double>>  entrySet = map.entrySet();
		System.out.println("entrySet 是: " + entrySet);
		for (Map.Entry<String, Double> entry : entrySet) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}




	}
}
