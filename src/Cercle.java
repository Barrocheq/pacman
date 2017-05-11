
import javax.swing.*;
import java.awt.*;

class Cercle extends Thread{
	
	private Model model;
	private int size;
	private int i;
	private int j;
	private boolean running;

    public Cercle(Model model) {

    	this.model = model;
    	this.size = 0;
		this.i = this.model.getHero().getCell().geti();
		this.j = this.model.getHero().getCell().getj();
        this.running = true;

        this.start();
    }
    
    public void paintCercle(Graphics g,int SCALE) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.drawOval((this.i*SCALE)-((this.size+50)/2), (SCALE*this.j)-((this.size+50)/2), this.size+50, this.size+50);
		g2.setStroke(new BasicStroke(2));
		g2.drawOval((this.i*SCALE)-((this.size+100)/2), (SCALE*this.j)-((this.size+100)/2), this.size+100, this.size+100);
		g2.setStroke(new BasicStroke(3));
		g2.drawOval((this.i*SCALE)-((this.size+150)/2), (SCALE*this.j)-((this.size+150)/2), this.size+150, this.size+150);
	}
    
    public void run(){
        for(int i=0; i<10000; i++){
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


	public void pauseThread() throws InterruptedException {
		this.running = false;
	}

	public void resumeThread() {
		this.running = true;
	}
}

