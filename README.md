# 通用后台网关

## 1. 程序运行

```
$ git clone https://github.com/kisscloud/kiss-console-gateway.git
$ cd kiss-console-gateway
$ mvn install -Dmaven.test.skip=true
$ mvn package -Dmaven.test.skip=true
$ java -jar -Dspring.config.location=/opt/configs/kiss-console-gatewa/application.yml /opt/apps/kiss-console-gatewa/target/kiss-console-gateway-0.0.1-SNAPSHOT.jar
```

## 2. 配置文件

编辑配置文件：

```
$ vim /opt/configs/kiss-console-gateway/application.yml
```

配置文件内容：

```
spring:
  application:
    name: kiss-api-gateway
server:
  port: 8100
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
ribbon:
  ConnectTimeout: 60000
  ReadTimeout: 60000
```
