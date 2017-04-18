package com.quchwe.gd.cms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2017/4/10 0010.
 */

public class FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);
    /**
     * 保存文件
     * @param path 路径
     * @param files 文件
     * @return 文件存储的路径地址，windows地址，windows文件分隔符为\,访问时需要替换为"/"
     */
    public static List<String> saveImage(String path, MultipartFile... files) {

        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile file : files) {

            if (file == null) {
                break;
            }
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(file.getInputStream());
                System.out.println(bis.available());
                File f = new File("D:/cms/images/" + path + "/");

                f.mkdirs();
                String fileName = EncryptionUtil.md5Encryption(file.getOriginalFilename() + System.currentTimeMillis());
                File image = new File(f, fileName);

                bos = new BufferedOutputStream(new FileOutputStream(image));
                byte[] bytes = new byte[2048];
                int len;
                while ((len = bis.read(bytes)) > -1) {
                    bos.write(bytes, 0, len);
                }
                bos.flush();
                log.info(path + "图片保存成功,图片保存在{}", image.getAbsolutePath());
                imagePaths.add(image.getAbsolutePath());
            } catch (IOException e) {

                e.printStackTrace();

                log.error("图片保存失败,{}", e.getMessage());
                return null;
            } finally {
                CloseIo.closeIo(bis, bos);
            }
        }
        return imagePaths;
    }
}
