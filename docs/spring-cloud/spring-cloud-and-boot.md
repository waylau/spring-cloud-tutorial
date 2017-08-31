# Spring Cloud 与 Spring Boot 的关系

Spring Boot 是构建 Spring Cloud 架构的基石，是一种快速启动项目的方式。

Spring Cloud 对应于 Spring Boot 版本，由以下依赖关系。

Finchley builds and works with Spring Boot 2.0.x, and is not expected to work with Spring Boot 1.5.x.

The Dalston and Edgware release trains build on Spring Boot 1.5.x, and are not expected to work with Spring Boot 2.0.x.

The Camden release train builds on Spring Boot 1.4.x, but is also tested with 1.5.x.

The Brixton release train builds on Spring Boot 1.3.x, but is also tested with 1.4.x.

The Angel release train builds on Spring Boot 1.2.x, and is incompatible in some areas with Spring Boot 1.3.x. Brixton builds on Spring Boot 1.3.x and is similarly incompatible with 1.2.x. Some libraries and most apps built on Angel will run fine on Brixton, but changes will be required anywhere that the OAuth2 features from spring-cloud-security 1.0.x are used (they were mostly moved to Spring Boot in 1.3.0).

Use your dependency management tools to control the version. If you are using Maven remember that the first version declared wins, so declare the BOMs in order, with the first one usually being the most recent (e.g. if you want to use Spring Boot 1.3.6 with Brixton.RELEASE, put the Boot BOM first). The same rule applies to Gradle if you use the Spring dependency management plugin.

NOTE: The release train contains a spring-cloud-dependencies as well as the spring-cloud-starter-parent. You can use the parent as you would the spring-boot-starter-parent (if you are using Maven). If you only need dependency management, the "dependencies" version is a BOM-only version of the same thing (it just contains dependency management and no plugin declarations or direct references to Spring or Spring Boot). If you are using the Spring Boot parent POM, then you can use the BOM from Spring Cloud. The opposite is not true: using the Cloud parent makes it impossible, or at least unreliable, to also use the Boot BOM to change the version of Spring Boot and its dependencies.