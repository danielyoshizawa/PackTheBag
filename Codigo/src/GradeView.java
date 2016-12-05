import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GradeView extends ComponentesGraficos {

    // TODO : Repensar isso
    protected Map<String, BlocoView> listaDeBlocos;
    protected String idUsuario;

    public GradeView(String idUsuario)
    {
        listaDeBlocos = new HashMap<>();
        this.idUsuario = idUsuario;
    }

    public void desenhar(Group grupo) {
        gerarGrade(grupo);
    }

    private void gerarGrade(Group grupo) {

        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {

                Posicao posicao = new Posicao(i, j);
                BlocoView bloco = new BlocoView(posicao);

                bloco.posicaoX(posicaoX + (i * Configuracoes.UNIT)).posixaoY(posicaoY + (j * Configuracoes.UNIT));
                bloco.desenhar(grupo);

                listaDeBlocos.put(posicao.identificador(), bloco);
            }
        }
    }

    // TODO : notificar bloco invalido?
    // TODO : Verificar se funciona com posicao ao inves do identificador
    protected void pintarBloco(Posicao posicao, String cor) {
        BlocoView bloco = (BlocoView) listaDeBlocos.get(posicao.identificador());
        bloco.cor(cor);
    }

    @Override
    public boolean pontoPertenceAoComponente(int x, int y, Posicao posicao) {

        for (Map.Entry<String, BlocoView> entrada : listaDeBlocos.entrySet()) {
            if (entrada.getValue().pontoPertenceAoComponente(x,y, posicao)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void remover(Group grupo) {
        for (Map.Entry<String, BlocoView> entrada : listaDeBlocos.entrySet()) {
            entrada.getValue().remover(grupo);
        }
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void aplicarJogada(ArrayList<Posicao> listaDePosicoes, String cor) {
        for (Posicao posicao : listaDePosicoes) {
            pintarBloco(posicao, cor);
        }
    }
}
