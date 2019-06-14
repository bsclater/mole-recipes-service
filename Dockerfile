FROM openjdk:11-jdk
VOLUME /tmp
ADD target/recipes.jar target/app.jar
RUN bash -c 'touch target/app.jar'
ENTRYPOINT ["java","-jar","target/app.jar"]
