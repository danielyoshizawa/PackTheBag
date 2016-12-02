import java.util.ArrayList;

public class Jogo {

    protected int numeroLinhas;
    protected int numeroColunas;
    protected Jogador jogadorDaVez;
    // TODO : Criar entidade posicao
    protected ArrayList<Peca> pecasDisponiveis;
    protected Jogador jogador1;
    protected Jogador jogador2;
    protected boolean jogoEmAndamento;
    protected boolean temPecaSelecionada;
    protected Peca pecaSelecionada;
    protected Posicao posicaoSelecionada;
    protected boolean estaConectado;
    protected GeradorDePecas geradorDePecas;
    protected String nomeJogadorDaVez;

    public Jogo() {
        numeroColunas = 0;
        numeroLinhas = 0;
        jogoEmAndamento = false;
        temPecaSelecionada = false;
        estaConectado = false;
        pecasDisponiveis = new ArrayList<>();
        geradorDePecas = new GeradorDePecas();
        jogador1 = new Jogador();
        jogador2 = new Jogador();
        gerarNovasPecasDisponiveis();
    }

    public boolean informarConectado() {
        return estaConectado;
    }

    public void estabelecerConectado(boolean ehConectado) {
        this.estaConectado = ehConectado;
    }

    public boolean informarEmAndamento() {
        return jogoEmAndamento;
    }

    public void esvaziar() {

    }

    public void criarJogador1(String idJogador) {
        jogador1.assumirNome(idJogador);
    }

    public void criarJogador2(String idJogador) {
        jogador2.assumirNome(idJogador);
    }

    public void determinarJogadorInicial() {

    }

    public void receberJogada(JogadaPack jogadaPack) {

        if (jogadaPack.getIdUsuario().equals(jogador1.getIdUsuario())) {
            jogador1.aplicarJogada(jogadaPack);
        } else if (jogadaPack.getIdUsuario().equals(jogador2.getIdUsuario())) {
            jogador2.aplicarJogada(jogadaPack);
        }

        gerarNovasPecasDisponiveis();
    }

    public int tratarClick(float posicaoX, float posicaoY, String idUsuario) {
        return 0;
    }

    // TODO : padronizar ou idUsuario - idJogador
    public boolean ehJogadorDaVez(String idUsuario) {
        return nomeJogadorDaVez.equals(idUsuario);
    }

    // TODO : Verificar se peca esta na lista, ou algum teste de consistencia
    public void setarPecaSelecionada(String identificador, Posicao posicaoSelecionada) {
        pecaSelecionada = pecaComIdentificador(identificador);
        this.posicaoSelecionada = posicaoSelecionada;
        temPecaSelecionada = true;
    }

    public boolean temPecaSelecionada() {
        return temPecaSelecionada;
    }

    public JogadaPack informarJogada(Peca peca, int posicaoX, int posicaoY, String idUsuario) {
        return null;
    }

    // TODO : Analisar a função disso
    public ArrayList<Integer> informarEstado() {
        return null;
    }

    private void gerarNovasPecasDisponiveis() {
        pecasDisponiveis = geradorDePecas.PegarPecasAleatorias(Configuracoes.QUANTIDADE_PECAS_TURNO);

        for (int i = 0; i < pecasDisponiveis.size(); i++) {
            pecasDisponiveis.get(i).setIdentificador(Configuracoes.PECA_IDENTIFICADOR + i);
        }
    }

    public ArrayList<Peca> pegarListaDePecas() {
        return pecasDisponiveis;
    }

    public Peca pecaComIdentificador(String identificador) {
        for (Peca peca : pecasDisponiveis) {
            if (peca.getIdentificador().equals(identificador))
                return peca;
        }

        return null;
    }

    public void setNomeJogadorDaVez(String nomeJogadorDaVez) {
        this.nomeJogadorDaVez = nomeJogadorDaVez;
    }

    public String getNomeJogadorDaVez() {
        return nomeJogadorDaVez;
    }
}
