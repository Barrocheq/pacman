import java.awt.*;


/**
 * Classe de gestion des cercles concentriques
 */
class Cercle extends Thread{
	
	private final Model model;
	private int size;
	private final int i;
	private final int j;
	private boolean running;

	/**
	 * Constructeur
	 * @param model model du jeu
	 */
    public Cercle(Model model) {

    	this.model = model;
    	this.size = 0;
		this.i = this.model.getHero().getCell().geti();
		this.j = this.model.getHero().getCell().getj();
        this.running = true;

        this.start();
    }

	/**
	 * Dessins du Cercle
	 * @param g
	 * @param SCALE
	 */
	public void paintCercle(Graphics g,int SCALE) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.drawOval((this.i*SCALE)-((this.size+50)/2), (SCALE*this.j)-((this.size+50)/2), this.size+50, this.size+50);
		g2.setStroke(new BasicStroke(2));
		g2.drawOval((this.i*SCALE)-((this.size+100)/2), (SCALE*this.j)-((this.size+100)/2), this.size+100, this.size+100);
		g2.setStroke(new BasicStroke(3));
		g2.drawOval((this.i*SCALE)-((this.size+150)/2), (SCALE*this.j)-((this.size+150)/2), this.size+150, this.size+150);
	}

	/**
	 * Fonction de gestion de pause
	 */
	public void pauseThread() { this.running = false; }
	public void resumeThread() {
		this.running = true;
	}

	/**
	 * Lancement du Thread
	 */
	public void run(){
        for(int i=0; i<10000; i++){

        	// Mise en pause
        	while(!this.running)
				yield();

        	this.size = i;

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.model.setCercle(null);
    }



}

