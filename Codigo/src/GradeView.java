import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

public class GradeView extends ComponentesGraficos {

    public GradeView() {

    }

    public void desenhar(Group group) {
        gerarGrade(group);
    }

    private void gerarGrade(Group group) {

        for (int i = 0; i <= numLinhas; i++) {
            Line line = new Line();

            line.setStartX(posicaoX + (i * Configurations.UNIT));
            line.setStartY(posicaoY);
            line.setEndX(posicaoX + (i * Configurations.UNIT));
            line.setEndY(posicaoY + (numColunas * Configurations.UNIT));

            group.getChildren().add(line);
        }

        for (int i = 0; i <= numColunas; i++) {
            Line line = new Line();

            line.setStartX(posicaoX);
            line.setStartY(posicaoY + (i * Configurations.UNIT));
            line.setEndX(posicaoX + (numLinhas * Configurations.UNIT));
            line.setEndY(posicaoY + (i * Configurations.UNIT));

            group.getChildren().add(line);
        }
    }

}
