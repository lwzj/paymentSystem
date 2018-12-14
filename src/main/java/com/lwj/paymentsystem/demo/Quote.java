package com.lwj.paymentsystem.demo;

/**
 * @ClassName Quote
 * @Description TODO
 * @Author junliang
 * @Date 2018/12/14 2:49 PM
 * @Version 1.0
 **/
public class Quote {
    public Quote(String shopName, double price, Discount.Code discountCode) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }

    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;
    public static Quote parse(String s){
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName,price,discountCode);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }
}
