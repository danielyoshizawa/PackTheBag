import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class PecaView extends ComponentesGraficos {

    protected Map<String, BlocoView> listaDeBlocos;

    public PecaView(Group grupo) {
        super(grupo);
        listaDeBlocos = new HashMap<>();
    }

    public void desenhar() {
        Rectangle rect = new Rectangle();
        rect.setWidth(Configuracoes.UNIT);
        rect.setHeight(Configuracoes.UNIT);
        super.grupo.getChildren().add(rect);
    }

    @Override
    public boolean pontoPertenceAoComponente(int x, int y) {


        return false;
    }
}
