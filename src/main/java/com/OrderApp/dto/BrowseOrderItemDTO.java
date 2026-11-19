package com.OrderApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseOrderItemDTO {

	private Integer ownerId;

	private Integer orderItemId;

	private Integer orderItemStatus;
	
	private Integer nextOrPrevious;
}

