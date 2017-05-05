public class Main {

    /**
     * Fonction principale du programme
     * @param args
     */
	public static void main(String[] args) {
		int tailleH, tailleL;
		Model model = new Model();
		model.init("lvl0.txt");

		tailleH = model.getSizeH();
		tailleL = model.getSizeL();

        View view = new View(model, 21);
        Controller controller = new Controller(model, view);
        Moteur BrapBrap = new Moteur(view, model);
		//View view = new View(model, tailleH, tailleL);

	}
}
