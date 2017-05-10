import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class DND extends Thread{

    private Panel panel;
    private JFrame frame;
    private Cell[][] map;
    private int size;
    private Hero hero;

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public DND(int size){

        this.size = size;
        this.map = new Cell[this.size+6][this.size];
        for(int i=0; i<size+6;i++){
            for(int j=0;j<size;j++){
                if(i==0 || j==0 || j==(size-1) || (i==size-1) || (i==size+5)){
                    this.map[i][j] = new Cell(0,i,j,0);
                }else if(i<size && j<size){
                    this.map[i][j]=new Cell(1,i,j,1);
                }
            }
        }

        this.map[size+1][2] = new Cell(0,size+1,2,0);
        this.map[size+3][2] = new Cell(1,size+3,2,2);
        this.map[size+1][4] = new Cell(1,size+1,4,1);
        this.hero = new Hero(this.map[size+1][4]);



        frame = new JFrame();
        this.frame.setSize(((size+1) * View.SCALE), ((size+2) * View.SCALE)) ;
        frame.setSize(1000,550);
        frame.setTitle("PacMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = frame.getContentPane();
        panel = new Panel(this);
        cp.add(panel);
        frame.setVisible(true);
        this.start();

    }

    public JFrame getFrame(){
        return this.frame;
    }

    public Cell[][] getMap(){
        return this.map;
    }

    public int getSize(){
        return this.size;
    }

    public Hero getHero() {
        return hero;
    }

    public void run(){
        while(true){
            this.frame.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}

class Panel extends JPanel{

    private DND dnd;

    public Panel(DND dnd){
        this.dnd = dnd;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for(int i=0; i<this.dnd.getSize()+6;i++){
            for(int j=0;j<this.dnd.getSize();j++){
                if(this.dnd.getMap()[i][j] != null){
                    this.dnd.getMap()[i][j].paintCell(g2, i*View.SCALE, j*View.SCALE, View.SCALE);
                    //System.out.println(i+" "+j);
                }
            }
        }

        try {
            this.dnd.getHero().paintHero(g2, View.SCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
