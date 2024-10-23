# Tarantool Client Java Example

This project is an example service for working with the Tarantool database using the tarantool-client library in a Java Spring application. The service demonstrates basic CRUD operations and pagination with Tarantool. Note that the tarantool-client library is a commercial product provided with Tarantool Enterprise.

## Project Structure

- **Application**: Main entry point of the Spring Boot application.
- **Configuration**: Tarantool client configuration setup.
- **Controller**: RESTful endpoints for managing products.
- **DTO**: Data Transfer Objects for API requests and responses.
- **Exception Handling**: Global exception handling for the application.
- **Model**: Represents the Product entity.
- **Service**: Business logic for product management.
- **Test**: Unit tests for the controller.

## Getting Started

### Prerequisites

- Java 21
- Gradle
- Tarantool server

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/bkudaibergenov/tarantool-client-java-example.git
    cd tarantool-client-java-example
    ```

2. Configure the `application.yml` file with your Tarantool database settings:
    ```yaml
    tarantooldb:
      user: your_user
      password: your_password
      nodes:
        - port: 3301
          tag: main
    ```

3. Build the project:
    ```sh
    ./gradlew build
    ```

4. Run the application:
    ```sh
    ./gradlew bootRun
    ```

### Usage

The service exposes a set of RESTful endpoints to manage products.

- **Get all products**: `GET /api/v1/products`
- **Get products with pagination**: `GET /api/v1/products/pagination?offset={offset}&limit={limit}`
- **Get product by ID**: `GET /api/v1/products/{id}`
- **Create or update a product**: `PUT /api/v1/products`
- **Create a new product**: `POST /api/v1/products`
- **Delete a product by ID**: `DELETE /api/v1/products/{id}`
- **Find products by name and/or description**: `GET /api/v1/products/search?name={name}&description={description}`
- **Delete all products**: `DELETE /api/v1/products/all`

### Testing

To run the unit tests, use:
```sh
./gradlew test
```

## Contact

For any questions or inquiries, you can reach me at [bkudaibergenov@gmail.com](mailto:bkudaibergenov@gmail.com).
