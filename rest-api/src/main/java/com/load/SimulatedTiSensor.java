package com.load;

import com.rest.config.Constants;

/**
 * Created by uday on 3/22/16.
 */
public class SimulatedTiSensor {
    public static void main(String args[]){
        for(int i=0; i<Constants.SIMULATED_TISENSOR_COUNT; i++){
            new Thread(new RunnableTiSensor(
                    Constants.SIMULATED_TISENSOR_ID_HANDLE+i)).start();
        }
    }
}
