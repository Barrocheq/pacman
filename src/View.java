import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.AbstractDocument;


public class View {

	private JFrame frame;
	public static final int SCALE = 48;
	private plateau plateau;
	private Model model;
	public JLabel label;
	private Container cp;
	private JPanel glass;
	private boolean wait;
	private JPanel cards;


    /**
     * Constructeur pour niveau de base
     */
	public View() {
		wait = true;

        this.frame = new JFrame();
        cp = frame.getContentPane();
        frame.setTitle("PacMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.glass = (JPanel) this.frame.getGlassPane();
		label = new JLabel();
		//label.setForeground(Color.WHITE);
		label.setText(String.format("<html><font color='rgb(%s, %s, %s)'>PERDU</font></html>", (int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
		label.setFont(new Font("Courier", Font.BOLD, this.SCALE));
		glass.add(label);
    }


	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public void menu() {
		this.glass.setVisible(false);

		this.frame.setSize(400, 400);

		cp.removeAll();

		JButton but = new JButton("Jouer");
		cp.add(but);

		but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wait = false;
			}
		});


		cp.revalidate();
		cp.repaint();
		this.frame.setVisible(true);
	}

	public boolean getWait() {
		if(this.wait) return true;
		else return false;
	}

	public void init(Model model, int sizeH, int sizeL) {

		this.model = model;
        cp.removeAll();

		this.frame.setSize(((sizeL+1) * SCALE), ((sizeH+2) * SCALE)) ;
        this.plateau = new plateau(this.model); // Dessins du plateau
        cp.add(this.plateau);

        cp.revalidate();
        cp.repaint();

		this.frame.setVisible(true);
    }

	public JFrame getFrame() {
		return this.frame;
	}
	
	public void perdu() {
		for(int i = 0; i < 5; i++) {
			this.label.setText(String.format("<html><font color='rgb(%s, %s, %s)'>PERDU</font></html>", (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
			this.glass.setVisible(true);
			glass.repaint();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}


class plateau extends JPanel {

	private Model model;
	private boolean anim = true;
	private int i = 0;
	private int SCALE = View.SCALE;

    /**
     * Constructeur par defaut
     * @param model
     */
	public plateau(Model model) {
		this.model = model;
	}

    /**
     * Fonction de dessins du plateau
     * @param g dessins
     */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		// Anti-aliasing
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(hints);
		
		for (int i = 0; i < this.model.getSizeL(); i++) {
			for (int j = 0; j < this.model.getSizeH(); j++) {
				this.model.getMap()[i][j].paintCell(g2, i * this.SCALE, j * this.SCALE, this.SCALE);
			}
		}
		try {

		    // Animation Hero
			if(this.model.getHero().nextIsPassable()) {
                this.model.getHero().paintHeroAnim(g2, this.SCALE, i);
                if (anim) {
                    i++;

                    if (i > 15)
                        anim = false;
                } else {
                    i--;

                    if (i < 0)
                        anim = true;
                }
            } else {
                this.model.getHero().paintHero(g2, this.SCALE);
            }

            // Dessins des monstres
			for(Monstre m : this.model.getMonstre()){
				m.paintMonstre(g2, this.SCALE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}





