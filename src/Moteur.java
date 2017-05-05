import javax.swing.JFrame;

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

		boolean parti = true;
		int i = -1;

		while(true) {

			parti = true;
			i++;
			this.model.init("lvl"+i+".txt");
			this.model.startHero();
			this.model.startMonstre();


			while (parti) {


				this.frame.repaint();


				if (this.model.nbBonbon() == 0) {

					this.model.stop();

					parti = false;
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
							System.out.println("Perdu");
							System.exit(0);
						}
					}
				}


				this.view.label.setText("Score : " + this.model.getHero().getScore());
			}
		}
		
		
	}
}
