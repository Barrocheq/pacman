
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
		this.Lmonstre[0] = new Monstre(this.map[this.size-2][this.size-2]);
		
		this.map[2][1] = new Cell(0,2,1);
		this.map[2][2] = new Cell(0,2,2);
		this.map[2][3] = new Cell(0,2,3);
		this.map[2][4] = new Cell(0,2,4);
		this.map[2][5] = new Cell(0,2,5);
		
		this.map[4][8] = new Cell(0,4,8);
		this.map[4][7] = new Cell(0,4,7);
		this.map[4][6] = new Cell(0,4,6);
		this.map[4][5] = new Cell(0,4,5);
		this.map[4][4] = new Cell(0,4,4);
		this.map[4][3] = new Cell(0,4,3);
		
	}
	
	public Monstre[] getMonstre(){
		return this.Lmonstre;
	}
	
	public void heroMove(Direction dir){
		this.hero.move(dir, this.map);
		for(Monstre m : this.Lmonstre){
			m.move(Direction.random(),this.map);
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
