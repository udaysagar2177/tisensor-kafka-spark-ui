package com.tisensor;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import java.util.HashMap;
import java.util.Map;


public class App {
    public static void main(String[] args) throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);

        String masterURL = "local[*]";
        String appName = "tisensor_spark";
        String topicName = args[0];
        String zkQuorum = args[1];
        final String pubnubChannel = "tisensor-spark";

        SparkConf sparkConf = new SparkConf().setMaster(masterURL).setAppName(appName);

        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new
                Duration(5000));

        Map<String, Integer> topicMap = new HashMap<>();
        topicMap.put(topicName, 1);

        JavaPairReceiverInputDStream<String, String> datapoints =
                KafkaUtils.createStream(jssc,
                        zkQuorum, "spark-consumer-group", topicMap);


        //datapoints.count().print();

        datapoints.count().foreach(new Function<JavaRDD<Long>, Void>() {
            @Override
            public Void call(JavaRDD<Long> longJavaRDD) throws Exception {
                longJavaRDD.foreach(new VoidFunction<Long>() {
                    @Override
                    public void call(Long aLong) throws Exception {
                        System.out.println(aLong);
                        try {
                            JsonObject data = new JsonObject();
                            data.addProperty("Total Datapoints Count:", aLong);
                            MyPubnub.pubnub.publish(pubnubChannel, new GsonBuilder().create().toJson
                                    (data), MyPubnub.callback);
                        }catch(Exception e){
                            System.out.println("Unable to publish data to " +
                                    "PubNub");
                        }
                    }
                });
                return null;
            }
        });

        /*datapoints.foreachRDD(
                new Function<JavaRDD<String>, String>() {
                    public Void call(JavaRDD<String> dpRDD) throws Exception {
                        dp
                        return null;
                    }
                }
        );*/

        jssc.start(); // Start the computation
        jssc.awaitTermination();
    }

}
