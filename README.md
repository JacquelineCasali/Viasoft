## 🛠 Projeto
Desafio Full-Stack
O objetivo desse teste é entender quais são as suas habilidades de desenvolvimento, estética e técnicas.
1. Entidades bases:
   a. Empresa
   i. CNPJ
   ii. Nome Fantasia
   iii. CEP
   b. Fornecedor
   i. CNPJ ou CPF
   ii. Nome
   iii. E-mail
   iv. CEP
2. Requisitos
   a. CRUD de todas as entidades (Front-end e Back-end)
   b. Uma empresa pode ter mais de um fornecedor
   c. Um fornecedor pode trabalhar para mais de uma empresa
   d. O CNPJ e CPF deve ser um valor único
   e. Caso o fornecedor seja pessoa física, também é necessário cadastrar o RG e a data de nascimento
   f. Caso a empresa seja do Paraná, não permitir cadastrar um fornecedor pessoa física menor de idade
   g. A listagem de fornecedores deverá conter filtros por Nome e CPF/CNPJ
   h. Validar CEP na API http://cep.la/api, a validação também deve ser feita no Front-end
   i. Pode adicionar novas colunas, classes, heranças, entidades de relacionamentos e demais recursos que julgar necessário
   j. Teste de unidade (opcional)
   k. Implementar Dockerfile (opcional)




## 🛠 Imagem do Projeto


## 🛠 Tecnologias utilizadas

- **[Java 17]**
- **[Spring Boot]**
- **[JPA / Hibernate]**
- **[Maven]**
- **[H2]**
- **[mysql]**
- **[Postman]**
- **[springdoc]**
- **[cors]**

## Como executar o projeto

Pré-requisitos: Java 17

# clonar repositório
git clone https://github.com/JacquelineCasali/Sistema-para-gerenciar-empresas-e-fornecedores

# entrar na pasta do projeto back end

# executar o projeto
./mvnw spring-boot:run

Autora
Jacqueline Casali

https://www.linkedin.com/in/jaquelinecasali/



