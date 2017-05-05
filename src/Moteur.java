import javax.swing.JFrame;

public class Moteur extends Thread {

	private JFrame frame;
	private View view;
	private Model model;
	
	public Moteur(View view,Model model){
		this.frame = view.getFrame();
		this.view = view;
		this.model = model;
		
	}
	
	
	public void run(){
		while(true){
			this.frame.repaint();
			if(this.model.getState()){
				for(Monstre m : this.model.getMonstre()){
					if(m!=null && this.model.getHero().getCell().equals(m.getCell())){
						m.meur();
						this.model.getHero().incScore(20);
					}
				}
			}else{
				for(Monstre m : this.model.getMonstre()){
					if(this.model.getHero().getCell().equals(m.getCell())){
						System.out.println("Perdu");
						System.exit(0);
					}
				}
			}
			if(this.model.nbBonbon()==0){
				System.out.println("GG");
				System.exit(0);
			}
			this.view.label.setText("Score : "+this.model.getHero().getScore());
		}
		
		
	}
}
