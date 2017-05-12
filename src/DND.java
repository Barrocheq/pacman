import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Classe Drag and Drop pour la création de niveau
 */
class DND extends Thread{

    private Panel panel;
    private JFrame frame;
    private Cell[][] map;
    private Cell fakeCell;
    private int size;
    private Hero hero;
    private Hero fakeHero;
    private Monstre fakeMonster;
    private Monstre2 fakeMonster2;
    private Monstre3 fakeMonster3;
    private Monstre4 fakeMonster4;
    private ArrayList<Monstre> Lmonstre;
    private String fileName;


    /**
     * Constructeur
     * @param size taille du niveau
     */
    public DND(int size, Moteur moteur){

        Lmonstre = new ArrayList<>();

        this.size = size;

        // Création de la map de base
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


        // Placement des "objets"
        this.map[size+1][2] = new Cell(0,size+1,2,0);
        this.fakeCell = new Cell(0, size+1,2, 0);


        this.map[size+3][2] = new Cell(1,size+3,2,2);
        this.map[size+1][4] = new Cell(1,size+1,4,1);

        this.map[size+3][4] = new Cell(1, size+3, 4, 0);
        this.fakeMonster = new Monstre(this.map[size+3][4], Color.GREEN);

        this.map[size+1][6] = new Cell(1, size+1, 6, 0);
        this.fakeMonster2 = new Monstre2(this.map[size+1][6]);

        this.map[size+3][6] = new Cell(1, size+3, 6, 0);
        this.fakeMonster3 = new Monstre3(this.map[size+3][6]);

        this.map[size+1][8] = new Cell(1, size+1, 8, 0);
        this.fakeMonster4 = new Monstre4(this.map[size+1][8]);

        this.hero = new Hero(this.map[size+1][4]);
        this.fakeHero = new Hero(this.map[size+1][4]);



        // Création de la frame
        frame = new JFrame();
        setSizeFrame(size, size+6);
        frame.setTitle("PacMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cp = frame.getContentPane();

        panel = new Panel(this);
        panel.setSize(size, size);
        cp.add(panel);

        JButton valider = new JButton("Valider");
        valider.setLocation(size, size);

        panel.add(valider);

        JTextField eat = new JTextField("Time to eat (millisec)");
        panel.add(eat);

        JTextField respawn = new JTextField("Time to respawn (millisec)");
        panel.add(respawn);

        JTextField refresh = new JTextField("Refresh (millisec)");
        panel.add(refresh);

        JTextField name = new JTextField("tmp");
        panel.add(name);


        // Action Listener
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if(hero.getCell().getj() != 4 || hero.getCell().geti() != size + 1) {
                        MapToFile(name.getText()+".txt", refresh.getText(), respawn.getText(), eat.getText());
                        frame.setVisible(false);
                    }
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

    // GETTEURS & SETTEUR
    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Panel getPanel() {
        return panel;
    }


    public void setSizeFrame(int sizeH, int sizeL) {

        String os = System.getProperty("os.name").toLowerCase();


        if (os.contains("win"))
            this.frame.setSize(((sizeL + 1) * View.SCALE), ((sizeH + 2) * View.SCALE));
        else if (os.contains("nux") || os.contains("nix"))
            this.frame.setSize(((sizeL + 1) * View.SCALE), ((sizeH + 2) * View.SCALE));
        else
            this.frame.setSize((sizeL) * View.SCALE, (sizeH) * View.SCALE + 22);

    }

    public String getNameFile() {
        return this.fileName;
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
    public Monstre2 getFakeMonster2() {
        return fakeMonster2;
    }
    public Monstre3 getFakeMonster3() {
        return fakeMonster3;
    }
    public Monstre4 getFakeMonster4() {
        return fakeMonster4;
    }

    public Cell getFakeCell() {
        return fakeCell;
    }


    /**
     * Sauvegarde de la map dans un fichier
     * @param fileName nom du fichier de sauvegarde
     * @param refresh temps de refresh de la partie
     * @param respawn temps de respawn des monstre
     * @param timeToEat temps bonbonMagique
     * @throws IOException
     */
    public void MapToFile(String fileName, String refresh, String respawn ,String timeToEat) throws IOException {

        try {
            Integer.parseInt(refresh);
        } catch (NumberFormatException e) {
            System.out.println("Erreur refresh n'est pas un entier");
            refresh = "10";
        }

        try {
            Integer.parseInt(respawn);
        } catch (NumberFormatException e) {
            System.out.println("Erreur respawn n'est pas un entier");
            respawn = "5000";
        }

        try {
            Integer.parseInt(timeToEat);
        } catch (NumberFormatException e) {
            System.out.println("Erreur timeToEat n'est pas un entier");
            timeToEat = "8000";
        }



        this.fileName = fileName;
        BufferedWriter file = new BufferedWriter(new FileWriter(fileName));
        String line;
        line = this.size + "," + this.size + "," + refresh + "," + respawn + "," + timeToEat + ","
                + this.Lmonstre.size();
        file.write(line);
        file.newLine();
        line = "";
        for (int i = 0; i < this.size; i++) {
            boucleline :for (int j = 0; j < this.size; j++) {
                for(Monstre m :this.Lmonstre){
                    if(m.getCell().geti() == j && m.getCell().getj() == i){
                        if(m instanceof Monstre4)
                            line+="3";
                        else if(m instanceof Monstre3)
                            line+="2";
                        else if(m instanceof  Monstre2)
                            line+="1";
                        else
                            line+="0";

                        continue boucleline;
                    }
                }
                if (this.hero.getCell().geti() == j && this.hero.getCell().getj() == i) {
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


    /**
     * Lancement du Thread
     */
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

/**
 * Creation du Panel
 */
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
                }
            }
        }

        this.dnd.getFakeCell().paintCell(g2, this.dnd.getFakeCell().geti(), this.dnd.getFakeCell().getj(), View.SCALE);


        // Dessins des faux objets
        try {
            this.dnd.getHero().paintHero(g2, View.SCALE);
            this.dnd.getFakeHero().paintHero(g2, View.SCALE);
            this.dnd.getFakeMonster().paintMonstre(g2, View.SCALE);
            this.dnd.getFakeMonster2().paintMonstre(g2, View.SCALE);
            this.dnd.getFakeMonster3().paintMonstre(g2, View.SCALE);
            this.dnd.getFakeMonster4().paintMonstre(g2, View.SCALE);
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
