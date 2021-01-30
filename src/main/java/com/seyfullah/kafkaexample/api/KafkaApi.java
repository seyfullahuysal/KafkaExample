package com.seyfullah.kafkaexample.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seyfullah.kafkaexample.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaApi {

    @Autowired
    KafkaService kafkaService;

    @Value("${kafka.topic}")
    String topic;

    @GetMapping(path = "/test", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> select(@RequestBody String body) {
        try {

            JsonArray jsonArray = new JsonParser().parse(body).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                JsonObject inputJson = jsonElement.getAsJsonObject();
                String topic = inputJson.get("topic").getAsString();
                Integer partition = null;
                String key = null;
                if (inputJson.has("partition")) {
                    partition = inputJson.get("partition").getAsInt();
                }
                if (inputJson.has("key")) {
                    key = inputJson.get("key").getAsString();
                }
                String data = inputJson.get("data").getAsString();
                if (partition == null) {
                    if (key == null) {
                        kafkaService.produce(topic, data);
                    } else {
                        kafkaService.produce(topic, key, data);
                    }
                } else {
                    kafkaService.produce(topic, partition, key, data);
                }
            }

            return new ResponseEntity<String>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
