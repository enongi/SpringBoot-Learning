package com.chapter115.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 控制器
 */
@Controller
public class MainController {
    /**
     * 存储登录状态
     */
    private Map<String, LoginResponse> loginMap = new ConcurrentHashMap<>();

    @GetMapping({ "/", "index" })
    public String index(Model model, @SessionAttribute(WebSecurityConfig.SESSION_KEY) String user) {
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 获取二维码
     *
     * @return
     */
    @GetMapping("login/getQrCode")
    public @ResponseBody
    Map<String, Object> getQrCode() throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("loginId", UUID.randomUUID());

        // app端登录地址
        String loginUrl = "http://localhost:8080/login/setUser/loginId/";
        result.put("loginUrl", loginUrl);
        result.put("image", createQrCode(loginUrl));
        return result;
    }

    /**
     * app二维码登录地址，这里为了测试才传{user},实际项目中user是通过其他方式传值
     *
     * @param loginId
     * @param user
     * @return
     */
    @GetMapping("login/setUser/{loginId}/{user}")
    public @ResponseBody Map<String, Object> setUser(@PathVariable String loginId, @PathVariable String user) {
        if (loginMap.containsKey(loginId)) {
            LoginResponse loginResponse = loginMap.get(loginId);

            // 赋值登录用户
            loginResponse.user = user;

            // 唤醒登录等待线程
            loginResponse.latch.countDown();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("loginId", loginId);
        result.put("user", user);
        return result;
    }

    /**
     * 等待二维码扫码结果的长连接
     *
     * @param loginId
     * @param session
     * @return
     */
    @GetMapping("login/getResponse/{loginId}")
    public @ResponseBody Map<String, Object> getResponse(@PathVariable String loginId, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        result.put("loginId", loginId);
        try {
            LoginResponse loginResponse = null;
            if (!loginMap.containsKey(loginId)) {
                loginResponse = new LoginResponse();
                loginMap.put(loginId, loginResponse);
            } else
                loginResponse = loginMap.get(loginId);

            // 第一次判断
            // 判断是否登录,如果已登录则写入session
            if (loginResponse.user != null) {
                session.setAttribute(WebSecurityConfig.SESSION_KEY, loginResponse.user);
                result.put("success", true);
                return result;
            }

            if (loginResponse.latch == null) {
                loginResponse.latch = new CountDownLatch(1);
            }
            try {
                // 线程等待
                loginResponse.latch.await(5, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 再次判断
            // 判断是否登录,如果已登录则写入session
            if (loginResponse.user != null) {
                session.setAttribute(WebSecurityConfig.SESSION_KEY, loginResponse.user);
                result.put("success", true);
                return result;
            }

            result.put("success", false);
            return result;
        } finally {
            // 移除登录请求
            if (loginMap.containsKey(loginId))
                loginMap.remove(loginId);
        }
    }

    /**
     * 生成base64二维码
     *
     * @param content
     * @return
     * @throws Exception
     */
    private String createQrCode(String content) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ImageIO.write(image, "JPG", out);
            return Base64.encodeBase64String(out.toByteArray());
        }
    }
}
