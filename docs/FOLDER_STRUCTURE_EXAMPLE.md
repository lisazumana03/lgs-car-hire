# Complete Folder Structure for DTO Implementation

## 📁 Project Structure Overview

This document shows the complete folder structure for implementing DTOs in the car hire application, specifically for the Notification class.

```
lgs-car-hire.git/
├── 📄 pom.xml                                    # Maven dependencies
├── 📄 README.md                                  # Project documentation
├── 📁 docs/                                      # Documentation folder
│   ├── 📄 DTO_IMPLEMENTATION_GUIDE.md           # This guide
│   ├── 📄 FOLDER_STRUCTURE_EXAMPLE.md           # This file
│   └── 📄 API_DOCUMENTATION.md                  # API endpoints documentation
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📁 za/co/carhire/
│   │   │       ├── 📁 controller/               # REST Controllers
│   │   │       │   ├── 📁 authentication/
│   │   │       │   │   └── 📄 UserController.java
│   │   │       │   └── 📁 reservation/
│   │   │       │       └── 📄 NotificationController.java  # ✅ UPDATED with DTOs
│   │   │       ├── 📁 dto/                      # 🆕 NEW: Data Transfer Objects
│   │   │       │   ├── 📄 NotificationDTO.java  # 🆕 NEW: Notification DTO
│   │   │       │   └── 📄 UserDTO.java          # 🆕 NEW: User DTO (for reference)
│   │   │       ├── 📁 mapper/                   # 🆕 NEW: Mapper classes
│   │   │       │   └── 📄 NotificationMapper.java # 🆕 NEW: Converts DTO ↔ Domain
│   │   │       ├── 📁 domain/                   # Domain objects (unchanged)
│   │   │       │   ├── 📁 authentication/
│   │   │       │   │   └── 📄 User.java
│   │   │       │   └── 📁 reservation/
│   │   │       │       └── 📄 Notification.java
│   │   │       ├── 📁 factory/                  # Factory classes
│   │   │       │   ├── 📁 authentication/
│   │   │       │   │   └── 📄 UserFactory.java
│   │   │       │   └── 📁 reservation/
│   │   │       │       └── 📄 NotificationFactory.java
│   │   │       ├── 📁 repository/               # Data access layer
│   │   │       │   ├── 📁 authentication/
│   │   │       │   │   └── 📄 IUserRepository.java
│   │   │       │   └── 📁 reservation/
│   │   │       │       └── 📄 INotificationRepository.java
│   │   │       ├── 📁 service/                  # Business logic layer
│   │   │       │   ├── 📁 authentication/
│   │   │       │   │   ├── 📄 UserService.java
│   │   │       │   │   └── 📁 Impl/
│   │   │       │   │       └── 📄 UserServiceImpl.java
│   │   │       │   └── 📁 reservation/
│   │   │       │       ├── 📄 NotificationService.java
│   │   │       │       └── 📁 Impl/
│   │   │       │           └── 📄 NotificationServiceImpl.java
│   │   │       ├── 📁 util/                     # Utility classes
│   │   │       │   └── 📄 Helper.java           # ✅ UPDATED: Added ID validation
│   │   │       ├── 📄 LGsCarHireSpringMain.java # Main application class
│   │   │       └── 📄 Main.java                 # Alternative main class
│   │   └── 📁 resources/
│   │       └── 📄 application.properties        # Database configuration
│   └── 📁 test/
│       └── 📁 java/
│           └── 📁 za/co/carhire/
│               ├── 📁 controller/               # Controller tests
│               │   ├── 📁 authentication/
│               │   │   └── 📄 UserControllerTest.java
│               │   └── 📁 reservation/
│               │       └── 📄 NotificationControllerTest.java # ✅ UPDATED with DTOs
│               ├── 📁 factory/                  # Factory tests
│               │   ├── 📁 authentication/
│               │   │   └── 📄 UserFactoryTest.java
│               │   └── 📁 reservation/
│               │       └── 📄 NotificationFactoryTest.java
│               └── 📁 service/                  # Service tests
│                   ├── 📁 authentication/
│                   │   └── 📁 Impl/
│                   │       └── 📄 UserServiceImplTest.java
│                   └── 📁 reservation/
│                       └── 📁 Impl/
│                           └── 📄 NotificationServiceImplTest.java
└── 📁 target/                                   # Compiled classes (generated)
```

## 🔧 Key Changes Made

### ✅ **New Folders Created:**
- `src/main/java/za/co/carhire/dto/` - Data Transfer Objects
- `src/main/java/za/co/carhire/mapper/` - Mapper classes
- `docs/` - Documentation folder

### ✅ **New Files Created:**
- `NotificationDTO.java` - Notification DTO class
- `UserDTO.java` - User DTO class (for reference)
- `NotificationMapper.java` - Mapper for DTO conversions
- `DTO_IMPLEMENTATION_GUIDE.md` - Comprehensive guide
- `FOLDER_STRUCTURE_EXAMPLE.md` - This file

### ✅ **Files Updated:**
- `NotificationController.java` - Now uses DTOs instead of Maps
- `NotificationControllerTest.java` - Updated to test with DTOs
- `Helper.java` - Added South African ID validation

## 📋 File Descriptions

### 🆕 **New DTO Files:**

#### `NotificationDTO.java`
- **Purpose**: Defines API contract for notification data
- **Location**: `src/main/java/za/co/carhire/dto/NotificationDTO.java`
- **Key Features**: Type-safe fields, no circular references, JSON-ready

#### `UserDTO.java`
- **Purpose**: Defines API contract for user data
- **Location**: `src/main/java/za/co/carhire/dto/UserDTO.java`
- **Key Features**: Excludes password for security, includes essential user info

#### `NotificationMapper.java`
- **Purpose**: Converts between domain objects and DTOs
- **Location**: `src/main/java/za/co/carhire/mapper/NotificationMapper.java`
- **Key Methods**: `toDTO()`, `toDomain()`, `toDTOList()`, `toDomainList()`

### ✅ **Updated Files:**

#### `NotificationController.java`
- **Before**: Used `Map<String, Object>` for responses
- **After**: Uses `NotificationDTO` for type safety
- **Benefits**: Cleaner code, better error handling, type safety

#### `NotificationControllerTest.java`
- **Before**: Created test data using Maps
- **After**: Uses `NotificationDTO` objects for testing
- **Benefits**: Type-safe tests, better test data creation

## 🔄 Data Flow in New Structure

```
1. Client Request (JSON)
   ↓
2. NotificationController (receives NotificationDTO)
   ↓
3. NotificationMapper.toDomain() (converts DTO → Domain)
   ↓
4. NotificationService (business logic)
   ↓
5. INotificationRepository (database operations)
   ↓
6. NotificationService (returns Domain object)
   ↓
7. NotificationMapper.toDTO() (converts Domain → DTO)
   ↓
8. NotificationController (returns NotificationDTO)
   ↓
9. Client Response (JSON)
```

## 🧪 Testing Structure

### Test Files Organization:
```
src/test/java/za/co/carhire/
├── controller/
│   ├── authentication/
│   │   └── UserControllerTest.java
│   └── reservation/
│       └── NotificationControllerTest.java  # ✅ Updated with DTOs
├── factory/
│   ├── authentication/
│   │   └── UserFactoryTest.java
│   └── reservation/
│       └── NotificationFactoryTest.java
└── service/
    ├── authentication/
    │   └── Impl/
    │       └── UserServiceImplTest.java
    └── reservation/
        └── Impl/
            └── NotificationServiceImplTest.java
```

### Test Data Creation:
```java
// Before (using Maps):
Map<String, Object> notificationData = new HashMap<>();
notificationData.put("message", "Test message");
notificationData.put("status", "BOOKED");

// After (using DTOs):
NotificationDTO dto = new NotificationDTO(
    null, "Test message", new Date(), "BOOKED", userId, "Test User"
);
```

## 📚 Documentation Structure

```
docs/
├── DTO_IMPLEMENTATION_GUIDE.md      # Comprehensive DTO guide
├── FOLDER_STRUCTURE_EXAMPLE.md      # This file
└── API_DOCUMENTATION.md             # API endpoints documentation
```

## 🚀 Benefits of This Structure

### 1. **Clear Separation of Concerns**
- DTOs handle API contracts
- Domain objects handle business logic
- Mappers handle conversions

### 2. **Type Safety**
- Compile-time checking
- IDE autocomplete support
- Clear API contracts

### 3. **Easy Maintenance**
- Centralized conversion logic
- Easy to add new fields
- Clear file organization

### 4. **Better Testing**
- Type-safe test data
- Clear test expectations
- Easy to mock and verify

### 5. **Scalability**
- Easy to add new DTOs
- Consistent patterns
- Clear documentation

## 🔧 How to Use This Structure

### 1. **Creating a New DTO:**
```bash
# Create DTO file
touch src/main/java/za/co/carhire/dto/NewEntityDTO.java

# Create mapper file
touch src/main/java/za/co/carhire/mapper/NewEntityMapper.java

# Update controller
# Update tests
```

### 2. **Adding New Fields:**
```java
// 1. Add field to DTO
private String newField;

// 2. Update mapper methods
// 3. Update tests
// 4. Update documentation
```

### 3. **Running Tests:**
```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=NotificationControllerTest

# Run with coverage
mvn test jacoco:report
```

## 📝 Best Practices

### 1. **File Naming:**
- DTOs: `EntityNameDTO.java`
- Mappers: `EntityNameMapper.java`
- Tests: `EntityNameControllerTest.java`

### 2. **Package Organization:**
- Keep related files together
- Use consistent naming conventions
- Follow domain-driven design principles

### 3. **Documentation:**
- Comment all public methods
- Explain complex logic
- Keep documentation up to date

### 4. **Testing:**
- Test both valid and invalid scenarios
- Use helper methods for test data
- Test all endpoints thoroughly

---

**Author**: Bonga Velem (220052379)  
**Date**: 18 May 2025  
**Version**: 1.0 