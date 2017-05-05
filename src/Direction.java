import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Enumeration permettant de definir les coordonnées en fonction du sens
 */
enum Direction {
	NORTH(0, -1), SOUTH(0, 1), EAST(1, 0), WEST(-1, 0);

	private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private final int dI, dJ;

	/**
	 * Constructeur par defaut
	 * @param di coordonnée i
	 * @param dj coordonnée j
	 */
	private Direction(int di, int dj) {
		this.dI = di;
		this.dJ = dj;
	}

	public int dI() {
		return dI;
	}
	public int dJ() {
		return dJ;
	}

	/**
	 * Génération aléatoire d'un mouvement
	 * @return la nouvelle direction
	 */
	public static Direction random() {
		return VALUES.get(new Random().nextInt(VALUES.size()));
	}
}