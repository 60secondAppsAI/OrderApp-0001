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
import com.OrderApp.dao.OrderDAO;
import com.OrderApp.domain.Order;
import com.OrderApp.dto.OrderDTO;
import com.OrderApp.dto.OrderSearchDTO;
import com.OrderApp.dto.OrderPageDTO;
import com.OrderApp.dto.OrderConvertCriteriaDTO;
import com.OrderApp.dto.common.RequestDTO;
import com.OrderApp.dto.common.ResultDTO;
import com.OrderApp.service.OrderService;
import com.OrderApp.util.ControllerUtils;


@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderDAO orderDao;

	

	@Override
	public GenericDAO<Order, Integer> getDAO() {
		return (GenericDAO<Order, Integer>) orderDao;
	}
	
	public List<Order> findAll () {
		List<Order> orders = orderDao.findAll();
		
		return orders;	
		
	}

	public ResultDTO addOrder(OrderDTO orderDTO, RequestDTO requestDTO) {

		Order order = new Order();

		order.setOrderId(orderDTO.getOrderId());

		order.setLocationId(orderDTO.getLocationId());

		order.setGuestId(orderDTO.getGuestId());

		order.setOrderType(orderDTO.getOrderType());

		order.setStatus(orderDTO.getStatus());

		order.setCreatedAt(orderDTO.getCreatedAt());

		order.setTotalAmount(orderDTO.getTotalAmount());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		order = orderDao.save(order);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Order> getAllOrders(Pageable pageable) {
		return orderDao.findAll(pageable);
	}

	public Page<Order> getAllOrders(Specification<Order> spec, Pageable pageable) {
		return orderDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrderPageDTO> getOrders(OrderSearchDTO orderSearchDTO) {
	
			Integer orderId = orderSearchDTO.getOrderId(); 
 			Integer locationId = orderSearchDTO.getLocationId(); 
 			Integer guestId = orderSearchDTO.getGuestId(); 
 			String orderType = orderSearchDTO.getOrderType(); 
 			String status = orderSearchDTO.getStatus(); 
 			Date createdAt = orderSearchDTO.getCreatedAt();
  			String sortBy = orderSearchDTO.getSortBy();
			String sortOrder = orderSearchDTO.getSortOrder();
			String searchQuery = orderSearchDTO.getSearchQuery();
			Integer page = orderSearchDTO.getPage();
			Integer size = orderSearchDTO.getSize();

	        Specification<Order> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, orderId, "orderId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, locationId, "locationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, guestId, "guestId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, orderType, "orderType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			
			spec = ControllerUtils.andIfNecessary(spec, createdAt, "createdAt");
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("orderType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Order> orders = this.getAllOrders(spec, pageable);
		
		//System.out.println(String.valueOf(orders.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(orders.getTotalPages()));
		
		List<Order> ordersList = orders.getContent();
		
		OrderConvertCriteriaDTO convertCriteria = new OrderConvertCriteriaDTO();
		List<OrderDTO> orderDTOs = this.convertOrdersToOrderDTOs(ordersList,convertCriteria);
		
		OrderPageDTO orderPageDTO = new OrderPageDTO();
		orderPageDTO.setOrders(orderDTOs);
		orderPageDTO.setTotalElements(orders.getTotalElements());
		return ResponseEntity.ok(orderPageDTO);
	}

	public List<OrderDTO> convertOrdersToOrderDTOs(List<Order> orders, OrderConvertCriteriaDTO convertCriteria) {
		
		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
		
		for (Order order : orders) {
			orderDTOs.add(convertOrderToOrderDTO(order,convertCriteria));
		}
		
		return orderDTOs;

	}
	
	public OrderDTO convertOrderToOrderDTO(Order order, OrderConvertCriteriaDTO convertCriteria) {
		
		OrderDTO orderDTO = new OrderDTO();

		orderDTO.setOrderId(order.getOrderId());

		orderDTO.setLocationId(order.getLocationId());

		orderDTO.setGuestId(order.getGuestId());

		orderDTO.setOrderType(order.getOrderType());

		orderDTO.setStatus(order.getStatus());

		orderDTO.setCreatedAt(order.getCreatedAt());

		orderDTO.setTotalAmount(order.getTotalAmount());
		
		return orderDTO;
	}

	public ResultDTO updateOrder(OrderDTO orderDTO, RequestDTO requestDTO) {
		
		Order order = orderDao.getById(orderDTO.getOrderId());
		
		order.setOrderId(ControllerUtils.setValue(order.getOrderId(), orderDTO.getOrderId()));
		
		order.setLocationId(ControllerUtils.setValue(order.getLocationId(), orderDTO.getLocationId()));
		
		order.setGuestId(ControllerUtils.setValue(order.getGuestId(), orderDTO.getGuestId()));
		
		order.setOrderType(ControllerUtils.setValue(order.getOrderType(), orderDTO.getOrderType()));
		
		order.setStatus(ControllerUtils.setValue(order.getStatus(), orderDTO.getStatus()));
		
		order.setCreatedAt(ControllerUtils.setValue(order.getCreatedAt(), orderDTO.getCreatedAt()));
		
		order.setTotalAmount(ControllerUtils.setValue(order.getTotalAmount(), orderDTO.getTotalAmount()));

        order = orderDao.save(order);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrderDTO getOrderDTOById(Integer orderId) {
	
		Order order = orderDao.getById(orderId);
		
		OrderConvertCriteriaDTO convertCriteria = new OrderConvertCriteriaDTO();
		return(this.convertOrderToOrderDTO(order,convertCriteria));
	}

}
