import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hero {

	private Cell cell;
	private int Score;
	private BufferedImage image;

	public Hero(Cell cell) {
		this.cell = cell;

		try {
		    this.loadImage();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image Hero");
            e.printStackTrace();
        }


	}

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

	
	public void move(Direction dir,Cell[][] map){
		Cell celltemp = map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];
		if(celltemp.passable()){
			this.cell = celltemp;
			
			if(this.cell.mangeBonbon()){
				this.Score++;
				System.out.println("Score : " + this.Score);
			}
		}
	}

	public Cell getCell() {
		return this.cell;
	}
	

	public void paintHero(Graphics2D g2d, int scale) throws IOException {
		g2d.drawImage(image, this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, null);
	}
}
