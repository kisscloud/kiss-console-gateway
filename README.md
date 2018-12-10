# 通用后台网关

## 1. 程序运行

```
$ git clone github.com/kisscloud/kiss-console-gateway.git
$ cd kiss-eureka-server
$ mvn package
$ java -j kiss-console-gateway.jar
```

## 2. 配置文件

编辑配置文件：

```
$ vim application.properties
```

配置文件内容：

```
# 服务名称
spring.application.name=kiss-api-gateway

# 服务端口
server.port=8100

# 服务中心地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# 超时配置
ribbon.ConnectTimeout=60000
ribbon.ReadTimeout=60000
```
