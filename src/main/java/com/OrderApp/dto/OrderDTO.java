package com.OrderApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class OrderDTO {

	private Integer orderId;

	private Integer locationId;

	private Integer guestId;

	private String orderType;

	private String status;

	private Date createdAt;

	private Double totalAmount;






}
