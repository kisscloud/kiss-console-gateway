# 通用后台网关

## 1. 程序运行

```
$ git clone https://github.com/kisscloud/kiss-console-gateway.git
$ cd kiss-console-gateway
$ mvn install -Dmaven.test.skip=true
$ mvn package -Dmaven.test.skip=true
$ java -jar -Dspring.config.location=/opt/configs/kiss-console-gatewa/application.yml /opt/apps/kiss-console-gatewa/target/kiss-account-0.0.1-SNAPSHOT.jar
```

## 2. 配置文件

编辑配置文件：

```
$ vim /opt/configs/kiss-console-gatewa/application.yml
```

配置文件内容：

```
# 服务名称
spring:
  application:
    name: kiss-console-gateway

# 服务端口
server:
  port: 8100

# 服务中心地址
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

# 超时配置
ribbon:
  ConnectTimeout: 60000
  ReadTimeout: 60000
```
