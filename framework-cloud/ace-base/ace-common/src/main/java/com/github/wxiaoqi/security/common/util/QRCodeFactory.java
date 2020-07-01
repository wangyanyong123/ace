package com.github.wxiaoqi.security.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

public class QRCodeFactory {

    // 日志
    private static Logger logger = LoggerFactory.getLogger(QRCodeFactory.class);

    //创建二维码
    public static byte[] createQrCode(String content, String outFileUri, int width, int height) {
        int[] size= new int[]{width, height};
        String format = "png";
        byte[] buffer = null;
        File file = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            creatQrImage(content, format, outFileUri, size);
            file = new File(outFileUri);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null)
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            if(bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(file.exists())
                file.delete();
        }
        return buffer;
    }


    /**
     * 创建不含logo的二维码图片
     * @param content            二维码内容
     * @param format             生成二维码的格式
     * @param outFileUri         二维码的生成地址
     * @param size               用于设定图片大小（可变参数，宽，高）
     * @throws IOException       抛出io异常
     * @throws WriterException   抛出书写异常
     */
    public static void creatQrImage(String content,String format,String outFileUri, int ...size) throws IOException, WriterException {
        int width = 430; // 二维码图片宽度 430
        int height = 430; // 二维码图片高度430

        //如果存储大小的不为空，那么对我们图片的大小进行设定
        if(size.length==2){
            width=size[0];
            height=size[1];
        }else if(size.length==1){
            width=height=size[0];
        }

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
        //hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,//要编码的内容
                //编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
                //Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
                //MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E 1D,UPC/EAN extension,UPC_EAN_EXTENSION
                BarcodeFormat.QR_CODE,
                width, //条形码的宽度
                height, //条形码的高度
                hints);//生成条形码时的一些配置,此项可选

        // 生成二维码图片文件
        File outputFile = new File(outFileUri);
        //指定输出路径
        deleteWhite(bitMatrix, 1);
        //logger.info("输出文件路径为:{}",outputFile.getPath());
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);

    }

    //设置任意白边宽度 必须大于0
    private static BitMatrix deleteWhite(BitMatrix matrix,int white) {
        int[] rec = matrix.getEnclosingRectangle();
        System.out.println(rec[2] +"====" +rec[3]);
        int resWidth = rec[2] + 1 + white * 2;
        int resHeight = rec[3] + 1 + white *2;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        //resMatrix.setRegion(-2,-2,resWidth, resHeight);
        resMatrix.clear();

        for (int i = 0; i < resWidth - white * 2 ; i++) {
            for (int j = 0; j < resHeight - white * 2 ; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i + white, j + white);
            }
        }
        return resMatrix;
    }


    /**
     * 生成二维码
     * @param content 要生成的二维码内容
     * @return 返回以.png格式的文件的绝对路径
     */
    public static String createQrCodeImg(String content,int width, int height){
        String qrCodeFilePath = "";
        try {
            int qrCodeWidth = width;
            int qrCodeHeight = height;
            String qrCodeFormat = "png";
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 内容所使用字符集编码
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数c
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight, hints);

            BufferedImage image = new BufferedImage(qrCodeWidth, qrCodeHeight, BufferedImage.TYPE_INT_RGB);
            // 本地存放二维码的文件夹
            File qrCodeFile = new File("/opt/jmdeploy/temp"+"." + qrCodeFormat);
            ImageIO.write(image, qrCodeFormat, qrCodeFile);
            MatrixToImageWriter.writeToFile(bitMatrix, qrCodeFormat, qrCodeFile);
            qrCodeFilePath = qrCodeFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrCodeFilePath;
    }




}
