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
import com.OrderApp.dao.OrderModifierDAO;
import com.OrderApp.domain.OrderModifier;
import com.OrderApp.dto.OrderModifierDTO;
import com.OrderApp.dto.OrderModifierSearchDTO;
import com.OrderApp.dto.OrderModifierPageDTO;
import com.OrderApp.dto.OrderModifierConvertCriteriaDTO;
import com.OrderApp.dto.common.RequestDTO;
import com.OrderApp.dto.common.ResultDTO;
import com.OrderApp.service.OrderModifierService;
import com.OrderApp.util.ControllerUtils;


@Service
public class OrderModifierServiceImpl extends GenericServiceImpl<OrderModifier, Integer> implements OrderModifierService {

    private final static Logger logger = LoggerFactory.getLogger(OrderModifierServiceImpl.class);

	@Autowired
	OrderModifierDAO orderModifierDao;

	

	@Override
	public GenericDAO<OrderModifier, Integer> getDAO() {
		return (GenericDAO<OrderModifier, Integer>) orderModifierDao;
	}
	
	public List<OrderModifier> findAll () {
		List<OrderModifier> orderModifiers = orderModifierDao.findAll();
		
		return orderModifiers;	
		
	}

	public ResultDTO addOrderModifier(OrderModifierDTO orderModifierDTO, RequestDTO requestDTO) {

		OrderModifier orderModifier = new OrderModifier();

		orderModifier.setOrderModifierId(orderModifierDTO.getOrderModifierId());

		orderModifier.setOrderItemId(orderModifierDTO.getOrderItemId());

		orderModifier.setModifierName(orderModifierDTO.getModifierName());

		orderModifier.setPriceAdjustment(orderModifierDTO.getPriceAdjustment());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		orderModifier = orderModifierDao.save(orderModifier);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<OrderModifier> getAllOrderModifiers(Pageable pageable) {
		return orderModifierDao.findAll(pageable);
	}

	public Page<OrderModifier> getAllOrderModifiers(Specification<OrderModifier> spec, Pageable pageable) {
		return orderModifierDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrderModifierPageDTO> getOrderModifiers(OrderModifierSearchDTO orderModifierSearchDTO) {
	
			Integer orderModifierId = orderModifierSearchDTO.getOrderModifierId(); 
 			Integer orderItemId = orderModifierSearchDTO.getOrderItemId(); 
 			String modifierName = orderModifierSearchDTO.getModifierName(); 
  			String sortBy = orderModifierSearchDTO.getSortBy();
			String sortOrder = orderModifierSearchDTO.getSortOrder();
			String searchQuery = orderModifierSearchDTO.getSearchQuery();
			Integer page = orderModifierSearchDTO.getPage();
			Integer size = orderModifierSearchDTO.getSize();

	        Specification<OrderModifier> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, orderModifierId, "orderModifierId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, orderItemId, "orderItemId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, modifierName, "modifierName"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("modifierName")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<OrderModifier> orderModifiers = this.getAllOrderModifiers(spec, pageable);
		
		//System.out.println(String.valueOf(orderModifiers.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(orderModifiers.getTotalPages()));
		
		List<OrderModifier> orderModifiersList = orderModifiers.getContent();
		
		OrderModifierConvertCriteriaDTO convertCriteria = new OrderModifierConvertCriteriaDTO();
		List<OrderModifierDTO> orderModifierDTOs = this.convertOrderModifiersToOrderModifierDTOs(orderModifiersList,convertCriteria);
		
		OrderModifierPageDTO orderModifierPageDTO = new OrderModifierPageDTO();
		orderModifierPageDTO.setOrderModifiers(orderModifierDTOs);
		orderModifierPageDTO.setTotalElements(orderModifiers.getTotalElements());
		return ResponseEntity.ok(orderModifierPageDTO);
	}

	public List<OrderModifierDTO> convertOrderModifiersToOrderModifierDTOs(List<OrderModifier> orderModifiers, OrderModifierConvertCriteriaDTO convertCriteria) {
		
		List<OrderModifierDTO> orderModifierDTOs = new ArrayList<OrderModifierDTO>();
		
		for (OrderModifier orderModifier : orderModifiers) {
			orderModifierDTOs.add(convertOrderModifierToOrderModifierDTO(orderModifier,convertCriteria));
		}
		
		return orderModifierDTOs;

	}
	
	public OrderModifierDTO convertOrderModifierToOrderModifierDTO(OrderModifier orderModifier, OrderModifierConvertCriteriaDTO convertCriteria) {
		
		OrderModifierDTO orderModifierDTO = new OrderModifierDTO();

		orderModifierDTO.setOrderModifierId(orderModifier.getOrderModifierId());

		orderModifierDTO.setOrderItemId(orderModifier.getOrderItemId());

		orderModifierDTO.setModifierName(orderModifier.getModifierName());

		orderModifierDTO.setPriceAdjustment(orderModifier.getPriceAdjustment());
		
		return orderModifierDTO;
	}

	public ResultDTO updateOrderModifier(OrderModifierDTO orderModifierDTO, RequestDTO requestDTO) {
		
		OrderModifier orderModifier = orderModifierDao.getById(orderModifierDTO.getOrderModifierId());
		
		orderModifier.setOrderModifierId(ControllerUtils.setValue(orderModifier.getOrderModifierId(), orderModifierDTO.getOrderModifierId()));
		
		orderModifier.setOrderItemId(ControllerUtils.setValue(orderModifier.getOrderItemId(), orderModifierDTO.getOrderItemId()));
		
		orderModifier.setModifierName(ControllerUtils.setValue(orderModifier.getModifierName(), orderModifierDTO.getModifierName()));
		
		orderModifier.setPriceAdjustment(ControllerUtils.setValue(orderModifier.getPriceAdjustment(), orderModifierDTO.getPriceAdjustment()));

        orderModifier = orderModifierDao.save(orderModifier);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrderModifierDTO getOrderModifierDTOById(Integer orderModifierId) {
	
		OrderModifier orderModifier = orderModifierDao.getById(orderModifierId);
		
		OrderModifierConvertCriteriaDTO convertCriteria = new OrderModifierConvertCriteriaDTO();
		return(this.convertOrderModifierToOrderModifierDTO(orderModifier,convertCriteria));
	}

}
