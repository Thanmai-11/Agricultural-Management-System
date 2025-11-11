import javafx.application.Application;
import javafx.geometry.Insets;
import java.util.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
class Base {
    Scanner sc = new Scanner(System.in);
    String name;
    long contactno;
    String emailid;
    int save;   // not required since we use javafx
    protected void register() {
        System.out.println("WELCOME TO THE REGISTRATION");
        System.out.print("Enter the name: ");
        name = sc.nextLine();
        contactno = getValidContactNo();
        emailid = getValidEmail();
    }
    void saving() {
        while (true) {
            System.out.println("Save?\n1. Yes    2. No");
            save = sc.nextInt();
            if (save == 1) {
                System.out.println("Congratulations!!! You have been registered successfully...");
                break; // Exit the loop if saved
            } else if (save == 2) {
                System.out.println("Please save before leaving the page.");
            } else {
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }
    }
    void viewDetails() {
        System.out.println("Name: " + name + "\nContact number: " + contactno + "\nEmail ID: " + emailid);
    }
    private long getValidContactNo() {
        long contact = 0;
        while (true) {
            System.out.print("Enter the contact no: ");
            try {
                contact = sc.nextLong();
                if (String.valueOf(contact).length() == 10) {
                    break;
                } else {
                    System.out.println("Contact number must be 10 digits long. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
                sc.next(); // clear invalid input
            }
        }
        return contact;
    }
 
    private String getValidEmail() {
        String email;
        while (true) {
            System.out.print("Enter the email ID: ");
            email = sc.next();
            if (email.endsWith("@gmail.com") && email.contains("@")) {
                break;
            } else {
                System.out.println("Invalid email format. Please ensure it ends with @gmail.com.");
            }
        }
        return email;
    }
 }
 class Farmer extends Base {
    Scanner sc = new Scanner(System.in);
    String state;
    String city;
    int age;
    int ID;
    static int count=0;
    double subsidyAmt;
    String fpswrd;
    List<String> supportRequests;
    List<String> notifications;
        Farmer() {
            supportRequests = new ArrayList<>();
            notifications = new ArrayList<>();

        }
        public void setState(String state) {
            this.state = state;
        }
        public void setCity(String city) {
            this.city = city;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public void setPassword(String password) {
            this.fpswrd = password; // Ensure to handle password securely
        }
        void getSubsidy(double amt) {
            subsidyAmt = amt;
        }
       int getID()
       {
        return ID;
       }
        void showSubsidyAmt() {
            if(subsidyAmt>0)
            System.out.println("Rs" + subsidyAmt + " of subsidy amount has been allocated to farmer " + ID);
            else
             System.out.println("No subsidy allocated");
          
        }   
        
        public void setName(String name) {
            this.name = name;
        }  
        public void setContactNo(long contactno) {
            this.contactno = contactno;
        }
        public void setEmail(String emailid) {
            this.emailid = emailid;
        }
        public void receiveNotification(String message) {
            notifications.add(message);
        }
        public void viewNotifications() {
            System.out.println("=== Notifications ===");
            if(notifications.isEmpty())
            {
              System.out.println("No notifications\n");
              return;
            }
            for (String notification : notifications) {
                System.out.println("- " + notification);
            }
        }
        public boolean login(String enteredPassword) {
            // Check if the entered password matches the stored password
            if (enteredPassword.equals(fpswrd)) {
                return true; // Successful login
            } else {
                return false; // Unsuccessful login
            }
        }
        void viewDetails() {
            super.viewDetails();
            System.out.println("State: " + state + "\nCity: " + city + "\nAge: " + age + "\nID: " + ID + "\n");
        }
        void requestSupport(String issue) {
            supportRequests.add(issue);
        }   
        public void receiveResponse(String response) {
            notifications.add("Response: " + response);
        }
        void viewSupportRequests() {
            System.out.println("Support Requests for Farmer ID: " + ID);
            if (supportRequests.isEmpty()) {
                System.out.println("No support requests found.");
                return;
            }
            for (int i = 0; i < supportRequests.size(); i++) {
                System.out.println((i + 1) + ". " + supportRequests.get(i));
            }
        }
                public void setID(int i) {
                    this.ID=i;
                }
             }
class Govt extends Base {
                    String govtID;
                        String gpswrd;
                        private String dept;
                        static int count=0;
                        private List<Farmer> farmers;
                        private List<Resource> resources;
                        private List<String> cropProductionReports;
                        static List<Govt> governmentList = new ArrayList<>();
                        public static void addGovt(Govt govt) {
                            if (govt != null) {
                                governmentList.add(govt);
                                System.out.println("Government added: " + govt.getGovtID());
                            } else {
                                System.out.println("Cannot add null government.");
                            }
                        }
                        public Govt() {
                            farmers = new ArrayList<>();
                            resources = new ArrayList<>();
                            cropProductionReports = new ArrayList<>();
                        }
                        public void setGovtID(String govtID) {
                            this.govtID = govtID;
                        }
                
                        // Setter for GPSWrd (password)
                        public void setPassword(String gpswrd) {
                            this.gpswrd = gpswrd;
                        }
                
                        // Setter for Department
                        public void setDepartment(String dept) {
                            this.dept = dept;
                        }
                        // Getter for Department
                        public String getDepartment() {
                            return dept;
                        }
                
                        public void setDept(String dept) {
                            if (dept.length() >= 3) {
                                this.dept = dept;
                            } else {
                                throw new IllegalArgumentException("Department name must be at least 3 letters.");
                            }
                        }
                
                        public void setGpswrd(String gpswrd) {
                            this.gpswrd = gpswrd; // Additional validation for password can be added if necessary
                        }
                
                        public void setName(String name) {
                            this.name = name; // Inherited from Base class
                        }
                
                        public void setContactNo(long contactNo) {
                            this.contactno = contactNo; // Inherited from Base class
                        }
                
                        public void setEmail(String email) {
                            this.emailid = email; // Inherited from Base class
                        }
                
                        String getGovtID() {
                            return govtID;
                        }
                        public String getName() {
                            return name; // Assuming 'name' is a field in your Govt class
                        }
                        
                        public long getContactNo() {
                            return contactno; // Assuming 'contactno' is a field in your Govt class
                        }
                        
                        public String getEmail() {
                            return emailid; // Assuming 'emailid' is a field in your Govt class
                        }
                        void govtRegister() {
                            super.register();
                    
                            // Set department with validation
                            while (true) {
                                System.out.print("Enter the department: ");
                                String inputDept = sc.next();
                                try {
                                    setDepartment(inputDept);
                                    break; // Valid department name
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                    
                            System.out.print("Create a strong password: ");
                            gpswrd = sc.next();
                    
                            // Generate unique Govt ID
                            govtID = (dept.substring(0, 3).toUpperCase() + "00" + count).toUpperCase();
                            System.out.println("Your ID is " + govtID + "\n");
                            
                            // Save the government instance to the list
                            addGovt(this);
                        }
                
                        void viewDetails() {
                            super.viewDetails();
                            System.out.println("Dept: " + dept + "\nID: " + govtID);
                        }
                
                        void enterData() {
                            System.out.print("How many resources? ");
                            int n = readIntegerInput(1, Integer.MAX_VALUE); // Validate input for resource count
                            for (int i = 0; i < n; i++) {
                                resources.add(new Resource());
                            }
                            System.out.println("Resource details:\n");
                            for (Resource r : resources) {
                                r.dispResource();
                            }
                        }
                
                        public boolean login(String enteredPassword) {
                            // Check if the entered password matches the stored password
                            if (enteredPassword.equals(gpswrd)) {
                                System.out.println("Successfully logged in as Government ID: " + govtID + "\n");
                                return true; // Successful login
                            } else {
                                System.out.println("Invalid credentials. Please try again.");
                                return false; // Unsuccessful login
                            }
                        }
                    
                
                        public void addFarmer(Farmer f) {
                            farmers.add(f);
                        }
                
                        private boolean isValidFarmerID(int farmerID) {
                            for (Farmer f : farmers) {
                                if (f.ID == farmerID) {
                                    return true;
                                }
                            }
                            return false; // ID not found
                        }
                
                        public void distributeResource() {
                            // Loop for Farmer ID input
                            int farmerID;
                            while (true) {
                                System.out.print("\nEnter Farmer ID: ");
                                farmerID = readIntegerInput(1, Integer.MAX_VALUE); // Validate input for Farmer ID
                    
                                if (!isValidFarmerID(farmerID)) {
                                    System.out.println("Invalid Farmer ID: " + farmerID + ". Please try again.");
                                    continue; // Loop back for a valid ID
                                }
                                break; // Valid ID, exit the loop
                            }
                    
                            // Loop for Resource Name input
                            Resource resource = null;
                            while (true) {
                                System.out.print("Enter resource name: ");
                                String resourceName = sc.next(); // Read the resource name
                    
                                // Check if the resource name is valid
                                for (Resource r : resources) {
                                    if (r.name.equalsIgnoreCase(resourceName)) {
                                        resource = r;
                                        break; // Found the resource
                                    }
                                }
                    
                                if (resource == null) {
                                    System.out.println("Resource " + resourceName + " not found. Please try again.");
                                    continue; // Loop back for a valid resource name
                                }
                                break; // Valid resource name, exit the loop
                            }
                    
                            // Loop for Amount input
                            int amount;
                            while (true) {
                                System.out.print("Enter the amount to distribute: ");
                                amount = readIntegerInput(1, Integer.MAX_VALUE); // Validate input for amount
                    
                                // Check if the requested amount is greater than the available quantity
                                if (amount > resource.quantity) {
                                    System.out.println("Insufficient quantity of " + resource.name + ". Available: " + resource.quantity);
                                    continue; // Loop back for a new attempt
                                }
                                break; // Valid amount, exit the loop
                            }
                    
                            // Proceed with the distribution
                            resource.distribute(amount);
                            System.out.println("Distributed " + amount + " of " + resource.name + " to Farmer ID: " + farmerID);
                    
                            // Send notification to the farmer
                            String notificationMessage = amount + " of " + resource.name + " has been distributed to you.";
                            for (Farmer f : farmers) {
                                if (f.ID == farmerID) {
                                    f.receiveNotification(notificationMessage);
                                    System.out.println("Notification sent to farmer " + farmerID);
                                    break; // Exit loop after sending notification
                                }
                            }
                        }
                
                        private double readDoubleInput() {
                            double value;
                            while (true) {
                                try {
                                    value = sc.nextDouble();
                                    sc.nextLine(); // Consume newline
                                    return value;
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input! Please enter a valid number.");
                                    sc.nextLine(); // Clear the invalid input
                                }
                            }
                        }
                    
                
                        private int readIntegerInput(int min, int max) {
                            int value;
                            while (true) {
                                try {
                                    value = sc.nextInt();
                                    sc.nextLine(); // Consume newline
                                    if (value < min || value > max) {
                                        System.out.println("Please enter a number between " + min + " and " + max + ".");
                                        continue;
                                    }
                                    return value;
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input! Please enter a valid integer.");
                                    sc.nextLine(); // Clear the invalid input
                                }
                            }
                        }
                
                        public void allocateSubsidy() {
                            while (true) {
                                System.out.print("\nEnter Farmer ID: ");
                                int farmerID = readIntegerInput(1, Integer.MAX_VALUE);
                    
                                if (!isValidFarmerID(farmerID)) {
                                    System.out.println("Invalid Farmer ID: " + farmerID + ". Please try again.");
                                    continue; // Loop back to prompt for a valid ID
                                }
                    
                                System.out.print("Enter subsidy amount: ");
                                double amount = readDoubleInput();
                    
                                for (Farmer f : farmers) {
                                    if (f.ID == farmerID) {
                                        f.getSubsidy(amount);
                                        System.out.println("Subsidy of Rs" + amount + " allocated to Farmer ID: " + farmerID);
                                        return; // Exit after successful allocation
                                    }
                                }
                            }
                        }
                    
                        public void sendNotification() {
                            int farmerID;
                
                            while (true) {
                                System.out.print("\nEnter Farmer ID: ");
                                farmerID = readIntegerInput(1, Integer.MAX_VALUE);
                
                                if (!isValidFarmerID(farmerID)) {
                                    System.out.println("Invalid Farmer ID: " + farmerID + ". Please try again.");
                                    continue; // Loop back to prompt for a valid ID
                                }
                
                                System.out.print("\nEnter the notification message: ");
                                String message = sc.nextLine();
                
                                for (Farmer f : farmers) {
                                    if (f.ID == farmerID) {
                                        f.receiveNotification(message);
                                        System.out.println("Notification sent to Farmer ID: " + farmerID);
                                        return; // Exit after successful notification
                                    }
                                }
                            }
                        }
                
                        public void respondToSupportRequest() {
                            int farmerID;
                
                            while (true) {
                                System.out.print("\nEnter Farmer ID: ");
                                farmerID = readIntegerInput(1, Integer.MAX_VALUE);
                    
                                if (!isValidFarmerID(farmerID)) {
                                    System.out.println("Invalid Farmer ID: " + farmerID + ". Please try again.");
                                    continue; // Loop back to prompt for a new ID
                                }
                    
                                Farmer farmer = null;
                                for (Farmer f : farmers) {
                                    if (f.ID == farmerID) {
                                        farmer = f;
                                        break; // Exit loop when the farmer is found
                                    }
                                }
                    
                                if (farmer == null) {
                                    System.out.println("Farmer ID not found. Please try again.");
                                    continue; // Loop back to prompt for a new ID
                                }
                    
                                if (farmer.supportRequests.isEmpty()) {
                                    System.out.println("No support requests available for Farmer ID: " + farmerID);
                                    return; // Exit if no requests are available
                                }
                    
                                farmer.viewSupportRequests();
                    
                                System.out.print("Enter the number of the request you want to respond to (or 0 to cancel): ");
                                int requestNumber = readIntegerInput(0, farmer.supportRequests.size());
                    
                                if (requestNumber == 0) {
                                    System.out.println("Response cancelled.");
                                    return; // Exit if the user cancels
                                }
                    
                                System.out.print("Enter your response: ");
                                String response = sc.nextLine();
                    
                                String selectedRequest = farmer.supportRequests.get(requestNumber - 1);
                                farmer.receiveResponse(response);
                                System.out.println("Response to request: '" + selectedRequest + "' has been sent to Farmer ID: " + farmerID);
                                break; // Exit after successful response
                            }
                        }
                        public void viewFarmers() {
                            System.out.println("\nRegistered Farmers:");
                            if (farmers.isEmpty()) {
                                System.out.println("No farmers registered.");
                                return;
                            }
                            
                            for (Farmer f : farmers) {
                                f.viewDetails();
                            }
                        }
                
                        public void trackCropProduction(String cropName, int quantity) {
                            cropProductionReports.add("Crop: " + cropName + ", Quantity: " + quantity);
                            System.out.println("Recorded crop production: " + cropName + ", Quantity: " + quantity);
                        }
                
                        public void generateReport() {
                            System.out.println("\n=== Crop Production Report ===");
                            for (String report : cropProductionReports) {
                                System.out.println(report);
                            }
                            System.out.println("==============================");
                        }
                    }
class Resource {
            Scanner sc = new Scanner(System.in);
            String name;
            short quantity;
            static int rcount = 0;
               
            Resource() {
            rcount++;
            System.out.print("\nEnter the resource: ");
            name = sc.next();
            quantity = readShortInput("Enter the quantity: ");
            }
               
                 
               
                    void saving() {
                        System.out.println("Data Saved");
                    }
               
                    void dispResource() {
                        System.out.println("Resource: " + name + "\nQuantity: " + quantity + "\n");
                    }
               
                    public void distribute(int amount) {
                        if (amount <= quantity) {
                            quantity -= amount;
                            System.out.println(amount + " units of " + name + " distributed.");
                        } else {
                            System.out.println("Not enough " + name + " in inventory.");
                        }
                    }
               
                    private short readShortInput(String prompt) {
                        short value;
                        while (true) {
                            try {
                                System.out.print(prompt);
                                value = sc.nextShort();
                                if (value < 0) {
                                    System.out.println("Quantity cannot be negative. Please enter a valid quantity.");
                                    continue;
                                }
                                sc.nextLine(); // Consume newline
                                return value;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input! Please enter a valid number.");
                                sc.nextLine(); // Clear the invalid input
                            }
                        }
                    }
                }
               
                interface MarketServiceInterface {
                    public void generateData();
                    public double generatePrice(String product);
                }
               
                class MarketService implements Runnable, MarketServiceInterface {
                    private static final String[] PRODUCTS = {"Wheat", "Rice", "Corn", "Barley", "Soyabeans"};
                    private Random random = new Random();
                    private int days;
                    private StringBuilder marketData = new StringBuilder();
               
                    public MarketService(int days) {
                        this.days = days;
                    }
               
                    public void run() {
                        generateData();
                    }
               
                    public void generateData() {
                        marketData.setLength(0); // Clear previous data
                        marketData.append("MARKET PRICE FOR DIFFERENT PRODUCTS:\n");
                        String highestProduct = "";
                        double highestPrice = 0;
                for (int i = 0; i < days; i++) {
                        marketData.append(String.format("Day %d:\n", i + 1));
                        for (String product : PRODUCTS) {
                            double price = generatePrice(product);
                            marketData.append(String.format("Product: %s | Price: Rs %.2f\n", product, price));
                            marketData.append("-----\n");
               
                            // Track the product with the highest price
                            if (price > highestPrice) {
                                highestPrice = price;
                                highestProduct = product;
                            }
                        }
                    }
                        marketData.append(String.format("Recommended crop based on market price: %s\n", highestProduct));
                    }
               
                    public String getMarketData() {
                        return marketData.toString();
                    }
               
                    public double generatePrice(String product) {
                        double min, max;
                        switch (product) {
                            case "Wheat":
                                min = 10.0; max = 20.0; break;
                            case "Rice":
                                min = 15.0; max = 25.0; break;
                            case "Corn":
                                min = 8.0; max = 15.0; break;
                            case "Barley":
                                min = 12.0; max = 18.0; break;
                            case "Soyabeans":
                                min = 14.0; max = 20.0; break; // Fixed price range
                            default:
                                min = 0; max = 1; break;
                        }
                        return min + (max - min) * random.nextDouble();
                    }
                }
                class WeatherData {
                    private String condition;
                    private double temperature;
                
                
                    public String getCondition() { return condition; }
                    public void setCondition(String condition) { this.condition = condition; }
                
                
                    public double getTemperature() { return temperature; }
                    public void setTemperature(double temperature) { this.temperature = temperature; }
                 }
               
                 class WeatherService implements Runnable {
                    private List<WeatherData> weatherReports = new ArrayList<>();
                    private Random random = new Random();
                    private int days;
                    private StringBuilder weatherData = new StringBuilder();
               
                    private static final String[] CONDITIONS = {"Sunny", "Rainy", "Cloudy", "Windy", "Stormy"};
               
                    public WeatherService(int days) {
                        this.days = days;
                    }
                    public void run() {
                        generateData();
                        // Update the weatherData string after generation
                        updateWeatherData();
                    }
               
                    public void generateData() {
                        weatherReports.clear(); // Clear previous reports
                        for (int i = 0; i < days; i++) {
                            WeatherData report = new WeatherData();
                            String condition = CONDITIONS[random.nextInt(CONDITIONS.length)];
                            double temperature = generateTemperature(condition);
                            report.setCondition(condition);
                            report.setTemperature(temperature);
                            weatherReports.add(report);
                        }
                       
                    }
               
                    private void updateWeatherData() {
                        weatherData.setLength(0); // Clear previous data
                        weatherData.append("WEATHER FORECAST FOR NEXT ").append(days).append(" DAYS:\n");
                        for (int i = 0; i < weatherReports.size(); i++) {
                            WeatherData report = weatherReports.get(i);
                            weatherData.append("Day ").append(i + 1)
                                       .append(": Condition: ").append(report.getCondition())
                                       .append(" | Temperature: ").append(String.format("%.2f",report.getTemperature())).append(" C\n");
                        }
                    }
                   
                    public String getWeatherData() {
                        return weatherData.toString();
                    }
               
                    private double generateTemperature(String condition) {
                        double min, max;
                        switch (condition) {
                            case "Sunny": min = 25; max = 40; break;
                            case "Rainy": min = 15; max = 25; break;
                            case "Cloudy": min = 20; max = 30; break;
                            case "Windy": min = 10; max = 20; break;
                            case "Stormy": min = 15; max = 25; break;
                            default: min = 0; max = 1; break;
                        }
                        return min + (max - min) * random.nextDouble();
                    }
                 }
               
                 class GenericList<T> {
                    private List<T> list = new ArrayList<>();
                    public void add(T item) {
                        list.add(item);
                    }
                    public T findById(int id) {
                        for (T item : list) {
                            if (item instanceof Farmer && ((Farmer) item).ID == id) {
                                return item;
                            }
                        }
                        return null; // Return null if no farmer is found
                    }
                }
                                                  
public class AMS_old extends Application {
            
                    static Govt government = new Govt();
                    static GenericList<Farmer> farmers = new GenericList<>();
               
                    public static void main(String[] args) {
                        launch(args);
                    }
               
                    @Override
                    public void start(Stage primaryStage) {
                        primaryStage.setTitle("Agriculture Management System");
               
                        // Main Menu
                        VBox mainMenu = new VBox(10);
                        mainMenu.setStyle("-fx-background-color: #ADD8E6;");
                        mainMenu.getChildren().addAll(createMainMenu(primaryStage));
               
                        Scene scene = new Scene(mainMenu, 600, 400);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                    
                    private VBox createMainMenu(Stage primaryStage) {
                        Button govtRegButton = new Button("Government Registration");
                        Button farmerRegButton = new Button("Farmer Registration");
                        Button farmerLoginButton = new Button("Farmer Login");
                        Button govtLoginButton = new Button("Government Login");
                        Button weatherButton = new Button("Weather Updates");
                        Button cropAndMarketButton = new Button("Crop and Market Info");
                        Button exitButton = new Button("Exit");
               
                        govtRegButton.setOnAction(e ->governmentRegistration());
                        farmerRegButton.setOnAction(e -> farmerRegistration());
                        farmerLoginButton.setOnAction(e -> farmerLogin());
                        govtLoginButton.setOnAction(e -> governmentLogin());
                        weatherButton.setOnAction(e -> weatherUpdates());
                        cropAndMarketButton.setOnAction(e -> cropAndMarketInfo());
                        exitButton.setOnAction(e -> primaryStage.close());
                        VBox menu = new VBox(10);
                        menu.setPadding(new Insets(20, 10, 10, 10));
                        menu.getChildren().addAll(govtRegButton, farmerRegButton, farmerLoginButton, govtLoginButton, weatherButton, cropAndMarketButton, exitButton);
                   
                    return menu;   
                 }
                 private void governmentRegistration() {
                    Dialog<Govt> dialog = new Dialog<>();
                    dialog.setTitle("Government Registration");
                    dialog.setHeaderText("Please enter your government details:");
                
                    // Set the button types
                    ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);
                
                    // Create input fields
                    TextField nameField = new TextField();
                    nameField.setPromptText("Name");
                
                    TextField contactField = new TextField();
                    contactField.setPromptText("Contact Number");
                
                    TextField emailField = new TextField();
                    emailField.setPromptText("Email ID");
                
                    TextField departmentField = new TextField();
                    departmentField.setPromptText("Department");
                
                    PasswordField passwordField = new PasswordField();
                    passwordField.setPromptText("Create a strong password");
                
                    // Create a grid and add the fields
                    GridPane grid = new GridPane();
                    grid.add(new Label("Name:"), 0, 0);
                    grid.add(nameField, 1, 0);
                    grid.add(new Label("Contact Number:"), 0, 1);
                    grid.add(contactField, 1, 1);
                    grid.add(new Label("Email ID:"), 0, 2);
                    grid.add(emailField, 1, 2);
                    grid.add(new Label("Department:"), 0, 3);
                    grid.add(departmentField, 1, 3);
                    grid.add(new Label("Password:"), 0, 4);
                    grid.add(passwordField, 1, 4);
                    dialog.getDialogPane().setContent(grid);
                
                    // Convert the result to a Govt object when the Register button is pressed
                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == registerButtonType) {
                            return new Govt(); // Placeholder; we'll fill in details below
                        }
                        return null; // Return null if Cancel is pressed
                    });
                
                    // Show the dialog and wait for the result
                    Optional<Govt> result = dialog.showAndWait();
                
                    // Check if the user pressed the Register button
                    if (result.isPresent()) {
                        government = result.get();
                        
                        // Validate all inputs
                        String errorMessage = validateGovtInputs(nameField, contactField, emailField, departmentField, passwordField);
                        
                        // Show error messages if there are any
                        if (!errorMessage.isEmpty()) {
                            showError(errorMessage);
                            return; // Exit without registering the government
                        }
                
                        // If all inputs are valid, set the Govt object's details
                        government.setName(nameField.getText());
                        government.setContactNo(Long.parseLong(contactField.getText()));
                        government.setEmail(emailField.getText());
                        government.setDepartment(departmentField.getText());
                        government.setPassword(passwordField.getText());
                        Govt.count++;
                        // Generate the government ID based on the department
                        government.govtID = (government.getDepartment().substring(0, 3).toUpperCase() + "00" + Govt.count).toUpperCase();
                
                        // Register the government
                        Govt.addGovt(government);
                        showAlert("Registration Successful", "Government registered successfully with ID: " + government.getGovtID());
                    }
                    // If the user pressed Cancel, no action is taken and no error message is shown
                }
                
             private String validateGovtInputs(TextField nameField, TextField contactField,
                                               TextField emailField, TextField departmentField,
                                               PasswordField passwordField) {
                StringBuilder errorMessage = new StringBuilder();
             
             
                // Validate name
                if (nameField.getText().isEmpty()) {
                    errorMessage.append("Name cannot be empty.\n");
                }
             
             
                // Validate contact number
                if (!contactField.getText().matches("\\d{10}")) {
                    errorMessage.append("Contact number must be 10 digits long and numeric.\n");
                }
             
             
                // Validate email
                if (!emailField.getText().endsWith("@govt.com") || !emailField.getText().contains("@")) {
                    errorMessage.append("Email must end with @govt.com\n");
                }
             
             
                // Validate department
                if (departmentField.getText().isEmpty()) {
                    errorMessage.append("Department cannot be empty.\n");
                }
             
             
                // Validate password
                if (passwordField.getText().isEmpty()) {
                    errorMessage.append("Password cannot be empty.\n");
                }
             
             
                return errorMessage.toString();
             }
             
             
             private void farmerRegistration() {
                Dialog<Farmer> dialog = new Dialog<>();
                dialog.setTitle("Farmer Registration");
                dialog.setHeaderText("Please enter your details:");
             
                // Set the button types
                ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);
             
             
                // Create input fields
                TextField nameField = new TextField();
                nameField.setPromptText("Name");
             
             
                TextField contactField = new TextField();
                contactField.setPromptText("Contact Number");
             
             
                TextField emailField = new TextField();
                emailField.setPromptText("Email ID");
             
             
                TextField stateField = new TextField();
                stateField.setPromptText("State");
             
             
                TextField cityField = new TextField();
                cityField.setPromptText("City");
             
             
                TextField ageField = new TextField();
                ageField.setPromptText("Age");
             
             
                PasswordField passwordField = new PasswordField();
                passwordField.setPromptText("Create a password");
             
             
                // Create a grid and add the fields
                GridPane grid = new GridPane();
                grid.add(new Label("Name:"), 0, 0);
                grid.add(nameField, 1, 0);
                grid.add(new Label("Contact Number:"), 0, 1);
                grid.add(contactField, 1, 1);
                grid.add(new Label("Email ID:"), 0, 2);
                grid.add(emailField, 1, 2);
                grid.add(new Label("State:"), 0, 3);
                grid.add(stateField, 1, 3);
                grid.add(new Label("City:"), 0, 4);
                grid.add(cityField, 1, 4);
                grid.add(new Label("Age:"), 0, 5);
                grid.add(ageField, 1, 5);
                grid.add(new Label("Password:"), 0, 6);
                grid.add(passwordField, 1, 6);
                dialog.getDialogPane().setContent(grid);
             
             
                 // Convert the result to a Farmer object when the Register button is pressed
                 dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == registerButtonType) {
                        return new Farmer(); // Placeholder; we'll fill in details below
                    }
                    return null; // Return null if Cancel is pressed
                });
            
                // Show the dialog and wait for the result
                Optional<Farmer> result = dialog.showAndWait();
            
                // Check if the user pressed the Register button
                if (result.isPresent()) {
                    Farmer farmer = result.get();
                    
                    // Validate all inputs
                    String errorMessage = validateInputs(nameField, contactField, emailField, stateField, cityField, ageField, passwordField);
                    
                    // Show error messages if there are any
                    if (!errorMessage.isEmpty()) {
                        showError(errorMessage);
                        return; // Exit without registering the farmer
                    }
            
                    // If all inputs are valid, set the Farmer object's details
                    farmer.setName(nameField.getText());
                    farmer.setContactNo(Long.parseLong(contactField.getText()));
                    farmer.setEmail(emailField.getText());
                    farmer.setState(stateField.getText());
                    farmer.setCity(cityField.getText());
                    farmer.setAge(Integer.parseInt(ageField.getText()));
                    farmer.setPassword(passwordField.getText());
                   Farmer.count++;
                   farmer.ID=2024000+Farmer.count;
            // Register the farmer
            showAlert("Registration Successful", "Farmer registered successfully! Your ID is: " + farmer.getID());
    
            // Successful registration
            government.addFarmer(farmer);
            farmers.add(farmer);
        }
        // If the user pressed Cancel, no action is taken and no error message is shown
    }
     private String validateInputs(TextField nameField, TextField contactField, TextField emailField,
                                  TextField stateField, TextField cityField, TextField ageField,
                                  PasswordField passwordField) {
        StringBuilder errorMessage = new StringBuilder();
     
     
        // Validate name
        if (nameField.getText().isEmpty()) {
            errorMessage.append("Name cannot be empty.\n");
        }
     
     
        // Validate contact number
        if (!contactField.getText().matches("\\d{10}")) {
            errorMessage.append("Contact number must be 10 digits long and numeric.\n");
        }
     
     
        // Validate email
        if (!emailField.getText().endsWith("@gmail.com") || !emailField.getText().contains("@")) {
            errorMessage.append("Email must end with @gmail.com\n");
        }
     
     
        // Validate age
        try {
            int age = Integer.parseInt(ageField.getText());
            if (age <= 0) {
                errorMessage.append("Age must be a positive number.\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Age must be a valid number.\n");
        }
     
     
        // Validate state and city
        if (stateField.getText().isEmpty()) {
            errorMessage.append("State cannot be empty.\n");
        }
        if (cityField.getText().isEmpty()) {
            errorMessage.append("City cannot be empty.\n");
        }
     
     
        // Validate password
        if (passwordField.getText().isEmpty()) {
            errorMessage.append("Password cannot be empty.\n");
        }
     
     
        return errorMessage.toString(); // Return any error messages
     }
     
     
     private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Validation Error");
        alert.setContentText(message);
        alert.showAndWait();
     }
     
     
        private void farmerLogin() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Farmer Login");
        dialog.setHeaderText("Please enter your Farmer ID and Password.");
     
     
        // Set the button types
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
     
     
        // Create the ID and password fields
        TextField idField = new TextField();
        idField.setPromptText("Farmer ID");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
     
     
        // Create a grid and add the fields
        GridPane grid = new GridPane();
        grid.add(new Label("Farmer ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        dialog.getDialogPane().setContent(grid);
     
     
        // Convert the result to a Pair (ID, Password) when the login button is pressed
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(idField.getText(), passwordField.getText());
            }
            return null;
        });
     
     
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            String id = pair.getKey();
            String password = pair.getValue();
            try {
                Farmer farmer = farmers.findById(Integer.parseInt(id));
                if (farmer != null && farmer.login(password)) { // Assume login takes a password
                    farmerMenu(farmer);
                } else {
                    showAlert("Login Failed", "Invalid Farmer ID or credentials.");
                }
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter a valid numeric Farmer ID.");
            }
        });
     }
     
     
     private void governmentLogin() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Government Login");
        dialog.setHeaderText("Please enter your Government ID and Password.");
     
     
        // Set the button types
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
     
     
        // Create the ID and password fields
        TextField idField = new TextField();
        idField.setPromptText("Government ID");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
     
     
        // Create a grid and add the fields
        GridPane grid = new GridPane();
        grid.add(new Label("Government ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        dialog.getDialogPane().setContent(grid);
     
     
        // Convert the result to a Pair (ID, Password) when the login button is pressed
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(idField.getText(), passwordField.getText());
            }
            return null;
        });
     
     
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            String id = pair.getKey();
            String password = pair.getValue();
            // Check against the static list of government accounts
            Govt govt = findGovernmentByID(id);
            if (govt != null && govt.login(password)) {
                governmentMenu();
            } else {
                showAlert("Login Failed", "Invalid Government ID or credentials.");
            }
        });
     }
     
     
     // Helper method to find Government by ID from the static list
     private Govt findGovernmentByID(String id) {
        for (Govt govt : Govt.governmentList) { // Use the static list from the Govt class
            if (govt.getGovtID().equals(id)) {
                return govt; // Return the found government object
            }
        }
        return null; // Return null if no matching ID is found
     }
     
     
        private void weatherUpdates() {
            Optional<String> result = showInputDialog("Weather Updates", "Enter number of days (1-15):");
            result.ifPresent(days -> {
                try {
                    int d = Integer.parseInt(days);
                    if (d >= 1 && d <= 15) {
                        WeatherService ws = new WeatherService(d);
                       
                        // Create a new thread for weather data generation
                        Thread weatherThread = new Thread(ws);
                        weatherThread.setDaemon(true); // Allows the thread to exit when the application closes
                        weatherThread.start();
       
                        // Use a new thread to wait for the data to be generated and then update the GUI
                        new Thread(() -> {
                            try {
                                weatherThread.join(); // Wait for the weather generation thread to finish
                                String weatherData = ws.getWeatherData(); // Get the generated weather data
                                // Update GUI on the JavaFX Application Thread
                                javafx.application.Platform.runLater(() -> displayWeatherData(weatherData));
                            } catch (InterruptedException e) {
                                showAlert("Error", "Weather data generation was interrupted.");
                            }
                        }).start();
                    } else {
                        showAlert("Invalid Input", "Please enter a number between 1 and 15.");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number.");
                }
            });
        }
       
     private void cropAndMarketInfo() {
            // Create a new dialog for selecting the type of information
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Select Information Type");
            alert.setHeaderText("What information would you like to retrieve?");
            ButtonType marketPricesButton = new ButtonType("Market Prices");
            ButtonType cropRecommendationButton = new ButtonType("Crop Recommendations");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
           
            alert.getButtonTypes().setAll(marketPricesButton, cropRecommendationButton, cancelButton);
           
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == marketPricesButton) {
                displayMarketPrices();
            } else if (result.isPresent() && result.get() == cropRecommendationButton) {
                cropRecommendation();
            }
        }
     
     
        private void displayMarketPrices() {
            Optional<String> result = showInputDialog("Market Prices", "Enter number of days (1-3):");
            result.ifPresent(days -> {
                try {
                    int d = Integer.parseInt(days);
                    if (d >= 1 && d <= 3) {
                        MarketService marketService = new MarketService(d);
                        Thread marketThread = new Thread(marketService);
                        marketThread.setDaemon(true);
                        marketThread.start();
     
     
                        new Thread(() -> {
                            try {
                                marketThread.join(); // Wait for the market data generation to finish
                                String marketData = marketService.getMarketData();
                                javafx.application.Platform.runLater(() -> displayMarketData(marketData));
                            } catch (InterruptedException e) {
                                showAlert("Error", "Market data generation was interrupted.");
                            }
                        }).start();
                    } else {
                        showAlert("Invalid Input", "Please enter a number between 1 and 3.");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number.");
                }
            });
        }
     
     
        private void displayMarketData(String marketData) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Market Prices");
            alert.setHeaderText("Market Price Updates");
            alert.setContentText(marketData);
            alert.showAndWait();
        }
     
     
        private void cropRecommendation() {
            Optional<String> result = showInputDialog("Crop Recommendations", "Enter Soil Type(clay,sandy,loamy):");
            result.ifPresent(soilType -> {
                String recommendations = getCropRecommendations(soilType);
                showAlert("Crop Recommendations", recommendations);
            });
        }
     
     
        private String getCropRecommendations(String soilType) {
            switch (soilType.toLowerCase()) {
                case "clay":
                    return "Recommended Crops: Rice, Wheat, Soybean";
                case "sandy":
                    return "Recommended Crops: Carrots, Onions, Potatoes";
                case "loamy":
                    return "Recommended Crops: Corn, Tomatoes, Beans";
                default:
                    return "No recommendations available for the specified soil type.";
            }
        }
     
     
        private void farmerMenu(Farmer farmer) {
            Stage farmerStage = new Stage();
            farmerStage.setTitle("Farmer Menu");
        
            VBox farmerMenu = new VBox(10);
            farmerMenu.setPadding(new Insets(20));
        
            // Add menu options
            Button viewDetailsButton = new Button("View Details");
            Button viewSubsidyButton = new Button("View Subsidy Amount");
            Button viewNotificationsButton = new Button("View Notifications");
            Button requestSupportButton = new Button("Request Support");
            Button viewSupportRequestsButton = new Button("View Support Requests");
            Button logout = new Button("Logout");
        
            viewDetailsButton.setOnAction(e -> viewFarmerDetails(farmer));
            viewSubsidyButton.setOnAction(e -> viewFarmerSubsidy(farmer));
            viewNotificationsButton.setOnAction(e -> viewFarmerNotifications(farmer));
            requestSupportButton.setOnAction(e -> requestSupport(farmer));
            viewSupportRequestsButton.setOnAction(e -> viewSupportRequests(farmer));
            logout.setOnAction(e -> {
                showAlert("Logout", "Logging out...");
                farmerMenu.getScene().getWindow().hide(); // Close the government menu
            });        
            farmerMenu.getChildren().addAll(
                    viewDetailsButton,
                    viewSubsidyButton,
                    viewNotificationsButton,
                    requestSupportButton,
                    viewSupportRequestsButton,
                    logout
            );
        
            Scene scene = new Scene(farmerMenu, 300, 400);
            farmerStage.setScene(scene);
            farmerStage.show();
        }
        
        private void viewFarmerDetails(Farmer farmer) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Farmer Details");
            alert.setHeaderText("Details of Farmer ID: " + farmer.getID());
            alert.setContentText("Name: " + farmer.name +
                                 "\nContact Number: " + farmer.contactno +
                                 "\nEmail ID: " + farmer.emailid +
                                 "\nState: " + farmer.state +
                                 "\nCity: " + farmer.city +
                         "\nAge: " + farmer.age +
                 "\nID: " + farmer.getID());
alert.showAndWait();
}

private void viewFarmerSubsidy(Farmer farmer) {
  // Check if the farmer has a subsidy amount
  if (farmer.subsidyAmt <= 0) {
    showAlert("No Subsidy", "This farmer has no subsidies allocated.");
    return;
}

// Display the subsidy amount
String message = "Farmer ID: " + farmer.ID + "\n" +
                 "Farmer Name: " + farmer.name + "\n" +
                 "Subsidy Amount: Rs " + farmer.subsidyAmt;

showAlert("Farmer Subsidy Details", message);
}

private void viewFarmerNotifications(Farmer farmer) {
StringBuilder notifications = new StringBuilder();
for (String notification : farmer.notifications) { // Directly accessing notifications
notifications.append("- ").append(notification).append("\n");
}

Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Farmer Notifications");
alert.setHeaderText("Notifications for Farmer ID: " + farmer.getID());
alert.setContentText(notifications.length() > 0 ? notifications.toString() : "No notifications available.");
alert.showAndWait();
}

private void requestSupport(Farmer farmer) {
Dialog<String> dialog = new Dialog<>();
dialog.setTitle("Request Support");
dialog.setHeaderText("Please describe your issue:");

ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

TextArea issueField = new TextArea();
issueField.setPromptText("Describe your issue here...");
dialog.getDialogPane().setContent(issueField);

dialog.setResultConverter(dialogButton -> {
if (dialogButton == submitButtonType) {
return issueField.getText();
}
return null;
});

Optional<String> result = dialog.showAndWait();
result.ifPresent(issue -> {
farmer.requestSupport(issue); // Call to requestSupport method
showAlert("Support Request Submitted", "Your support request has been submitted.");
});
}

private void viewSupportRequests(Farmer farmer) {
StringBuilder supportRequests = new StringBuilder();
for (String request : farmer.supportRequests) { // Directly accessing support requests
supportRequests.append("- ").append(request).append("\n");
}

Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Support Requests");
alert.setHeaderText("Support Requests for Farmer ID: " + farmer.getID());
alert.setContentText(supportRequests.length() > 0 ? supportRequests.toString() : "No support requests found.");
alert.showAndWait();
}
    private void governmentMenu() {
        VBox govtMenu = new VBox(10);
        Button viewDetails = new Button("View Details");
        Button enterData = new Button("Enter Resource Data");
        Button distributeResource = new Button("Distribute Resource");
        Button allocateSubsidy = new Button("Allocate Subsidy");
        Button viewFarmers = new Button("View Farmers");
        Button trackProduction = new Button("Track Crop Production");
        Button generateReport = new Button("Generate Report");
        Button sendNotification = new Button("Send Notification");
        Button respondSupport = new Button("Respond to Support Request");
        Button logout = new Button("Logout");
 
 
        viewDetails.setOnAction(e -> viewDetails());
        enterData.setOnAction(e -> enterData());
        distributeResource.setOnAction(e -> distributeResource());
        allocateSubsidy.setOnAction(e -> allocateSubsidy());
        viewFarmers.setOnAction(e -> viewFarmers());
        trackProduction.setOnAction(e -> trackCropProduction());
        generateReport.setOnAction(e -> generateReport());
        sendNotification.setOnAction(e -> sendNotification());
        respondSupport.setOnAction(e -> government.respondToSupportRequest());
        logout.setOnAction(e -> {
            showAlert("Logout", "Logging out...");
            govtMenu.getScene().getWindow().hide(); // Close the government menu
        });
 
 
        govtMenu.getChildren().addAll(viewDetails, enterData, distributeResource, allocateSubsidy, viewFarmers, trackProduction, generateReport, sendNotification, respondSupport, logout);
        Scene scene = new Scene(govtMenu, 300, 400);
        Stage govtStage = new Stage();
        govtStage.setScene(scene);
        govtStage.setTitle("Government Menu");
        govtStage.show();
    }
    private void viewDetails() {
        if (government == null) {
            showError("No government registered. Please register first.");
            return;
        }
    
        // Assuming you have appropriate methods to display government details
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Government Details");
        alert.setHeaderText("Details for Government ID: " + government.getGovtID());
    
        StringBuilder content = new StringBuilder();
        content.append("Name: ").append(government.getName()).append("\n");
        content.append("Contact Number: ").append(government.getContactNo()).append("\n");
        content.append("Email ID: ").append(government.getEmail()).append("\n");
        content.append("Department: ").append(government.getDepartment()).append("\n");
    
        alert.setContentText(content.toString());
        alert.showAndWait();
    }
    
     private void enterData() {
        government.enterData();
        showAlert("Data Entry", "Resource data has been entered.");
    }
    private void distributeResource() {
        government.distributeResource();
        showAlert("Resource Distribution", "Resource distribution process completed.");
    }

    private void allocateSubsidy() {
        government.allocateSubsidy();
        showAlert("Subsidy Allocation", "Subsidy allocated successfully.");
    }

    private void viewFarmers() {
        government.viewFarmers();
    }

 
    private void trackCropProduction() {
        Optional<String> cropResult = showInputDialog("Track Crop Production", "Enter crop name:");
        cropResult.ifPresent(cropName -> {
            Optional<String> quantityResult = showInputDialog("Enter Quantity", "Enter quantity for " + cropName + ":");
            quantityResult.ifPresent(qty -> {
                try {
                    int quantity = Integer.parseInt(qty);
                    government.trackCropProduction(cropName, quantity);
                    showAlert("Success", "Crop production tracked successfully.");
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid quantity.");
                }
            });
        });
    }
    private void generateReport() {
        government.generateReport();
        showAlert("Report Generation", "Crop production report generated.");
    }

    private void sendNotification() {
        government.sendNotification();
        showAlert("Notification", "Notification sent to the farmer.");
    }

    private Optional<String> showInputDialog(String title, String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(contentText);
        return dialog.showAndWait();
    }
    private void displayWeatherData(String weatherData) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Weather Forecast");
        alert.setHeaderText("Weather Updates");
        alert.setContentText(weatherData);
        alert.showAndWait();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
 }
 