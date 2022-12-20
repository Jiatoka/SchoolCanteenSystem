package Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**读写图片的静态方法**/
public class ImageUtils {
    private  static Image addImage;
    private  static Image schoolIconImage;
    /**菜品**/
    public  static Image defaultImage;
    public  static Image cakeImage;
    public  static Image chickenImage;
    public  static Image pudingImage;
    public  static Image orangeImage;
    public  static HashMap<Integer,Image> foodImage;

    /**用户头像**/
    public static  Image userImage;
    public static Image getSchoolIconImage() {
        return schoolIconImage;
    }

    public static Image getAddImage() {
        return addImage;
    }

    public static Image getSubImage() {
        return subImage;
    }

    private  static Image subImage;
    public   static void initImage() throws IOException {
        foodImage=new HashMap<>();
        //读取图片
        BufferedImage addImageReader= ImageIO.read(new File("src/main/java/Image/jiahao.png" +
                ""));
        BufferedImage subImageReader= ImageIO.read(new File("src/main/java/Image/jianhao.png" +
                ""));
        BufferedImage schoolIconImageReader= ImageIO.read(new File("src/main/java/Image/schoolIcon.png" +
                ""));
        BufferedImage defaultImageReader=ImageIO.read(new File("src/main/java/Image/defaultImage.png" +
                ""));
        BufferedImage cakeImageReader=ImageIO.read(new File("src/main/java/Image/cakeImage.png" +
                ""));
        BufferedImage chickenImageReader=ImageIO.read(new File("src/main/java/Image/chickenImage.jpg" +
                ""));
        BufferedImage orangeImageReader=ImageIO.read(new File("src/main/java/Image/orangeImage.png" +
                ""));
        BufferedImage pudingImageReader=ImageIO.read(new File("src/main/java/Image/puding.png" +
                ""));
        BufferedImage userImageReader=ImageIO.read(new File("src/main/java/Image/userImage.png" +
                ""));

        //缩放图片
        addImage=shrinkImage(addImageReader);
        subImage=shrinkImage(subImageReader);
        schoolIconImage=shrinkImage(schoolIconImageReader,100.0);
        defaultImage=shrinkImage(defaultImageReader);
        cakeImage=shrinkImage(cakeImageReader);
        chickenImage = shrinkImage(chickenImageReader);
        orangeImage=shrinkImage(orangeImageReader);
        pudingImage=shrinkImage(pudingImageReader);
        userImage=shrinkImage(userImageReader);

        //将菜品加入hashMap
        foodImage.put(3,orangeImage);
        foodImage.put(5,cakeImage);
        foodImage.put(8,pudingImage);
        foodImage.put(9,defaultImage);
        foodImage.put(10,chickenImage);
    }

    private static Image shrinkImage(BufferedImage bufferedImage){
        int width= bufferedImage.getWidth();
        int height=bufferedImage.getHeight();
        //等比缩放到100以内
        double rate=height/100.0;
        rate+=1;
        Image image=bufferedImage.getScaledInstance((int)(width/rate),
                (int)(height/rate),Image.SCALE_AREA_AVERAGING);
        return  image;
    }

    private static Image shrinkImage(BufferedImage bufferedImage,double limit){
        int width= bufferedImage.getWidth();
        int height=bufferedImage.getHeight();
        //等比缩放到100以内
        double rate=height/limit;
        rate+=1;
        Image image=bufferedImage.getScaledInstance((int)(width/rate),
                (int)(height/rate),Image.SCALE_AREA_AVERAGING);
        return  image;
    }
}
