import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Controller implements KeyListener {

	private Model model;
	private JFrame frame;
	private View view;


    /**
     * Constructeur par defaut
     * @param model model du jeu
     * @param view view du jeu
     */
	public Controller(Model model, View view) {
		this.model = model;
		this.frame = view.getFrame();
		this.view = view;
		this.frame.addKeyListener(this);
		this.frame.setFocusable(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}


    /**
     * Fonction de capture de nos evenements
     * @param e
     */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyChar() == 'z') {
			this.model.getHero().nextDir(Direction.NORTH);
		}

		if (e.getKeyChar() == 's') {
			this.model.getHero().nextDir(Direction.SOUTH);
		}

		if (e.getKeyChar() == 'd') {
			this.model.getHero().nextDir(Direction.EAST);
		}

		if (e.getKeyChar() == 'q') {
			this.model.getHero().nextDir(Direction.WEST);
		}

	}
}
