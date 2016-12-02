import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.ArrayList;

public class Peca implements Jogada {

    protected String nome;
    protected Grade gradePeca;
    protected String cor;
    protected ArrayList<Posicao> listaDePosicoes;


    public Peca(Posicao ... posicoes) {
        listaDePosicoes = new ArrayList<>();

        for (Posicao posicao : posicoes)
            listaDePosicoes.add(posicao);
    }

    // TODO : Rever necessidade de ter esse metodo
    public void adicionarPosicoes(Posicao ... posicoes) {
        for (Posicao posicao : posicoes)
            listaDePosicoes.add(posicao);
    }

    public ArrayList<Posicao> pegarPosicoes() {
        return listaDePosicoes;
    }

    public boolean ehPosicaoValida(float posicaoX, float posicaoY) {
        return false;
    }
}
