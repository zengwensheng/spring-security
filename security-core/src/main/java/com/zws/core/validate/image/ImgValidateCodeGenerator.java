package com.zws.core.validate.image;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.validate.ValidateCode;
import com.zws.core.validate.ValidateCodeGenerator;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Author: Gosin
 * @Date: 2018/10/2 11:51
 * @Email: 2848392861@qq.com
 */
@Data
public class ImgValidateCodeGenerator  implements ValidateCodeGenerator {


    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generator() {
        int width = securityProperties.getCode().getImage().getWidth();
        int height = securityProperties.getCode().getImage().getHeight();
        int length =securityProperties.getCode().getImage().getLength();
        Long expireIn = securityProperties.getCode().getImage().getExpireIn();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(sRand,expireIn,image);
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
