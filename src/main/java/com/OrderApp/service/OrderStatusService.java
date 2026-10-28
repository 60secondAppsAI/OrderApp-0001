package com.OrderApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.OrderApp.domain.OrderStatus;
import com.OrderApp.dto.OrderStatusDTO;
import com.OrderApp.dto.OrderStatusSearchDTO;
import com.OrderApp.dto.OrderStatusPageDTO;
import com.OrderApp.dto.OrderStatusConvertCriteriaDTO;
import com.OrderApp.service.GenericService;
import com.OrderApp.dto.common.RequestDTO;
import com.OrderApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface OrderStatusService extends GenericService<OrderStatus, Integer> {

	List<OrderStatus> findAll();

	ResultDTO addOrderStatus(OrderStatusDTO orderStatusDTO, RequestDTO requestDTO);

	ResultDTO updateOrderStatus(OrderStatusDTO orderStatusDTO, RequestDTO requestDTO);

    Page<OrderStatus> getAllOrderStatuss(Pageable pageable);

    Page<OrderStatus> getAllOrderStatuss(Specification<OrderStatus> spec, Pageable pageable);

	ResponseEntity<OrderStatusPageDTO> getOrderStatuss(OrderStatusSearchDTO orderStatusSearchDTO);
	
	List<OrderStatusDTO> convertOrderStatussToOrderStatusDTOs(List<OrderStatus> orderStatuss, OrderStatusConvertCriteriaDTO convertCriteria);

	OrderStatusDTO getOrderStatusDTOById(Integer orderStatusId);



	
}
