FROM livingobjects/jre8

ADD yss-qf-gate.jar /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xdebug","-Xrunjdwp:transport=dt_socket,address=8999,server=y,suspend=n","-jar","/app.jar"]
