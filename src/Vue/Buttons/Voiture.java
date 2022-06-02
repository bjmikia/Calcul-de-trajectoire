package Vue.Buttons;
import java.awt.Color;

public class Voiture extends MyButtons {

	private static final long serialVersionUID = 1L;
	
    public Voiture(int x, int y, int num){
        this.x = x;
        this.y = y;
        if (num == 1){
            setBackground(new Color(255,255,77));
        }else if (num == 2){
            setBackground(new Color(153,204,255));
        }else if (num == 3){
            setBackground(new Color(153,255,204));
        } else if (num == 4){
            setBackground(new Color(255,204,255));
        } else if (num == 5){
            setBackground(new Color(100,250,166));
        }else {
            setBackground(Color.ORANGE);
        }

    }
    
}
