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

            System.out.println("Start Thread : " + this.getId());

            this.model.setState(true);

            try {
                Thread.sleep(this.time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.model.finTime();

            System.out.println("End Thread : " + this.getId());
        }
    }
}
