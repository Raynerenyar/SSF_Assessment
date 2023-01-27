package sg.edu.nus.iss.app.assessment.config;

import java.util.Optional;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Bean;

@Configuration
public class RedisConfig {

    /*
     * ==================================================
     * All config values from .env file or railway's .env
     * application.properties contains:
     * spring.config.import=optional:file:.env[.properties]
     * to retrieve values from .env
     * ==================================================
     */
    @Value("${REDISHOST}")
    private String redisHost;

    // Optional so that there's a default value to fallback to
    // value redis host from application_properties
    @Value("${REDISPORT}")
    private Integer redisPort;

    @Value("${REDISUSER}")
    private String redisUsername;

    @Value("${REDISPASSWORD}")
    private String redisPassword;

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemp() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(redisHost);
        config.setPort(redisPort);

        // if not localhost then config set username and password
        if (!redisHost.equalsIgnoreCase("localhost")) {
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }
        // if (!redisUsername.isEmpty() && !redisPassword.isEmpty()) {
        // config.setUsername(redisUsername);
        // config.setPassword(redisPassword);
        // }

        config.setDatabase(0);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // associate with redis connection
        redisTemplate.setConnectionFactory(jedisFac);

        /*
         * not necessary to set this as it defaults to string
         * StringRedisSerializer converts String into bytes as Redis stores data in
         * bytes. It can also do the reverse.
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // this is for direct key value in redis
        // redisTemplate.setValueSerializer(new StringRedisSerializer()); // this is for
        // direct key value in redis

        /*
         * enable redis map key to store String
         * usually default is string
         */
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> objSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());

        // enable redis to store java object in map value
        // redisTemplate.setHashValueSerializer(objSerializer);
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        // enable redis to store java object on the value column
        // redisTemplate.setValueSerializer(objSerializer);
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;

    };

}
