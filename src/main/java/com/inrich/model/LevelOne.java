package com.inrich.model;

public class LevelOne {
	private int id;
	private int level; //问题的等级  0位最顶层，1其他的层的儿子
	private String question;
	private String keyWord;
	private int clickNum;
	private int isBase;//是否是最底层，0不是最底层，1是最底层
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getClickNum() {
		return clickNum;
	}
	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}
	public int getIsBase() {
		return isBase;
	}
	public void setIsBase(int isBase) {
		this.isBase = isBase;
	}
	
	
}
