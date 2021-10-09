package com.suven.framework.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public final class MatrixToImageWriter {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {}


    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static BufferedImage writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
//        if (!ImageIO.write(image, format, file)) {
//            throw new IOException("Could not write an image of format " + format + " to " + file);
//        }
        return image;
    }

    public static BufferedImage writeToFile(BitMatrix matrix, String format)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        return image;
    }


    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
//        if (!ImageIO.write(image, format, stream)) {
//            throw new IOException("Could not write an image of format " + format);
//        }
    }




    /**
     * 给二维码图片添加Logo
     *
     * @param barCodeImage
     * @param logoPic
     */
    public static BufferedImage addLogo_QRCode(BufferedImage barCodeImage, File logoPic, LogoConfig logoConfig) {
        try {
            if (!logoPic.isFile()) {
                System.out.print("file not find !");
                throw new IOException("file not find !");
            }

            /**
             * 读取二维码图片，并构建绘图对象
             */
            Graphics2D g = barCodeImage.createGraphics();

            /**
             * 读取Logo图片
             */
//            BufferedImage logo = ImageIO.read(logoPic);

            int widthLogo = barCodeImage.getWidth() / logoConfig.getLogoPart();
            int heightLogo = barCodeImage.getWidth() / logoConfig.getLogoPart(); //保持二维码是正方形的

            // 计算图片放置位置
            int x = (barCodeImage.getWidth() - widthLogo) / 2;
            int y = (barCodeImage.getHeight() - heightLogo) / 2 - heightLogo;


            //开始绘制图片
//            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 10, 10);
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, widthLogo, heightLogo);

            g.dispose();
            return barCodeImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param pressText 文字
     * @param newImg    带文字的图片
     * @param image     需要添加文字的图片
     * @param fontStyle
     * @param color
     * @param fontSize
     * @param width
     * @param height
     * @为图片添加文字
     */
    public static void pressText(String pressText, String newImg,
                                 BufferedImage image, String formatName,
                                 int fontStyle, Color color,
                                 int fontSize, int width, int height) {

        //计算文字开始的位置
        //x开始的位置：（图片宽度-字体大小*字的个数）/2
        int startX = (width - (fontSize * pressText.getBytes().length)) ;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height - (height - width) / 2;

        System.out.println("startX: " + startX);
        System.out.println("startY: " + startY);
        System.out.println("height: " + height);
        System.out.println("width: " + width);
        System.out.println("fontSize: " + fontSize);
        System.out.println("pressText.length(): " + pressText.length());

        try {
            int imageW = image.getWidth();
            int imageH = image.getHeight();
            Graphics g = image.createGraphics();
            g.drawImage(image, 0, 0, imageW, imageH, null);
            g.setColor(color);
            g.setFont(new Font("粗体", fontStyle, fontSize));
            g.drawString(pressText, startX, startY);
//            g.drawString("诗多多", startX +30 , startY +30 );
            g.dispose();

            FileOutputStream out = new FileOutputStream(newImg);
//            ImageIO.write(image, formatName, out);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(image);
            out.close();
            System.out.println("image press success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    //二维码表示的内容
    private static String content = "http://www.changxingnanjing.com?qr_code=01900302|773f3c8f872bf34cc3fe97022720330e";

    //存放logo的文件夹
    private static String path = "/Users/suven/project/workspace/openSource/top-suven/";

    public static void main(String args[]) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            @SuppressWarnings("rawtypes")
            Map hints = new HashMap();

            //设置UTF-8， 防止中文乱码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //设置二维码四周白色区域的大小
            hints.put(EncodeHintType.MARGIN, 2);
            //设置二维码的容错性
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            //width:图片完整的宽;height:图片完整的高
            //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
            int width = 300;//352
            int height = 350;//500;//612

            //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            //qrcFile用来存放生成的二维码图片（无logo，无文字）
            File logoFile = new File(path, "qrcFile.jpg");
            File logo = new File(path, "logo.png");
            String formatName = "png";

            //开始画二维码
            BufferedImage barCodeImage = MatrixToImageWriter.writeToFile(bitMatrix, formatName);

            //在二维码中加入图片
            LogoConfig logoConfig = new LogoConfig(); //LogoConfig中设置Logo的属性
            BufferedImage image = addLogo_QRCode(barCodeImage, logo, logoConfig);

            int font = 30; //字体大小
            int fontStyle = Font.BOLD; //字体风格

            //用来存放带有logo+文字的二维码图片
            String newImageWithText = path + "/newLogo.png";
            //附加在图片上的文字信息
            String text = "诗多多";

            //在二维码下方添加文字（文字居中）
            pressText(text, newImageWithText, image,formatName, Font.BOLD, Color.black, font, width, height);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

