FROM tomcat:9.0

# 設定時區為 Asia/Taipei
RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/Asia/Taipei /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata

COPY target/jersey-modular-monolith.war /usr/local/tomcat/webapps/

CMD ["/usr/local/tomcat/bin/catalina.sh","run"]