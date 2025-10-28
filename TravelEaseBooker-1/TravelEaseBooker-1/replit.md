# Travel Booking System

## Overview
A comprehensive Java-based travel booking system built with Spring Boot. The system enables users to search and book flights, hotels, and transportation services through an intuitive web interface.

## Project Architecture

### Technology Stack
- **Backend**: Spring Boot 3.1.5 with Java 17
- **Database**: H2 in-memory database
- **ORM**: Spring Data JPA with Hibernate
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Build Tool**: Maven
- **Server Port**: 5000

### Project Structure
```
src/
├── main/
│   ├── java/com/travelbooking/
│   │   ├── TravelBookingApplication.java (Main application)
│   │   ├── model/ (Domain entities)
│   │   │   ├── Flight.java
│   │   │   ├── Hotel.java
│   │   │   ├── Transportation.java
│   │   │   └── Booking.java
│   │   ├── repository/ (Data access layer)
│   │   │   ├── FlightRepository.java
│   │   │   ├── HotelRepository.java
│   │   │   ├── TransportationRepository.java
│   │   │   └── BookingRepository.java
│   │   ├── service/ (Business logic)
│   │   │   ├── FlightService.java
│   │   │   ├── HotelService.java
│   │   │   ├── TransportationService.java
│   │   │   └── BookingService.java
│   │   ├── controller/ (REST API endpoints)
│   │   │   ├── FlightController.java
│   │   │   ├── HotelController.java
│   │   │   ├── TransportationController.java
│   │   │   └── BookingController.java
│   │   └── config/
│   │       └── DataInitializer.java (Mock data setup)
│   └── resources/
│       ├── application.properties (App configuration)
│       └── static/ (Web interface)
│           ├── index.html
│           ├── styles.css
│           └── app.js
```

## Features

### MVP Features (Current)
1. **Flight Search & Booking**
   - Search by origin and destination
   - Filter by date range and price
   - View flight details (airline, times, cabin class, availability)
   - Book flights with confirmation

2. **Hotel Search & Booking**
   - Search by location
   - Filter by star rating and price
   - View amenities and room types
   - Book hotel rooms with confirmation

3. **Transportation Search & Booking**
   - Search by location and vehicle type
   - View vehicle specifications and features
   - Book rental vehicles with confirmation

4. **Booking Management**
   - Generate unique booking reference numbers
   - View bookings by email or reference
   - Cancel existing bookings
   - Track booking status (CONFIRMED/CANCELLED)

5. **Mock Data Service**
   - Pre-populated sample flights (8 destinations)
   - Pre-populated sample hotels (8 locations)
   - Pre-populated sample transportation options

### REST API Endpoints

**Flights**
- `GET /api/flights` - Get all flights
- `GET /api/flights/{id}` - Get flight by ID
- `GET /api/flights/search?origin=X&destination=Y` - Search flights
- `GET /api/flights/search/price?maxPrice=X` - Filter by price

**Hotels**
- `GET /api/hotels` - Get all hotels
- `GET /api/hotels/{id}` - Get hotel by ID
- `GET /api/hotels/search?location=X&minStars=Y` - Search hotels
- `GET /api/hotels/search/price?maxPrice=X` - Filter by price

**Transportation**
- `GET /api/transportation` - Get all transportation
- `GET /api/transportation/{id}` - Get transportation by ID
- `GET /api/transportation/search?location=X&type=Y` - Search transportation
- `GET /api/transportation/search/type?type=X` - Filter by type

**Bookings**
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `GET /api/bookings/reference/{reference}` - Get booking by reference
- `GET /api/bookings/customer/{email}` - Get bookings by customer email
- `POST /api/bookings` - Create new booking
- `PUT /api/bookings/cancel/{reference}` - Cancel booking

## Recent Changes
- **October 26, 2025**: Initial project setup
  - Created Spring Boot application structure
  - Implemented domain models with JPA entities
  - Created repository layer with custom query methods
  - Implemented service layer with business logic
  - Created REST API controllers with CORS support
  - Built responsive web interface with search and booking functionality
  - Added data initialization service with mock travel data
  - Configured H2 in-memory database

## Next Phase Features (Planned)
1. **Real API Integration**
   - Integrate with flight booking APIs (e.g., Amadeus, Skyscanner)
   - Connect to hotel booking platforms (e.g., Booking.com API)
   - Add real-time availability checks

2. **Payment Processing**
   - Integrate Stripe for secure payments
   - Add payment confirmation workflow
   - Support multiple payment methods

3. **User Authentication**
   - User registration and login
   - Secure booking history
   - Profile management
   - Password recovery

4. **Enhanced Features**
   - Email confirmation system
   - Booking modification workflow
   - Multi-leg trip planning
   - Price comparison tools
   - User reviews and ratings

5. **Database**
   - Migrate to PostgreSQL for production
   - Implement database migrations
   - Add data persistence

## Running the Application
The application runs on port 5000 and can be accessed at the Replit webview URL.

## Database Access
H2 Console is available at: `/h2-console`
- JDBC URL: `jdbc:h2:mem:travelbooking`
- Username: `sa`
- Password: (empty)

## User Preferences
- Language: Java
- Framework: Spring Boot
- Database: H2 (development), PostgreSQL (planned for production)
- Frontend: Vanilla JavaScript (no framework dependencies)
