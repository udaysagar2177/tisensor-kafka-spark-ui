FROM ubuntu:14.04
MAINTAINER uday sagar shiramshetty <udaysagar.2177@gmail.com>

ENV DEBIAN_FRONTEND noninteractive
ENV INITRD no
RUN apt-get update && apt-get install -y python-pip python-dev
RUN pip install maestro-ng

# Install Java
# Add oracle java 7 ppa @ webupd8
RUN apt-get -y install software-properties-common python-software-properties
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get -y update

# Tell the oracle-java7-installer that we have already accepted the oracle java license
RUN echo "oracle-java7-installer shared/accepted-oracle-license-v1-1 boolean true" | debconf-set-selections

# Install oracle java 7
RUN apt-get -y install oracle-java7-installer

# Fix java-7-oracle cacerts
RUN apt-get -y install ca-certificates-java
RUN rm /usr/lib/jvm/java-7-oracle/jre/lib/security/cacerts ; ln -s /etc/ssl/certs/java/cacerts /usr/lib/jvm/java-7-oracle/jre/lib/security/cacerts


# Install Common Software
RUN apt-get install -y wget git vim curl initscripts net-tools passwd sudo tar

# Install MySQL
RUN apt-get -y install mysql-server libapache2-mod-auth-mysql php5-mysql

# Add Spark
RUN wget http://d3kbcqa49mib13.cloudfront.net/spark-1.5.2-bin-hadoop2.6.tgz
RUN tar -xvzf spark-1.5.2-bin-hadoop2.6.tgz
RUN mv spark-1.5.2-bin-hadoop2.6 /opt/spark

# Add maven
ENV MAVEN_VERSION 3.3.3

RUN mkdir -p /usr/share/maven \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

# Add code
ADD tisensor-spark /tisensor-spark
WORKDIR /tisensor-spark
RUN mvn package

# Add driver script
ADD run.py /tisensor-spark

CMD ["python", "-m", "trace", "--count", "run.py"]