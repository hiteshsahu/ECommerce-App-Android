/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.bumptech.glide.Glide;
import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.model.CenterRepository;
import com.hitesh_sahu.retailapp.model.entities.Money;
import com.hitesh_sahu.retailapp.model.entities.Product;
import com.hitesh_sahu.retailapp.util.ColorGenerator;
import com.hitesh_sahu.retailapp.util.Utils;
import com.hitesh_sahu.retailapp.view.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.view.customview.TextDrawable;
import com.hitesh_sahu.retailapp.view.customview.TextDrawable.IBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hitesh Sahu (hiteshsahu.com)
 */
public class ProductListAdapter extends
        RecyclerView.Adapter<ProductListAdapter.VersionViewHolder> implements
        ItemTouchHelperAdapter {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    private IBuilder mDrawableBuilder;

    private TextDrawable drawable;

    private String ImageUrl;

    private List<Product> productList = new ArrayList<Product>();
    private OnItemClickListener clickListener;

    private Context context;

    public ProductListAdapter(String subcategoryKey, Context context,
                              boolean isCartlist) {

        if (isCartlist) {

            productList = CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList();

        } else {

            productList = CenterRepository.getCenterRepository().getMapOfProductsInCategory()
                    .get(subcategoryKey);
        }

        this.context = context;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_product_list, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder holder,
                                 final int position) {

        holder.itemName.setText(productList.get(position)
                .getItemName());

        holder.itemDesc.setText(productList.get(position)
                .getItemShortDesc());

        String sellCostString = Money.rupees(
                BigDecimal.valueOf(Long.valueOf(productList.get(position)
                        .getSellMRP()))).toString()
                + "  ";

        String buyMRP = Money.rupees(
                BigDecimal.valueOf(Long.valueOf(productList.get(position)
                        .getMRP()))).toString();

        String costString = sellCostString + buyMRP;

        holder.itemCost.setText(costString, BufferType.SPANNABLE);

        Spannable spannable = (Spannable) holder.itemCost.getText();

        spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(String.valueOf(productList
                .get(position).getItemName().charAt(0)), mColorGenerator
                .getColor(productList.get(position).getItemName()));

        ImageUrl = productList.get(position).getImageURL();


        Glide.with(context).load(ImageUrl).placeholder(drawable)
                .error(drawable).animate(R.anim.base_slide_right_in)
                .centerCrop().into(holder.imagView);


        holder.addItem.findViewById(R.id.add_item).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        //current object
                        Product tempObj = productList.get(position);


                        //if current object is lready in shopping list
                        if (CenterRepository.getCenterRepository()
                                .getListOfProductsInShoppingList().contains(tempObj)) {


                            //get position of current item in shopping list
                            int indexOfTempInShopingList = CenterRepository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .indexOf(tempObj);

                            // increase quantity of current item in shopping list
                            if (Integer.parseInt(tempObj.getQuantity()) == 0) {

                                ((ECartHomeActivity) getContext())
                                        .updateItemCount(true);

                            }


                            // update quanity in shopping list
                            CenterRepository
                                    .getCenterRepository()
                                    .getListOfProductsInShoppingList()
                                    .get(indexOfTempInShopingList)
                                    .setQuantity(
                                            String.valueOf(Integer
                                                    .valueOf(tempObj
                                                            .getQuantity()) + 1));


                            //update checkout amount
                            ((ECartHomeActivity) getContext()).updateCheckOutAmount(
                                    BigDecimal
                                            .valueOf(Long
                                                    .valueOf(productList
                                                            .get(position)
                                                            .getSellMRP())),
                                    true);

                            // update current item quanitity
                            holder.quanitity.setText(tempObj.getQuantity());

                        } else {

                            ((ECartHomeActivity) getContext()).updateItemCount(true);

                            tempObj.setQuantity(String.valueOf(1));

                            holder.quanitity.setText(tempObj.getQuantity());

                            CenterRepository.getCenterRepository()
                                    .getListOfProductsInShoppingList().add(tempObj);

                            ((ECartHomeActivity) getContext()).updateCheckOutAmount(
                                    BigDecimal
                                            .valueOf(Long
                                                    .valueOf(productList
                                                            .get(position)
                                                            .getSellMRP())),
                                    true);

                        }

                        Utils.vibrate(getContext());

                    }
                });

        holder.removeItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Product tempObj = (productList).get(position);

                if (CenterRepository.getCenterRepository().getListOfProductsInShoppingList()
                        .contains(tempObj)) {

                    int indexOfTempInShopingList = CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .indexOf(tempObj);

                    if (Integer.valueOf(tempObj.getQuantity()) != 0) {

                        CenterRepository
                                .getCenterRepository()
                                .getListOfProductsInShoppingList()
                                .get(indexOfTempInShopingList)
                                .setQuantity(
                                        String.valueOf(Integer.valueOf(tempObj
                                                .getQuantity()) - 1));

                        ((ECartHomeActivity) getContext()).updateCheckOutAmount(
                                BigDecimal.valueOf(Long.valueOf(productList
                                        .get(position).getSellMRP())),
                                false);

                        holder.quanitity.setText(CenterRepository
                                .getCenterRepository().getListOfProductsInShoppingList()
                                .get(indexOfTempInShopingList).getQuantity());

                        Utils.vibrate(getContext());

                        if (Integer.valueOf(CenterRepository
                                .getCenterRepository().getListOfProductsInShoppingList()
                                .get(indexOfTempInShopingList).getQuantity()) == 0) {

                            CenterRepository.getCenterRepository()
                                    .getListOfProductsInShoppingList()
                                    .remove(indexOfTempInShopingList);

                            notifyDataSetChanged();

                            ((ECartHomeActivity) getContext())
                                    .updateItemCount(false);

                        }

                    }

                } else {

                }

            }

        });

    }


    private ECartHomeActivity getContext() {
        // TODO Auto-generated method stub
        return (ECartHomeActivity) context;
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public void SetOnItemClickListener(
            final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onItemDismiss(int position) {
        productList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(productList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(productList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    class VersionViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        TextView itemName, itemDesc, itemCost, availability, quanitity,
                addItem, removeItem;
        ImageView imagView;

        public VersionViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.item_name);

            itemDesc = (TextView) itemView.findViewById(R.id.item_short_desc);

            itemCost = (TextView) itemView.findViewById(R.id.item_price);

            availability = (TextView) itemView
                    .findViewById(R.id.iteam_avilable);

            quanitity = (TextView) itemView.findViewById(R.id.iteam_amount);

            itemName.setSelected(true);

            imagView = ((ImageView) itemView.findViewById(R.id.product_thumb));

            addItem = ((TextView) itemView.findViewById(R.id.add_item));

            removeItem = ((TextView) itemView.findViewById(R.id.remove_item));

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

}
