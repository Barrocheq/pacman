import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Moteur extends Thread {

	private JFrame frame;
	private View view;
	private Model model;
	private Controller controller;

	/**
	 * Constructeur par defaut
	 */
	public Moteur() {
		this.model = new Model();
		this.view = new View();
		this.controller = new Controller();

		this.frame = null;

		this.start(); // lancement du thread
	}

	/**
	 * Fonction de lancement de notre moteur graphique
	 */
	public void run() {

		boolean parti = true;
		int i = -1;

		while (true) {

			parti = true;
			i++;
			this.model.init("lvl" + i + ".txt");
			this.view.init(this.model, this.model.getSizeH(), this.model.getSizeL());
			this.model.startMonstre();
			this.model.startHero();
			this.frame = view.getFrame();
			this.controller.init(this.model, this.view);
			

			while (parti) {
				this.frame.repaint();

				if (this.model.nbBonbon() == 0) {

					this.model.stop();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					parti = false;
					break;
				} else if (this.model.getState()) {
					for (Monstre m : this.model.getMonstre()) {
						if (m != null && this.model.getHero().getCell().equals(m.getCell())) {
							m.meur();
							this.model.getHero().incScore(20);
						}
					}
				} else {
					for (Monstre m : this.model.getMonstre()) {
						if (this.model.getHero().getCell().equals(m.getCell())) {
							final JPanel glass = (JPanel) this.frame.getGlassPane();
							JLabel label = new JLabel();
							label.setForeground(Color.WHITE);
							label.setText("PERDU");
							label.setFont(new Font("Courier", Font.BOLD, 200));
							glass.add(label);
							glass.setVisible(true);
							System.out.println("Perdu");
							glass.repaint();
							// System.exit(0);
							try {
								Thread.sleep(1000000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
