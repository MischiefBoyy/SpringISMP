package com.inrich.model;

import java.util.List;

public class TabModel {
	private String name;
	private int id;
	private int isBase;
	private List<TabModel> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<TabModel> getChildren() {
		return children;
	}

	public void setChildren(List<TabModel> children) {
		this.children = children;
	}

	public int getIsBase() {
		return isBase;
	}

	public void setIsBase(int isBase) {
		this.isBase = isBase;
	}
	
	

}
