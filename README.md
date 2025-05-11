
## Sobre o Projeto

Este sistema desenvolvido utilizando **Vaadin**, **Spring Boot** e **JPA (Hibernate)**, com foco na criação de uma interface moderna e funcional para **gestão de produtos e projetos**.

### Principais Funcionalidades 

* **Menu lateral dinâmico:** ao clicar em uma categoria (ex: *Gestão* ou *Projeto*), o cabeçalho superior se atualiza dinamicamente com submenus relacionados.
* **Navegação entre views:** cada submenu leva o usuário para uma `View` específica com seu próprio **Grid de dados** e **operações CRUD**.
* **Diálogos de edição:** formulários modais com validação, sem permitir o fechamento acidental.
* **Design responsivo e limpo**, com destaque visual para itens selecionados no menu.

---

## Estrutura do Menu

* **Gestão**

  * Produto
  * Categoria
* **Projeto**

  * Projetos
  * Projetos Ativos

Cada item do menu superior leva a uma tela (`View`) com:

* Um `Grid` exibindo os registros.
* Botões para **adicionar**, **editar** e **excluir** itens.
* Dialogs para formulários com validação de campos.

---

## Como Executar o Projeto

### Requisitos:

* Java 17+
* Maven 3.6+
* Banco de dados (MySQL, PostgreSQL ou H2)
* IDE (Eclipse, IntelliJ ou VS Code)

### Passos:

1. Clone o projeto:

   ```bash
   git clone https://github.com/abelcumbane/challenge_infordata.git
   cd seu-projeto
   ```

2. Configure a base de dados em `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nome_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Compile e execute:

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. Acesse o sistema:

   ```
   http://localhost:8080
   ```

---

## Tecnologias Utilizadas

* [Vaadin Flow](https://vaadin.com/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* Maven

---

## Tela principal:

![data](https://github.com/user-attachments/assets/6b04979e-63ab-4700-8259-b25f0cc9e101)

