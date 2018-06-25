### Generic micro service with CI

This is my first take on what is the recommended approach to build a micro service, with CI/CD in mind. Of course the focus here is on CI. This project is not going to share knowledge on how CD should be done.

For those who are doing it for the first time, read on.

I have taken a stab with the latest libraries and SDK in mind.
As I was working with JDK 10, I went through quite a few challenges to build a complete micro service in CI/CD model.
It would be worthwhile to share my final approach and get some feedback.

### Objectives that were thought through while building this sample service

1. Using JDK 10 (Java 10)

With the introduction of modularization, things have changed. I observed many tools and plugins are not completely ready and not seamless to intergrate with Java 10.
In order to build this project, you need JDK 10.

2. Multi module maven project

In a real world, a single app or micro service will tend to have multiple maven modules.
We obviously need techniques to effectively run unit tests, integation tests and get a overall code coverage.
This sample service tries to achieve the same.

3. Using HTTP/2 and Spring boot 2.x

I am a big fan of HTTP/2 and want to use it for my future assignments. Spring 2.x has made it so easy to build HTTP2 server.
In this approach, the spring boot app supports both HTTP/1.1 and HTTP/2 specs.
If the client supports only HTTP/1.1, it works too. Test it on firefox, you will see !!!

4. Junit 5.x and Spring boot 2.x

With Spring boot 2.x and introduction of Junit 5, integration of these tools has changed.
I am not specifically trying to answer how to write junit 5 test cases, there are plenty of examples in internet, 
but I am trying to provide a mechanism to integrate with surefire and failsafe plugin.

5. Spring boot tests

In this example, all integration tests were written using Spring boot test.
To me if you are using Spring boot, use their testing framework. It works so well !!!
If you are using intellij, a developer can simply right click on a spring boot test case and run in debug mode. Life is made simple !!!
In my journey, the integration with Junit 5, surefire and failsafe plugins were not straight forward.

6. Jacoco code coverage

In our CI model, we need to generate code coverage report which will give insight how effectively we have done testing.
If you are using multi module maven project, it is important to get a single coverage report.
This project attempts to build individual and combined (aggregate) jacoco code coverage report.

### Some key observations while working on this assignment

1. There is a reason, I have used specific version of surefire, failsafe plugins. 
When I built this project, the latest surefire and failsafe plugins were not seamlessly working with Spring Boot 2.x.

2. With JDK 10 modularization, a strict control has been imposed on what one module can export/import. 
This also applies to how reflections could be used by various mocking frameworks. If you notice carefully, in "module-info.java", I have used "opens". 
Read the official JDK 10 documentation and you will get to know its connection with mocking framework. 

3. If you start the spring boot application, it will run on HTTP/2. However when running integration test cases, I used HTTP/1.1.
When I built this project, the rest API testing framework "Rest Assured" was not ready for HTTP/2 specs. Hopefully that will be available.
I am particularly biased to Rest Assured as it gives "BDD" way of defining test cases. 
However one can use spring rest template along with "OK http3" to achieve the objective. I will live it to you as what best fits your requirement.
