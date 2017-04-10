package com.quchwe.gd.cms.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2017/4/10 0010.
 */
@Slf4j
public class FileUtils {

    public static List<String> saveImage(String path, MultipartFile ...files){

        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile file :files){

            if (file==null){
                break;
            }
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(file.getInputStream());

                File f = new File("D:/cms/images/"+path+"createPair/");

                f.mkdirs();
                File image = new File(f, "app-"+path+"-" + System.currentTimeMillis() + "-" + "icon.png");
                image.createNewFile();
                bos = new BufferedOutputStream(new FileOutputStream(image));
                byte[] bytes = new byte[2048];
                int len;
                while ((len = bis.read(bytes)) > -1) {
                    bos.write(len);
                }
                bos.flush();
                CloseIo.closeIo(bis,bos);
                log.info(path+"图片保存成功,图片保存在{}",image.getAbsolutePath());
                imagePaths.add(image.getAbsolutePath());
            } catch (IOException e) {
                CloseIo.closeIo(bis,bos);
                e.printStackTrace();

                log.error("图片保存失败,{}", e.getMessage());
                return null;

            }
        }
        return imagePaths;
    }
}
