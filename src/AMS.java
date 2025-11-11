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

    void viewDetails() {
        return;
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
    public String getName() { return name; }
    public long getContactNo() { return contactno; }
    public String getEmail() { return emailid; }

    void allocateSubsidy(double amt) {
        subsidyAmt = amt;
    }

    public void receiveNotification(String message) {
        notifications.add(message);
    }

    public boolean login(String enteredPassword) {
        return enteredPassword.equals(password);
    }

    void requestSupport(String issue) {
        supportRequests.add(issue);
    }

    public void receiveResponse(String response) {
        notifications.add("Response: " + response);
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
    public List<Farmer> getFarmers() { return farmers; }
    public List<Resource> getResources() { return resources; }

    public static void addGovt(Govt govt) {
        if (govt != null) {
            governmentList.add(govt);
        }
    }

    public boolean login(String enteredPassword) {
        return enteredPassword.equals(password);
    }

    public void addFarmer(Farmer f) {
        farmers.add(f);
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void trackCropProduction(String cropName, int quantity) {
        cropProductionReports.add("Crop: " + cropName + ", Quantity: " + quantity);
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== Crop Production Report ===\n");
        if (cropProductionReports.isEmpty()) {
            report.append("No crop production data available.\n");
        } else {
            for (String entry : cropProductionReports) {
                report.append(entry).append("\n");
            }
        }
        report.append("==============================");
        return report.toString();
    }
}

// Resource class
class Resource {
    private String name;
    private int quantity;

    public Resource(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }

    public boolean distribute(int amount) {
        if (amount <= quantity) {
            quantity -= amount;
            return true;
        }
        return false;
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
        marketData.append("MARKET PRICE FOR DIFFERENT PRODUCTS:\n\n");
        String highestProduct = "";
        double highestPrice = 0;

        for (int i = 0; i < days; i++) {
            marketData.append(String.format("Day %d:\n", i + 1));
            for (String product : PRODUCTS) {
                double price = generatePrice(product);
                marketData.append(String.format("  %s: Rs %.2f\n", product, price));

                if (price > highestPrice) {
                    highestPrice = price;
                    highestProduct = product;
                }
            }
            marketData.append("\n");
        }
        marketData.append(String.format("Recommended crop based on market price: %s", highestProduct));
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
        weatherData.append("WEATHER FORECAST FOR NEXT ").append(days).append(" DAYS:\n\n");
        for (int i = 0; i < weatherReports.size(); i++) {
            WeatherData report = weatherReports.get(i);
            weatherData.append(String.format("Day %d: %s | %.2f°C\n", 
                i + 1, report.getCondition(), report.getTemperature()));
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
public class AMS extends Application {
    private static Govt government = new Govt();
    private static GenericList<Farmer> farmers = new GenericList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Agriculture Management System");

        VBox mainMenu = new VBox(10);
        mainMenu.setStyle("-fx-background-color: #E8F5E9; -fx-padding: 20;");
        mainMenu.setPadding(new Insets(20));

        Label titleLabel = new Label("🌾 Agriculture Management System 🌾");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button govtRegButton = createStyledButton("Government Registration", "#4CAF50");
        Button farmerRegButton = createStyledButton("Farmer Registration", "#2196F3");
        Button farmerLoginButton = createStyledButton("Farmer Login", "#FF9800");
        Button govtLoginButton = createStyledButton("Government Login", "#9C27B0");
        Button weatherButton = createStyledButton("Weather Updates", "#00BCD4");
        Button cropAndMarketButton = createStyledButton("Crop and Market Info", "#FFC107");
        Button exitButton = createStyledButton("Exit", "#F44336");

        govtRegButton.setOnAction(e -> governmentRegistration());
        farmerRegButton.setOnAction(e -> farmerRegistration());
        farmerLoginButton.setOnAction(e -> farmerLogin());
        govtLoginButton.setOnAction(e -> governmentLogin());
        weatherButton.setOnAction(e -> weatherUpdates());
        cropAndMarketButton.setOnAction(e -> cropAndMarketInfo());
        exitButton.setOnAction(e -> primaryStage.close());

        mainMenu.getChildren().addAll(titleLabel, govtRegButton, farmerRegButton, farmerLoginButton, 
                                      govtLoginButton, weatherButton, cropAndMarketButton, exitButton);

        Scene scene = new Scene(mainMenu, 400, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                       "-fx-font-size: 14px; -fx-padding: 10; -fx-min-width: 350px;");
        return button;
    }

    private void governmentRegistration() {
        Dialog<Govt> dialog = new Dialog<>();
        dialog.setTitle("Government Registration");
        dialog.setHeaderText("Please enter your government details:");

        ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

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
                String errorMessage = validateGovtInputs(nameField, contactField, emailField, 
                                                        departmentField, passwordField);

                if (!errorMessage.isEmpty()) {
                    showError(errorMessage);
                    return null;
                }

                Govt govt = new Govt();
                govt.setName(nameField.getText());
                govt.setContactNo(Long.parseLong(contactField.getText()));
                govt.setEmail(emailField.getText());
                govt.setDepartment(departmentField.getText());
                govt.setPassword(passwordField.getText());
                Govt.count++;

                govt.setGovtID((govt.getDepartment().substring(0, 3).toUpperCase() + 
                             "00" + Govt.count).toUpperCase());

                Govt.addGovt(govt);
                government = govt;
                
                showAlert("Registration Successful", 
                         "Government registered successfully!\n\nYour ID: " + govt.getGovtID() +
                         "\n\nPlease save this ID for login.");
                return govt;
            }
            return null;
        });

        dialog.showAndWait();
    }

    private String validateGovtInputs(TextField nameField, TextField contactField,
                                     TextField emailField, TextField departmentField,
                                     PasswordField passwordField) {
        StringBuilder errorMessage = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            errorMessage.append("• Name cannot be empty.\n");
        }

        if (!contactField.getText().matches("\\d{10}")) {
            errorMessage.append("• Contact number must be 10 digits.\n");
        }

        if (!emailField.getText().matches(".*@.*\\..*")) {
            errorMessage.append("• Please enter a valid email address.\n");
        }

        if (departmentField.getText().length() < 3) {
            errorMessage.append("• Department name must be at least 3 characters.\n");
        }

        if (passwordField.getText().isEmpty()) {
            errorMessage.append("• Password cannot be empty.\n");
        }

        return errorMessage.toString();
    }

    private void farmerRegistration() {
        Dialog<Farmer> dialog = new Dialog<>();
        dialog.setTitle("Farmer Registration");
        dialog.setHeaderText("Please enter your details:");

        ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

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
                String errorMessage = validateFarmerInputs(nameField, contactField, emailField,
                                                           stateField, cityField, ageField, passwordField);

                if (!errorMessage.isEmpty()) {
                    showError(errorMessage);
                    return null;
                }

                Farmer farmer = new Farmer();
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
                
                showAlert("Registration Successful", 
                         "Farmer registered successfully!\n\nYour Farmer ID: " + farmer.getID() +
                         "\n\nPlease save this ID for login.");
                return farmer;
            }
            return null;
        });

        dialog.showAndWait();
    }

    private String validateFarmerInputs(TextField nameField, TextField contactField, TextField emailField,
                                        TextField stateField, TextField cityField, TextField ageField,
                                        PasswordField passwordField) {
        StringBuilder errorMessage = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            errorMessage.append("• Name cannot be empty.\n");
        }

        if (!contactField.getText().matches("\\d{10}")) {
            errorMessage.append("• Contact number must be 10 digits.\n");
        }

        if (!emailField.getText().matches(".*@.*\\..*")) {
            errorMessage.append("• Please enter a valid email address.\n");
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            if (age <= 0 || age > 150) {
                errorMessage.append("• Age must be between 1 and 150.\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("• Age must be a valid number.\n");
        }

        if (stateField.getText().isEmpty()) {
            errorMessage.append("• State cannot be empty.\n");
        }

        if (cityField.getText().isEmpty()) {
            errorMessage.append("• City cannot be empty.\n");
        }

        if (passwordField.getText().isEmpty()) {
            errorMessage.append("• Password cannot be empty.\n");
        }

        return errorMessage.toString();
    }

    private void farmerLogin() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Farmer Login");
        dialog.setHeaderText("Please enter your credentials");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField idField = new TextField();
        idField.setPromptText("Farmer ID");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

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
            try {
                Farmer farmer = farmers.findById(Integer.parseInt(pair.getKey()));
                if (farmer != null && farmer.login(pair.getValue())) {
                    farmerMenu(farmer);
                } else {
                    showAlert("Login Failed", "Invalid Farmer ID or password.");
                }
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter a valid numeric Farmer ID.");
            }
        });
    }

    private void governmentLogin() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Government Login");
        dialog.setHeaderText("Please enter your credentials");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField idField = new TextField();
        idField.setPromptText("Government ID");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

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
            Govt govt = findGovernmentByID(pair.getKey());
            if (govt != null && govt.login(pair.getValue())) {
                governmentMenu(govt);
            } else {
                showAlert("Login Failed", "Invalid Government ID or password.");
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
        TextInputDialog dialog = new TextInputDialog("7");
        dialog.setTitle("Weather Updates");
        dialog.setHeaderText("Weather Forecast");
        dialog.setContentText("Enter number of days (1-15):");

        Optional<String> result = dialog.showAndWait();
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
                            javafx.application.Platform.runLater(() -> 
                                showAlert("Error", "Weather data generation was interrupted."));
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
        alert.setTitle("Crop & Market Information");
        alert.setHeaderText("What information would you like?");
        
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
        TextInputDialog dialog = new TextInputDialog("3");
        dialog.setTitle("Market Prices");
        dialog.setHeaderText("Market Price Information");
        dialog.setContentText("Enter number of days (1-3):");

        Optional<String> result = dialog.showAndWait();
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
                            javafx.application.Platform.runLater(() -> 
                                showAlert("Error", "Market data generation was interrupted."));
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
        
        TextArea textArea = new TextArea(marketData);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(400, 300);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    private void cropRecommendation() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("loamy", "clay", "sandy", "loamy");
        dialog.setTitle("Crop Recommendations");
        dialog.setHeaderText("Soil Type Selection");
        dialog.setContentText("Select your soil type:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(soilType -> {
            String recommendations = getCropRecommendations(soilType);
            showAlert("Crop Recommendations", recommendations);
        });
    }

    private String getCropRecommendations(String soilType) {
        switch (soilType.toLowerCase()) {
            case "clay":
                return "Recommended Crops for Clay Soil:\n\n• Rice\n• Wheat\n• Soybean";
            case "sandy":
                return "Recommended Crops for Sandy Soil:\n\n• Carrots\n• Onions\n• Potatoes";
            case "loamy":
                return "Recommended Crops for Loamy Soil:\n\n• Corn\n• Tomatoes\n• Beans";
            default:
                return "No recommendations available for the specified soil type.";
        }
    }

    private void farmerMenu(Farmer farmer) {
        Stage farmerStage = new Stage();
        farmerStage.setTitle("Farmer Menu - ID: " + farmer.getID());

        VBox farmerMenu = new VBox(10);
        farmerMenu.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 20;");
        farmerMenu.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + farmer.getName() + "!");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button viewDetailsButton = createStyledButton("View My Details", "#2196F3");
        Button viewSubsidyButton = createStyledButton("View Subsidy Amount", "#4CAF50");
        Button viewNotificationsButton = createStyledButton("View Notifications", "#FF9800");
        Button requestSupportButton = createStyledButton("Request Support", "#9C27B0");
        Button viewSupportRequestsButton = createStyledButton("View My Support Requests", "#00BCD4");
        Button logout = createStyledButton("Logout", "#F44336");

        viewDetailsButton.setOnAction(e -> viewFarmerDetails(farmer));
        viewSubsidyButton.setOnAction(e -> viewFarmerSubsidy(farmer));
        viewNotificationsButton.setOnAction(e -> viewFarmerNotifications(farmer));
        requestSupportButton.setOnAction(e -> requestSupport(farmer));
        viewSupportRequestsButton.setOnAction(e -> viewSupportRequests(farmer));
        logout.setOnAction(e -> farmerStage.close());

        farmerMenu.getChildren().addAll(welcomeLabel, viewDetailsButton, viewSubsidyButton, 
                                        viewNotificationsButton, requestSupportButton, 
                                        viewSupportRequestsButton, logout);

        Scene scene = new Scene(farmerMenu, 400, 450);
        farmerStage.setScene(scene);
        farmerStage.show();
    }

    private void viewFarmerDetails(Farmer farmer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Farmer Details");
        alert.setHeaderText("Your Profile Information");
        
        String details = String.format(
            "Farmer ID: %d\nName: %s\nContact: %d\nEmail: %s\nState: %s\nCity: %s\nAge: %d",
            farmer.getID(), farmer.getName(), farmer.getContactNo(), 
            farmer.getEmail(), farmer.getState(), farmer.getCity(), farmer.getAge()
        );
        
        alert.setContentText(details);
        alert.showAndWait();
    }

    private void viewFarmerSubsidy(Farmer farmer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Subsidy Information");
        
        if (farmer.getSubsidyAmt() <= 0) {
            alert.setHeaderText("No Subsidy Allocated");
            alert.setContentText("You currently have no subsidies allocated.");
        } else {
            alert.setHeaderText("Subsidy Details");
            String message = String.format(
                "Farmer ID: %d\nFarmer Name: %s\nSubsidy Amount: Rs %.2f",
                farmer.getID(), farmer.getName(), farmer.getSubsidyAmt()
            );
            alert.setContentText(message);
        }
        
        alert.showAndWait();
    }

    private void viewFarmerNotifications(Farmer farmer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifications");
        alert.setHeaderText("Your Notifications");
        
        if (farmer.getNotifications().isEmpty()) {
            alert.setContentText("No notifications available.");
        } else {
            StringBuilder notifications = new StringBuilder();
            for (int i = 0; i < farmer.getNotifications().size(); i++) {
                notifications.append((i + 1)).append(". ")
                           .append(farmer.getNotifications().get(i)).append("\n\n");
            }
            
            TextArea textArea = new TextArea(notifications.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefSize(400, 300);
            
            alert.getDialogPane().setContent(textArea);
        }
        
        alert.showAndWait();
    }

    private void requestSupport(Farmer farmer) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Request Support");
        dialog.setHeaderText("Describe your issue:");

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        TextArea issueField = new TextArea();
        issueField.setPromptText("Describe your issue here...");
        issueField.setPrefRowCount(5);
        issueField.setWrapText(true);
        
        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Issue Description:"), issueField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType && !issueField.getText().trim().isEmpty()) {
                return issueField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(issue -> {
            farmer.requestSupport(issue);
            showAlert("Success", "Your support request has been submitted successfully.");
        });
    }

    private void viewSupportRequests(Farmer farmer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Support Requests");
        alert.setHeaderText("Your Support Requests");
        
        if (farmer.getSupportRequests().isEmpty()) {
            alert.setContentText("No support requests found.");
        } else {
            StringBuilder requests = new StringBuilder();
            for (int i = 0; i < farmer.getSupportRequests().size(); i++) {
                requests.append((i + 1)).append(". ")
                       .append(farmer.getSupportRequests().get(i)).append("\n\n");
            }
            
            TextArea textArea = new TextArea(requests.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefSize(400, 300);
            
            alert.getDialogPane().setContent(textArea);
        }
        
        alert.showAndWait();
    }

    private void governmentMenu(Govt govt) {
        Stage govtStage = new Stage();
        govtStage.setTitle("Government Menu - " + govt.getGovtID());

        VBox govtMenu = new VBox(10);
        govtMenu.setStyle("-fx-background-color: #F3E5F5; -fx-padding: 20;");
        govtMenu.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + govt.getDepartment() + " Department");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button viewDetails = createStyledButton("View My Details", "#9C27B0");
        Button enterData = createStyledButton("Add Resources", "#4CAF50");
        Button distributeResource = createStyledButton("Distribute Resource", "#2196F3");
        Button allocateSubsidy = createStyledButton("Allocate Subsidy", "#FF9800");
        Button viewFarmers = createStyledButton("View All Farmers", "#00BCD4");
        Button trackProduction = createStyledButton("Track Crop Production", "#8BC34A");
        Button generateReport = createStyledButton("Generate Report", "#FFC107");
        Button sendNotification = createStyledButton("Send Notification", "#FF5722");
        Button respondSupport = createStyledButton("Respond to Support Request", "#3F51B5");
        Button logout = createStyledButton("Logout", "#F44336");

        viewDetails.setOnAction(e -> viewGovtDetails(govt));
        enterData.setOnAction(e -> enterResourceData(govt));
        distributeResource.setOnAction(e -> distributeResource(govt));
        allocateSubsidy.setOnAction(e -> allocateSubsidy(govt));
        viewFarmers.setOnAction(e -> viewFarmers(govt));
        trackProduction.setOnAction(e -> trackCropProduction(govt));
        generateReport.setOnAction(e -> generateReport(govt));
        sendNotification.setOnAction(e -> sendNotification(govt));
        respondSupport.setOnAction(e -> respondToSupportRequest(govt));
        logout.setOnAction(e -> govtStage.close());

        govtMenu.getChildren().addAll(welcomeLabel, viewDetails, enterData, distributeResource, 
                                      allocateSubsidy, viewFarmers, trackProduction, generateReport, 
                                      sendNotification, respondSupport, logout);

        Scene scene = new Scene(govtMenu, 400, 600);
        govtStage.setScene(scene);
        govtStage.show();
    }

    private void viewGovtDetails(Govt govt) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Government Details");
        alert.setHeaderText("Department Information");
        
        String details = String.format(
            "Government ID: %s\nName: %s\nContact: %d\nEmail: %s\nDepartment: %s",
            govt.getGovtID(), govt.getName(), govt.getContactNo(), 
            govt.getEmail(), govt.getDepartment()
        );
        
        alert.setContentText(details);
        alert.showAndWait();
    }

    private void enterResourceData(Govt govt) {
        Dialog<Resource> dialog = new Dialog<>();
        dialog.setTitle("Add Resource");
        dialog.setHeaderText("Enter resource details:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nameField = new TextField();
        nameField.setPromptText("Resource Name");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        grid.add(new Label("Resource Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(quantityField, 1, 1);
        
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String name = nameField.getText().trim();
                    int quantity = Integer.parseInt(quantityField.getText().trim());
                    
                    if (name.isEmpty()) {
                        showError("Resource name cannot be empty.");
                        return null;
                    }
                    
                    if (quantity <= 0) {
                        showError("Quantity must be greater than 0.");
                        return null;
                    }
                    
                    Resource resource = new Resource(name, quantity);
                    govt.addResource(resource);
                    showAlert("Success", "Resource added successfully!");
                    return resource;
                } catch (NumberFormatException e) {
                    showError("Please enter a valid quantity.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void distributeResource(Govt govt) {
        if (govt.getFarmers().isEmpty()) {
            showError("No farmers registered.");
            return;
        }
        
        if (govt.getResources().isEmpty()) {
            showError("No resources available. Please add resources first.");
            return;
        }

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Distribute Resource");
        dialog.setHeaderText("Enter distribution details:");

        ButtonType distributeButtonType = new ButtonType("Distribute", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(distributeButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField farmerIdField = new TextField();
        farmerIdField.setPromptText("Farmer ID");
        
        ComboBox<String> resourceCombo = new ComboBox<>();
        for (Resource r : govt.getResources()) {
            resourceCombo.getItems().add(r.getName());
        }
        resourceCombo.setPromptText("Select Resource");
        
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        grid.add(new Label("Farmer ID:"), 0, 0);
        grid.add(farmerIdField, 1, 0);
        grid.add(new Label("Resource:"), 0, 1);
        grid.add(resourceCombo, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == distributeButtonType) {
                try {
                    int farmerId = Integer.parseInt(farmerIdField.getText().trim());
                    String resourceName = resourceCombo.getValue();
                    int amount = Integer.parseInt(amountField.getText().trim());
                    
                    if (resourceName == null) {
                        showError("Please select a resource.");
                        return false;
                    }
                    
                    Farmer farmer = null;
                    for (Farmer f : govt.getFarmers()) {
                        if (f.getID() == farmerId) {
                            farmer = f;
                            break;
                        }
                    }
                    
                    if (farmer == null) {
                        showError("Farmer ID not found.");
                        return false;
                    }
                    
                    Resource resource = null;
                    for (Resource r : govt.getResources()) {
                        if (r.getName().equals(resourceName)) {
                            resource = r;
                            break;
                        }
                    }
                    
                    if (amount <= 0) {
                        showError("Amount must be greater than 0.");
                        return false;
                    }
                    
                    if (amount > resource.getQuantity()) {
                        showError("Insufficient quantity. Available: " + resource.getQuantity());
                        return false;
                    }
                    
                    resource.distribute(amount);
                    String notification = String.format("%d units of %s distributed to you.", amount, resourceName);
                    farmer.receiveNotification(notification);
                    
                    showAlert("Success", String.format("Distributed %d of %s to Farmer ID: %d", 
                                                       amount, resourceName, farmerId));
                    return true;
                } catch (NumberFormatException e) {
                    showError("Please enter valid numeric values.");
                    return false;
                }
            }
            return false;
        });

        dialog.showAndWait();
    }

    private void allocateSubsidy(Govt govt) {
        if (govt.getFarmers().isEmpty()) {
            showError("No farmers registered.");
            return;
        }

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Allocate Subsidy");
        dialog.setHeaderText("Enter subsidy details:");

        ButtonType allocateButtonType = new ButtonType("Allocate", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(allocateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField farmerIdField = new TextField();
        farmerIdField.setPromptText("Farmer ID");
        TextField amountField = new TextField();
        amountField.setPromptText("Subsidy Amount (Rs)");

        grid.add(new Label("Farmer ID:"), 0, 0);
        grid.add(farmerIdField, 1, 0);
        grid.add(new Label("Amount (Rs):"), 0, 1);
        grid.add(amountField, 1, 1);
        
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == allocateButtonType) {
                try {
                    int farmerId = Integer.parseInt(farmerIdField.getText().trim());
                    double amount = Double.parseDouble(amountField.getText().trim());
                    
                    if (amount <= 0) {
                        showError("Amount must be greater than 0.");
                        return false;
                    }
                    
                    Farmer farmer = null;
                    for (Farmer f : govt.getFarmers()) {
                        if (f.getID() == farmerId) {
                            farmer = f;
                            break;
                        }
                    }
                    
                    if (farmer == null) {
                        showError("Farmer ID not found.");
                        return false;
                    }
                    
                    farmer.allocateSubsidy(amount);
                    String notification = String.format("Subsidy of Rs %.2f has been allocated to you.", amount);
                    farmer.receiveNotification(notification);
                    
                    showAlert("Success", String.format("Subsidy of Rs %.2f allocated to Farmer ID: %d", 
                                                       amount, farmerId));
                    return true;
                } catch (NumberFormatException e) {
                    showError("Please enter valid numeric values.");
                    return false;
                }
            }
            return false;
        });

        dialog.showAndWait();
    }

    private void viewFarmers(Govt govt) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registered Farmers");
        alert.setHeaderText("List of All Farmers");
        
        if (govt.getFarmers().isEmpty()) {
            alert.setContentText("No farmers registered.");
        } else {
            StringBuilder farmersList = new StringBuilder();
            for (Farmer f : govt.getFarmers()) {
                farmersList.append(String.format(
                    "ID: %d | Name: %s | State: %s | City: %s | Age: %d\n" +
                    "Contact: %d | Email: %s\n" +
                    "Subsidy: Rs %.2f\n\n",
                    f.getID(), f.getName(), f.getState(), f.getCity(), f.getAge(),
                    f.getContactNo(), f.getEmail(), f.getSubsidyAmt()
                ));
            }
            
            TextArea textArea = new TextArea(farmersList.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefSize(500, 400);
            
            alert.getDialogPane().setContent(textArea);
        }
        
        alert.showAndWait();
    }

    private void trackCropProduction(Govt govt) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Track Crop Production");
        dialog.setHeaderText("Enter crop production details:");

        ButtonType trackButtonType = new ButtonType("Track", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(trackButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField cropNameField = new TextField();
        cropNameField.setPromptText("Crop Name");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity (kg)");

        grid.add(new Label("Crop Name:"), 0, 0);
        grid.add(cropNameField, 1, 0);
        grid.add(new Label("Quantity (kg):"), 0, 1);
        grid.add(quantityField, 1, 1);
        
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == trackButtonType) {
                try {
                    String cropName = cropNameField.getText().trim();
                    int quantity = Integer.parseInt(quantityField.getText().trim());
                    
                    if (cropName.isEmpty()) {
                        showError("Crop name cannot be empty.");
                        return false;
                    }
                    
                    if (quantity <= 0) {
                        showError("Quantity must be greater than 0.");
                        return false;
                    }
                    
                    govt.trackCropProduction(cropName, quantity);
                    showAlert("Success", String.format("Tracked: %s - %d kg", cropName, quantity));
                    return true;
                } catch (NumberFormatException e) {
                    showError("Please enter a valid quantity.");
                    return false;
                }
            }
            return false;
        });

        dialog.showAndWait();
    }

    private void generateReport(Govt govt) {
        String report = govt.generateReport();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Crop Production Report");
        alert.setHeaderText("Generated Report");
        
        TextArea textArea = new TextArea(report);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(400, 300);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    private void sendNotification(Govt govt) {
        if (govt.getFarmers().isEmpty()) {
            showError("No farmers registered.");
            return;
        }

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Send Notification");
        dialog.setHeaderText("Send notification to a farmer:");

        ButtonType sendButtonType = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(sendButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField farmerIdField = new TextField();
        farmerIdField.setPromptText("Farmer ID");
        TextArea messageField = new TextArea();
        messageField.setPromptText("Notification message...");
        messageField.setPrefRowCount(4);
        messageField.setWrapText(true);

        grid.add(new Label("Farmer ID:"), 0, 0);
        grid.add(farmerIdField, 1, 0);
        grid.add(new Label("Message:"), 0, 1);
        grid.add(messageField, 1, 1);
        
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == sendButtonType) {
                try {
                    int farmerId = Integer.parseInt(farmerIdField.getText().trim());
                    String message = messageField.getText().trim();
                    
                    if (message.isEmpty()) {
                        showError("Message cannot be empty.");
                        return false;
                    }
                    
                    Farmer farmer = null;
                    for (Farmer f : govt.getFarmers()) {
                        if (f.getID() == farmerId) {
                            farmer = f;
                            break;
                        }
                    }
                    
                    if (farmer == null) {
                        showError("Farmer ID not found.");
                        return false;
                    }
                    
                    farmer.receiveNotification(message);
                    showAlert("Success", "Notification sent to Farmer ID: " + farmerId);
                    return true;
                } catch (NumberFormatException e) {
                    showError("Please enter a valid Farmer ID.");
                    return false;
                }
            }
            return false;
        });

        dialog.showAndWait();
    }

    private void respondToSupportRequest(Govt govt) {
        if (govt.getFarmers().isEmpty()) {
            showError("No farmers registered.");
            return;
        }

        // Find farmers with support requests
        List<Farmer> farmersWithRequests = new ArrayList<>();
        for (Farmer f : govt.getFarmers()) {
            if (!f.getSupportRequests().isEmpty()) {
                farmersWithRequests.add(f);
            }
        }

        if (farmersWithRequests.isEmpty()) {
            showAlert("No Requests", "No support requests available.");
            return;
        }

        // Step 1: Select farmer
        ChoiceDialog<String> farmerDialog = new ChoiceDialog<>();
        farmerDialog.setTitle("Respond to Support Request");
        farmerDialog.setHeaderText("Select a farmer:");
        
        for (Farmer f : farmersWithRequests) {
            farmerDialog.getItems().add(f.getID() + " - " + f.getName());
        }
        
        Optional<String> farmerResult = farmerDialog.showAndWait();
        if (!farmerResult.isPresent()) return;
        
        int selectedFarmerId = Integer.parseInt(farmerResult.get().split(" - ")[0]);
        Farmer selectedFarmer = null;
        for (Farmer f : farmersWithRequests) {
            if (f.getID() == selectedFarmerId) {
                selectedFarmer = f;
                break;
            }
        }

        if (selectedFarmer == null) return;

        // Step 2: Select request
        ChoiceDialog<String> requestDialog = new ChoiceDialog<>();
        requestDialog.setTitle("Select Request");
        requestDialog.setHeaderText("Select a support request:");
        
        for (int i = 0; i < selectedFarmer.getSupportRequests().size(); i++) {
            requestDialog.getItems().add((i + 1) + ". " + selectedFarmer.getSupportRequests().get(i));
        }
        
        Optional<String> requestResult = requestDialog.showAndWait();
        if (!requestResult.isPresent()) return;

        // Step 3: Enter response
        TextInputDialog responseDialog = new TextInputDialog();
        responseDialog.setTitle("Enter Response");
        responseDialog.setHeaderText("Enter your response to the request:");
        responseDialog.setContentText("Response:");
        
        Optional<String> responseResult = responseDialog.showAndWait();
        if (responseResult.isPresent() && !responseResult.get().trim().isEmpty()) {
            selectedFarmer.receiveResponse(responseResult.get());
            showAlert("Success", "Response sent to Farmer ID: " + selectedFarmerId);
        }
    }

    private void displayWeatherData(String weatherData) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Weather Forecast");
        alert.setHeaderText("Weather Updates");
        
        TextArea textArea = new TextArea(weatherData);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(400, 300);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
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