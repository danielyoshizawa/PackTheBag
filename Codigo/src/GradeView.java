import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GradeView extends ComponentesGraficos {

    // TODO : Repensar isso
    protected Map<String, Bloco> listaDeBlocos;

    public GradeView() {
        listaDeBlocos = new HashMap<>();
    }

    public void desenhar(Group group) {
        gerarGrade(group);
    }

    private void gerarGrade(Group group) {

        // TODO : Adaptar para adequar ao modelo
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                Rectangle bloco = new Rectangle();
                bloco.setHeight(Configurations.UNIT);
                bloco.setWidth(Configurations.UNIT);

                bloco.setX(posicaoX + (i * Configurations.UNIT));
                bloco.setY(posicaoY + (j * Configurations.UNIT));

                bloco.setFill(Paint.valueOf("white"));
                bloco.setStroke(Paint.valueOf("black"));

                group.getChildren().add(bloco);
            }
        }
    }

}
