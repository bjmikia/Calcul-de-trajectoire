package Vue.Buttons;

import java.awt.*;

public class Robot extends MyButtons{

    private static final long serialVersionUID = 1L;

    public Robot(int x, int y, int num){
        this.x = x;
        this.y = y;
        if (num == 1){
            setBackground(new Color(204,153,255));
        }else if (num == 2){
            setBackground(new Color(153,153,255));
        }else if (num == 3){
            setBackground(new Color(255,153,153));
        } else{
            setBackground(new Color(255,153,187));
        }

    }
}
