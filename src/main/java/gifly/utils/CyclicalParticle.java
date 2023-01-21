package gifly.utils;

import java.util.Objects;
import java.util.function.Function;

public class CyclicalParticle extends Particle {
	int direction = 1;
	public int frame;
	public int framesInX;
	public int framesInY;
	public int radius;
	public double cX; public double cY;

	Function<Double, Double> xCalc;
	Function<Double, Double> yCalc;

	Particle reference;

	public CyclicalParticle(double x, double y) {
		super(x, y);
		cX = x;
		cY = y;
	}

	public void setCycleParameters(int radius, int frame, int framesInX) {
		setCycleParameters(radius, frame, framesInX, framesInX);
	}
	public void setCycleParameters(int radius, int frame, int framesInX, int framesInY) {
		this.radius = radius;
		this.frame = frame;
		this.framesInX = framesInX;
		this.framesInY = framesInY;
	}

	public void setReferenceParticle(Particle reference) {
		this.reference = reference;
	}

	@Override
	void tick(Image img) {
		if (Objects.nonNull(reference)) {
			cX += (int)(Math.sqrt(Math.abs(cX - reference.x)) * (cX > reference.x ? -1 : 1));
			cY += (int)(Math.sqrt(Math.abs(cY - reference.y)) * (cY > reference.y ? -1 : 1));
		}
		frame += direction;

		double progressX = (double)frame/framesInX;
		double progressY = (double)frame/framesInY;
		x = cX + radius * xCalc.apply(progressX);
		y = cY + radius * yCalc.apply(progressY); // cY + (radius * 1.2 * Math.cos(progressY * 2 * Math.PI));
		checkVisibility(img);
		frame %= Math.max(framesInX, framesInY);
	}

	public void setCalcForX(Function<Double, Double> function) {
		xCalc = function;
	}

	public void setCalcForY(Function<Double, Double> function) {
		yCalc = function;
	}



	@Override
	public CyclicalParticle copy() {
		CyclicalParticle particle = new CyclicalParticle(x, y);
		particle.id = Particle.NEXT++;
		particle.cX = this.cX;
		particle.cY = this.cY;
		particle.direction = this.direction;
		particle.radius = this.radius;
		particle.reference = this.reference;
		particle.frame = this.frame;
		particle.framesInX = this.framesInX;
		particle.framesInY = this.framesInY;
		particle.particleSize = this.particleSize;
		return particle;
	}

	public void setClockWise(boolean b) {
		direction = b ? -1 : 1;
	}

	public void setFrame(int i) {
		this.frame = i;
	}
}
