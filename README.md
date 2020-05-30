# Calculate Sum on simple Java web server

Calculate sum on simple java web server using Socket

## Tools Used: 
- Java 11
- slf4j library

## Task

- Under package `com.fortump.httpserver`, execute the main class HttpServer.java.
  This will start the server on port 8081.
- Now, try executing the curl commands on different terminals with different or same payloads
  like below <br/>
   `curl -d 1 http://localhost:8081/`, <br/>
    `curl -d 3 http://localhost:8081/` <br/>
    `curl -d 6 http://localhost:8081/`, <br/>
    `curl -d 3 http://localhost:8081/`, <br/>
    `curl -d end http://localhost:8081/`, <br/>
- Observe there result in each terminal after the execution of `curl -d end http://localhost:8081/` command.
- I have also written a script which will execute 20 curl command at once in TestApplication.java in test folder.
- In TestApplication.java, I placed sleep() method and that is optional which can be removed for testing async results.
- On testing async result i.e after removing sleep method, everytime the test gets executed, the console may give different results since,
  the thread doesn't guarantees the order of execution. However, the result will be consistent for all the requests before each "end" curl payload.

## Assumption

- I assume that I am free to use any web server as engine, so I used ServerSocket in this case.