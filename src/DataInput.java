import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DataInput extends Application {
    @Override
    public void start(Stage primaryStage) {
        Font customFont = Font.loadFont("file:Express.ttf", 50);
        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setFont(customFont); 
        welcomeLabel.setTextFill(Color.WHITE); 

        TextField locationField = new TextField("Rr. Tiranë-Rinas, Km. 12, 1032 Vorë, Tirana, Albania"); 
        locationField.setEditable(false); 

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");

        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneField.setText(oldValue); 
            } else if (newValue.length() > 10) {
                phoneField.setText(oldValue); 
            }
        });

        Label locationLabel = new Label("Location:");
        locationLabel.setTextFill(Color.WHITE);
        Label phoneLabel = new Label("Phone:");
        phoneLabel.setTextFill(Color.WHITE);
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: white;"); 

        Button submitButton = new Button("Find Restaurants");
        submitButton.setOnAction(event -> {
            String location = locationField.getText().trim();
            String phone = phoneField.getText().trim();

            if (location.length() < 3) {
                errorLabel.setText("❌ Location must be at least 3 characters.");
                return;
            }
            if (!phone.matches("\\d{9,10}")) { 
                errorLabel.setText("❌ Phone number must be 9-10 digits long.");
                return;
            }

            errorLabel.setText(""); 
            showSuccessAlert(location, phone, primaryStage);
        });
        
        submitButton.setDefaultButton(true);

        GridPane welcomeGrid = new GridPane();
        welcomeGrid.setPadding(new Insets(20));
        welcomeGrid.setHgap(10);
        welcomeGrid.setVgap(10);
        welcomeGrid.setAlignment(Pos.TOP_CENTER); 

        welcomeGrid.add(welcomeLabel, 0, 15);

        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(20));
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setAlignment(Pos.CENTER); 

        inputGrid.add(locationLabel, 0, 5);
        inputGrid.add(locationField, 1, 5);
        inputGrid.add(phoneLabel, 0, 6);
        inputGrid.add(phoneField, 1, 6);
        inputGrid.add(submitButton, 1, 10);
        
        GridPane errorGrid = new GridPane();
        errorGrid.setPadding(new Insets(20));
        errorGrid.setHgap(10);
        errorGrid.setVgap(10);
        errorGrid.setAlignment(Pos.BOTTOM_CENTER);
        errorGrid.add(errorLabel, 0, 0);

        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(welcomeGrid, inputGrid, errorGrid); 

        StackPane root = new StackPane(vbox);
        root.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root, 400, 711);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Data Input - Find Restaurants");
        primaryStage.show();
    }

    private void showSuccessAlert(String location, String phone, Stage primaryStage) {
      
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Searching for restaurants...");
        alert.setContentText("Location: " + location + "\nPhone: " + phone);


        alert.show();

 
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
          
            alert.close();

           
            primaryStage.close();

           
            new MenuItems().start(new Stage());
        });
        pause.play();
    }
    

    
}
