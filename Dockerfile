FROM idock.daumkakao.io/adoptopenjdk/adoptopenjdk:11-hotspot-bionic AS builder

ENV GRADLE_OPTS -Dkotlin.compiler.execution.strategy="in-process"

WORKDIR /root

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY src ./src

RUN chmod +x /root/gradlew
RUN ./gradlew bootJar

# APP
FROM idock.daumkakao.io/adoptopenjdk/adoptopenjdk:11-hotspot-bionic
COPY --from=builder /root/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]


