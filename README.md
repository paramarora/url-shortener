# Url Shortener Application

Build Url Shortener application using Spring Boot, Mysql, JPA and Hibernate.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Mysql - 8.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/paramarora/url-shortener.git
```

**2. Create Mysql database**
```bash
create database urlshortener
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

```bash
mvn package
java -jar target/shorturl-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following Rest endpoints.

    POST /api/create-short-url
    
    GET /api/{shortUrl}
    
    GET /api/urls

## Explore UI
    
    baseURL - <http://localhost:8080>

## Swagger UI

    baseUrl - <http://localhost:8080/swagger-ui.html>
