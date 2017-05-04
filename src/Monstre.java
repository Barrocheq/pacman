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
	
	
	public Monstre(Cell cell,Model model,int repop) {
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
			g2d.drawImage(image, this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, null);

		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(this.vivant){
				this.move(this.randDir());
				try {
					Thread.sleep(500);
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
}
