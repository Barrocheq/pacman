import java.awt.Color;

public class Monstre3 extends Monstre{

	
	private int i;
	private int j;
	private Direction nextDir;
	private Direction lastDir;
	private Direction heroDir;
	
	public Monstre3(Cell cell, Model model, int repop, int speed) {
		super(cell, model, repop, Color.PINK, speed);
		this.i = 0;
		this.j = 0;
		this.nextDir = null;
		this.lastDir = null;
		this.heroDir = null;
		// TODO Auto-generated constructor stub
	}

	public void meur(){
		this.vivant = false;
		this.cell=this.cellpop;
		this.lastMove = null;
		this.lastDir = null;
		this.heroDir = null;
		this.nextDir = null;
		
	}
	
	public Direction Dir(){
		this.heroDir = null;
		getHeroLinePlus();
		if(this.cell.geti()==this.i && this.cell.getj()==this.j){
			this.i = 0;
			this.j = 0;
			this.lastDir = null;
			System.out.println("test");
			System.out.println(this.nextDir);
			return this.nextDir;
			
		}else if(this.lastDir!=null){
			return this.lastDir;
		}
		
		if(this.heroDir != null){
			return this.heroDir;
		}else{
			Direction[] dirs = movePossible();
			return dirs[(int) (dirs.length*Math.random())];
		}
		
	}
	
	public void getHeroLinePlus(){
		int tempi;
		int tempj;
		
		
		for(Direction dir: Direction.values()){
			if(this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()].passable()){
				
				tempi = this.cell.geti();
				tempj = this.cell.getj();
				
				while(true){
					tempi = tempi+dir.dI();
					tempj = tempj+dir.dJ();

					if(!this.map[tempi][tempj].passable()){
						break;
					}
					if(this.map[tempi+dir.dI()][tempj+dir.dJ()].equals(this.model.getHero().getCell())){
						this.heroDir = dir;
					}
					for(Direction d:Direction.values()){
						if(this.map[tempi+d.dI()][tempj+d.dJ()].passable()){
							if(this.map[tempi+d.dI()][tempj+d.dJ()].equals(this.model.getHero().getCell())){
								this.nextDir = d;
								this.i = tempi;
								this.j = tempj;
								this.lastDir = dir;
							}
						}
						
					}

				}
			}
		}
	}
	
//	public Direction getHeroLine(){
//		
//		int tempi;
//		int tempj;
//		
//		
//		for(Direction dir: Direction.values()){
//			if(this.map[this.cell.geti()+dir.dI()][this.cell.getj()+dir.dJ()].passable()){
//				
//				tempi = this.cell.geti();
//				tempj = this.cell.getj();
//				
//				while(true){
//					tempi = tempi+dir.dI();
//					tempj = tempj+dir.dJ();
//					if(!this.model.getMap()[tempi][tempj].passable()){
//						break;
//					}
//					if(this.model.getMap()[tempi][tempj].equals(this.model.getHero().getCell())){
//						return dir;
//					}
//				}
//			}
//		}
//		return null;
//	}
}
