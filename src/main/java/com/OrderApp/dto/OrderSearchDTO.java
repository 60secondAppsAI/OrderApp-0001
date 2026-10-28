package com.OrderApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer orderId;
	
	private Integer locationId;
	
	private Integer guestId;
	
	private String orderType;
	
	private String status;
	
	private Date createdAt;
	
	private Double totalAmount;
	
}
