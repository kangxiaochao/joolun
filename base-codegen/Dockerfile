FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER www.joolun.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /base-codegen

WORKDIR /base-codegen

EXPOSE 7001

ADD ./base-codegen/target/base-codegen.jar ./

CMD sleep 150;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar base-codegen.jar
