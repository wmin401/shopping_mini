package com.study.order;

import java.util.List;

public interface OrderMapper {

	//cartMapper
	List<CartAndContentsDTO> getOrders(CartDTO cartDto);
	
	List<CartAndContentsDTO> getContents(CartDTO cartDto);
}
