# Web API 说明

> 本项目使用的 RESTful Web API 说明文档。 组员具体贡献表格见文档末尾。

本项目Web API分两大模块——用户管理和商品管理，分别提供用户管理相关接口和商品管理相关接口。本项目使用Spring Boot + Swagger2 实现了14个REST风格的Web API，并利用swagger-ui生成接口文档，使用swagger-ui界面上的按钮直接调用接口方法，以测试API. 

![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui.png)

之所以选用Spring Boot 和 Swagger：在软件开发过程中，Spring Boot 拥有能够快速开发、便捷部署等特性。Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。Swagger2 可以轻松的整合到Spring Boot中，并与Spring MVC程序配合组织出强大RESTful API文档，也提供了强大的页面测试功能来调试每个RESTful API. 我们还使用了一些Swagger提供的注解，以提高生成文档的可读性。

Spring Boot 项目中添加 Swagger 依赖：

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
	<version>2.7.0</version>
</dependency>

<dependency>
	<groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.7.0</version>
</dependency>
```

提供接口的 SpringBoot 项目使用 MVC 三层架构，整个业务应用划分为: 表现层(UI)、业务逻辑层(BLL)、数据访问层(DAL) ，分别对应项目中的`Controller`类、`Service类`和`Mapper`类。其中，`Mapper`使用xml文件实现；`Service`使用手动编写的实现类，调用`Mapper`的方法完成相应的功能，`Service`实现类使用 `@Service` 注解自动注册为组件，`Mapper`字段使用 `@Autowired` 注解自动注入；Controller直接使用实体类，并调用Service层的方法，同样，使用 `@Service` 注解实现Service字段的自动注入。

我们创建了一个配置类，以对Swagger生成的API文档进行配置：

```java
@Configuration
@EnableSwagger2
public class swaggerconfig {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.whu"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("校园二手商品交易平台 FleaMarket 后台 RESTful API")
                .description("rest api 文档构建利器")
                .contact("李光华，葛佳硕，刘青沐")
                .version("2.0")
                .build();
    }
```

注解`@Configuration`告诉程序，这是一个Swagger配置文件。 springfox提供了一个Docket ( 摘要 ) 类，我们使用注解`@Bean`将`createRestApi`方法返回的Docket对象注入到spring中，方法体中对该对象的属性进行了设置。

`createRestApi`中用到的`ApiInfo`类对象则通过`apiInfo`方法生成，同样，方法体中对其属性进行了设置。

## UserController

### 1. 查询全部

* 功能：从数据库获取所有商品列表

* 实现原理: 

  `ItemController`类中，定义`findAllItem`方法：

  ```java
  @ApiOperation(value = "获取商品列表", notes = "获取所有商品信息")
  @RequestMapping(value = {"/"}, method = RequestMethod.GET)
  public Object findAllItem() {
    List<Item> items =itemService.findAll();
    return items;
  }	
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Get方法

  此方法调用了`Service`层的 `findAll`方法。如前所述，`findAll`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public List<Item> findAll() {
      return itemMapper.findAll();
  }
  ```

  而`Mapper`层的`findAll`方法通过xml文件实现：

  ```xml
  <select id="findAll" resultMap="BaseResultMap">
  	select * from items
  </select>
  ```

  其中，`BaseResultMap`将items表格的列与`Item`的属性建立映射关系

  ```
  <resultMap id="BaseResultMap" type="com.whu.fleamarket.entity.Item" >
      <id column="id" jdbcType="INTEGER" property="id"/>
      <result column="pic_url" jdbcType="VARCHAR" property="picUrl"/>
      <result column="item_name" jdbcType="VARCHAR" property="itemName"/>
      <result column="price" jdbcType="VARCHAR" property="price"/>
      <result column="on_shelf" jdbcType="TINYINT" property="onShelf"/>
      <result column="owner_id" jdbcType="INTEGER" property="ownerId"/>
      <result column="category" jdbcType="VARCHAR" property="category"/>
  </resultMap>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_findAllUser.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_findAllUser_result.png)

  可以看到，查询结果以json格式返回。

### 2. 添加商品

* 功能：向数据库添加一条商品信息

* 实现原理: 

  `ItemController`类中，定义`postItem`方法：

  ```java
  @ApiOperation(value = "添加商品", notes = "根据Item对象向数据库中添加商品信息")
  //    @ApiImplicitParam(name = "item", value = "商品详细实体item", required = true, dataType = "Item")
      @RequestMapping(value = "/", method = RequestMethod.POST)
      public String postItem(Item item) {
  
          itemService.add(item);
  
          return "success" ;
      }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Post方法

  此方法调用了`Service`层的 `add`方法。如前所述，`Service`层的 `add`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public Boolean add(Item item) {
      return itemMapper.add(item) > 0;
  }
  ```

  而`Mapper`层的add方法通过xml文件中的条目实现：

  ```xml
  <insert id="add" parameterType="com.whu.fleamarket.entity.Item">
      insert into items
      <trim prefix="(" suffix=")" suffixOverrides=",">
          <if test="id != null">
              id,
          </if>
          <if test="picUrl != null">
              pic_url,
          </if>
          <if test="itemName != null">
              item_name,
          </if>
          <if test="price != null">
              price,
          </if>
          <if test="onShelf != null">
              on_shelf,
          </if>
          <if test="ownerId != null">
              owner_id,
          </if>
          <if test="category != null">
              category,
          </if>
      </trim>
      <trim prefix=" values (" suffix=")" suffixOverrides=",">
          <if test="id != null">
              #{id},
          </if>
          <if test="picUrl != null">
              #{picUrl},
          </if>
          <if test="itemName != null">
              #{itemName},
          </if>
          <if test="price != null">
              #{price},
          </if>
          <if test="onShelf != null">
              #{onShelf},
          </if>
          <if test="ownerId != null">
              #{ownerId},
          </if>
          <if test="category != null">
              #{category},
          </if>
      </trim>
  </insert>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_postItem.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_postItem_result_1.png)

  这里显示

  Response Code

  ```
  0
  ```

  Response Headers

  ```
  {
    "error": "no response from server"
  }
  ```

  是由于我们的`Controller`方法中使用了，`return “success";`，而`”success“`字符串无法被解析为json格式；此时，数据库中的内容已经成功更新：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_postItem_result_2.png)

### 3. 查找商品

* 功能：根据id从数据库查找对应商品信息

* 实现原理: 

  `ItemController`类中，定义`getItem`方法：

  ```java
  @ApiOperation(value="查找商品", notes="根据商品id获取商品详细信息")
  @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "String" , paramType ="path")
  @RequestMapping(value="/{id}", method=RequestMethod.GET)
  public Item getItem(@PathVariable String id) {
  
      return itemService.queryById(Integer.parseInt(id));
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@ApiImplicitParam`  注解用来给API的参数 `id` 增加文字说明，包括参数名：id， 参数描述：商品id；`required = true`将此参数设置为必需，dataType设置参数类型为`String` ，`paramType ="path`指明这是一个路径参数，即参数值从url路径中的某段对应，这里id参数对应路径中的 {id}

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Get方法

  `@PathVariable`指明后面的参数 `String id` 来自url路径，这里即指 {id}

  此方法调用了`Service`层的 `queryById`方法。如前所述，`Service`层的`queryById`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public Item queryById(Integer id) {
      return itemMapper.queryById(id);
  }
  ```

  `Mapper`层的`queryById`方法通过xml文件中的一段实现：

  ```xml
  <select id="queryById" resultMap="BaseResultMap" parameterType="java.lang.Integer">
      select * from items where id = #{id}
  </select>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryItemById.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryItemById_result.png)

  可以看到，查询结果成功以json格式返回。

### 4. 更新商品

* 功能：根据传入的id和Item类对象，更新数据库中对应id的商品信息

* 实现原理: 

  `ItemController`类中，定义`putItem`方法：

  ```java
  @ApiOperation(value="更新商品", notes="根据url指定的id和传来的user对象，更新数据库中相应的商品信息")
  @RequestMapping(value="/{id}", method=RequestMethod.PUT)
  public String putItem(@ApiParam(value = "商品id", required = true) @PathVariable String id, @RequestBody Item item) {
      item.setId(Integer.parseInt(id));
      itemService.update(item);
      return "success";
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Put方法

  `@ApiParam`  注解直接放在参数`String id`前，用来给API的参数 `id` 增加文字说明，其中，`required = true`将此参数设置为必需

  `@PathVariable`指明后面的参数 `String id` 来自url路径，这里即指 {id}

  `@RequestBody` 用来接收前端传来的json字符串中的数据

  此方法调用了`Service`层的 `update`方法。如前所述，`Service`层的 `update`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public Boolean update(Item item) {
      return itemMapper.update(item) > 0;
  }
  ```

  `Mapper`层的`update`方法通过xml文件中的一段实现：

  ```xml
  <update id="update" parameterType="com.whu.fleamarket.entity.Item">
      update items
  	<set>
          <if test="picUrl != null">
              pic_url = #{picUrl},
          </if>
          <if test="itemName != null">
              item_name = #{itemName},
          </if>
          <if test="price != null">
              price = #{price},
          </if>
          <if test="onShelf != null">
              on_shelf = #{onShelf},
          </if>
          <if test="ownerId != null">
              owner_id = #{ownerId},
          </if>
          <if test="category != null">
              category = #{category},
          </if>
     </set>
     where id = #{id}
  </update>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_updateItem.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_updateItem_result_1.png)

  这里显示

  Response Code

  ```
  0
  ```

  Response Headers

  ```
  {
    "error": "no response from server"
  }
  ```

  是由于我们的`Controller`方法中使用了，`return “success";`，而`”success“`字符串无法被解析为json格式；此时，数据库中的内容已经成功更新：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_updateItem_result_2.png)

### 5.删除商品

* 功能：根据id从数据库删除对应商品信息

* 实现原理: 

  `ItemController`类中，定义`deleteItem`方法：

  ```java
  @ApiOperation(value="删除商品信息", notes="根据url中给定的id，删除数据库中相应的商品信息")
  @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "String", paramType = "path")
  @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
  public String deleteItem(@PathVariable String id) {
      itemService.deleteById(Integer.parseInt(id));
      return "success";
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@ApiImplicitParam`  注解用来给API的参数 `id` 增加文字说明，包括参数名：id， 参数描述：商品id；`required = true`将此参数设置为必需，`dataType = "String"`告知swagger此参数类型为`String` ，`paramType ="path`指明这是一个路径参数，即参数值从url路径中的某段对应，这里id参数对应路径中的 {id}

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Delete方法

  `@PathVariable`指明后面的参数 `String id` 来自url路径，这里即指 {id}

  此方法调用了`Service`层的 `deleteById`方法。如前所述，`Service`层的`deleteById`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public Boolean deleteById(Integer id) {
      return itemMapper.deleteById(id) > 0;
  }
  ```

  `Mapper`层的`queryById`方法通过xml文件中的一段实现：

  ```xml
  <delete id="deleteById" parameterType="java.lang.Integer">
      delete from items
      where id = #{id}
  </delete>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_deleteItem.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_deleteItem_result_1.png)

  这里显示

  Response Code

  ```
  0
  ```

  Response Headers

  ```
  {
    "error": "no response from server"
  }
  ```

  是由于我们的`Controller`方法中使用了，`return “success";`，而`”success“`字符串无法被解析为json格式；此时，数据库中的相应条目已经被删除：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_deleteItem_result_2.png)

### 6. 查找所有在架上的商品

* 功能：查找当前所有在架上的商品

* 实现原理: 

  `ItemController`类中，定义`findAllOnShelfItem`方法：

  ```java
  @ApiOperation(value = "获取在架上的商品列表", notes = "获取所有在架上的商品的信息")
  @RequestMapping(value = {"/ofOnShelf"}, method = RequestMethod.GET)
  public Object findAllOnShelfItem() {
  
      List<Item> items =itemService.findAllOnShelf();
  
      return items;
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Get方法

  此方法调用了`Service`层的 `findAllOnShelf`方法。如前所述，`Service`层的`findAllOnShelf`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public List<Item> findAllOnShelf() {
      return itemMapper.findAllOnShelf();
  }
  ```

  `Mapper`层的`findAllOnShelf`方法通过xml文件中的一段实现：

  ```xml
  <select id="findAllOnShelf" resultMap="BaseResultMap">
      select * from items where on_shelf = 1
  </select>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_findAllOnShelfItem.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_findAllOnShelfItem_result_1.png)

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_findAllOnShelfItem_result_2.png)

  可以看到，查询结果成功以json格式返回。

### 7. 根据商品名查找商品

* 功能：根据商品名，从数据库中查找出所有名称匹配的商品信息

* 实现原理: 

  `ItemController`类中，定义`getItemByItemName`方法：

  ```java
  @ApiOperation(value="使用商品名查找商品", notes="根据商品名查询商品详细信息，返回所有名称相符的商品")
  @RequestMapping(value="/ofItemName/{itemName}", method=RequestMethod.GET)
  public List<Item> getItemByItemName(@ApiParam(value = "商品名", required = true) @PathVariable String itemName) {
  
      return itemService.queryByItemName(itemName);
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Get方法

  `@ApiParam`  注解直接放在参数`String id`前，用来给API的参数 `id` 增加文字说明，其中，`required = true`将此参数设置为必需

  `@PathVariable`指明后面的参数 `String id` 来自url路径，这里即指 {id}

  此方法调用了`Service`层的 `queryByItemName`方法。如前所述，`Service`层的`queryByItemName`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public List<Item> queryByItemName(String itemName) {
      return itemMapper.queryByItemName(itemName);
  }
  ```

  `Mapper`层的`queryByItemName`方法通过xml文件中的一段实现：

  ```xml
  <select id="queryByItemName" resultMap="BaseResultMap" >
      select * from items where item_name = #{itemName}
  </select>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryByItemName.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryByItemName_result.png)

  可以看到，查询结果成功以json格式返回。

### 8. 根据物主id查找商品

* 功能：从数据库中查询某物主拥有的全部商品的信息

* 实现原理: 

  `ItemController`类中，定义`getItemByOwnerId`方法：

  ```java
  @ApiOperation(value="使用物主id查找商品", notes="根据物主id，获取该用户拥有的所有商品")
  @ApiImplicitParam(name = "ownerId", value = "物主id", required = true, dataType = "Integer", paramType = "path")
  @RequestMapping(value="/ofOwnerId/{ownerId}", method=RequestMethod.GET)
  public List<Item> getItemByOwnerId( @PathVariable String ownerId) {
  
      return itemService.queryByOwnerId(Integer.parseInt(ownerId));
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@ApiImplicitParam`  注解用来给API的参数 `ownerId` 增加文字说明，包括参数名：ownerId， 参数描述：物主id；`required = true`将此参数设置为必需，`dataType = "String"`告知swagger此参数类型为`String` ，`paramType ="path`指明这是一个路径参数，即参数值从url路径中的某段对应，这里id参数对应路径中的 {ownerId}

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Get方法

  `@PathVariable`指明后面的参数 `String ownerId` 来自url路径，这里即指 {ownerId}

  此方法调用了`Service`层的 `queryByOwnerId`方法。如前所述，`Service`层的`queryByOwnerId`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public List<Item> queryByItemName(String itemName) {
      return itemMapper.queryByItemName(itemName);
  }
  ```

  `Mapper`层的`queryByOwnerId`方法通过xml文件中的一段实现：

  ```xml
  <select id="queryByItemName" resultMap="BaseResultMap" >
      select * from items where item_name = #{itemName}
  </select>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryByOwnerId.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryByOwnerId_result.png)

  可以看到，查询结果成功以json格式返回。

## UserController

### 1. 查询全部

* 功能：从数据库获取所有用户的列表

* 实现原理: 

  `UserController`类中，定义`findAllItem`方法：

  ```java
  @ApiOperation(value = "获取用户列表", notes = "获取所有用户信息")
  @RequestMapping(value = {"/"}, method = RequestMethod.GET)
  public List<User> findAllUser() {
  
      List<User> users =userService.findAll();
  
      return users;
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Get方法

  此方法调用了`Service`层的 `findAll`方法。如前所述，`findAll`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public List<User> findAll() {
      return userMapper.findAll();
  }
  ```

  而`Mapper`层的`findAll`方法通过xml文件实现：

  ```xml
  <select id="findAll" resultType="com.whu.fleamarket.entity.User">
      select * from users
  </select>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_findAllUser.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_findAllUser_result.png)

  可以看到，查询结果成功以json格式返回。

### 2. 添加用户

* 功能：向数据库添加一条用户信息

* 实现原理: 

  `ItemController`类中，定义`createUser`方法：

  ```java
  @ApiOperation(value = "创建用户", notes = "根据User对象创建新用户")
  //    @ApiImplicitParam(name = "user", value = "包含用户信息的User对象", required = true, dataType = "User")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createUser(User user) {
  
      userService.add(user);
  
      return "success" ;
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Post方法

  此方法调用了`Service`层的 `add`方法。如前所述，`Service`层的 `add`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public Boolean add(User user) {
      return userMapper.add(user) > 0;
  }
  ```

  而`Mapper`层的add方法通过xml文件中的条目实现：

  ```xml
  <insert id="add" parameterType="com.whu.fleamarket.entity.User">
      insert into users
      <trim prefix="(" suffix=")" suffixOverrides=",">
          <if test="id != null">
              id,
          </if>
          <if test="username != null">
              username,
          </if>
          <if test="password != null">
              password,
          </if>
          <if test="address != null">
              address,
          </if>
  
      </trim>
      <trim prefix=" values (" suffix=")" suffixOverrides=",">
          <if test="id != null">
              #{id},
          </if>
          <if test="username != null">
              #{username},
          </if>
          <if test="password != null">
              #{password},
          </if>
          <if test="address != null">
              #{address},
          </if>
      </trim>
  </insert>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_createUser.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_createUser_result_1.png)

  这里显示

  Response Code

  ```
  0
  ```

  Response Headers

  ```
  {
    "error": "no response from server"
  }
  ```

  是由于我们的`Controller`方法中使用了，`return “success";`，而`”success“`字符串无法被解析为json格式；此时，数据库表格中已经加入了新条目：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_createUser_result_2.png)

### 3. 查找用户

* 功能：根据用户id从数据库查找对应的一条用户信息

* 实现原理: 

  `UserController`类中，定义`getUser`方法：

  ```java
  @ApiOperation(value="查找用户", notes="根据用户id获取用户详细信息")
  @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer", paramType = "path")
  @RequestMapping(value="/{id}", method=RequestMethod.GET)
  public User getUser(@PathVariable String id) {
  
      return userService.queryById(Integer.parseInt(id));
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@ApiImplicitParam`  注解用来给API的参数 `id` 增加文字说明，包括参数名：id， 参数描述：用户id；`required = true`将此参数设置为必需，dataType设置参数类型为`String` ，`paramType ="path`指明这是一个路径参数，即参数值从url路径中的某段对应，这里id参数对应路径中的 {id}

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Get方法

  `@PathVariable`指明后面的参数 `String id` 来自url路径，这里即指 {id}

  此方法调用了`Service`层的 `queryById`方法。如前所述，`Service`层的`queryById`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public User queryById(Integer id) {
      return userMapper.queryById(id);
  }
  ```

  `Mapper`层的`queryById`方法通过xml文件中的一段实现：

  ```xml
  <select id="queryById" resultType="com.whu.fleamarket.entity.User" parameterType="java.lang.Integer">
      select * from users where id = #{id}
  </select>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryByUserId.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryByUserId_result.png)

  可以看到，查询结果成功以json格式返回。

### 4. 更新一条用户信息

* 功能：根据传入的id和User类对象，更新数据库中对应id的用户信息

* 实现原理: 

  `UserController`类中，定义`putUser`方法：

  ```java
  @ApiOperation(value="更新用户", notes="根据url指定的id和传来的user对象，更新相应的用户信息")
  @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
  @RequestMapping(value="/{id}", method=RequestMethod.PUT)
  public String putUser(@PathVariable String id, @RequestBody User user) {
      user.setId(Integer.parseInt(id));
      userService.update(user);
      return "success";
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@ApiImplicitParam`  注解用来给API的参数 `id` 增加文字说明，包括参数名：id， 参数描述：用户ID；`required = true`将此参数设置为必需，dataType设置参数类型为`String` ，`paramType ="path`指明这是一个路径参数，即参数值从url路径中的某段对应，这里id参数对应路径中的 {id}

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Put方法

  `@PathVariable`指明后面的参数 `String id` 来自url路径，这里即指 {id}

  `@RequestBody` 用来接收前端传来的json字符串中的数据

  此方法调用了`Service`层的 `update`方法。如前所述，`Service`层的 `update`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public Boolean update(User user) {
      return userMapper.update(user) > 0;
  }
  ```

  `Mapper`层的`update`方法通过xml文件中的一段实现：

  ```xml
  <update id="update" parameterType="com.whu.fleamarket.entity.User">
      update users
      <set>
          <if test="id != null">
              id = #{id},
          </if>
          <if test="username != null">
              username = #{username},
          </if>
          <if test="password != null">
              password = #{password},
          </if>
          <if test="address != null">
              address = #{address},
          </if>
      </set>
      where id = #{id}
  </update>
  ```

* 调用效果

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_updateUser_1.png)

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_updateUser_2.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_updateUser_result_1.png)

  这里显示

  Response Code

  ```
  0
  ```

  Response Headers

  ```
  {
    "error": "no response from server"
  }
  ```

  是由于我们的`Controller`方法中使用了，`return “success";`，而`”success“`字符串无法被解析为json格式；此时，数据库中的内容已经成功更新：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_updateUser_result_2.png)

### 5. 删除用户

* 功能：根据id从数据库删除相应用户的信息

* 实现原理: 

  `UserController`类中，定义`deleteUser`方法：

  ```java
  @ApiOperation(value="删除用户", notes="根据url中给定的id，删除相应用户信息")
  @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
  @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
  public String deleteUser(@PathVariable String id) {
      userService.deleteById(Integer.parseInt(id));
      return "success";
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@ApiImplicitParam`  注解用来给API的参数 `id` 增加文字说明，包括参数名：id， 参数描述：用户ID；`required = true`将此参数设置为必需，`dataType = "String"`告知swagger此参数类型为`String` ，`paramType ="path`指明这是一个路径参数，即参数值从url路径中的某段对应，这里id参数对应路径中的 {id}

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Delete方法

  `@PathVariable`指明后面的参数 `String id` 来自url路径，这里即指 {id}

  此方法调用了`Service`层的 `deleteById`方法。如前所述，`Service`层的`deleteById`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public Boolean deleteById(Integer id) {
      return userMapper.deleteById(id) > 0;
  }
  ```

  `Mapper`层的`queryById`方法通过xml文件中的一段实现：

  ```xml
  <delete id="deleteById" parameterType="java.lang.Integer">
      delete from users
      where id = #{id}
  </delete>
  ```

* 调用效果：直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_deleteUser_1.png)

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_deleteUser_2.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_deleteUser_result_1.png)

  这里显示

  Response Code

  ```
  0
  ```

  Response Headers

  ```
  {
    "error": "no response from server"
  }
  ```

  是由于我们的`Controller`方法中使用了，`return “success";`，而`”success“`字符串无法被解析为json格式；此时，数据库表格中的相应条目已经被删除：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_deleteUser_result_2.png)

### 6. 根据用户名查找用户

* 功能：根据用户名，从数据库中查找出用户名匹配的一条用户信息

* 实现原理: 

  `UserController`类中，定义`getUserByUsername`方法：

  ```java
  @ApiOperation(value="使用用户名查找用户", notes="根据用户名获取用户详细信息")
  @RequestMapping(value="/ofUsername/{username}", method=RequestMethod.GET)
  public User getUserByUsername(@ApiParam(value = "用户名", required = true) @PathVariable String username) {
  
      return userService.queryByUsername(username);
  }
  ```

  `@ApiOperation`  注解用来给API增加文字说明

  `@RequestMapping`注解则把此方法映射到一个url地址，并指明这是一个Get方法

  `@ApiParam`  注解直接放在参数`String username`前，用来给API的参数 `username` 增加文字说明，其中，`required = true`将此参数设置为必需

  `@PathVariable`指明后面的参数 `String username` 来自url路径，这里即指 {username}

  此方法调用了`Service`层的 `queryByUsername`方法。如前所述，`Service`层的`queryByUsername`方法会进一步调用`Mapper`层的相应方法：

  ```java
  @Override
  public User queryByUsername(String username) {
      return userMapper.queryByUsername(username);
  }
  ```

  `Mapper`层的`queryByUsername`方法通过xml文件中的一段实现：

  ```xml
  <select id="queryByUsername" resultType="com.whu.fleamarket.entity.User" parameterType="java.lang.String">
      select * from users where username = #{username}
  </select>
  ```

* 调用效果：

  直接在Swagger生成的API文档界面使用 "Try it out!" 按钮调用接口：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryByUsername.png)

  查询结果：

  ![image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryByUsername_result.png)

  可以看到，查询结果成功以json格式返回。



#### 分工：

| 姓名   | 贡献分 |                             贡献                             |
| ------ | :----: | :----------------------------------------------------------: |
| 李光华 |   23   | ①实验一中，类图的设计与绘制<br>类图和用例图的简要说明<br>②实验二中，简单工厂模式、组合模式的设计以及后台实现<br>设计模式文档中“组合模式”部分的书写<br>③ 实验三中，完整实现11个接口，<br>将项目改为MVC三层架构<br>修改数据库表格的列名和类的属性名方法名使其符合一般命名规范<br>将说明文档转为使用markdown格式书写，<br>加入文档开头，加入对注解的详细介绍 |
| 葛佳硕 |   20   | ①实验一中，用例图的设计与绘制，<br>用例图，时序图的详细说明<br/>②实验二中，简单工厂模式、观察者模式的设计以及后台实现，<br/>商品上架界面窗口的设计及实现，<br>设计模式文档的补充完善，<br>③实验三中，数据库设计，完整实现`findAllUser`接口和`createUser`接口，<br>使用swagger-ui界面测试接口 |
| 刘青沐 |   17   | ① 实验一中，时序图的设计与绘制<br>②实验二中，设计模式文档的书写<br>③ 实验三中，`deleteUser`接口的实现，说明文档框架的书写，<br>说明文档中插入相关代码 |

