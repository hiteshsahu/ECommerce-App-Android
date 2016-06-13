/**
 * 
 */
package com.serveroverload.retailapp.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * @author Hitesh
 *
 */
public class PreferenceHelper {

	private static PreferenceHelper preferenceHelperInstance = new PreferenceHelper();

	private static final String USER_LOGGED_IN = "isLoggedIn";
	public final static String FIRST_TIME = "FirstTime";
	public final static String WHATS_NEW_LAST_SHOWN = "whats_new_last_shown";
	public final static String SUBMIT_LOGS = "CrashLogs";
	public final static String PHONE_NUMBER = "ContactNumber";
	public final static String SAVE_DEATILS = "shouldStoreNumber";
	public static final String SAVE_ADDRESS = "shouldSaveAdd";
	public static final String ADDRESS = "Address";
	public static final String ADDRESS_ID = "AddID";
	public static final String ADD_HOUSE = "AddHouse";
	// public static final String ADD_BUILDING = "AddBuilding";
	public static final String ADD_STREET = "AddStreet";
	public static final String ADD_AREA = "AddArea";
	public static final String FIRST_NAME = "FirstName";
	public static final String LAST_NAME = "lastName";
	public static final String EMAIL_ID = "EmailID";

	// Handle Local Caching of data for responsiveness
	public static final String PRODUCT_CATEGORY_RESPONSE_JSON = "CategoryResponse";
	public static final String ALL_PRODUCT_LIST_RESPONSE_JSON = "AllProductsResponse";
	public static final String MY_ADDRESS_RESPONSE_JSON = "AllProductsResponse";
	public static final String MY_ORDER_RESPONSE_JSON = "AllProductsResponse";
	public static final String HOT_OFFER_RESPONSE_JSON = "AllProductsResponse";
	public static final String MY_CART_LIST_LOCAL = "MyCartItems";
	public static final String IMAGE_URL = "ProfilePic";

	/** The app context. */
	private static Context appContext;

	public static String LOGED_IN = USER_LOGGED_IN;

	private PreferenceHelper() {
	}

	public void init(Context context) {

		if (!getPrefernceHelperInstace().getBoolean(context,
				AppConstants.FIRST_TIME, false)) {

			// App
			setBoolean(context, AppConstants.FIRST_TIME, true);

			// Support
			setBoolean(context, AppConstants.SUBMIT_LOGS, true);

		}

	}

	public static PreferenceHelper getPrefernceHelperInstace() {

		return preferenceHelperInstance;
	}

	public void setBoolean(Context appContext, String key, Boolean value) {

		PreferenceManager.getDefaultSharedPreferences(appContext).edit()
				.putBoolean(key, value).apply();
	}

	public void setInteger(Context appContext, String key, int value) {

		PreferenceManager.getDefaultSharedPreferences(appContext).edit()
				.putInt(key, value).apply();
	}

	public void setFloat(Context appContext, String key, float value) {

		PreferenceManager.getDefaultSharedPreferences(appContext).edit()
				.putFloat(key, value).apply();
	}

	public void setString(Context appContext, String key, String value) {

		PreferenceManager.getDefaultSharedPreferences(appContext).edit()
				.putString(key, value).apply();
	}

	// To retrieve values from shared preferences:

	public boolean getBoolean(Context appContext, String key,
			Boolean defaultValue) {

		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getBoolean(key, defaultValue);
	}

	public int getInteger(Context appContext, String key, int defaultValue) {

		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getInt(key, defaultValue);
	}

	public float getString(Context appContext, String key, float defaultValue) {

		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getFloat(key, defaultValue);
	}

	public String getString(Context appContext, String key, String defaultValue) {

		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getString(key, defaultValue);
	}

	public void setUserLoggedIn(boolean UserLoggedIn,Context appContext) {

		setBoolean(appContext, USER_LOGGED_IN, UserLoggedIn);
	}

	public boolean isUserLoggedIn(Context appContext) {

		return getBoolean(appContext, USER_LOGGED_IN, false);
	}

}
