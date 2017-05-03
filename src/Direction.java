import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

enum Direction {
	NORTH(0, -1), SOUTH(0, 1), EAST(1, 0), WEST(-1, 0), NORTH_EAST(1, -1), NORTH_WEST(-1, -1), SOUTH_EAST(1,1), SOUTH_WEST(-1, 1);

	private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private final int dI, dJ;

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

	public static Direction random() {
		return VALUES.get(new Random().nextInt(VALUES.size()));
	}
}