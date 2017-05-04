public class Main {

	public static void main(String[] args) {
		int tailleH, tailleL;
		Model model = new Model(21);

		tailleH = model.getSizeH();
		tailleL = model.getSizeL();

        View view = new View(model, 21);
//		View view = new View(model, tailleH, tailleL);
		Controller controller = new Controller(model, view);
		Moteur moteur = new Moteur(view, model);
		moteur.start();
	}
}
