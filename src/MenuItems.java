import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MenuItems extends Application 
{


    private Map<String, String> productImages = new HashMap<>();
    private Map<String, Integer> cart = new HashMap<>();
    private Label cartLabel = new Label("Items: 0");


    @Override
    public void start(Stage primaryStage) 
    {
 
        initializeProductImages();

      
        Label restaurantLabel = new Label("We found 1 restaurant: Bereqet Catering");
        restaurantLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ImageView logoImageView = new ImageView(new Image("file:Pictures/Bereqet.png"));
        logoImageView.setFitWidth(250);
        logoImageView.setPreserveRatio(true);

        HBox topBox = new HBox(10, logoImageView, restaurantLabel);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(10));

      
        ImageView cartIcon = new ImageView(new Image("file:Pictures/cart_icon.png"));
        cartIcon.setFitWidth(50);
        cartIcon.setPreserveRatio(true);
        cartIcon.setStyle("-fx-cursor: hand;");
        cartIcon.setOnMouseClicked(event -> openCartWindow(primaryStage));
        
		cartLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox cartBox = new HBox(10, cartIcon, cartLabel);
        cartBox.setAlignment(Pos.TOP_RIGHT);
        
     
        StackPane cartStack = new StackPane(cartBox);
        StackPane.setAlignment(cartIcon, Pos.TOP_RIGHT);

      
        HBox header = new HBox(topBox, cartStack);
        HBox.setHgrow(cartStack, Priority.ALWAYS);
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setPadding(new Insets(10));

      
        VBox categoriesBox = new VBox(10);
        categoriesBox.setPadding(new Insets(20));
        
        
        categoriesBox.getChildren().addAll
        (
                createCategory("Drinks", "Spring\n50 ALL", "Glina\n100 ALL","Pepsi\n100 ALL", "Ama Ice Coffee\n150 ALL", "RedBull\n200 ALL", "8S2\n150 ALL", "Ivi\n150 ALL", "Bravo\n100 ALL", "Fruit Juice\n250 ALL"),
                createCategory("Soft Drinks", "Cappuccino\n100 ALL", "Cacao\n100 ALL", "Salep\n100 ALL", "Dark Chocolate\n100 ALL", "White Chocolate\n100 ALL", "Hot Tea\n50 ALL", "Espresso\n60 ALL", "Macchiato\n70 ALL"),
                createCategory("Fast Food", "Cheese Burek\n50 ALL","Spinach Burek\n50 ALL","Sausage Burek\n70 ALL","Fried Eggs\n50 ALL","Boiled Eggs\n30 ALL","Simple Sandwich\n100 ALL","Chicken Sandwich\n250 ALL","Burger\n250 ALL","French Fries\n150 ALL","Chicken Gyro\n250 ALL","Pizza\n200 ALL","Calzone\n200 ALL"),
                createCategory("Dishes","Chicken Soup\n250 ALL","Tomato Soup\n250 ALL","Vegetable Soup\n250 ALL","Plain Rice\n100 ALL","Spaghetti Bolognese\n300 ALL","Spaghetti with pesto\n350 ALL","Lasagna\n400 ALL","Mixed Salad\n200 ALL","Mixed Fruits\n200 ALL","Meatballs\n50 ALL","Potato Croquettes\n30 ALL"),
                createCategory("Desserts", "Profiterole\n150 ALL", "Trelecce\n150 ALL", "Creppe\n150 ALL", "Tiramisu\n200 ALL", "Cheesecake\n200 ALL"),
                createCategory("Snacks", "Albeni Bar\n70 ALL","Albeni Cakes\n100 ALL","Biskrem Bar\n70 ALL","Biskrem Cakes\n70 ALL","Croissant\n70 ALL","Kit-Kat\n60 ALL","Lays\n70 ALL","Bake Rolls\n100 ALL","Snickers\n70 ALL","Twix\n70 ALL"));

      
       
        ScrollPane scrollPane = new ScrollPane(categoriesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setStyle("-fx-background-color: transparent;");
        
       
        VBox mainLayout = new VBox(20, header, scrollPane);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

      
        VBox reviewsBox = new VBox(10);
        reviewsBox.setPadding(new Insets(20));
        reviewsBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px;");

        Label reviewsLabel = new Label("Latest Reviews:");
        reviewsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        reviewsBox.getChildren().add(reviewsLabel);
        updateReviews(reviewsBox); 

      
        mainLayout.getChildren().add(reviewsBox);

      
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateReviews(reviewsBox));
            }
        }, 0, 5000); 
        
        Scene scene = new Scene(mainLayout, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu");
        primaryStage.show();
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
    }

    private List<String> readLastThreeReviews() {
        List<String> reviews = new ArrayList<>();
        File file = new File("reviewList.txt");

       
        if (!file.exists()) {
            System.out.println("File not found: " + file.getAbsolutePath());
            return reviews; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reviews.add(line);
            }
          
            if (reviews.size() > 3) {
                reviews = reviews.subList(reviews.size() - 3, reviews.size());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return reviews;
    }

    private void updateReviews(VBox reviewsBox) {
        List<String> reviews = readLastThreeReviews();
        reviewsBox.getChildren().clear();
        reviewsBox.getChildren().add(new Label("Latest Reviews:"));
        for (String review : reviews) {
            reviewsBox.getChildren().add(new Label(review));
        }
    }
    
	
    private void initializeProductImages() 
    {
		    productImages.put("Spring\n50 ALL", "file:Pictures/spring.jpg");
		    productImages.put("Glina\n100 ALL", "file:Pictures/glina.jpg");
		    productImages.put("Pepsi\n100 ALL", "file:Pictures/pepsi.jpg");
		    productImages.put("Ama Ice Coffee\n150 ALL", "file:Pictures/ama.jpg");
		    productImages.put("RedBull\n200 ALL", "file:Pictures/redbull.png");
		    productImages.put("8S2\n150 ALL", "file:Pictures/b52.png");
		    productImages.put("Ivi\n150 ALL", "file:Pictures/ivi.png");
		    productImages.put("Bravo\n100 ALL", "file:Pictures/bravo.png");
		    productImages.put("Fruit Juice\n250 ALL", "file:Pictures/fruitjuice.jpg");
		    productImages.put("Cappuccino\n100 ALL", "file:Pictures/cappucino.jpg");
		    productImages.put("Cacao\n100 ALL", "file:Pictures/cacao.jpg");
		    productImages.put("Salep\n100 ALL", "file:Pictures/salep.jpg");
		    productImages.put("Dark Chocolate\n100 ALL", "file:Pictures/darkchoco.jpg");
		    productImages.put("White Chocolate\n100 ALL", "file:Pictures/whitechoco.jpg");
		    productImages.put("Hot Tea\n50 ALL", "file:Pictures/hottea.jpg");
		    productImages.put("Espresso\n60 ALL", "file:Pictures/espresso.jpg");
		    productImages.put("Macchiato\n70 ALL", "file:Pictures/macchiato.jpg");
		    productImages.put("Cheese Burek\n50 ALL", "file:Pictures/cheeseburek.jpg");
		    productImages.put("Spinach Burek\n50 ALL", "file:Pictures/spinachburek.jpg");
		    productImages.put("Sausage Burek\n70 ALL", "file:Pictures/sausageburek.jpg");
		    productImages.put("Fried Eggs\n50 ALL", "file:Pictures/friedeggs.jpg");
		    productImages.put("Boiled Eggs\n30 ALL", "file:Pictures/boiledeggs.jpg");
		    productImages.put("Simple Sandwich\n100 ALL", "file:Pictures/toast.jpg");
		    productImages.put("Chicken Sandwich\n250 ALL", "file:Pictures/chickensandwich.jpg");
		    productImages.put("Burger\n250 ALL", "file:Pictures/burger.jpg");
		    productImages.put("French Fries\n150 ALL", "file:Pictures/frenchfries.jpg");
		    productImages.put("Chicken Gyro\n250 ALL", "file:Pictures/gyro.jpg");
		    productImages.put("Pizza\n200 ALL", "file:Pictures/pizza.jpg");
		    productImages.put("Calzone\n200 ALL", "file:Pictures/calzone.jpg");
		    productImages.put("Chicken Soup\n250 ALL", "file:Pictures/chickensoup.jpg");
		    productImages.put("Tomato Soup\n250 ALL", "file:Pictures/tomatosoup.jpg");
		    productImages.put("Vegetable Soup\n250 ALL", "file:Pictures/vegetablesoup.jpg");
		    productImages.put("Plain Rice\n100 ALL", "file:Pictures/rice.jpg");
		    productImages.put("Spaghetti Bolognese\n300 ALL", "file:Pictures/spaghettitomato.jpg");
		    productImages.put("Spaghetti with pesto\n350 ALL", "file:Pictures/spaghettipesto.jpg");
		    productImages.put("Lasagna\n400 ALL", "file:Pictures/lasagna.jpg");
		    productImages.put("Mixed Salad\n200 ALL", "file:Pictures/mixedsalad.jpg");
		    productImages.put("Mixed Fruits\n200 ALL", "file:Pictures/mixedfruits.jpg");
		    productImages.put("Meatballs\n50 ALL", "file:Pictures/meatballs.jpg");
		    productImages.put("Potato Croquettes\n30 ALL", "file:Pictures/potatocroquettes.jpg");
		    productImages.put("Profiterole\n150 ALL", "file:Pictures/profiterole.jpg");
		    productImages.put("Trelecce\n150 ALL", "file:Pictures/trelecce.jpg");
		    productImages.put("Creppe\n150 ALL", "file:Pictures/creppe.jpg");
		    productImages.put("Tiramisu\n200 ALL", "file:Pictures/tiramisu.jpg");
		    productImages.put("Cheesecake\n200 ALL", "file:Pictures/cheesecake.jpg");
		    productImages.put("Albeni Bar\n70 ALL", "file:Pictures/albenifishek.jpg");
		    productImages.put("Albeni Cakes\n100 ALL", "file:Pictures/albeniqe.jpg");
		    productImages.put("Biskrem Bar\n70 ALL", "file:Pictures/biskremfishek.jpg");
		    productImages.put("Biskrem Cakes\n70 ALL", "file:Pictures/biskremqese.jpg");
		    productImages.put("Croissant\n70 ALL", "file:Pictures/molto.jpg");
		    productImages.put("Kit-Kat\n60 ALL", "file:Pictures/kitkat.png");
		    productImages.put("Lays\n70 ALL", "file:Pictures/lays.jpg");
		    productImages.put("Bake Rolls\n100 ALL", "file:Pictures/bakerolls.jpg");
		    productImages.put("Snickers\n70 ALL", "file:Pictures/snickers.jpg");
		    productImages.put("Twix\n70 ALL", "file:Pictures/twix.jpg");
    }

    
    	private TitledPane createCategory(String categoryName, String... products) 
    	{
	        HBox productRow = new HBox(20);  // Adjusted spacing
	        productRow.setPadding(new Insets(10));
	        productRow.setAlignment(Pos.CENTER_LEFT);
	
	        for (String product : products) 
	        {
	            String imagePath = productImages.get(product);
	            if (imagePath == null || imagePath.isEmpty()) 
	            {
	                System.out.println("Error: Image path for product '" + product + "' is missing!");
	                continue;
	            }
	
	            Image image;
	            try 
	            {
	                image = new Image(imagePath);
	            } 
	            catch (Exception e)
	            {
	                System.out.println("Error loading image for '" + product + "': " + e.getMessage());
	                image = new Image("file: default_image.png"); 
	            }
	
	            ImageView productImage = new ImageView(image);
	            productImage.setFitWidth(100);
	            productImage.setPreserveRatio(true);
	            productImage.setStyle("-fx-cursor: hand");
	
	         
	            productImage.setOnMouseClicked(event -> addToCart(product));
	
	            Label productLabel = new Label(product);
	            productLabel.setStyle("-fx-font-size: 14px; -fx-border-color: gray; -fx-padding: 5px 10px; -fx-text-fill: black;");
	            productLabel.setMinWidth(120);
	            if(categoryName.equals("Fast Food") || categoryName.equals("Dishes"))
	            {
	                productLabel.setMinHeight(75);
	            }
	            productLabel.setWrapText(true);
	            productLabel.setAlignment(Pos.CENTER);
	
	            VBox productBox = new VBox(5);
	            productBox.setAlignment(Pos.CENTER);
	            productBox.getChildren().addAll(productImage, productLabel);
	
	            productRow.getChildren().add(productBox);
	        }
	
	       
	        ScrollPane scrollPane = new ScrollPane(productRow);
	        scrollPane.setFitToHeight(true);  
	        scrollPane.setFitToWidth(false);  
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	        scrollPane.setStyle("-fx-background: transparent; -fx-border-color: lightgray;");
	
	        TitledPane titledPane;
	        if (categoryName.equals("Fast Food") || categoryName.equals("Dishes")) 
	        {
	            titledPane = new TitledPane(categoryName, scrollPane);
	        } 
	        else 
	        {
	            titledPane = new TitledPane(categoryName, productRow);
	        }
	
	       
	        titledPane.setExpanded(false);
	
	        return titledPane;
    }

    private void addToCart(String product) 
    {
        cart.put(product, cart.getOrDefault(product, 0) + 1);
        
        
        int totalItems = cart.values().stream().mapToInt(Integer::intValue).sum();
        
      
        cartLabel.setText("Items: " + totalItems);
        
    }
    
    
    
    private void openCartWindow(Stage primaryStage) 
    {

        Stage cartStage = new Stage();
        cartStage.initModality(Modality.APPLICATION_MODAL);
        cartStage.setTitle("Shopping Cart");

        VBox cartLayout = new VBox(10);
        cartLayout.setPadding(new Insets(20));

       
        ScrollPane scrollPane = new ScrollPane(cartLayout);
        scrollPane.setFitToWidth(true);  
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);  
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  

        Label totalLabel = new Label();
        Runnable updateTotalPrice = () -> 
        {
        	double newTotalCost = 0;
            
            for (Map.Entry<String, Integer> entry : cart.entrySet()) 
            {
                newTotalCost += extractPrice(entry.getKey()) * entry.getValue();
            }
            if (newTotalCost == 0) 
            {
                cartLayout.getChildren().clear();
                cartLayout.getChildren().add(new Label("Your cart is empty."));
            } 
            else
            {
                totalLabel.setText("Total Price: " + String.format("%.2f ALL", newTotalCost));
            }

            
            int totalItems = cart.values().stream().mapToInt(Integer::intValue).sum();
            cartLabel.setText("Items: " + totalItems); 
        };

        if (cart.isEmpty()) 
        {
            cartLayout.getChildren().add(new Label("Your cart is empty."));
        } 
        else 
        {
            double totalCost = 0;
            Map<String, HBox> itemRows = new HashMap<>();

            for (Map.Entry<String, Integer> entry : new HashMap<>(cart).entrySet())
            {
                String product = entry.getKey();
                int quantity = entry.getValue();
                double price = extractPrice(product);
                double itemTotal = price * quantity;
                totalCost += itemTotal;

                String imagePath = productImages.get(product);
                ImageView productImage = new ImageView(new Image(imagePath));
                productImage.setFitWidth(50);
                productImage.setPreserveRatio(true);

                Label nameLabel = new Label(product);
                TextField quantityField = new TextField(String.valueOf(quantity));
                quantityField.setPrefWidth(40);
                quantityField.setEditable(false);
                Label priceLabel = new Label(String.format("%.2f ALL", itemTotal));

                Button increaseButton = new Button("+");
                increaseButton.setOnAction(e -> {
                    int newQuantity = cart.get(product) + 1;
                    cart.put(product, newQuantity);
                    quantityField.setText(String.valueOf(newQuantity));
                    priceLabel.setText(String.format("%.2f ALL", extractPrice(product) * newQuantity));
                    updateTotalPrice.run();
                });

                Button decreaseButton = new Button("-");
                decreaseButton.setOnAction(e -> 
                {
                    int newQuantity = cart.get(product) - 1;
                    if (newQuantity <= 0)
                    {
                        cart.remove(product);
                        cartLayout.getChildren().remove(itemRows.get(product));
                    } 
                    else 
                    {
                        cart.put(product, newQuantity);
                        quantityField.setText(String.valueOf(newQuantity));
                        priceLabel.setText(String.format("%.2f ALL", extractPrice(product) * newQuantity));
                    }
                    updateTotalPrice.run();
                });

                HBox itemRow = new HBox(10, productImage, nameLabel, decreaseButton, quantityField, increaseButton, priceLabel);
                itemRow.setAlignment(Pos.CENTER_LEFT);
                cartLayout.getChildren().add(itemRow);
                itemRows.put(product, itemRow);
            }

            totalLabel.setText("Total Price: " + String.format("%.2f ALL", totalCost));
            totalLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            cartLayout.getChildren().add(totalLabel);
        }

        Button checkoutButton = new Button("Proceed to Checkout");
        checkoutButton.setOnAction(e -> 
        {
            if (cart.isEmpty()) 
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Cart");
                alert.setHeaderText(null);
                alert.setContentText("You cannot proceed because you have not selected anything from the menu!");
                alert.showAndWait();
            } 
            else
            {
                cartStage.close(); 
                primaryStage.close();
                
                Platform.runLater(() -> 
                {
                	ProceedToCheckout checkoutWindow = new ProceedToCheckout();
                	checkoutWindow.setCart(cart);
                    checkoutWindow.start(new Stage());
                
                });
            }
        });

        Button backButton = new Button("Go Back to Main Menu");
        backButton.setOnAction(e -> cartStage.close());

        HBox buttonBox = new HBox(10, checkoutButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        cartLayout.getChildren().add(buttonBox);

        Scene cartScene = new Scene(scrollPane, 400, 400);  
        cartStage.setScene(cartScene);
        cartStage.setResizable(false);
        cartStage.showAndWait();
        
 
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