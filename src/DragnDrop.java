import javax.swing.*;
import java.awt.*;

public class DragnDrop extends JFrame {

    public DragnDrop() {
        JFrame frame = new JFrame();
        Container cp = frame.getContentPane();
        frame.setTitle("DnD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);


        JPanel pan = new JPanel();
        pan.setBackground(Color.red);
        pan.setSize(new Dimension(400, 500));
        cp.add(pan);


//        JButton yeah = new JButton("Ba ouai morray");
//        cp.add(yeah);


        JPanel custom = new JPanel();
        custom.setBackground(Color.green);
        cp.add(custom);

        frame.setVisible(true);
    }
}
