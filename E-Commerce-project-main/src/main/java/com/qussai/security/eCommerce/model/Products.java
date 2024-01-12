package com.qussai.security.eCommerce.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Products {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	@NotNull(message = "please input a valid product name")
	private String productName;
	@NotNull(message = "please input a valid product name")
	private Double price;
	private String color;

	@Min(value = 1,message = "quantity should be minimum one")
	private Integer quantity;

	@CreatedBy
	@Column(
			nullable = false,
			updatable = false
	)
	private Integer createdBy;


}
