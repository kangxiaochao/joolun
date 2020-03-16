FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER www.joolun.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /base-weixin

WORKDIR /base-weixin

EXPOSE 6002

ADD ./base-weixin/base-weixin-admin/target/base-weixin-admin.jar ./

CMD sleep 150;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar base-weixin-admin.jar
