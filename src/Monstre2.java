import java.awt.Color;

public class Monstre2 extends Monstre {

	public Monstre2(Cell cell, Model model, int repop) {
		super(cell, model, repop,Color.RED);
		// TODO Auto-generated constructor stub
	}

	public Monstre2(Cell cell) {
		super(cell, Color.RED);
	}

	public Direction Dir(){
		Direction dir = getHeroLine();
		if(dir != null){
			return dir;
		}else{
			Direction[] dirs = movePossible();
			return dirs[(int) (dirs.length*Math.random())];
		}
		
	}

	
	public Direction getHeroLine(){
		
		int tempi;
		int tempj;
		
		
		for(Direction dir: Direction.values()){
			if(this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()].passable()){
				
				tempi = this.cell.geti();
				tempj = this.cell.getj();
				
				while(true){
					tempi = tempi+dir.dI();
					tempj = tempj+dir.dJ();
					if(!this.model.getMap()[tempi][tempj].passable()){
						break;
					}
					if(this.model.getMap()[tempi][tempj].equals(this.model.getHero().getCell())){
						return dir;
					}
				}
			}
		}
		return null;
	}
}
