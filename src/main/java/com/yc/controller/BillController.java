package com.yc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yc.po.Bill;
import com.yc.po.Provider;
import com.yc.po.Role;
import com.yc.po.User;
import com.yc.service.BillService;

@RequestMapping("bill")
@Controller
public class BillController {

	@Autowired
	private BillService billService;
	
	//session�����
	@Autowired
	private HttpSession session;
	
	/**
	 * ��ת����������ҳ��
	 * @return
	 */
	@GetMapping("billlist")
	public String getBillProList(
			@RequestParam(value="productname",defaultValue="")String productname,
			@RequestParam(value="proname",defaultValue="")String proname,
			@RequestParam(value="ispayment",defaultValue="")String ispayment,
			@RequestParam(value="n",defaultValue="1")Integer n,
			@RequestParam(value="pageSize",defaultValue="5")Integer pageSize,
			Map<String,Object> map){
		//��ȡ�����б���Ϣ
		PageInfo pageInfo = billService.getBillProListPage(productname, proname, ispayment, n, pageSize);
		//��ȡ��Ӧ���б���Ϣ
		List<String> proNames = billService.getProNames();
		map.put("pageInfo", pageInfo);
		map.put("proNames",proNames);
		map.put("productname",productname);
		map.put("proname",proname);
		map.put("ispayment",ispayment);
		return "billlist";
	}
	
	
	@ModelAttribute
	public void modelAttributeMethod(Long id,String flag,Map<String,Object> map){
		if("update".equals(flag)){
			Bill bill = new Bill();
			bill = billService.getBillById(id);
			
			List<Provider> proids = billService.getProNameAndIdByAll();
			map.put("bill",bill);
			map.put("proids",proids);
		}
	}
	
	/**
	 * ��ת�������޸�ҳ��
	 * @return
	 */
	@GetMapping("billmodify")
	public String billmodify(){
		return "billmodify";
	}
	
	/**
	 * ��ת�������鿴ҳ��
	 * @return
	 */
	@GetMapping("billview")
	public String billview(){
		return "billview";
	}
	
	
	/**
	 * ���¶�����Ϣ
	 * @return
	 */
	@PostMapping("update")
	public String userupdate(Bill bill,Map<String,Object> map){
		//�޸�ʱ��
		bill.setModifydate(new Date());
		//�޸���
		User onlineUser = (User)session.getAttribute("user");
		if(onlineUser != null){
			bill.setModifyby(onlineUser.getId());
		}
		billService.billUpdate(bill);
		
		List<Provider> proids = billService.getProNameAndIdByAll();
		map.put("proids",proids);
		return "billmodify";
	}
}
