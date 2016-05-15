package com.rest.service;

import com.kafka.KafkaProducerSingleton;
import com.rest.model.TiSensorDatapoint;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by uday on 3/21/16.
 */

@Service("TiSensorServiceImpl")
public class TiSensorServiceImpl implements TiSensorService {
    private Logger logger = LoggerFactory.getLogger(TiSensorServiceImpl.class);

    public void publishDatapoint(TiSensorDatapoint datapoint) {
        String KAFKA_TOPIC = System.getenv("KAFKA_TOPIC");
        if(KAFKA_TOPIC == null){
            logger.error("Environment variable KAFKA_TOPIC is null!");
        }
        ProducerRecord<String,String> producerRecord =
                new ProducerRecord<>(KAFKA_TOPIC,
                        datapoint.getTiSensorId(),
                        datapoint.getTemperature()
                        +"");
        KafkaProducerSingleton.getProducer().send(producerRecord);
    }
}
