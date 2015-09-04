package com.snapdeal.gohack.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SellerRequest {

	@JsonProperty("product")
	private String product;
	
	@JsonProperty("subCat")
	private String subCat;
	
	@JsonProperty("cat")
	private String cat;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSubCat() {
		return subCat;
	}

	public void setSubCat(String subCat) {
		this.subCat = subCat;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}
	
	
}
