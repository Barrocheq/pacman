import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Moteur extends Thread {

	private JFrame frame;
	private View view;
	private Model model;
	private Controller controller;
	private boolean running;


	/**
	 * Constructeur par defaut
	 */
	public Moteur() {
		this.model = new Model();
		this.view = new View();
		this.controller = new Controller(this);
		this.controller.init(this.model, this.view);

		this.frame = null;

		this.running = true;

		this.start(); // lancement du thread
	}

	public void pauseThread() throws InterruptedException {
		running = false;
	}

	public void resumeThread() {
		running = true;
	}


	/**
	 * Fonction de lancement de notre moteur graphique
	 */
	public void run() {

		
		boolean parti = true;
		int i = -1;
		int taille = 9;
		int vie = 0;


		while (true) {
			this.view.menu();
			while(this.view.getWait() && vie == 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			taille += 2;
			parti = true;
			i++;

			if(this.view.getChoixLvl() == 0 ) {
				this.model.init(taille);
			}
			else if(this.view.getChoixLvl() == 1) {
				//this.model.init("lvl" + i + ".txt");
				this.model.init("tmp.txt");
			}
			else if(this.view.getChoixLvl() == 2) {
				DND dnd = new DND(15, this);
				Controller controleur = new Controller(dnd);

				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					this.model.init(dnd.getNameFile());
				}

			}
			else
				System.err.println("Errurs choix LVL");

			//this.model.init("lvl" + i + ".txt");
			//this.model.init(taille);

			this.model.startHero();
			this.model.startMonstre();

			this.view.init(this.model, this.model.getSizeH(), this.model.getSizeL());
			this.frame = view.getFrame();



			parti : while (parti) {
				this.frame.repaint();

				while(!running)
					yield();

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
							this.model.incScore(20);
						}
					}
				} else {
					for (Monstre m : this.model.getMonstre()) {
						if (this.model.getHero().getCell().equals(m.getCell())) {
							this.model.stop();
							this.view.setWait(true);
							vie++;
							this.view.perdu(vie);
							taille -=2;
							i--;

							if(vie == 3){
								i = -1;
								taille = 5;
								vie = 0;
								try {
									this.model.saveScore();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}


							break parti;
						}
					}
				}
			}
		}
	}
}
