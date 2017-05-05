import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hero extends Thread{

	private Cell cell;
	private int Score;
	private BufferedImage image;
	private Model model;
	private Direction lastDir;
	private Direction nextDir;
	private Direction move;
	private int ScaleX;
	private int ScaleY;

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

		try {
		    this.loadImage();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image Hero");
            e.printStackTrace();
        }

        // Lancement du thread
		this.start();

	}

	/**
	 * Fonction de chargement de l'image qui representera le hero lors de la personnalisation du jeu (os : detection OS)
	 * @throws IOException exception pour l'image
	 */
    protected void loadImage() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                image = ImageIO.read(new File("images\\pacman.png"));
            } else if (os.contains("nux") || os.contains("nix")) {
                System.out.println("Linux");
            } else {
                image = ImageIO.read(new File("images/pacman.png"));
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image Hero");
            e.printStackTrace();
        }
    }

	
	public int getScore(){
		return this.Score;
	}
	
	public void incScore(int x){
		this.Score = this.Score +x;
	}


	/**
	 * Fonction de mouvement du Hero
	 * @param dir direction du mouvement
	 */
	public void move(Direction dir) {
		Cell celltemp = this.model.getMap()[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];

		if(celltemp.passable()){
			this.cell = celltemp;
			int tmpbonbon = this.cell.mangeBonbon();

			if(tmpbonbon == 1) // bonbon classique
				this.Score++;
			else if(tmpbonbon == 2) { // bonbon magique
				this.Score += 10;
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
	public void setScaleX(int x){
		this.ScaleX = x;
	}
	public void setScaleY(int y){
		this.ScaleY = y;
	}
	public Direction getLastDir() { return this.lastDir; }
	public Direction getNextDir() { return this.nextDir; }

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

        if(!this.model.getState()){
            g2d.setPaint(Color.YELLOW); // Jaune si pas de super bonbon
        }else{
            g2d.setPaint(Color.RED); // Rouge si super bonbon
        }

        g2d.fillArc((this.cell.geti() * scale)+this.ScaleY, (this.cell.getj() * scale)+this.ScaleX, scale, scale, x - i, 300 + 2 * i);

        g2d.setColor(Color.black);
        g2d.drawArc((this.cell.geti() * scale)+this.ScaleY, (this.cell.getj() * scale)+this.ScaleX, scale, scale, x - i, 300 + 2 * i);

        g2d.setPaint(Color.BLACK);
        g2d.fillOval(((this.cell.geti() * scale) + (scale / y))+this.ScaleY, (this.cell.getj() * scale + (scale / 5))+this.ScaleX, scale / 6, scale / 6);

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

		if(!this.model.getState()){
			g2d.setPaint(Color.YELLOW);
		}else{
			g2d.setPaint(Color.RED);
		}

		g2d.fillArc((this.cell.geti() * scale)+this.ScaleY, (this.cell.getj() * scale)+this.ScaleX, scale, scale, x, 300);

		g2d.setColor(Color.black);
		g2d.drawArc((this.cell.geti() * scale)+this.ScaleY, (this.cell.getj() * scale)+this.ScaleX, scale, scale, x, 300);

		g2d.setPaint(Color.BLACK);
		g2d.fillOval(((this.cell.geti() * scale) + (scale / y))+this.ScaleY, (this.cell.getj() * scale + (scale / 5))+this.ScaleX, scale / 6, scale / 6);

	}


	public void run(){
		while(true){
			if(this.nextDir != null && this.model.getMap()[this.cell.geti()+this.nextDir.dI()][this.cell.getj()+this.nextDir.dJ()].passable()){
					this.lastDir = this.nextDir;
					this.move = this.lastDir;
					
					HeroMove heromove = new HeroMove(this,this.move);
					try {
						heromove.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.move(this.move);
					this.nextDir = null;
			}else if(this.lastDir != null){
				Cell celltemplast = this.model.getMap()[this.cell.geti()+this.lastDir.dI()][this.cell.getj()+this.lastDir.dJ()];
				if(celltemplast.passable()){
					//this.move(this.lastDir);
					this.move = this.lastDir;
					HeroMove heromove = new HeroMove(this,this.move);
					try {
						heromove.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.move(this.move);
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}

class HeroMove extends Thread{
	
	private Hero hero;
	private int scale;
	private Direction move;
	
	public HeroMove(Hero hero,Direction move) {
		this.hero = hero;
		this.scale = View.SCALE;
		this.move = move;
		this.start();
	}

	public void run(){
		
		for(int i=0;i<this.scale;i++){
			this.hero.setScaleX(this.move.dJ()*i);
			this.hero.setScaleY(this.move.dI()*i);
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.hero.setScaleX(0);
		this.hero.setScaleY(0);
	}
}
