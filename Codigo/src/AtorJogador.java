import javafx.stage.Stage;

public class AtorJogador {

    protected AtorNetGames rede;
    protected Jogo jogo;
    protected String nome;
    protected String servidor;
    protected View view;
    protected GerenteDeEventos gerenteEventos;
    protected String nomeJogador1;
    protected String nomeJogador2;

    public AtorJogador() {
        // TODO : Repensar sobre essa dependencia ciclica, talvez utilizar o sistema de eventos
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


    public int desconectar() {
        rede.desconectar();
        jogo.estabelecerConectado(false);
        return 0;
    }

    public void tratarInciarPartida() {
        rede.iniciarPartida();
        System.out.println("Tratar Iniciar Partida");
    }

    // TODO : Alterar para receber jogada e testar se é JogadaPack ou JogadaFinalizar
    public void receberJogada(JogadaPack jogadaPack) {
        view.mensagemDeStatus("Jogada Recebida!!! Pode jogar.");

        if (jogadaPack.getPeca() != null) {
            jogo.receberJogada(jogadaPack);
            view.aplicarJogada(jogadaPack);
        }

        view.novasPecas(jogo.pegarListaDePecas());

        alterarJogadorDaVez();
    }


    public void enviarJogada(JogadaPack jogada) {
        if (jogada == null) {
            view.mensagemDeStatus("Jogada invalida");
        } else {
            view.aplicarJogada(jogada);
            rede.enviarJogada(jogada);
            alterarJogadorDaVez();
        }
    }


    public void comecar(Stage primaryStage) {

        view = new View(primaryStage, Configuracoes.APPNOME, Configuracoes.JANELA_LARGURA, Configuracoes.JANELA_ALTURA, gerenteEventos);
        view.iniciar();

        /*do {
            idUsuario = view.obterIdJogador();
        } while (idUsuario.isEmpty());

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

    protected void conectarEventos() {

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
                } else {
                    JogadaPack jogada = jogo.informarJogada((String) objetos[0], (Posicao) objetos[1]);
                    enviarJogada(jogada);
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

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_PASSAR_VEZ, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                String idUsuario = (String) objetos[0];
                JogadaPack jogada = jogo.informarJogadaVazia(idUsuario);

                if (jogada == null) {
                    view.mensagemDeStatus("Não pode passar a vez ainda, espera seu turno");
                } else {
                    rede.enviarJogada(jogada);
                    alterarJogadorDaVez();
                }
            }
        });

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_FINALIZAR_PARTIDA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                rede.enviarJogada(new JogadaFinalizar());
                finalizarPartida();
            }
        });
    }

    public void finalizarPartida() {
        view.mensagemDeStatus("Partida finalizada");
        jogo.finalizarPartida();

        view.exibirPontuacao(jogo.pontuacaoJogador(nomeJogador1), jogo.pontuacaoJogador(nomeJogador2));
    }
}
