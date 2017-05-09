/* TODO
clignotement a partir de 30% du temps de base
 */

import java.awt.*;

public class BonbonMagique extends Thread {
    private int time;
    private Model model;

    public BonbonMagique(int time,Model model){
            this.time = time;
            this.model = model;
            this.start();
        }

    public void addTime(int time) {
        this.time += time;
    }

    public void stopBonbon() {
        this.time = 0;
    }

    public void run(){
        synchronized (this.model) {
            this.model.setState(true);
            this.model.getHero().setColor(Color.red);

            while(time > 0) {
                System.out.println(time);

                if(time > 4000) {
                    this.model.getHero().setColor(Color.red);
                }
                else if(time < 4000) {
                    System.out.println("C " + this.model.getHero().getColor() + " , t : " + time);
                    this.model.getHero().blink();
                }

                try {
                    Thread.sleep(500);
                    time -= 500;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            this.model.getHero().setColor(Color.yellow);
            this.model.setState(false);
            this.model.finEat();
        }
    }
}
