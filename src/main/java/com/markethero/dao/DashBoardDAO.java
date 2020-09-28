package com.markethero.dao;

import java.util.List;

import com.markethero.vo.MerchantVO;
import com.markethero.vo.OrderItemVO;

public interface DashBoardDAO {
	
	public List<OrderItemVO> newOrder(MerchantVO vo) throws Exception;
	
	public List<OrderItemVO> orderList(MerchantVO vo) throws Exception;

}
