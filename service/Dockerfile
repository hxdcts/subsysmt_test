FroM docker.sunfund.com/tomcat:7.0.75-alpine
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    rm -rf /usr/local/tomcat/webapps/*
ENV JAVA_OPTS=-Xmx1g
COPY ROOT.war /usr/local/tomcat/webapps/


