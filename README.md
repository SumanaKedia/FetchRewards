# FetchRewards
Receipt Processor Challenge

# Receipt Processor

## Overview
The Receipt Processor is a web service that processes receipts and calculates points based on predefined rules. It provides two main endpoints:
1. **Process Receipts**: Accepts a receipt JSON in body and returns a unique ID.
2. **Get Points**: Retrieves the number of points awarded for a receipt based on its ID.

## API Specification
The API endpoints are documented below:

### 1. Process Receipts
- **Endpoint**: `/receipts/process`
- **Method**: `POST`
- **Request Payload**:
  ```json
  {
    "retailer": "Retailer Name",
    "purchaseDate": "YYYY-MM-DD",
    "purchaseTime": "HH:MM",
    "items": [
      {
        "shortDescription": "Item Description",
        "price": "Price Amount"
      }
    ],
    "total": "Total Amount"
  }
- **Response**:
  {
  "id": "unique-receipt-id"
  }
  
### 2. Get Points
- **Endpoint**: ` /receipts/{id}/points
- **Method**:: GET
- **Response**:
{
  "points": Number
}

### Points Calculation Rules
**Points are awarded based on the following criteria:**
- **1 point for every alphanumeric character in the retailer name.**
- **50 points if the total is a round dollar amount with no cents.**
- **25 points if the total is a multiple of 0.25.**
- **5 points for every two items on the receipt.**
- **For item descriptions that are multiples of 3 characters, the price is multiplied by 0.2, rounded up, and added to the points.**
- **6 points if the purchase date is an odd day.**
- **10 points if the purchase time is between 2:00 PM and 4:00 PM.**

## Examples

### Example Request

```json
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {"shortDescription": "Mountain Dew 12PK", "price": "6.49"},
    {"shortDescription": "Emils Cheese Pizza", "price": "12.25"},
    {"shortDescription": "Knorr Creamy Chicken", "price": "1.26"},
    {"shortDescription": "Doritos Nacho Cheese", "price": "3.35"},
    {"shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ", "price": "12.00"}
  ],
  "total": "35.35"
}
```

### Example Response
```json
{
  "id": "7fb1377b-b223-49d9-a31a-5a02701dd310"
}
```

## Getting Started

### Prerequisites

Ensure that you have Docker installed on your machine.

### Running the Application

1. Clone the repository:

    ```bash
    git clone https://github.com/SumanaKedia/FetchRewards.git
    cd ReceiptProcessor
    ```

2. **Using Docker:** If you prefer to use Docker, you can build and run the application using the following commands:

    ```bash
    docker build -t receipt-processor .
    docker run -p 8080:8080 receipt-processor
    ```

3. **Without Docker:** If you wish to run it directly using Maven

    ```bash
     mvn clean package
     mvn spring-boot:run
    ```
4. **Run with test:** If you wish to run test while build make changes in docker file:
    #### RUN mvn clean package -DskipTests to
    ```bash
    RUN mvn clean package
    ```
   
The application will be running on [http://localhost:8080](http://localhost:8080).


## Testing the Endpoints

You can test the API using tools like Postman or curl.

### To process a receipt:

```bash
curl -X POST http://localhost:8080/receipts/process -H "Content-Type: application/json" -d '{...}'
```
```bash
curl http://localhost:8080/receipts/{id}/points
```

#### Feel free to replace the `{...}` with an actual JSON payload for processing receipts when you're ready.

# Features

### REST API with Spring Boot
Implements RESTful endpoints with Spring Boot for efficient receipt processing and management.

### Global Exception Handling
Centralized error-handling for consistent and user-friendly API error responses.

### Environment Profiling for Development and Production
Separates configurations for `dev` and `prod` using Spring profiles for enhanced flexibility and security.

### Aspect-Oriented Programming (AOP) for Logging
AOP adds logging across key application points without cluttering business logic, enabling detailed insights.

### Spring Boot Actuator
Integrates Actuator for monitoring health, metrics, and application statistics with endpoints like `/actuator/health`.

### Unit Testing with JUnit and Mockito
Comprehensive unit tests for services and controllers with high coverage, using Mockito to mock dependencies and verify responses.

### Docker Multi-Stage Build
Uses Docker multi-stage build to streamline containerization, separating build and runtime stages for optimized image size and performance.

### H2 In-Memory Database
Implements H2 in-memory database for fast, lightweight data storage during development and testing, ensuring quick setup and teardown.




