import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe mère decrivant les monstre
 */
public class Monstre extends Thread{

	protected Cell cell;
	protected Cell cellpop;
	protected Cell[][] map;
	protected Model model;
	protected Direction lastMove;
	protected boolean vivant;
	private int repop;
	private Color color;
	private int ScaleY;
	private int ScaleX;
	private boolean stop;
	private boolean running;


	/**
	 * Constructeur
	 * @param cell cellule du monstre
	 * @param model model du jeu
	 * @param repop temps de respawn des monstres
	 */
	public Monstre(Cell cell,Model model,int repop) {
		this.ScaleX = 0;
		this.ScaleY = 0;
		this.color = Color.GREEN;
		this.repop = repop;
		this.model = model;
		this.map = model.getMap();
		this.cell = cell;
		this.cellpop = cell;
		this.vivant = true;
		this.stop = false;
		this.running = true;

	}

	/**
	 * Constructeur
	 * @param cell cellule du monstre
	 * @param color couleurs du monstre
	 */
	public Monstre(Cell cell, Color color) {
		this.ScaleX = 0;
		this.ScaleY = 0;
		this.color = color;
		this.cell = cell;
		this.cellpop = cell;
		this.vivant = true;
		this.stop = false;
		this.model = null;
		this.running = true;
	}

	/**
	 * Constructeur
	 * @param cell cellule du monstre
	 * @param model model du jeu
	 * @param repop temps de respawn du montre
	 * @param color coleur du monstre
	 */
	protected Monstre(Cell cell,Model model,int repop,Color color) {
		this.ScaleX = 0;
		this.ScaleY = 0;
		this.color = color;
		this.repop = repop;
		this.model = model;
		this.map = model.getMap();
		this.cell = cell;
		this.cellpop = cell;
		this.vivant = true;
		this.stop = false;
		this.running = true;

	}

	/**
	 * Permet de faire bouger un monstre
	 * @param dir Enumeration Direction qui correspond au sens du mouvement
	 */
	public void move(Direction dir){
		Cell celltemp = this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];
		if(celltemp.passable()){
			this.cell = celltemp;
			this.lastMove = dir;
		}
	}

	// GETTEURS ET SETTEURS
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	public Cell getCell() {
		return this.cell;
	}


	/**
	 * Calcule des mouvements possible pour un monstre
	 * @return les directions possibles
	 */
	public Direction[] movePossible() {
		int i = 0;
		Direction[] tmp = new Direction[4];

		for(Direction dir: Direction.values()){

			if(this.lastMove != null && (this.lastMove.dI() == (dir.dI()*-1) && this.lastMove.dJ() == (dir.dJ()*-1))){
				continue;
			}

			if(this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()].passable()){
				tmp[i]=dir;
				i++;
			}
		}
		
		if(i == 0){
			for(Direction dir: Direction.values()){
				if(this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()].passable()){
					tmp[i]=dir;
					i++;
				}
			}
		}
		if(i==0)return null;
		Direction[] res = new Direction[i];
		for(int j=0; j<i;j++){
			res[j]=tmp[j];
		}
		return res;
	}

	/**
	 * Choisis la direction du monstre
	 * @return Enum DIR
	 */
	public Direction Dir(){
		Direction[] dirs = movePossible();
		if(dirs==null)return null;
		return dirs[(int) (dirs.length*Math.random())];
	}

	/**
	 * Tue un monstre
	 */
	public void dead(){
		this.vivant = false;
		this.cell=this.cellpop;
		this.lastMove = null;
	}


	/**
	 * Dessin du montre
	 * @param g2d
	 * @param scale taille du SCALE
	 * @throws IOException
	 */
	public void paintMonstre(Graphics2D g2d, int scale) throws IOException {
		if(this.vivant){
			//g2d.drawImage(image, this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, null);
			if(this.model == null) {
				g2d.setPaint(this.color);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+this.ScaleX, scale/2, scale/2);

				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+(scale/6)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+(scale/3)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);

				g2d.fillRect((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/2, scale/2);

				g2d.setPaint(Color.WHITE);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(3*scale/4)-(scale/6)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/6, scale/6);


				g2d.fillOval((this.cell.geti()*scale)+(scale/2)-(scale/12)+this.ScaleY, (this.cell.getj()*scale)+(scale/2)-(scale/12)+this.ScaleX, scale/6, scale/6);


			}
			else if(this.model.getState()){
				g2d.setPaint(Color.BLUE);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+this.ScaleX, scale/2, scale/2);
				
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+(scale/6)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+(scale/3)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);
				
				g2d.fillRect((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/2, scale/2);
				
				g2d.setPaint(Color.WHITE);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(3*scale/4)-(scale/6)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/6, scale/6);
				

				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine((this.cell.geti()*scale)+(scale/2)-(scale/7)+this.ScaleY, (this.cell.getj()*scale)+(scale/2)+this.ScaleX, (this.cell.geti()*scale)+(scale/2)+(scale/7)+this.ScaleY, (this.cell.getj()*scale)+(scale/2)+this.ScaleX);
			}else{
				g2d.setPaint(this.color);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+this.ScaleX, scale/2, scale/2);
				
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+(scale/6)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+(scale/3)+this.ScaleY, (this.cell.getj()*scale)+(3*scale/4)-(scale/12)+this.ScaleX, scale/6, scale/6);
				
				g2d.fillRect((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/2, scale/2);
				
				g2d.setPaint(Color.WHITE);
				g2d.fillOval((this.cell.geti()*scale)+(scale/4)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/6, scale/6);
				g2d.fillOval((this.cell.geti()*scale)+(3*scale/4)-(scale/6)+this.ScaleY, (this.cell.getj()*scale)+(scale/4)+this.ScaleX, scale/6, scale/6);
				

				g2d.fillOval((this.cell.geti()*scale)+(scale/2)-(scale/12)+this.ScaleY, (this.cell.getj()*scale)+(scale/2)-(scale/12)+this.ScaleX, scale/6, scale/6);
			}
			

		}
	}


	/**
	 * Arrete le Thread
	 */
    public void stopMonstre() {
        this.stop = true;
    }


	/**
	 * GESTION PAUSE
	 */
	public void pauseThread() { running = false;}
	public void resumeThread() {
		running = true;
	}


	/**
	 * Fonction de lancement du Thread
	 */
	@Override
	public void run() {
		while(!stop){

			while(!running)
				yield();

			if(this.vivant){
				
				Direction dir = this.Dir();
				if(dir!=null){

					for(int i = 0; i < View.SCALE; i++){
	                    if(stop) break;
						this.ScaleX = dir.dJ()*i;
						this.ScaleY = dir.dI()*i;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	
					}
					this.ScaleX = 0;
					this.ScaleY = 0;
	
	                if(stop) break;

	                synchronized (this.model) {
						this.move(dir);
					}
				}
			} else{
				try {
					Thread.sleep(this.repop);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.vivant = true;
			}

		}

		
	}

}