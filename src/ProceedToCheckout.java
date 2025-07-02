

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProceedToCheckout extends Application 
{
	
	private Map<String, Integer> cart;
	private double totalPrice;
	
    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("Checkout");
        primaryStage.setResizable(false);
        

    
        File file = new File("Pictures/foodDeliveryIcon.png"); 
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

       
        Label addressLabel = new Label("Rr. Tiranë-Rinas, Km. 12, 1032 Vorë, Tirana, Albania");
        addressLabel.setStyle("-fx-alignment: center;");

        
        Label whenLabel = new Label("When?");
        whenLabel.setStyle("-fx-alignment: center;");
        ToggleGroup timeGroup = new ToggleGroup();
        RadioButton standardTime = new RadioButton("Standard (10-30 min)");
        RadioButton scheduleTime = new RadioButton("Schedule");
        standardTime.setToggleGroup(timeGroup);
        scheduleTime.setToggleGroup(timeGroup);
        standardTime.setSelected(true);

        TextField scheduleTextField = new TextField();
        scheduleTextField.setPromptText("Enter date & time");
        scheduleTextField.setVisible(false);

        scheduleTime.setOnAction(e -> scheduleTextField.setVisible(scheduleTime.isSelected()));
        standardTime.setOnAction(e -> scheduleTextField.setVisible(false));

        VBox timeBox = new VBox(5, whenLabel, new HBox(10, standardTime, scheduleTime), scheduleTextField);

        
        Label paymentLabel = new Label("Payment");
        paymentLabel.setMaxWidth(Double.MAX_VALUE);
        paymentLabel.setAlignment(Pos.CENTER_LEFT);
        ToggleGroup paymentGroup = new ToggleGroup();
        RadioButton cashPayment = new RadioButton("Cash");
        RadioButton cardPayment = new RadioButton("Card");
        cashPayment.setToggleGroup(paymentGroup);
        cardPayment.setToggleGroup(paymentGroup);
        cashPayment.setSelected(true);

        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Enter 16-digit card number");
        cardNumberField.setVisible(false);
        
        cardNumberField.setTextFormatter(new TextFormatter<>(change -> 
        {
            String newText = change.getControlNewText().replaceAll("[^\\d]", "");

            if (newText.length() > 16) return null; 

            
            StringBuilder formattedText = new StringBuilder();
            for (int i = 0; i < newText.length(); i++) 
            {
                if (i > 0 && i % 4 == 0) 
                {
                    formattedText.append("-");
                }
                formattedText.append(newText.charAt(i));
            }

           
            change.setText(formattedText.toString());
            change.setRange(0, change.getControlText().length()); 

           
            change.setCaretPosition(formattedText.length());
            change.setAnchor(formattedText.length());

            return change;
        }));
        cardPayment.setOnAction(e -> cardNumberField.setVisible(cardPayment.isSelected()));
        cashPayment.setOnAction(e -> cardNumberField.setVisible(false));

        VBox paymentBox = new VBox(5, new HBox (10, cashPayment, cardPayment), cardNumberField);

       
        Label promoCodeLabel = new Label("Promo Code");
        promoCodeLabel.setStyle("-fx-alignment: center;");
        TextField redeemCode = new TextField();
        redeemCode.setPromptText("Enter gift card or promo code");
        Button checkCodeBtn = new Button("Check Code");
        Label resultLabel = new Label();  

       
        Label priceLabel = new Label("Total: " + calculateTotalPrice() + " ALL");
        Button placeOrderBtn = new Button("Place Order");
        
        checkCodeBtn.setOnAction(e -> 
        {
            String code = redeemCode.getText().trim();
            if (code.isEmpty()) 
            {
                resultLabel.setText("Please enter a promo code.");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            
            if (isPromoCodeValid(code, "redeemcodes.txt"))
            {
                resultLabel.setText("Code found! Discount of 20% applied.");
                resultLabel.setStyle("-fx-text-fill: green;");
                
               
                totalPrice = calculateTotalPrice() * 0.8; 
                priceLabel.setText("Total: " + String.format("%.2f", totalPrice) + " ALL");
                
                priceLabel.setText("Total: " + totalPrice);  
                
                checkCodeBtn.setDisable(true); 

            } 
            else 
            {
                resultLabel.setText("Code not valid.");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });
        
        placeOrderBtn.setOnAction(e ->
        {
            primaryStage.close();
            Platform.runLater(() -> 
            {
                OrderProgress progress = new OrderProgress();
                progress.start(new Stage());

            });

            try (FileWriter fileWriter = new FileWriter("receiptList.txt", true)) 
            {
                fileWriter.write("** Order **\n");
                for (Map.Entry<String, Integer> entry : cart.entrySet()) 
                {

                    fileWriter.write(entry.getKey() + ", " + entry.getValue() + "\n");
                }

                fileWriter.write("Total: " + totalPrice + " ALL\n***\n\n\n");

            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        });

        VBox promoBox = new VBox(5, promoCodeLabel, redeemCode, checkCodeBtn, resultLabel);
        

        
        VBox layout = new VBox(10, imageView, addressLabel, timeBox, paymentLabel, paymentBox, promoBox, priceLabel, placeOrderBtn);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-alignment: center; -fx-background-color: khaki;");

        Scene scene = new Scene(layout, 300, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private boolean isPromoCodeValid(String code, String filePath) 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                if (line.trim().equalsIgnoreCase(code)) 
                {
                    return true;  
                }
            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return false;  
    }
    
    public void setCart(Map<String, Integer> cart)
    {
    	this.cart = cart;
    }
    
    public double calculateTotalPrice()
    {
        totalPrice = 0; 
        for (Map.Entry<String, Integer> entry : cart.entrySet()) 
        {
            totalPrice += extractPrice(entry.getKey()) * entry.getValue();
        }
        return totalPrice;
    }

    
    private double extractPrice(String product) 
    {
        String[] parts = product.split("\n");
        if (parts.length > 1) 
        {
            String priceText = parts[1].replace(" ALL", "").trim();
            return Double.parseDouble(priceText);
        }
        return 0;
    }
 

    
}