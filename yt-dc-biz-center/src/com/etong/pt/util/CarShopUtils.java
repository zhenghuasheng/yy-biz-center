package com.etong.pt.util;

import com.etong.data.auto.vehicle.Vehicle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenlinyang on 2015/10/28.
 */
public class CarShopUtils {
    private final static String LETTER_UC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static List<Map> groupBrand(List<Vehicle> carSetInfoList) {
        List<Map> resultList = new ArrayList();
        char [] charArray = LETTER_UC.toCharArray();
        for(char ch : charArray) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("firstLetter", String.valueOf(ch));

            List<Vehicle> dataList = new ArrayList<Vehicle>();
            for(Vehicle carSetInfo : carSetInfoList) {

//                if (carSetInfo.getLetter()==null){
//                    continue;
//                }
                if(String.valueOf(ch).equals(carSetInfo.getLetter())) {
                    dataList.add(carSetInfo);
                }
            }
            if(dataList.size() > 0) {
                map.put("dataList", dataList);
                resultList.add(map);
            }
        }
        return resultList;
    }

    public static String trimPhoto(String photo) {
        if(photo == null) {
            return "";
        }
        int split = photo.indexOf('#');

        if (split == -1) {
            return photo;
        } else {
            return photo.substring(0, split);
        }
    }

    public static String rebuildColor(String color) {
        if(color == null) {
            return "[]";
        }
        String[] strs = color.split(",");
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            builder.append("[").append(str).append("]");
        }
        return builder.toString();
    }

    public static Double caculate(Double guidePrice, Double lessPrice) {
        BigDecimal value = new BigDecimal(guidePrice).multiply(new BigDecimal(10000)).subtract(new BigDecimal(lessPrice)).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
        return value.doubleValue();
    }
}
