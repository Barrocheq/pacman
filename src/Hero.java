import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class Hero extends Thread{

	private Cell cell;
	private Model model;
	private Direction lastDir;
	private Direction nextDir;
	private int ScaleX;
	private int ScaleY;
	private boolean stop;
	private Color color;
    private Timer timer;
	private boolean running;
	private Music m;
	private boolean runMusic;

	/**
	 * Constructeur par defaut de la classe Hero
	 * @param cell case depart du hero
	 * @param model model du jeu
	 */
	public Hero(Cell cell, Model model) {
		this.cell = cell;
		this.model = model;
		this.ScaleX = 0;
		this.ScaleY = 0;
		this.stop = false;
		this.color = Color.yellow;
		this.running = true;
		this.runMusic = false;
		this.m = new Music(this);
	}

	public Hero(Cell cell) {
		this.cell = cell;
		this.ScaleX = 0;
		this.ScaleY = 0;
		this.stop = false;
		this.color = Color.yellow;
		this.running = true;
	}

	public Hero(Cell cell, RandomLvl r) {
		this.cell = cell;
		this.ScaleX = 0;
		this.ScaleY = 0;
		this.stop = false;
		this.color = Color.yellow;
		this.running = true;
		this.runMusic = false;
		this.m = new Music(this);

		//this.cell = r.getMap()[this.cell.geti()][this.cell.getj()];
	}

    public void setColor(Color c) {
        this.color = c;
    }


	public boolean getRunMusic() {
		return runMusic;
	}

	public void setRunMusic(boolean runMusic) {
		this.runMusic = runMusic;
	}

	/**
	 * Fonction de mouvement du Hero
	 * @param dir direction du mouvement
	 */
	public void move(Direction dir) {
		Cell celltemp = this.model.getMap()[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];


		if(celltemp.passable()){

			if(this.model.getMusic())
				m.launch();

			this.runMusic = true;
			this.cell = celltemp;
			int tmpbonbon = this.cell.mangeBonbon();

			if(tmpbonbon == 1) // bonbon classique
				this.model.incScore(1);
			else if(tmpbonbon == 2) { // bonbon magique
				this.model.incScore(10);
				this.model.mangeBonbonRouge();
			}
		}
	}


	public void nextDir(Direction dir){
		this.nextDir = dir;
	}
	public Cell getCell() {
		return this.cell;
	}
	public Direction getLastDir() { return this.lastDir; }
	public Direction getNextDir() { return this.nextDir; }

    public Color getColor() {
        return color;
    }

    /**
	 * Fonction qui regarde si la case suivante est passable
	 * @return true si elle l'est
	 */
	public boolean nextIsPassable() {
		if(this.lastDir == null)
			return false;
		else if (this.model.getMap()[this.cell.geti()+lastDir.dI()][this.cell.getj()+lastDir.dJ()].passable())
			return true;
		else
			return false;

	}

	public void blink() {
		if(this.color == Color.yellow) this.setColor(Color.red);
		else if(this.color == Color.red) this.setColor(Color.yellow);
		else System.err.println("Erreur blink(Hero)");
	}


	/**
	 * Fonction qui dessine le hero en l'animant (Bouche qui bouge)
	 * @param g2d dessins
	 * @param scale rapport de dessins (en fonction de la taille de la fênetre)
	 * @param i range d'animation
	 */
    public void paintHeroAnim(Graphics2D g2d, int scale, int i) throws IOException {

	    int x = 0; // angle de depart
	    int y = 0; // position de l'oeil

	    if(this.lastDir == Direction.NORTH) {
            x = 120;
            y = 4;
        } else if(this.lastDir == Direction.SOUTH) {
            x = 300;
            y = 4;
        } else if(this.lastDir == Direction.EAST) {
            x = 30;
            y = 2;
        } else if(this.lastDir == Direction.WEST) {
            x = 210;
            y = 2;
        } else {
            x = 30;
            y = 2;
        }

        g2d.setColor(this.color);

        g2d.fillArc((this.cell.geti() * scale)+this.ScaleY, (this.cell.getj() * scale)+this.ScaleX, scale, scale, x - i, 300 + 2 * i);

        g2d.setColor(Color.black);
        g2d.drawArc((this.cell.geti() * scale)+this.ScaleY, (this.cell.getj() * scale)+this.ScaleX, scale, scale, x - i, 300 + 2 * i);

        g2d.setPaint(Color.BLACK);
        g2d.fillOval(((this.cell.geti() * scale) + (scale / y))+this.ScaleY, (this.cell.getj() * scale + (scale / 5))+this.ScaleX, scale / 6, scale / 6);

    }


	public int getScaleX() {
		return ScaleX;
	}

	public void setScaleX(int scaleX) {
		ScaleX = scaleX;
	}

	public int getScaleY() {
		return ScaleY;
	}

	public void setScaleY(int scaleY) {
		ScaleY = scaleY;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	/**
	 * Fonction de dessins du hero non animé
	 * @param g2d dessins
	 * @param scale rapport de dessins (en fonction de la taille de la fênetre)
	 */
	public void paintHero(Graphics2D g2d, int scale) throws IOException {

		int x = 0;
		int y = 0;

		if(this.lastDir == Direction.NORTH) {
			x = 120;
			y = 4;
		} else if(this.lastDir == Direction.SOUTH) {
			x = 300;
			y = 4;
		} else if(this.lastDir == Direction.EAST) {
			x = 30;
			y = 2;
		} else if(this.lastDir == Direction.WEST) {
			x = 210;
			y = 2;
		} else {
			x = 30;
			y = 2;
		}


		g2d.setColor(this.color);

		g2d.fillArc((this.cell.geti() * scale)+this.ScaleY, (this.cell.getj() * scale)+this.ScaleX, scale, scale, x, 300);

		g2d.setColor(Color.black);
		g2d.drawArc((this.cell.geti() * scale)+this.ScaleY, (this.cell.getj() * scale)+this.ScaleX, scale, scale, x, 300);

		g2d.setPaint(Color.BLACK);
		g2d.fillOval(((this.cell.geti() * scale) + (scale / y))+this.ScaleY, (this.cell.getj() * scale + (scale / 5))+this.ScaleX, scale / 6, scale / 6);

	}

	public void stopHero() {
		this.stop = true;
	}

	public void pause() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.err.println("Erreur mise en pause Hero");
			}
		}
	}

	public void unPause() {
		synchronized (this) {
			this.notifyAll();
		}
	}

	public boolean getRunning() {
		return running;
	}

	public void pauseThread() throws InterruptedException {
		running = false;
	}

	public void resumeThread() {
		running = true;
	}

	public void run(){
		while(!stop){
			synchronized (this) {
			    //if(this.model.getState()) this.setColor(Color.red);
                //else this.setColor(Color.yellow);
				while(!running)
					yield();

				if (this.nextDir != null && this.model.getMap()[this.cell.geti() + this.nextDir.dI()][this.cell.getj() + this.nextDir.dJ()].passable()) {
					this.lastDir = this.nextDir;

					for (int i = 0; i < View.SCALE; i++) {
						if(stop) break;
						this.ScaleX = this.lastDir.dJ() * i;
						this.ScaleY = this.lastDir.dI() * i;
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					this.ScaleX = 0;
					this.ScaleY = 0;

					if(stop) break;
					this.move(this.lastDir);
					this.nextDir = null;
				} else if (this.lastDir != null && this.model.getMap()[this.cell.geti() + this.lastDir.dI()][this.cell.getj() + this.lastDir.dJ()].passable()) {


					for (int i = 0; i < View.SCALE; i++) {
						if(stop) break;
						this.ScaleX = this.lastDir.dJ() * i;
						this.ScaleY = this.lastDir.dI() * i;
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					this.ScaleX = 0;
					this.ScaleY = 0;

					if(stop) break;
					this.move(this.lastDir);
				}
			}
		}
	}
}
