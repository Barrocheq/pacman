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

    public void run(){
        synchronized (this.model) {
            this.model.setState(true);
            this.model.getHero().setColor(Color.red);

            try {
                Thread.sleep(this.time - 3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.model.getHero().blink(3000);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.model.getHero().stopBlink();
            this.model.getHero().setColor(Color.yellow);
            this.model.finTime();
        }
    }
}
