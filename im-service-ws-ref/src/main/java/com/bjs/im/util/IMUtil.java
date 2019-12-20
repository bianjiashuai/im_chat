package com.bjs.im.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

/**
 * @Description 基础工具类.
 * @Date 2019-12-03 15:03:26
 * @Author BianJiashuai
 */
public class IMUtil {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 判断给定参数中是否有null对象.
     *
     * @param objects
     * @return
     */
    public static boolean hasNull(Object... objects) {
        for (Object obj : objects) {
            if (null == obj) {
                return true;
            }
        }
        return false;
    }

    /**
     * 随机生成ID.
     *
     * @return
     */
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    /**
     * 获取当前时间使用默认格式化.
     *
     * @return
     */
    public static String getDateStr() {
        return getDateStr(TIME_FORMATTER);
    }

    /**
     * 获取当前时间并格式化.
     *
     * @param formatter
     * @return
     */
    public static String getDateStr(DateTimeFormatter formatter) {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 根据指定名称获取输入信息.
     *
     * @param scanner
     * @param scannerName
     * @return
     */
    public static String scanner(Scanner scanner, String scannerName) {
        return scanner(scanner, scannerName, false);
    }

    /**
     * 根据指定名称获取输入信息.
     *
     * @param scanner
     * @param scannerName
     * @param isEmpty     是否可为空
     * @return
     */
    public static String scanner(Scanner scanner, String scannerName, boolean isEmpty) {
        while (true) {
            System.out.print("请输入" + scannerName + ": ");
            String line = scanner.nextLine();
            if (!isEmpty && (line == null || "".equals(line.trim()))) {
                System.err.println(scannerName + "不能为空, 请重新输入");
                continue;
            } else {
                return line;
            }
        }
    }

    private IMUtil() {
    }
}
