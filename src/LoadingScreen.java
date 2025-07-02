import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

public class LoadingScreen extends Application {
    @Override
    public void start(Stage primaryStage) {
        File gifFile = new File("Pictures/Loading.gif");
        ImageView imageView = new ImageView(new Image(gifFile.toURI().toString()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            primaryStage.close();  
            new DataInput().start(new Stage()); 
        }));
        timeline.setCycleCount(1);
        timeline.play();

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 400, 711);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Loading...");
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
