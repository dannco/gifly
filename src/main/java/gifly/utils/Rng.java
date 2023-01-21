package gifly.utils;

import java.util.Random;

public class Rng {
	private static Random random = new Random();



	public static int[] intsInRange(int count, int min, int max) {
		return random.ints(count).map(i -> (Math.abs(i) % (max + 1 - min)) + min).toArray();
	}


	public static int intInRange(int min, int max) {
		return (Math.abs(random.nextInt()) % (max + 1 - min)) + min;
	}
}
