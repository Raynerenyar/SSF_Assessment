# The database used for this branch of SSF assessment was changed from Redis to MySQL.


# Before starting application

## 1. Add.env with the following contents
- ### For localhost development
    ```
    MYSQLUSER=<user>
    MYSQLPASSWORD=<password>
    MYSQLDATABASE=
    MYSQLHOST=
    MYSQLPORT=
    MYSQL_URL=jdbc:mysql://localhost/<schema_name>
    ```
    <schema_name> = pizza
- ### For deploying to Railway MySQL and using root user
    `MYSQLUSER=root` if using root user.

    Spring runs `MySqlConfig.java` and pulls railway's environment variables to connect to railway's MySQL.

- ### For deploying to Railway MySQL and using new user
    #### share these variables to railway
    ```
    MYSQLUSER=<user>
    MYSQLPASSWORD=<password>
    ```

## 2. Create database and tables with:
[pizza Database](src/main/java/sg/edu/nus/iss/app/assessment/database)