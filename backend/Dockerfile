FROM maven:3.9.9-eclipse-temurin-17-alpine 
ENV HOME=/usr/app
RUN mkdir -p $HOME
RUN mkdir -p /app
WORKDIR $HOME
ADD . $HOME
RUN chmod a+x ./mvnw
RUN ./mvnw clean package spring-boot:repackage -Dmaven.test.skip=true
RUN cp -r /usr/app/target/*.jar /app/runner.jar
EXPOSE 8080
CMD [ "java", "-jar", "/app/runner.jar" ] 
