## 什么是 Spring Boot Admin?
Spring Boot Admin 是一个管理和监控 Spring Boot 应用程序的开源软件。每个应用都认为是一个客户端，通过 HTTP 或者使用 Eureka 注册到 admin server 中进行展示，Spring Boot Admin UI 部分使用 VueJs 将数据展示在前端。
###  依赖版本：
```
spring-boot 2.1.0
eureka-server 2.1.0
spring-boot-admin 2.1.6
```
### 项目结构
![f2c6be0de3d009b93e7d85ab9be0e91](http://520htt.com/upload/2020/04/f2c6be0de3d009b93e7d85ab9be0e91-5fd361ff3eb649929ffd46df61f69f2c.png)

### 建立父工程springbootadmin
主要依赖
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```
### 建立eureka-server（端口：8080） eureka-servertow端口：8081）实现高可用
主要依赖
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```
启动类中添加注释 `@EnableEurekaServer`
``` 
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```
配置Security
```
  @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //spring security csrf (跨域保护)关闭
            http.csrf().disable();
            super.configure(http);
        }
    }
```



配置application.yml
```
server:
  port: 8081
spring:
  application:
    name: eureka-server
  security:
    user:
      name: "admin"
      password: "admin"
eureka:
  instance:
    hostname: localhost
    health-check-url-path: /actuator/health
    prefer-ip-address: true #如果设置了eureka.instance.ip-address 属性，则使用该属性配置的IP
    metadata-map:
      startup: ${random.int}  #需要在重启后触发信息和端点更新
      #如果springbootadmin配置了security需要在这里添加用户名和密码
      user.name: ${spring.security.user.name}
      user.password: ${spring.security.user.password}
  client:
    service-url:
      startup: ${random.int}  #需要在重启后触发信息和端点更新
      #因为eureka配置了security需要在url上面带上用户名和密码
      defaultZone: http://admin:admin@${eureka.instance.hostname}:8080/eureka/,http://admin:admin@${eureka.instance.hostname}:8081/eureka/
    register-with-eureka: true # 是否拉取其它服务的信息，默认是true，如果是单个EurekaServer为false
    fetch-registry: true # 是否注册自己的信息到EurekaServer，默认是true，如果是单个EurekaServer为false
    server:
      enable-self-preservation: false # 关闭自我保护模式（缺省为打开）
      eviction-interval-timer-in-ms: 1000 # 扫描失效服务的间隔时间（缺省为60*1000ms）
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS

```
eureka-servertow的application.yml唯一区别在于一个端口
关于eureka以后会出相关文章

### 创建admin-server
主要依赖
```
     <!-- spring-boot-admin服务端 -->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.1.6</version>
        </dependency>
        <!-- spring-boot-adminUI -->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-server-ui</artifactId>
            <version>2.1.6</version>
        </dependency>

```
启动类中添加注释 `@EnableAdminServer` ` @EnableEurekaClient`
``` 
@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
public class AdminServerApplication{

    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
```

配置Security
```
public  class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String adminContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        http.authorizeRequests()
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .httpBasic().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                        adminContextPath + "/instances",
                        adminContextPath + "/actuator/**"
                );
        // @formatter:on
    }
}
```
配置application.yml
```
server:
  port: 9091

#设置security
spring:
  application:
    name: admin-server
  security:
    user:
      name: "admin"
      password: "admin"
#配置eureka
eureka:
  instance:
    leaseRenewalIntervalInSeconds: 10 #eurek心跳
    health-check-url-path: /actuator/health #健康检查页面的URL，相对路径，默认使用 HTTP 访问，如果需要使用 HTTPS则需要使用绝对路径配置
    prefer-ip-address: true #如果设置了eureka.instance.ip-address 属性，则使用该属性配置的IP
    metadata-map:
      startup: ${random.int}  #需要在重启后触发信息和端点更新
      user.name: ${spring.security.user.name}
      user.password: ${spring.security.user.password}
    hostname: localhost
  client:
    registryFetchIntervalSeconds: 5 #  从eureka服务器注册表中获取注册信息的时间间隔（s），默认为30秒
    serviceUrl:
      defaultZone: http://admin:admin@${eureka.instance.hostname}:8080/eureka/,http://admin:admin@${eureka.instance.hostname}:8081/eureka/
# 开放所有endpoint，实际生产根据自身需要开放，出于安全考虑不建议全部开放。
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS

```
### 创建admin-client
主要依赖
```
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-client</artifactId>
            <version>2.1.6</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

```
启动类中添加注释 `@EnableEurekaClient` ` `
``` 
@SpringBootApplication
@EnableEurekaClient
public class AdminClientApplication{

    public static void main(String[] args) {
        SpringApplication.run(AdminClientApplication.class, args);
    }
}

```
配置Security
```
    @Configuration
public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll()
                .and().csrf().disable();
    }
}
```
配置application.yml
```
server:
  port: 9002
#配置security
spring:
  application:
    name: admin-client
  security:
    user:
      name: "admin"
      password: "admin"

#配置eureka
eureka:
  instance:
    leaseRenewalIntervalInSeconds: 10 #eurek心跳
    health-check-url-path: /actuator/health #健康检查页面的URL，相对路径，默认使用 HTTP 访问，如果需要使用 HTTPS则需要使用绝对路径配置
    prefer-ip-address: true #如果设置了eureka.instance.ip-address 属性，则使用该属性配置的IP
    metadata-map:
      startup: ${random.int}  #需要在重启后触发信息和端点更新
  #如果eureka配置了security需要在这里添加用户名和密码
      user.name: ${spring.security.user.name}
      user.password: ${spring.security.user.password}
    hostname: localhost
  client:
    registryFetchIntervalSeconds: 5  #  从eureka服务器注册表中获取注册信息的时间间隔（s），默认为30秒
    serviceUrl:
      defaultZone: http://admin:admin@${eureka.instance.hostname}:8080/eureka/,http://admin:admin@${eureka.instance.hostname}:8081/eureka/
# 开放所有endpoint，实际生产根据自身需要开放，出于安全考虑不建议全部开放。
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS
```
#### 浏览器输入：  localhost:9091
![6b046e4844d6a0110f616ec2137b985](http://520htt.com/upload/2020/04/6b046e4844d6a0110f616ec2137b985-fb138d2c5abe481f9451326246c7536d.png)
输入用户名/密码
```
admin/admin
```
![977657a972612ece2b9180b1acd2e41](http://520htt.com/upload/2020/04/977657a972612ece2b9180b1acd2e41-f6daa56267c94a4fac48e3c376baca16.png)

![eebca0df6d1576ebd3cdfb7705f609a](http://520htt.com/upload/2020/04/eebca0df6d1576ebd3cdfb7705f609a-8a8fb2b717ba43afbf5959dc608ce2a1.png)





