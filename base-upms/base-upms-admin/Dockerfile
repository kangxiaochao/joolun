FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER www.joolun.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /base-upms

WORKDIR /base-upms

EXPOSE 5999

ADD ./base-upms/base-upms-admin/target/base-upms-admin.jar ./

CMD sleep 150;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar base-upms-admin.jar
