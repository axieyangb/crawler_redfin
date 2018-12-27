FROM java
COPY target/classes/browers/chromedriver_linux /src/main/resources/browers/chromedriver_linux
COPY target/classes/datasheets/zip_code.csv /src/main/resources/datasheets/zip_code.csv
COPY target/crawler_redfin-0.0.1-SNAPSHOT.jar /crawler_redfin-0.0.1-SNAPSHOT.jar
RUN chmod +x /src/main/resources/browers/chromedriver_linux
workdir /
ENTRYPOINT ["java", "-jar","crawler_redfin-0.0.1-SNAPSHOT.jar"]
