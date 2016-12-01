import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class PecaView extends ComponentesGraficos {

    public PecaView(Group grupo) {
        super(grupo);
    }

    public void desenhar() {
        Rectangle rect = new Rectangle();
        rect.setWidth(Configurations.UNIT);
        rect.setHeight(Configurations.UNIT);
        super.grupo.getChildren().add(rect);
    }

    @Override
    public boolean pontoPertenceAoComponente(int x, int y) {


        return false;
    }
}
