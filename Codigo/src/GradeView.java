import javafx.scene.Group;

import java.util.HashMap;
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
                bloco.posicaoX(posicaoX + (i * Configuracoes.UNIT)).posixaoY(posicaoY + (j * Configuracoes.UNIT));

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
