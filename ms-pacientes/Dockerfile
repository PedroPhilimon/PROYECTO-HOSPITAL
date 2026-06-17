# Etapa 1: Compilar la aplicación
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el pom.xml y descargar las dependencias primero (aprovecha la caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar el archivo JAR omitiendo los tests
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución ligera
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiar el JAR generado desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto interno en el que escucha la aplicación (según tu docker-compose es el 8082)
EXPOSE 8082

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]  