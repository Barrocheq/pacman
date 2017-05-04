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
	private boolean State;

	public Model() {
        this.State = false;

        try {
            this.setFromFile("lvl.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


	}

    public void setFromFile(String fileName) throws IOException {
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

                    this.map = new Cell[this.sizeL][this.sizeH];
                    this.Lmonstre = new Monstre[numberOfMonster];

                } else {
                    String[] symbol = line.split("");

                    for(String s : symbol) {
                        if(s.equals("#"))
                            this.map[i-1][j] = new Cell(0, i-1, j, 0);
                        else if(s.equals("."))
                            this.map[i-1][j] = new Cell(1, i-1, j, 1);
                        else if(s.equals("o"))
                            this.map[i-1][j] = new Cell(1, i-1, j, 2);
                        else if(s.equals("C")) {
                            this.map[i - 1][j] = new Cell(1, i - 1, j, 0);
                            this.hero = new Hero(this.map[i-i][j], this);
                        }
                        else if (Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 9) {
                            indexMonster++;
                            if(indexMonster == numberOfMonster) {
                                System.err.println("Erreur Monstre : config et description");
                                System.exit(0);
                            }

                            this.map[i - 1][j] = new Cell(1, i - 1, j, 0);
                            this.Lmonstre[indexMonster] = new Monstre(this.map[i-1][j],this, this.respawnMonster);
                        }
                        else
                            System.err.println("Symbol non reconnu lors de l'initialisation du tableau : " + s);

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


	public Model(int size) {
		this.timeToEat = 5000;
		this.State = false;
		this.size = size;
		this.map = new Cell[this.size][this.size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == 0 || j == 0 || i == size-1 || j == size-1) {
					this.map[i][j] = new Cell(0,i,j,0);
				} else {
					this.map[i][j] = new Cell(1,i,j,1);
				}

			}
		}
		this.hero = new Hero(this.map[1][1],this);
		this.Lmonstre = new Monstre[4];

        this.Lmonstre[0] = new Monstre(this.map[this.size-2][this.size-2],this, 2000);
        this.Lmonstre[1] = new Monstre(this.map[this.size-2][this.size-2],this, 2000);
        this.Lmonstre[2] = new Monstre(this.map[this.size-2][this.size-2],this, 2000);
        this.Lmonstre[3] = new Monstre(this.map[this.size-2][this.size-2],this, 2000);


        this.map[10][1] = new Cell(0,10,1,0);
        this.map[10][2] = new Cell(0,10,2,0);
        this.map[10][3] = new Cell(0,10,3,0);
        this.map[10][19] = new Cell(0,10,19,0);
        this.map[10][18] = new Cell(0,10,18,0);
        this.map[10][17] = new Cell(0,10,17,0);
        this.map[11][17] = new Cell(1,11,17,2);

        this.map[3][2] = new Cell(0,3,2,0);
        this.map[2][2] = new Cell(0,2,2,0);
        this.map[3][3] = new Cell(0,3,3,0);
        this.map[2][3] = new Cell(0,2,3,0);

        this.map[18][17] = new Cell(0,18,17,0);
        this.map[18][18] = new Cell(0,18,18,0);
        this.map[17][17] = new Cell(0,17,17,0);
        this.map[17][18] = new Cell(0,17,18,0);

        this.map[3][17] = new Cell(0,3,17,0);
        this.map[2][17] = new Cell(0,2,17,0);
        this.map[3][18] = new Cell(0,3,18,0);
        this.map[2][18] = new Cell(0,2,18,0);

        this.map[18][2] = new Cell(0,18,2,0);
        this.map[17][2] = new Cell(0,17,2,0);
        this.map[18][3] = new Cell(0,18,3,0);
        this.map[17][3] = new Cell(0,17,3,0);


        for(int i = 5; i <= 15; i++) {
            for(int j = 2; j < 19; j++) {
                if(i == 8 || i  == 11 || j == 10) {
                    continue;
                }
                this.map[j][i] = new Cell(0,j,i,0);
            }
        }


        this.map[5][2] = new Cell(0,5,2,0);
        this.map[6][2] = new Cell(0,6,2,0);
        this.map[7][2] = new Cell(0,7,2,0);
        this.map[8][2] = new Cell(0,8,2,0);
        this.map[5][3] = new Cell(0,5,3,0);
        this.map[6][3] = new Cell(0,6,3,0);
        this.map[7][3] = new Cell(0,7,3,0);
        this.map[8][3] = new Cell(0,8,3,0);

        this.map[12][2] = new Cell(0,12, 2,0);
        this.map[13][2] = new Cell(0,13, 2,0);
        this.map[14][2] = new Cell(0,14, 2,0);
        this.map[15][2] = new Cell(0,15, 2,0);
        this.map[12][3] = new Cell(0,12, 3,0);
        this.map[13][3] = new Cell(0,13, 3,0);
        this.map[14][3] = new Cell(0,14, 3,0);
        this.map[15][3] = new Cell(0,15, 3,0);

        this.map[5][17] = new Cell(0,5, 17,0);
        this.map[6][17] = new Cell(0,6, 17,0);
        this.map[7][17] = new Cell(0,7, 17,0);
        this.map[8][17] = new Cell(0,8, 17,0);
        this.map[5][18] = new Cell(0,5, 18,0);
        this.map[6][18] = new Cell(0,6, 18,0);
        this.map[7][18] = new Cell(0,7, 18,0);
        this.map[8][18] = new Cell(0,8, 18,0);

        this.map[12][17] = new Cell(0,12, 17,0);
        this.map[13][17] = new Cell(0,13, 17,0);
        this.map[14][17] = new Cell(0,14, 17,0);
        this.map[15][17] = new Cell(0,15, 17,0);
        this.map[12][18] = new Cell(0,12, 18,0);
        this.map[13][18] = new Cell(0,13, 18,0);
        this.map[14][18] = new Cell(0,14, 18,0);
        this.map[15][18] = new Cell(0,15, 18,0);

        System.err.println("Constructeur model ");

		
	}
	
	public int nbBonbon(){
		int res=0;
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
		return this.State;
	}
	
	public void mangeBonbonRouge() {
		this.State = true;
		new bonbonrouge(timeToEat,this);
	}
	
	public void finTime(){
		System.out.println("Fin du temps pour manger");
		this.State =false;
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

	public int getSize() {
		return this.size;
	}

}


class bonbonrouge extends Thread{
	
	private int time;
	private Model model;
	
	public bonbonrouge(int time,Model model){
		this.time = time;
		this.model = model;
		this.start();
	}
	
	public void run(){
		try {
			Thread.sleep(this.time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.model.finTime();
	}
}
