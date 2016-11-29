import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class View extends Application {


    public View() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle circ = new Circle(40,40,30);
        Group root = new Group(circ);
        Scene scene = new Scene(root, 1200, 900);

        primaryStage.setTitle("My JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
