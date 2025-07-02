import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class OrderProgress extends Application {
    private Label label1, label2, label3, label4;
    private Font customFont;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Order Progress");
        
      
        String fontPath = "file:.ttf";
        customFont = Font.loadFont(fontPath, 24);
        if (customFont == null) {
            customFont = new Font("Garamond", 30);
        }
        
        WebView webView = new WebView();
        webView.setPrefSize(400, 380);
        String gifPath = new File("Pictures/GIF.gif").toURI().toString();
        webView.getEngine().load(gifPath);
        webView.setMouseTransparent(true);
        
        label1 = createLabel("1. Confirming Order. . .");
        label2 = createLabel("2. Waiting to be seen. . .");
        label3 = createLabel("3. Preparing. . .");
        label4 = createLabel("4. Order Ready for Pickup. . .");
        
        VBox vbox = new VBox(10, webView, label1, label2, label3, label4);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(10));
        
        Scene scene = new Scene(vbox, 400, 711);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        updateLabels(primaryStage);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(customFont);
        label.setTextFill(Color.RED);
        return label;
    }

    private void updateLabels(Stage primaryStage) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int step = 0;
            @Override
            public void run() {
                Platform.runLater(() -> {
                    switch (step) {
                        case 0: label1.setText("✓ Order Confirmed!"); label1.setTextFill(Color.GREEN); break;
                        case 1: label2.setText("✓ Seen by Employee!"); label2.setTextFill(Color.GREEN); break;
                        case 2: label3.setText("✓ Ready!"); label3.setTextFill(Color.GREEN); break;
                        case 3: 
                            label4.setText("✓ Order has been Picked up!");
                            label4.setTextFill(Color.GREEN);
                            timer.cancel();
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(() -> {
                                        primaryStage.close();
                                        new ReviewWindow().start(new Stage());
                                    });
                                }
                            }, 3000);
                            break;
                    }
                    step++;
                });
            }
        };
        timer.schedule(task, 5000, 5000);
    }
   
}