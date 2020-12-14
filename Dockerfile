FROM openjdk:15
ADD target/roleservice.jar roleservice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "roleservice.jar"]