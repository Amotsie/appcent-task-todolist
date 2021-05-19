# Appcent-task-todolist

A todo list API built using SpringBoot (Maven). The app allows the registerd user to create and mange tasks easilu byoffering basic CRUD opreations.
The todo app is implemented using the in memory H2 databse that is accessed via JDBC. The exposed  swagger documented RESTFUL API endpoinds can be accsessed
vis the link ***"http://localhost:8080/swagger-ui/"*** when the app is upp and running.

### Build and Run.
The app containerized image of the app is availble on DockerHub, to run the app locally:
- $ docker run -p 8080:8080 amotsie/spring-todolist 
- the app should run on http://localhost:8080/ with all the dependcies installed. 
## Entities
#### User
( ***long*** id,
***String*** username,
 ***String*** email,
***String*** password)

#### Task
( ***long*** id,
***String*** description,
 ***Boolean*** complete,
***long*** userid FK)

## REST Endpoints Overview
### Users
 |  Request Mapping       |        Type           |     Description               |           Return            |
 |---------------         |       ---------------  |    -----------------         |   -----------------         | 
 | /api/users/register    |           POST         |  Registers/create a new user |        A Jason Web Token    |
 | /api/users/login       |           POST         |  Registers/create a new user  |             A JWT           |

 
 ### Tasks
 
  |  Request Mapping      |        Type           |     Description                           |                     Return                |
 |---------------         |       ---------------  |    -----------------                     |           -----------------                | 
 | /api/todos             |           GET         |  Retrives a list of all the tasks of the current logged in user|  A JSON list of tasks  |
 | /api/todos             |           POST         |  Creates and stored a task of the current in user   |     JSON of a newly created task       |
 | /api/todos/{taskid}   |           GET         |  Retrieves the task with the given ID   |   single result JSON task of the mentioned ID       |
 | /api/todos/{taskid}   |           PUT         |  Updates the task with the given ID   |   Success or fail string message       |
 | /api/todos/{taskid}   |           Delete        |  Deletes the task with the given ID   |   Success or fail string message       |
   
 
 ## Testing using Postman
 The routes of the Todos/Tasks are protected and therefore the JWT should always be included as Authorization Bearer in the header.
 
 ![Capture](https://user-images.githubusercontent.com/57603284/118839163-f4840a80-b8ce-11eb-9639-1d6ffe1f7275.PNG)
 
 The following users are pre-build in the database for testing purposes.
 - Emai: **John@gmail.com** Password: **1234**;
 - Emai: **Doe@gmail.com** Password: **1234**;
 
## Sample request and response
![Capture](https://user-images.githubusercontent.com/57603284/118839644-55134780-b8cf-11eb-9f13-481fa2aa2dde.PNG)
 
 ### Remarks
 It has been an amazing strugglr for me to try and fit the given tech stack into this application.  Althought the final product doesnt contain Mockito test cases and Couchbase databse,
 during the course of the task I tried them plenty, even if at the end I couldnt get them to function as I want in the project. I also used Spring Data JPA but because of time I dropped it as it was giving me nested exception errors towards the end.  
 I have have built a frontend React todo list previously ***https://r3act-t4sk-li5t.netlify.app*** and therefore for this task I put a lot of effort into trying out other stuff that I dont know, I even used Docker for the first time on this task.😀 
