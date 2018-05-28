
/*
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
*/

public class OCRTest {

  /*  public static String FindOCR(String srImage, boolean ZH_CN) {
        try {
            System.out.println("start");
            double start=System.currentTimeMillis();
            File imageFile = new File(srImage);
            if (!imageFile.exists()) {
                return "图片不存在";
            }
            BufferedImage textImage = ImageIO.read(imageFile);
            //Tesseract instance=Tesseract.getInstance();
            Tesseract instance = new Tesseract();
            instance.setDatapath("C:\\Users\\YUI\\Desktop\\tessdata");//设置训练库
            if (ZH_CN)
                instance.setLanguage("chi_sim");//中文识别
            String result = null;
            result = instance.doOCR(textImage);
            double end=System.currentTimeMillis();
            System.out.println("耗时"+(end-start)/1000+" s");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "发生未知错误";
        }
    }
    public static void main(String[] args) throws Exception {
        String result=FindOCR("C:\\Users\\YUI\\Desktop\\资产.jpg",true);
        System.out.println(result);
    }*/

}
