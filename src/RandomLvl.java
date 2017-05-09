import java.util.Random;

/**
 * Created by Quentin on 09/05/2017.
 */
public class RandomLvl  {
    private Cell[][] map;
    private int i;
    private int j;
    private Direction d;
    private int nbTrou;
    private int nbTrouSupple;
    private int size;

    public RandomLvl() {
        this.i = 1;
        this.j = 1;

    }

    public Cell[][] getMap() {
        return map;
    }

    public int getSize() {
        return size;
    }

    public void init(int size) {

        this.size = size;

        this.nbTrou = (size/2) * (size/2) - 1;
        this.nbTrouSupple = 2;
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

    }

    public void make() {

        int nextI = 0;
        int nextJ = 0;

        while (nbTrou > 0) {

            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/


            //view.init(this, this.getSize(), this.getSize());
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
                System.out.println(nbTrou);
                this.i = this.i + nextI;
                this.j = this.j + nextJ;

            } else if(this.map[this.i+nextI][this.j+nextJ].passable()) {
                this.i = this.i + nextI;
                this.j = this.j + nextJ;
            }
        }

        while (nbTrouSupple > 0) {
            //view.init(this, this.getSize(), this.getSize());
            this.d = Direction.random();
            nextI = this.d.dI() * 2;
            nextJ = this.d.dJ() * 2;

            try {
                this.map[this.i+nextI][this.j+nextJ].passable();
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }

            //System.out.println("b");

            if(check(this.i + nextI, this.j + nextJ, 3)) {
                this.map[this.i+this.d.dI()][this.j+this.d.dJ()] = new Cell(1,this.i+this.d.dI(),this.j+this.d.dJ(),1);
                nbTrouSupple--;
                System.out.println(nbTrou);
                System.out.println("i : " + (this.i+this.d.dI()) +  ", j :" + (this.j+this.d.dJ()));
                this.i = this.i + nextI;
                this.j = this.j + nextJ;

            } else if(this.map[this.i+nextI][this.j+nextJ].passable()) {
                this.i = this.i + nextI;
                this.j = this.j + nextJ;
            }
        }


    }

    /**
     * Check si on est enfermé
     * @param nI
     * @param nJ
     * @return true si oui
     */
    public boolean check(int nI, int nJ, int nbMur) {
        boolean fourWall = true;
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
                //System.out.println("OUT OF BOUND");
                //fourWall = false;
                //nbWall--;
                continue;
            }

            if(!(this.map[nI + newI][nJ + newJ].passable())) nbWall--;
        }

        return nbWall == 0;
    }

}
