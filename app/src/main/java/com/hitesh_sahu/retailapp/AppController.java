package com.hitesh_sahu.retailapp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.hitesh_sahu.retailapp.util.PreferenceHelper;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(mailTo = "hiteshkumarsahu1990@gmail.com", customReportContent = {
		ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
		ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
		ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT }, mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		// The following line triggers the initialization of ACRA for crash Log Reposrting
		if (PreferenceHelper.getPrefernceHelperInstace().getBoolean(
				this, PreferenceHelper.SUBMIT_LOGS, true)) {
			ACRA.init(this);
		}

	}
	
	// Fake Volley queue for fake network calls

	public static synchronized AppController getInstance() {
		return mInstance;
	}


//	public RequestQueue getFakeRequestQueue() {
//		if (mRequestQueue == null) {
//			mRequestQueue = new FakeRequestQueue(getApplicationContext());
//		}
//		return mRequestQueue;
//	}
//
//	public <T> void addToRequestQueue(Request<T> req, String tag) {
//		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//		getFakeRequestQueue().add(req);
//	}
//
//	public <T> void addToFakeRequestQueue(Request<T> req) {
//		req.setTag(TAG);
//		getFakeRequestQueue().add(req);
//	}
//
//	public void cancelPendingRequests(Object tag) {
//		if (mRequestQueue != null) {
//			mRequestQueue.cancelAll(tag);
//		}
//	}
}