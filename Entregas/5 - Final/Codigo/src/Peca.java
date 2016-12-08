import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.ArrayList;

public class Peca implements Jogada {

    protected String identificador;
    protected Grade gradePeca;
    protected String cor;
    protected ArrayList<Posicao> listaDePosicoes;

    public Peca(Peca peca) {
        this.identificador = peca.identificador;
        this.gradePeca = peca.gradePeca;
        this.cor = peca.cor;
        this.listaDePosicoes = peca.listaDePosicoes;
    }

    public Peca(Posicao ... posicoes) {
        listaDePosicoes = new ArrayList<>();

        for (Posicao posicao : posicoes)
            listaDePosicoes.add(posicao);
    }

    public ArrayList<Posicao> pegarPosicoes() {
        return listaDePosicoes;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void deslocar(Posicao posicaoNaGrade, Posicao posicaoNaPeca) {
        Posicao posicaoDeslocar = new Posicao(
                posicaoNaGrade.getX() - posicaoNaPeca.getX(),
                posicaoNaGrade.getY() - posicaoNaPeca.getY());

        for (Posicao posicaoBloco : listaDePosicoes) {
            posicaoBloco.deslocar(posicaoDeslocar);
        }
    }
}
