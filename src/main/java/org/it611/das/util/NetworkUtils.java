package org.it611.das.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils {

    private static final String DEFAULT_HOST_IP = "10.10.10.10";

    /**
     *  获取当前进程的PID
     */
    public static String getPid(){
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    /**
     *  获取当前进程的主机IP地址
     */
    public static String getHostIP() throws UnknownHostException {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return DEFAULT_HOST_IP;
        }
    }
}
