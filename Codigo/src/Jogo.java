import java.util.ArrayList;

public class Jogo {

    protected int numeroLinhas;
    protected int numeroColunas;
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
        // TODO : Gerar tamanhos variados de grade
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

    public void criarJogador1(String idUsuario) {
        jogador1.assumirNome(idUsuario);
        jogador1.iniciarGrade(5,5);
    }

    public void criarJogador2(String idUsuario) {
        jogador2.assumirNome(idUsuario);
        jogador2.iniciarGrade(5,5);
    }

    public void receberJogada(JogadaPack jogada) {

        if (jogada.getIdUsuario().equals(jogador1.getIdUsuario())) {
            jogador1.aplicarJogada(jogada);
        } else if (jogada.getIdUsuario().equals(jogador2.getIdUsuario())) {
            jogador2.aplicarJogada(jogada);
        }

        gerarNovasPecasDisponiveis();
    }

    public boolean ehJogadorDaVez(String idUsuario) {
        return nomeJogadorDaVez.equals(idUsuario);
    }

    // TODO : Verificar se peca esta na lista, ou algum teste de consistencia
    public void setarPecaSelecionada(String identificador, Posicao posicaoSelecionada) {
        pecaSelecionada = pecaComIdentificador(identificador);
        this.posicaoSelecionada = posicaoSelecionada;
        temPecaSelecionada = true;
    }

    public JogadaPack informarJogada(String idUsuario, Posicao posicaoNaGrade) {
        if (!ehJogadorDaVez(idUsuario)) {
            System.out.println("Não é o jogador da vez");
            // TODO : analisar como sera formada a jogada e se null é o melhor retorno em caso de erro
            return null;
        } else {
            JogadaPack jogada = new JogadaPack();

            Peca pecaDeslocada = new Peca(pecaSelecionada);
            pecaDeslocada.deslocar(posicaoNaGrade, posicaoSelecionada);

            jogada.iniciar(pecaDeslocada, idUsuario);
            if (ehJogadorDaVez(jogador1.getIdUsuario())) {
                if (jogador1.aplicarJogada(jogada))
                    return jogada;
            } else {
                if (jogador2.aplicarJogada(jogada))
                    return jogada;
            }
        }

        return null;
    }

    protected void gerarNovasPecasDisponiveis() {
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

    public JogadaPack informarJogadaVazia(String idUsuario) {
        if (!ehJogadorDaVez(idUsuario)) {
            System.out.println("Não é o jogador da vez");
            // TODO : analisar como sera formada a jogada e se null é o melhor retorno em caso de erro
            return null;
        } else {
            JogadaPack jogada = new JogadaPack();
            jogada.iniciar(null, idUsuario);
            return jogada;
        }
    }

    public void finalizarPartida() {
        jogoEmAndamento = false;
    }

    public int pontuacaoJogador(String idUsuario) {
        if (idUsuario.equals(jogador1.getIdUsuario())) {
            return jogador1.calcularPontuacao();
        } else {
            return jogador2.calcularPontuacao();
        }
    }

    public boolean existeEncaixePossivel(String idUsuario) {
        if (idUsuario.equals(jogador1.getIdUsuario())) {
            return jogador1.exiteEncaixePosivel(pecasDisponiveis);
        } else {
            return jogador2.exiteEncaixePosivel(pecasDisponiveis);
        }
    }

    public void encerrarParticipacao(String idUsuario) {
        if (idUsuario.equals(jogador1.getIdUsuario())) {
            jogador1.encerrarParticipacao();
        } else {
            jogador2.encerrarParticipacao();
        }
    }

    public boolean jogadorAtivo(String idUsuario) {
        if (idUsuario.equals(jogador1.getIdUsuario()))
            return jogador1.ativo();
        else
            return jogador2.ativo();
    }

    public boolean temJogadorAtivo() {
        return jogador1.ativo() || jogador2.ativo();
    }

    public void limparComponentes() {
        temPecaSelecionada = false;
        pecaSelecionada = null;
        posicaoSelecionada = null;
        jogador1.limpar();
        jogador2.limpar();
    }
}
