import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {

    public static void main(String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AtorJogador atorJogador = new AtorJogador();
        atorJogador.comecar(primaryStage);
    }
}