package Vue.Buttons;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.BasicStroke;
import javax.swing.JPanel;
import java.awt.Stroke;
import javax.swing.event.MouseInputListener;
import java.awt.Graphics2D;

public class Possible extends MyButtons implements MouseInputListener{

    private static final long serialVersionUID = 1L;
    private JPanel circuit;
    private int posButx;
    private int posButy;

    public Possible(int x, int y) {
        this.x = x;
        this.y = y;
        setBackground(Color.green);
    }

    public void setPosButx(int x){
        this.posButx = x;
    }
    
    public void setPosButy(int y){
        this.posButy = y;
    }
    
    //action pour le survolage

    //tracer la ligne de notre voiture a ce point
    public void mouseEntered(java.awt.event.MouseEvent evt){
        Graphics2D g = (Graphics2D) circuit.getGraphics();
        g.setStroke((Stroke) new BasicStroke(2f));
        g.setColor(Color.yellow);
        g.drawLine(500, 500, posButx, posButy);
       // repaint();
    }

    //supprime le trait
    public void mouseExited(java.awt.event.MouseEvent evt){
        
    }

    //quand on clic sur le bouton
    //  -> changement de la voiture
    //  -> algo pour mettre a jour 
    // (re creer circuit avec )

    @Override
    public void mouseClicked(MouseEvent ev) {}
    @Override
    public void mousePressed(MouseEvent eventMouse) {}
    @Override
    public void mouseReleased(MouseEvent eventMouse) {
    }
    @Override
    public void mouseDragged(MouseEvent event) {}
    @Override
    public void mouseMoved(MouseEvent event) {}

}
