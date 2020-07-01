package com.github.wxiaoqi.security.common.util;

import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类直接拷贝到源码中使用
 */
public class MatrixToImageWriter {

    // 日志
    private static Logger logger = LoggerFactory.getLogger(MatrixToImageWriter.class);
    private static final int BLACK = 0xFF000000;//用于设置图案的颜色
    private static final int WHITE = 0xFFFFFFFF; //用于背景色

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y,  (matrix.get(x, y) ? BLACK : WHITE));
                //image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;
    }

    /**
     * 带logo的二维码
     * 文件
     */
   /* public static void writeToFile(BitMatrix matrix, String format, File file, String logUri) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        image = logoConfig.setMatrixLogo(image, logUri);
        if (!ImageIO.write(image, format, file)) {
            //logger.info("生成图片失败!");
            System.out.println("");
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }else{
            //logger.info("生成图片成功！");
        }
    }*/

    /**
     * 不带logo的二维码
     * 文件
     */
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            logger.info("生成图片失败!");
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }else{
            logger.info("生成图片成功！");
        }
    }

    /**
     * 带logo的二维码
     * 文件流
     */
   /* public static void writeToStream(BitMatrix matrix, String format, OutputStream stream, String logUri) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        image = logoConfig.setMatrixLogo(image, logUri);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }*/

    /**
     * 不带logo的二维码
     * 文件流
     */
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

}
