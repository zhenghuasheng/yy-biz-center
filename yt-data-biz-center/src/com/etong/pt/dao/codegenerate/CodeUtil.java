package com.etong.pt.dao.codegenerate;

import java.util.Date;
import java.util.Random;


/**设置优惠券编码
 * hgqc+8位数字随机码+毫秒数后5位
 * Created by Administrator on 2015/6/2.
 */
public class CodeUtil {
    public static final int SN_ERROR_INDEX=3;
    public static String getCodeSN() {
        String no = "";
        int num[] = new int[8];
        int c = 0;
        for (int i = 0; i < 8; i++) {
            num[i] = new Random().nextInt(10);
            c = num[i];
            for (int j = 0; j < i; j++) {
                if (num[j] == c) {
                    i--;
                    break;
                }
            }
        }
        if (num.length > 0) {
            for (int i = 0; i < num.length; i++) {
                no += num[i];
            }
        }
        Long str=new Date().getTime();
        String string=str.toString();
        return no+string.substring(8);
    }
    public static String getOrderCodeSN(long id){
        RandomId r=new RandomId();
        String str=r.randomId(id);
        Long time=new Date().getTime();
        String timeStamp=time.toString().substring(8);
        return str+timeStamp;
    }
}
