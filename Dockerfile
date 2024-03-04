
#Para crear la imagen: docker build -t vilu . -f .\Dockerfile --network spring
#Para levantar Mysql8: docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=db_pisos mysql:8
#Para ejecutar la imagen : docker run vilu
ARG VILU=vilu

FROM ibmjava:11-jdk as builder

ARG VILU

WORKDIR /app/$VILU

COPY .mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip

COPY src ./src
COPY uploads ./uploads

#Con esto indicamos donde está la calse main configurada en los plugins del pom
RUN ./mvnw package spring-boot:repackage -DskipTests=true

FROM ibmjava:11-jdk

ARG VILU

WORKDIR /app

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$VILU/target

COPY --from=builder $TARGET_FOLDER/vilu-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8080

ENV PORT $PORT_APP

EXPOSE $PORT

CMD ["java", "-jar", "vilu-0.0.1-SNAPSHOT.jar"]