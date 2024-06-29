# market-solution

**integrantes:**

- vinicius augusto moreira costa - RA: 1222011688,  Daniel Augusto Eller - RA: 123221280

**testes**
  ![Screenshot 2024-06-28 211946](https://github.com/vinicost01/07-Gestao-e-qualidade-de-software/assets/65936994/0151b34b-1fa5-4663-8057-e43147b6fb48)


**Tecnologias utilizadas**

- Maven para gestão de dependências e build
- SpringBoot + Spring Data JPA + Spring Rest + Spring Test
- Swagger para a documentação da API REST
- Junit para os testes

**Ambiente de Desenvolvimento**

- Java OpenJDK 22
- VSCode com plugin Java Extension Pack
- Versionamento com GitHub

**Configuração da aplicação**

application.properties

```
spring.datasource.url=jdbc:h2:file:./db
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.platform=h2
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.sql.init.mode=always
```

O arquivo import.sql pode ser utilizado para incluir registros de exemplo ao inicializar a aplicação.

**Configuração para testes**

application.properties

```
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.platform=h2
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.sql.init.mode=always
spring.h2.console.enabled=true
```

