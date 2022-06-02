package Vue.Buttons;

import javax.swing.*;
import java.net.URL;

public class Malus extends MyButtons{

	private static final long serialVersionUID = 1L;

	public Malus(int x, int y){
        this.x = x;
        this.y = y;
        URL urlImage = getClass().getResource("/bananess.png");
        Icon icone = new ImageIcon(urlImage);
        setIcon(icone);
        setDisabledIcon(this.getIcon());
    }

}
