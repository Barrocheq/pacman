import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monstre extends Thread{

	private Cell cell;
	private Cell cellpop;
	private Cell[][] map;
	private Model model;
	private Direction lastMove;
	private BufferedImage image;
	private boolean vivant;
	private int repop;
	private Color color;
	private int ScaleY;
	private int ScaleX;
	
	
	public Monstre(Cell cell,Model model,int repop, Color color) {
		this.ScaleX = 0;
		this.ScaleY = 0;
		this.color = color;
		this.repop = repop;
		this.model = model;
		this.map = model.getMap();
		this.cell = cell;
		this.cellpop = cell;
		this.vivant = true;

		try {
            this.loadImage();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image monster");
            e.printStackTrace();
        }

		this.start();
	}

	protected void loadImage() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                image = ImageIO.read(new File("images\\"
                        + "monstre.png"));
            } else if (os.contains("nux") || os.contains("nix")) {
                System.out.println("Linux");
            } else {
                image = ImageIO.read(new File("images/"
                        + "monstre.png"));
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image monster");
            e.printStackTrace();
        }
    }
	
	public void move(Direction dir){
		Cell celltemp = this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];
		if(celltemp.passable()){
			this.cell = celltemp;
			this.lastMove = dir;
		}
	}

	public void changeCell(Cell cell) {
		this.cell = cell;
	}

	public Cell getCell() {
		return this.cell;
	}
	
	public Direction[] movePossible(){
		int i=0;
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
		
		if(i==0){
			for(Direction dir: Direction.values()){
				if(this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()].passable()){
					tmp[i]=dir;
					i++;
				}
			}
		}
		Direction[] res = new Direction[i];
		for(int j=0; j<i;j++){
			res[j]=tmp[j];
		}
		return res;
	}
	
	public Direction randDir(){
	
		Direction[] dirs = movePossible();
		return dirs[(int) (dirs.length*Math.random())];
		
	}
	
	public void meur(){
		this.vivant = false;
		this.cell=this.cellpop;
	}
	
	public void repop(){
		this.vivant = true;
		
	}
	
	

	public void paintMonstre(Graphics2D g2d, int scale) throws IOException {
		if(this.vivant){
			//g2d.drawImage(image, this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, null);
			if(this.model.getState()){
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(this.vivant){
				Direction dir = this.randDir();
				MonstreMove monstremove = new MonstreMove(this,dir);
				try {
					monstremove.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.move(dir);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					Thread.sleep(this.repop);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.vivant = true;
			}

		}

		
	}

	public void setScaleY(int i) {
		this.ScaleY = i;
		
	}

	public void setScaleX(int i) {
		this.ScaleX = i;
		
	}
}


class MonstreMove extends Thread{
	
	private Monstre monstre;
	private int scale;
	private Direction move;
	
	public MonstreMove(Monstre monstre,Direction move) {
		this.monstre = monstre;
		this.scale = View.SCALE;
		this.move = move;
		this.start();
	}

	public void run(){
		
		for(int i=0;i<this.scale;i++){
			this.monstre.setScaleX(this.move.dJ()*i);
			this.monstre.setScaleY(this.move.dI()*i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.monstre.setScaleX(0);
		this.monstre.setScaleY(0);
	}
}
