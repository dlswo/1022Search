package org.zerock.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class PageParam {

	private static final double PER = 5.0;
	private int page;
	private int display;
	
	private int total, start, end, bno;
	private boolean prev, next;
	
	
	private String[] types;
	private String keyword;
	private String type;
	
	public PageParam() {
		this.page = 1;
		this.display = 10;
	}
	
	public void setType(String type) {
		this.type = type;
		
		if(type == null || type.trim().length() == 0) { 
			this.types = null;
			return;
		}
		
		this.types = this.type.split("");
		
	}

	
	public int getSkip() {
		return (this.page - 1) * this.display;
	}
	
	public void setTotal(int total) {
		this.total = total;
		
		this.end = (int)(Math.ceil(this.page/PER) * 5);
		this.start = this.end - 4 ;
		
		if((this.end * this.display) >= total) {
			this.end = (int)(Math.ceil(total/20.0));
			this.next = false;
		}else {
			this.next = true;
		}
		
		this.prev = this.start != 1;
	}
	
	public String getLink(String path) {
		
		return UriComponentsBuilder.fromPath(path)
		.queryParam("bno", this.bno)
		.queryParam("page", this.page)
		.queryParam("type", this.type)
		.queryParam("keyword", this.keyword)
		.toUriString();
	}
	
	public static void main(String[] args) {
		
		PageParam obj = new PageParam();
		obj.setBno(123);
		obj.setPage(7);
		
		System.out.println(obj);
		System.out.println(obj.getLink("redirect:/board/read"));
		
	}
	
	
}
