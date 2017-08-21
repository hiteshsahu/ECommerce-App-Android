/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.util.Utils;
import com.hitesh_sahu.retailapp.util.Utils.AnimationType;
import com.hitesh_sahu.retailapp.view.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.view.adapter.ProductListAdapter;
import com.hitesh_sahu.retailapp.view.adapter.ProductListAdapter.OnItemClickListener;

public class ProductListFragment extends Fragment {
    private String subcategoryKey;
    private boolean isShoppingList;

    public ProductListFragment() {
        isShoppingList = true;
    }

    public ProductListFragment(String subcategoryKey) {

        isShoppingList = false;
        this.subcategoryKey = subcategoryKey;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_product_list_fragment, container,
                false);

        if (isShoppingList) {
            view.findViewById(R.id.slide_down).setVisibility(View.VISIBLE);
            view.findViewById(R.id.slide_down).setOnTouchListener(
                    new OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

//							Utils.switchContent(R.id.top_container,
//									Utils.HOME_FRAGMENT,
//									((ECartHomeActivity) (getContext())),
//									AnimationType.SLIDE_DOWN);

                            Utils.switchFragmentWithAnimation(
                                    R.id.frag_container,
                                    new HomeFragment(),
                                    ((ECartHomeActivity) (getContext())), Utils.HOME_FRAGMENT,
                                    AnimationType.SLIDE_DOWN);


                            return false;
                        }
                    });
        }

        // Fill Recycler View

        RecyclerView recyclerView = (RecyclerView) view
                .findViewById(R.id.product_list_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        ProductListAdapter adapter = new ProductListAdapter(subcategoryKey,
                getActivity(), isShoppingList);

        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                Utils.switchFragmentWithAnimation(R.id.frag_container,
                        new ProductDetailsFragment(subcategoryKey, position, false),
                        ((ECartHomeActivity) (getContext())), null,
                        AnimationType.SLIDE_LEFT);

            }
        });

        // Handle Back press
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

//					Utils.switchContent(R.id.top_container,
//							Utils.HOME_FRAGMENT,
//							((ECartHomeActivity) (getContext())),
//							AnimationType.SLIDE_UP);

                    Utils.switchFragmentWithAnimation(
                            R.id.frag_container,
                            new HomeFragment(),
                            ((ECartHomeActivity) (getContext())), Utils.HOME_FRAGMENT,
                            AnimationType.SLIDE_UP);

                }
                return true;
            }
        });

        return view;
    }

}