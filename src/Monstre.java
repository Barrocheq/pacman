import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monstre {

	private Cell cell;

	public Monstre(Cell cell) {
		this.cell = cell;
	}
	
	public void move(Direction dir,Cell[][] map){
		Cell celltemp = map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];
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
	

	public void paintMonstre(Graphics2D g2d, int scale) throws IOException {

		BufferedImage image = ImageIO.read(new File("images/monstre.png"));
		g2d.drawImage(image, this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, null);
	}
}
