## Innocv Java Spring Boot Excercise

This is my solution of the problem presented in the repository [https://gist.github.com/pjmarchal/2e2ddf1957afa189036b8ab8aa76a558].  
It was built using Java 8 with Spring Boot as the backend framework following the Model-View-Controller pattern and with features such as Global Exception Handling and Entity validation.  
For the unit testing I used JUnit 5 with Mockito BDD.  
For the Data layer, I used a CrudRepository (from SpringBoot)  and an H2 in-memory database without physical persistence.  
I used Maven as the Dependency manager.  
I also added a swagger client to access the API through a UI.

### How to build and launch the application
Make sure to have maven installed and Java 8 in the PATH variable (and make sure nothing is running on port 8080). 
Clone this respository and execute the following command.

    mvn spring-boot: run

If you want to try the API, go to 

    http//:localhost:8080/swagger-ui.html

To launch the tests execute

     mvn clean test

### Things I would like to add / improve
There are no Unit tests for the Entity validation annotations. While this exercise is fairly simple, it would be nice to add some tests there.  
I would like to switch from JUnit assertions to AssertJ assertions.  
I would like to try Reactive programming with Spring Boot (as soon as I have a bit more time).  
Dockerizing the application would be nice.  

