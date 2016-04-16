package com.rest.service;

import com.rest.model.TiSensorDatapoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by uday on 3/21/16.
 */

@Service("TiSensorServiceImpl")
public class TiSensorServiceImpl implements TiSensorService {
    private Logger logger = LoggerFactory.getLogger(TiSensorServiceImpl.class);
    private int existingUsers = 0;

    public void publishDatapoint(TiSensorDatapoint datapoint) {
        // Publish this datapoint to kafka
    }
}
