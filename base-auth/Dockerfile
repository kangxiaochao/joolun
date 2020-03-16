FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER www.joolun.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /base-auth

WORKDIR /base-auth

EXPOSE 5000

ADD ./base-auth/target/base-auth.jar ./

CMD sleep 150;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar base-auth.jar
