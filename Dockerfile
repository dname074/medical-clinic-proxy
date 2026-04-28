FROM eclipse-temurin:21
COPY adapter/target/adapter-0.0.1-SNAPSHOT.jar medical-clinic-proxy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/medical-clinic-proxy-0.0.1-SNAPSHOT.jar"]