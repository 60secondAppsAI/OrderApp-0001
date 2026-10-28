package com.OrderApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class OrderStatusDTO {

	private Integer orderStatusId;

	private Integer orderId;

	private String status;

	private Date changedAt;






}
