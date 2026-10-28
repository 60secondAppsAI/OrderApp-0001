package com.OrderApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class OrderItemDTO {

	private Integer orderItemId;

	private Integer orderId;

	private Integer menuItemId;

	private Integer quantity;

	private Double unitPrice;






}
