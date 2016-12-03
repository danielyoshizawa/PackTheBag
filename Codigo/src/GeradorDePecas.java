import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GeradorDePecas {

    protected Map<Integer, Peca> listaDePecas;

    public GeradorDePecas() {
        listaDePecas = new HashMap<>();
        criarPecas();
    }

    // TODO : Achar um jeito melhor de fazer isso, tem muitos objetos sendo inseridos.
    private void criarPecas() {
        // Quadrado
        listaDePecas.put(0, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(0,1), new Posicao(1,1)));
        // Linha Vertical
        listaDePecas.put(1, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(2,0), new Posicao(3,0)));
        // Linha Horizontal
        listaDePecas.put(2, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(0,2), new Posicao(0,3)));
        // Peca Z Horizontal
        listaDePecas.put(3, new Peca(new Posicao(0,0), new Posicao(1,0), new Posicao(1,1), new Posicao(1,2)));
        // Peca Z vertidal
        listaDePecas.put(4, new Peca(new Posicao(1,0), new Posicao(1,1), new Posicao(0,1), new Posicao(0,2)));
        // Peca S Horizontal
        listaDePecas.put(5, new Peca(new Posicao(0,1), new Posicao(1,1), new Posicao(1,0), new Posicao(2,0)));
        // Peca S vertical
        listaDePecas.put(6, new Peca(new Posicao(0,0), new Posicao(0,1), new Posicao(1,1), new Posicao(2,0)));
    }

    // TODO : Verificar se a peca ja nao foi gerada para evitar duplicacao
    public ArrayList<Peca> PegarPecasAleatorias(int quantidade) {
        ArrayList<Peca> pecas = new ArrayList<>();

        for (int i = 0; i < quantidade ; i++) {
            Peca peca = listaDePecas.get((int)( Math.random() * 6));
            peca.setCor("red");
            pecas.add(peca);
        }

        return pecas;
    }



}
