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


public class View {

	private JFrame frame;
	private JFrame framemenu;
	public static final int SCALE = 48;
	private plateau plateau;
	private Model model;
	public JLabel label;
	private Container cp;
	private JPanel glass;
	public static boolean jeu = false;


    /**
     * Constructeur pour niveau de base
     */
	public View() {
		View.jeu = false;
        this.frame = new JFrame();
        cp = frame.getContentPane();
        frame.setTitle("PacMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel glass = (JPanel) this.frame.getGlassPane();
		JLabel label = new JLabel();
		label.setForeground(Color.WHITE);
		label.setText("PERDU");
		label.setFont(new Font("Courier", Font.BOLD, this.SCALE));
		glass.add(label);
		this.glass = glass;
    }
	
	public void startPage(){
		if(this.frame != null && this.frame.isVisible()){this.frame.setVisible(false);}
		JFrame framemenu = new JFrame();
        framemenu.setTitle("PacMan");
        framemenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framemenu.setLayout(new FlowLayout());
		JButton button = new JButton("Jouer");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				View.jeu = true;
				
			}
			
		});
		framemenu.getContentPane().add(button);
		framemenu.setSize(500,500);
		framemenu.setVisible(true);
		this.framemenu = framemenu;

	}

	public void init(Model model, int sizeH, int sizeL) {
		this.glass.setVisible(false);
		this.framemenu.setVisible(false);
        this.model = model;

        frame.setSize(((sizeL+1) * SCALE), ((sizeH+2) * SCALE)) ;
        this.plateau = new plateau(model); // Dessins du plateau
        cp.add(this.plateau);

        frame.setVisible(true);
        this.frame = frame;
    }

	public JFrame getFrame() {
		return this.frame;
	}
	
	public void perdu(){

		this.glass.setVisible(true);
		glass.repaint();
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





