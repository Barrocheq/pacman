import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class View {

	private JFrame frame;
	public static final int SCALE = 30;
	private plateau plateau;
	private Model model;
	public JLabel label;
	private Container cp;
	private JPanel glass;
	private boolean wait;
	private int choixLvl; // Random = 0, lvl = 1, DnD = 2

	public int getChoixLvl() {
		return choixLvl;
	}

	/**
	 * Constructeur pour niveau de base
	 */
	public View() {
		wait = true;

		this.frame = new JFrame();
		cp = frame.getContentPane();
		frame.setTitle("PacMan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp.setLayout(new BorderLayout());

		this.glass = (JPanel) this.frame.getGlassPane();
		label = new JLabel();
		label.setForeground(Color.RED);
		label.setFont(new Font("Courier", Font.BOLD, this.SCALE));
		glass.add(label);
	}

	public void setPause() {
		this.label.setForeground(Color.RED);
		this.label.setText("Pause");
		this.label.setFont(new Font("Courier", Font.BOLD, this.SCALE * 2 ));

		this.glass.setVisible(true);
	}

	public void releasedPause() {
		this.label.setFont(new Font("Courier", Font.BOLD, this.SCALE));
		this.glass.setVisible(false);
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public boolean getWait() {
		if (this.wait)
			return true;
		else
			return false;
	}

	public void loading(Model m) {
		cp.removeAll();

		JProgressBar bar = new JProgressBar(0, 100);
		bar.setValue(m.getLoading());


		cp.add(bar);
		cp.revalidate();
		cp.repaint();

		this.frame.setVisible(true);
	}

	public void menu()  {
		this.glass.setVisible(false);

		this.frame.setSize(500, 300);

		cp.removeAll();

		JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		JButton jRandom = new JButton("Random");
		JButton jLevel = new JButton("Level");
		JButton jDnD = new JButton("Drag&Drop");
		
		JLabel score;
		try {
			score = new JLabel("Meilleur score : "+this.Best());
			pan.add(score);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// cp.setLayout(new FlowLayout());
		
		pan.add(jRandom);
		pan.add(jLevel);
		pan.add(jDnD);
		pan.setBackground(Color.BLACK);

		jRandom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wait = false;
				choixLvl = 0;
			}
		});

		jLevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wait = false;
				choixLvl = 1;
			}
		});

		jDnD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wait = false;
				choixLvl = 2;
			}
		});

		cp.add(pan,BorderLayout.NORTH);
		cp.add(new JLabel(new ImageIcon("pacman.png")),BorderLayout.CENTER);
		cp.revalidate();
		cp.repaint();
		this.frame.setVisible(true);
	}


	public void setSizeFrame(int sizeH, int sizeL) {

		String os = System.getProperty("os.name").toLowerCase();


		if (os.contains("win"))
			this.frame.setSize(((sizeL + 1) * SCALE)-8, ((sizeH + 2) * SCALE)-4);
		else if (os.contains("nux") || os.contains("nix"))
			this.frame.setSize(((sizeL + 1) * SCALE), ((sizeH + 2) * SCALE));
		else
			this.frame.setSize((sizeL) * SCALE, (sizeH) * SCALE + 22);



	}

	public void init(int size) {
		// this.model = model;
		cp.removeAll();

		setSizeFrame(size, size);

		cp.revalidate();
		cp.repaint();

		this.frame.setVisible(true);
	}

	public void init(Model model, int sizeH, int sizeL) {

		this.model = model;
		cp.removeAll();

		setSizeFrame(sizeH, sizeL);
		this.plateau = new plateau(this.model); // Dessins du plateau
		cp.add(this.plateau);

		cp.revalidate();
		cp.repaint();

		this.frame.setVisible(true);
	}

	public void init(RandomLvl model, int sizeH, int sizeL, boolean see) {
		// this.glass.setLayout(null);
		label.setForeground(Color.WHITE);
		label.setText(Integer.toString(100 - (model.getNbTrou() * (100 / (8 * 8)))) + "%");
		setSizeFrame(sizeH, sizeL);
		Construction plateau = new Construction(model); // Dessins du plateau
		cp.add(plateau);

		this.glass.setVisible(see);
		this.frame.setVisible(see);
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public void perdu(int vie) {
		for (int i = 0; i < 5; i++) {
			this.label.setText("VIE RESTANTES: " + ( 3 - vie));
			this.glass.setVisible(true);
			glass.repaint();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public String Best() throws IOException{
		BufferedReader file = new BufferedReader(new FileReader("score.txt"));
		String line;
		int res = 0;
		int cut;
		while ((line = file.readLine()) != null) {
			cut = Integer.parseInt(line.split(":")[1]);
			if(res<cut){
				res = cut;
			}
		}
		file.close();
		return Integer.toString(res);
	}
}

class Construction extends JPanel {
	private RandomLvl model;
	private boolean anim = true;
	private int i = 0;
	private int SCALE = View.SCALE;
	private int op = 1;

	/**
	 * Constructeur par defaut
	 * 
	 * @param model
	 */
	public Construction(RandomLvl model) {
		this.model = model;
	}

	/**
	 * Fonction de dessins du plateau
	 * 
	 * @param g
	 *            dessins
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		// Anti-aliasing
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);

		for (int i = 0; i < this.model.getSize(); i++) {
			for (int j = 0; j < this.model.getSize(); j++) {
				this.model.getMap()[i][j].paintCell(g2, i * this.SCALE, j * this.SCALE, this.SCALE);
			}
		}

		Rectangle2D rect = new Rectangle2D.Double(0, 0, this.SCALE * model.getSize(), this.SCALE * model.getSize());
		g2.setPaint(new Color(0, 0, 0, this.model.getNbTrou() * (255 / (8 * 8))));
		g2.fill(rect);

		if (op > 255)
			op = 255;

		try {
			this.model.getHero().paintHero(g2, this.SCALE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class plateau extends JPanel {

	private Model model;
	private boolean anim = true;
	private int i = 0;
	private int SCALE = View.SCALE;
	private JLabel labelScore;

	/**
	 * Constructeur par defaut
	 * 
	 * @param model
	 */
	public plateau(Model model) {
		this.model = model;
		this.labelScore = new JLabel();
		this.labelScore.setText("Score");
		this.labelScore.setLocation(100, 100);
		this.labelScore.setFont(new Font("Courier", Font.BOLD, this.SCALE / 2));
		this.labelScore.setForeground(Color.WHITE);
		this.add(labelScore);
	}

	/**
	 * Fonction de dessins du plateau
	 * 
	 * @param g
	 *            dessins
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		this.labelScore.setText("Score : " + this.model.getScore());
		// Anti-aliasing
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);

		for (int i = 0; i < this.model.getSizeL(); i++) {
			for (int j = 0; j < this.model.getSizeH(); j++) {
				this.model.getMap()[i][j].paintCell(g2, i * this.SCALE, j * this.SCALE, this.SCALE);
			}
		}
		try {

			// Animation Hero
			if (this.model.getHero().nextIsPassable()) {
				this.model.getHero().paintHeroAnim(g2, this.SCALE, i);
				if (anim) {
					i++;

					if (i > 15)
						anim = false;
				} else {
					i--;

					if (i < 0)
						anim = true;
				}
			} else {
				this.model.getHero().paintHero(g2, this.SCALE);
			}

			// Dessins des monstres
			for (Monstre m : this.model.getMonstre()) {
				m.paintMonstre(g2, this.SCALE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(this.model.getCercle() != null){
			this.model.getCercle().paintCercle(g2, SCALE);
		}
	}
}
