# kafka-exercise

to use cli tools to create topic, produce/consume messages using cli tools -> https://hub.docker.com/r/apache/kafka#quick-start

1. docker-compose up
2. docker exec -it broker /bin/bash
3. inside container goto /opt/kafka/bin
4. execute './kafka-topics.sh --bootstrap-server localhost:9092 --create --topic test-topic' in order to create test-topic
5. execute './kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test-topic' -> this will generate prompt
after each hit enter , when im done providing events control + C
6. execute './kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning' in order to consume
to finish listening hit ctrl + C
7. for ui we need to allow docker container to read config file. to do so chmod 644 ~/kui/config.yml

