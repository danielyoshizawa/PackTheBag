import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grade {

    // TODO : iniciar uma maneira melhor de armazenar as posicoes
    protected boolean [][] posicoes;
    protected int numLinhas;
    protected int numColunas;
    // TODO : Talvez seja melhor usar um ArrayList<Bloco>
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

    // TODO : iniciar uma entidade posicao ou algo assim, pra representar essas tuplas
    public void configurarPosicoesOcupadas(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

    }

    public void esvaziar() {
        listaDeBlocos.clear();
    }

    // TODO : Avaliar pra que vai servir isso
    public void excluirGrade() {

    }

    // TODO : Nao sei se jogada deveria estar aqui
    public boolean encaixa(JogadaPack jogada) {

        Peca peca = jogada.getPeca();
        ArrayList<Posicao> posicoesPeca = peca.pegarPosicoes();

        if (posicoesDisponiveis(posicoesPeca)) {
            for (Posicao posicao : posicoesPeca) {
                Bloco bloco = listaDeBlocos.get(posicao.identificador());
                bloco.setCor(peca.getCor());
                bloco.setOcupado(true);
            }

            return true;
        }

        return false;
    }

    private boolean posicoesDisponiveis(ArrayList<Posicao> posicoesPeca) {
        for (Posicao posicao : posicoesPeca ) {
            Bloco bloco = listaDeBlocos.get(posicao.identificador());
            try {
                if (bloco.Ocupado())
                    return false;
            } catch (NullPointerException nullEx) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Bloco> getListaDeBlocos() {
        return listaDeBlocos;
    }

    public int pontuacao() {
        int pontos = 0;
        for (Map.Entry<String, Bloco> entrada : listaDeBlocos.entrySet()) {
            String cor = entrada.getValue().getCor();

            if (Configuracoes.CATEGORIA_COR_VERMELHO.equals(cor)) {
                pontos += Configuracoes.CATEGORIA_PONTOS_VERMELHO;
            } else if (Configuracoes.CATEGORIA_COR_AMARELO.equals(cor)) {
                pontos += Configuracoes.CATEGORIA_PONTOS_AMARELO;
            } else if (Configuracoes.CATEGORIA_COR_AZUL.equals(cor)) {
                pontos += Configuracoes.CATEGORIA_PONTOS_AZUL;
            }
        }
        return pontos;
    }
}
