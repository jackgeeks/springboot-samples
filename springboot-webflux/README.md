我们在来看一下下图，可以看到，在目前的Spring WebFlux还没有支持类似
Mysql这样的关系型数据库，本文以MongoDb数据库为例
![144cdcde9a18385f23a95b0140bc9f5](http://520htt.com/upload/2020/05/144cdcde9a18385f23a95b0140bc9f5-0cc9552cf7d54e3ca89924df6c60b9ae.png)
pom
```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
```
整合mongodb

* 配置文件
```
spring.data.mongodb.uri=mongodb://localhost:27017/webflux
```
* 启动类添加注解`@EnableReactiveMongoRepositories`
```
@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringbootWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebfluxApplication.class, args);
    }

}

```
#实现ReactiveMongoRepositor
```
public interface UserDao extends ReactiveMongoRepository<User,String> {

}

```
###  基于注释的@Controller和MVC还支持其他注释
编写 UserController 
```
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/")
    public Flux<User> GetAll() {
        return userDao.findAll();
    }

    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> StreamGetAll() {
        return userDao.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> GetId(  @PathVariable String id) {
        return userDao.findById(id).map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PostMapping(value = "/")
    public Mono<User> createUser(@Valid@RequestBody User user) {
        user.setId(null);
        user.setCreatedDate(LocalDateTime.now());
        return userDao.save(user);
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable String id, @Valid@RequestBody User user) {
        return userDao.findById(id).flatMap(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    u.setCreatedDate(LocalDateTime.now());
                    return userDao.save(u);
                }
        ).map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> DeleteUser(@PathVariable String id) {
        return userDao.findById(id)
                .flatMap(u -> userDao.deleteById(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));


    }
}

```
### 功能性Java 8 Lambda样式的路由和处理

## 编写Handle
```
@Component
public class UserHandle {
    @Autowired
    private UserDao userDao;

    public Mono<ServerResponse> GetAll (ServerRequest request){
      return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
              .body(userDao.findAll(), User.class);
    }
    public Mono<ServerResponse> StreamGetAll (ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .body(userDao.findAll(), User.class);
    }


    public Mono<ServerResponse> createUser (ServerRequest request){
        Mono<User> userMono = request.bodyToMono(User.class);
        return userMono.flatMap( u->{
            u.setCreatedDate(LocalDateTime.now());
             return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userDao.saveAll(userMono), User.class);});
    }
    public Mono<ServerResponse> updateUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        String id = request.pathVariable("id");

        return  userMono.flatMap(updateUser -> {


            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                   .body(userDao.findById(id).flatMap(oldUser -> {
                       // 将新的用户数据覆盖旧的用户数据
                       BeanUtils.copyProperties(updateUser, oldUser);
                       oldUser.setId(id);
                       oldUser.setCreatedDate(LocalDateTime.now());
                       return userDao.save(oldUser);
                   }), User.class);
        });


    }



    public Mono<ServerResponse> deleteUserById (ServerRequest request) {
        String id = request.pathVariable("id");
        return userDao.findById(id)
                .flatMap(u -> userDao.delete(u)
                        .then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }



}

```
配置Routers路由信息
```
@Configuration
public class UserRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandle handle){

        return RouterFunctions.nest(
                //相当于UserController里的 @RequestMapping("/user")
                RequestPredicates.path("/userhandle"),
                //相当于UserController里的  @GetMapping("/")
                RouterFunctions.route(RequestPredicates.GET("/")
                        ,handle::GetAll)
                        //相当于UserController里的  @GetMapping("/stream/all")                     
                .andRoute(RequestPredicates.GET("/stream/all")
                        ,handle::StreamGetAll)
                   //相当于UserController里的  @PostMapping("/")
                .andRoute(RequestPredicates.POST("/")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handle::createUser)
                        //相当于UserController里的  @PutMapping("/{id}")       
               .andRoute(RequestPredicates.PUT("/{id}")
                    .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handle::updateUser)
                        //相当于UserController里的  @DeleteMapping("/{id}") 
                .andRoute(RequestPredicates.DELETE("/{id}"),handle::deleteUserById)

        );

    }
}
```

## 获取源码公众号回复: WebFlux
![qrcode_for_gh_c8260435c2d7_258](http://520htt.com/upload/2020/05/qrcode_for_gh_c8260435c2d7_258-4703192829404b63b48d02e6e2cf9e1c.jpg)




