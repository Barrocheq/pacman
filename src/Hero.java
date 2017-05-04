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

	public Hero(Cell cell, Model model) {
		this.cell = cell;
		this.model = model;

		try {
		    this.loadImage();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image Hero");
            e.printStackTrace();
        }

		this.start();

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
	
	public void incScore(int x){
		this.Score = this.Score +x;
	}

	
	public void move(Direction dir) {
		Cell celltemp = this.model.getMap()[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()];
		if(celltemp.passable()){
			this.cell = celltemp;
			int tmpbonbon = this.cell.mangeBonbon();
			if(tmpbonbon==1){
				this.Score++;
			}else if(tmpbonbon==2){
				this.Score = this.Score +10;
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

    public void paintHeroAnim(Graphics2D g2d, int scale, int i) throws IOException {

        int rotate = 0;
        int x = 0;
        int y = 0;

	    if(this.lastDir == Direction.NORTH) {
            rotate = 90;
            x = 0;
            y = 0;
        } else if(this.lastDir == Direction.SOUTH) {
            rotate = 90;
            x = 190;
            y = -20;
        } else if(this.lastDir == Direction.EAST) {
            rotate = 0;
            x = 0;
            y = 0;
        } else if(this.lastDir == Direction.WEST) {
            rotate = 0;
            x = 190;
            y = -20;
        } else {
            rotate = 0;
            x = 0;
            y = 0;
        }

        g2d.rotate(Math.toDegrees(rotate), this.cell.geti() * scale + scale/2, this.cell.getj() * scale + scale/2);


        if(!this.model.getState()){
            g2d.setPaint(Color.YELLOW);
        }else{
            g2d.setPaint(Color.RED);
        }


        g2d.fillArc(this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, 30 + x -i, 300 + y + 2 * i);
        //g2d.fillArc(this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, 30-i+2 * x, 300+2*i-y);

        g2d.setColor(Color.black);
        g2d.drawArc(this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, 30-i + x, 300+2*i + y);


        g2d.setPaint(Color.BLACK);
        g2d.fillOval((this.cell.geti() * scale) + (scale / 2), (this.cell.getj() * scale + (scale / 5)), scale / 6, scale / 6);

        g2d.rotate(Math.toDegrees(-rotate));

    }


	public void paintHero(Graphics2D g2d, int scale) throws IOException {
		//g2d.drawImage(image, this.cell.geti() * scale, this.cell.getj() * scale, scale, scale, null);

        //CONTOUR
        if(!this.model.getState()){
            g2d.setPaint(Color.YELLOW);
        }else{
            g2d.setPaint(Color.RED);
        }


        g2d.fillArc(this.cell.geti() * scale, this.cell.getj() * scale,scale,scale, 30, 300);

        g2d.setColor(Color.black);
        //g2d.setStroke(new BasicStroke(2));
        g2d.drawArc(this.cell.geti() * scale, this.cell.getj() * scale,scale,scale,30,300);

        //g2d.setColor(Color.BLUE);
        //g2d.drawLine(this.cell.geti() * scale + scale/2, this.cell.getj() * scale+ scale/2, this.cell.geti() * scale + scale / 4, this.cell.getj() * scale + scale / 4);

		/*g2d.setPaint(Color.BLACK);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawOval(this.cell.geti() * scale, this.cell.getj() * scale, scale-3, scale-3);



		// ROND

		
		g2d.fillOval(this.cell.geti() * scale, this.cell.getj() * scale, scale-3, scale-3);


		*/

		// OEUIL
		g2d.setPaint(Color.BLACK);
		g2d.fillOval((this.cell.geti() * scale)+(scale/2), (this.cell.getj() * scale+(scale/5)), scale/6, scale/6);

    /*
		// BOUCHE
		g2d.setPaint(Color.WHITE);
		int[] xPoints = {(this.cell.geti()*scale)+(scale/2),(this.cell.geti()+1)*scale,(this.cell.geti()+1)*scale};
		int[] yPoints = {(this.cell.getj()*scale)+(scale/2)+(scale/10),(this.cell.getj()*scale)+(3*scale/4)+(scale/10),(this.cell.getj()*scale)+(scale/4)+(scale/10)};
		g2d.fillPolygon(xPoints, yPoints, 3);*/
	}
	
	public void run(){
		while(true){
			
			if(this.nextDir != null){
				Cell celltempnext = this.model.getMap()[this.cell.geti()+this.nextDir.dI()][this.cell.getj()+this.nextDir.dJ()];
				if(celltempnext.passable()){
					this.lastDir = this.nextDir;
					this.move(this.lastDir);
					this.nextDir = null;
				}else{
					if(this.lastDir != null){
						Cell celltemplast = this.model.getMap()[this.cell.geti()+this.lastDir.dI()][this.cell.getj()+this.lastDir.dJ()];
						if(celltemplast.passable()){
							this.move(this.lastDir);
						}
					}
				}
			}else if(this.lastDir != null){
				Cell celltemplast = this.model.getMap()[this.cell.geti()+this.lastDir.dI()][this.cell.getj()+this.lastDir.dJ()];
				if(celltemplast.passable()){
					this.move(this.lastDir);
				}
			}
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
