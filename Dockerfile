FROM openjdk:22
WORKDIR /home
COPY /target/market-solution.jar market-solution.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "market-solution.jar"]
