FROM mysql:8.0.16

MAINTAINER www.joolun.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY ./db/1base_config.sql /docker-entrypoint-initdb.d

COPY ./db/2base_upms.sql /docker-entrypoint-initdb.d

COPY ./db/3base_wx.sql /docker-entrypoint-initdb.d

COPY ./db/4base_mall.sql /docker-entrypoint-initdb.d
