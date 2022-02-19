# Getting Started
## System Requirement to run this:
1. Java 11
2. mysql database

##Steps to follow for running the application:
1. Run queries given in db_schema.sql
2. Update database username and password if required, Default username and password for database is 'admin' and 'password'. You can update it from application.properties  file
3. Now can run the application.
4. Command to run the application : ./gradlew bootRun

Congratulation! your server must have started now.

##Note:
1. While transferring, your account balance should be greater than transfer amount
2. Only 4 customers ids(1,2,3,4) are configured by default. You can update it from customer.json file in resources

Postman Collection URL: https://www.getpostman.com/collections/565b19c663a5a204a16a

CURLs:
## For Create Account
```
curl --location --request POST 'http://localhost:8080/api/fakebank/createAccount' \
--header 'Content-Type: application/json' \
--data-raw '{
"customerId": 2,
"openingBalance": "200000"
}'
```

## For Fetch Balance
```
curl --location --request GET 'http://localhost:8080/api/fakebank/fetchBalance?account_number=1644148676260&customer_id=2'
```

## For Transfer between accounts
```
curl --location --request POST 'http://localhost:8080/api/fakebank/transfer' \
--header 'Content-Type: application/json' \
--data-raw '{
"customerId": 2,
"debitAccount": 1644148676260,
"creditAccount": 1644148664661,
"transferAmount": 10000
}'
```

## For Fetch Transfer History
```
curl --location --request GET 'http://localhost:8080/api/fakebank/fetchTransferHistory?account_number=1644148676260&customer_id=2'
```


# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.3/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

