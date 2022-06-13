FROM tomcat:10-jdk17
ADD target/ThePolynomialCalculator-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/PolynomialCalculator.war
EXPOSE 8080
CMD ["catalina.sh", "run"]