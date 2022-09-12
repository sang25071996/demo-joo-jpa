package jooq.demo.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class SpringIntergrationLockConfiguration {

  private static final String LOCK_PREFIX = "Lock";
  private static final long EXPIRE_AFTER = 60000L * 5;

  @Bean
  public RedisLockRegistry redisLockRegistry(RedisConnectionFactory connectionFactory) {
    return new RedisLockRegistry(connectionFactory, LOCK_PREFIX, EXPIRE_AFTER);
  }

}
