import java.awt.*;

/**
 * Classe representant le bonbonMagique
 */
public class BonbonMagique extends Thread {
    private int time;
    private Model model;
    private boolean running;

    /**
     * Constructeur de base
     * @param time temps pendant lequel le bonbon est actif
     * @param model model du jeu
     */
    public BonbonMagique(int time,Model model){
            this.time = time;
            this.model = model;
            this.running = true;
            this.start();
        }

    /**
     * Ajout de temps d'existance du bonbon
     * @param time nombre de milliseconde
     */
    public void addTime(int time) {
        this.time += time;
    }

    /**
     * Permet d'arreter le Thread
     */
    public void stopBonbon() {
        this.time = 0;
    }


    /**
     * Fonction de mise en pause
     */
    public void pauseThread() {
        running = false;
    }
    public void resumeThread() {
        running = true;
    }


    /**
     * Lancement du Thread
     */
    public void run(){
        this.model.setState(true);
        this.model.getHero().setColor(Color.red);

        while(time > 0) {

            // Mise en pause
            while(!running)
                yield();


            // Clignotement 3sec avant la fin
            if(time > 3000)
                this.model.getHero().setColor(Color.red);

            else if(time < 3000)
                this.model.getHero().blink();


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
