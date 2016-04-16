package com.rest.controller;

import com.rest.model.TiSensorDatapoint;
import com.rest.service.TiSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by uday on 3/21/16.
 */

@RestController
public class TiSensorController {

    @Autowired
    TiSensorService tiSensorServiceImpl;

    private static Logger logger = LoggerFactory.getLogger(
            TiSensorController.class);;


    @RequestMapping(value = "/datapoint", method = RequestMethod.POST,
            consumes = {"application/json"})
    public ResponseEntity<Void> checkAndInsertDataPoint(
            @RequestBody TiSensorDatapoint datapoint){
        logger.info("Datapoint is received!");
        tiSensorServiceImpl.publishDatapoint(datapoint);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Void> sayHello(){
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
