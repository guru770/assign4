FROM openjdk:11 as mysqldoc
EXPOSE 8768
WORKDIR /CustomerProducer

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN chmod 755 /CustomerProducer/mvnw

RUN ./mvnw dependency:go-offline -B

RUN ./mvnw package -DskipTests
RUN ls -al
ENTRYPOINT ["java","-jar","eurekaProducer-0.0.1-SNAPSHOT.jar"]