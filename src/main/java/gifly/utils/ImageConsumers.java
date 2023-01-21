package gifly.utils;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class ImageConsumers {

	public static Consumer<Image> noise = (image) -> {
		int[][][] pixels = image.getPixels();
		IntStream.range(0, pixels.length).forEach(y -> IntStream.range(0, pixels.length).forEach(x -> {
			int mono = Rng.intsInRange(1, 0, 255)[0];
			pixels[y][x] =
					new int[]{
							mono, mono, mono,
							Rng.intsInRange(1, 0, 255)[0],
					};
		})
		);
	};

	public static Consumer<Image> moveParticles = (image) -> {
		int[][][] pixels = image.getPixels();

		if (image.hasParticleSmoke() || image.fade > 0) {
			IntStream.range(0, pixels.length).forEach(y -> IntStream.range(0, pixels.length).filter(x ->
					pixels[y][x][0] != 0
			).forEach(x -> {
				if (image.hasParticleSmoke()) {
					int smokeColor = (pixels[y][x][0] / 2);
					if (y > 0) {
						IntStream.of(Rng.intsInRange(Rng.intInRange(0, 2), -2, 2)).map(i ->
								Math.min(Math.max(0, i + x), pixels.length - 1)
						).forEach(i -> IntStream.range(0, 4).forEach(j ->
								pixels[y - 1][i][j] += smokeColor)
						);
					}
					IntStream.range(0,3).forEach(i -> pixels[y][x][i] = smokeColor);
				}
				else IntStream.range(0,3).forEach(i -> {
					pixels[y][x][i] *= image.fade;
				});
//				if (Arrays.stream(pixels[y][x], 0, 3).sum() == 0) {
//					pixels[y][x][3] = 0;
//				}
			}
			));
		}

		image.getParticles().stream().peek(particle ->
				particle.tick(image)
		).filter(Particle::isVisible).forEach(image::drawParticle);
	};

}
