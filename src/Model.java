import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Model {

	private Cell[][] map;
	private int size; // pour les plateaux carré
	private int sizeH;
	private int sizeL;
	private int speed;
	private int respawnMonster;
	private int timeToEat;
	private int numberOfMonster;
	private Hero hero;
	private Monstre[] Lmonstre;
	private boolean state;

	public Model() {
        
    }


	public void init(String f) {

	    this.state = false;

        try {
            this.setFromFile(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(int size) {
        this.sizeL = size;
        this.sizeH = size;
        
	    RandomLvl lvl = new RandomLvl();
	    lvl.init(size);
//        this.map = new Cell[size][size];
	    this.map = lvl.getMap();

        this.hero = new Hero(this.map[1][1], this);
        this.map[1][1] = new Cell(1, 1, 1, 0);



        this.generateMonster();
    }

    public void generateMonster() {
	    int nbMonster = this.sizeH/4;
	    int i = 0;
        int randI;
        int randJ;
        this.respawnMonster = 1000;

        this.Lmonstre = new Monstre[nbMonster];

        System.out.println("Nombre monstre : " + nbMonster);

        while (nbMonster > 0) {
            randI = (int)(Math.random() * ((this.sizeH-1)-1)) + 1;
            randJ = (int)(Math.random() * ((this.sizeH-1)-1)) + 1;
            //randI = (int)(Math.random() * (this.sizeH-3)) + 3;
            //randJ = (int)(Math.random() * (this.sizeH-3)) + 3;

            if(!(this.map[randI][randJ].passable())) {

                System.out.println("i : " + randI + ", j : " + randJ);

                this.Lmonstre[i] = new Monstre(this.map[randI][randJ],this, this.respawnMonster, 10);
                nbMonster--;
                i++;
            }
        }
    }

    public void setFromFile(String fileName) throws IOException {

        //System.out.println(fileName);

        try {
            String line;
            int i = 0, j = 0, indexMonster = -1;
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            while ((line = file.readLine()) != null) {
                if(i == 0) {
                    String[] config = line.split(",");
                    this.sizeL              = Integer.parseInt(config[0]);
                    this.sizeH              = Integer.parseInt(config[1]);
                    this.speed              = Integer.parseInt(config[2]);
                    this.respawnMonster     = Integer.parseInt(config[3]);
                    this.timeToEat          = Integer.parseInt(config[4]);
                    this.numberOfMonster    = Integer.parseInt(config[5]);


                    this.Lmonstre = new Monstre[numberOfMonster];
                    this.map = new Cell[this.sizeL][this.sizeH];



                    for (int k = 0; k < sizeL; k++)
                        for (int n = 0; n < sizeH; n++)
                                this.map[k][n] = new Cell(0,k,n,0);


                } else {
                    String[] symbol = line.split("");

                    for(String s : symbol) {
                        if(s.equals("#")) {
                            this.map[j][i-1] = new Cell(0,j, i-1, 0);
                        }
                        else if(s.equals(".")) {
                            this.map[j][i-1] = new Cell(1,j, i-1, 1);
                        }
                        else if(s.equals("o")) {
                            this.map[j][i-1]= new Cell(1,j, i-1, 2);
                        }
                        else if(s.equals("C")) {
                            this.map[j][i-1] = new Cell(1,j, i-1, 0);
                            this.hero = new Hero(this.map[j][i-1], this);
                        }
                        else if (Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 9) {
                            indexMonster++;
                            if(indexMonster == numberOfMonster) {
                                System.err.println("Erreur Monstre : config et description");
                                System.exit(0);
                            }


                            int r = (int)(Math.random() * 255);
                            int g = (int)(Math.random() * 255);
                            int b = (int)(Math.random() * 255);

                            this.map[j][i - 1]= new Cell(0, j, i - 1, 0);
                            this.Lmonstre[indexMonster] = new Monstre3(this.map[j][i - 1],this, this.respawnMonster,this.speed);

                        }
                        else
                            System.err.println("Symbol non reconnu lors de l'initialisation du tableau : " + s);

                        j++;

                    }
                }


                j = 0;
                i++;
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public Monstre[] getMonstre(){
        return this.Lmonstre;
    }
    public Hero getHero(){
        return this.hero;
    }
    public Cell[][] getMap() {
        return this.map;
    }
    public void setState(boolean state) { this.state = state; }
    public int getSize() {
        return this.size;
    }
    public int getSizeH() { return this.sizeH ;}
    public int getSizeL() { return this.sizeL ;}
    public void startHero() { this.hero.start(); }
    public void startMonstre() {
	    for (Monstre m : this.Lmonstre)
	        m.start();
	}

	public void stop() {
	    this.hero.stopHero();

        for (Monstre m : this.Lmonstre)
            m.stopMonstre();
    }

	
	public int nbBonbon(){
		int res = 0;
		for(Cell[] c : this.map){
			for(Cell cell : c){
				if(cell.getBonbon()>0){
					res++;
				}
			}
		}
		return res;
	}
	
	
	public boolean getState(){
		return this.state;
	}



    public void mangeBonbonRouge() {
        new BonbonMagique(this.timeToEat, this);
	}
	
	public void finTime(){
		this.state = false;
	}

}
