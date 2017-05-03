public class Main {

	public static void main(String[] args) {
		int taille = 21;
		Model model = new Model(taille);
		View view = new View(model,taille);
		view.start();
		Controller controller = new Controller(model, view);
	}
}
