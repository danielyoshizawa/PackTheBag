
public class Jogador {

    protected String idUsuario;
    protected float pontuacao;
    protected Grade grade;

    public Jogador() {

    }

    public void assumirNome(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void iniciarGrade(int numLinhas, int numColunas) {

    }

    public void aplicarJogada(JogadaPack jogadaPack) {

    }

    public boolean pecaEncaixaNaPosicao(Peca peca) {
        return false;
    }

    public Grade getGrade() {
        return null;
    }

    public void pontuar(int pontos) {

    }

    public String getIdUsuario() {
        return idUsuario;
    }
}
