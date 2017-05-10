import javax.swing.*;
import java.awt.*;

class Cercle extends Thread{

    private CerclePanel panel;
    private JFrame frame;

    public Cercle(JFrame frame) {
        this.frame = frame;
        Container cp = frame.getContentPane();
        panel = new CerclePanel(1000);
        cp.add(panel);

        System.out.println("Lancement Thread");

        this.start();
    }

    public Cercle() {
        Container cp = frame.getContentPane();
        panel = new CerclePanel(1000);
        cp.add(panel);


        this.start();
    }

    public void run(){
        for(int i=0; i<1500; i++){
            this.panel.incSize();
            this.frame.repaint();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
