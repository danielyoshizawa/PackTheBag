import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class Botao extends AbstractComponent {

    protected int x = 0;
    protected int y = 0;
    protected int width = 0;
    protected int height = 0;
    protected String text = "";
    protected Color color;

    public Botao(GUIContext container) {
        super(container);
    }

    public Botao text(String text) {
        this.text = text;
        return this;
    }

    public Botao size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Botao color(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public void render(GUIContext container, Graphics graphics) throws SlickException {
        graphics.setColor(color);
        graphics.drawRect(x, y, width, height);
        graphics.drawString(text, x + 10, y + 10);

    }

    @Override
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Botao location(int x, int y) {
        setLocation(x,y);
        return this;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
