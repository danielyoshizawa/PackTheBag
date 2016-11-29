import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class main extends Application {

    public static void main(String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root, 1200, 900);

        GradeView gradeView = new GradeView();

        gradeView.colunas(5).linhas(6).posicaoX(300).posixaoY(400);

        gradeView.desenhar(root);

        primaryStage.setTitle("Pack the Bag");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
