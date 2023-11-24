package com.user.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/CaptchaServlet")
public class CaptchaServlet extends HttpServlet {
    private static final int WIDTH = 120; // 验证码图片宽度
    private static final int HEIGHT = 40; // 验证码图片高度
    private static final int CAPTCHA_LENGTH = 4; // 验证码字符长度

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedImage captchaImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = captchaImage.createGraphics();

        // 生成随机验证码字符串
        String captchaText = generateRandomCaptchaText();
        request.getSession().setAttribute("authCode",captchaText);
        // 设置背景色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // 设置字体和前景色
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);

        // 绘制验证码字符
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int x = 10 + i * 25;
            int y = 25;
            g2d.drawChars(new char[]{captchaText.charAt(i)}, 0, 1, x, y);
        }

        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g2d.setColor(getRandomColor());
            int x1 = new Random().nextInt(WIDTH);
            int y1 = new Random().nextInt(HEIGHT);
            int x2 = new Random().nextInt(WIDTH);
            int y2 = new Random().nextInt(HEIGHT);
            g2d.drawLine(x1, y1, x2, y2);
        }

        // 设置响应类型为图片
        response.setContentType("image/jpeg");

        // 输出验证码图片到响应流
        ImageIO.write(captchaImage, "jpeg", response.getOutputStream());
    }

    private String generateRandomCaptchaText() {
        // 生成随机字符序列
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captchaText = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int index = random.nextInt(characters.length());
            captchaText.append(characters.charAt(index));
        }
        return captchaText.toString();
    }

    private Color getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }
}
