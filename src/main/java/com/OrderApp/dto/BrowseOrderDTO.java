package com.OrderApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseOrderDTO {

	private Integer ownerId;

	private Integer orderId;

	private Integer orderStatus;
	
	private Integer nextOrPrevious;
}

