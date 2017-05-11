import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	private View view;
	private BonbonMagique mangeBonbon;
	private int Score;
	private String nom;
	private Cercle Cercle;
	public float loading;

	public int getLoading() {
		return (int)loading;
	}

	public void setLoading(int loading) {
		this.loading = loading;
	}



	public Model() {
		this.nom = "default";
	}

	public void init(String f, View v) throws IOException {

		this.state = false;

		this.view = v;

		this.setFromFile(f);

	}

	public void setMap(Cell[][] map) {
		this.sizeL = map.length;
		this.sizeH = map.length;
		timeToEat = 5000;
		this.map = map;

		this.hero = new Hero(this.map[1][1], this);
		this.map[1][1] = new Cell(1, 1, 1, 0);

		//System.out.println("GoM");

		this.generateMonster();
	}

	public void init(int size) {
		this.sizeL = size;
		this.sizeH = size;
		timeToEat = 5000;

		RandomLvl lvl = new RandomLvl();
		lvl.init(size);
		// this.map = new Cell[size][size];
		this.map = lvl.getMap();

		this.hero = new Hero(this.map[1][1], this);
		this.map[1][1] = new Cell(1, 1, 1, 0);

		//System.out.println("Go");

		this.generateMonster();
	}

	public void generateMonster() {
		int nbMonster = this.sizeH / 4;
		int i = 0;
		int randI;
		int randJ;
		this.respawnMonster = 1000;

		this.Lmonstre = new Monstre[nbMonster];

		while (nbMonster > 0) {
			randI = (int) (Math.random() * ((this.sizeH - 1) - 1)) + 1;
			randJ = (int) (Math.random() * ((this.sizeH - 1) - 1)) + 1;
			// randI = (int)(Math.random() * (this.sizeH-3)) + 3;
			// randJ = (int)(Math.random() * (this.sizeH-3)) + 3;

			if (!(this.map[randI][randJ].passable())) {
				this.Lmonstre[i] = new Monstre(this.map[randI][randJ], this, this.respawnMonster, 10);
				nbMonster--;
				i++;
			}
		}
	}


	public void setFromFile(String fileName) throws IOException {


		String line;
		int i = 0, j = 0, indexMonster = -1;
		BufferedReader file = new BufferedReader(new FileReader(fileName));
		while ((line = file.readLine()) != null) {
			if (i == 0) {
				String[] config = line.split(",");
				this.sizeL = Integer.parseInt(config[0]);
				this.sizeH = Integer.parseInt(config[1]);
				this.speed = Integer.parseInt(config[2]);
				this.respawnMonster = Integer.parseInt(config[3]);
				this.timeToEat = Integer.parseInt(config[4]);
				this.numberOfMonster = Integer.parseInt(config[5]);

				this.Lmonstre = new Monstre[numberOfMonster];
				this.map = new Cell[this.sizeL][this.sizeH];

				for (int k = 0; k < sizeL; k++)
					for (int n = 0; n < sizeH; n++)
						this.map[k][n] = new Cell(0, k, n, 0);

			} else {
				String[] symbol = line.split("");

				for (String s : symbol) {
					if (s.equals("#")) {
						this.map[j][i - 1] = new Cell(0, j, i - 1, 0);
					} else if (s.equals(".")) {
						this.map[j][i - 1] = new Cell(1, j, i - 1, 1);
					} else if (s.equals("o")) {
						this.map[j][i - 1] = new Cell(1, j, i - 1, 2);
					} else if (s.equals("C")) {
						this.map[j][i - 1] = new Cell(1, j, i - 1, 0);
						this.hero = new Hero(this.map[j][i - 1], this);
					} else if (Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 9) {
						indexMonster++;
						if (indexMonster == numberOfMonster) {
							System.err.println("Erreur Monstre : config et description");
							System.exit(0);
						}

						this.map[j][i - 1] = new Cell(0, j, i - 1, 0);

						if(Integer.parseInt(s) == 0)
							this.Lmonstre[indexMonster] = new Monstre(this.map[j][i - 1], this, this.respawnMonster, this.speed);
						else if (Integer.parseInt(s) == 1)
							this.Lmonstre[indexMonster] = new Monstre2(this.map[j][i - 1], this, this.respawnMonster, this.speed);
						else if (Integer.parseInt(s) == 2)
							this.Lmonstre[indexMonster] = new Monstre3(this.map[j][i - 1], this, this.respawnMonster, this.speed);
						else if (Integer.parseInt(s) == 3)
							this.Lmonstre[indexMonster] = new Monstre4(this.map[j][i - 1], this, this.respawnMonster, this.speed);
						else
							this.Lmonstre[indexMonster] = new Monstre(this.map[j][i - 1], this, this.respawnMonster, this.speed);


					} else
						System.err.println("Symbol non reconnu lors de l'initialisation du tableau : " + s);


					loading = ((float)((i-1)*sizeH + j) / (float)(sizeH * sizeL)) * 100;

					//System.out.println(    (     (float)(     (i*sizeL) + j    )/   (float)(  (i*(sizeL+1)) -1 ))  * 100);


					j++;

					this.view.loading(this);

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

//						System.out.println(loading);

				}
			}

			j = 0;
			i++;
		}
		file.close();
	}

	public Monstre[] getMonstre() {
		return this.Lmonstre;
	}

	public Hero getHero() {
		return this.hero;
	}

	public Cell[][] getMap() {
		return this.map;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getSize() {
		return this.size;
	}

	public int getSizeH() {
		return this.sizeH;
	}

	public int getSizeL() {
		return this.sizeL;
	}

	public void startHero() {
		this.hero.start();
	}

	public void startMonstre() {
		System.out.println(this.Lmonstre.length);

		for (Monstre m : this.Lmonstre)
			m.start();
	}

	public void stop() {
		this.hero.stopHero();

		if (this.mangeBonbon != null)
			this.mangeBonbon.stopBonbon();

		for (Monstre m : this.Lmonstre)
			m.stopMonstre();
		this.Cercle = null;
	}

	public int nbBonbon() {
		int res = 0;
		for (Cell[] c : this.map) {
			for (Cell cell : c) {
				if (cell.getBonbon() > 0) {
					res++;
				}
			}
		}
		return res;
	}

	public boolean getState() {
		return this.state;
	}

	public void finEat() {
		this.mangeBonbon = null;
	}

	public void mangeBonbonRouge() {
		if (this.mangeBonbon != null) {
			this.mangeBonbon.addTime(this.timeToEat);
		} else {
			this.mangeBonbon = new BonbonMagique(this.timeToEat, this);
		}
		this.Cercle = new Cercle(this);
	}

	public BonbonMagique getMangeBonbon() {
		return mangeBonbon;
	}

	public void incScore(int i) {
		this.Score += i;

	}
	
	public int getScore(){
		return this.Score;
	}

	public void saveScore() throws IOException {
		
		BufferedWriter file = new BufferedWriter(new FileWriter("score.txt",true	));
		file.append(this.nom+":"+Integer.toString(this.Score));
		file.newLine();
		file.close();
		this.Score = 0;
		
	}
	
	public void setCercle(Cercle cercle){
		this.Cercle = cercle;
	}
	
	public Cercle getCercle(){
		return this.Cercle;
	}
}
