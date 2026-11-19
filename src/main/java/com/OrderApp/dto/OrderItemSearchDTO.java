package com.OrderApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer orderItemId;
	
	private Integer orderId;
	
	private Integer menuItemId;
	
	private Integer quantity;
	
	private Double unitPrice;
	
}
