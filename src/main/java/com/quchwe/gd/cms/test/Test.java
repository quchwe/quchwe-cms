package com.quchwe.gd.cms.test;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by YSten on 2017/5/7.
 */
public class Test {

    public static void main(String[] args) {
        try {
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
            System.out.println(Inet6Address.getLocalHost());
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
