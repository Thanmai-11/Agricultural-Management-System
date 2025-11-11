import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.*;

// Base class for common user properties
class Base {
    protected String name;
    protected long contactno;
    protected String emailid;

    protected void register(Scanner sc) {
        System.out.println("WELCOME TO THE REGISTRATION");
        System.out.print("Enter the name: ");
        name = sc.nextLine();
        contactno = getValidContactNo(sc);
        emailid = getValidEmail(sc);
    }

    void viewDetails() {
        System.out.println("Name: " + name + "\nContact number: " + contactno + "\nEmail ID: " + emailid);
    }

    private long getValidContactNo(Scanner sc) {
        while (true) {
            System.out.print("Enter the contact no: ");
            try {
                long contact = sc.nextLong();
                if (String.valueOf(contact).length() == 10) {
                    return contact;
                } else {
                    System.out.println("Contact number must be 10 digits long. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
                sc.next();
            }
        }
    }

    private String getValidEmail(Scanner sc) {
        while (true) {
            System.out.print("Enter the email ID: ");
            String email = sc.next();
            if (email.endsWith("@gmail.com") && email.contains("@")) {
                return email;
            } else {
                System.out.println("Invalid email format. Please ensure it ends with @gmail.com.");
            }
        }
    }
}

// Farmer class
class Farmer extends Base {
    private String state;
    private String city;
    private int age;
    private int ID;
    private double subsidyAmt;
    private String password;
    private List<String> supportRequests;
    private List<String> notifications;
    
    static int count = 0;

    Farmer() {
        supportRequests = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    // Setters
    public void setState(String state) { this.state = state; }
    public void setCity(String city) { this.city = city; }
    public void setAge(int age) { this.age = age; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setContactNo(long contactno) { this.contactno = contactno; }
    public void setEmail(String emailid) { this.emailid = emailid; }
    public void setID(int id) { this.ID = id; }

    // Getters
    public int getID() { return ID; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public int getAge() { return age; }
    public double getSubsidyAmt() { return subsidyAmt; }
    public List<String> getSupportRequests() { return supportRequests; }
    public List<String> getNotifications() { return notifications; }

    void allocateSubsidy(double amt) {
        subsidyAmt = amt;
    }

    void showSubsidyAmt() {
        if (subsidyAmt > 0) {
            System.out.println("Rs" + subsidyAmt + " of subsidy amount has been allocated to farmer " + ID);
        } else {
            System.out.println("No subsidy allocated");
        }
    }

    public void receiveNotification(String message) {
        notifications.add(message);
    }

    public void viewNotifications() {
        System.out.println("=== Notifications ===");
        if (notifications.isEmpty()) {
            System.out.println("No notifications\n");
            return;
        }
        for (String notification : notifications) {
            System.out.println("- " + notification);
        }
    }

    public boolean login(String enteredPassword) {
        return enteredPassword.equals(password);
    }

    @Override
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
}

// Government class
class Govt extends Base {
    private String govtID;
    private String password;
    private String dept;
    private List<Farmer> farmers;
    private List<Resource> resources;
    private List<String> cropProductionReports;
    private Scanner sc = new Scanner(System.in);
    
    static int count = 0;
    static List<Govt> governmentList = new ArrayList<>();

    public Govt() {
        farmers = new ArrayList<>();
        resources = new ArrayList<>();
        cropProductionReports = new ArrayList<>();
    }

    // Setters
    public void setGovtID(String govtID) { this.govtID = govtID; }
    public void setPassword(String password) { this.password = password; }
    public void setDepartment(String dept) { this.dept = dept; }
    public void setName(String name) { this.name = name; }
    public void setContactNo(long contactNo) { this.contactno = contactNo; }
    public void setEmail(String email) { this.emailid = email; }

    // Getters
    public String getGovtID() { return govtID; }
    public String getName() { return name; }
    public long getContactNo() { return contactno; }
    public String getEmail() { return emailid; }
    public String getDepartment() { return dept; }

    public static void addGovt(Govt govt) {
        if (govt != null) {
            governmentList.add(govt);
            System.out.println("Government added: " + govt.getGovtID());
        }
    }

    void govtRegister() {
        super.register(sc);

        while (true) {
            System.out.print("Enter the department: ");
            String inputDept = sc.next();
            if (inputDept.length() >= 3) {
                setDepartment(inputDept);
                break;
            } else {
                System.out.println("Department name must be at least 3 letters.");
            }
        }

        System.out.print("Create a strong password: ");
        password = sc.next();

        govtID = (dept.substring(0, 3).toUpperCase() + "00" + count).toUpperCase();
        System.out.println("Your ID is " + govtID + "\n");

        addGovt(this);
    }

    @Override
    void viewDetails() {
        super.viewDetails();
        System.out.println("Dept: " + dept + "\nID: " + govtID);
    }

    void enterData() {
        System.out.print("How many resources? ");
        int n = readIntegerInput(1, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            resources.add(new Resource());
        }
        System.out.println("Resource details:\n");
        for (Resource r : resources) {
            r.dispResource();
        }
    }

    public boolean login(String enteredPassword) {
        if (enteredPassword.equals(password)) {
            System.out.println("Successfully logged in as Government ID: " + govtID + "\n");
            return true;
        } else {
            System.out.println("Invalid credentials. Please try again.");
            return false;
        }
    }

    public void addFarmer(Farmer f) {
        farmers.add(f);
    }

    private boolean isValidFarmerID(int farmerID) {
        for (Farmer f : farmers) {
            if (f.getID() == farmerID) {
                return true;
            }
        }
        return false;
    }

    public void distributeResource() {
        int farmerID;
        while (true) {
            System.out.print("\nEnter Farmer ID: ");
            farmerID = readIntegerInput(1, Integer.MAX_VALUE);

            if (!isValidFarmerID(farmerID)) {
                System.out.println("Invalid Farmer ID: " + farmerID + ". Please try again.");
                continue;
            }
            break;
        }

        Resource resource = null;
        while (true) {
            System.out.print("Enter resource name: ");
            String resourceName = sc.next();

            for (Resource r : resources) {
                if (r.getName().equalsIgnoreCase(resourceName)) {
                    resource = r;
                    break;
                }
            }

            if (resource == null) {
                System.out.println("Resource " + resourceName + " not found. Please try again.");
                continue;
            }
            break;
        }

        int amount;
        while (true) {
            System.out.print("Enter the amount to distribute: ");
            amount = readIntegerInput(1, Integer.MAX_VALUE);

            if (amount > resource.getQuantity()) {
                System.out.println("Insufficient quantity of " + resource.getName() + ". Available: " + resource.getQuantity());
                continue;
            }
            break;
        }

        resource.distribute(amount);
        System.out.println("Distributed " + amount + " of " + resource.getName() + " to Farmer ID: " + farmerID);

        String notificationMessage = amount + " of " + resource.getName() + " has been distributed to you.";
        for (Farmer f : farmers) {
            if (f.getID() == farmerID) {
                f.receiveNotification(notificationMessage);
                System.out.println("Notification sent to farmer " + farmerID);
                break;
            }
        }
    }

    private double readDoubleInput() {
        while (true) {
            try {
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.nextLine();
            }
        }
    }

    private int readIntegerInput(int min, int max) {
        while (true) {
            try {
                int value = sc.nextInt();
                sc.nextLine();
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                sc.nextLine();
            }
        }
    }

    public void allocateSubsidy() {
        while (true) {
            System.out.print("\nEnter Farmer ID: ");
            int farmerID = readIntegerInput(1, Integer.MAX_VALUE);

            if (!isValidFarmerID(farmerID)) {
                System.out.println("Invalid Farmer ID: " + farmerID + ". Please try again.");
                continue;
            }

            System.out.print("Enter subsidy amount: ");
            double amount = readDoubleInput();

            for (Farmer f : farmers) {
                if (f.getID() == farmerID) {
                    f.allocateSubsidy(amount);
                    System.out.println("Subsidy of Rs" + amount + " allocated to Farmer ID: " + farmerID);
                    return;
                }
            }
        }
    }

    public void sendNotification() {
        while (true) {
            System.out.print("\nEnter Farmer ID: ");
            int farmerID = readIntegerInput(1, Integer.MAX_VALUE);

            if (!isValidFarmerID(farmerID)) {
                System.out.println("Invalid Farmer ID: " + farmerID + ". Please try again.");
                continue;
            }

            System.out.print("\nEnter the notification message: ");
            String message = sc.nextLine();

            for (Farmer f : farmers) {
                if (f.getID() == farmerID) {
                    f.receiveNotification(message);
                    System.out.println("Notification sent to Farmer ID: " + farmerID);
                    return;
                }
            }
        }
    }

    public void respondToSupportRequest() {
        while (true) {
            System.out.print("\nEnter Farmer ID: ");
            int farmerID = readIntegerInput(1, Integer.MAX_VALUE);

            if (!isValidFarmerID(farmerID)) {
                System.out.println("Invalid Farmer ID: " + farmerID + ". Please try again.");
                continue;
            }

            Farmer farmer = null;
            for (Farmer f : farmers) {
                if (f.getID() == farmerID) {
                    farmer = f;
                    break;
                }
            }

            if (farmer == null) {
                System.out.println("Farmer ID not found. Please try again.");
                continue;
            }

            if (farmer.getSupportRequests().isEmpty()) {
                System.out.println("No support requests available for Farmer ID: " + farmerID);
                return;
            }

            farmer.viewSupportRequests();

            System.out.print("Enter the number of the request you want to respond to (or 0 to cancel): ");
            int requestNumber = readIntegerInput(0, farmer.getSupportRequests().size());

            if (requestNumber == 0) {
                System.out.println("Response cancelled.");
                return;
            }

            System.out.print("Enter your response: ");
            String response = sc.nextLine();

            String selectedRequest = farmer.getSupportRequests().get(requestNumber - 1);
            farmer.receiveResponse(response);
            System.out.println("Response to request: '" + selectedRequest + "' has been sent to Farmer ID: " + farmerID);
            break;
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

// Resource class
class Resource {
    private Scanner sc = new Scanner(System.in);
    private String name;
    private short quantity;
    static int rcount = 0;

    Resource() {
        rcount++;
        System.out.print("\nEnter the resource: ");
        name = sc.next();
        quantity = readShortInput("Enter the quantity: ");
    }

    public String getName() { return name; }
    public short getQuantity() { return quantity; }

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
        while (true) {
            try {
                System.out.print(prompt);
                short value = sc.nextShort();
                if (value < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a valid quantity.");
                    continue;
                }
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.nextLine();
            }
        }
    }
}

// Market Service Interface
interface MarketServiceInterface {
    void generateData();
    double generatePrice(String product);
}

// Market Service
class MarketService implements Runnable, MarketServiceInterface {
    private static final String[] PRODUCTS = {"Wheat", "Rice", "Corn", "Barley", "Soybeans"};
    private Random random = new Random();
    private int days;
    private StringBuilder marketData = new StringBuilder();

    public MarketService(int days) {
        this.days = days;
    }

    @Override
    public void run() {
        generateData();
    }

    @Override
    public void generateData() {
        marketData.setLength(0);
        marketData.append("MARKET PRICE FOR DIFFERENT PRODUCTS:\n");
        String highestProduct = "";
        double highestPrice = 0;

        for (int i = 0; i < days; i++) {
            marketData.append(String.format("Day %d:\n", i + 1));
            for (String product : PRODUCTS) {
                double price = generatePrice(product);
                marketData.append(String.format("Product: %s | Price: Rs %.2f\n", product, price));
                marketData.append("-----\n");

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

    @Override
    public double generatePrice(String product) {
        double min, max;
        switch (product) {
            case "Wheat": min = 10.0; max = 20.0; break;
            case "Rice": min = 15.0; max = 25.0; break;
            case "Corn": min = 8.0; max = 15.0; break;
            case "Barley": min = 12.0; max = 18.0; break;
            case "Soybeans": min = 14.0; max = 20.0; break;
            default: min = 0; max = 1; break;
        }
        return min + (max - min) * random.nextDouble();
    }
}

// Weather Data
class WeatherData {
    private String condition;
    private double temperature;

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
}

// Weather Service
class WeatherService implements Runnable {
    private List<WeatherData> weatherReports = new ArrayList<>();
    private Random random = new Random();
    private int days;
    private StringBuilder weatherData = new StringBuilder();
    private static final String[] CONDITIONS = {"Sunny", "Rainy", "Cloudy", "Windy", "Stormy"};

    public WeatherService(int days) {
        this.days = days;
    }

    @Override
    public void run() {
        generateData();
        updateWeatherData();
    }

    public void generateData() {
        weatherReports.clear();
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
        weatherData.setLength(0);
        weatherData.append("WEATHER FORECAST FOR NEXT ").append(days).append(" DAYS:\n");
        for (int i = 0; i < weatherReports.size(); i++) {
            WeatherData report = weatherReports.get(i);
            weatherData.append("Day ").append(i + 1)
                    .append(": Condition: ").append(report.getCondition())
                    .append(" | Temperature: ").append(String.format("%.2f", report.getTemperature())).append(" C\n");
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

// Generic List for type-safe storage
class GenericList<T> {
    private List<T> list = new ArrayList<>();

    public void add(T item) {
        list.add(item);
    }

    public T findById(int id) {
        for (T item : list) {
            if (item instanceof Farmer && ((Farmer) item).getID() == id) {
                return item;
            }
        }
        return null;
    }
}

// Main Application
public class AMS_prev_to_the_latest extends Application {
    private static Govt government = new Govt();
    private static GenericList<Farmer> farmers = new GenericList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Agriculture Management System");

        VBox mainMenu = new VBox(10);
        mainMenu.setStyle("-fx-background-color: #ADD8E6;");
        mainMenu.setPadding(new Insets(20));
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

        govtRegButton.setOnAction(e -> governmentRegistration());
        farmerRegButton.setOnAction(e -> farmerRegistration());
        farmerLoginButton.setOnAction(e -> farmerLogin());
        govtLoginButton.setOnAction(e -> governmentLogin());
        weatherButton.setOnAction(e -> weatherUpdates());
        cropAndMarketButton.setOnAction(e -> cropAndMarketInfo());
        exitButton.setOnAction(e -> primaryStage.close());

        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.getChildren().addAll(govtRegButton, farmerRegButton, farmerLoginButton, 
                                  govtLoginButton, weatherButton, cropAndMarketButton, exitButton);

        return menu;
    }

    private void governmentRegistration() {
        Dialog<Govt> dialog = new Dialog<>();
        dialog.setTitle("Government Registration");
        dialog.setHeaderText("Please enter your government details:");

        ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

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

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
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

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registerButtonType) {
                return new Govt();
            }
            return null;
        });

        Optional<Govt> result = dialog.showAndWait();

        if (result.isPresent()) {
            government = result.get();

            String errorMessage = validateGovtInputs(nameField, contactField, emailField, 
                                                    departmentField, passwordField);

            if (!errorMessage.isEmpty()) {
                showError(errorMessage);
                return;
            }

            government.setName(nameField.getText());
            government.setContactNo(Long.parseLong(contactField.getText()));
            government.setEmail(emailField.getText());
            government.setDepartment(departmentField.getText());
            government.setPassword(passwordField.getText());
            Govt.count++;

            government.setGovtID((government.getDepartment().substring(0, 3).toUpperCase() + 
                                 "00" + Govt.count).toUpperCase());

            Govt.addGovt(government);
            showAlert("Registration Successful", "Government registered successfully with ID: " + 
                     government.getGovtID());
        }
    }

    private String validateGovtInputs(TextField nameField, TextField contactField,
                                     TextField emailField, TextField departmentField,
                                     PasswordField passwordField) {
        StringBuilder errorMessage = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            errorMessage.append("Name cannot be empty.\n");
        }

        if (!contactField.getText().matches("\\d{10}")) {
            errorMessage.append("Contact number must be 10 digits long and numeric.\n");
        }

        if (!emailField.getText().endsWith("@govt.com") || !emailField.getText().contains("@")) {
            errorMessage.append("Email must end with @govt.com\n");
        }

        if (departmentField.getText().isEmpty()) {
            errorMessage.append("Department cannot be empty.\n");
        }

        if (passwordField.getText().isEmpty()) {
            errorMessage.append("Password cannot be empty.\n");
        }

        return errorMessage.toString();
    }

    private void farmerRegistration() {
        Dialog<Farmer> dialog = new Dialog<>();
        dialog.setTitle("Farmer Registration");
        dialog.setHeaderText("Please enter your details:");

        ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

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

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
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

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registerButtonType) {
                return new Farmer();
            }
            return null;
        });

        Optional<Farmer> result = dialog.showAndWait();

        if (result.isPresent()) {
            Farmer farmer = result.get();

            String errorMessage = validateFarmerInputs(nameField, contactField, emailField,
                                                       stateField, cityField, ageField, passwordField);

            if (!errorMessage.isEmpty()) {
                showError(errorMessage);
                return;
            }

            farmer.setName(nameField.getText());
            farmer.setContactNo(Long.parseLong(contactField.getText()));
            farmer.setEmail(emailField.getText());
            farmer.setState(stateField.getText());
            farmer.setCity(cityField.getText());
            farmer.setAge(Integer.parseInt(ageField.getText()));
            farmer.setPassword(passwordField.getText());
            Farmer.count++;
            farmer.setID(2024000 + Farmer.count);

            government.addFarmer(farmer);
            farmers.add(farmer);
            showAlert("Registration Successful", "Farmer registered successfully! Your ID is: " + farmer.getID());
        }
    }

    private String validateFarmerInputs(TextField nameField, TextField contactField, TextField emailField,
                                        TextField stateField, TextField cityField, TextField ageField,
                                        PasswordField passwordField) {
        StringBuilder errorMessage = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            errorMessage.append("Name cannot be empty.\n");
        }

        if (!contactField.getText().matches("\\d{10}")) {
            errorMessage.append("Contact number must be 10 digits long and numeric.\n");
        }

        if (!emailField.getText().endsWith("@gmail.com") || !emailField.getText().contains("@")) {
            errorMessage.append("Email must end with @gmail.com\n");
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            if (age <= 0) {
                errorMessage.append("Age must be a positive number.\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Age must be a valid number.\n");
        }

        if (stateField.getText().isEmpty()) {
            errorMessage.append("State cannot be empty.\n");
        }

        if (cityField.getText().isEmpty()) {
            errorMessage.append("City cannot be empty.\n");
        }

        if (passwordField.getText().isEmpty()) {
            errorMessage.append("Password cannot be empty.\n");
        }

        return errorMessage.toString();
    }

    private void farmerLogin() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Farmer Login");
        dialog.setHeaderText("Please enter your Farmer ID and Password.");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("Farmer ID");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Farmer ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        dialog.getDialogPane().setContent(grid);

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
                if (farmer != null && farmer.login(password)) {
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

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("Government ID");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Government ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        dialog.getDialogPane().setContent(grid);

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
            Govt govt = findGovernmentByID(id);
            if (govt != null && govt.login(password)) {
                governmentMenu();
            } else {
                showAlert("Login Failed", "Invalid Government ID or credentials.");
            }
        });
    }

    private Govt findGovernmentByID(String id) {
        for (Govt govt : Govt.governmentList) {
            if (govt.getGovtID().equals(id)) {
                return govt;
            }
        }
        return null;
    }

    private void weatherUpdates() {
        Optional<String> result = showInputDialog("Weather Updates", "Enter number of days (1-15):");
        result.ifPresent(days -> {
            try {
                int d = Integer.parseInt(days);
                if (d >= 1 && d <= 15) {
                    WeatherService ws = new WeatherService(d);

                    Thread weatherThread = new Thread(ws);
                    weatherThread.setDaemon(true);
                    weatherThread.start();

                    new Thread(() -> {
                        try {
                            weatherThread.join();
                            String weatherData = ws.getWeatherData();
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
                            marketThread.join();
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
        Optional<String> result = showInputDialog("Crop Recommendations", "Enter Soil Type (clay, sandy, loamy):");
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
            farmerStage.close();
        });

        farmerMenu.getChildren().addAll(viewDetailsButton, viewSubsidyButton, viewNotificationsButton,
                                        requestSupportButton, viewSupportRequestsButton, logout);

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
                "\nState: " + farmer.getState() +
                "\nCity: " + farmer.getCity() +
                "\nAge: " + farmer.getAge() +
                "\nID: " + farmer.getID());
        alert.showAndWait();
    }

    private void viewFarmerSubsidy(Farmer farmer) {
        if (farmer.getSubsidyAmt() <= 0) {
            showAlert("No Subsidy", "This farmer has no subsidies allocated.");
            return;
        }

        String message = "Farmer ID: " + farmer.getID() + "\n" +
                "Farmer Name: " + farmer.name + "\n" +
                "Subsidy Amount: Rs " + farmer.getSubsidyAmt();

        showAlert("Farmer Subsidy Details", message);
    }

    private void viewFarmerNotifications(Farmer farmer) {
        StringBuilder notifications = new StringBuilder();
        for (String notification : farmer.getNotifications()) {
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
            farmer.requestSupport(issue);
            showAlert("Support Request Submitted", "Your support request has been submitted.");
        });
    }

    private void viewSupportRequests(Farmer farmer) {
        StringBuilder supportRequests = new StringBuilder();
        for (String request : farmer.getSupportRequests()) {
            supportRequests.append("- ").append(request).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Support Requests");
        alert.setHeaderText("Support Requests for Farmer ID: " + farmer.getID());
        alert.setContentText(supportRequests.length() > 0 ? supportRequests.toString() : "No support requests found.");
        alert.showAndWait();
    }

    private void governmentMenu() {
        Stage govtStage = new Stage();
        govtStage.setTitle("Government Menu");

        VBox govtMenu = new VBox(10);
        govtMenu.setPadding(new Insets(20));

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

        viewDetails.setOnAction(e -> viewGovtDetails());
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
            govtStage.close();
        });

        govtMenu.getChildren().addAll(viewDetails, enterData, distributeResource, allocateSubsidy,
                viewFarmers, trackProduction, generateReport, sendNotification, respondSupport, logout);

        Scene scene = new Scene(govtMenu, 300, 400);
        govtStage.setScene(scene);
        govtStage.show();
    }

    private void viewGovtDetails() {
        if (government == null) {
            showError("No government registered. Please register first.");
            return;
        }

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

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An Error Occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
}