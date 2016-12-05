
public class Jogador {

    protected String idUsuario;
    protected int pontuacao;
    protected Grade grade;

    public Jogador() {
        grade = new Grade();
    }

    public void assumirNome(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void iniciarGrade(int numLinhas, int numColunas) {
        grade.iniciar(numLinhas, numColunas);
    }

    public boolean aplicarJogada(JogadaPack jogadaPack) {
        return grade.encaixa(jogadaPack);
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

    public int calcularPontuacao() {
        pontuacao = grade.pontuacao();
        return pontuacao;
    }
}
