package sg.edu.nus.iss.app.assessment.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${REDISHOST}")
    private String REDIS_HOST;
    // Optional so that there's a default value to fallback to
    // value redis host from application.properties
    @Value("${REDISPORT}")
    private Optional<Integer> REDIS_PORT;

    @Value("${REDISUSER}")
    private String REDIS_USERNAME;

    @Value("${REDISPASSWORD}")
    private String REDIS_PASSWORD;

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemp() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        // @Value("${spring.redis.username}") final String REDIS_USERNAME;

        config.setPort(REDIS_PORT.get());

        // if not localhost then config set
        if (!REDIS_HOST.equalsIgnoreCase("localhost")) {
            config.setUsername(REDIS_USERNAME);
            config.setPassword(REDIS_PASSWORD);
        }

        config.setDatabase(0);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // associate with redis connection
        redisTemplate.setConnectionFactory(jedisFac);

        // not necessary to set this as it defaults to string
        // StringRedisSerializer converts String into bytes as Redis stores data in
        // bytes. It can also do the reverse.
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // this is for direct key value in redis
        // redisTemplate.setValueSerializer(new StringRedisSerializer()); // this is for
        // direct key value in redis

        // enable redis map key to store String
        // usually default is string
        // redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> objSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());

        // enable redis to store java object on the map-value column
        // redisTemplate.setHashValueSerializer(objSerializer);

        redisTemplate.setValueSerializer(new StringRedisSerializer());
        // redisTemplate.setValueSerializer(objSerializer);

        return redisTemplate;
    };

}
