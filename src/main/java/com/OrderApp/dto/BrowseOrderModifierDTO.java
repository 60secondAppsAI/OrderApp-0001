package com.OrderApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseOrderModifierDTO {

	private Integer ownerId;

	private Integer orderModifierId;

	private Integer orderModifierStatus;
	
	private Integer nextOrPrevious;
}

