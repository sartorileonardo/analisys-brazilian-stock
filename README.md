# spring-boot-demo-mongodb

> Spring boot with MongoDB

## Run Reactive MongoDB with docker


1. Run container：`docker run -it mongo`
2. Get container IP：`docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <id_container>`
3. Add container IP to your application.yml


## application.yml

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://172.17.0.2:27017/webflux_demo
```

## Stock.java
```kotlin


```


## StockRepository.java
```kotlin

```

## StockController.java
```kotlin

```

## StockControllerTest.java
```kotlin

```

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
