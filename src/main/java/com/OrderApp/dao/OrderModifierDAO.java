package com.OrderApp.dao;

import java.util.List;
import java.util.Date;

import com.OrderApp.dao.GenericDAO;
import com.OrderApp.domain.OrderModifier;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface OrderModifierDAO extends GenericDAO<OrderModifier, Integer> {
  
	List<OrderModifier> findAll();
	


}

