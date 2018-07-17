FROM node:latest AS storefront
WORKDIR /usr/src/junkstarter/app/frontend
COPY frontend .
RUN yarn
RUN yarn build

FROM maven:latest AS appserver
WORKDIR /usr/src/junkstarter
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:8-jdk-alpine
RUN adduser -Dh /home/gordon gordon
WORKDIR /static
COPY --from=storefront /usr/src/junkstarter/app/frontend/build/ .
WORKDIR /app
COPY --from=appserver /usr/src/junkstarter/target/AtSea-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/app/AtSea-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=postgres"]
