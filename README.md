# Agriculture Management System 🌾

A JavaFX-based desktop application for managing agricultural operations, farmers, government resources, and subsidies.

## Features

### For Farmers 👨‍🌾
- ✅ Registration and Login
- 📊 View subsidy allocations
- 📬 Receive notifications from government
- 🆘 Request support
- 📋 Track support requests
- 🌤️ Weather updates
- 📈 Market price information
- 🌱 Crop recommendations based on soil type

### For Government 🏛️
- ✅ Registration and Login
- 👥 Manage farmer registrations
- 📦 Resource management and distribution
- 💰 Subsidy allocation
- 📊 Track crop production
- 📄 Generate reports
- 📧 Send notifications to farmers
- 💬 Respond to support requests

### Additional Features ⭐
- 🌤️ Weather forecasting (1-15 days)
- 📈 Market price tracking for 5 major crops (Wheat, Rice, Corn, Barley, Soybeans)
- 🌱 Crop recommendations based on soil type (Clay, Sandy, Loamy)
- 💬 Support request system with government responses
- 📊 Real-time data generation using multithreading

## Technologies Used
- **Language**: Java 11+
- **Framework**: JavaFX 19.0.2.1
- **Build Tool**: Maven
- **Design Patterns**: 
  - Inheritance (Base class for common properties)
  - Interface implementation (MarketServiceInterface)
  - Multithreading (Runnable for Weather and Market services)
  - Generic types (GenericList<T>)

## Requirements
- Java 11 or higher
- Maven 3.6+
- JavaFX (automatically handled by Maven)

## Installation & Setup

### Option 1: Using Maven (Recommended)

1. **Clone the repository**
```bash
   git clone https://github.com/YOUR_USERNAME/agriculture-management-system.git
   cd agriculture-management-system
```

2. **Build the project**
```bash
   mvn clean install
```

3. **Run the application**
```bash
   mvn javafx:run
```

That's it! Maven will automatically download JavaFX and all dependencies.

### Option 2: Manual Setup

1. **Install Java JDK 11+**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/)

2. **Download JavaFX SDK**
   - Go to [Gluon JavaFX Downloads](https://gluonhq.com/products/javafx/)
   - Download JavaFX SDK for your OS
   - Extract to a location (e.g., `C:\javafx-sdk-19`)

3. **Compile and Run**
```bash
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls src/main/java/AMS.java
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp src/main/java AMS
```

## Usage

### First Time Setup
1. Run the application
2. Register as Government user first
3. Register Farmers
4. Login with your credentials

### Government User Flow
1. Click "Government Registration"
2. Fill in details (name, contact, email, department, password)
3. **Save your Government ID** (shown after registration)
4. Login with Government ID and password
5. Access government menu to manage resources, subsidies, and farmers

### Farmer User Flow
1. Click "Farmer Registration"
2. Fill in details (name, contact, email, state, city, age, password)
3. **Save your Farmer ID** (shown after registration)
4. Login with Farmer ID and password
5. Access farmer menu to view subsidies, notifications, and request support

## Project Structure
```
AMS/
├── src/
│   └── main/
│       └── java/
│           └── AMS.java          # Main application file
├── pom.xml                       # Maven configuration
├── .gitignore                    # Git ignore rules
├── README.md                     # This file
└── LICENSE                       # License file (optional)
```

## Class Structure
- **Base**: Abstract base class with common user properties
- **Farmer**: Extends Base, manages farmer-specific operations
- **Govt**: Extends Base, manages government operations
- **Resource**: Handles resource data and distribution
- **MarketService**: Implements multithreading for market price generation
- **WeatherService**: Implements multithreading for weather data
- **GenericList<T>**: Type-safe list for farmer storage
- **AMS**: Main JavaFX Application class

## Screenshots
![alt text](image.png)

## Future Enhancements
- 💾 Database integration (MySQL/PostgreSQL)
- 🔐 Password encryption and security
- 📊 Advanced analytics dashboard with charts
- 📧 Email notifications
- 🌍 Multi-language support
- 📱 Mobile responsive design
- 🔔 Real-time push notifications
- 📄 PDF report generation
- 🗺️ GPS/Maps integration for farmer locations

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Author
Thanmai
- GitHub: [@Thanmai-11](https://github.com/Thanmai-11)

## Acknowledgments
- Project Team Members
- JavaFX community
- OpenJFX project
- Agriculture department for requirements inspiration

---
Made with ❤️ for farmers and agricultural management