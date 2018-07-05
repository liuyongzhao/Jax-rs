# reDemo
demo for Jax-rs with the Junit test
#开发需求
1、实现RS服务
2、生成SWAGGER接口文档
3、实现接口单元测试示例
4、对接口访问过程进行日志记录

#实现方案
## REST API实现方式
### Spring Boot框架实现方法
1、创建资源表示类 
创建java对象，字段为id、content，构造器java(long id , String  content),访问器getId()、getContent()
```java
public class Greeting {
	
	private final long id;
	private final String content;
	
	public Greeting(long id,String content){
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
```
2、创建资源控制器 
@RestController注释识别组件 
@RequestMapping(value="\"，method=)映射到请求的URL地址 ，method默认为get
@RequestParam绑定查询字符串参数
```java
@RestController
public class GreetingController {
	
	private static final String template = "Hi,%s";
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name",defaultValue="World")String name){
		
		return new Greeting(String.format(template, name));
	}
}
```
3、创建程序的执行文件Application
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
4、maven生成可执行的jar包并运行
生成jar包
```java
mvn clean package
```
运行jar包
```java
java -jar target/demo-0.1.0.jar
```
####依赖设置
在pom.xml中增加 
```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
	<dependency>
            <groupId>com.jayway.jsonpath</groupId>
            <artifactId>json-path</artifactId>
            <scope>test</scope>
        </dependency>
</dependencies>
```
### 文件夹规划 
```txt    
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─example
│  │  │          └─demo
│  │  │                  Greeting.java
│  │  │                  GreetingController.java
│  │  │                  RsDemoApplication.java
│  │  │                  
│  │  └─resources
│  │          application.properties
│  │          
│  └─test
│      └─java
│          └─com
│              └─example
│                  └─demo
│                          RsDemoApplicationTests.java
└─pom.xml          
```           
###单元测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class RsDemoApplicationTests {	
    @Autowired  
    private WebApplicationContext wac;  
    private MockMvc mockMvc;  

    @Before  
    public void setUp() {  
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  
    } 
	@Test
	public void responseWithoutName() throws Exception {
        String secondResult = mockMvc.perform(MockMvcRequestBuilders.get("/greeting"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))  
                .andReturn().getResponse().getContentAsString();
        System.out.println("----------不指定姓名查询----------\n" + secondResult);
	}
```

###自动生成文档swagger工具
####填加依赖
```xml
<dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.2.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.2.2</version>
</dependency>
```
####swagger的配置configuration
```java
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("LYZ项目接口文档")
                .description("LYZ项目的接口文档，符合RESTful API。")
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo")) //以扫描包的方式
                .paths(PathSelectors.any())
                .build();
    }
}
```
####访问方法
启动Spring Boot程序，访问：http://localhost:8080/swagger-ui.html
