# PC-Parts-Manager

This project it's api to manage computers and it's parts.

## Swagger access
- localhost:8080/swagger-ui/index.html

## H2 database console access
- localhost:8080/h2-console
- JDBC Url: jdbc:h2:mem:tools

## Methods
API request standards:

### Base URL: /computer

| Method | Description |
|---|---|
| `GET/{id}` | Return information of a record passing it's ID as a paramether. |
| `POST` | Creates a new record. |
| `PUT` | Updates a record. |
| `DELETE/{id}` | Deletes a record passing it's ID as a paramether. |

### Base URL: /component

| Method | Description |
|---|---|
| `GET` | Return information of all records. |
| `GET/{id}` | Return information of a record passing it's ID as a paramether. |
| `POST` | Creates a new record. |
| `POST/multiple` | Creates multiple records. |
| `PUT` | Updates a record. |
| `DELETE/{id}` | Deletes a record passing it's ID as a paramether. |

### Base URL: /users

| Method | Description |
|---|---|
| `GET` | Return information of all records. |
| `GET/{id}` | Return information of a record passing it's ID as a paramether. |
| `GET/email/{email}` | Return information of a record passing it's email as a paramether. |
| `POST` | Creates a new record. |
| `POST/multiple` | Creates multiple records. |
| `PUT/{id}` | Updates a record given it's ID as a paramether. |
| `DELETE/{id}` | Deletes a record passing it's ID as a paramether. |

### Base URL: /auth/login

| Method | Description |
|---|---|
| `POST` | Authenticates a user returning a token. |

## Authentication schema

![Diagrama de autenticação (1)](https://user-images.githubusercontent.com/31359489/225329715-0975324d-57c7-4910-93a2-a2d5afeb72ee.png)


### Built with

- Java
- Spring Boot
