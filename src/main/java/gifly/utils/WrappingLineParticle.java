package gifly.utils;

public class WrappingLineParticle extends LineParticle {


	public WrappingLineParticle(int x, int y, int xV, int yV) {
		super(x, y, xV, yV);
//		this.colorData = new int[] { 255, 255, 255, 0 };
	}

	@Override
	public void tick(Image img) {
		super.tick(img);
		visible = true;
		if (x < 0) {
			x += img.pixels.length - 1;
		} else if (x >= img.pixels.length) {
			x %= img.pixels.length;
		}
		if (y < 0) {
			y += img.pixels.length - 1;
		} else if (y >= img.pixels.length) {
			y %= img.pixels.length;
		}
	}

}
