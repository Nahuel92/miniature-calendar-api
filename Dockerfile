# Execute stage
FROM openjdk:17-slim-buster AS app-image
RUN adduser --system --group --no-create-home stduser
ENTRYPOINT ["java", "-jar", "app.jar"]
ENV SPRING_PROFILES_ACTIVE docker
COPY /build/libs/*.jar app.jar
USER stduser

# Unset SUID and GUID permissions to get a hardened docker image
RUN for i in `find / -perm +6000 -type f`; do chmod a-s $i; done
