package jooq.demo.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class SpringIntegrationLockConfiguration {

  private static final String LOCK = "lock";
  private static final long EXPIRE_AFTER = 60000L * 5;

  @Bean
  public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
    return new RedisLockRegistry(redisConnectionFactory, LOCK, EXPIRE_AFTER);
  }
}
