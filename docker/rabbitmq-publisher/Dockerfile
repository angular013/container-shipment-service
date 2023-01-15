FROM ubuntu
RUN apt-get update && \
    apt-get install -y amqp-tools

COPY /docker/rabbitmq-publisher/script.sh .
RUN chmod 700 script.sh
CMD ["./script.sh"]