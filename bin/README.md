# Reddit Clone Backend

## - Inicilizar un proyecto Spring desde start.spring.io
## - Agregar las sig. dependencias:
    - Spring Web
    - Spring Security
    - Spring Data JPA
    - MySql Driver
    - Java Mail Sender
    - Java Validation (actualmente hay que agregarlo si o si)
 
 ## - En POM.xml agregar:
    - JWT related dependencies - Ver POM
    - timeago de Marlonlom en github - requiere agregar librerias de Kotlin

## - Configurar aplication properties:
        ### Database Properties #####
        spring.datasource.driver-class-name= com.mysql.cj.jdbc.Driver
        spring.datasource.url = jdbc:mysql://localhost:3306/reddit?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
        spring.datasource.username=root
        spring.datasource.password=
        spring.jpa.hibernate.ddl-auto = update
        spring.datasource.initialize= true
        spring.jpa.show-sql = true

        ### Mail Properties #########
        #spring.mail.host = smtp.mailtrap.io
        #spring.mail.port= 25
        #spring.mail.username= 
        #spring.mail.password=
        #spring.mail.protocol= smtp
