package com.example.taxidata.constant;

import java.util.HashMap;
import java.util.Map;

public class Algorithm {
    private Algorithm() {}

    public static final String WANG_ALGORITHM = "多元回归";

    public static final String GU_ALGORITHM = "最小二乘";

    public static final String LI_ALGORITHM = "神经网络";

    public static final Map<String,Integer> algorithm = new HashMap<>();

    static {
        algorithm.put("多元回归",0);
        algorithm.put("最小二乘",1);
        algorithm.put("神经网络",2);
    }
}
