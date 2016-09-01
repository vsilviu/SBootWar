#!/usr/bin/env bash
#automated local deploy action
mvn clean install &&
/Library/Tomcat/bin/shutdown.sh &&
rm -rf /usr/local/apache-tomcat-9.0.0.M9/logs/* &&
rm -rf /usr/local/apache-tomcat-9.0.0.M9/webapps/testwar &&
rm -rf /usr/local/apache-tomcat-9.0.0.M9/webapps/testwar.war &&
cp /Users/Silviu/IdeaProjects/Spring\ Boot\ War/Module_One/target/testwar.war /usr/local/apache-tomcat-9.0.0.M9/webapps &&
/Library/Tomcat/bin/startup.sh