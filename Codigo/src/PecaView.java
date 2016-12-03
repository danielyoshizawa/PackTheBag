import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PecaView extends ComponentesGraficos {

    protected Map<Posicao, BlocoView> listaDeBlocos;
    protected String identificador;

    public PecaView(Posicao ... posicoes) {
        listaDeBlocos = new HashMap<>();

        for (Posicao posicao : posicoes) {

            listaDeBlocos.put(posicao, new BlocoView(posicao));
        }
    }

    public PecaView(ArrayList<Posicao> posicoes) {
        listaDeBlocos = new HashMap<>();

        for (Posicao posicao : posicoes) {

            listaDeBlocos.put(posicao, new BlocoView(posicao));
        }
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void desenhar(Group grupo) {
        for (Map.Entry<Posicao, BlocoView> entrada : listaDeBlocos.entrySet()) {

            BlocoView bloco = entrada.getValue();

            bloco.posicaoX(posicaoX + (entrada.getKey().getX() * Configuracoes.UNIT))
                    .posixaoY(posicaoY + (entrada.getKey().getY() * Configuracoes.UNIT));
            bloco.cor(cor);

            if (cor.equals("red"))
                cor = "red";

            bloco.desenhar(grupo);
        }
    }

    @Override
    public boolean pontoPertenceAoComponente(int x, int y, Posicao posicao) {
        for (Map.Entry<Posicao, BlocoView> entrada : listaDeBlocos.entrySet()) {
            if (entrada.getValue().pontoPertenceAoComponente(x,y, posicao) == true) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void remover(Group grupo) {
        for (Map.Entry<Posicao, BlocoView> entrada : listaDeBlocos.entrySet()) {
            entrada.getValue().remover(grupo);
        }
    }
}
