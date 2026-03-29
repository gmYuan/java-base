FROM amazoncorretto:8-alpine

RUN mkdir /app

WORKDIR /app

COPY target/xdml-v1-0.1.0.jar /app

EXPOSE 8080

CMD [ "java", "-jar", "xdml-v1-0.1.0.jar" ]