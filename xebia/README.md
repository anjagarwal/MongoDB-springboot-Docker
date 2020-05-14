# WriterPad Application

This is a WritedPad application is an article based application which has several API's like to add article,
to delete article, to list all the articles,etc. For adding and updating an article, I have used a third party 
API which will check the similarity of the body. This is a springboot application.

# Requirements:

* Java 8
* Maven
* MongoDB

# How to build: 

From Command Line
-----------------
    mvn clean install

## Docker

### How to build with Docker?

First start mongo db using below commands:
```bash
1) docker pull mongo:latest
2) docker run -d -p 27017:27017 --name mymongodb mongo:latest

```

After starting mongo db build the project through dockerfile using below command.

```bash
docker build -t xebia:1.0 .
```

### How to run with Docker?

```bash
docker run -p 8080:8080 --name xebia --link mymongodb:mongo -d xebia:1.0
```

Open a browser and hit http://localhost:8080/ .

### Endpoints with sample request/responses:

1) Create Article: This is a POST api to create/insert an article. 

* URL: [http://localhost:8080/api/articles](http://localhost:8080/api/articles)
* REQUEST: 
```bash
            {
                "title": "How to learn Spring Booot Online",
                "description": "Ever wonder how?",
                "body": "Do you get it?",
                "tags": ["java", "Spring Boot", "tutorial"]
            }
```
* RESPONSE:
```bash
            {
                "id": "5ebda493bd7350153b1aa0a5",
                "title": "How to learn Spring Booot Online",
                "description": "Ever wonder how?",
                "body": "Do you get it?",
                "tags": [
                    "java",
                    "Spring Boot",
                    "tutorial"
                ],
                "slug": "how-to-learn-spring-booot-online",
                "createdAt": "2020-05-14T20:05:39.979+00:00",
                "updatedAt": "2020-05-14T20:05:39.979+00:00",
                "favourited": false,
                "favouritesCount": 0
            }
```            
 
# For other requests attaching postman collection:
