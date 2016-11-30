import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GerenteDeEventos {

    Map eventMap;

    public GerenteDeEventos() {
        eventMap = new HashMap<String, ArrayList<OuvinteDeEventos>>();
    }

    public void AdicionarEvento(String evento) {
        eventMap.put(evento, new ArrayList<OuvinteDeEventos>());
    }

    public void AdicionarOuvinte(String evento, OuvinteDeEventos ouvinte) {
        ArrayList<OuvinteDeEventos> temp = (ArrayList<OuvinteDeEventos>) eventMap.get(evento);
        temp.add(ouvinte);
        eventMap.put(evento, temp);
    }

    public void NotificarEvento(String evento) {
        ArrayList<OuvinteDeEventos> temp = (ArrayList<OuvinteDeEventos>) eventMap.get(evento);
        for (OuvinteDeEventos ouvinte : temp) {
            ouvinte.realizaAcao();
        }
    }

}
