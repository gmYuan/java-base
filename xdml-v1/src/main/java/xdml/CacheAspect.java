package xdml;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class CacheAspect {
    // Map<String, Object> cache = new HashMap<>();
    Map<String, Object> cache = new HashMap<>();

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(xdml.anno.Cache)")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        // Object cacheVal = cache.get(methodName);
        Object cacheVal = redisTemplate.opsForValue().get(methodName);

        if (cacheVal != null) {
            System.out.println("Get Val from cache!");
            return cacheVal;
        } else {
            System.out.println("Get Val from database~~");
            Object realVal = joinPoint.proceed();
            // cache.put(methodName, realVal);
            redisTemplate.opsForValue().set(methodName, realVal);
            return realVal;
        }
    }
}
