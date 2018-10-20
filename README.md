# rest


Test the service
Now that the service is up, visit http://localhost:8080/greeting, where you see:

{"id":1,"content":"Hello, World!"}
Provide a name query string parameter with http://localhost:8080/greeting?name=User. Notice how the value of the content attribute changes from "Hello, World!" to "Hello User!":


{"id":2,"content":"Hello, User!"}
