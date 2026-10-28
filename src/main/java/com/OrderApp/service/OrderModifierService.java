package com.OrderApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.OrderApp.domain.OrderModifier;
import com.OrderApp.dto.OrderModifierDTO;
import com.OrderApp.dto.OrderModifierSearchDTO;
import com.OrderApp.dto.OrderModifierPageDTO;
import com.OrderApp.dto.OrderModifierConvertCriteriaDTO;
import com.OrderApp.service.GenericService;
import com.OrderApp.dto.common.RequestDTO;
import com.OrderApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface OrderModifierService extends GenericService<OrderModifier, Integer> {

	List<OrderModifier> findAll();

	ResultDTO addOrderModifier(OrderModifierDTO orderModifierDTO, RequestDTO requestDTO);

	ResultDTO updateOrderModifier(OrderModifierDTO orderModifierDTO, RequestDTO requestDTO);

    Page<OrderModifier> getAllOrderModifiers(Pageable pageable);

    Page<OrderModifier> getAllOrderModifiers(Specification<OrderModifier> spec, Pageable pageable);

	ResponseEntity<OrderModifierPageDTO> getOrderModifiers(OrderModifierSearchDTO orderModifierSearchDTO);
	
	List<OrderModifierDTO> convertOrderModifiersToOrderModifierDTOs(List<OrderModifier> orderModifiers, OrderModifierConvertCriteriaDTO convertCriteria);

	OrderModifierDTO getOrderModifierDTOById(Integer orderModifierId);



	
}
