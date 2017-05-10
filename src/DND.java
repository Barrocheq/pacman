import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class DND extends Thread{

    private Panel panel;
    private JFrame frame;
    private Cell[][] map;
    private int size;
    private Hero hero;
    private Hero fakeHero;
    private Monstre fakeMonster;
    private ArrayList<Monstre> Lmonstre;
    private String fileName;

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Panel getPanel() {
        return panel;
    }

    public DND(int size, Moteur moteur){

        Lmonstre = new ArrayList<>();

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

        this.map[size+3][4] = new Cell(1, size+3, 4, 0);
        this.fakeMonster = new Monstre(this.map[size+3][4]);
        //Lmonstre.add(new Monstre(this.map[size+3][4]));


        this.hero = new Hero(this.map[size+1][4]);
        this.fakeHero = new Hero(this.map[size+1][4]);

        frame = new JFrame();
        this.frame.setSize(((size+1) * View.SCALE), ((size+2) * View.SCALE)) ;
        frame.setSize(1000,550);
        frame.setTitle("PacMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = frame.getContentPane();
        //cp.setLayout(null);

        panel = new Panel(this);
        panel.setSize(size, size);
        //panel.setLayout(null);
        cp.add(panel);

        JButton but = new JButton("Valider");
        but.setLocation(size, size);
        panel.add(but);


        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MapToFile("tmp.txt");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


                try {
                    synchronized (moteur) {
                        moteur.notify();
                    }
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                }

            }
        });


        frame.setVisible(true);
        this.start();

    }

    public String getNameFile() {
        return this.fileName;
    }

    public void MapToFile(String fileName) throws IOException {

        this.fileName = fileName;
        BufferedWriter file = new BufferedWriter(new FileWriter(fileName));
        String line;
        line = this.size + "," + this.size + "," + Integer.toString(100) + "," + Integer.toString(100) + "," + Integer.toString(1000) + ","
                + this.Lmonstre.size();
        file.write(line);
        file.newLine();
        line = "";
        for (int i = 0; i < this.size; i++) {
            boucleline :for (int j = 0; j < this.size; j++) {
                for(Monstre m :this.Lmonstre){
                    if(m.getCell().geti() == i && m.getCell().getj() == j){
                        line+="0";
                        continue boucleline;
                    }
                }
                if (this.hero.getCell().geti() == i && this.hero.getCell().getj() == j) {
                    line += "C";
                } else if (this.map[j][i].getStats() == 0) {
                    line += "#";
                } else if (this.map[j][i].getStats() == 1) {
                    if (this.map[j][i].getBonbon() == 1) {
                        line += ".";
                    } else {
                        line += "o";
                    }
                }


            }
            file.write(line);
            file.newLine();
            line="";
        }
        file.close();
    }

    public JFrame getFrame(){
        return this.frame;
    }

    public Hero getFakeHero() {
        return fakeHero;
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

    public void setLmonstre(Monstre monstre) {
        this.Lmonstre.add(monstre);
    }

    public ArrayList<Monstre> getLmonstre() {
        return Lmonstre;
    }

    public Monstre getFakeMonster() {
        return fakeMonster;
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
            this.dnd.getFakeHero().paintHero(g2, View.SCALE);
            this.dnd.getFakeMonster().paintMonstre(g2, View.SCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for(Monstre m : this.dnd.getLmonstre()){
            try {
                m.paintMonstre(g2, View.SCALE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
