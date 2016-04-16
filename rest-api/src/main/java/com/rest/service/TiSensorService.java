package com.rest.service;

import com.rest.model.TiSensorDatapoint;

/**
 * Created by uday on 3/21/16.
 */

public interface TiSensorService {
    void publishDatapoint(TiSensorDatapoint datapoint);
}
