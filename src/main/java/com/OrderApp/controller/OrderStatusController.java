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

import com.OrderApp.domain.OrderStatus;
import com.OrderApp.dto.OrderStatusDTO;
import com.OrderApp.dto.OrderStatusSearchDTO;
import com.OrderApp.dto.OrderStatusPageDTO;
import com.OrderApp.service.OrderStatusService;
import com.OrderApp.dto.common.RequestDTO;
import com.OrderApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/orderStatus")
@RestController
public class OrderStatusController {

	private final static Logger logger = LoggerFactory.getLogger(OrderStatusController.class);

	@Autowired
	OrderStatusService orderStatusService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<OrderStatus> getAll() {

		List<OrderStatus> orderStatuss = orderStatusService.findAll();
		
		return orderStatuss;	
	}

	@GetMapping(value = "/{orderStatusId}")
	@ResponseBody
	public OrderStatusDTO getOrderStatus(@PathVariable Integer orderStatusId) {
		
		return (orderStatusService.getOrderStatusDTOById(orderStatusId));
	}

 	@RequestMapping(value = "/addOrderStatus", method = RequestMethod.POST)
	public ResponseEntity<?> addOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = orderStatusService.addOrderStatus(orderStatusDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/orderStatuss")
	public ResponseEntity<OrderStatusPageDTO> getOrderStatuss(OrderStatusSearchDTO orderStatusSearchDTO) {
 
		return orderStatusService.getOrderStatuss(orderStatusSearchDTO);
	}	

	@RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
	public ResponseEntity<?> updateOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = orderStatusService.updateOrderStatus(orderStatusDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
