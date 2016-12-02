import javafx.stage.Stage;
import org.lwjgl.Sys;

import java.util.ArrayList;

public class AtorJogador {

    protected String idUsuario;
    // TODO : Aqui será criada uma dependencia ciclica, rever isso
    protected AtorNetGames rede;
    protected Jogo jogo;
    protected String nome;
    protected String servidor;
    protected View view;
    protected GerenteDeEventos gerenteEventos;
    protected String nomeJogador1;
    protected String nomeJogador2;

    public AtorJogador() {
        super();
        // TODO : Repensar sobre essa dependencia ciclica, talvez implementar um sistema de eventos
        rede = new AtorNetGames(this);
        gerenteEventos = new GerenteDeEventos();
        jogo = new Jogo();
    }

    public boolean conectar() {
        return rede.conectar("127.0.0.1", nome);
    }

    public String obterDadosConexao() {
        return "";
    }

    public int iniciarPartida(boolean ehMinhaVez) {
        String nomeOutroJogador = rede.obterNomeAdversario() ;

        if (ehMinhaVez) {
            nomeJogador1 = this.nome;
            nomeJogador2 = nomeOutroJogador;
        } else {
            nomeJogador1 = nomeOutroJogador;
            nomeJogador2 = this.nome;
        }

        jogo.criarJogador1(nomeJogador1);
        jogo.criarJogador2(nomeJogador2);

        view.configurarJogador1(nomeJogador1);
        view.configurarJogador2(nomeJogador2);

        jogo.setNomeJogadorDaVez(nomeJogador1);
        view.setNomeJogadorDaVez(nomeJogador1);

        view.novasPecas(jogo.pegarListaDePecas());

        view.iniciarPartida();

        view.mensagemDeStatus("Partida Iniciada");

        return 0; // Contexto da JogadaPack
    }

    public boolean avaliarInterrupcao() {
        return false;
    }

    public int desconectar() {
        rede.desconectar();
        jogo.estabelecerConectado(false);
        return 0;
    }

    public void tratarInciarPartida() {
        rede.iniciarPartida();
        System.out.println("Tratar Iniciar Partida");
    }

    public void receberJogada(JogadaPack jogadaPack) {
        view.mensagemDeStatus("Jogada Recebida!!! Pode jogar.");
        jogo.receberJogada(jogadaPack);

        view.novasPecas(jogo.pegarListaDePecas());

        alterarJogadorDaVez(jogadaPack.getIdUsuario());
    }

    // TODO : Isso nao deveria fazer parte do modelo, eu acho
    public int tratarClick(float posicaoX, float posicaoY) {
        return 0;
    }

    public void enviarJogada(String identificador, Posicao posicaoNaGrade, String idUsuario) {
        JogadaPack jogadaPack = new JogadaPack();
        Peca peca = jogo.pecaComIdentificador(identificador);

        if (peca != null) {
            jogadaPack.criar(peca, posicaoNaGrade, idUsuario);
            alterarJogadorDaVez(idUsuario);
            rede.enviarJogada(jogadaPack);
            view.mensagemDeStatus("Jogada Enviada");
        } else {
            view.mensagemDeStatus("Peca invalida, tente novamente");
        }
    }

    // TODO : Analizar como este metodo sera implementado
    public ArrayList<Integer> informarEstado() {

        return null;
    }

    public ArrayList<Integer> notificarPecasDisponiveis() {
        return null;
    }

    public void comecar(Stage primaryStage) {

        view = new View(primaryStage, Configuracoes.APPNOME, Configuracoes.JANELA_LARGURA, Configuracoes.JANELA_ALTURA, gerenteEventos);
        view.start();

        do {
            nome = view.obterIdJogador();
        } while (nome.isEmpty());

        do {
            servidor = view.obterIdServidor();
        } while (servidor.isEmpty());

        if (!nome.isEmpty() && !servidor.isEmpty()) {
            if (conectar()) {
                jogo.estabelecerConectado(true);
                view.mensagemDeStatus("Jogo Conectado");
            } else {
                view.ExibirMensagemDeErro("Falha ao conectar com o servidor");
            }
        }

        conectarEventos();

        view.mensagemDeStatus("Aguardando o outro Jogador");
    }

    private void alterarJogadorDaVez(String jogadorAtual) {
        if (jogadorAtual.equals(nomeJogador1)) {
            view.setNomeJogadorDaVez(nomeJogador2);
            jogo.setNomeJogadorDaVez(nomeJogador2);
        }
        else {
            view.setNomeJogadorDaVez(nomeJogador1);
            jogo.setNomeJogadorDaVez(nomeJogador2);
        }
    }

    private void conectarEventos() {

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_INICIAR_PARTIDA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                tratarInciarPartida();
            }
        });

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_DESCONECTAR, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                if (jogo.informarConectado())
                    desconectar();
            }
        });

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_ENVIAR_JOGADA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                if (jogo.getNomeJogadorDaVez().equals(nome)) {
                    Peca peca = (Peca) objetos[0];
                    Posicao posicao = (Posicao) objetos[1];
                    enviarJogada(peca.getIdentificador(), posicao, nome);
                    view.mensagemDeStatus("Jogada enviada!!!");
                } else {
                    view.mensagemDeStatus("Ainda não é sua vez");
                }
            }
        });

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_GRADE_SELECIONADA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                if (!jogo.temPecaSelecionada) {
                    view.mensagemDeStatus("Selecione a peça primeiro");
                    return;
                }

                String idUsuario = "";
                Posicao posicao = null;

                // TODO : Ver a necessidade disso, acredito que ja lance uma excecao caso os objetos nao existam
                try {
                    idUsuario = (String) objetos[0];
                    posicao = (Posicao) objetos[1];
                } catch (Exception ex) {
                    System.out.println("Ocorreu um erro ao recuperar parametros de realizarAcao");
                    ex.printStackTrace();
                }

                JogadaPack jogada = jogo.informarJogada(idUsuario, posicao);

                if (jogada == null) {
                    view.mensagemDeStatus("Jogada invalida");
                } else {
                    rede.enviarJogada(jogada);
                }
            }
        });

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_PECA_SELECIONADA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                String idPeca = (String)objetos[0];
                Posicao posicao = (Posicao)objetos[1];
                view.mensagemDeStatus(idPeca + " selecionada");
                jogo.setarPecaSelecionada(idPeca, posicao);
            }
        });
    }
}
