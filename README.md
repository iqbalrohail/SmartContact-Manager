Following are the tools required to run this application in your local machine

These readme instructions are as per the Linux OS. You can install the same in Windows/MacOS by following the required utilities on internet.

# 1 Install intelliJ idea

sudo snap install intellij-idea-community --classic

# 2 Install JRE

apt install openjdk-11-jre-headless

apt install openjdk-11-jdk-headless

# 3 Install Mysql and create a database smart_db

sudo apt update 

sudo apt install mysql-server

sudo mysql_secure_installation    

sudo mysql

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root1234';

FLUSH PRIVILEGES;



# 4 Run IntelliJ idea

intellij-idea-community

(open the downloaded code base of the application)

# 5 Start you application as Java apllication

/SmartContactManagementSystem/src/main/java/com/smart/SmartContactManagerApplication.java

# 6 Application will run on this port in your browser

http://localhost:8888/


