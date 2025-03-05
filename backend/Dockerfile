FROM amazoncorretto:21-alpine-jdk
COPY target/Chat-ai.jar chat-ai.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "Chat-ai.jar"]