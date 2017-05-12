/**
 * Classe qui genere une maps aléatoire
 */
public class RandomLvl  {
    private Cell[][] map;
    private int i;
    private int j;
    private Direction d;
    private int nbTrou;
    private int nbTrouSupple;
    private int size;
    private int nbRouge;
    private Hero hero;
    private Model model;


    /**
     * Consteurs
     * @param m model du jeu
     */
    public RandomLvl(Model m ) {
        this.i = 1;
        this.j = 1;

        this.model = m;
    }


    // GETTEURS ET SETTEURS
    public Cell[][] getMap() {
        return map;
    }

    public int getSize() {
        return size;
    }

    public int getNbTrou() {
        return nbTrou;
    }

    public Hero getHero() {
        return hero;
    }



    /**
     * Initialisation du générateurs
     * @param size taille de la map a générer
     */
    public void init(int size) {

        this.size = size;

        // Nombre de trou a faire
        this.nbTrou = (size/2) * (size/2) - 1;

        // Nombre de trou supplémentaire pour créer des "boucles"
        this.nbTrouSupple = size;

        // Nombre de bonbonRouge a générer
        this.nbRouge = size/4;
        this.map = new Cell[size][size];


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i % 2 == 0 || j % 2 == 0) {
                    this.map[i][j] = new Cell(0,i,j,0);
                } else {
                    this.map[i][j] = new Cell(1,i,j,1);
                }

            }
        }

       make();
       generateSupBonbon();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * Fonction de génération
     */
    public void make() {
        int nextI = 0;
        int nextJ = 0;

        View v = new View(); // permet d'afficher la création

        while (nbTrou > 0) {
            if(model.getChargementR()) {
                v.init(this, this.getSize(), this.getSize(), true);
                this.hero = new Hero(this.map[i][j], this);
            }


            this.d = Direction.random();
            nextI = this.d.dI() * 2;
            nextJ = this.d.dJ() * 2;

            try {
                this.map[this.i+nextI][this.j+nextJ].passable();
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }



            if(check(this.i + nextI, this.j + nextJ, 4)) {
                this.map[this.i+this.d.dI()][this.j+this.d.dJ()] = new Cell(1,this.i+this.d.dI(),this.j+this.d.dJ(),1);
                nbTrou--;
                this.i = this.i + nextI;
                this.j = this.j + nextJ;

            } else if(this.map[this.i+nextI][this.j+nextJ].passable()) {
                this.i = this.i + nextI;
                this.j = this.j + nextJ;
            }
        }

        while (nbTrouSupple > 0) {
            if(model.getChargementR())
                v.init(this, this.getSize(), this.getSize(), true);
            this.d = Direction.random();
            nextI = this.d.dI() * 2;
            nextJ = this.d.dJ() * 2;

            try {
                this.map[this.i+nextI][this.j+nextJ].passable();
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }


            if(check(this.i + nextI, this.j + nextJ, 2)) {
                this.map[this.i+this.d.dI()][this.j+this.d.dJ()] = new Cell(1,this.i+this.d.dI(),this.j+this.d.dJ(),1);
                nbTrouSupple--;
                this.i = this.i + nextI;
                this.j = this.j + nextJ;

            } else if(this.map[this.i+nextI][this.j+nextJ].passable()) {
                this.i = this.i + nextI;
                this.j = this.j + nextJ;
            }
        }

        v.init(this, this.getSize(), this.getSize(), false);

    }

    /**
     * Check si on est enfermé
     * @param nI
     * @param nJ
     * @return true si oui
     */
    public boolean check(int nI, int nJ, int nbMur) {
        int nbWall = nbMur;
        int newI = 0;
        int newJ = 0;

        for(int k = 0; k < 4; k++) {

            switch (k) {
                case 0:
                    newI = -1;
                    newJ = 0;
                    break;
                case 1:
                    newI = 0;
                    newJ = -1;
                    break;
                case 2:
                    newI = 1;
                    newJ = 0;
                    break;
                case 3:
                    newI = 0;
                    newJ = 1;
                    break;
            }


            try {
                this.map[nI + newI][nJ + newJ].passable();
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }

            if(!(this.map[nI + newI][nJ + newJ].passable())) nbWall--;
        }

        return nbWall == 0;
    }

    /**
     * Fonction de génération aléatoire des bonbonMagique
     */
    public void generateSupBonbon() {

        while (this.nbRouge > 0) {
            int randI = (int)(Math.random() * (this.size-2)) + 2;
            int randJ = (int)(Math.random() * (this.size-2)) + 2;

            if(this.map[randI][randJ].passable()) {
                this.map[randI][randJ] = new Cell(1, randI, randJ, 2);
                this.nbRouge--;

            }
        }

    }

}
