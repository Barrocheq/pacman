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
	private int bonbon; //0 = pas de bonbon ; 1 = bonbon normale; 2 = super bonbon
	private int tailleBonbon;


	/**
	 * Constructeur par defaut de Cell
	 * @param stats etat de la case
	 * @param i coordonnée i de la case
	 * @param j coordonnée j de la case
	 * @param bonbon type du bonbon
	 */
	public Cell(int stats,int i, int j,int bonbon){
		this.i = i;
		this.j = j;
		this.stats = stats;
		this.bonbon = bonbon;
		if(stats == 0){
			this.color = Color.BLUE; // Couleurs du Mur
			this.bonbon = 0;
		}else{
			this.color = Color.BLACK; // Couleurs du Fond
			if(bonbon == 2){
				this.colorBonbon = Color.RED; // Couleur bonbon magique
				this.tailleBonbon = 10;
			}else{
				this.colorBonbon = Color.WHITE; // Couleur bonbon normale
				this.tailleBonbon = 6;
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
	public boolean passable(){
		return this.stats == 1;
	}

	/**
	 * Fonction de suppression du bonbon
	 * @return le nombre de points que rapporte le bonbon
	 */
	public int mangeBonbon(){
		int tmp = this.bonbon;
		this.bonbon = 0;
		return tmp;
	}

	/**
	 * Fonction de dessins de la case
	 * @param g2d dessins
	 * @param leftX coordonnée x
	 * @param topY coordonnée Y
	 * @param scale rapport
	 */
	public void paintCell(Graphics2D g2d, int leftX, int topY, int scale) {
		Rectangle2D rect = new Rectangle2D.Double(leftX, topY, scale, scale);
		g2d.setPaint(this.color);
		g2d.fill(rect);
		if(this.bonbon>0){
			Ellipse2D ellipse = new Ellipse2D.Double(leftX+(scale/2)-(this.tailleBonbon/2), topY+(scale/2)-(this.tailleBonbon/2), this.tailleBonbon, this.tailleBonbon);
			g2d.setPaint(this.colorBonbon);
			g2d.fill(ellipse);
		}
	}
}
