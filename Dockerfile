FROM openjdk:17-jdk-slim

COPY . .
RUN javac app/App.java
RUN jar -cfm App.jar manifest.txt

CMD java -jar App.jar
