package com.serveroverload.retailapp.network_layer;


// TODO: Auto-generated Javadoc
/**
 * The Interface NetworkConstants.
 */
public interface NetworkConstants {
	public boolean DEBUGABLE = true;

	/** The Constant URL_BASE_URI. */
	// public static final String URL_BASE_URI =
	// "http://192.168.2.7:8080/Delivery2Home/";

	public static final String URL_BASE_URI = "http://delivery2home.com"
			+ "/Delivery2Home/";

	/** The Constant URL_GET_PRODUCTS_BY_CATEGORY. */
	public static final String URL_GET_ALL_CATEGORY = URL_BASE_URI
			+ "categories";

	/** The Constant URL_GET_PRODUCTS_BY_CATEGORY. */
	public static final String URL_GET_PRODUCTS_MAP = URL_BASE_URI
			+ "productMap";

	/** The Constant URL_GET_PRODUCTS_BY_CATEGORY. */
	public static final String URL_PLACE_ORDER = URL_BASE_URI + "insertOrder";

	public static final String URL_APPLY_COUPAN = URL_BASE_URI
			+ "validateCoupan";

	public static final String URL_GET_ALL_ADDRESS = URL_BASE_URI + "address";

	public static final String URL_GET_ALL_ORDERS = URL_BASE_URI + "orders";

	public String URL_LOGIN_BY_OTP = URL_BASE_URI + "otp";

	// -------------------------
	// Products functionality
	/** The Constant getProductByCategory. */
	// -------------------------
	public static final int GET_ALL_PRODUCT_BY_CATEGORY = 0;
	public static final int GET_ALL_PRODUCT = 1;
	public static final int GET_ALL_CATEGORY = 2;
	public static final int GET_ALL_PRODUCT_BY_ID = 3;
	public static final int GET_ALL_CUSTOMERS = 4;
	public static final int GET_ALL_ORDRS_FOR_CUSTOMER = 5;
	public static final int GET_ALL_ADDRESS_FOR_CUSTOMER = 6;
	public static final int REQUEST_OTP = 7;
	public static final int GET_SHOPPING_LIST = 9;

	String GET_CATEGORY_RESPONSE = "{\"categories\": [{ \"categoryName\":\"Electronic\" , \"productDescription\":\"Electronic Goods\", \"productDiscount\":\"10\", \"productUrl\":\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeNSONF3fr9bZ6g0ztTAIPXPRCYN9vtKp1dXQB2UnBm8n5L34r\" },{ \"categoryName\":\"Furnitures\" , \"productDescription\":\"Wooden Furnitures\", \"productDiscount\":\"15\", \"productUrl\":\"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRaUR5_wzLgBOuNtkWjOxhgaYaPBm821Hb_71xTyQ-OdUd-ojMMvw\"}]\"}";

}
