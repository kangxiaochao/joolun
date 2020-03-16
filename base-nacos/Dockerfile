FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER www.joolun.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /base-nacos

WORKDIR /base-nacos

EXPOSE 8848

ADD ./base-nacos/target/base-nacos.jar ./


CMD sleep 50;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar base-nacos.jar


