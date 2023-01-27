## .env values
- All config values from .env file or railway's .env
- application.properties contains:
    `spring.config.import=optional:file:.env[.properties]`
- Retrieve values from .env
- .env is not pushed to github