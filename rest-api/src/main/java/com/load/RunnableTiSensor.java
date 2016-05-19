package com.load;

import com.rest.model.TiSensorDatapoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Created by uday on 3/22/16.
 */
public class RunnableTiSensor implements Runnable {
    private String tiSensorId;
    private RestTemplate restTemplate;
    private String DATAPOINT_REST_URL;
    private static Logger logger = LoggerFactory
            .getLogger(RunnableTiSensor.class);

    public RunnableTiSensor(String tiSensorId, String datapoint_rest_url){
        this.tiSensorId = tiSensorId;
        restTemplate = new RestTemplate();
        this.DATAPOINT_REST_URL = datapoint_rest_url;
    }

    public void run(){
        Random random = new Random();
        int temperatureStart = 15 + random.nextInt(10);
        TiSensorDatapoint datapoint = new TiSensorDatapoint();
        while(true){
            prepareDataPoint(random, temperatureStart, datapoint);
            sendDataPoint(datapoint);
            try {
                Thread.sleep(random.nextInt(500));
            }catch(Exception e){
                logger.info("Thread: "+tiSensorId+" Interrupted!"+e);
            }
        }
    }

    private void sendDataPoint(TiSensorDatapoint datapoint){
        try{
            logger.debug("Sending datapoint to "+ this.DATAPOINT_REST_URL);
            restTemplate.postForLocation(
                    this.DATAPOINT_REST_URL,
                    datapoint);
        }catch(Exception e){
            logger.error("Failed to send datapoint! "+e);
        }
    }

    private void prepareDataPoint(Random random, int temperatureStart,
                                  TiSensorDatapoint datapoint){
        datapoint.setTimestamp(System.currentTimeMillis());
        datapoint.setTemperature(temperatureStart + random.nextDouble());
        datapoint.setLight(random.nextInt(5) + random.nextDouble());
        datapoint.setTiSensorId(this.tiSensorId);
    }
}
