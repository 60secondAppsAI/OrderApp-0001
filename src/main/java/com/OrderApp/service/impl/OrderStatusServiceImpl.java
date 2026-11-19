package com.OrderApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.OrderApp.dao.GenericDAO;
import com.OrderApp.service.GenericService;
import com.OrderApp.service.impl.GenericServiceImpl;
import com.OrderApp.dao.OrderStatusDAO;
import com.OrderApp.domain.OrderStatus;
import com.OrderApp.dto.OrderStatusDTO;
import com.OrderApp.dto.OrderStatusSearchDTO;
import com.OrderApp.dto.OrderStatusPageDTO;
import com.OrderApp.dto.OrderStatusConvertCriteriaDTO;
import com.OrderApp.dto.common.RequestDTO;
import com.OrderApp.dto.common.ResultDTO;
import com.OrderApp.service.OrderStatusService;
import com.OrderApp.util.ControllerUtils;


@Service
public class OrderStatusServiceImpl extends GenericServiceImpl<OrderStatus, Integer> implements OrderStatusService {

    private final static Logger logger = LoggerFactory.getLogger(OrderStatusServiceImpl.class);

	@Autowired
	OrderStatusDAO orderStatusDao;

	

	@Override
	public GenericDAO<OrderStatus, Integer> getDAO() {
		return (GenericDAO<OrderStatus, Integer>) orderStatusDao;
	}
	
	public List<OrderStatus> findAll () {
		List<OrderStatus> orderStatuss = orderStatusDao.findAll();
		
		return orderStatuss;	
		
	}

	public ResultDTO addOrderStatus(OrderStatusDTO orderStatusDTO, RequestDTO requestDTO) {

		OrderStatus orderStatus = new OrderStatus();

		orderStatus.setOrderStatusId(orderStatusDTO.getOrderStatusId());

		orderStatus.setOrderId(orderStatusDTO.getOrderId());

		orderStatus.setStatus(orderStatusDTO.getStatus());

		orderStatus.setChangedAt(orderStatusDTO.getChangedAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		orderStatus = orderStatusDao.save(orderStatus);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<OrderStatus> getAllOrderStatuss(Pageable pageable) {
		return orderStatusDao.findAll(pageable);
	}

	public Page<OrderStatus> getAllOrderStatuss(Specification<OrderStatus> spec, Pageable pageable) {
		return orderStatusDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrderStatusPageDTO> getOrderStatuss(OrderStatusSearchDTO orderStatusSearchDTO) {
	
			Integer orderStatusId = orderStatusSearchDTO.getOrderStatusId(); 
 			Integer orderId = orderStatusSearchDTO.getOrderId(); 
 			String status = orderStatusSearchDTO.getStatus(); 
 			Date changedAt = orderStatusSearchDTO.getChangedAt();
 			String sortBy = orderStatusSearchDTO.getSortBy();
			String sortOrder = orderStatusSearchDTO.getSortOrder();
			String searchQuery = orderStatusSearchDTO.getSearchQuery();
			Integer page = orderStatusSearchDTO.getPage();
			Integer size = orderStatusSearchDTO.getSize();

	        Specification<OrderStatus> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, orderStatusId, "orderStatusId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, orderId, "orderId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			
			spec = ControllerUtils.andIfNecessary(spec, changedAt, "changedAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<OrderStatus> orderStatuss = this.getAllOrderStatuss(spec, pageable);
		
		//System.out.println(String.valueOf(orderStatuss.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(orderStatuss.getTotalPages()));
		
		List<OrderStatus> orderStatussList = orderStatuss.getContent();
		
		OrderStatusConvertCriteriaDTO convertCriteria = new OrderStatusConvertCriteriaDTO();
		List<OrderStatusDTO> orderStatusDTOs = this.convertOrderStatussToOrderStatusDTOs(orderStatussList,convertCriteria);
		
		OrderStatusPageDTO orderStatusPageDTO = new OrderStatusPageDTO();
		orderStatusPageDTO.setOrderStatuss(orderStatusDTOs);
		orderStatusPageDTO.setTotalElements(orderStatuss.getTotalElements());
		return ResponseEntity.ok(orderStatusPageDTO);
	}

	public List<OrderStatusDTO> convertOrderStatussToOrderStatusDTOs(List<OrderStatus> orderStatuss, OrderStatusConvertCriteriaDTO convertCriteria) {
		
		List<OrderStatusDTO> orderStatusDTOs = new ArrayList<OrderStatusDTO>();
		
		for (OrderStatus orderStatus : orderStatuss) {
			orderStatusDTOs.add(convertOrderStatusToOrderStatusDTO(orderStatus,convertCriteria));
		}
		
		return orderStatusDTOs;

	}
	
	public OrderStatusDTO convertOrderStatusToOrderStatusDTO(OrderStatus orderStatus, OrderStatusConvertCriteriaDTO convertCriteria) {
		
		OrderStatusDTO orderStatusDTO = new OrderStatusDTO();

		orderStatusDTO.setOrderStatusId(orderStatus.getOrderStatusId());

		orderStatusDTO.setOrderId(orderStatus.getOrderId());

		orderStatusDTO.setStatus(orderStatus.getStatus());

		orderStatusDTO.setChangedAt(orderStatus.getChangedAt());
		
		return orderStatusDTO;
	}

	public ResultDTO updateOrderStatus(OrderStatusDTO orderStatusDTO, RequestDTO requestDTO) {
		
		OrderStatus orderStatus = orderStatusDao.getById(orderStatusDTO.getOrderStatusId());
		
		orderStatus.setOrderStatusId(ControllerUtils.setValue(orderStatus.getOrderStatusId(), orderStatusDTO.getOrderStatusId()));
		
		orderStatus.setOrderId(ControllerUtils.setValue(orderStatus.getOrderId(), orderStatusDTO.getOrderId()));
		
		orderStatus.setStatus(ControllerUtils.setValue(orderStatus.getStatus(), orderStatusDTO.getStatus()));
		
		orderStatus.setChangedAt(ControllerUtils.setValue(orderStatus.getChangedAt(), orderStatusDTO.getChangedAt()));

        orderStatus = orderStatusDao.save(orderStatus);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrderStatusDTO getOrderStatusDTOById(Integer orderStatusId) {
	
		OrderStatus orderStatus = orderStatusDao.getById(orderStatusId);
		
		OrderStatusConvertCriteriaDTO convertCriteria = new OrderStatusConvertCriteriaDTO();
		return(this.convertOrderStatusToOrderStatusDTO(orderStatus,convertCriteria));
	}

}
