# FROM tomcat
# COPY target/zap.war.original /usr/local/tomcat/webapps/zap.war


# FROM openjdk:17-jdk-alpine
# ARG JAR_FILE=target/*.jar
# COPY ./target/zap.jar app.jar
# EXPOSE 9060
# ENTRYPOINT ["java", "-jar", "/app.jar"]



#
# Build stage
#
FROM maven:3.8.5-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/zap.jar /usr/local/lib/zap.jar
EXPOSE 9060
ENTRYPOINT ["java","-jar","/usr/local/lib/zap.jar"]