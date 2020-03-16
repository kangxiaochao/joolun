FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER www.joolun.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /base-mall

WORKDIR /base-mall

EXPOSE 6003

ADD ./base-mall/base-mall-admin/target/base-mall-admin.jar ./

CMD sleep 150;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar base-mall-admin.jar
