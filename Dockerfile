FROM maven:3.8.4-openjdk-17 AS MAVEN_TOOL_CHAIN

COPY pom.xml /tmp/
RUN mvn dependency:go-offline -f /tmp/pom.xml

COPY src /tmp/src/
WORKDIR /tmp/

RUN mvn clean package


FROM openjdk:17-jdk-oracle

EXPOSE 8080

RUN mkdir /app
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

HEALTHCHECK --interval=1m --timeout=3s CMD wget -q -T 3 -s http://localhost:8080/actuator/health/ || exit 1
