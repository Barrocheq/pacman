import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Cell {
	
	private int stats; //0 = mur; 1 = libre
	private Color color;//mur = GRAY; libre = WHITE
	private Color colorBonbon;//1=bleu;2=red
	private int i;
	private int j;
	private int bonbon; //0=pas de bonbon ;1=bonbon norale; 2=super bonbon
	
	public Cell(int stats,int i, int j){
		this.i = i;
		this.j = j;
		this.stats = stats;
		if(stats == 0){
			this.color = Color.GRAY;
			this.bonbon = 0;
		}else{
			this.color = Color.WHITE;
			double rand = Math.random();
			if(rand<0.02){
				this.bonbon = 2;
				this.colorBonbon = Color.RED;
			}else{
				this.bonbon = 1;
				this.colorBonbon = Color.BLUE;
			}
			
		}
	}
	
	public int geti(){
		return i;
	}
	
	public int getj(){
		return j;
	}
	
	public int getBonbon(){
		return this.bonbon;
	}
	
	public boolean mangeBonbon(){
		int tmp = this.bonbon;
		this.bonbon = 0;
		if(tmp>0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean passable(){
		return this.stats==1;
	}
	
	public void paintCell(Graphics2D g2d, int leftX, int topY, int scale) {
		Rectangle2D rect = new Rectangle2D.Double(leftX, topY, scale, scale);
		g2d.setPaint(this.color);
		g2d.fill(rect);
		if(this.bonbon>0){
			Ellipse2D ellipse = new Ellipse2D.Double(leftX+(scale/2)-5, topY+(scale/2)-5, 6, 6);
			g2d.setPaint(this.colorBonbon);
			g2d.fill(ellipse);
		}
	}
}
