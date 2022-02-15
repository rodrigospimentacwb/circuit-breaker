# Circuit Breaker - resilience4j

This POC (PROOF OF CONCEPT) demonstrates the use of the Circuit Breaker patter using resilience4j. For the tests, the OkHttp3 and RestTemplate clients were used.

## Technologies used

* Java 11
* Stubby4j
* Docker
* Resilience4j
* OkHttp3
* RestTemplate

## How prepare tests

The project configuration parameters are available in the "application.yml" file. There are also mock settings by stubby4j in the "service-stubs.yaml" file.

To run stubby4j:

    docker-compose up -d

To stop

     docker stop -t 0 $(docker ps -q) 

To clean all images in docker

    docker container prune --force && docker network prune --force && docker volume prune --force && docker ps && docker network ls && docker volume ls

## How to run the tests?

Just open one of the test classes according to the client you want, "OkHttpClientTest" or "RestTemplateTest" and run.

## Difference between clients

There is a difference between the two clients restTemplate and OkHttp. In restTemplate HTTP errors like 400 and 500 generate exceptions, in addition to timeouts and read timeouts. In the OKHttp3 client, the HTTP errors are encapsulated. For this reason, you will notice that the url for the tests is different.

## Thanks

Thanks to co-worker Marcos Vinícios for his help and teachings and to all my EBANX work team.

### Bibliographic reference:

* [Testing Microservices with Docker and Docker Compose] (Alexander Zagniotov) - feb, 2022.

* [Stubby4j] official documentation (Azagniotov) - feb, 2022.

* [CircuitBreaker] official documentation - feb, 2022.

* [[Resilience4J: Circuit Breaker Implementation on Spring Boot] (Pramuditya Ananta Nur) - feb, 2022.

Written in Feb 14, 2022 


[//]:#
[Testing Microservices with Docker and Docker Compose]:(https://alexanderzagniotov.medium.com/testing-microservices-in-docker-and-docker-compose-4dd54b02bd1c)
[Stubby4j]:(https://stubby4j.com)
[CircuitBreaker]:(https://github.com/gruppopam/stubby4j/blob/master/README.md#endpoint-configuration-howto)
[Resilience4J: Circuit Breaker Implementation on Spring Boot]:(https://medium.com/bliblidotcom-techblog/resilience4j-circuit-breaker-implementation-on-spring-boot-9f8d195a49e0)