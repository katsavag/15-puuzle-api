# 🧩 15 Puzzle Game API

This project is a RESTful API for the 15 Puzzle game. The 15 Puzzle is a sliding puzzle that consists of a frame of numbered square tiles in random order with one tile missing. The goal is to place the tiles in order by making sliding moves that use the empty space. More about the game can be found [here](https://en.wikipedia.org/wiki/15_puzzle).

## 🛠️ Technologies Used

- Java 17
- Spring Boot 3.2.5
- Springdoc OpenAPI UI for API documentation
- Lombok for reducing boilerplate code
- Spring Boot Starter Test for testing

## 🚀 Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 17
- Maven
- Docker (for deployment)

### Installing

1. Clone the repository
```
git clone https://github.com/katsavag/15-puzzle-api.git
```
2. Navigate to the project directory
```
cd 15-puzzle-api
```
3. Build the project with Maven
```
mvn clean install
```
4. Run the application
```
java -jar target/15-puzzle-api-0.0.1-SNAPSHOT.jar
```
The application will start running at `http://localhost:8080`.

### Running the tests

To run the tests, use the following Maven command:
```
mvn test
```

## 📚 API Documentation

Once the application is running, you can view the API documentation at `http://localhost:8080/swagger-ui.html`.

## 🐳 Deployment with Docker

1. Build the Docker image
```
docker build -t 15-puzzle-api .
```
2. Run the Docker container
```
docker run -p 8080:8080 15-puzzle-api
```
The application will start running at `http://localhost:8080`.

## 👥 Authors

- **Evangelos Katsadouros** - *Initial work* - [katsavag](https://github.com/katsavag)
