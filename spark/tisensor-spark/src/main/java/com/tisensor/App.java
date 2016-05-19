package com.tisensor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
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
    static Pubnub pubnub;
    static String publishKey = System.getenv("PUBNUB_PUBLISH_KEY");
    static String subscribeKey = System.getenv("PUBNUB_SUBSCRIBE_KEY");
    static Callback callback;
    static final String pubnubChannel = "tisensor-spark";
    static final Gson gson = new GsonBuilder().create();


    public static void main(String[] args) throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);

        String masterURL = "local[*]";
        String appName = "tisensor_spark";
        String topicName = args[0];
        String zkQuorum = args[1];

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
                            publishDatapointCount(aLong);
                        }catch(Exception e){
                            System.out.println("Unable to publish data to " +
                                    "PubNub");
                        }
                    }
                });
                return null;
            }
        });

        jssc.start(); // Start the computation
        jssc.awaitTermination();
    }

    private static Pubnub getPubNub(){
        if(pubnub == null){
            if(publishKey == null || subscribeKey == null){
                throw new NullPointerException("Environment variables:" +
                        "PUBNUB_PUBLISH_KEY or " +
                        "PUBNUB_SUBSCRIBE_KEY is not set!");
            }
            pubnub = new Pubnub(publishKey, subscribeKey);
            callback = new Callback() {
                public void successCallback(String channel, Object response) {
                    System.out.println("My Publish callback message: "+response
                            .toString
                            ());
                }
                public void errorCallback(String channel, PubnubError error) {
                    System.out.println("My Publish callback message: "+error
                            .toString());
                }
            };
        }
        return pubnub;
    }

    private static void publishDatapointCount(Long datapointCount){
        JsonObject data = new JsonObject();
        data.addProperty("datapointCount", datapointCount);
        getPubNub().publish(pubnubChannel, gson.toJson(data), callback);
    }

}
