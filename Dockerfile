# BUILDER
FROM eclipse-temurin:18-jdk AS app-builder

## Unset SUID and GUID permissions to get a hardened image
RUN for i in `find / -perm /6000 -type f`; do chmod a-s $i; done

## Add and use non-root user
RUN adduser --disabled-login --system stduser
USER stduser

## Copy necessary stuff
WORKDIR /home/stduser
COPY gradle gradle
COPY gradlew gradlew.bat settings.gradle build.gradle ./

## Download dependencies
RUN ./gradlew dependencies --no-daemon

# Package JAR
COPY src src
RUN ./gradlew bootJar --no-daemon

# APP
FROM eclipse-temurin:18 AS app-image

## Unset SUID and GUID permissions to get a hardened image
RUN for i in `find / -perm /6000 -type f`; do chmod a-s $i; done

## Add and use non-root user
RUN adduser --disabled-login --system stduser
USER stduser

## Entrypoint for app
ENTRYPOINT ["java", "-jar", "app.jar"]
## Copy JAR
WORKDIR /home/stduser
COPY --from=app-builder /home/stduser/build/libs/*.jar ./app.jar
