package com.quchwe.gd.cms.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by quchwe on 2017/4/10 0010.
 */
@Slf4j
public class CloseIo {
    public static void closeIo(Closeable ...closeables){
        for (Closeable io:closeables){
            if (io!=null){
                try {
                    io.close();
                } catch (IOException e) {
                    log.error("流关闭失败，{}",e.getMessage());
                }
            }
        }
    }
}
