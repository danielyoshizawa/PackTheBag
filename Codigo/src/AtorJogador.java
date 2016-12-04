import javafx.stage.Stage;

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
        if(servidor != ""){
            servidor = "127.0.0.1";
        }
        return rede.conectar(servidor, nome);
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

        view.mensagemDeStatus("Partida Iniciada - Jogador " + jogo.getNomeJogadorDaVez());

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
        view.aplicarJogada(jogadaPack);

        view.novasPecas(jogo.pegarListaDePecas());

        alterarJogadorDaVez();
    }

    // TODO : Isso nao deveria fazer parte do modelo, eu acho
    public int tratarClick(float posicaoX, float posicaoY) {
        return 0;
    }

    // TODO : Analisar se esse metodo vai ser necessario
//    public void enviarJogada(String identificador, Posicao posicaoNaGrade, String idUsuario) {
//        JogadaPack jogadaPack = new JogadaPack();
//        Peca peca = jogo.pecaComIdentificador(identificador);
//
//        if (peca != null) {
//            jogadaPack.iniciar(peca, posicaoNaGrade, idUsuario);
//            alterarJogadorDaVez(idUsuario);
//            rede.enviarJogada(jogadaPack);
//            view.mensagemDeStatus("Jogada Enviada");
//        } else {
//            view.mensagemDeStatus("Peca invalida, tente novamente");
//        }
//    }

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

        /*do {
            nome = view.obterIdJogador();
        } while (nome.isEmpty());

        do {
            servidor = view.obterIdServidor();
        } while (servidor.isEmpty());

*/
        // TODO : remover quando descomentar codigo acima
        nome = view.obterIdJogador();
        servidor = "127.0.0.1";



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

    private void alterarJogadorDaVez() {
        if (jogo.getNomeJogadorDaVez().equals(nomeJogador1)) {
            view.setNomeJogadorDaVez(nomeJogador2);
            jogo.setNomeJogadorDaVez(nomeJogador2);
        }
        else {
            view.setNomeJogadorDaVez(nomeJogador1);
            jogo.setNomeJogadorDaVez(nomeJogador1);
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

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_GRADE_SELECIONADA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                if (!jogo.temPecaSelecionada) {
                    view.mensagemDeStatus("Selecione a peça primeiro");
                    return;
                }

                String idUsuario = (String) objetos[0];
                Posicao posicao = (Posicao) objetos[1];

                JogadaPack jogada = jogo.informarJogada(idUsuario, posicao);

                // TODO : talvez mover para o metodo enviar jogada
                if (jogada == null) {
                    view.mensagemDeStatus("Jogada invalida");
                } else {
                    view.aplicarJogada(jogada);
                    rede.enviarJogada(jogada);
                    alterarJogadorDaVez();
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
