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
@Table(name="orders")
@Getter @Setter @NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="order_id")
	private Integer orderId;
    
  	@Column(name="location_id")
	private Integer locationId;
    
  	@Column(name="guest_id")
	private Integer guestId;
    
  	@Column(name="order_type")
	private String orderType;
    
  	@Column(name="status")
	private String status;
    
  	@Column(name="created_at")
	private Date createdAt;
    
  	@Column(name="total_amount")
	private Double totalAmount;
    

}
