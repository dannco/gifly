package gifly.utils;

public class BouncingLineParticle extends LineParticle {

	public BouncingLineParticle(int x, int y, double xV, double yV) {
		super(x, y, xV, yV);
	}

	@Override
	public void tick(Image img) {
		super.tick(img);
		visible = true;
		if (x < 0) {
			xV *= -1;
			x = Math.abs(x);
		} else if (x >= img.pixels.length) {
			xV *= -1;
			x = 2 * img.pixels.length - x - 1;
		}
		if (y < 0) {
			yV *= -1;
			y = Math.abs(y);
		} else if (y >= img.pixels.length) {
			yV *= -1;
			y = 2 * img.pixels.length - y - 1;
		}
	}
}
