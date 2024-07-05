# ToySwap Backend API

## Overview

ToySwap Backend API is a RESTful web 
service for managing a toy swapping platform. This API allows users to manage their  toy listings, User Profile, perform CRUD operations, and check the health of various components of the service.

## Table of Contents

- [Getting Started](#getting-started)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
    - [Prerequisites](#prerequisites)
    - [Installation Steps](#installation-steps)
    - [Running the Application](#running-the-application)
- [Endpoints](#endpoints)
    - [Listings](#listings)
    - [Members](#members)    
    - [Health](#health)
- [Caching](#caching)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Contact US](#contact-us)
- [License](#license)

## Getting Started

## Features

- Manage toy listings with CRUD operations
- Manage members with CRUD operations
- Exception handling and validation
- Health check endpoints for monitoring the status of the application
- Caching for improved performance 

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (for development and testing)
- Maven
- JUnit and Mockito for testing
- Postman for API testing

## Setup and Installation

### Prerequisites

- Java 21 or higher
- Maven 3.9.6 or higher
- PostgreSQL Database

### Installation Steps

1. **Clone the repository:**
   ```sh
   git clone https://github.com/Spring-Samurais/toy-swap-backend
   cd toy-swap-backend
   ```

2. **Install dependencies:**
   ```sh
   mvn clean install
   ```

### Running the Application

1. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```

2. **Access the application:**
    - The API will be available at `http://address`

## Endpoints

### Listings

- **Get All Listings**
    - **Endpoint:** `GET /api/v1/listings`
    - **Description:** Retrieve all listings.
    - **Response:**
      ```json
      [
        {
          "id": 10,
          "title": "Sample",
          "member": {
            "id": 2,
            "name": "export the data",
            "nickname": "",
            "location": "loc"
          },
          "category": " ",
          "description": " ",
          "condition": " ",
          "statusListing": " "
        },
        ...
      ]
      ```

- **Get Listing by ID**
    - **Endpoint:** `GET /api/v1/listings/{listingID}`
    - **Description:** Retrieve a listing by ID.
    - **Response:**
      ```json
      {
        "id": 10,
        "title": "actual data",
        "member": {
          "id": 2,
          "name": "actual data",
          "nickname": " ",
          "location": " "
        },
        "category": "actual data",
        "description": " .",
        "condition": " ",
        "statusListing": " "
      }
      ```

- **Add Listing**
    - **Endpoint:** `POST /api/v1/listings`
    - **Description:** Add a new listing.
    - **Request Body:**
      ```json
      {
        "title": " ",
        "memberId": 2,
        "category": " ",
        "description": " ",
        "condition": " ",
        "statusListing": " "
      }
      ```

- **Update Listing**
    - **Endpoint:** `PUT /api/v1/listings/{listingID}`
    - **Description:** Update an existing listing.
    - **Request Body:**
      ```json
      {
        "title": "actual data",
        "memberId": 2,
        "category": "actual data",
        "description": "actual data",
        "condition": "actual data",
        "statusListing": "actual data"
      }
      ```

- **Delete Listing**
    - **Endpoint:** `DELETE /api/v1/listings/{listingID}`
    - **Description:** Delete a listing by ID.


### Members

- **Get All Members**
    - **Endpoint:** `GET /api/v1/members`
    - **Description:** Retrieve all members.
    - **Response:**
      ```json
      [
        {
          "id": 1,
          "name": " ",
          "nickname": " ",
          "location": " ",
          "listings": [
            {
              "id": 10,
              "title": " "
            },
            ...
          ]
        },
        ...
      ]
      ```

- **Get Member by ID**
    - **Endpoint:** `GET /api/v1/members/{memberID}`
    - **Description:** Retrieve a member by ID.
    - **Response:**
      ```json
      {
        "id": 1,
        "name": " ",
        "nickname": " ",
        "location": " ",
        "listings": [
          {
            "id": 10,
            "title": " "
          },
          ...
        ]
      }
      ```

- **Add Member**
    - **Endpoint:** `POST /api/v1/members`
    - **Description:** Add a new member.
    - **Request Body:**
      ```json
      {
        "name": " ",
        "nickname": " ",
        "location": " "
      }
      ```

- **Update Member**
    - **Endpoint:** `PUT /api/v1/members/{memberID}`
    - **Description:** Update an existing member.
    - **Request Body:**
      ```json
      {
        "name": " ",
        "nickname": " ",
        "location": " "
      }
      ```

- **Delete Member**
    - **Endpoint:** `DELETE /api/v1/members/{memberID}`
    - **Description:** Delete a member by ID.


### Health

- **Health Check for Members**
    - **Endpoint:** `GET /health/members`
    - **Description:** Health check for members.
    - **Response:**
      ```json
      {
        "status": "UP",
        "details": {
          "members": {
            "status": "UP",
            "count": 5
          }
        }
      }
      ```

- **Health Check for Listings**
    - **Endpoint:** `GET /health/listings`
    - **Description:** Health check for listings.
    - **Response:**
      ```json
      {
        "status": "UP",
        "details": {
          "listings": {
            "status": "UP",
            "count": 10
          }
        }
      }
      ```

## Caching

Caching is implemented using Spring Cache with a `ConcurrentMapCacheManager`. The following methods are cached:

- **Members**:
    - `getAllMembers`
    - `getMemberByID`

- **Listings**:
    - `getAllListings`
    - `getListingByID`

### Example Cache Configuration

```java
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("listings", "members");
    }
}
```

### Error Handling
Custom exceptions are used to handle errors in the application:

- CommentFailedToSaveException
- CommentNotFoundException
- ImageFailedToUpload
- InvalidListingException
- ListingNotFoundException
- ListingFailedToSaveException
- MemberNotFoundException

 
These exceptions are handled globally using 

@ControllerAdvice and @ExceptionHandler.

Example Global Exception Handler

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ListingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleListingNotFoundException(ListingNotFoundException e, WebRequest request) {
        logger.error("ListingNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Listing Not Found", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException e, WebRequest request) {
        logger.error("MemberNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Member Not Found", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ListingFailedToSaveException.class)
    public ResponseEntity<ErrorResponse> handleListingFailedToSaveException(ListingFailedToSaveException e, WebRequest request) {
        logger.error("ListingFailedToSaveException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Listing Failed to Save", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
```
### Testing
Tests are written using JUnit and Mockito.

Cache functionality is verified using Mockito.verify.

[//]: # (Example Test for Caching)

```java
@SpringBootTest
@DataJpaTest
class ListingServiceImplementationTest {

    @Mock
    private ListingRepository listingRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberServiceImplementation memberServiceImplementation;
    @Mock
    private S3Service s3Service;

    @InjectMocks
    private ListingServiceImplementation serviceImplementation;

    private Member memberOne;
    private List<Listing> listings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberOne = new Member(1L, "Test Member", "member", "location", null);

        Listing listing = new Listing(1L, "A listing test", LocalDateTime.now(), Category.ACTION_FIGURES, "I am a description :-)", ItemCondition.GOOD, Status.AVAILABLE, memberOne, null, null);
        Listing listing2 = new Listing(2L, "Another listing test", LocalDateTime.now(), Category.DOLLS, "Description2", ItemCondition.LIKE_NEW, Status.PENDING, memberOne, null, null);
        Listing listing3 = new Listing(3L, "Third listing test", LocalDateTime.now(), Category.CONSTRUCTION_TOYS, "Description3", ItemCondition.LIKE_NEW, Status.SWAPPED, memberOne, null, null);
        Listing listing4 = new Listing(4L, "Fourth listing test", LocalDateTime.now(), Category.VEHICLES, "Description4", ItemCondition.USED, Status.AVAILABLE, memberOne, null, null);
        Listing listing5 = new Listing(5L, "Fifth listing test", LocalDateTime.now(), Category.EDUCATIONAL_TOYS, "Description5", ItemCondition.DAMAGED, Status.PENDING, memberOne, null, null);
        listings = Arrays.asList(listing, listing2, listing3, listing4, listing5);
    }

    @Test
    void getAllListings() {
        when(listingRepository.findAll()).thenReturn(listings);

        List<Listing> result = serviceImplementation.getAllListings();

        assertEquals(5, result.size());
        assertEquals("A listing test", result.get(0).getTitle());
        assertEquals("I am a description :-)", result.get(0).getDescription());
        assertEquals("Another listing test", result.get(1).getTitle());
    }
}

```

### Contact US
For any questions or suggestions, feel free to contact us:

Your Name
- Email: < Email Address >
- GitHub: Spring-Samurais

### License
< Add License details>