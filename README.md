# 🌟 Spring Boot REST API Project Guide

Welcome to this awesome guide for setting up a basic CRUD REST API using Spring Boot! This project is perfect to train and review because
you will walk you through essential concepts like interaction between different layers, database connection, and basic testing.

Please check the code, review it and improve it and last but not least, fork it if you want...

## 🚀 Getting Started

### Prerequisites

Before we dive in, make sure you have the following installed:
- Java (JDK 21 or above)
- Maven
- An IDE (like IntelliJ IDEA or Eclipse)
- PostgreSQL (or any other relational database - change if you want)

### Project Structure

Here's a quick overview of our project structure:

```shell
src 
├── main 
│ ├── java 
│ │ └── com.globant.trainingnewgen 
│ │ ├─── controller 
│ │ ├─── service 
│ │ ├─── repository 
│ │ ├─── entity 
│ │ └─── mapper 
│ └── resources 
│ ├── application.yml 
└── test 
└─── java 
└──── globant.trainingnewgen
```

## 📦 Layers Interaction

### 1. Controller Layer 🎮

Controllers handle HTTP requests. Here's a basic example:

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }
}

```

### 2. Service Layer 🛠️

Services contain the business logic:

```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        user = userRepository.save(user);
        return UserMapper.toDto(user);
    }
}
```


### 3. Repository Layer 🗃️
Repositories interact with the database:
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```

### 4. Mapper Layer 🔄
Mappers convert between entities and DTOs:

```java
public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }

    public static User toEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        return user;
    }
}
```

### 5. Exception manager 🎉

Exception management is a fundamental part for all apis, in spring you can manage exceptions as cross-cutting-concerns:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> runtimeExceptionHandler(RuntimeException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        e.getMessage(),
                        request.getRequestURI()));

    }
}
```


### 🔗 Database Connection

Configure your database in application.properties:

```yml
spring:
  datasource:
    # Se esta usando postgresql como db, si quieres cambiarlo asegurate de cambiar el driver en el POM.xml
    username: # database url
    url: # database username
    password: # database password

  application:
    name: training-new-gen
```
### 🌐 Exposing REST Controllers

You can now expose your REST endpoints and interact with them using tools like Postman or CURL. For example:

**GET /users** to get all users
**POST /users** to create a new user

### 🧪 Basic Testing

Let's write a simple test for our service:

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");

        User user = new User();
        user.setName("John Doe");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto createdUser = userService.createUser(userDto);

        assertEquals("John Doe", createdUser.getName());
    }
}
```

