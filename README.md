# product-service

Spring Boot product service with MongoDB.

## How to run

### Prerequisites
- Java 17+
- Maven
- MongoDB (or use Docker Compose)

### Local Development
1. Start MongoDB:
   ```bash
   docker-compose up -d mongodb-db
   ```

2. Build and run:
   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

The service will run on `http://localhost:8080`

### Docker
- Build: `docker build -t product-service .`
- Compose: `docker-compose up --build`

## API Usage

**Important:** All API requests require the `X-API-Version: 1.0` header.

### Example Requests

```bash
# List products
curl -H "X-API-Version: 1.0" http://localhost:8080/api/products

# Get product by ID
curl -H "X-API-Version: 1.0" http://localhost:8080/api/products/{id}

# Create product
curl -X POST -H "X-API-Version: 1.0" -H "Content-Type: application/json" \
  -d '{"name":"Product Name","category":"Category","brand":"Brand","price":99.99}' \
  http://localhost:8080/api/products
```

### Available Endpoints
- `GET /api/products` - List products (with filters, sorting, pagination)
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product (soft delete)
- `POST /api/products/{id}/images` - Upload product images
- `POST /api/products/{id}/related` - Link related products
