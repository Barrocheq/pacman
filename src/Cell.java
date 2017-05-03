import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Cell {
	
	private int stats; //0 = mur; 1 = libre
	private Color color;//mur = GRAY; libre = WHITE
	private int i;
	private int j;
	private boolean bonbon;
	
	public Cell(int stats,int i, int j){
		this.i = i;
		this.j = j;
		this.stats = stats;
		if(stats == 0){
			this.color = Color.GRAY;
			this.bonbon = false;
		}else{
			this.color = Color.WHITE;
			this.bonbon = true;
		}
	}
	
	public int geti(){
		return i;
	}
	
	public int getj(){
		return j;
	}
	
	public boolean getBonbon(){
		return this.bonbon;
	}
	
	public boolean mangeBonbon(){
		boolean tmp = this.bonbon;
		this.bonbon = false;
		if(tmp){
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
		if(this.bonbon){
			Ellipse2D ellipse = new Ellipse2D.Double(leftX+(scale/2)-5, topY+(scale/2)-5, 6, 6);
			g2d.setPaint(Color.BLUE);
			g2d.fill(ellipse);
		}
	}
}
