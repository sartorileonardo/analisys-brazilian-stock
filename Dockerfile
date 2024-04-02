FROM openjdk:11
COPY target/stock-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT java -Xss512k -Xss1m -jar /app.jar
