import java.util.ArrayList;

public class Jogo {

    protected int numeroLinhas;
    protected int numeroColunas;
    protected Jogador jogadorDaVez;
    // TODO : Criar entidade posicao
    protected ArrayList<Integer> pecasDisponiveis;
    protected Jogador jogador1;
    protected Jogador jogador2;
    protected boolean jogoEmAndamento;
    protected boolean temPecaSelecionada;
    protected Peca pecaSelecionada;

    public Jogo() {

    }

    public boolean informarConectado() {
        return false;
    }

    public void estabelecerConectado(boolean ehConectado) {

    }

    public boolean informarEmAndamento() {
        return false;
    }

    public void esvaziar() {

    }

    public void criarJogador(String idJogador) {

    }

    public void determinarJogadorInicial() {

    }

    public void receberJogada(Jogada jogada) {

    }

    public int tratarClick(float posicaoX, float posicaoY, String idUsuario) {
        return 0;
    }

    // TODO : padronizar o idUsuario - idJogador
    public boolean ehJogadorDaVez(String idUsuario) {
        return false;
    }

    public void setarPecaSelecionada(Peca peca) {

    }

    public boolean temPecaSelecionada() {
        return false;
    }

    public Jogada informarJogada(Peca peca, int posicaoX, int posicaoY, String idUsuario) {
        return null;
    }

    // TODO : Analisar a função disso
    public ArrayList<Integer> informarEstado() {
        return null;
    }

    private void gerarNovasPecasDisponiveis() {

    }

    public ArrayList<Peca> pegarListaDePecas() {
        return null;
    }
}
