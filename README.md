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

Ensure that you have Java JDK 17, Maven and Docker installed on your machine.

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

### Contribution
Feel free to fork the repository and submit pull requests.





