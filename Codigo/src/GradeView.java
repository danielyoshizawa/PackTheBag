import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GradeView extends ComponentesGraficos {

    // TODO : Repensar isso
    protected Map<String, BlocoView> listaDeBlocos;


    public GradeView(Group grupo)
    {
        super(grupo);
        listaDeBlocos = new HashMap<>();
    }

    public void desenhar() {
        gerarGrade();
    }

    private void gerarGrade() {

        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {

                BlocoView bloco = new BlocoView(super.grupo);
                bloco.posicaoX(posicaoX + (i * Configurations.UNIT)).posixaoY(posicaoY + (j * Configurations.UNIT));

                bloco.desenhar();

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

    @Override
    public boolean pontoPertenceAoComponente(int x, int y) {

        for (Map.Entry<String, BlocoView> entrada : listaDeBlocos.entrySet()) {
            if (entrada.getValue().pontoPertenceAoComponente(x,y) == true)
                return true;
        }

        return false;
    }

}
