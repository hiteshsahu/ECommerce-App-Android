/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.util;

import android.content.Context;
import android.content.CursorLoader;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.view.fragment.ContactUsFragment;
import com.hitesh_sahu.retailapp.view.fragment.HomeFragment;
import com.hitesh_sahu.retailapp.view.fragment.MyCartFragment;
import com.hitesh_sahu.retailapp.view.fragment.ProductOverviewFragment;
import com.hitesh_sahu.retailapp.view.fragment.SettingsFragment;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static final String ATTRIBUTE_TTF_KEY = "ttf_name";

    public static final String ATTRIBUTE_SCHEMA = "http://schemas.android.com/apk/lib/com.hitesh_sahu.retailapp.util";

    public final static String SHOPPING_LIST_TAG = "SHoppingListFragment";
    public static final String PRODUCT_OVERVIEW_FRAGMENT_TAG = "ProductOverView";
    public static final String MY_CART_FRAGMENT = "MyCartFragment";
    public static final String MY_ORDERS_FRAGMENT = "MYOrdersFragment";
    public static final String HOME_FRAGMENT = "HomeFragment";
    public static final String SEARCH_FRAGMENT_TAG = "SearchFragment";
    public static final String SETTINGS_FRAGMENT_TAG = "SettingsFragment";
    public static final String OTP_LOGIN_TAG = "OTPLogingFragment";
    public static final String CONTACT_US_FRAGMENT = "ContactUs";
    private static final String PREFERENCES_FILE = "materialsample_settings";
    private static String CURRENT_TAG = null;
    private static Map<String, Typeface> TYPEFACE = new HashMap<String, Typeface>();

    public static int getToolbarHeight(Context context) {
        int height = (int) context.getResources().getDimension(
                R.dimen.abc_action_bar_default_height_material);
        return height;
    }

    public static int getStatusBarHeight(Context context) {
        int height = (int) context.getResources().getDimension(
                R.dimen.statusbar_size);
        return height;
    }
//
//	public static Map<String, Integer> buildEffectMap(Context context) {
//		Map<String, Integer> effectMap = new LinkedHashMap<>();
//		int i = 0;
//		String[] effects = context.getResources().getStringArray(
//				R.array.jazzy_effects);
//		for (String effect : effects) {
//			effectMap.put(effect, i++);
//		}
//		return effectMap;
//	}

//	public static PageTransformer currentPageTransformer(Context context) {
//		PageTransformer transformer = null;
//
//		switch (PreferenceHelper.getPrefernceHelperInstace().getInteger(
//				context, AppConstants.VIEW_PAGER_ANIME, 13)) {
//		case AppConstants.STANDARD:
//
//			transformer = new DefaultTransformer();
//
//			break;
//
//		case AppConstants.TABLET:
//
//			transformer = new TabletTransformer();
//
//			break;
//
//		case AppConstants.CUBEIN:
//
//			transformer = new CubeInTransformer();
//
//			break;
//
//		case AppConstants.CUBEOUT:
//
//			transformer = new CubeOutTransformer();
//
//			break;
//
//		case AppConstants.FLIPVERTICAL:
//
//			transformer = new FlipVerticalTransformer();
//
//			break;
//
//		case AppConstants.FLIPHORIZONTAL:
//
//			transformer = new FlipHorizontalTransformer();
//
//			break;
//
//		case AppConstants.STACK:
//
//			transformer = new StackTransformer();
//
//			break;
//
//		case AppConstants.ZOOMIN:
//
//			transformer = new ZoomInTransformer();
//
//			break;
//
//		case AppConstants.ZOOMOUT:
//
//			transformer = new ZoomOutTranformer();
//
//			break;
//
//		case AppConstants.ROTATEUP:
//
//			transformer = new RotateUpTransformer();
//
//			break;
//
//		case AppConstants.ROTATEDOWN:
//
//			transformer = new RotateDownTransformer();
//
//			break;
//
//		case AppConstants.ACCORDION:
//
//			transformer = new AccordionTransformer();
//
//			break;
//
//		default:
//
//			transformer = new CubeOutTransformer();
//			break;
//		}
//		return transformer;
//	}

    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static String getRealPathFromURI(Uri contentUri, Context mContext) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mContext, contentUri, proj,
                null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    /**
     * Convert milliseconds into time hh:mm:ss
     *
     * @param milliseconds
     * @return time in String
     */
    public static String getDuration(long milliseconds) {
        long sec = (milliseconds / 1000) % 60;
        long min = (milliseconds / (60 * 1000)) % 60;
        long hour = milliseconds / (60 * 60 * 1000);

        String s = (sec < 10) ? "0" + sec : "" + sec;
        String m = (min < 10) ? "0" + min : "" + min;
        String h = "" + hour;

        String time = "";
        if (hour > 0) {
            time = h + ":" + m + ":" + s;
        } else {
            time = m + ":" + s;
        }
        return time;
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static void switchFragmentWithAnimation(int id, Fragment fragment,
                                                   FragmentActivity activity, String TAG, AnimationType transitionStyle) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if (transitionStyle != null) {
            switch (transitionStyle) {
                case SLIDE_DOWN:

                    // Exit from down
                    fragmentTransaction.setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down);

                    break;

                case SLIDE_UP:

                    // Enter from Up
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,
                            R.anim.slide_out_up);

                    break;

                case SLIDE_LEFT:

                    // Enter from left
                    fragmentTransaction.setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_out_left);

                    break;

                // Enter from right
                case SLIDE_RIGHT:
                    fragmentTransaction.setCustomAnimations(R.anim.slide_right,
                            R.anim.slide_out_right);

                    break;

                case FADE_IN:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.fade_out);

                case FADE_OUT:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.donot_move);

                    break;

                case SLIDE_IN_SLIDE_OUT:

                    fragmentTransaction.setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_out_left);

                    break;

                default:
                    break;
            }
        }

        CURRENT_TAG = TAG;

        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }

    public static void switchContent(int id, String TAG,
                                     FragmentActivity baseActivity, AnimationType transitionStyle) {

        Fragment fragmentToReplace = null;

        FragmentManager fragmentManager = baseActivity
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // If our current fragment is null, or the new fragment is different, we
        // need to change our current fragment
        if (CURRENT_TAG == null || !TAG.equals(CURRENT_TAG)) {

            if (transitionStyle != null) {
                switch (transitionStyle) {
                    case SLIDE_DOWN:
                        // Exit from down
                        transaction.setCustomAnimations(R.anim.slide_up,
                                R.anim.slide_down);

                        break;
                    case SLIDE_UP:
                        // Enter from Up
                        transaction.setCustomAnimations(R.anim.slide_in_up,
                                R.anim.slide_out_up);
                        break;
                    case SLIDE_LEFT:
                        // Enter from left
                        transaction.setCustomAnimations(R.anim.slide_left,
                                R.anim.slide_out_left);
                        break;
                    // Enter from right
                    case SLIDE_RIGHT:
                        transaction.setCustomAnimations(R.anim.slide_right,
                                R.anim.slide_out_right);
                        break;
                    case FADE_IN:
                        transaction.setCustomAnimations(R.anim.fade_in,
                                R.anim.fade_out);
                    case FADE_OUT:
                        transaction.setCustomAnimations(R.anim.fade_in,
                                R.anim.donot_move);
                        break;
                    case SLIDE_IN_SLIDE_OUT:
                        transaction.setCustomAnimations(R.anim.slide_left,
                                R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }
            }

            // Try to find the fragment we are switching to
            Fragment fragment = fragmentManager.findFragmentByTag(TAG);

            // If the new fragment can't be found in the manager, create a new
            // one
            if (fragment == null) {

                if (TAG.equals(HOME_FRAGMENT)) {
                    fragmentToReplace = new HomeFragment();
                } else if (TAG.equals(SHOPPING_LIST_TAG)) {
                    fragmentToReplace = new MyCartFragment();
                } else if (TAG.equals(SETTINGS_FRAGMENT_TAG)) {
                    fragmentToReplace = new SettingsFragment();
                } else if (TAG.equals(CONTACT_US_FRAGMENT)) {
                    fragmentToReplace = new ContactUsFragment();
                } else if (TAG.equals(PRODUCT_OVERVIEW_FRAGMENT_TAG)) {
                    fragmentToReplace = new ProductOverviewFragment();
                } else if (TAG.equals(SHOPPING_LIST_TAG)) {
                    fragmentToReplace = new MyCartFragment();
                }

            } else

            {
                if (TAG.equals(HOME_FRAGMENT)) {
                    fragmentToReplace = (HomeFragment) fragment;
                } else if (TAG.equals(SHOPPING_LIST_TAG)) {
                    fragmentToReplace = (MyCartFragment) fragment;
                } else if (TAG.equals(PRODUCT_OVERVIEW_FRAGMENT_TAG)) {
                    fragmentToReplace = (ProductOverviewFragment) fragment;
                } else if (TAG.equals(SETTINGS_FRAGMENT_TAG)) {
                    fragmentToReplace = (SettingsFragment) fragment;
                } else if (TAG.equals(CONTACT_US_FRAGMENT)) {
                    fragmentToReplace = (ContactUsFragment) fragment;
                }
            }

            CURRENT_TAG = TAG;

            // Replace our current fragment with the one we are changing to
            transaction.replace(id, fragmentToReplace, TAG);
            transaction.commit();

        } else

        {
            // Do nothing since we are already on the fragment being changed to
        }
    }

    public static void vibrate(Context context) {
        // Get instance of Vibrator from current Context and Vibrate for 400
        // milliseconds
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE))
                .vibrate(100);
    }

    public static String getVersion(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return String.valueOf(pInfo.versionCode) + " " + pInfo.versionName;

        } catch (NameNotFoundException e) {
            return "1.0.1";
        }
    }

    public static Typeface getFonts(Context context, String fontName) {
        Typeface typeface = TYPEFACE.get(fontName);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "font/"
                    + fontName);
            TYPEFACE.put(fontName, typeface);
        }
        return typeface;
    }

    public enum AnimationType {
        SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN, FADE_IN, SLIDE_IN_SLIDE_OUT, FADE_OUT
    }

}
