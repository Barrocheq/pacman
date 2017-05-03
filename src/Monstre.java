import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monstre extends Thread{

	private Cell cell;
	private Cell[][] map;
	private Model model;
	
	public Monstre(Cell cell,Model model) {
		this.model = model;
		this.map = model.getMap();
		this.cell = cell;
	}
	
	public void move(Direction dir){
		Cell celltemp = this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];
		if(celltemp.passable()){
			this.cell = celltemp;
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
			if(this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()].passable()){
				tmp[i]=dir;
				i++;
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
	
	
	

	public void paintMonstre(Graphics2D g2d, int scale) throws IOException {

		BufferedImage image = ImageIO.read(new File("images\\"
				+ "monstre.png"));
		g2d.drawImage(image, this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, null);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			this.move(this.randDir());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
}
