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
import com.OrderApp.dao.OrderItemDAO;
import com.OrderApp.domain.OrderItem;
import com.OrderApp.dto.OrderItemDTO;
import com.OrderApp.dto.OrderItemSearchDTO;
import com.OrderApp.dto.OrderItemPageDTO;
import com.OrderApp.dto.OrderItemConvertCriteriaDTO;
import com.OrderApp.dto.common.RequestDTO;
import com.OrderApp.dto.common.ResultDTO;
import com.OrderApp.service.OrderItemService;
import com.OrderApp.util.ControllerUtils;


@Service
public class OrderItemServiceImpl extends GenericServiceImpl<OrderItem, Integer> implements OrderItemService {

    private final static Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);

	@Autowired
	OrderItemDAO orderItemDao;

	

	@Override
	public GenericDAO<OrderItem, Integer> getDAO() {
		return (GenericDAO<OrderItem, Integer>) orderItemDao;
	}
	
	public List<OrderItem> findAll () {
		List<OrderItem> orderItems = orderItemDao.findAll();
		
		return orderItems;	
		
	}

	public ResultDTO addOrderItem(OrderItemDTO orderItemDTO, RequestDTO requestDTO) {

		OrderItem orderItem = new OrderItem();

		orderItem.setOrderItemId(orderItemDTO.getOrderItemId());

		orderItem.setOrderId(orderItemDTO.getOrderId());

		orderItem.setMenuItemId(orderItemDTO.getMenuItemId());

		orderItem.setQuantity(orderItemDTO.getQuantity());

		orderItem.setUnitPrice(orderItemDTO.getUnitPrice());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		orderItem = orderItemDao.save(orderItem);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<OrderItem> getAllOrderItems(Pageable pageable) {
		return orderItemDao.findAll(pageable);
	}

	public Page<OrderItem> getAllOrderItems(Specification<OrderItem> spec, Pageable pageable) {
		return orderItemDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrderItemPageDTO> getOrderItems(OrderItemSearchDTO orderItemSearchDTO) {
	
			Integer orderItemId = orderItemSearchDTO.getOrderItemId(); 
 			Integer orderId = orderItemSearchDTO.getOrderId(); 
 			Integer menuItemId = orderItemSearchDTO.getMenuItemId(); 
 			Integer quantity = orderItemSearchDTO.getQuantity(); 
  			String sortBy = orderItemSearchDTO.getSortBy();
			String sortOrder = orderItemSearchDTO.getSortOrder();
			String searchQuery = orderItemSearchDTO.getSearchQuery();
			Integer page = orderItemSearchDTO.getPage();
			Integer size = orderItemSearchDTO.getSize();

	        Specification<OrderItem> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, orderItemId, "orderItemId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, orderId, "orderId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, menuItemId, "menuItemId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, quantity, "quantity"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<OrderItem> orderItems = this.getAllOrderItems(spec, pageable);
		
		//System.out.println(String.valueOf(orderItems.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(orderItems.getTotalPages()));
		
		List<OrderItem> orderItemsList = orderItems.getContent();
		
		OrderItemConvertCriteriaDTO convertCriteria = new OrderItemConvertCriteriaDTO();
		List<OrderItemDTO> orderItemDTOs = this.convertOrderItemsToOrderItemDTOs(orderItemsList,convertCriteria);
		
		OrderItemPageDTO orderItemPageDTO = new OrderItemPageDTO();
		orderItemPageDTO.setOrderItems(orderItemDTOs);
		orderItemPageDTO.setTotalElements(orderItems.getTotalElements());
		return ResponseEntity.ok(orderItemPageDTO);
	}

	public List<OrderItemDTO> convertOrderItemsToOrderItemDTOs(List<OrderItem> orderItems, OrderItemConvertCriteriaDTO convertCriteria) {
		
		List<OrderItemDTO> orderItemDTOs = new ArrayList<OrderItemDTO>();
		
		for (OrderItem orderItem : orderItems) {
			orderItemDTOs.add(convertOrderItemToOrderItemDTO(orderItem,convertCriteria));
		}
		
		return orderItemDTOs;

	}
	
	public OrderItemDTO convertOrderItemToOrderItemDTO(OrderItem orderItem, OrderItemConvertCriteriaDTO convertCriteria) {
		
		OrderItemDTO orderItemDTO = new OrderItemDTO();

		orderItemDTO.setOrderItemId(orderItem.getOrderItemId());

		orderItemDTO.setOrderId(orderItem.getOrderId());

		orderItemDTO.setMenuItemId(orderItem.getMenuItemId());

		orderItemDTO.setQuantity(orderItem.getQuantity());

		orderItemDTO.setUnitPrice(orderItem.getUnitPrice());
		
		return orderItemDTO;
	}

	public ResultDTO updateOrderItem(OrderItemDTO orderItemDTO, RequestDTO requestDTO) {
		
		OrderItem orderItem = orderItemDao.getById(orderItemDTO.getOrderItemId());
		
		orderItem.setOrderItemId(ControllerUtils.setValue(orderItem.getOrderItemId(), orderItemDTO.getOrderItemId()));
		
		orderItem.setOrderId(ControllerUtils.setValue(orderItem.getOrderId(), orderItemDTO.getOrderId()));
		
		orderItem.setMenuItemId(ControllerUtils.setValue(orderItem.getMenuItemId(), orderItemDTO.getMenuItemId()));
		
		orderItem.setQuantity(ControllerUtils.setValue(orderItem.getQuantity(), orderItemDTO.getQuantity()));
		
		orderItem.setUnitPrice(ControllerUtils.setValue(orderItem.getUnitPrice(), orderItemDTO.getUnitPrice()));

        orderItem = orderItemDao.save(orderItem);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrderItemDTO getOrderItemDTOById(Integer orderItemId) {
	
		OrderItem orderItem = orderItemDao.getById(orderItemId);
		
		OrderItemConvertCriteriaDTO convertCriteria = new OrderItemConvertCriteriaDTO();
		return(this.convertOrderItemToOrderItemDTO(orderItem,convertCriteria));
	}

}
