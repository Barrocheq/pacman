import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

public class Controller implements KeyListener, MouseListener, MouseMotionListener{

	private Model model;
	private Moteur moteur;
	private JFrame frame;
	private View view;
    private DND dnd;
    private int cell;
    private boolean pause;

    public boolean getPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     * Constructeur par defaut
     */
	public Controller() {
        this.pause = false;
	}

    public Controller(Moteur moteur) {
        this.moteur = moteur;
        this.pause = false;
    }

    public Controller(DND dnd) {
        // TODO Auto-generated constructor stub
        this.dnd = dnd;
        dnd.getPanel().addMouseListener(this);
        dnd.getPanel().addMouseMotionListener(this);
        this.cell = 0;
        this.pause = false;
    }

	public void init(Model model, View view) {
        this.model = model;
        this.frame = view.getFrame();
        this.view = view;
        this.frame.addKeyListener(this);
        this.frame.setFocusable(true);
        this.pause = false;
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
	public synchronized void keyTyped(KeyEvent e) {
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

        if (Character.isSpaceChar(e.getKeyChar())) {
            synchronized (this.moteur) {

                this.pause = !this.pause;

                if (this.pause) {
                    try {

                        // Permet de mettre en pause uniquement si le thread de bonbonMagique est lancé
                        if (this.model.getMangeBonbon() != null)
                            this.model.getMangeBonbon().pauseThread();

                        this.model.getHero().pauseThread();
                        if(this.model.getCercle()!=null)
                        this.model.getCercle().pauseThread();

                        for (Monstre m : this.model.getMonstre())
                            m.pauseThread();

                        this.moteur.pauseThread();

                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    this.view.setPause();

                } else if (!this.pause) {

                    if (this.model.getMangeBonbon() != null)
                        this.model.getMangeBonbon().resumeThread();

                    this.model.getHero().resumeThread();
                    if(this.model.getCercle()!=null)
                    	this.model.getCercle().resumeThread();
                    for (Monstre m : this.model.getMonstre())
                        m.resumeThread();

                    this.moteur.resumeThread();
                    
                    this.view.releasedPause();

                } else
                    System.err.println("Erreur mise en pause null");
            }
        }

	}

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Pressed");
        System.out.println("X: "+((e.getX()/View.SCALE)));
        System.out.println("Y: "+((e.getY()/View.SCALE)));
        if(e.getX()/View.SCALE==dnd.getSize()+1 && (e.getY()/View.SCALE)==2){
            this.cell = 1;
        } else if (e.getX()/View.SCALE==dnd.getSize()+3 && (e.getY()/View.SCALE)==2) {
            this.cell = 2;
        } else if (e.getX()/View.SCALE==dnd.getSize()+1 && (e.getY()/View.SCALE)==4) {
            this.cell = 3;
        } else if (e.getX()/View.SCALE==dnd.getSize()+3 && (e.getY()/View.SCALE)==4) {
            this.cell = 4;
        } else if (e.getX()/View.SCALE==dnd.getSize()+1 && (e.getY()/View.SCALE)==6) {
            this.cell = 5;
        } else if (e.getX()/View.SCALE==dnd.getSize()+3 && (e.getY()/View.SCALE)==6) {
            this.cell = 6;
        } else if (e.getX()/View.SCALE==dnd.getSize()+1 && (e.getY()/View.SCALE)==8) {
            this.cell = 7;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Released");
        if(e.getX()/View.SCALE>0 && e.getX()/View.SCALE<this.dnd.getSize()-1 && e.getY()/View.SCALE>0 && e.getY()/View.SCALE<this.dnd.getSize()-1){
            if(this.cell == 1){
                this.dnd.getMap()[(e.getX()/View.SCALE)][(e.getY()/View.SCALE)] = new Cell(0,e.getX()/View.SCALE,(e.getY()/View.SCALE),0);
                this.dnd.getFakeCell().setI(this.dnd.getSize()+1);
                this.dnd.getFakeCell().setJ(2);
                
            } else if(this.cell == 2) {
                this.dnd.getMap()[(e.getX()/View.SCALE)][(e.getY()/View.SCALE)] = new Cell(1,e.getX()/View.SCALE,(e.getY()/View.SCALE),2);
                
            } else if(this.cell == 3) {
                this.dnd.setHero(new Hero(this.dnd.getMap()[e.getX()/View.SCALE][e.getY()/View.SCALE]));
                
            } else if(this.cell == 4) {
                this.dnd.setLmonstre(new Monstre(this.dnd.getMap()[e.getX()/View.SCALE][e.getY()/View.SCALE], Color.GREEN));

                this.dnd.getFakeMonster().getCell().setI(this.dnd.getSize()+3);
                this.dnd.getFakeMonster().getCell().setJ(4);

                
            } else if(this.cell == 5) {
                this.dnd.setLmonstre(new Monstre2(this.dnd.getMap()[e.getX()/View.SCALE][e.getY()/View.SCALE]));

                this.dnd.getFakeMonster2().getCell().setI(this.dnd.getSize()+1);
                this.dnd.getFakeMonster2().getCell().setJ(6);

                
            } else if(this.cell == 6) {
                this.dnd.setLmonstre(new Monstre3(this.dnd.getMap()[e.getX()/View.SCALE][e.getY()/View.SCALE]));

                this.dnd.getFakeMonster3().getCell().setI(this.dnd.getSize()+3);
                this.dnd.getFakeMonster3().getCell().setJ(6);

                
            } else if(this.cell == 7) {
                this.dnd.setLmonstre(new Monstre4(this.dnd.getMap()[e.getX() / View.SCALE][e.getY() / View.SCALE]));

                this.dnd.getFakeMonster4().getCell().setI(this.dnd.getSize() + 1);
                this.dnd.getFakeMonster4().getCell().setJ(8);

                
            }
        }
        this.cell = 0;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
	    if(this.cell == 1) {
            this.dnd.getFakeCell().setI(e.getX()-(View.SCALE/2));
            this.dnd.getFakeCell().setJ(e.getY()-(View.SCALE/2));
        } else if(this.cell == 2) {

        } else if(this.cell == 3) {
            this.dnd.getFakeHero().getCell().setI(e.getX()/ View.SCALE);
            this.dnd.getFakeHero().getCell().setJ(e.getY()/ View.SCALE);
        } else if(this.cell == 4) {
            this.dnd.getFakeMonster().getCell().setI(e.getX()/ View.SCALE);
            this.dnd.getFakeMonster().getCell().setJ(e.getY()/ View.SCALE);
        } else if(this.cell == 5) {
            this.dnd.getFakeMonster2().getCell().setI(e.getX()/ View.SCALE);
            this.dnd.getFakeMonster2().getCell().setJ(e.getY()/ View.SCALE);
        } else if(this.cell == 6) {
            this.dnd.getFakeMonster3().getCell().setI(e.getX()/ View.SCALE);
            this.dnd.getFakeMonster3().getCell().setJ(e.getY()/ View.SCALE);
        } else if(this.cell == 7) {
            this.dnd.getFakeMonster4().getCell().setI(e.getX()/ View.SCALE);
            this.dnd.getFakeMonster4().getCell().setJ(e.getY()/ View.SCALE);
        }

    }
}
