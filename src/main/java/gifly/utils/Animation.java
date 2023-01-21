package gifly.utils;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class Animation {

	private static ImageOutputStream getOutputStream(String fileName) throws IOException {
		return new FileImageOutputStream(new File(fileName + ".gif"));
	}


	public static void writeFramePerImage(List<Image> images, String fileName) throws IOException {
		BufferedImage img = images.get(0).getBufferedImage();
		images.get(0).applyFrameToBufferedImage(img);
		ImageOutputStream output = getOutputStream(fileName);
		GifSequenceWriter writer = new GifSequenceWriter(output, img.getType(), 50, true);
		IntStream.range(0, images.size()).forEach(i -> {
			try {
				images.get(i).applyFrameToBufferedImage(img);
				writer.writeToSequence(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		writer.close();
		output.close();
	}

	public static void writeFramesOfImage(int frames, int delay, Image image, String fileName, int frameOffset) throws IOException {
		BufferedImage img = image.getBufferedImage();
		ImageOutputStream output = getOutputStream(fileName);
		GifSequenceWriter writer = new GifSequenceWriter(output, img.getType(), delay, true);
		IntStream.range(0, frameOffset).forEach(i -> {
			image.drawFrame();
			image.applyFrameToBufferedImage(img);
		});
		IntStream.range(0, frames).forEach(i -> {
			try {
				image.drawFrame();
				image.applyFrameToBufferedImage(img);
				writer.writeToSequence(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		writer.close();
		output.close();
	}
}
