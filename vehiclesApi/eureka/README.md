# Eureka

Eureka is a REST (Representational State Transfer) based service that is primarily used in the AWS cloud for locating services for the purpose of load balancing and failover of middle-tier servers.

## Instructions

Via shell it can be started using

```
$ mvn clean package
```

```
$ java -jar target/eureka-0.0.1-SNAPSHOT.jar
```

The service is available by default on port `8761`. You can check it on the 
command line by using

```
$ curl http://localhost:8761/
``` 

More information:
https://github.com/Netflix/eureka