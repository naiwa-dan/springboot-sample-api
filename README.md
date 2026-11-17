# SpringBoot Sample API

一个基于 Spring Boot 3.3.8 + Java 17 的示例服务，对外提供 **3 个 REST 接口**，用于演示标准的工程结构、参数校验与统一异常处理。

## 技术栈

| 组件 | 版本 |
| --- | --- |
| Java | 17 |
| Spring Boot | 3.3.8 |
| 构建工具 | Maven 3.8+ |
| 测试 | JUnit 5 + MockMvc |

## 快速开始

```bash
# 1. 编译并运行全部测试
mvn clean package

# 2. 启动服务（默认端口 8080）
java -jar target/springboot-sample-api-1.0.0.jar

# 或者直接用 Maven 启动
mvn spring-boot:run
```

启动后访问 <http://localhost:8080/api/v1/health> 即可验证服务是否正常。

## 接口一览

| 序号 | 方法 | 路径 | 说明 |
| --- | --- | --- | --- |
| 1 | GET | `/api/v1/health` | 健康检查，返回服务名、版本与运行时长 |
| 2 | POST | `/api/v1/echo` | 回显请求内容，带参数校验 |
| 3 | GET | `/api/v1/time` | 按指定时区返回当前服务器时间 |

### 1. 健康检查

```http
GET /api/v1/health
```

```json
{
  "status": "UP",
  "service": "SpringBoot Sample API",
  "version": "1.0.0",
  "uptimeSeconds": 12,
  "timestamp": "2026-11-17T08:33:47.204413Z"
}
```

### 2. 回显

```bash
curl -X POST http://localhost:8080/api/v1/echo \
  -H "Content-Type: application/json" \
  -d '{"message": "hello spring", "client": "curl"}'
```

```json
{
  "message": "hello spring",
  "length": 12,
  "client": "curl",
  "receivedAt": "2026-11-17T08:33:47.388671Z"
}
```

请求字段约束：

- `message`：必填，非空白，最长 500 字符
- `client`：选填，最长 64 字符，缺省为 `anonymous`

### 3. 服务器时间

```http
GET /api/v1/time?zone=Asia/Shanghai
```

```json
{
  "zone": "Asia/Shanghai",
  "iso": "2026-11-17T16:33:47.468568700+08:00",
  "formatted": "2026-11-17 16:33:47",
  "epochMillis": 1794904427468,
  "offset": "+08:00"
}
```

`zone` 为可选的 IANA 时区 ID，默认 `UTC`；传入无法识别的时区会返回 400。

## 错误响应

所有异常都由 `GlobalExceptionHandler` 统一转换为同一结构：

```json
{
  "status": 400,
  "error": "VALIDATION_FAILED",
  "message": "request body failed validation",
  "details": ["message: message must not be blank"],
  "path": "/api/v1/echo",
  "timestamp": "2026-11-17T08:33:47.705217Z"
}
```

| error | HTTP 状态 | 触发场景 |
| --- | --- | --- |
| `VALIDATION_FAILED` | 400 | 请求体字段校验不通过 |
| `MALFORMED_JSON` | 400 | 请求体缺失或不是合法 JSON |
| `INVALID_ZONE` | 400 | 时区 ID 无法解析 |
| `NOT_FOUND` | 404 | 路径没有对应的处理器 |
| `INTERNAL_ERROR` | 500 | 未预期的服务端异常 |

## 项目结构

```
springboot-sample-api
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/example/sampleapi
│   │   │   ├── SampleApiApplication.java    # 启动类
│   │   │   ├── config/                      # 配置属性与时钟 Bean
│   │   │   ├── controller/                  # 三个接口
│   │   │   ├── dto/                          # 请求/响应记录类
│   │   │   ├── exception/                    # 统一异常处理
│   │   │   └── service/                      # 运行时长统计
│   │   └── resources/application.yml
│   └── test/java/com/example/sampleapi
│       └── ApiEndpointsTest.java            # 7 个接口测试
└── .gitignore
```

## 测试

```bash
mvn test
```

覆盖三个接口的正常路径、参数校验失败、非法时区以及 404 处理，共 7 个用例。
