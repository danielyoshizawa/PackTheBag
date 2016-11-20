import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

import java.awt.Font;


public class ConectarViewState extends BasicGameState {

    protected TextField nomeInputField;
    protected TrueTypeFont fontePadrao;
    protected Font font;
    protected Botao botaoConectar;

    public ConectarViewState() {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        font = new Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 16);
        fontePadrao = new TrueTypeFont(font, false);
        nomeInputField = new TextField(container, fontePadrao, 100, 100, 400, 20);
        botaoConectar = new Botao(container);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("Digite seu nome", 100, 80);
        nomeInputField.render(container, graphics);

        nomeInputField.setBackgroundColor(Color.black);
        nomeInputField.setAcceptingInput(true);
        nomeInputField.setTextColor(Color.white);

        botaoConectar.location(100,140).size(100,50).color(Color.white).text("Concectar");

        botaoConectar.render(container, graphics);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }
}
