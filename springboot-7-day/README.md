# Spring Boot 7 天速成

最终目标：使用 Spring Boot 3、JDK 17、Maven、MyBatis-Plus 和 MySQL 实现带事务的 REST CRUD。

## 版本

- Spring Boot 3.5.16
- JDK 17
- Maven 3.6.3+
- MyBatis-Plus 3.5.17
- MySQL 8.4

## 7 天交付计划

| 天数 | 可交付结果 | 必须掌握 | 了解即可 |
| --- | --- | --- | --- |
| Day 1 | `/api/hello` 返回 JSON | 启动类、Controller、路由、Starter | 自动配置源码 |
| Day 2 | Controller 调用 Service | IoC、Bean、构造器注入、分层 | 完整 Bean 生命周期 |
| Day 3 | POST JSON 与参数校验 | `@RequestBody`、`@Valid`、Jackson | 自定义校验器 |
| Day 4 | 外部化配置与统一错误 JSON | YAML、环境变量、`@RestControllerAdvice` | 配置加载源码 |
| Day 5 | 内存 CRUD | REST 语义、DTO、状态码、幂等性 | HATEOAS |
| Day 6 | Controller 自动测试 | JUnit、MockMvc、切片测试 | Testcontainers |
| Day 7 | MySQL 事务 CRUD | DataSource、BaseMapper、`@Transactional` | MyBatis-Plus 高级插件 |

当前 `main` 保存 Day 7 的最终可运行状态。学习时建议每天从空白文件重写当天涉及的类，而不是只阅读成品。

## 项目结构

```text
src/main/java/com/example/bootcamp/
├─ BootcampApplication.java
├─ config/
│  └─ AppProperties.java
├─ db/
│  ├─ DbUser.java
│  ├─ DbUserController.java
│  ├─ DbUserMapper.java
│  ├─ DbUserNotFoundException.java
│  └─ DbUserService.java
└─ web/
   ├─ ApiExceptionHandler.java
   ├─ AppInfoController.java
   └─ HelloController.java
```

## 启动

确认环境：

```powershell
java -version
mvn -version
docker version
```

启动 MySQL：

```powershell
docker compose up -d
docker compose ps
```

运行测试和应用：

```powershell
mvn clean test
mvn spring-boot:run
```

## 接口

| Method | URL | 用途 |
| --- | --- | --- |
| GET | `/api/hello` | 最小 REST 接口 |
| GET | `/api/info` | 验证配置绑定 |
| GET | `/api/db-users` | 查询全部用户 |
| GET | `/api/db-users/{id}` | 按 ID 查询 |
| POST | `/api/db-users` | 创建用户 |
| PUT | `/api/db-users/{id}` | 更新用户 |
| DELETE | `/api/db-users/{id}` | 删除用户 |
| POST | `/api/db-users/batch` | 事务批量创建 |

## CRUD 验证

创建用户：

```powershell
$body = @{ name = 'Ada'; email = 'ada@example.com' } | ConvertTo-Json
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/db-users -ContentType 'application/json' -Body $body
```

查询：

```powershell
Invoke-RestMethod http://localhost:8080/api/db-users
Invoke-RestMethod http://localhost:8080/api/db-users/1
```

更新：

```powershell
$body = @{ name = 'Ada Lovelace'; email = 'ada@example.com' } | ConvertTo-Json
Invoke-RestMethod -Method Put -Uri http://localhost:8080/api/db-users/1 -ContentType 'application/json' -Body $body
```

删除：

```powershell
Invoke-RestMethod -Method Delete -Uri http://localhost:8080/api/db-users/1
```

## 事务回滚验证

先创建 `ada@example.com`，再运行：

```powershell
$batch = @{
    users = @(
        @{ name = 'Bob'; email = 'bob@example.com' },
        @{ name = 'Duplicate Ada'; email = 'ada@example.com' }
    )
} | ConvertTo-Json -Depth 4

Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/db-users/batch -ContentType 'application/json' -Body $batch
```

预期返回 HTTP 409。再次查询用户列表时不应出现 Bob，说明批量事务已经整体回滚。

## 面试验收

完成后应能脱离资料回答：

1. IoC、DI 和 Bean 分别是什么？
2. 为什么优先使用构造器注入？
3. Controller、Service、Mapper 如何分工？
4. `@RequestBody`、`@PathVariable`、`@RequestParam` 有什么区别？
5. POST、PUT、GET、DELETE 的语义和幂等性是什么？
6. MyBatis-Plus `BaseMapper` 提供哪些 CRUD 方法？
7. `@Transactional` 默认遇到什么异常回滚？
8. 为什么同类中的 `this.method()` 可能使事务失效？
9. 为什么邮箱既要业务校验，也要数据库唯一索引？
10. `@WebMvcTest` 和 `@SpringBootTest` 有什么区别？

## 清理本地数据库

停止容器但保留数据：

```powershell
docker compose down
```

只有在确定不需要本地练习数据时，才删除 volume：

```powershell
docker compose down -v
```
