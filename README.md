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

# Properties File

The properties file is read from the same folder the program is run from.

Its location can also be set with the '-p' parameter. For example:

```
java -jar delete-service.jar -p /etc/delete.d/application.properties
```