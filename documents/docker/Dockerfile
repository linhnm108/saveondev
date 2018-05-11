FROM openjdk:8-jre
WORKDIR /app
VOLUME ["/app"]
COPY maven/documents.jar documents.jar
COPY maven/start.sh start.sh
COPY maven/wait-for-it.sh wait-for-it.sh
RUN sh -c 'touch documents.jar'
ENTRYPOINT ["./start.sh"]