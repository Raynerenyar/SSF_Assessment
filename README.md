# Main branch uses Redis
- [For MySQL, check branch MySQL](https://github.com/Raynerenyar/SSF_Assessment/tree/MySQL)


# Update to README.md[^1]
## Before running server 
### add .env file with
```
REDISHOST=<host>
REDISPORT=<port>
REDISUSER=<username>
REDISPASSWORD=<password>
```
- .env is not pushed to github


# Redis config
- The below redis config values comes from .env file or railway's .env
```java
    @Value("${REDISHOST}")
    private String redisHost;

    @Value("${REDISPORT}")
    private Integer redisPort;

    @Value("${REDISUSER}")
    private String redisUsername;

    @Value("${REDISPASSWORD}")
    private String redisPassword;
```


# In application.properties
- Add into application.properties to allow spring to get key/value from .env:
    `spring.config.import=optional:file:.env[.properties]`

[^1]: Code was not modified after 5pm.