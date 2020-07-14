FROM openjdk:8u141-jre

COPY /target/caresoft-0.0.1.jar /app/
WORKDIR /app/
ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

CMD java -jar -Dspring.profiles.active=release /app/caresoft-0.0.1.jar