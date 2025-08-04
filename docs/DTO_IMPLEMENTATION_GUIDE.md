# DTO Implementation Guide for Notification Class

## 📋 Overview

This guide explains how the DTO (Data Transfer Object) pattern is implemented for the Notification class in the car hire application. The DTO approach provides type safety, prevents circular references, and creates a clear separation between API contracts and domain models.

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client App    │    │   Controller    │    │   Service Layer │
│                 │    │                 │    │                 │
│ JSON Request    │───▶│ NotificationDTO │───▶│ Domain Object   │
│                 │    │                 │    │                 │
│ JSON Response   │◀───│ NotificationDTO │◀───│ Domain Object   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌─────────────────┐
                       │   Mapper        │
                       │                 │
                       │ toDTO()         │
                       │ toDomain()      │
                       └─────────────────┘
```

## 📁 File Structure

```
src/main/java/za/co/carhire/
├── controller/
│   └── reservation/
│       └── NotificationController.java     # REST endpoints using DTOs
├── dto/
│   ├── NotificationDTO.java               # Data Transfer Object
│   └── UserDTO.java                       # User DTO (for reference)
├── mapper/
│   └── NotificationMapper.java            # Converts between DTO and Domain
└── domain/
    └── reservation/
        └── Notification.java              # Domain object (unchanged)

src/test/java/za/co/carhire/
└── controller/
    └── reservation/
        └── NotificationControllerTest.java # Tests using DTOs
```

## 🔧 Key Components

### 1. NotificationDTO.java
**Purpose**: Defines the API contract for notification data

**Key Features**:
- Type-safe fields with proper data types
- Clear separation from domain object
- Includes user information without circular references
- Ready for JSON serialization/deserialization

**Fields**:
```java
private Integer notificationID;  // Primary key
private String message;          // Notification content
private Date dateSent;           // Timestamp
private String status;           // Status (BOOKED, CANCELLED, etc.)
private Integer userId;          // User reference (ID only)
private String userName;         // User name for display
```

### 2. NotificationMapper.java
**Purpose**: Converts between domain objects and DTOs

**Key Methods**:
- `toDTO(Notification)` - Converts domain object to DTO
- `toDomain(NotificationDTO)` - Converts DTO to domain object
- `toDTOList(List<Notification>)` - Batch conversion for lists
- `toDomainList(List<NotificationDTO>)` - Batch conversion for lists

**Benefits**:
- Prevents circular reference issues
- Provides type safety
- Centralizes conversion logic
- Easy to maintain and extend

### 3. NotificationController.java
**Purpose**: REST API endpoints using DTOs

**Endpoints**:
- `POST /api/notifications` - Create notification
- `GET /api/notifications/{id}` - Get single notification
- `GET /api/notifications` - Get all notifications
- `DELETE /api/notifications/{id}` - Delete notification

**Flow Example**:
```java
@PostMapping
public NotificationDTO create(@RequestBody NotificationDTO dto) {
    // 1. Convert DTO to domain object
    Notification notification = NotificationMapper.toDomain(dto);
    
    // 2. Save using service layer
    Notification created = service.save(notification);
    
    // 3. Convert back to DTO for response
    return NotificationMapper.toDTO(created);
}
```

## 🔄 Data Flow Examples

### Creating a Notification

**1. Client Request (JSON)**:
```json
{
  "message": "Your booking is confirmed",
  "status": "BOOKED",
  "userId": 123,
  "userName": "John Doe"
}
```

**2. Controller Processing**:
```java
// DTO is automatically deserialized from JSON
NotificationDTO dto = // from request body

// Convert to domain object
Notification notification = NotificationMapper.toDomain(dto);

// Save to database
Notification saved = service.save(notification);

// Convert back to DTO for response
return NotificationMapper.toDTO(saved);
```

**3. Client Response (JSON)**:
```json
{
  "notificationID": 1,
  "message": "Your booking is confirmed",
  "dateSent": "2025-01-15T10:30:00",
  "status": "BOOKED",
  "userId": 123,
  "userName": "John Doe"
}
```

### Getting All Notifications

**1. Controller Processing**:
```java
// Get all domain objects from database
List<Notification> notifications = service.findAll();

// Convert each to DTO
return NotificationMapper.toDTOList(notifications);
```

**2. Client Response (JSON)**:
```json
[
  {
    "notificationID": 1,
    "message": "Booking confirmed",
    "dateSent": "2025-01-15T10:30:00",
    "status": "BOOKED",
    "userId": 123,
    "userName": "John Doe"
  },
  {
    "notificationID": 2,
    "message": "Payment received",
    "dateSent": "2025-01-15T11:00:00",
    "status": "PAID",
    "userId": 124,
    "userName": "Jane Smith"
  }
]
```

## 🧪 Testing with DTOs

### Test Structure
```java
@SpringBootTest
@AutoConfigureWebMvc
class NotificationControllerTest {
    
    @Test
    void testCreateNotificationWithValidData() throws Exception {
        // 1. Create test user
        User user = createTestUser("test@example.com", 123456789L);
        Integer userId = createUserAndGetId(user);
        
        // 2. Create DTO for request
        NotificationDTO dto = createNotificationDTO(userId, "Test message", "BOOKED");
        
        // 3. Send POST request
        mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Test message"))
                .andExpect(jsonPath("$.status").value("BOOKED"))
                .andExpect(jsonPath("$.userId").value(userId));
    }
}
```

### Helper Methods
```java
private NotificationDTO createNotificationDTO(Integer userId, String message, String status) {
    return new NotificationDTO(
        null,           // ID will be generated
        message,        // Notification content
        new Date(),     // Current timestamp
        status,         // Status
        userId,         // User ID
        "Test User"     // User name
    );
}
```

## ✅ Benefits of DTO Approach

### 1. Type Safety
- Compile-time checking of data structure
- IDE autocomplete and refactoring support
- Clear API contracts

### 2. No Circular References
- DTOs only include necessary fields
- No infinite loops during JSON serialization
- Clean, flat structure

### 3. Separation of Concerns
- API layer separate from domain layer
- Easy to change API without affecting domain
- Clear boundaries between layers

### 4. Easy to Extend
- Add new fields to DTO without affecting domain
- Version API contracts easily
- Backward compatibility

### 5. Better Testing
- Type-safe test data creation
- Clear test expectations
- Easy to mock and verify

## 🚀 Usage Guidelines

### When to Use DTOs
- ✅ REST API endpoints
- ✅ External system integration
- ✅ Complex data transformations
- ✅ API versioning requirements

### When NOT to Use DTOs
- ❌ Simple internal operations
- ❌ Database-only operations
- ❌ Small applications with simple requirements

### Best Practices
1. **Keep DTOs Simple**: Only include necessary fields
2. **Use Meaningful Names**: Clear field and method names
3. **Document Everything**: Comments explain purpose and usage
4. **Test Thoroughly**: Test both valid and invalid scenarios
5. **Version Carefully**: Plan for API evolution

## 🔧 Maintenance

### Adding New Fields
1. Add field to DTO class
2. Update mapper methods
3. Update tests
4. Update documentation

### Changing Field Types
1. Update DTO field type
2. Update mapper conversion logic
3. Update tests with new data types
4. Consider backward compatibility

### Performance Considerations
- DTOs add slight overhead due to conversion
- Use batch operations for large datasets
- Consider caching for frequently accessed data

## 📚 Related Documentation

- [Spring Boot REST Controllers](https://spring.io/guides/gs/rest-service/)
- [Jackson JSON Processing](https://github.com/FasterXML/jackson)
- [JUnit Testing](https://junit.org/junit5/)
- [MockMvc Testing](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework)

---

**Author**: Bonga Velem (220052379)  
**Date**: 18 May 2025  
**Version**: 1.0 