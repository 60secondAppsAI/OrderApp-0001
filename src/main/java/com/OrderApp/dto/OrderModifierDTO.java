package com.OrderApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class OrderModifierDTO {

	private Integer orderModifierId;

	private Integer orderItemId;

	private String modifierName;

	private Double priceAdjustment;






}
