FROM eclipse-temurin:21
COPY target/medical-clinic-proxy-0.0.1-SNAPSHOT.jar medical-clinic-proxy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/medical-clinic-proxy-0.0.1-SNAPSHOT.jar"]