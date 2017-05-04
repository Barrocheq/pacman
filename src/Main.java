public class Main {

	public static void main(String[] args) {
		int taille = 21;
		Model model = new Model(taille);
		View view = new View(model,taille);
		Controller controller = new Controller(model, view);
		Moteur moteur = new Moteur(view, model);
		moteur.start();
	}
}
