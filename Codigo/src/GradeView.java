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
    protected Map<String, BlocoView> listaDeBlocos;

    public GradeView() {
        listaDeBlocos = new HashMap<>();
    }

    public void desenhar(Group group) {
        gerarGrade(group);
    }

    private void gerarGrade(Group group) {

        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                BlocoView bloco = new BlocoView();
                bloco.posicaoX(posicaoX + (i * Configurations.UNIT)).posixaoY(posicaoY + (j * Configurations.UNIT));

                bloco.desenhar(group);

                Posicao posicao = new Posicao(i, j);
                listaDeBlocos.put(posicao.identificador(), bloco);
            }
        }
    }

    // TODO : notificar bloco invalido?
    public void pintarBloco(Posicao posicao, String cor) {
        BlocoView bloco = (BlocoView) listaDeBlocos.get(posicao.identificador());
        bloco.cor(cor);
    }

}
