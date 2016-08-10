# ftp_delete_service
Microservice that deletes a file from a server using FTP and message queuing

# Requests

An example request

```
{
	"file_name": "something.txt",
	"file_path": "/home/username/Documents",
	"host": "localhost",
	"username": "user1",
	"password": "password",
	"correlation_id": "somerandomstring"
}
```

An example response

```
{
	"file_name": "something.txt",
	"file_path": "/home/username/Documents",
	"status": "OK",
	"correlation_id": "somerandomstring""
}
```

# Maven 
To compile as fat jar

```
mvn clean compile assembly:single
```

# Properties

The properties are read from a file called "application.properties" located in the working
directory

Its location can also be set with the '-p' parameter. For example:

```
java -jar delete-service.jar -p /etc/delete.d/application.properties
```

Example properties file

```
# Configuration for the RabbitMQ connection
mq.rabbit.host=localhost
mq.rabbit.username=
mq.rabbit.password=
```