package com.load;

import com.rest.config.Constants;
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
    private Logger logger;
    private RestTemplate restTemplate;

    public RunnableTiSensor(String tiSensorId){
        this.tiSensorId = tiSensorId;
        logger = LoggerFactory.getLogger(
                RunnableTiSensor.class+", "+tiSensorId);
        restTemplate = new RestTemplate();
    }

    public void run(){
        Random random = new Random();
        int temperatureStart = 15 + random.nextInt(10);
        TiSensorDatapoint datapoint = new TiSensorDatapoint();
        while(true){
            prepareDataPoint(random, temperatureStart, datapoint);
            sendDataPoint(datapoint);
            try {
                Thread.sleep(1000);
            }catch(Exception e){
                logger.info("Thread: "+tiSensorId+" Interrupted!"+e);
            }
        }
    }

    private void sendDataPoint(TiSensorDatapoint datapoint){
        try{
            logger.debug("Sending datapoint to "+ Constants.DATAPOINT_REST_URL);
            restTemplate.postForLocation(
                    Constants.DATAPOINT_REST_URL,
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
