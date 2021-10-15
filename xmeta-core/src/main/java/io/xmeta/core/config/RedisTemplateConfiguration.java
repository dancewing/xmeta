package io.xmeta.core.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate 配置
 * @author jeff_qian
 */
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfiguration {

	@Bean(name = "redisTemplate")
	@ConditionalOnMissingBean(RedisTemplate.class)
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		StringRedisSerializer redisKeySerializer = new StringRedisSerializer();
		// key 序列化
		redisTemplate.setKeySerializer(redisKeySerializer);
		redisTemplate.setHashKeySerializer(redisKeySerializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}
}
