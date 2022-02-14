FROM bellsoft/liberica-openjdk-alpine-musl:11

WORKDIR /app

COPY target/circuit-breaker.jar /app/circuit-breaker.jar

ENTRYPOINT ["java","-jar","myapp.jar"]