## Desafio Back-End Viasoft

Imagine (sem realizar a integração) que você precisa enviar e-mail mediante plataformas como AWS e OCI.
O teste consiste em criar uma aplicação REST com endpoint que recebe dados para envio de email, com apenas uma requisição, sem alterar o objeto de entrada, dependendo da configuração setada em application.properties o objeto deve ser adaptado para novas classes, também deve ser serializado e impresso no console.
## 🎥Vídeo do projeto 



## 🚀 Tecnologias Utilizadas

## Backend:
- **[Java 17]**
- **[Spring Boot(Spring Web, Spring Data JPA, Validation)]**
- **[Hibernate (ORM para interação com banco de dados) ]**
- **[Maven]**
- **[mysql (Banco de dados relacional)]**
- **[Lombok (Redução de código boilerplate)]**
- **[Postman]**
- **[springdoc (Documentação da API)]**
- **[cors]**

## ⚙️ Funcionalidades


## 🚀 Como Rodar o Projeto

📌 1. Configuração do Banco de Dados

Certifique-se de que você tem o MySQL instalado e crie um banco de dados:
CREATE DATABASE desafio_fullstack;

📌 2. Configurar o application.properties

No diretório src/main/resources/application.properties, configure a conexão com o banco:

- spring.datasource.url=jdbc:mysql://localhost:3306/desafio_fullstack
- spring.datasource.username=root
- spring.datasource.password=senha
- spring.jpa.hibernate.ddl-auto=update
 
📌 3. Rodar o Backend (Spring Boot)
- ./mvnw spring-boot:run


Execute o seguinte comando na raiz do projeto backend:

Pré-requisitos: Java 17

# clonar repositório
git clone https://github.com/JacquelineCasali/Viasoft.git

# executar o projeto
./mvnw spring-boot:run

A API estará disponível em: http://localhost:8080


📖 Documentação da API

Após iniciar o backend, acesse a documentação da API no Swagger:
http://localhost:8080/swagger-ui/index.html

## 📝 Projeto Desenvolvido por
Jacqueline Casali
https://www.linkedin.com/in/jaquelinecasali/



