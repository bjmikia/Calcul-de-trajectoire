package Vue.Buttons;

import javax.swing.*;
import java.net.URL;

public class Bonus extends MyButtons {

	private static final long serialVersionUID = 1L;

	public Bonus(int x, int y){
        this.x = x;
        this.y = y;
        URL urlImage = getClass().getResource("/champi.png");
        Icon icone = new ImageIcon(urlImage);
        setIcon(icone);
        setDisabledIcon(this.getIcon());
    }

}
