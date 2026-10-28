package com.OrderApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="order_statuss")
@Getter @Setter @NoArgsConstructor
public class OrderStatus {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="order_status_id")
	private Integer orderStatusId;
    
  	@Column(name="order_id")
	private Integer orderId;
    
  	@Column(name="status")
	private String status;
    
  	@Column(name="changed_at")
	private Date changedAt;
    

}
