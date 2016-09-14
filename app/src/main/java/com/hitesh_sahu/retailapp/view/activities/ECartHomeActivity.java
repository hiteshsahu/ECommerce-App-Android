package com.hitesh_sahu.retailapp.view.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.domain.helper.Connectivity;
import com.hitesh_sahu.retailapp.model.GlobaDataHolder;
import com.hitesh_sahu.retailapp.model.entities.Money;
import com.hitesh_sahu.retailapp.model.entities.Product;
import com.hitesh_sahu.retailapp.util.PreferenceHelper;
import com.hitesh_sahu.retailapp.util.TinyDB;
import com.hitesh_sahu.retailapp.util.Utils;
import com.hitesh_sahu.retailapp.util.Utils.AnimationType;
import com.hitesh_sahu.retailapp.view.fragment.HomeFragment;
import com.hitesh_sahu.retailapp.view.fragment.WhatsNewDialog;
import com.wang.avi.AVLoadingIndicatorView;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ECartHomeActivity extends AppCompatActivity {

	private static final String TAG = ECartHomeActivity.class.getSimpleName();

	private int itemCount = 0;
	private BigDecimal checkoutAmount = new BigDecimal(BigInteger.ZERO);
	private DrawerLayout mDrawerLayout;

	private TextView checkOutAmount, itemCountTextView;
	private TextView offerBanner;
	private AVLoadingIndicatorView progressBar;

	private NavigationView mNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ecart);

		GlobaDataHolder.getGlobaDataHolder().setShoppingList(
				new TinyDB(getApplicationContext()).getListObject(
						PreferenceHelper.MY_CART_LIST_LOCAL, Product.class));
		
		itemCount = GlobaDataHolder.getGlobaDataHolder().getShoppingList()
				.size();

	//	makeFakeVolleyJsonArrayRequest();

		offerBanner = ((TextView) findViewById(R.id.new_offers_banner));

		itemCountTextView = (TextView) findViewById(R.id.item_count);
		itemCountTextView.setSelected(true);
		itemCountTextView.setText(String.valueOf(itemCount));

		checkOutAmount = (TextView) findViewById(R.id.checkout_amount);
		checkOutAmount.setSelected(true);
		checkOutAmount.setText(Money.rupees(checkoutAmount).toString());
		offerBanner.setSelected(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);
		mNavigationView = (NavigationView) findViewById(R.id.nav_view);

		progressBar = (AVLoadingIndicatorView) findViewById(R.id.loading_bar);

		checkOutAmount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Utils.vibrate(getApplicationContext());

				Utils.switchContent(R.id.frag_container,
						Utils.SHOPPING_LIST_TAG, ECartHomeActivity.this,
						AnimationType.SLIDE_UP);

			}
		});
		
	
		if (itemCount != 0) {
			for (Product product : GlobaDataHolder.getGlobaDataHolder()
					.getShoppingList()) {

				updateCheckOutAmount(
						BigDecimal.valueOf(Long.valueOf(product.getSellMRP())),
						true);
			}
		}

		findViewById(R.id.item_counter).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						Utils.vibrate(getApplicationContext());
						Utils.switchContent(R.id.frag_container,
								Utils.SHOPPING_LIST_TAG,
								ECartHomeActivity.this, AnimationType.SLIDE_UP);

					}
				});

		findViewById(R.id.checkout_envelop).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						Utils.vibrate(getApplicationContext());

						Utils.switchContent(R.id.frag_container,
								Utils.SHOPPING_LIST_TAG,
								ECartHomeActivity.this, AnimationType.SLIDE_UP);

					}
				});

		Utils.switchFragmentWithAnimation(R.id.frag_container,
				new HomeFragment(), this, Utils.HOME_FRAGMENT,
				AnimationType.SLIDE_UP);
		
		toggleBannerVisibility();

		mNavigationView
				.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {

						menuItem.setChecked(true);
						switch (menuItem.getItemId()) {
						case R.id.home:

							mDrawerLayout.closeDrawers();

							Utils.switchContent(R.id.frag_container,
									Utils.HOME_FRAGMENT,
									ECartHomeActivity.this,
									AnimationType.SLIDE_LEFT);

							return true;

						case R.id.my_cart:

							mDrawerLayout.closeDrawers();

							Utils.switchContent(R.id.frag_container,
									Utils.SHOPPING_LIST_TAG,
									ECartHomeActivity.this,
									AnimationType.SLIDE_LEFT);
							return true;

						case R.id.contact_us:

							mDrawerLayout.closeDrawers();

							Utils.switchContent(R.id.frag_container,
									Utils.CONTACT_US_FRAGMENT,
									ECartHomeActivity.this,
									AnimationType.SLIDE_LEFT);
							return true;

						case R.id.settings:

							mDrawerLayout.closeDrawers();

							Utils.switchContent(R.id.frag_container,
									Utils.SETTINGS_FRAGMENT_TAG,
									ECartHomeActivity.this,
									AnimationType.SLIDE_LEFT);
							return true;
						default:
							return true;
						}
					}
				});

	}

	public AVLoadingIndicatorView getProgressBar() {
		return progressBar;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void updateItemCount(boolean ifIncrement) {
		if (ifIncrement) {
			itemCount++;
			itemCountTextView.setText(String.valueOf(itemCount));

		} else {
			itemCountTextView.setText(String.valueOf(itemCount <= 0 ? 0
					: --itemCount));
		}

		toggleBannerVisibility();
	}

	public void updateCheckOutAmount(BigDecimal amount, boolean increment) {

		if (increment) {
			checkoutAmount = checkoutAmount.add(amount);
		} else {
			if (checkoutAmount.signum() == 1)
				checkoutAmount = checkoutAmount.subtract(amount);
		}

		checkOutAmount.setText(Money.rupees(checkoutAmount).toString());
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Store Shopping Cart in DB
		new TinyDB(getApplicationContext()).putListObject(
				PreferenceHelper.MY_CART_LIST_LOCAL, GlobaDataHolder
						.getGlobaDataHolder().getShoppingList());
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Show Offline Error Message
		if (!Connectivity.isConnected(getApplicationContext())) {
			final Dialog dialog = new Dialog(ECartHomeActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.connection_dialog);
			Button dialogButton = (Button) dialog
					.findViewById(R.id.dialogButtonOK);

			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();

				}
			});

			dialog.show();
		}

		// Show Whats New Features If Requires
		new WhatsNewDialog(this);
	}

	/*
	 * Toggles Between Offer Banner and Checkout Amount. If Cart is Empty SHow
	 * Banner else display total amount and item count
	 */
	public void toggleBannerVisibility() {
		if (itemCount == 0) {

			findViewById(R.id.checkout_item_root).setVisibility(View.GONE);
			findViewById(R.id.new_offers_banner).setVisibility(View.VISIBLE);

		} else {
			findViewById(R.id.checkout_item_root).setVisibility(View.VISIBLE);
			findViewById(R.id.new_offers_banner).setVisibility(View.GONE);
		}
	}

	/*
	 * get total checkout amount
	 */
	public BigDecimal getCheckoutAmount() {
		return checkoutAmount;
	}

	/*
	 * Makes fake Volley request by adding request in fake Volley Queue and
	 * return mock JSON String plese visit
	 * com.hitesh_sahu.retailapp.domain.mock.FakeHttpStack and
	 * FakeRequestQueue queu
	 */
//	private void makeFakeVolleyJsonArrayRequest() {
//
//		JsonArrayRequest req = new JsonArrayRequest(
//				NetworkConstants.URL_GET_ALL_CATEGORY,
//				new Response.Listener<JSONArray>() {
//					@Override
//					public void onResponse(JSONArray response) {
//						Log.d(TAG,
//
//						response.toString());
//
////						Toast.makeText(getApplicationContext(),
////								"Volley Fake response", Toast.LENGTH_SHORT)
////								.show();
//
//						// hidepDialog();
//					}
//				}, new Response.ErrorListener() {
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						VolleyLog.d(TAG, "Error: " + error.getMessage());
//
//						Log.e(TAG,
//								"------------------------" + error.getMessage());
////						Toast.makeText(getApplicationContext(),
////								error.getMessage(), Toast.LENGTH_SHORT).show();
//					}
//				});
//
//		// Adding request to request queue
//		AppController.getInstance().addToFakeRequestQueue(req);
//	}

	/*
	 * Get Number of items in cart
	 */
	public int getItemCount() {
		return itemCount;
	}

	/*
	 * Get Navigation drawer
	 */
	public DrawerLayout getmDrawerLayout() {
		return mDrawerLayout;
	}

}
