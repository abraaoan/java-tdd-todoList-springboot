# TODO List API – Java + Spring Boot (Clean Architecture)

Este projeto é uma API para gerenciamento de tarefas (TODO list), desenvolvida com Java 17, Spring Boot e estruturada com os princípios da Clean Architecture. O foco é obter separação de responsabilidades, testabilidade e flexibilidade na evolução da aplicação.

## 🧰 Tecnologias utilizadas

- Java 17  
- Spring Boot  
- Maven  
- PostgreSQL  
- Spring Data JPA  
- JUnit 5 + Mockito  
- Bean Validation  
- Clean Architecture  

## 📁 Estrutura de pacotes

```text
com.todoList.app
├── domain
│   ├── model                  # Entidades de negócio
│   └── exception              # Exceções do domínio
├── application
│   ├── port
│   │   ├── in                 # Interfaces dos casos de uso (entrada)
│   │   └── out                # Interfaces de saída (ex: TaskRepository)
│   └── service                # Implementações dos casos de uso
├── adapter
│   ├── in
│   │   └── controller         # Controllers REST (entrada)
│   └── out
│       └── persistence        # Adaptadores de persistência
├── infrastructure
│   └── persistence
│       └── entity             # Entidades JPA (TaskEntity, UserEntity)
```

## 🔁 Padrões adotados

- Clean Architecture (Robert C. Martin)  
- Ports & Adapters (Hexagonal Architecture)  
- Inversão de dependência via interfaces  
- Separação entre domínio puro e frameworks  
- Testes em camadas: aplicação e infraestrutura  

## 📦 Endpoints principais

### Criar tarefa

POST /tasks  

Body esperado:  
{ "title": "Comprar leite", "userId": 1 }

Resposta:  
{ "id": 1, "title": "Comprar leite", "userId": 1, "completed": false }

### Buscar tarefa por ID

GET /tasks/{id}  

Resposta:  
{ "id": 1, "title": "Comprar leite", "userId": 1, "completed": false }

## ⚙️ Configuração do banco de dados

Arquivo: src/main/resources/application.properties  

spring.datasource.url=jdbc:postgresql://localhost:5432/todo_list?sslmode=disable  
spring.datasource.username=seu_usuario  
spring.datasource.password=sua_senha  
spring.datasource.driver-class-name=org.postgresql.Driver  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  
spring.jpa.properties.hibernate.format_sql=true  
server.port=8081  

## 🧪 Testes

- Testes de unidade para casos de uso com Mockito  
- Testes de integração com @SpringBootTest (adaptadores)  
- Testes JPA com @DataJpaTest  

## 🛡️ Tratamento de erros

- Exceções de domínio como InvalidTaskException e TaskNotFoundException  
- Mensagens carregadas via MessageSource (suporte a i18n)  
- Erros tratados com @ControllerAdvice e @ExceptionHandler  

## 📌 Boas práticas aplicadas

- DTOs limitados à camada adapter.in.controller.dto  
- Domínio não conhece frameworks nem JSON  
- Separação clara entre casos de uso, controladores e persistência  
- Validações feitas no domínio e no DTO via Bean Validation  

## 🚀 Possíveis melhorias futuras

- Autenticação com JWT  
- Documentação com Swagger/OpenAPI  
- Docker para ambiente de desenvolvimento  
- RabbitMQ para eventos assíncronos  
- Kubernetes para orquestração  