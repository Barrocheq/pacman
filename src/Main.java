public class Main {

	public static void main(String[] args) {
		int taille = 10;
		Model model = new Model(taille);
		View view = new View(model,taille);
		Controller controller = new Controller(model, view);
	}
}
