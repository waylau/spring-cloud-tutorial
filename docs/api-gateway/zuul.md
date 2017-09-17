# 如何集成Zuul

路由是微服务架构中必须的一部分，比如，“/” 可能映射到你的WEB程序上，“/api/users”可能映射到你的用户服务上，“/api/shop”可能映射到你的商品服务商。通过路由，让不同的服务，都集中到统一的入口上来，这就是 API 网关的作用。

Zuul是Netflix出品的一个基于JVM路由和服务端的负载均衡器.

Zuul功能包括：

* 认证
* 压力测试
* 金丝雀测试
* 动态路由
* 负载削减
* 安全
* 静态响应处理
* 主动/主动交换管理

Zuul的规则引擎允许通过任何JVM语言来编写规则和过滤器, 支持基于Java和Groovy的构建。

配置属性 zuul.max.host.connections 已经被两个新的配置属性替代, zuul.host.maxTotalConnections （总连接数）和 zuul.host.maxPerRouteConnections,（每个路由连接数） 默认值分别是200和20。



在 `micro-weather-eureka-client`  的基础上稍作修改即可。新的项目称为`micro-weather-zuul` 。

## 开发环境

* Gradle 4.0
* Spring Boot 2.0.0.M3
* Spring Cloud Netflix Eureka Client Finchley.M2
* Spring Cloud Netflix Zuul Finchley.M2

## 更改配置

增加如下配置：

```groovy
dependencies {
    //...

	compile('org.springframework.cloud:spring-cloud-starter-netflix-zuul')

	//...
}
```