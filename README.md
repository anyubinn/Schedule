## 일정 관리 앱 develop 과제
>Spring Boot 기반의 일정 관리 웹 애플리케이션<br>
유저 등록부터 일정 관리, 댓글 기능, 인증·인가, 페이징 처리까지 포함된 프로젝트
### 🚀 주요 기능 소개
#### ✏️ 일정 관리 (CRUD)
- 유저는 일정 생성, 조회, 수정, 삭제할 수 있습니다.
- 로그인을 한 유저만 접근이 가능합니다.
- 일정 목록은 유저명을 기준으로 조회할 수 있습니다.
- 일정 목록은 페이지 형태로 조회가 가능합니다.
- 선택한 일정의 상세 정보를 조회할 수 있습니다.
- 일정을 작성한 유저만 해당 일정을 수정/삭제할 수 있습니다.

#### ✏️ 유저 관리 (CRUD)
- 유저 생성, 조회, 수정, 삭제할 수 있습니다.
- 로그인을 한 유저만 유저 조회, 수정, 삭제에 접근이 가능합니다.
- 선택한 유저의 상세 정보를 조회할 수 있습니다.
- 유저를 생성한 유저만 해당 유저를 수정/삭제할 수 있습니다.

#### ✏️ 회원가입 및 로그인
- 유저는 이메일과 비밀번호를 통해 로그인이 가능합니다.
- 회원가입, 로그인, 로그아웃을 제외한 모든 경로는 로그인을 해야 접근이 가능합니다.

#### ✏️ 댓글 관리 (CRUD)
- 유저는 댓글 생성, 조회, 수정, 삭제할 수 있습니다.
- 로그인을 한 유저만 접근이 가능합니다.
- 댓글 목록은 유저명을 기준으로 조회할 수 있습니다.
- 선택한 댓글의 상세 정보를 조회할 수 있습니다.
- 댓글을 작성한 유저만 해당 댓글을 수정/삭제할 수 있습니다.

### 🛠️ 사용 기술 스택
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Servlet Filter
- BCrypt

### 🚀 API 명세서
<img width="1297" alt="Image" src="https://github.com/user-attachments/assets/c150b63a-a1de-4cae-b592-6d853726f08b" />
<img width="1299" alt="Image" src="https://github.com/user-attachments/assets/65200a3e-15c1-44da-ad7a-be29de92fb87" />
<img width="1300" alt="Image" src="https://github.com/user-attachments/assets/13b769cc-eb8b-45ae-af48-8087d8c28a4a" />
<img width="1300" alt="Image" src="https://github.com/user-attachments/assets/8675c283-3fbb-4a73-b950-e194b69b27a4" />

### 🚀 ERD
![Image](https://github.com/user-attachments/assets/8cf6fb88-0314-416c-8697-f9db3d6dea16)

### 🚀 프로젝트 구조
```
.
├── HELP.md
├── README.md
├── build
│   ├── classes
│   │   └── java
│   │       └── main
│   │           └── com
│   │               └── example
│   │                   └── schedule
│   │                       ├── ScheduleApplication.class
│   │                       ├── config
│   │                       │   ├── PasswordEncoder.class
│   │                       │   └── WebConfig.class
│   │                       ├── controller
│   │                       │   ├── CommentController.class
│   │                       │   ├── LoginController.class
│   │                       │   ├── ScheduleController.class
│   │                       │   └── UserController.class
│   │                       ├── dto
│   │                       │   ├── request
│   │                       │   │   ├── CommentRequestDto.class
│   │                       │   │   ├── LoginRequestDto.class
│   │                       │   │   ├── ScheduleRequestDto.class
│   │                       │   │   ├── UpdateCommentRequestDto.class
│   │                       │   │   ├── UpdateScheduleRequestDto.class
│   │                       │   │   ├── UpdateUserRequestDto.class
│   │                       │   │   └── UserRequestDto.class
│   │                       │   └── response
│   │                       │       ├── CommentResponseDto.class
│   │                       │       ├── ReadScheduleResponseDto.class
│   │                       │       ├── ScheduleResponseDto.class
│   │                       │       └── UserResponseDto.class
│   │                       ├── entity
│   │                       │   ├── BaseEntity.class
│   │                       │   ├── Comment.class
│   │                       │   ├── Schedule.class
│   │                       │   └── User.class
│   │                       ├── filter
│   │                       │   └── LoginFilter.class
│   │                       ├── handler
│   │                       │   ├── GlobalExceptionHandler.class
│   │                       │   └── LoginUserHandler.class
│   │                       ├── repository
│   │                       │   ├── CommentRepository.class
│   │                       │   ├── ScheduleRepository.class
│   │                       │   └── UserRepository.class
│   │                       └── service
│   │                           ├── CommentService.class
│   │                           ├── LoginService.class
│   │                           ├── ScheduleService.class
│   │                           └── UserService.class
│   ├── generated
│   │   └── sources
│   │       ├── annotationProcessor
│   │       │   └── java
│   │       │       └── main
│   │       └── headers
│   │           └── java
│   │               └── main
│   ├── reports
│   │   └── problems
│   │       └── problems-report.html
│   ├── resources
│   │   └── main
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── tmp
│       └── compileJava
│           ├── compileTransaction
│           │   ├── backup-dir
│           │   └── stash-dir
│           │       ├── CommentController.class.uniqueId0
│           │       ├── CommentService.class.uniqueId2
│           │       ├── ScheduleController.class.uniqueId1
│           │       ├── ScheduleService.class.uniqueId4
│           │       ├── UserController.class.uniqueId3
│           │       └── UserService.class.uniqueId5
│           └── previous-compilation-data.bin
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── README.md
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── schedule
    │   │               ├── ScheduleApplication.java
    │   │               ├── config
    │   │               │   ├── PasswordEncoder.java
    │   │               │   └── WebConfig.java
    │   │               ├── controller
    │   │               │   ├── CommentController.java
    │   │               │   ├── LoginController.java
    │   │               │   ├── ScheduleController.java
    │   │               │   └── UserController.java
    │   │               ├── dto
    │   │               │   ├── request
    │   │               │   │   ├── CommentRequestDto.java
    │   │               │   │   ├── LoginRequestDto.java
    │   │               │   │   ├── ScheduleRequestDto.java
    │   │               │   │   ├── UpdateCommentRequestDto.java
    │   │               │   │   ├── UpdateScheduleRequestDto.java
    │   │               │   │   ├── UpdateUserRequestDto.java
    │   │               │   │   └── UserRequestDto.java
    │   │               │   └── response
    │   │               │       ├── CommentResponseDto.java
    │   │               │       ├── ReadScheduleResponseDto.java
    │   │               │       ├── ScheduleResponseDto.java
    │   │               │       └── UserResponseDto.java
    │   │               ├── entity
    │   │               │   ├── BaseEntity.java
    │   │               │   ├── Comment.java
    │   │               │   ├── Schedule.java
    │   │               │   └── User.java
    │   │               ├── filter
    │   │               │   └── LoginFilter.java
    │   │               ├── handler
    │   │               │   ├── GlobalExceptionHandler.java
    │   │               │   └── LoginUserHandler.java
    │   │               ├── repository
    │   │               │   ├── CommentRepository.java
    │   │               │   ├── ScheduleRepository.java
    │   │               │   └── UserRepository.java
    │   │               └── service
    │   │                   ├── CommentService.java
    │   │                   ├── LoginService.java
    │   │                   ├── ScheduleService.java
    │   │                   └── UserService.java
    │   └── resources
    │       ├── application.properties
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── com
                └── example
                    └── schedule
                        └── ScheduleApplicationTests.java
```