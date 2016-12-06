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

    public void conectar() {
        do {
            nome = view.obterIdJogador();
        } while (nome.isEmpty());

        do {
            servidor = view.obterIdServidor();
        } while (servidor.isEmpty());


        if (!nome.isEmpty() && !servidor.isEmpty()) {
            if (rede.conectar(servidor, nome)) {
                jogo.estabelecerConectado(true);
                view.mensagemDeStatus("Aguardando o outro Jogador");
            } else {
                jogo.estabelecerConectado(false);
                view.ExibirMensagemDeErro("Falha ao conectar com o servidor");
            }
        }
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
        boolean estaConectado = false;
        boolean interromper = false;

        if (jogo.informarEmAndamento()) {
            interromper = view.notificarInterromperPartida();
        } else {
            estaConectado = jogo.informarConectado();
        }

        if (interromper || (estaConectado && !jogo.informarEmAndamento())) {
            rede.iniciarPartida();
        }
    }

    // TODO : Rever a Logica, esta finalizando em apenas uma instancia.
    // INFO : Caso se encerrem as possibilidades de encaixe em um determinado turno o jogador não podera mais jogar
    public void receberJogada(JogadaPack jogadaPack) {
        view.mensagemDeStatus("Jogada Recebida!!!");


        // INFO : Jogada vazia quer dizer que se esgotaram as possibilidades para o outro jogador
        if (jogadaPack.getPeca() != null) {
            jogo.receberJogada(jogadaPack);
            view.aplicarJogada(jogadaPack);
        } else {
            jogo.encerrarParticipacao(jogadaPack.getIdUsuario());
        }

        if (!jogo.temJogadorAtivo()) {
            finalizarPartida();
            return;
        }

        alterarJogadorDaVez();

        if (jogo.jogadorAtivo(jogo.getNomeJogadorDaVez())) {
            view.novasPecas(jogo.pegarListaDePecas());

            if (!jogo.existeEncaixePossivel(jogo.getNomeJogadorDaVez())) {
                jogo.encerrarParticipacao(jogo.getNomeJogadorDaVez());

                if (!jogo.temJogadorAtivo()) {
                    finalizarPartida();
                }

                enviarJogada(jogo.informarJogadaVazia(jogo.getNomeJogadorDaVez()));
            }
        } else {
            view.mensagemDeStatus("Acabaram suas Jogadas");
            enviarJogada(jogo.informarJogadaVazia(jogo.getNomeJogadorDaVez()));
        }
    }


    public void enviarJogada(JogadaPack jogada) {
        if (jogada == null) {
            view.mensagemDeStatus("Jogada invalida");
        } else {
            if (jogada.peca == null) {
                view.mensagemDeStatus("Acabaram suas Jogadas");
            } else {
                view.mensagemDeStatus("Jogada enviada");
                view.aplicarJogada(jogada);
            }
            rede.enviarJogada(jogada);
            alterarJogadorDaVez();
        }
    }


    public void comecar(Stage primaryStage) {

        view = new View(primaryStage, Configuracoes.APPNOME, Configuracoes.JANELA_LARGURA, Configuracoes.JANELA_ALTURA, gerenteEventos);
        view.iniciar();

        conectarEventos();
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

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_CONECTAR, new OuvinteDeEventos() {
            @Override
            public void realizaAcao(Object... objetos) {
                conectar();
            }
        });

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

                if (!nome.equals(jogo.getNomeJogadorDaVez())) {
                    view.mensagemDeStatus("Ainda não é sua vez, seu apressadinho!!!");
                    return;
                }

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

                if (!nome.equals(jogo.getNomeJogadorDaVez())) {
                    view.mensagemDeStatus("Ainda não é sua vez, seu apressadinho!!!");
                    return;
                }

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

    public void desativarJogador1() {
        jogo.desativarJogador1();
    }

    public void destativarJogador2() {
        jogo.desativarJogador2();
    }
}
