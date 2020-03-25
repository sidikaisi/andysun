package utils;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 *  @Description 把pdf解析成图片（耗内存）
 *  @Author zhangxiaojun
 *  @Date 2019/1/8
 *  @Version 1.0.0
 **/
public class PdfUtil {

	public static void main(String[] args) throws IOException {
		String filePath = "111.pdf";
		Document document = new Document();
		try {
			document.setFile(filePath);
		} catch (Exception ex) {
		}
		// save page caputres to file.
		float scale = 2f;
		float rotation = 0f;
		// Paint each pages content to an image and write the image to file
		System.out.println(document.getNumberOfPages());
		for (int i = 0; i < document.getNumberOfPages(); i++) {
			BufferedImage image = (BufferedImage) document
					.getPageImage(i, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, scale);
			RenderedImage rendImage = image;
			// capture the page image to file
			try {
				File file = new File("image_" + (i + 1) + ".png");
				ImageIO.write(rendImage, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}

			image.flush();
		}

		// clean up resources
		document.dispose();
	}

}
