FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY FoodOrderingApp-api/pom.xml FoodOrderingApp-api/
COPY FoodOrderingApp-service/pom.xml FoodOrderingApp-service/
COPY FoodOrderingApp-db/pom.xml FoodOrderingApp-db/
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
RUN mvn dependency:go-offline -B || true
COPY . .
RUN mvn clean install -DskipTests -q

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/FoodOrderingApp-api/target/FoodOrderingApp-api-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
