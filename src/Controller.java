import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Controller implements KeyListener, MouseListener {

	private Model model;
	private JFrame frame;
	private View view;
    private DND dnd;
    private int cell;

    /**
     * Constructeur par defaut
     */
	public Controller() {

	}

    public Controller(DND dnd) {
        // TODO Auto-generated constructor stub
        this.dnd = dnd;
        dnd.getFrame().addMouseListener(this);
        this.cell = 0;
    }

	public void init(Model model, View view) {

		System.out.println("Controller started");

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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Pressed");
        System.out.println("X: "+((e.getX()/View.SCALE)));
        System.out.println("Y: "+((e.getY()/View.SCALE)-1));
        if(e.getX()/View.SCALE==dnd.getSize()+1 && (e.getY()/View.SCALE)-1==2){
            this.cell = 1;
        } else if (e.getX()/View.SCALE==dnd.getSize()+3 && (e.getY()/View.SCALE)-1==2) {
            this.cell = 2;
        } else if (e.getX()/View.SCALE==dnd.getSize()+1 && (e.getY()/View.SCALE)-1==4) {
            this.cell = 3;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Relesed");
        if(this.cell == 1){
            this.dnd.getMap()[(e.getX()/View.SCALE)][(e.getY()/View.SCALE)-1] = new Cell(0,e.getX()/View.SCALE,(e.getY()/View.SCALE)-1,0);
            this.cell = 0;
        } else if(this.cell == 2) {
            this.dnd.getMap()[(e.getX()/View.SCALE)][(e.getY()/View.SCALE)-1] = new Cell(1,e.getX()/View.SCALE,(e.getY()/View.SCALE)-1,2);
            this.cell = 0;
        } else if(this.cell == 3) {
            this.dnd.setHero(new Hero(this.dnd.getMap()[e.getX()/View.SCALE][e.getY()/View.SCALE-1]));
            this.cell = 0;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
