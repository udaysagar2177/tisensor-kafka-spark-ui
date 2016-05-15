FROM maven:3.3.9-jdk-8

ENV DEBIAN_FRONTEND noninteractive
ENV INITRD no
RUN apt-get update && apt-get install -y python-pip python-dev
RUN pip install maestro-ng

ADD src /rest-api/src/
COPY pom.xml /rest-api/
COPY run.py /rest-api/

WORKDIR /rest-api

CMD ["python", "run.py"]