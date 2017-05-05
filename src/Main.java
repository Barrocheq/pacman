public class Main {

    /**
     * Fonction principale du programme
     * @param args
     */
	public static void main(String[] args) {
		int tailleH, tailleL;
		Model model = new Model(21);

		tailleH = model.getSizeH();
		tailleL = model.getSizeL();

        View view = new View(model, 21);
        Controller controller = new Controller(model, view);
		//View view = new View(model, tailleH, tailleL);

	}

    /**
     * Fonction de lancement du moteur graphique
     * @param model model du jeu
     * @param view view du jeu
     */
	public static void start(Model model, View view) {

        Moteur moteur = new Moteur(view, model);
    }
}
