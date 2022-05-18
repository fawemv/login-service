package com.example.loginservice.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lang
 * @Desc
 * @data 2022/5/17 11:07
 **/
public class DataUtil {
    public static List<List<String>> createTestListStringHead() {
        // 模型上没有注解，表头数据动态传入
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> headCoulumn1 = new ArrayList<String>();
        List<String> headCoulumn2 = new ArrayList<String>();
        List<String> headCoulumn3 = new ArrayList<String>();
        List<String> headCoulumn4 = new ArrayList<String>();
        List<String> headCoulumn5 = new ArrayList<String>();
        List<String> headCoulumn6 = new ArrayList<String>();


        headCoulumn1.add("学号");
        headCoulumn2.add("姓名");
        headCoulumn3.add("性别");
        headCoulumn4.add("年级");
        headCoulumn5.add("学院");
        headCoulumn6.add("宿舍号");


        head.add(headCoulumn1);
        head.add(headCoulumn2);
        head.add(headCoulumn3);
        head.add(headCoulumn4);
        head.add(headCoulumn5);
        head.add(headCoulumn6);
        return head;
    }

}
