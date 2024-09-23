# Based on `ykenan/tomcatr:java17tomcat10R4_2` image
FROM ykenan/tomcatr:java17tomcat10R4_2
# Author
MAINTAINER YuZhengMin
# Define variables
ENV DIR_WEBAPP /home/tomcat/tomcat/webapps/
ENV WEB_NAME scBG_service
# Switch working path
WORKDIR $DIR_WEBAPP
# Add local war package to remote container
ADD ./target/sc2GWAS-1.0.0.war $WEB_NAME.war
# Unzip the war package
RUN unzip $WEB_NAME.war -d $WEB_NAME
