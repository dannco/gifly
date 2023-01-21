import gifly.utils.Animation;
import gifly.utils.BouncingLineParticle;
import gifly.utils.CyclicalParticle;
import gifly.utils.Image;
import gifly.utils.ImageConsumers;
import gifly.utils.PathParticle;
import gifly.utils.Rng;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.IntStream;

public class App {

	public static void main(String[] args) throws IOException {
		int length = 128;
		int frames = 256;
		Image image = new Image(length);
		image.setFade(0.995);
//		image.setParticleSmoke(true);
		image.setBackground(new int[] {0, 0, 0, 255});
		image.setDrawer(ImageConsumers.moveParticles);

		PathParticle pather = new PathParticle(length / 2.0, length / 8.0, frames);
		pather.addPoint(3.0 * length / 4, length / 3.0);
		pather.addPoint(length / 4.0, 2 * length / 3.0);
		pather.addPoint(length / 2.0, 7 * length / 8.0);
		pather.addPoint(3 * length / 4.0, 2 * length / 3.0);
		pather.addPoint(length / 4.0, length / 3.0);

		image.addParticle(pather);
		PathParticle pathCopy = pather.copy();
		pathCopy.reverseOrder();
		pathCopy.setToStage(1);
		image.addParticle(pathCopy);

		Function<Double, Double> sineCurve = (progress) -> (Math.sin(progress * 2 * Math.PI));
		Function<Double, Double> elongatedCos = (progress) -> (1.2 * Math.cos(progress * 2 * Math.PI));
		Function<Double, Double> elongatedCos2 = (progress) -> (1.5 * Math.cos(progress * 2 * Math.PI));

		IntStream.range(3, 5).forEach(i -> {
			CyclicalParticle p = new CyclicalParticle(length / 2.0, length / 2.0);
			p.setCalcForX(sineCurve);
			p.setCalcForY(elongatedCos);
			p.setCycleParameters(
					i * 10,
					Rng.intInRange(0, frames),
					frames
			);
			p.setClockWise(i % 2 == 0);
			image.addParticle(p);
		});
		CyclicalParticle p = new CyclicalParticle(length / 2.0, length / 2.0);
		p.setCalcForX(elongatedCos2);
		p.setCalcForY(sineCurve);
		p.setCycleParameters(
				12,
				Rng.intInRange(0, frames),
				frames / 2
		);
		image.addParticle(p);

		image.drawFrame();
		Animation.writeFramesOfImage(frames, 25, image, "particles", frames);

	}


}
