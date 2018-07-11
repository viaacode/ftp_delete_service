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
This service depends on the FXP service [FXP service](https://github.com/viaacode/fxp_service), thus the VIAA nexus repo should be configured correctly.

Then you can compile or build the move service regularly with a Maven command of you choice (package, compile, ...) like you normally would.

`mvn package` creates the same 'fat' jar as `mvn clean compile assembly:single`. Build the project using one of the two following:

- `mvn clean compile assembly:single`
- `mvn clean package`

Once built, the artifact can be deployed to the nexus, assuming credentials for the VIAA repo are set up correctly:
- `mvn deploy`

Consult the [`mvn deploy`](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) documentation.

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