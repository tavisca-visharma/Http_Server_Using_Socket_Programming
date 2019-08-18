FROM openjdk:12

COPY build/libs /var/www/java

WORKDIR /var/www/java

EXPOSE 80

CMD ["java","-jar", "HttpServer-1.0-SNAPSHOT.jar"]
