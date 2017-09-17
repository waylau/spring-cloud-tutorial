# 实现服务的负载均衡及高可用

如果你自己观察 Feign 依赖，可以看到，Feign 是依赖了 Ribbon的。

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

有兴趣的朋友，可以自行查看依赖信息：<https://github.com/spring-cloud/spring-cloud-netflix/blob/master/spring-cloud-starter-netflix/spring-cloud-starter-openfeign/pom.xml>

## 客户端负载均衡器——Ribbon

Ribbon 是一个客户端负载平衡器，它可以很好地控制HTTP和TCP客户端的行为。 Feign 已经使用 Ribbon，所以如果你使用`@FeignClient`，就已经启用了客户端负载均衡功能。

Ribbon 的一个中心概念就是命名客户端（named clien）。 每个负载平衡器都是组合的组件的一部分，它们一起工作以根据需要联系远程服务器，并且集合具有您将其作为应用程序开发人员（例如使用`@FeignClient`注解）的名称。 Spring Cloud使用RibbonClientConfiguration为每个命名的客户端根据需要创建一个新的集合作为ApplicationContext。 这包含（其中包括）一个ILoadBalancer，一个RestClient和一个ServerListFilter。

## 实现高可用

将我们需要访问的服务（比如，“micro-weather-eureka-client”），启动为多个示例。当客户端需要访问“micro-weather-eureka-client”时，会自行去选择其中任意一个服务实例来访问，这样，即便其中的某个服务实例不可用，也不会影响整个服务功能。

这样就实现了服务的高可用。