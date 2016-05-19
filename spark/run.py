import os
import urllib
import sys
import trace
from subprocess import call


from maestro.guestutils import get_container_name, \
    get_container_host_address, \
    get_environment_name, \
    get_node_list, \
    get_port, \
    get_service_name

ZOOKEEPER_NODE_LIST = ','.join(s + "/zk-kafka/kafka" for s in (get_node_list(
        'zookeeper', ports=['client'])))

KAFKA_TOPIC = os.getenv('KAFKA_TOPIC')
call(['mvn', 'package'])
call(['/opt/spark/bin/spark-submit',
      '--class',
      'com.tisensor.App',
      'target/tisensor_spark-jar-with-dependencies.jar',
      KAFKA_TOPIC,
      str(ZOOKEEPER_NODE_LIST)])
