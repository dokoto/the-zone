package Utils;
import java.util.Random;

public class RandomUtil {
    // Declare as class variable so that it is not re-seeded every call
    private static Random random = new Random();

    /**
     * Returns a psuedo-random number between min and max (both inclusive)
     * @param min Minimim value
     * @param max Maximim value. Must be greater than min.
     * @return Integer between min and max (both inclusive)
     * @see java.util.Random#nextInt(int)
     */
    public static int nextInt(int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return random.nextInt((max - min) + 1) + min;
    }
}

