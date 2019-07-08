package com.shouzan.back.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName: com.shouzan.back.rest.teeet
 * @Author: bin.yang
 * @Date: 2019/5/15 16:30
 * @Description: TODO 图片处理类
 */
public class ImageUtils {

    // 指定目录
    private String destFile;
    private int width;
    private int height;
    private BufferedImage img;
    private String ext;

    /**
     * @Description: 构造函数
     * @param srcFile  文件路径
     * @param lastName	 后缀名
     * @[param] [srcFile, lastName]
     * @return
     * @author:  bin.yang
     * @date:  2019/5/15 5:16 PM
     */
    public ImageUtils(String srcFile , String lastName) throws IOException {
        //得到最后一个.的位置
        int index = srcFile.lastIndexOf(".");
        //获取被缩放的图片的格式
        this.ext = srcFile.substring(index + 1);
        //获取目标路径(和原始图片路径相同,在文件名后添加了一个_s)
        this.destFile = srcFile.substring(0, index) + lastName+ "." + ext;
        //读取图片,返回一个BufferedImage对象
        this.img = ImageIO.read(new File(srcFile));
        //获取图片的长和宽
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    /**
     * @Description: 按比例对图片进行缩放
     * @param scale	 缩放比例
     * @[param] [scale]
     * @return void
     * @author:  bin.yang
     * @date:  2019/5/15 5:15 PM
     */
    public void zoomByScale(double scale) throws IOException {
        //获取缩放后的长和宽
        int _width = (int) (scale * width);
        int _height = (int) (scale * height);
        //获取缩放后的Image对象
        Image _img = img.getScaledInstance(_width, _height, Image.SCALE_DEFAULT);
        //新建一个和Image对象相同大小的画布
        BufferedImage image = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D graphics = image.createGraphics();
        //将Image对象画在画布上,最后一个参数,ImageObserver:接收有关 Image 信息通知的异步更新接口,没用到直接传空
        graphics.drawImage(_img, 0, 0, null);
        //释放资源
        graphics.dispose();
        //使用ImageIO的方法进行输出,记得关闭资源
        OutputStream out = new FileOutputStream(destFile);
        ImageIO.write(image, ext, out);
        out.close();
    }

    /**
     * @Description: 指定长和宽对图片进行缩放
     * @param width
     * @param height
     * @[param] [width, height]
     * @return void
     * @author:  bin.yang
     * @date:  2019/5/15 5:15 PM
     */
    public void zoomBySize(int width, int height) throws IOException {
        //与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
        Image _img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        OutputStream out = new FileOutputStream(destFile);
        ImageIO.write(image, ext, out);
        out.close();
    }



}
