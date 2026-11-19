package com.OrderApp.dao;

import java.util.List;
import java.util.Date;

import com.OrderApp.dao.GenericDAO;
import com.OrderApp.domain.OrderStatus;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface OrderStatusDAO extends GenericDAO<OrderStatus, Integer> {
  
	List<OrderStatus> findAll();
	


}

