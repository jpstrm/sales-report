package br.com.sales.helper;

import br.com.sales.config.SalesConfiguration;

public class Helper {

    final public static String SALES_MAN_TYPE = "001";
    final public static String CUSTOMER_TYPE = "002";
    final public static String SALES_TYPE = "003";

    private static String getHomePath(SalesConfiguration salesConfig) {
        return System.getProperty(salesConfig.getPath());
    }

    public static String getPathIn(SalesConfiguration salesConfig) {
        return getHomePath(salesConfig) + salesConfig.getPathIn();
    }

    public static String getPathOut(SalesConfiguration salesConfig) {
        return getHomePath(salesConfig) + salesConfig.getPathOut();
    }

}
