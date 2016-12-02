import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PecaView extends ComponentesGraficos {

    protected Map<Posicao, BlocoView> listaDeBlocos;

    public PecaView(Posicao ... posicoes) {
        listaDeBlocos = new HashMap<>();

        for (Posicao posicao : posicoes) {

            listaDeBlocos.put(posicao, new BlocoView());
        }
    }

    public PecaView(ArrayList<Posicao> posicoes) {
        listaDeBlocos = new HashMap<>();

        for (Posicao posicao : posicoes) {

            listaDeBlocos.put(posicao, new BlocoView());
        }
    }

    public void desenhar(Group grupo) {
        for (Map.Entry<Posicao, BlocoView> entrada : listaDeBlocos.entrySet()) {

            BlocoView bloco = entrada.getValue();

            bloco.posicaoX(posicaoX + (entrada.getKey().getX() * Configuracoes.UNIT))
                    .posixaoY(posicaoY + (entrada.getKey().getY() * Configuracoes.UNIT));

            bloco.desenhar(grupo);
        }
    }

    @Override
    public boolean pontoPertenceAoComponente(int x, int y) {


        return false;
    }
}
