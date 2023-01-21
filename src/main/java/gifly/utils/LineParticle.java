package gifly.utils;


public class LineParticle extends Particle {
	double xV, yV;

	public LineParticle(double x, double y, double xV, double yV) {
		super(x, y);
		this.xV = xV;
		this.yV = yV;
//		this.colorData = new int[] { 255, 255, 255, 0 };
	}

	@Override
	public void tick(Image img) {
		if (!visible)
			return;
		x += xV;
		y += yV;
		if (Math.min(x, y) < 0 || Math.max(x, y) >= img.pixels.length)
			visible = false;
	}

	@Override
	public LineParticle copy() {
		LineParticle p = new LineParticle(x, y, xV, yV);
		p.id = Particle.NEXT++;
		p.particleSize = this.particleSize;
		return p;
	}
}
