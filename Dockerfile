# BUILDER
FROM eclipse-temurin:18-jdk AS app-builder
RUN useradd --user-group --system --create-home --no-log-init stduser
USER stduser

WORKDIR /app
COPY gradle gradle
COPY gradlew gradlew.bat settings.gradle build.gradle ./

RUN ./gradlew dependencies --no-daemon
COPY src src

## Unset SUID and GUID permissions to get a hardened image
RUN for i in `find / -perm +6000 -type f`; do chmod a-s $i; done
RUN ./gradlew bootJar --no-daemon

# APP
FROM eclipse-temurin:18 AS app-image
RUN useradd --user-group --system --create-home --no-log-init stduser
USER stduser

WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]
COPY --from=app-builder /app/build/libs/*.jar ./app.jar

## Unset SUID and GUID permissions to get a hardened image
RUN for i in `find / -perm +6000 -type f`; do chmod a-s $i; done
