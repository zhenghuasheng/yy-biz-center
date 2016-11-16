package com.etong.pt.dao.codegenerate;

import java.util.Random;

/**
 * Created by Administrator on 2015/7/28.
 */

public class RandomId {
    private Random random;
    private String table;

    public RandomId() {
        random = new Random();
        table = "0123456789";
    }

    public String randomId(Long id) {
        String ret = null;
        String num = String.format("%05d", id);
        int key = random.nextInt(10), seed = random.nextInt(100);
        Caesar caesar = new Caesar(table, seed);
        num = caesar.encode(key, num);
        ret = num + String.format("%01d", key)
                + String.format("%02d", seed);
        return ret;
    }
}
