/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.view.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.domain.mining.AprioriFrequentItemsetGenerator;
import com.hitesh_sahu.retailapp.domain.mining.FrequentItemsetData;
import com.hitesh_sahu.retailapp.model.CenterRepository;
import com.hitesh_sahu.retailapp.util.Animatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class APrioriResultActivity extends AppCompatActivity implements OnChartValueSelectedListener {


    View appRoot;
    BarChart barChart;
    Toolbar toolbar;

    List<BarEntry> entries = new ArrayList<>();
    List<String> setEntries = new ArrayList<>();
    Toast mCurrentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apriori_result);
        appRoot = (View) findViewById(R.id.app_root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        barChart = (BarChart) findViewById(R.id.apriori_data);

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        ViewTreeObserver viewTreeObserver = appRoot.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    Animatrix.circularRevealView(appRoot);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        appRoot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        appRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
        barChart.setOnChartValueSelectedListener(this);

        drawChart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apriori, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {

            if (barChart.saveToGallery("AprioriResult.jpg", 85)) {
                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                        "Chart Saved on SD Card", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                        "Failed to Save Image", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


            return true;
        } else if (id == android.R.id.home) {
            animateExitScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        animateExitScreen();
    }

    private void animateExitScreen() {

        //Slide out toolbar to Distract user
        toolbar.animate().y(-500f).setDuration(500);

        //Circular exit Animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = Animatrix.exitReveal(appRoot);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    supportFinishAfterTransition();
                }
            });

            anim.start();
        } else {
            finish();
        }
    }

//    private void generateTestData() {
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "b")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("b", "c", "d")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "c", "d", "e")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "d", "e")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "b", "c")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "b", "c", "d")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "b", "c", "d")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "b", "c", "d")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "b", "c", "d")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "b", "c")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("a", "b", "d")));
//        CenterRepository.getInstance().getAprioriItemSetCollection()
//                .addToItemSetList(new HashSet<>(Arrays.asList("b", "c", "e")));
//    }

    private void drawChart() {

        //APRIORI RESULT SET

        AprioriFrequentItemsetGenerator<String> generator =
                new AprioriFrequentItemsetGenerator<>();

//        generateTestData();

        FrequentItemsetData<String> aprioriDataSet = generator.generate(CenterRepository
                        .getCenterRepository().getItemSetList()
                , 0.1);

        if (null == aprioriDataSet)
            return;

        float counter = 0;

        for (Set<String> itemset : aprioriDataSet.getFrequentItemsetList()) {
            Log.e("APriori", "Set : " +
                    itemset + "Support : " +
                    aprioriDataSet.getSupport(itemset));

            entries.add(new BarEntry(counter++, (float) aprioriDataSet.getSupport(itemset)));
            setEntries.add(itemset.toString());
        }

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        set.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
        barChart.animateY(700);

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }

        mCurrentToast = Toast.makeText(this, "Support for Set : " +
                        setEntries.get(Math.round(e.getX())) + " \n is : " + e.getY(),
                Toast.LENGTH_LONG);
        mCurrentToast.show();
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
