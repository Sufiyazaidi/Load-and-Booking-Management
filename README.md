# Load-and-Booking-Management
This Spring Boot API manages load shipments and booking operations for a logistics system. It allows shippers to create, update, and delete loads while transporters can book available loads. The API ensures proper validation and maintains business rules like preventing bookings for cancelled loads.

# 🚛 Load & Booking Management System

This is a backend REST API built with **Spring Boot** and **PostgreSQL** to manage load (cargo) posting and transporter bookings.

## 📌 Project Features

- Create, update, delete, and fetch **Loads** and **Bookings**
- Manage status transitions (`POSTED`, `BOOKED`, `CANCELLED`)
- Validation and error handling for business logic
- Supports filtering (e.g. by `shipperId`, `transporterId`, etc.)
- Logging and structured error responses
- Basic setup for testing with Postman

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot, Spring Web, Spring Data JPA
- **Database**: PostgreSQL
- **Validation**: Jakarta Bean Validation
- **Logging**: SLF4J + Logback
- **Testing**: Postman
- **Build Tool**: Maven

---

## 🧪 API Endpoints

### 🚚 Load Management

| Method | Endpoint               | Description                   |
|--------|------------------------|-------------------------------|
| POST   | `/load`                | Create a new load             |
| GET    | `/load`                | Get all loads (with filters)  |
| GET    | `/load/{id}`           | Get load by ID                |
| PUT    | `/load/{id}`           | Update load                   |
| DELETE | `/load/{id}`           | Delete load                   |

### 📦 Booking Management

| Method | Endpoint                  | Description                      |
|--------|---------------------------|----------------------------------|
| POST   | `/booking`                | Create a new booking             |
| GET    | `/booking`                | Get all bookings (with filters)  |
| GET    | `/booking/{id}`           | Get booking by ID                |
| PUT    | `/booking/{id}`           | Update booking                   |
| DELETE | `/booking/{id}`           | Delete booking                   |

---

## 🚦 Status Rules

### Load Status
- Default status when created → `POSTED`
- When a booking is made → `BOOKED`
- If a booking is deleted → `CANCELLED`

### Booking Status
- Default → `PENDING`
- If load is `CANCELLED` → Booking is **rejected**
- Can be updated to `ACCEPTED` or `REJECTED`

---



## ⚠️ Validation Rules

### ✅ General
- `weight` must be **> 0**
- `noOfTrucks` must be **≥ 1**
- Mandatory fields for:
  - **Load** → `shipperId`, `productType`, `truckType`, `facility` info
  - **Booking** → `loadId`, `transporterId`, `proposedRate`

### ❌ Restrictions
- Bookings **cannot** be created for loads with status `CANCELLED`

---

## 📦 Load Entity
```json
{
  "id": "UUID",
  "shipperId": "String",
  "facility": {
    "loadingPoint": "String",
    "unloadingPoint": "String",
    "loadingDate": "Timestamp",
    "unloadingDate": "Timestamp"
  },
  "productType": "String",
  "truckType": "String",
  "noOfTrucks": "int",
  "weight": "double",
  "comment": "String",
  "datePosted": "Timestamp",
  "status": "POSTED | BOOKED | CANCELLED"
}
```

- Default status: `POSTED`
- On booking: status → `BOOKED`
- On booking deletion: status → `CANCELLED`

---

## 📑 Booking Entity
```json
{
  "id": "UUID",
  "loadId": "UUID",
  "transporterId": "String",
  "proposedRate": "double",
  "comment": "String",
  "status": "PENDING | ACCEPTED | REJECTED",
  "requestedAt": "Timestamp"
}
```

- Booking only allowed if load is not `CANCELLED`
- Status updates to `ACCEPTED` when approved

---



## 🚀 Getting Started

### 1. Clone the Repo
```bash
git clone https://github.com/Sufiyazaidi/Load-and-Booking-Management.git
cd Load-and-Booking-Management
```


### 2. Configure PostgreSQL Database

- Create a PostgreSQL DB: `load_booking_db`
- Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/load_booking_db
spring.datasource.username=your_pg_username
spring.datasource.password=your_pg_password
spring.jpa.hibernate.ddl-auto=update
```

   Replace `your_pg_password` with your actual PostgreSQL password.

---


### 3. Build and Run the Application

```bash
mvn spring-boot:run
```

### 4. Testing with Postman

- Test endpoints by sending requests to `http://localhost:8080`
- Set `Content-Type: application/json` in headers

---

## 🧪 Sample Test Cases

| Test Scenario                                              | Expected Result         |
|------------------------------------------------------------|--------------------------|
| Create Load with valid data                                | 201 Created              |
| Create Load with missing fields                            | 400 Bad Request          |
| Create Booking for `CANCELLED` Load                        | 400 Bad Request          |
| Accept a Booking and verify status                         | 200 OK, status updated   |
| Delete a Booking                                           | 200 OK                   |
| Update Booking with new valid Load ID                      | 200 OK                   |
| Update Load with only one field (partial update)           | 200 OK                   |
| Get all loads/bookings with filters                        | 200 OK + filtered list   |
| Get Booking by invalid ID                                  | 404 Not Found            |





---

## 🧠 Assumptions

- Booking can only be created for existing, active (`POSTED`) loads.
- Deleting a booking sets the load status to `CANCELLED`.
- Booking status must be explicitly updated to `ACCEPTED` or `REJECTED`.
- Booking ID and Load ID must be UUID strings.

---



## 👩‍💻 Author

**Sufiya Zaidi**  
B.Tech CSE (ML & AI), Uttaranchal University  
📍 Dehradun, Uttarakhand  
GitHub: [@Sufiyazaidi](https://github.com/Sufiyazaidi)


---



