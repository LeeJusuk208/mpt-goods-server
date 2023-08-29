# Stage 1: Build the application
FROM ubuntu:20.04 AS build

WORKDIR /home/gradle/project

RUN apt-get update;
RUN apt-get install -y openjdk-17-jdk;
RUN apt-get install -y unzip zip;
RUN apt-get clean;

COPY . .

RUN ./gradle_build.sh

# Stage 2: Run the application
FROM ubuntu:20.04

WORKDIR /root/workspace

RUN apt-get update;
RUN apt-get install -y openjdk-17-jre;
RUN apt-get clean;

COPY --from=build /home/gradle/project/build/libs/goods-server-1.0.jar .

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "goods-server-1.0.jar"]

EXPOSE 8080