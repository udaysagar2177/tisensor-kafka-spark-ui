package com.load;

/**
 * Created by uday on 3/22/16.
 */

public class SimulatedTiSensor {
    public static void main(String args[]) throws Exception {
        if (args.length < 3) {
            throw new IllegalArgumentException(
                    "Usage: SimulatedTiSensor " +
                            "DATAPOINT_REST_URL " +
                            "SIMULATED_TISENSOR_COUNT" +
                            "SIMULATED_TISENSOR_ID_HANDLE");
        }
        String DATAPOINT_REST_URL = args[0];
        int SIMULATED_TISENSOR_COUNT = Integer.parseInt(args[1]);
        String SIMULATED_TISENSOR_ID_HANDLE = args[2];

        for (int i = 0; i < SIMULATED_TISENSOR_COUNT; i++) {
            new Thread(new RunnableTiSensor(SIMULATED_TISENSOR_ID_HANDLE + i,
                    DATAPOINT_REST_URL)).start();
        }
    }

    // TODO: FIND A BETTER WAY OF INITIALIZING CONSTANTS - DONE
    // TODO: CHANGE RUN.PY TO ACCOMODATE LOAD KIND OF LAUNCH - DONE
    // TODO: ADD ZOOKEEPER TO REGISTER REST SERVER AND GET REST SERVER IN LOAD
    //
}
