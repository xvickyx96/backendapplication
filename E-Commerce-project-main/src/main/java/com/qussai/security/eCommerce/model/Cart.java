package com.qussai.security.eCommerce.model;


import com.qussai.security.webSecurity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cartId;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Products cartItem;

//	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
//	private List<CartItem> cartItems = new ArrayList<>();


	@ManyToOne
	private User customerlist;

	private int quantity;

}
