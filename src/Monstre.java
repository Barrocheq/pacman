import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monstre extends Thread{

	protected Cell cell;
	protected Cell cellpop;
	protected Cell[][] map;
	protected Model model;
	protected Direction lastMove;
	private BufferedImage image;
	protected boolean vivant;
	private int repop;
	private Color color;
	private int ScaleY;
	private int ScaleX;
	private boolean stop;
	private int speed;
	private boolean running;


	public Monstre(Cell cell,Model model,int repop, int speed) {
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
		this.speed = speed;
		this.running = true;

	}

	public Monstre(Cell cell) {
		this.ScaleX = 0;
		this.ScaleY = 0;
		this.color = Color.GREEN;
		this.cell = cell;
		this.cellpop = cell;
		this.vivant = true;
		this.stop = false;
		this.model = null;
		this.running = true;
	}
	
	protected Monstre(Cell cell,Model model,int repop,Color color, int speed) {
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
		this.speed = speed;
		this.running = true;

	}

	public void move(Direction dir){
		Cell celltemp = this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];
		if(celltemp.passable()){
			this.cell = celltemp;
			this.lastMove = dir;
		}
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}
	public Cell getCell() {
		return this.cell;
	}
	
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
	
	public Direction Dir(){
		Direction[] dirs = movePossible();
		if(dirs==null)return null;
		return dirs[(int) (dirs.length*Math.random())];
		
	}
	
	public void meur(){
		this.vivant = false;
		this.cell=this.cellpop;
		this.lastMove = null;
	}
	
	public void repop(){
		this.vivant = true;
	}

	public void pause() {
		synchronized (this.model) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.err.println("Erreur mise en pause Monstre");
			}
		}
	}

	public void paintMonstre(Graphics2D g2d, int scale) throws IOException {
		if(this.vivant){
			//g2d.drawImage(image, this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, null);
			if(this.model == null) {
				g2d.setPaint(Color.GREEN);

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


    public void stopMonstre() {
        this.stop = true;
    }

	public void pauseThread() throws InterruptedException {
		running = false;
	}

	public void resumeThread() {
		running = true;
	}



	@Override
	public void run() {
		while(!stop){

			while(!running)
				yield();

			if(this.vivant){
				
				Direction dir = this.Dir();
				if(dir!=null){
					System.out.println("null");
					for(int i = 0; i < View.SCALE; i++){
	                    if(stop) break;
						this.ScaleX = dir.dJ()*i;
						this.ScaleY = dir.dI()*i;
						try {
							Thread.sleep(100/speed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	
					}
					this.ScaleX = 0;
					this.ScaleY = 0;
	
	                if(stop) break;
					this.move(dir);
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