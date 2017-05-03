
public class Model {

	private Cell[][] map;
	private int size;
	private Hero hero;
	private Monstre[] Lmonstre;

	public Model() {
		new Model(10);
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
		this.Lmonstre = new Monstre[1];

        this.Lmonstre[0] = new Monstre(this.map[this.size-2][this.size-2],this);
        this.Lmonstre[0].start();

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



		
//		this.map[2][1] = new Cell(0,2,1);

//		this.map[2][2] = new Cell(0,2,2);
//		this.map[2][3] = new Cell(0,2,3);
//		this.map[2][4] = new Cell(0,2,4);
//		this.map[2][5] = new Cell(0,2,5);
//		
//		this.map[4][8] = new Cell(0,4,8);
//		this.map[4][7] = new Cell(0,4,7);
//		this.map[4][6] = new Cell(0,4,6);
//		this.map[4][5] = new Cell(0,4,5);
//		this.map[4][4] = new Cell(0,4,4);
//		this.map[4][3] = new Cell(0,4,3);
		
	}
	
	public Monstre[] getMonstre(){
		return this.Lmonstre;
	}
	
	public void heroMove(Direction dir){
		this.hero.move(dir, this.map);
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
