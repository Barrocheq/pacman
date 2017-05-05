import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Moteur extends Thread {

	private JFrame frame;
	private View view;
	private Model model;

	/**
	 * Constructeur par defaut
	 * @param view view du jeu
	 * @param model model du jeu
	 */
	public Moteur(View view,Model model) {
		this.frame = view.getFrame();
		this.view = view;
		this.model = model;

		this.start(); // lancement du thread
	}

	/**
	 * Fonction de lancement de notre moteur graphique
	 */
	public void run() {
		while(true) {
			this.frame.repaint();


			if(this.model.nbBonbon() == 0 ){
				System.out.println("GG");
				System.exit(0);
			}
			 else if(this.model.getState()) {
				for(Monstre m : this.model.getMonstre()) {
					if(m!=null && this.model.getHero().getCell().equals(m.getCell())) {
						m.meur();
						this.model.getHero().incScore(20);
					}
				}
			}
			else {
				for(Monstre m : this.model.getMonstre()) {
					if(this.model.getHero().getCell().equals(m.getCell())) {
						final JPanel glass = (JPanel) this.frame.getGlassPane();
						JLabel label = new JLabel();
						label.setForeground(Color.WHITE);
						label.setText("PERDU");
						label.setFont(new Font("Courier", Font.BOLD, 200));
						glass.add(label);
						glass.setVisible(true);
						System.out.println("Perdu");
						glass.repaint();
						//System.exit(0);
						try {
							Thread.sleep(1000000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}


			this.view.label.setText("Score : " + this.model.getHero().getScore());
		}
		
		
	}
}
