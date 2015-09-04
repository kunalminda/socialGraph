package com.snapdeal.gohack.test;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Output {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("children")
	private List<Output> children;

	@JsonProperty("size")
	private String size;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Output> getChildren() {
		return children;
	}

	public void setChildren(List<Output> children) {
		this.children = children;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	
}
