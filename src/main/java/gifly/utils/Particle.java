package gifly.utils;

import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class Particle {
	protected static int NEXT = 1;

	boolean visible;
	int particleSize = 1;
	double x; double y; int id;
	int[] colorData = new int[] { 255, 255, 255, 255 }; // RGBA

	public Particle(double x, double y) {
		visible = true;
		this.id = NEXT++;
		this.x = x;
		this.y = y;
	}

	abstract void tick(Image img);

	public int getPackedRgba() {
		return Image.packedRgb(colorData);
	}

	public int[] getRgba() {
		return Arrays.copyOf(colorData, 4);
	}

	public boolean isVisible() {
		return visible;
	}

	protected void checkVisibility(Image img) {
		visible = Math.max(x, y) < img.pixels.length && Math.min(x, y) >= 0;
	}

	public void setColorData(int[] data) {
		IntStream.range(0, colorData.length).forEach(i -> colorData[i] = data[i]);
	}

	public void setParticleSize(int i) {
		particleSize = i;
	}

	public abstract Particle copy();
}
