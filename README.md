# Java Backend Learning

面向国内 Java 后端开发工程师校招的 6 周学习仓库。

背景：计算机科学本科，熟悉 Java / Python / JavaScript 和 AWS Serverless；需要补齐 Spring、数据库、缓存、消息队列、JVM 与并发。

## 学习路线

| 阶段 | 子项目 | 状态 | 目标 |
| --- | --- | --- | --- |
| 01 | [springboot-7-day](./springboot-7-day) | 进行中 | Spring Boot 3 + MySQL + MyBatis-Plus + 事务 CRUD |
| 02 | `redis-lab` | 待开始 | 缓存、分布式锁、缓存一致性 |
| 03 | `message-queue-lab` | 待开始 | RabbitMQ / Kafka 基础与可靠消息 |
| 04 | `jvm-concurrency-lab` | 待开始 | JVM、线程池、锁与并发容器 |
| 05 | `backend-capstone` | 待开始 | 可写入简历的综合后端项目 |

## 仓库原则

- 每个子项目必须可以独立运行。
- 每个知识点必须有代码、测试和复现命令。
- `必须掌握` 的内容要能脱离教程重写并口述原理。
- 不提交密码、密钥、IDE 缓存和构建产物。

## 环境

- JDK 17
- Maven 3.6.3+
- Docker Desktop
- Git

## 开始

```powershell
cd springboot-7-day
docker compose up -d
mvn clean test
mvn spring-boot:run
```

接口启动后访问：

```powershell
Invoke-RestMethod http://localhost:8080/api/hello
Invoke-RestMethod http://localhost:8080/api/db-users
```
