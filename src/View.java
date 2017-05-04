import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class View {

	private JFrame frame;
	private static final int SCALE = 48;
	private plateau plateau;
	private Model model;
	public JLabel label;


    public View(Model model, int sizeH, int sizeL) {
        this.model = model;
        JFrame frame = new JFrame();
        Container cp = frame.getContentPane();
        frame.setTitle("PacMan");
        frame.setSize((sizeL * SCALE) + 25 +300, (sizeH * SCALE) + 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.plateau = new plateau(model);
        cp.add(this.plateau);


        JPanel LP = new JPanel();
        JLabel label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setText("Score : "+this.model.getHero().getScore());
        System.out.println(this.model.getHero().getScore());
        LP.setPreferredSize(new Dimension(300,(sizeH*SCALE)+50));
        LP.setBackground(Color.BLACK);
        LP.add(label);
        this.label = label;
        cp.add(LP, BorderLayout.EAST);

        frame.setVisible(true);
        this.frame = frame;
    }



	public View(Model model, int size) {
		this.model = model;
		JFrame frame = new JFrame();
		Container cp = frame.getContentPane();
		frame.setTitle("PacMan");
		frame.setSize((size * SCALE) + 25 +300, (size * SCALE) + 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.plateau = new plateau(model);
		cp.add(this.plateau);
		
		
		JPanel LP = new JPanel();
		JLabel label = new JLabel();
		label.setForeground(Color.WHITE);
		label.setText("Score : "+this.model.getHero().getScore());
		System.out.println(this.model.getHero().getScore());
		LP.setPreferredSize(new Dimension(300,(size*SCALE)+50));
		LP.setBackground(Color.BLACK);
		LP.add(label);
		this.label = label;
		cp.add(LP, BorderLayout.EAST);
		
		frame.setVisible(true);
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

}

class plateau extends JPanel {

	private Model model;
	private boolean anim = true;
	private int i = 0;
	private static final int SCALE = 48;

	public plateau(Model model) {
		this.model = model;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(hints);
		
		for (int i = 0; i < this.model.getSizeL(); i++) {
			for (int j = 0; j < this.model.getSizeH(); j++) {
				this.model.getMap()[i][j].paintCell(g2, i * this.SCALE, j * this.SCALE, this.SCALE);
			}
		}
		try {
			this.model.getHero().paintHeroAnim(g2, this.SCALE, i);

			if(anim) {
                i++;

                if(i > 15)
                    anim = false;
            } else {
			    i--;

			    if(i < 0)
			        anim = true;
            }



			for(Monstre m : this.model.getMonstre()){
				m.paintMonstre(g2, this.SCALE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}





