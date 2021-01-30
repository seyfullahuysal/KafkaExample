# Kafka usage example integrated with spring

**API USAGE**



> localhost:8080/api/kafka/test

       [  
       	 { 
       		 "topic": "test", 
       		 "data": "your message", 
       		 "partition":"0", 
       		 "key":"your message key" 
       	 }
       ]

**KAFKA USAGE**

- **Start zookeeper :**

  bin/zookeeper-server-start.sh config/zookeeper.properties

- **Start kafka :**

  bin/kafka-server-start.sh config/server.properties

- **Create topic :**

  bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test

- **Topic List :**

  bin/kafka-topics.sh --list --bootstrap-server localhost:9092

- **Consume with key :**

  bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning --property
  print.key=true

- **Consume without key :**

  bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

- **Consume with partition :**

  bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --partition 1 --from-beginning
- **Consume with consumer group :**

  bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning -consumer-property
  group.id=test-group

- **Produce with key :**

  bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test --property "parse.key=true"   --property "
  key.separator=:" key1:value1 key2:value2 key3:value3

- **Produce without key :**

  bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

- **List consumer group :**

  bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

- **Describe consumer group :**

  bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group kafka-example

- **Delete consumer group :**

  bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --delete --group kafka-example
