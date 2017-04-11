package com.quchwe.gd.cms.downLoadFile.download;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteFile
{
    private static final Logger logger = Logger.getLogger(ReadWriteFile.class);
    
    /**
     * <从文件中读取数据>
     * 
     * @param filePath
     * @return
     */
    public static List<String> readTxtFile(String filePath)
    {
        List<String> contentIds = new ArrayList<String>();
        try
        {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    contentIds.add(lineTxt.trim());
                }
                read.close();
            }
            else
            {
                logger.error("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            logger.error("读取文件内容出 ,e:{}", e);
        }
        return contentIds;
    }
    
    /**
     * 创建文件
     * 
     * @param fileName
     * @return
     */
    public static boolean createFile(File fileName)
        throws Exception
    {
        boolean flag = false;
        try
        {
            if (!fileName.exists())
            {
                fileName.createNewFile();
                flag = true;
            }
        }
        catch (Exception e)
        {
            logger.error("create file error e:{}", e);
        }
        return flag;
    }
    
    /**
     * <追加内容到txt>
     * 
     * @param content
     * @param fileName
     * @return
     */
    public static boolean writeTxtFile(String content, File fileName)
    {
        boolean flag = false;
        FileOutputStream o = null;
        try
        {
            content = content + "\n";
            o = new FileOutputStream(fileName, true);
            o.write(content.getBytes("UTF-8"));
            o.close();
            flag = true;
        }
        catch (Exception e)
        {
            logger.error("write file error e:{}", e);
        }
        return flag;
    }
    
}
