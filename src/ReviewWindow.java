import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;




public class ReviewWindow extends Application {
    @Override
    public void start(Stage reviewStage) {
        reviewStage.setTitle("Rate Our Service");

        Label prompt = new Label("Rate our service from 1 to 5:");
        prompt.setFont(new Font("Arial", 18));

        ToggleGroup toggleGroup = new ToggleGroup();
        HBox ratingBox = new HBox(10);
        ratingBox.setAlignment(Pos.CENTER);

        TextArea feedbackField = new TextArea();
        feedbackField.setPromptText("Enter no more than 250 characters");
        feedbackField.setWrapText(true);
        feedbackField.setVisible(false);
        feedbackField.setPrefSize(300, 150);

        Button submitButton = new Button("Submit");
        submitButton.setVisible(false);

        for (int i = 1; i <= 5; i++) {
            RadioButton radioButton = new RadioButton(String.valueOf(i));
            radioButton.setToggleGroup(toggleGroup);
            radioButton.setOnAction(e -> {
                for (int j = 0; j < ratingBox.getChildren().size(); j++) {
                    ((RadioButton) ratingBox.getChildren().get(j)).setDisable(false);
                }
                feedbackField.setVisible(true);
                submitButton.setVisible(true);
            });
            ratingBox.getChildren().add(radioButton);
        }

        submitButton.setOnAction(e -> {
            RadioButton selectedRating = (RadioButton) toggleGroup.getSelectedToggle();
            String rating = selectedRating != null ? selectedRating.getText() : "No rating";
            String feedback = feedbackField.getText().trim();

            try (FileWriter fileWriter = new FileWriter("reviewList.txt", true)) {
                fileWriter.write("Rating: " + rating + " | Feedback: " + feedback + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
           
            
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event ->
            {
            	reviewStage.close();
            	new CreditsWindow().show();

            }));
            timeline.setCycleCount(1);
            timeline.play();
            submitButton.setDisable(true);
       
            
        });

        VBox layout = new VBox(10, prompt, ratingBox, feedbackField, submitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 300);
        reviewStage.setScene(scene);
        reviewStage.show();
    }

   
}