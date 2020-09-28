package com.markethero.controller;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.markethero.service.MOrderService;
import com.markethero.service.MerchantService;
import com.markethero.service.ProductService;
import com.markethero.vo.MOrderVO;
import com.markethero.vo.DetailVO;
import com.markethero.vo.DetailVO2;
import com.markethero.vo.MerchantVO;
import com.markethero.vo.ProductVO;
import com.markethero.vo.U_ProductVO;
import com.markethero.vo.UserVO;

@Controller
@RequestMapping("/merchant/*")
public class MerchantController {
	private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

	@Inject
	MerchantService service;
	@Inject
	ProductService Pservice;
	
	@Inject
	MOrderService mos;
	
	
	@Inject
	BCryptPasswordEncoder pwdEncoder;
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public String login(MerchantVO vo, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
		logger.info("post login");
		String email = req.getParameter("email");
//		System.out.println(vo.getEmail());
//		System.out.println(vo.getPw());
		
		
		
		HttpSession session = req.getSession();
		MerchantVO login = service.login(vo);
		System.out.println(login);
		
		boolean pwdMatch = false;
		if (login != null && vo != null) {
			pwdMatch = pwdEncoder.matches(vo.getPw(), login.getPw());
		}
		if (pwdMatch == true) {
			session.setAttribute("merchant", login);
			rttr.addFlashAttribute("msg",true);
			
		} else {
			session.setAttribute("merchant", null);
			rttr.addFlashAttribute("msg", false);
			
		}
		

//		if (login != null && pwdMatch == true) {
//			session.setAttribute("Merchant", login);
//		} else {
//			session.setAttribute("Merchant", null);
//			rttr.addFlashAttribute("msg", false);
//		}
		return "redirect:/";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception {

		session.invalidate();

		return "/merchant/logout";
	}

	@RequestMapping(value = "/MerchantUpdateView", method = RequestMethod.GET)
	public String registerUpdateView() throws Exception {

		return "merchant/MerchantUpdateView";
	}

	@RequestMapping(value = "/MerchantUpdate", method = RequestMethod.POST)
	public String registerUpdate(MerchantVO vo, HttpSession session) throws Exception {
		String inputPass = vo.getPw();
		String pwd = pwdEncoder.encode(inputPass);
		vo.setPw(pwd);
		service.MerchantUpdate(vo);

		session.invalidate();

		return "redirect:/";
	}

	// 패스워드 체크
	@ResponseBody
	@RequestMapping(value = "/passChk", method = RequestMethod.POST)
	public boolean passChk(MerchantVO vo) throws Exception {

		MerchantVO login = service.login(vo);
		boolean pwdChk = pwdEncoder.matches(vo.getPw(), login.getPw());
		return pwdChk;
	}

	// 아이디 중복 체크
	@ResponseBody
	@RequestMapping(value = "/idChk", method = RequestMethod.POST)
	public String idChk(MerchantVO vo) throws Exception {
		logger.info("idChk" + vo.getEmail());
		int result = service.idChk(vo);
		return Integer.toString(result);
	}

	// 회원가입 get
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegister() throws Exception {
		logger.info("get register");
		return "/merchant/m_register";
	}

	// 회원가입 post
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegister(MerchantVO vo) throws Exception {
		logger.info("post register");
		int result = service.idChk(vo);
		System.out.println(result);
		try {
			if (result == 1) {
				return "/merchant/register";
			} else if (result == 0) {
				String inputPw = vo.getPw();
				String pwd = pwdEncoder.encode(inputPw);
				vo.setPw(pwd);

				service.register(vo);
			}
			// 요기에서~ 입력된 아이디가 존재한다면 -> 다시 회원가입 페이지로 돌아가기
			// 존재하지 않는다면 -> register
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return "redirect:/";
	}
//	@RequestMapping(value = "/main", method = RequestMethod.GET)
//	public String main() throws Exception {
//		logger.info("get main");
//		return "merchant/m_main";
//	}

	

//	public String order() throws Exception {
//		logger.info("get order");
//		return "merchant/m_order";
//	}
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String order(MOrderVO mvo,MerchantVO vo, HttpSession session ,Model model) throws Exception {
		logger.info("get orderList");
		
		
		model.addAttribute("list", mos.orderList(vo, session));
		
		return "merchant/m_order";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(DetailVO2 vo2, Model model ,DetailVO vo , @RequestParam("O.id") int oid) throws Exception {
		logger.info("get detail");
		System.out.println(oid);
		vo.setOid(oid);
		model.addAttribute("list", mos.detail(vo));
		
		vo2.setOid(oid);
		model.addAttribute("tlist", mos.detail(vo2));
		
		return "merchant/m_detail";
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String product(Model model, ProductVO vo,MerchantVO Mvo, HttpSession session) throws Exception {
		logger.info("get product");
		model.addAttribute("product", Pservice.list(Mvo, session));

		return "merchant/m_product";
	}
	@RequestMapping(value = "/u_product", method = RequestMethod.GET)
	public String product(Model model, U_ProductVO vo,MerchantVO mvo, HttpSession session) throws Exception {
		logger.info("get u_product");
		model.addAttribute("u_product", Pservice.u_list(mvo,session));

		return "merchant/m_u_product";
	}



}