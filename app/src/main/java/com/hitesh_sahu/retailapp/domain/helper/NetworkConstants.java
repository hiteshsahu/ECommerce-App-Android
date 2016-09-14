package com.hitesh_sahu.retailapp.domain.helper;


// TODO: Auto-generated Javadoc

/**
 * The Interface NetworkConstants.
 */
public interface NetworkConstants {
    public boolean DEBUGABLE = true;

    /**
     * The Constant URL_BASE_URI.
     */
    // public static final String URL_BASE_URI =
    // "http://192.168.2.7:8080/Delivery2Home/";

    public static final String URL_BASE_URI = "http://delivery2home.com"
            + "/Delivery2Home/";

    /**
     * The Constant URL_GET_PRODUCTS_BY_CATEGORY.
     */
    public static final String URL_GET_ALL_CATEGORY = URL_BASE_URI
            + "categories";

    /**
     * The Constant URL_GET_PRODUCTS_BY_CATEGORY.
     */
    public static final String URL_GET_PRODUCTS_MAP = URL_BASE_URI
            + "productMap";

    /**
     * The Constant URL_GET_PRODUCTS_BY_CATEGORY.
     */
    public static final String URL_PLACE_ORDER = URL_BASE_URI + "insertOrder";

    public static final String URL_APPLY_COUPAN = URL_BASE_URI
            + "validateCoupan";


    // -------------------------
    // Products functionality
    /**
     * The Constant getProductByCategory.
     */
    // -------------------------
    public static final int GET_ALL_PRODUCT_BY_CATEGORY = 0;
    public static final int GET_ALL_PRODUCT = 1;
    public static final int GET_SHOPPING_LIST = 9;


}
