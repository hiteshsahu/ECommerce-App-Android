/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.domain.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hitesh_sahu.retailapp.domain.mock.FakeWebServer;
import com.hitesh_sahu.retailapp.model.CenterRepository;
import com.hitesh_sahu.retailapp.view.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.view.adapter.ProductsInCategoryPagerAdapter;
import com.hitesh_sahu.retailapp.view.fragment.ProductListFragment;

import java.util.Set;

/**
 * The Class ImageLoaderTask.
 */
public class ProductLoaderTask extends AsyncTask<String, Void, Void> {

    private Context context;
    private ViewPager viewPager;
    private TabLayout tabs;
    private RecyclerView recyclerView;

    public ProductLoaderTask(RecyclerView listView, Context context,
                             ViewPager viewpager, TabLayout tabs) {

        this.viewPager = viewpager;
        this.tabs = tabs;
        this.context = context;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (null != ((ECartHomeActivity) context).getProgressBar())
            ((ECartHomeActivity) context).getProgressBar().setVisibility(
                    View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (null != ((ECartHomeActivity) context).getProgressBar())
            ((ECartHomeActivity) context).getProgressBar().setVisibility(
                    View.GONE);

        setUpUi();

    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FakeWebServer.getFakeWebServer().getAllElectronics();

        return null;
    }

    private void setUpUi() {

        setupViewPager();

        // bitmap = BitmapFactory
        // .decodeResource(getResources(), R.drawable.header);
        //
        // Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        // @SuppressWarnings("ResourceType")
        // @Override
        // public void onGenerated(Palette palette) {
        //
        // int vibrantColor = palette.getVibrantColor(R.color.primary_500);
        // int vibrantDarkColor = palette
        // .getDarkVibrantColor(R.color.primary_700);
        // collapsingToolbarLayout.setContentScrimColor(vibrantColor);
        // collapsingToolbarLayout
        // .setStatusBarScrimColor(vibrantDarkColor);
        // }
        // });

        // tabLayout
        // .setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        // @Override
        // public void onTabSelected(TabLayout.Tab tab) {
        //
        // viewPager.setCurrentItem(tab.getPosition());
        //
        // switch (tab.getPosition()) {
        // case 0:
        //
        // header.setImageResource(R.drawable.header);
        //
        // bitmap = BitmapFactory.decodeResource(
        // getResources(), R.drawable.header);
        //
        // Palette.from(bitmap).generate(
        // new Palette.PaletteAsyncListener() {
        // @SuppressWarnings("ResourceType")
        // @Override
        // public void onGenerated(Palette palette) {
        //
        // int vibrantColor = palette
        // .getVibrantColor(R.color.primary_500);
        // int vibrantDarkColor = palette
        // .getDarkVibrantColor(R.color.primary_700);
        // collapsingToolbarLayout
        // .setContentScrimColor(vibrantColor);
        // collapsingToolbarLayout
        // .setStatusBarScrimColor(vibrantDarkColor);
        // }
        // });
        // break;
        // case 1:
        //
        // header.setImageResource(R.drawable.header_1);
        //
        // bitmap = BitmapFactory.decodeResource(
        // getResources(), R.drawable.header_1);
        //
        // Palette.from(bitmap).generate(
        // new Palette.PaletteAsyncListener() {
        // @SuppressWarnings("ResourceType")
        // @Override
        // public void onGenerated(Palette palette) {
        //
        // int vibrantColor = palette
        // .getVibrantColor(R.color.primary_500);
        // int vibrantDarkColor = palette
        // .getDarkVibrantColor(R.color.primary_700);
        // collapsingToolbarLayout
        // .setContentScrimColor(vibrantColor);
        // collapsingToolbarLayout
        // .setStatusBarScrimColor(vibrantDarkColor);
        // }
        // });
        //
        // break;
        // case 2:
        //
        // header.setImageResource(R.drawable.header2);
        //
        // Bitmap bitmap = BitmapFactory.decodeResource(
        // getResources(), R.drawable.header2);
        //
        // Palette.from(bitmap).generate(
        // new Palette.PaletteAsyncListener() {
        // @SuppressWarnings("ResourceType")
        // @Override
        // public void onGenerated(Palette palette) {
        //
        // int vibrantColor = palette
        // .getVibrantColor(R.color.primary_500);
        // int vibrantDarkColor = palette
        // .getDarkVibrantColor(R.color.primary_700);
        // collapsingToolbarLayout
        // .setContentScrimColor(vibrantColor);
        // collapsingToolbarLayout
        // .setStatusBarScrimColor(vibrantDarkColor);
        // }
        // });
        //
        // break;
        // }
        // }
        //
        // @Override
        // public void onTabUnselected(TabLayout.Tab tab) {
        //
        // }
        //
        // @Override
        // public void onTabReselected(TabLayout.Tab tab) {
        //
        // }
        // });

    }

    private void setupViewPager() {

        ProductsInCategoryPagerAdapter adapter = new ProductsInCategoryPagerAdapter(
                ((ECartHomeActivity) context).getSupportFragmentManager());

        Set<String> keys = CenterRepository.getCenterRepository().getMapOfProductsInCategory()
                .keySet();

        for (String string : keys) {

            adapter.addFrag(new ProductListFragment(string), string);

        }

        viewPager.setAdapter(adapter);

//		viewPager.setPageTransformer(true,
//				new );

        tabs.setupWithViewPager(viewPager);

    }

}