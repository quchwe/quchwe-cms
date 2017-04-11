package com.quchwe.gd.cms.downLoadFile.download;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class FileDownLoad {
    private static final Logger logger = Logger.getLogger(FileDownLoad.class);

    /**
     * <获取url访问返回值>
     *
     * @param path
     * @return
     */
    public static int getStatusCode(String path) {
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(5 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            return conn.getResponseCode();
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException ", e);
        } catch (IOException e) {
            logger.error("IOException  ", e);
        }
        return 0;

    }

    public static boolean wget(String url, String fileName, String savepath) {
        try {
            String cmdString = "wget -c " + url + " -O  " + savepath + fileName;
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(cmdString);
            p.waitFor();
            return true;
        } catch (IOException e) {
            logger.error("catch exception e:{}", e);
            return false;
        } catch (InterruptedException e) {
            logger.error("catch exception e:{}", e);
            return false;
        }
    }

    /**
     * <下载>
     *
     * @param staticDir
     * @param staticIp
     * @param programPlayUrl
     * @param programDownLoadUrl
     * @return
     */
    private static boolean toDownLoad(String staticDir, String staticIp, String programPlayUrl,
                                      String programDownLoadUrl) {
        String filename = getFileName(programDownLoadUrl);
        String path = getFileDir(programPlayUrl);

        // 下载目录替换 programDownLoadUrl 的域名为 staticIp
        String downUrl = staticIp + getFileDir(programDownLoadUrl) + getFileName(programDownLoadUrl);
        try {
            if (200 == getStatusCode(downUrl)) {
                wget(downUrl, filename, path);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(" catch exception when download file e:{}", e);
            return false;
        }
    }

    /**
     * <获取url存放路径>
     *
     * @param paramString
     * @return
     */
    private static String getFileDir(String paramString) {
        int i = 0;
        int j = 1;
        while ((i < 3) && (j > 0)) {
            j = paramString.indexOf("/", j + 1);
            if (j <= 0) {
                continue;
            }
            i++;
        }
        if ((j > 0) && (i == 3)) {
            int k = paramString.lastIndexOf("/");
            if (k > 0) {
                return paramString.substring(j + 1, k + 1);
            }
            return "";
        }
        return "";
    }

    private static String getFileName(String paramString) {
        int i = paramString.lastIndexOf("/");
        if (i > 0) {
            return paramString.substring(i + 1);
        }
        return paramString;
    }

    public static void main(String[] args) {
        String contenIds_file = "/data/id/";
        String result_file = "/data/result.txt";

        String staticDir = "/storage01/ysten/";
        String staticIp = "http://223.82.250.219:81/";
        ConnectionPool p = new ConnectionPool();
        ResultSet reSet;
        Connection con = p.getConnection();

        // <2> 创建存储结果的文件
        File resultFile = new File(result_file);
        try {
            ReadWriteFile.createFile(resultFile);
        } catch (Exception e) {
            logger.error("crate result_file file error e:{}", e);
        }
        for (int i = 1; i <= 250; i++) {
            // <1> 读取 txt文件 获取contenIds
            String filename = contenIds_file + +i + "_id.txt";
            List<String> contendIds = ReadWriteFile.readTxtFile(filename);
            if (!contendIds.isEmpty()) {
                int j = 1;
                for (String contentId : contendIds) {
                    // <3> 根据contentId 获取节目id ,playUrl
                    String sql =
                            "SELECT b.program_id,m.media_id,m.play_url,b.play_url,b.down_url "
                                    + " FROM  bss_media_url m,  `bss_program_bitrate_record` b "
                                    + " where m.play_url LIKE '%Contentid=" + contentId + "' AND m.media_id=b.program_rate_id ";
                    PreparedStatement pStemt = null;
                    try {
                        pStemt = con.prepareStatement(sql);
                        reSet = pStemt.executeQuery();
                        logger.info("search sql   begin contentId = " + contentId);
                        boolean searchResultIsNull = true;
                        while (reSet.next()) {
                            searchResultIsNull = false;
                            Long programId = reSet.getLong(1);
                            if (null == programId) {
                                logger.info("  programId is null, contentId = " + contentId);
                                // <4> 存储contentId 到result.txt
                                ReadWriteFile.writeTxtFile(contentId.toString() + " " + " error=programId is null ",
                                        resultFile);
                                continue;
                            }
                            String programPlayUrl = reSet.getString(4);
                            String programDownLoadUrl = reSet.getString(5);
                            if (!programPlayUrl.equals("")) {

                                String dir = staticDir + getFileDir(programPlayUrl) + getFileName(programPlayUrl);
                                File file = new File(dir);
                                logger.info("validate file exists :" + file.exists());
                                // <5> 校验mediaUrl对应的playUrl 是否在服务器存在
                                if (!file.exists()) {
                                    logger.info("downLoad file ");
                                    // <6> 根据<5>的校验结果 如果不存在则 需重新下载文件到服务器
                                    boolean result =
                                            toDownLoad(staticDir, staticIp, programPlayUrl, programDownLoadUrl);
                                    if (!result) {
                                        // 下载文件失败 ,记录programId和 contentId 到失败的文件
                                        logger.info(" download file fail contentid = " + contentId);
                                        // <4> 存储失败的programId 到programId.txt
                                        ReadWriteFile.writeTxtFile(contentId + " " + programId.toString() + " "
                                                + "error = 下载失败 ", resultFile);
                                    } else {
                                        // 下载文件成功,记录programId 到成功的文件
                                        ReadWriteFile.writeTxtFile(contentId + " " + programId.toString() + " "
                                                + "success = 下载成功 ", resultFile);
                                    }
                                } else {
                                    // 源文件存在,不需要下载 ,记录programId 到成功的文件
                                    logger.info("file exists in SERVER contentId = " + contentId);
                                    ReadWriteFile.writeTxtFile(contentId + " " + programId.toString() + " "
                                            + "success = 文件在服务器已存在 ", resultFile);
                                }
                            } else {
                                // playUrl 是空的 记录programId和 contentId 到失败的文件
                                logger.info("playUrl is null contentId = " + contentId);
                                ReadWriteFile.writeTxtFile(contentId + " " + programId.toString() + " "
                                        + "error = playUrl 是空", resultFile);
                            }
                            break;
                        }
                        if (searchResultIsNull) {
                            logger.info("search result is null no programId and playUrl found, contentId = "
                                    + contentId);
                            // <5> 存储表中不存在的contentId 到contentId.txt
                            ReadWriteFile.writeTxtFile(contentId.toString() + " " + "error = 在表中无记录", resultFile);
                        }
                        logger.info("search sql end SUCCESS contendId =" + contentId);
                    } catch (SQLException e) {
                        logger.error("SQLException error :{}", e);
                    } catch (Exception e) {
                        logger.error("catch error :{}", e);
                    }
                    logger.info("第【" + j + "】个contentId处理完成");
                    j++;
                }
                logger.info("第【" + i + "】个文件下载完成");
            }
        }
    }
}
