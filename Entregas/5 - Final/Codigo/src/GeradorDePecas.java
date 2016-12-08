import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeradorDePecas {

    protected Map<Integer, Peca> listaDePecas;
    protected Map<Integer, String> listaDeCores;

    public GeradorDePecas() {
        listaDePecas = new HashMap<>();
        listaDeCores = new HashMap<>();
        criarPecas();
        criarCores();
    }

    // TODO : Pensar em uma maneira mais elegante, por enquanto vai ser assim
    protected void criarCores() {
        listaDeCores.put(0, "blue");
        listaDeCores.put(1, "red");
        listaDeCores.put(2, "yellow");
    }

    // TODO : Achar um jeito melhor de fazer isso, tem muitos objetos sendo inseridos.
    protected void criarPecas() {
        // Quadrado
        listaDePecas.put(0, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(0,1), new Posicao(1,1)));
        // Linha Vertical
        listaDePecas.put(1, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(2,0), new Posicao(3,0)));
        // Linha Horizontal
        listaDePecas.put(2, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(0,2), new Posicao(0,3)));
        // Peca Z Horizontal
        listaDePecas.put(3, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(1,1), new Posicao(2,1)));
        // Peca Z Vertical
        listaDePecas.put(4, new Peca(new Posicao(1,0), new Posicao(1,1), new Posicao(0,1), new Posicao(0,2)));
        // Peca S Horizontal
        listaDePecas.put(5, new Peca(new Posicao(0,1), new Posicao(1,1), new Posicao(1,0), new Posicao(2,0)));
        // Peca S Vertical
        listaDePecas.put(6, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(1,1), new Posicao(1,2)));
        // Peca _|_ Horizontal
        listaDePecas.put(7, new Peca(new Posicao(0,1), new Posicao(1,1), new Posicao(1,0), new Posicao(2,1)));
        // Peca _|_ Esquerda
        listaDePecas.put(8, new Peca(new Posicao(0,1), new Posicao(1,0), new Posicao(1,1), new Posicao(1,2)));
        // Peca _|_ Direita
        listaDePecas.put(9, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(0,2), new Posicao(1,1)));
        // Peca _|_ Ponta Cabeça
        listaDePecas.put(10, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(2,0), new Posicao(1,1)));
        // Peca L Horizontal
        listaDePecas.put(11, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(0,2), new Posicao(1,2)));
        // Peca L Esquerda
        listaDePecas.put(12, new Peca(new Posicao(0,1), new Posicao(1,1), new Posicao(2,1), new Posicao(2,0)));
        // Peca L Direita
        listaDePecas.put(13, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(1,0), new Posicao(2,0)));
        // Peca L Ponta Cabeca
        listaDePecas.put(14, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(1,1), new Posicao(1,2)));
        // Peca j Horizontal
        listaDePecas.put(15, new Peca(new Posicao(1,0), new Posicao(1,1), new Posicao(1,2), new Posicao(0,2)));
        // Peca j Esquerda
        listaDePecas.put(16, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(2,0), new Posicao(2,1)));
        // Peca j Direita
        listaDePecas.put(17, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(1,1), new Posicao(1,2)));
        // Peca j Ponta Cabeca
        listaDePecas.put(18, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(0,2), new Posicao(1,0)));

    }

    // TODO : Verificar se a peca ja nao foi gerada para evitar duplicacao
    public ArrayList<Peca> PegarPecasAleatorias(int quantidade) {
        ArrayList<Peca> pecas = new ArrayList<>();

        for (int i = 0; i < quantidade ; i++) {
            Peca peca = null;

            do {
                peca = listaDePecas.get((int) (Math.random() * 19));
            } while (pecas.contains(peca));

            int corRandom = (int)( Math.random() * 100);
            int corValor = 0;

            // 10% de chance de ser Vermelho
            // 30% de chance de ser Amarelo
            // 60% de chance de ser Azul
            if (corRandom < 10)
                corValor = 1;
            if (corRandom >= 10 && corRandom <= 40)
                corValor = 2;
            if (corRandom > 40)
                corValor = 0;

            peca.setCor(listaDeCores.get(corValor));

            pecas.add(peca);
        }

        return pecas;
    }



}
