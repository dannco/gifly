package gifly.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Image {
	int frame;
	double fade;
	Consumer<Image> drawer;

	List<Particle> particles = new ArrayList<>();

	int[][][] pixels;
	private boolean particleSmoke;

	public Image(int size) {
		pixels = new int[size][size][4];
	}

	public static int[] mergeColors(int[] color1, int[] color2, double fade) {
		return new int[] {
				(int)Math.floor((color1[0] + color2[0]) * fade),
				(int)Math.floor((color1[1] + color2[1]) * fade),
				(int)Math.floor((color1[2] + color2[2]) * fade),
				(int)Math.floor((color1[3] + color2[3]) * fade),
		};
	}

	public void addParticle(Particle p) {
		particles.add(p);
	}

	public void setBackground(int[] color) {
		IntStream.range(0, pixels.length).forEach(y ->
				IntStream.range(0, pixels.length)
						.forEach(x ->
								pixels[y][x] = Arrays.copyOf(color, color.length)
						)
		);
	}
	public void setDrawer(Consumer<Image> drawer) {
		this.drawer = drawer;
	}

	public void drawFrame() {
		frame++;
		if (Objects.isNull(drawer)) {
			throw new IllegalStateException("image drawer is not set");
		}
		drawer.accept(this);
	}

	public void applyFrameToBufferedImage(BufferedImage img) {
		IntStream.range(0, pixels.length).forEach(y ->
				IntStream.range(0, pixels.length)
						.forEach(x ->
						img.setRGB(x, y, packedRgb(pixels[y][x]))
				)
		);

	}


	public BufferedImage getBufferedImage() {
		return new BufferedImage(pixels.length, pixels.length, BufferedImage.TYPE_INT_ARGB);
	}

	public void writeImageToFile(String fileName) {
		try {
			ImageIO.write(getBufferedImage(), "png",  new File(fileName + ".png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	final static int[] RGBA = new int[]{ 0, 8, 16, 24}; // BLUE, GREEN, RED, ALPHA
	public static int packedRgb(int[] input) {
		if (input.length != 4)
			throw new IllegalArgumentException("input does not have 4 entries");
		return IntStream.range(1, 4).reduce(input[0], (res, i) ->
			res + Math.min(input[i], 255) * (int)Math.pow(2, RGBA[i])
		);
	}

	public int[][][] getPixels() {
		return pixels;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public void setFade(double fade) {
		this.fade = fade;
	}

	public void setParticleSmoke(boolean b) {
		particleSmoke = b;
	}

	public boolean hasParticleSmoke() {
		return particleSmoke;
	}

	public void drawParticle(Particle particle) {
		if (particle.particleSize == 1) {
			pixels[(int)Math.round(particle.y)][(int)Math.round(particle.x)] = particle.getRgba();
			return;
		}
		double radius = particle.particleSize / 2.0;
		IntStream.range(
				(int) Math.floor(0 - radius),
				(int) Math.floor(1 + radius)
		).filter(iX -> (particle.x + iX) > 0 && (particle.x + iX) < pixels.length).forEach(iX -> {
			int yRange = (int) Math.sqrt(Math.pow(radius, 2) - Math.pow(iX, 2));
			IntStream.range(-yRange, yRange + 1)
					.filter(iY -> (particle.y + iY) > 0 && (particle.y + iY) < pixels.length)
					.forEach(iY -> {
						try {
							pixels
									[(int)Math.round(particle.y + iY)]
									[(int)Math.round(particle.x + iX)]
									= particle.getRgba();
						} catch (ArrayIndexOutOfBoundsException e) {
							System.out.println("foo");
						}
					});
		});
	}
}
