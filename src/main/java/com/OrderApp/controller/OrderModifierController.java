package com.OrderApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.OrderApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.OrderApp.domain.OrderModifier;
import com.OrderApp.dto.OrderModifierDTO;
import com.OrderApp.dto.OrderModifierSearchDTO;
import com.OrderApp.dto.OrderModifierPageDTO;
import com.OrderApp.service.OrderModifierService;
import com.OrderApp.dto.common.RequestDTO;
import com.OrderApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/orderModifier")
@RestController
public class OrderModifierController {

	private final static Logger logger = LoggerFactory.getLogger(OrderModifierController.class);

	@Autowired
	OrderModifierService orderModifierService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<OrderModifier> getAll() {

		List<OrderModifier> orderModifiers = orderModifierService.findAll();
		
		return orderModifiers;	
	}

	@GetMapping(value = "/{orderModifierId}")
	@ResponseBody
	public OrderModifierDTO getOrderModifier(@PathVariable Integer orderModifierId) {
		
		return (orderModifierService.getOrderModifierDTOById(orderModifierId));
	}

 	@RequestMapping(value = "/addOrderModifier", method = RequestMethod.POST)
	public ResponseEntity<?> addOrderModifier(@RequestBody OrderModifierDTO orderModifierDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = orderModifierService.addOrderModifier(orderModifierDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/orderModifiers")
	public ResponseEntity<OrderModifierPageDTO> getOrderModifiers(OrderModifierSearchDTO orderModifierSearchDTO) {
 
		return orderModifierService.getOrderModifiers(orderModifierSearchDTO);
	}	

	@RequestMapping(value = "/updateOrderModifier", method = RequestMethod.POST)
	public ResponseEntity<?> updateOrderModifier(@RequestBody OrderModifierDTO orderModifierDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = orderModifierService.updateOrderModifier(orderModifierDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
