package com.example.carlos.evalproyect;

public class Product {
	
	private String id;
	private String name;
	private String model;
	private String item;

	public Product() {
	}

	public Product(String pid,String pname, String pmodel, String pitem){
		id = pid;
		name = pname;
		model = pmodel;
		item = pitem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
