package com.example.taxidata.constant;

import java.util.HashMap;
import java.util.Map;

public class Algorithm {
    private Algorithm() {}

    public static final String WANG_ALGORITHM = "王氏算法";                     //岭回归

    public static final String GU_ALGORITHM = "辜氏算法";                       //高斯回归

    public static final String LI_ALGORITHM = "李氏算法";                       //多元回归

    public static final Map<String,Integer> algorithm = new HashMap<>();

    static {
        algorithm.put("王氏算法",0);
        algorithm.put("辜氏算法",1);
        algorithm.put("李氏算法",2);
    }
}
