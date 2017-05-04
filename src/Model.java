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

	public Model() {
		new Model(10);
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
                        if(s == "#")
                            this.map[i-1][j] = new Cell(0, i-1, j, 0);
                        else if(s == ".")
                            this.map[i-1][j] = new Cell(1, i-1, j, 1);
                        else if(s == "o")
                            this.map[i-1][j] = new Cell(1, i-1, j, 2);
                        else if (Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 9) {
                            indexMonster++;
                            if(indexMonster == numberOfMonster) {
                                System.err.println("Erreur Monstre : config et description");
                                System.exit(0);
                            }

                            this.map[i - 1][j] = new Cell(1, i - 1, j, 0);
                            this.Lmonstre[indexMonster] = new Monstre(this.map[i-1][j],this);
                        }
                        else if(s == "C") {
                            this.map[i - 1][j] = new Cell(1, i - 1, j, 0);
                            this.hero = new Hero(this.map[i-i][j]);
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
		this.size = size;
		this.map = new Cell[this.size][this.size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == 0 || j == 0 || i == size-1 || j == size-1) {
					this.map[i][j] = new Cell(0,i,j);
				} else {
					this.map[i][j] = new Cell(1,i,j);
				}
				
			}
		}
		this.hero = new Hero(this.map[1][1]);
		this.Lmonstre = new Monstre[4];

        this.Lmonstre[0] = new Monstre(this.map[this.size-2][this.size-2],this);
        this.Lmonstre[1] = new Monstre(this.map[this.size-2][this.size-2],this);
        this.Lmonstre[2] = new Monstre(this.map[this.size-2][this.size-2],this);
        this.Lmonstre[3] = new Monstre(this.map[this.size-2][this.size-2],this);
        

        this.map[10][1] = new Cell(0,10,1);
        this.map[10][2] = new Cell(0,10,2);
        this.map[10][3] = new Cell(0,10,3);
        this.map[10][19] = new Cell(0,10,19);
        this.map[10][18] = new Cell(0,10,18);
        this.map[10][17] = new Cell(0,10,17);

        this.map[3][2] = new Cell(0,3,2);
        this.map[2][2] = new Cell(0,2,2);
        this.map[3][3] = new Cell(0,3,3);
        this.map[2][3] = new Cell(0,2,3);

        this.map[18][17] = new Cell(0,18,17);
        this.map[18][18] = new Cell(0,18,18);
        this.map[17][17] = new Cell(0,17,17);
        this.map[17][18] = new Cell(0,17,18);

        this.map[3][17] = new Cell(0,3,17);
        this.map[2][17] = new Cell(0,2,17);
        this.map[3][18] = new Cell(0,3,18);
        this.map[2][18] = new Cell(0,2,18);

        this.map[18][2] = new Cell(0,18,2);
        this.map[17][2] = new Cell(0,17,2);
        this.map[18][3] = new Cell(0,18,3);
        this.map[17][3] = new Cell(0,17,3);


        for(int i = 5; i <= 15; i++) {
            for(int j = 2; j < 19; j++) {
                if(i == 8 || i  == 11 || j == 10) {
                    continue;
                }
                this.map[j][i] = new Cell(0,j,i);
            }
        }


        this.map[5][2] = new Cell(0,5,2);
        this.map[6][2] = new Cell(0,6,2);
        this.map[7][2] = new Cell(0,7,2);
        this.map[8][2] = new Cell(0,8,2);
        this.map[5][3] = new Cell(0,5,3);
        this.map[6][3] = new Cell(0,6,3);
        this.map[7][3] = new Cell(0,7,3);
        this.map[8][3] = new Cell(0,8,3);

        this.map[12][2] = new Cell(0,12, 2);
        this.map[13][2] = new Cell(0,13, 2);
        this.map[14][2] = new Cell(0,14, 2);
        this.map[15][2] = new Cell(0,15, 2);
        this.map[12][3] = new Cell(0,12, 3);
        this.map[13][3] = new Cell(0,13, 3);
        this.map[14][3] = new Cell(0,14, 3);
        this.map[15][3] = new Cell(0,15, 3);

        this.map[5][17] = new Cell(0,5, 17);
        this.map[6][17] = new Cell(0,6, 17);
        this.map[7][17] = new Cell(0,7, 17);
        this.map[8][17] = new Cell(0,8, 17);
        this.map[5][18] = new Cell(0,5, 18);
        this.map[6][18] = new Cell(0,6, 18);
        this.map[7][18] = new Cell(0,7, 18);
        this.map[8][18] = new Cell(0,8, 18);

        this.map[12][17] = new Cell(0,12, 17);
        this.map[13][17] = new Cell(0,13, 17);
        this.map[14][17] = new Cell(0,14, 17);
        this.map[15][17] = new Cell(0,15, 17);
        this.map[12][18] = new Cell(0,12, 18);
        this.map[13][18] = new Cell(0,13, 18);
        this.map[14][18] = new Cell(0,14, 18);
        this.map[15][18] = new Cell(0,15, 18);



		
	}
	
	public int nbBonbon(){
		int res=0;
		for(Cell[] c : this.map){
			for(Cell cell : c){
				if(cell.getBonbon()){
					res++;
				}
			}
		}
		return res;
	}
	
	public Monstre[] getMonstre(){
		return this.Lmonstre;
	}
	
	public void heroMove(Direction dir){
		this.hero.move(dir, this.map);
		if(this.nbBonbon()==0){
			System.out.println("GG");
			System.exit(0);
		}
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
