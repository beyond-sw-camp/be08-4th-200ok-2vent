FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="henhen7 <g1008702@gmail.com>"
COPY ./backend/build/libs/backend-latest.jar /root
WORKDIR /root
CMD [ "java", "-jar", "backend-latest.jar" ]