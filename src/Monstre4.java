import java.awt.Color;

public class Monstre4 extends Monstre{

	public Monstre4(Cell cell) {
		super(cell, Color.ORANGE);
	}

	public Monstre4(Cell cell, Model model, int repop, int speed) {
		super(cell, model, repop,Color.ORANGE, speed);
		// TODO Auto-generated constructor stub
	}
	public Direction[] movePossible() {
		int i = 0;
		Direction[] tmp = new Direction[4];
		if(this.cell.geti()-this.model.getHero().getCell().geti()>0 && this.model.getMap()[this.cell.geti()+Direction.WEST.dI()][this.cell.getj()+Direction.WEST.dJ()].passable()){
			tmp[i] = Direction.WEST;
			i++;
		}
		if(this.cell.geti()-this.model.getHero().getCell().geti()<0 && this.model.getMap()[this.cell.geti()+Direction.EAST.dI()][this.cell.getj()+Direction.EAST.dJ()].passable()){
			tmp[i] = Direction.EAST;
			i++;
		}
		if(this.cell.getj()-this.model.getHero().getCell().getj()>0 && this.model.getMap()[this.cell.geti()+Direction.NORTH.dI()][this.cell.getj()+Direction.NORTH.dJ()].passable()){
			tmp[i] = Direction.NORTH;
			i++;
		}
		if(this.cell.getj()-this.model.getHero().getCell().getj()<0 && this.model.getMap()[this.cell.geti()+Direction.SOUTH.dI()][this.cell.getj()+Direction.SOUTH.dJ()].passable()){
			tmp[i] = Direction.SOUTH;
			i++;
		}
		if(i==0)return null;
		Direction[] res = new Direction[i];
		for(int j=0; j<i;j++){
			res[j]=tmp[j];
		}
		return res;
	}

}
