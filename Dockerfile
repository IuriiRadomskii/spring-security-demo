FROM openjdk:17-alpine
COPY target/react-demo-back-0.1.jar /usr/src/react-demo-back-0.1.jar
WORKDIR /usr/src/
ENTRYPOINT ["java", "-jar", "react-demo-back-0.1.jar"]
