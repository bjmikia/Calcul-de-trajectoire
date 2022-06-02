package Vue.Buttons;
import javax.swing.JButton;

public abstract class MyButtons extends JButton{
	
	private static final long serialVersionUID = 1L;
	int x;
    int y;
    
    MyButtons(){
        setEnabled(false);
    }
}