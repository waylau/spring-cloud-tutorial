# 如何集成 Config

本章节，我们将创建一个`micro-weather-config-server` 作为配置服务器的服务端。

## 开发环境

* Gradle 4.0
* Spring Boot 2.0.0.M3
* Spring Cloud Netflix Eureka Client Finchley.M2
* Spring Cloud Config Server Finchley.M2


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

## 一个最简单的 Config Server

主应用：

```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

在程序的入口Application类加上`@EnableConfigServer`注解开启配置服务器的功能。

## 测试

在<https://github.com/waylau/spring-cloud-tutorial/tree/master/config> 我们放置了一个配置文件`micro-weather-config-client-dev.properties`，里面简单的放置了测试内容:

```
auther=waylau.com
```

启动应用，访问<http://localhost:8888/auther/dev>，应能看到如下输出内容，说明服务启动正常。

```json
{"name":"auther","profiles":["dev"],"label":null,"version":"00836f0fb49488bca170c8227e3ef13a5aff2d1a","state":null,"propertySources":[]}
```

其中，在配置中心的文件命名规则如下：

```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```


## 源码

本章节源码，见`micro-weather-config-server` 。