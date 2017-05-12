import java.awt.*;

public class BonbonMagique extends Thread {
    private int time;
    private Model model;
    private boolean running;

    public BonbonMagique(int time,Model model){
            this.time = time;
            this.model = model;
            this.running = true;
            this.start();
        }

    public void addTime(int time) {
        this.time += time;
    }

    public void stopBonbon() {
        this.time = 0;
    }


    public void pauseThread() {
        running = false;
    }

    public void resumeThread() {
        running = true;
    }


    public void run(){
            this.model.setState(true);
            this.model.getHero().setColor(Color.red);

            while(time > 0) {

                while(!running)
                    yield();


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
        //}
    }
}
