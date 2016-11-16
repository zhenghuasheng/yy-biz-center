package com.etong.pt.constants;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public class EnumConstant {
    // 订单状态
    public static enum OrderStatus {
        REVOKE(0,"作废"),
        INQUIRE(1, "询价"), // 询价
        OFFER(2,"已报价"),
        PAY(3, "已支付"),// 支付=待签约
        ORERDUE(4, "过期"),
        PREPICKUP(5,"待提车"),
        OVER(6,"已完成");

        private int value;
        private String desc;

        OrderStatus(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public static enum VehicleType {
        HOT_BRAND(0, "热门品牌"), // 热门品牌
        HOT_CARSET(1, "热门车型"); // 热门车型

        private int value;
        private String desc;

        VehicleType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public static enum DataLevel {
        BRAND(0, "品牌"),
        MANU(1, "厂商"),
        CARSET(2, "车型");

        private int value;
        private String desc;

        DataLevel(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public static enum PriceRange {
        One(0, "5W以下", new Double[]{0d, 5d}),
        Two(1, "5-8W",  new Double[]{5d, 8d}),
        Three(2, "8-10W",  new Double[]{8d, 10d}),
        Fore(3, "10-15W",  new Double[]{10d, 15d}),
        Five(4, "15-20W",  new Double[]{15d, 20d}),
        Six(5, "20-25W",  new Double[]{20d, 25d}),
        Seven(6, "25-35W",  new Double[]{25d, 35d}),
        Eight(7, "35-50W",  new Double[]{35d, 50d}),
        Nine(8, "50-70W",  new Double[]{50d, 70d}),
        Ten(9, "70-100W",  new Double[]{70d, 100d}),
        Eleven(10, "100W以上",  new Double[]{100d, null});

        private int value;
        private String desc;
        private Double[] range;

        PriceRange(int value, String desc, Double[] range) {
            this.value = value;
            this.desc = desc;
            this.range = range;
        }

        private PriceRange(int value) {
            this.value = value;
        }

        public static PriceRange valueOf(int value) {
            switch (value) {
                case 0:return One;
                case 1:return Two;
                case 2:return Three;
                case 3:return Fore;
                case 4:return Five;
                case 5:return Six;
                case 6:return Seven;
                case 7:return Eight;
                case 8:return Nine;
                case 9:return Ten;
                case 10:return Eleven;
                default:return null;
            }
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public Double[] getRange() {
            return range;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    /**活动类别*/
    public static enum ActType {
        TIME_LIMIT(0, "限时购"),
        PROMOTIONAL (1, "促销车");

        private int value;
        private String desc;

        ActType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public static enum VehicleLevel {
        BRAND(0, "品牌"),
        MANU(1, "厂商"),
        CARSET(2, "车型"),
        CAR(3, "车款");

        private int value;
        private String desc;

        VehicleLevel(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }
}
