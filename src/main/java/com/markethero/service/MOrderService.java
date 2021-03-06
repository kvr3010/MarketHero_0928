package com.markethero.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.markethero.vo.DetailVO;
import com.markethero.vo.DetailVO2;
import com.markethero.vo.MOrderVO;
import com.markethero.vo.MerchantVO;
import com.markethero.vo.UserVO;

public interface MOrderService {

	public List<MOrderVO> orderList(MerchantVO vo, HttpSession session) throws Exception;

	public List<DetailVO> detail(DetailVO vo) throws Exception;
	
	public List<DetailVO2> detail(DetailVO2 vo) throws Exception;
}
