FROM openjdk:8-jdk-alpine
COPY tourism-authority/target/tourism-authority-0.0.1-SNAPSHOT.jar tourism-authority.jar
EXPOSE 8881
CMD java -jar tourism-authority.jar --server.port=8881 --spring.profiles.active=pro