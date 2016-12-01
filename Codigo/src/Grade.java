import java.util.HashMap;
import java.util.Map;

public class Grade {

    // TODO : criar uma maneira melhor de armazenar as posicoes
    protected boolean [][] posicoes;
    protected int numLinhas;
    protected int numColunas;
    protected Map<String, Bloco> listaDeBlocos;


    public Grade() {
        listaDeBlocos = new HashMap<>();
    }

    public void iniciar(int numLinhas, int numColunas) {
        this.numColunas = numColunas;
        this.numLinhas = numLinhas;

        for (int i = 0; i < numColunas; i++) {
            for (int j = 0; j < numLinhas; j++) {
                Posicao posicao = new Posicao(i, j);
                listaDeBlocos.put(posicao.identificador(), new Bloco("white", posicao));
            }
        }
    }

    // TODO : criar uma entidade posicao ou algo assim, pra representar essas tuplas
    public void configurarPosicoesOcupadas(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

    }

    public void esvaziar() {
        listaDeBlocos.clear();
    }

    // TODO : Avaliar pra que vai servir isso
    public void excluirGrade() {

    }

    public boolean encaixa(Peca peca) {
        return false;
    }

    public Map<String, Bloco> getListaDeBlocos() {
        return listaDeBlocos;
    }
}
