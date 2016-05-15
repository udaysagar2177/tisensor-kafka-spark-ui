package com.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

/**
 * Created by uday on 5/6/16.
 */
public class KafkaProducerSingleton {
    private static KafkaProducer<String,String> producer;
    private static Object object = new Object();

    public static Producer getProducer(){
        if(producer == null){
            synchronized (object){
                String KAFKA_BROKER_LIST;
                if(System.getenv("KAFKA_BROKER_LIST") != null)
                    KAFKA_BROKER_LIST = System.getenv("KAFKA_BROKER_LIST");
                else{
                    throw new NullPointerException("KAFKA_BROKER_LIST is null!");
                }
                Properties props = new Properties();
                props.put("bootstrap.servers", KAFKA_BROKER_LIST);
                props.put("acks", "all");
                props.put("retries", 0);
                props.put("batch.size", 16384);
                props.put("linger.ms", 1);
                props.put("buffer.memory", 33554432);
                props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                producer = new KafkaProducer<String,String>(props);
            }
        }
        return producer;
    }
}
