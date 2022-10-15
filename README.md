# GyManager backend source code
Codigo fuente de la API del sistema GyManager.

Esta aplicacion es una API REST desarrollada en Java 17 con Spring Boot,
utilizando Maven como gestor de dependencias, 
y Jacoco para repotes y validacion de covertura de test unitarios.

La aplicacion esta configurada para ser compilada con maven como un Jar. <br>
Cabe aclarar que para poder compilar debe haber una covertura de test unitarios minima del 50% 
(esto puede cambiarse desde el pom de ser requerido). 
Si desea esquivar esta validacion puede utilizar el parametro **-DskipTests**

## Despliegue
Para desplegar la aplicacion tan solo es necesario 
* Descargar este repositorio
* Compilar con el comando **mvn clean install [-DskipTests]** para generar el Jar
* Y luego simplemente desplegarlo con el comando **java -jar target/BackEndSource-1.0-SNAPSHOT.jar**

Cabe aclarar que para que la aplicacion funcione es necesario proveer 
* Una base de datos PostgreSQL
* Un conjunto de variables de entorno de configuracion descriptas a continuacion

## Variables de entorno necesarias para el despliegue
A la hora de levantar este servicio es necesario proveer 
las siguientes variables de entorno

##### Variables relacionadas a JWT (Valores de ejemplo seteados)
La aplicacion funciona con autorizacion mediante tokens JWT.<br>
Aqui tenemos nombre que le daremos a los claims dentro del token JWT,
tiempos de expiracion de los token
y la clave para firmar el mismo

    claim-name=permisos;
    access-token.expiration=3600000;
    access-token.header=access_token;
    refresh-token.expiration=604800000;
    refresh-token.header=refresh_token;
    secret.key=******************;

##### Variables de URLs de login y refresco de token
Configuracion de las URL de autenticacion y refresco de token.

    login-path=/api/auth;
    refresh-path=/api/auth/refresh;

##### Configuraciones para manejo de errores
Configuraciones para enviar verbosamente errores de input.

    server.error.include-binding-errors=always;
    server.error.include-message=always;

##### Configuraciones de Aplicación
    server.port=8081;
    spring.application.name=GyManager;

##### Configuraciones de la Base de datos
La aplicacion esta configurada para funcionar con una base de datos PostgreSQL

    spring.datasource.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect;
    spring.datasource.maxActive=5;
    spring.datasource.password=*********;
    spring.datasource.platform=postgres;
    spring.datasource.url=jdbc:postgresql://<dbms-url>:5432/<database>;
    spring.datasource.username=<user>;
    spring.jpa.properties.hibernate.default_schema=<schema>;
    
La aplicacion puede configurarse para funcionarcon delete logico o fisico con la siguiente propiedad.<br>
*Aclaracion: Es requerido colocar esta variable* 

    logical-delete=<true/false>
    
##### Configuracion para debugging de las transacciones a la Base de datos
Si es necesario ver en los logs que tipo de queries SQL estan siendo enviadas
puede configurar las siguientes variables de entorno

    spring.jpa.properties.hibernate.format_sql=true;
    spring.jpa.show-sql=true;
    hibernate.show-sql=true;

## Base de datos
La misma debe tener la estructura definida en: [URL] <br>
Y por defecto debera ser una base de datos PostgreSQL.

Se puede cambiar de motor de bases de datos agregando el driver en el pom.xml y 
cambiando las variables de entorno, pero dependiendo del motor podria haber algunos problemas menores.
Que deberan solventarse con los cambios necesarios en el codigo.

## Documentacion
La API utiliza para documentacion la herramienta de **springdoc-openapi-ui**
Para acceder a la documentacion una vez levantada la aplicacion acceda al path **/swagger-ui/index.html#/**. Ejemplo:
* Si esta accediente a los endpoint mediante http://localhost:8081/api/
* La documentacion estaria accesible en http://localhost:8081/swagger-ui/index.html#/