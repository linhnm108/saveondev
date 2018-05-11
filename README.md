# saveondev
The assignment for Java Developer role with Saveondev.com

1. Create Spring Boot Web Application to manage documents (CRUD, upload, download)
  + Using Google Cloud Storage to store physical files.
  + Using PostgreSQL to store data.
2. Integrate Activiti with Spring Boot application to manage the upload document process.
This is main flow of this process:
  + Go to homepage: http://localhost:8080/ then upload file to start the process.
  + Go to approval page: http://localhost:8080/tasks (This page will display all active tasks that need to be reviewed.)
  User can download document to review.
  User can reject document.
  User can approve document.
  
  + Back to homepage to verify review result.(http://localhost:8080/)
  User can download the document that was approved.
  User can delete the document that was rejected.
  
3. Using docker
  + Build application
  mvn clean install
  
  + Run application
  cd src/main/docker
  docker-compose up

4. References
+ https://spring.io/blog/2015/03/08/getting-started-with-activiti-and-spring-boot
+ https://github.com/jirkapinkas/spring-boot-postgresql-docker-compose
+ https://cloud.google.com/java/getting-started/using-cloud-storage
+ https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.pdf
