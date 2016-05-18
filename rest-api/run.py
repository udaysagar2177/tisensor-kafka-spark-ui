import logging
import os
import sys
import random

from maestro.guestutils import get_container_name, \
    get_container_host_address, \
    get_environment_name, \
    get_node_list, \
    get_port, \
    get_service_name

LOAD_GENERATION = os.getenv('LOAD_GENERATION', None)

if LOAD_GENERATION and bool(LOAD_GENERATION) is True:
    # Start load generation
    REST_URL_LIST = (get_node_list('restapi', ports=['rest']))
    REST_URL = 'http://' + REST_URL_LIST[random.randrange(0, len(REST_URL_LIST))] + '/datapoint'
    SIMULATED_TISENSOR_COUNT = os.getenv('SIMULATED_TISENSOR_COUNT', 5)
    SIMULATED_TISENSOR_ID_HANDLE = os.getenv('SIMULATED_TISENSOR_ID_HANDLE', 'TISENSOR_DEFAULT_ID_')
    os.execl('/usr/bin/mvn', '-e', '-X', 'exec:java', '-Dexec.mainClass=com.load.SimulatedTiSensor', '-Dexec.args='+REST_URL+' '+SIMULATED_TISENSOR_COUNT+' '+SIMULATED_TISENSOR_ID_HANDLE)
else:
    # Start the REST API.
    KAFKA_BROKER_LIST = ','.join(get_node_list('kafka', ports=['broker']))
    os.environ['KAFKA_BROKER_LIST'] = KAFKA_BROKER_LIST
    os.execl('/usr/bin/mvn', '-X', 'tomcat7:run',
             '-Dmaven.tomcat.port=' +
             (str)(get_port('rest')))
