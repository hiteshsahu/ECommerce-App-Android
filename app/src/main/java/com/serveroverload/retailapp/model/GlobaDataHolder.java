package com.serveroverload.retailapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobaDataHolder {

	private static GlobaDataHolder globaDataHolder;

	public static GlobaDataHolder getGlobaDataHolder() {

		if (null == globaDataHolder) {
			globaDataHolder = new GlobaDataHolder();
		}
		return globaDataHolder;
	}

	private ArrayList<ProductCategoryModel> listOfCategory = new ArrayList<ProductCategoryModel>();

	private ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();

	private List<Product> shoppingList = Collections.synchronizedList( new ArrayList<Product>());

	public List<Product> getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ArrayList<Product> getShoppingList) {
		this.shoppingList = getShoppingList;
	}

	public Map<String, ArrayList<Product>> getProductMap() {
		return productMap;
	}

	public void setProductMap(ConcurrentHashMap<String, ArrayList<Product>> productMap) {
		this.productMap = productMap;
	}

	public ArrayList<ProductCategoryModel> getListOfCategory() {
		return listOfCategory;
	}

	public void setListOfCategory(ArrayList<ProductCategoryModel> listOfCategory) {
		this.listOfCategory = listOfCategory;
	}

	public Map<String, ArrayList<Product>> getElectricProductMap() {
		return productMap;
	}

	public void setElectricProductMap(
			ConcurrentHashMap<String, ArrayList<Product>> electricProductMap) {
		this.productMap = electricProductMap;
	}

}
