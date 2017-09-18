# 如何集成 Config

本章节，我们将创建一个`micro-weather-config-server` 作为配置服务器的服务端，创建一个`micro-weather-config-client` 作为配置服务器的客户端。

## 开发环境

* Gradle 4.0
* Spring Boot 2.0.0.M3
* Spring Cloud Config Server Finchley.M2
* Spring Cloud Config Client Finchley.M2

## 更改配置

增加如下配置：

```groovy
dependencies {
    //...

	compile('org.springframework.cloud:spring-cloud-starter-netflix-eureka-client')
	compile('org.springframework.cloud:spring-cloud-config-server')

	//...
}
```

项目配置：

```
spring.application.name: micro-weather-config-server
server.port=8888

eureka.client.serviceUrl.defaultZone: http://localhost:8761/eureka/

spring.cloud.config.server.git.uri=https://github.com/waylau/spring-cloud-tutorial
spring.cloud.config.server.git.searchPaths=config
```

其中:

* spring.cloud.config.server.git.uri：配置Git仓库地址
* spring.cloud.config.server.git.searchPaths：配置查找配置的路径


## 测试

启动应用