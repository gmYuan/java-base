package com.github.hcsp.annotation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class CacheClassDecorator {
    // 将传入的服务类Class进行增强
    // 使得返回一个具有如下功能的Class：
    // 如果某个方法标注了@Cache注解，则返回值能够被自动缓存注解所指定的时长
    // 这意味着，在短时间内调用同一个服务的同一个@Cache方法两次
    // 它实际上只被调用一次，第二次的结果直接从缓存中获取
    // 注意，缓存的实现需要是线程安全的
    @SuppressWarnings("unchecked")
    public static <T> Class<T> decorate(Class<T> klass) {
        return (Class<T>) new ByteBuddy()
            .subclass(klass)
            .method(ElementMatchers.isAnnotatedWith(Cache.class))
            .intercept(MethodDelegation.to(CacheAdvisor.class))
            .make()
            .load(klass.getClassLoader())
            .getLoaded();
    }

    public static class CacheAdvisor {
        private static ConcurrentHashMap<CacheKey, CacheValue> cache = new ConcurrentHashMap<>();
        @RuntimeType
        public static Object cache(
            @SuperCall Callable<Object> zuper,
            @Origin Method method,
            @This Object thisObj,
            @AllArguments Object[] args
            ) throws Exception {
            CacheKey cacheKey = new CacheKey(thisObj, method.getName(), args);
            final CacheValue cacheRes = cache.get(cacheKey);
            // 存在缓存值 + 缓存未过期，才直接返回 之前的缓存值
            if (cacheRes != null && !cacheExpired(cacheRes, method)) {
                return cacheRes.value;
            }
            // 其他情况（缓存值过期/ 无缓存）：调用真正的方法 + 存入缓存时间
            return invokeRealMethodAndSaveCache(zuper, cacheKey);
        }
        private static boolean cacheExpired(CacheValue cacheValue, Method method) {
            long preCalledTime = cacheValue.time;
            int setTime = method.getAnnotation(Cache.class).cacheSeconds();
            return System.currentTimeMillis() - preCalledTime > setTime * 1000L;
        }

        private static Object invokeRealMethodAndSaveCache(
            @SuperCall Callable<Object> superCall,
            CacheKey cacheKey
        ) throws Exception {
            Object relRes = superCall.call();
            cache.put(cacheKey, new CacheValue(relRes, System.currentTimeMillis()));
            return relRes;
        }
    }

    private static class CacheKey {
        private Object thisObj;
        private String methodName;
        private Object[] args;
        public CacheKey(Object thisObj, String methodName, Object[] args) {
            this.thisObj = thisObj;
            this.methodName = methodName;
            this.args = args;
        }
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(thisObj, cacheKey.thisObj) && Objects.equals(methodName, cacheKey.methodName) && Objects.deepEquals(args, cacheKey.args);
        }
        @Override
        public int hashCode() {
            return Objects.hash(thisObj, methodName, Arrays.hashCode(args));
        }
    }
    private static class CacheValue {
        private Object value;
        private long time;
        public CacheValue(Object value, long time) {
            this.value = value;
            this.time = time;
        }
    }


    public static void main(String[] args) throws Exception {
        DataService dataService = decorate(DataService.class).getConstructor().newInstance();

        // 有缓存的查询：只有第一次执行了真正的查询操作，第二次从缓存中获取
        System.out.println(dataService.queryData(1));
        Thread.sleep(1 * 1000);
        System.out.println(dataService.queryData(1));

        // 无缓存的查询：两次都执行了真正的查询操作
        System.out.println(dataService.queryDataWithoutCache(1));
        Thread.sleep(1 * 1000);
        System.out.println(dataService.queryDataWithoutCache(1));
    }
}
