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


KAFKA CONNECT

SOURCE CONNECTOR
after adding proper entry in docker-compose 'confluentinc/cp-kafka-connect:7.9.0' i need to create folder called plugins. i can name it
differently but then this change need  to be relfected in volume definition inside docker-compose. once folder is created i need to create
subfolder - in my case its called 'mongodb-kafka-connect-mongodb' but name can be different and does not have any implication.
i just need any folder because confluentinc/cp-kafka-connect:7.9.0 is not able to read properly from root folders. Once
i have proper folder structure i need actual source/sink jar. for mongodb i can visit their side 'https://www.confluent.io/hub/mongodb/kafka-connect-mongodb'
i just need jar file rest is not relevant. i put this jar in previously created subfolder.

next i run docker-compose up

in order to check if plugin is configured as expected i can run

curl --location 'http://localhost:8083/connector-plugins'

and seek proper entry related with mongodb

once its up and running i need to provide configuration for my source (i want kafka broker to read data from mongodb database called
for-kafka and collection users to kafka topic called mongo..for-kafka.users).

i can do that by executing

curl --location 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data '{
"name": "mongodb-source-connector",
"config": {
"connector.class": "com.mongodb.kafka.connect.MongoSourceConnector",
"tasks.max": "1",
"connection.uri": "mongodb://mongodb:27017",
"database": "for-kafka",
"collection": "users",
"topic.prefix": "mongo.",
"output.format.key": "json",
"output.format.value": "json",
"change.stream.full.document": "updateLookup"
}
}'

to check if everything is working as expected i can execute status of my connector

curl --location 'http://localhost:8083/connectors/mongodb-source-connector/status'

once everything works without errors i can define new entry in mongodb for a given table and then read topic to see it
./kafka-console-consumer.sh --bootstrap-server localhost:29092 --topic mongo..for-kafka.users --from-beginning
as of now topic is populated only with new db entries old are not there, in order to read all messages connector config need to
be somehow changed but at this point i do not care

SINK CONNECTOR

steps are very similar as for producer, exception is in defining connector config

curl --location 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data '{
"name": "mongodb-sink-connector",
"config": {
"connector.class": "com.mongodb.kafka.connect.MongoSinkConnector",
"tasks.max": "1",
"topics": "test-topic",
"connection.uri": "mongodb://mongodb:27017",
"database": "sink-db",
"collection": "sink-collection",
"key.converter": "org.apache.kafka.connect.storage.StringConverter",
"value.converter": "org.apache.kafka.connect.json.JsonConverter",
"value.converter.schemas.enable": "false"
}
}'

after executing this i will have in db all events from given topic in this case 'test-topic'

if for some reason i will encounter problems like: my event never reached db i can execute
curl --location 'http://localhost:8083/connectors/mongodb-sink-connector/status'

in order to see what went wrong (I should have detailed stack trace there)

