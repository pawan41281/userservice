# userservice

mvn clean install -Dmaven.test.skip=true

docker build -t pawan41281/userservice:latest .

docker run --rm --name userservice -p 9999:9999 pawan41281/userservice:latest


**Spring Security Integration**

Login Functionality
User Registration Functionality

Execute the following queries before the user registration API call

INSERT INTO ROLES(1,'ROLE_ADMIN');

INSERT INTO ROLES(2,'ROLE_USER');
