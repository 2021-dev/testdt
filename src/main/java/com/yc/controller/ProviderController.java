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
import com.yc.po.Provider;
import com.yc.po.Role;
import com.yc.po.User;
import com.yc.service.ProviderService;

@RequestMapping("provider")
@Controller
public class ProviderController {
	
	@Autowired
	private ProviderService providerService;
	
	//session�����
	@Autowired
	private HttpSession session;
	
	@GetMapping("providerlist")
	public String getProviderList(
			@RequestParam(value="proname",defaultValue="")String proname,
			@RequestParam(value="procontact",defaultValue="")String procontact,
			@RequestParam(value="n",defaultValue="1")Integer n,
			@RequestParam(value="pageSize",defaultValue="5")Integer pageSize,
			Map<String,Object> map){
		//��ȡ�û��б���Ϣ
		PageInfo pageInfo = providerService.getProviderListPage(proname, procontact, n, pageSize);
		
		map.put("pageInfo", pageInfo);	
		map.put("proname",proname);
		map.put("procode",procontact);
		return "providerlist";
	}
	
	@ModelAttribute
	public void modelAttributeMethod(Long id,String flag,Map<String,Object> map){
		if("update".equals(flag)){
			Provider provider = new Provider();
			provider = providerService.getProById(id);
		
			map.put("provider",provider);
		}
	}
	
	/**
	 * ���¹�Ӧ����Ϣ
	 * @return
	 */
	@PostMapping("update")
	public String proupdate(Provider provider,Map<String,Object> map){
		//�޸�ʱ��
		provider.setModifydate(new Date());
		//�޸���
		User onlineUser = (User)session.getAttribute("user");
		if(onlineUser != null){
			provider.setModifyby(onlineUser.getId());
		}
		providerService.proUpdate(provider);
		
		return "redirect:providerlist";
	}
	
	/**
	 * ��ת����Ӧ�̸���ҳ��
	 * @return
	 */
	@GetMapping("providermodify")
	public String providermodify(){
		return "providermodify";
	}
	
	/**
	 * ��ת����Ӧ�̲鿴ҳ��
	 * @return
	 */
	@GetMapping("providerview")
	public String providerview(){
		return "providerview";
	}
	
	/**
	 * ��ת����Ӧ������ҳ��
	 * @return
	 */
	@GetMapping("provideradd")
	public String provideradd(){
		return "provideradd";
	}
	
	/**
	 * ��Ӧ��ɾ������
	 * @return
	 */
	@RequestMapping("providerdel")
	public String userdel(@RequestParam("id") Long id){
		providerService.proDelect(id);
		return "redirect:providerlist";
	}
	
	/**
	 * ���ӹ�Ӧ����Ϣ
	 * @return
	 */
	@PostMapping("add")
	public String useradd(Provider provider,Map<String,Object> map){
	
		provider.setCreationdate(new Date());		
		//�޸���
		User onlineUser = (User)session.getAttribute("user");
		if(onlineUser != null){
			provider.setCreatedby(onlineUser.getId());
		}
		providerService.proAdd(provider);
		return "redirect:providerlist";
	}
		
}
