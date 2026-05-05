FROM maven:3.9-eclipse-temurin-21 AS buildstage
WORKDIR /app
COPY pom.xml .
COPY src /app/src
COPY Wallet_DLNA4QPL3D91304G/ /app/wallet/
ENV TNS_ADMIN=/app/wallet
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=buildstage /app/target/reservas-hotel-0.0.1-SNAPSHOT.jar /app/reservas-hotel.jar
COPY Wallet_DLNA4QPL3D91304G/ /app/wallet/
ENV TNS_ADMIN=/app/wallet
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/reservas-hotel.jar"]
