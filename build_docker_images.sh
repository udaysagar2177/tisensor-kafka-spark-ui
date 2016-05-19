#!/bin/bash
set -xe

docker build -t tisensor_spark spark
docker build -t tisensor_rest_api rest-api