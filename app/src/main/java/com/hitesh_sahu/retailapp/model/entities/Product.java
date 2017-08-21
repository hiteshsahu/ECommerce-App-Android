/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

/**
 *
 */
package com.hitesh_sahu.retailapp.model.entities;

/**
 * The Class Product used as model for Products.
 *
 * @author Hitesh
 */
public class Product {


    /**
     * The item short desc.
     */
    private String description = "";

    /**
     * The item detail.
     */
    private String longDescription = "";

    /**
     * The mrp.
     */
    private String mrp;

    /**
     * The discount.
     */
    private String discount;

    /**
     * The sell mrp.
     */
    private String salePrice;

    /**
     * The quantity.
     */
    private String orderQty;

    /**
     * The image url.
     */
    private String imageUrl = "";

    /**
     * The item name.
     */
    private String productName = "";

    private String productId = "";

    /**
     * @param itemName
     * @param itemShortDesc
     * @param itemDetail
     * @param MRP
     * @param discount
     * @param sellMRP
     * @param quantity
     * @param imageURL
     */
    public Product(String itemName, String itemShortDesc, String itemDetail,
                   String MRP, String discount, String sellMRP, String quantity,
                   String imageURL, String orderId) {
        this.productName = itemName;
        this.description = itemShortDesc;
        this.longDescription = itemDetail;
        this.mrp = MRP;
        this.discount = discount;
        this.salePrice = sellMRP;
        this.orderQty = quantity;
        this.imageUrl = imageURL;
        this.productId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getItemName() {
        return productName;
    }

    public void setItemName(String itemName) {
        this.productName = itemName;
    }

    public String getItemShortDesc() {
        return description;
    }

    public void setItemShortDesc(String itemShortDesc) {
        this.description = itemShortDesc;
    }

    public String getItemDetail() {
        return longDescription;
    }

    public void setItemDetail(String itemDetail) {
        this.longDescription = itemDetail;
    }

    public String getMRP() {
        return this.mrp;
    }

    public void setMRP(String MRP) {
        this.mrp = MRP;
    }

    public String getDiscount() {
        return discount + "%";
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountNumeric() {
        return discount;
    }

    public String getSellMRP() {
        return salePrice;
    }

    public void setSellMRP(String sellMRP) {
        this.salePrice = sellMRP;
    }

    public String getQuantity() {
        return orderQty;
    }

    public void setQuantity(String quantity) {
        this.orderQty = quantity;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public void setImageURL(String imageURL) {
        this.imageUrl = imageURL;
    }

}
