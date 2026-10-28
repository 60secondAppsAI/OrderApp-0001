package com.OrderApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseOrderStatusDTO {

	private Integer ownerId;

	private Integer orderStatusId;

	private Integer orderStatusStatus;
	
	private Integer nextOrPrevious;
}

