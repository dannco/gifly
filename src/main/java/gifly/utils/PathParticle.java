package gifly.utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class PathParticle extends LineParticle {
	int stage = 0;
	List<double[]> points = new ArrayList<>();
	boolean set;
	double speed;
	int framesInLoop;

	public PathParticle(double x, double y, int frames) {
		super(x, y, 0,0);
		addPoint(x, y);
		this.framesInLoop = frames;
	}

	public void addPoint(double x, double y) {
		points.add(new double[] {
				x, y
		});
	}

	private void setPath() {
		if (points.size() == 1) {
			speed = 0;
		}
		double distance = IntStream.range(1, points.size()).mapToDouble(i ->
			getDistance(points.get(i - 1), points.get(i))
		).sum();
		distance += getDistance(points.get(0), points.get(points.size() - 1));
		speed = distance / framesInLoop;
		incrementStageAndSetVectors();
		set = true;
	}

	private void incrementStageAndSetVectors() {
		stage = (stage + 1) % points.size();
		double[] destination = points.get(stage);
		double distance = getDistance(x, y, destination[0], destination[1]);
		double frames = distance/speed;
		xV = (destination[0] - x) / frames;
		yV = (destination[1] - y) / frames;
	}

	private static double getDistance(double[] a, double[] b) {
		return getDistance(a[0], a[1], b[0], b[1]);
	}

	private static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(
			Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)
		);
	}

	public void setToStage(int i) {
		stage = i;
		double[] position = points.get(stage);
		x = position[0];
		y = position[1];
		incrementStageAndSetVectors();
	}

	@Override
	public void tick(Image img) {
		if (!set) {
			setPath();
			set = true;
		}
		if (speed == 0)
			return;
		double[] next = points.get(stage);
		double distanceToNext = getDistance(x, y, next[0], next[1]);
		if (distanceToNext/speed <= 1) {
			setToStage(stage);
			x += xV * distanceToNext/speed;
			y += yV * distanceToNext/speed;
		} else {
			x += xV;
			y += yV;
		}
		checkVisibility(img);
	}

	@Override
	public PathParticle copy() {
		PathParticle p = new PathParticle(x, y, framesInLoop);
		p.points = new ArrayList<>(this.points);
		return p;
	}

	public void reverseOrder() {
		Collections.reverse(points);
	}
}
