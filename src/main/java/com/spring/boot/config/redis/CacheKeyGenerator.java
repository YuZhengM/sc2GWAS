package com.spring.boot.config.redis;

import com.github.javaparser.utils.ClassUtils;
import com.google.common.hash.Hashing;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.util.result.Page;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * @author Yu Zhengmin
 */
@Component
public class CacheKeyGenerator implements KeyGenerator {

    private static final Log log = LogFactory.getLog(CacheKeyGenerator.class);

    public static final String NO_PARAM_KEY = "NO";
    public static final String NULL_PARAM_KEY = "NULL";
    public static final String CONNECTION_SYMBOL = "_";

    @Override
    public String generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getSimpleName()).append(CONNECTION_SYMBOL).append(method.getName()).append(CONNECTION_SYMBOL);
        if (params.length == 0) {
            return key.append(NO_PARAM_KEY).toString();
        }
        // 循环参数
        for (Object param : params) {
            // 判空
            if (param == null) {
                log.info("input null param for Spring cache, use default key={}", NULL_PARAM_KEY);
                key.append(NULL_PARAM_KEY);
                // 判断是否为包装类
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String || param instanceof Page) {
                key.append(param);
            } else {
                log.info("Using an object as a cache key may lead to unexpected results. Either use @Cacheable(key=..) or implement CacheKey. Method is " + target.getClass() + "#" + method.getName());
                key.append(param.hashCode());
            }
            key.append(CONNECTION_SYMBOL);
        }
        String finalKey = key.toString();
        // hash 函数
        long cacheKeyHash = Hashing.murmur3_128().hashString(finalKey, Charset.defaultCharset()).asLong();
        log.debug("using cache key=[{}], hashCode=[{}]", finalKey, cacheKeyHash);
        return finalKey;
    }
}
