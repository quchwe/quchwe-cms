package com.quchwe.gd.cms.downLoadFile.download;

import java.util.List;

public class GGG
{
    
    public static void main(String[] args)
    {
        String filename = "1.txt";
        StringBuffer sb = new StringBuffer();
        List<String> contendIds = ReadWriteFile.readTxtFile(filename);
        for (String id : contendIds)
        {
            sb.append("'").append(id).append("',");
        }
        System.out.println(sb.toString());
    }
    
}
