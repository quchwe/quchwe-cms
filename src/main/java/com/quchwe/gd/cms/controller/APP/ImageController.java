package com.quchwe.gd.cms.controller.APP;

import com.quchwe.gd.cms.utils.CloseIo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by quchwe on 2017/4/11 0011.
 * 随便写的一个图片下载，图片在本机电脑上
 */
@RestController
@RequestMapping(value = "/")
public class ImageController {

    /**
     *
     * @param directory 图片所在文件夹
     * @param imageName
     * @param request
     * @param response
     */
    @RequestMapping(value = "/image/get/{directory}/{imageName}")
    public void getImage(@PathVariable String directory, @PathVariable String imageName, HttpServletRequest request, HttpServletResponse response) {
        OutputStream out;
        BufferedInputStream bis = null;
        response.setContentType("image/gif");
        try {
            File file = new File("D:" + File.separator + "cms" + File.separator + "images" + File.separator
                    + directory + File.separator + imageName);
            System.out.println(file.getAbsolutePath());
            if (!file.exists()) {
                System.out.println("----");
                return;
            }
            bis = new BufferedInputStream(new FileInputStream(file));
            out = response.getOutputStream();


            byte[] b = new byte[bis.available()];
            bis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseIo.closeIo(bis);
        }
    }
}
