import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CreditsWindow extends Stage {
    public CreditsWindow() {
        setTitle("Project Credits");
        setResizable(false); 

     
        VBox creditsLayout = new VBox(15);
        creditsLayout.setAlignment(Pos.CENTER);
        creditsLayout.setPadding(new javafx.geometry.Insets(20));

      
        Pane background = new Pane();
        background.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));

     
        Label arberLabel = createStyledLabel("Arber", 
                "- Worked on the connections between classes and windows.\n- Overall architecture of the project.");
        Label licernLabel = createStyledLabel("Licern", 
                "- Worked on the review window with reading/writing on .txt files.\n- Edited the images for the projects.");
        Label vasilLabel = createStyledLabel("Vasil", 
                "- Worked on GridPane, placement of elements in the classes.\n- Graphic design section.");

        
        Button closeButton = new Button("Close");
        closeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        closeButton.setTextFill(Color.WHITE);
        closeButton.setStyle("-fx-background-color: #333; -fx-border-color: #fff; -fx-border-width: 2px;");
        closeButton.setOnAction(e -> this.close());

       
        creditsLayout.getChildren().addAll(arberLabel, licernLabel, vasilLabel, closeButton);

    
        StackPane root = new StackPane(background, creditsLayout);

      
        Scene scene = new Scene(root, 400, 300);
        setScene(scene);
    }

    private Label createStyledLabel(String name, String description) {
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.GOLD);

        Label descriptionLabel = new Label(description);
        descriptionLabel.setFont(Font.font("Arial", 12));
        descriptionLabel.setTextFill(Color.WHITE);

        VBox labelContainer = new VBox(5, nameLabel, descriptionLabel);
        labelContainer.setAlignment(Pos.CENTER);

        return new Label("", labelContainer); 
    }
}