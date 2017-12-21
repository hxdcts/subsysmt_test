FROM java:7-jdk-alpine
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    rm -rf /usr/local/tomcat/webapps/*
ENV JAVA_OPTS=-Xmx1g
COPY lib/* /usr/local/dubbo/lib/
COPY dubbo_provider.jar /usr/local/dubbo/dubbo_subsysmt_provider.jar
RUN chmod =x /usr/local/dubbo/dubbo_subsysmt_provider.jar


