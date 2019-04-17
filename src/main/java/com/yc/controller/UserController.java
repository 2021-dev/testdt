package com.yc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yc.po.Role;
import com.yc.po.User;
import com.yc.service.UserService;
import com.yc.util.ResultData;
@RequestMapping("user")
@Controller
public class UserController {

	//ҵ�����
	@Autowired
	private UserService userService;
	//session�����
	@Autowired
	private HttpSession session;
	
	/**
	 * �û���¼
	 * @param userCode
	 * @param password
	 * @return
	 */
	@RequestMapping("gologin")
	@ResponseBody
	public String gologin(String userCode,String password){
		ResultData rd = userService.goLogin(userCode, password);
		if(rd.getData() != null){
			//���û���Ϣ�浽session��,�����û�������״̬
			session.setAttribute("user",rd.getData());
		}
		//����json���ݵ��ͻ���
		System.out.println(JSONObject.toJSONString(rd));
		return JSONObject.toJSONString(rd);
	}
	
	/**
	 * �˳�ϵͳ����
	 * @return
	 */
	@GetMapping("logout")
	public String logout(HttpServletResponse response){
		//ɾ��session�������û���Ϣ
		session.removeAttribute("user");
		//ɾ��cookie
		Cookie cookie = new Cookie("usercode","-1");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/login.html";
	}
	
	/**
	 * ��������֤
	 * @param pwd
	 * @param id
	 * @return
	 */
	@PostMapping("isPwd")
	@ResponseBody
	public String isPwd(String pwd,Long id){
		ResultData rd = userService.isPwd(pwd, id);
		return JSONObject.toJSONString(rd);
	}
	
	/**
	 * �����޸�
	 * @param userpassword
	 * @param id
	 * @return
	 */
	@PostMapping("pwdupdate")
	@ResponseBody
	public String pwdupdate(String userpassword,Long id){
		int pwdset = userService.pwdupdate(userpassword, id);
		return JSONObject.toJSONString(pwdset);
	}
	
	/**
	 * ��ҳչʾ�û���Ϣ
	 * @param username
	 * @param rolename
	 * @param n
	 * @param pageSize
	 * @param map
	 * @return
	 */
	@GetMapping("userlist")
	public String getUserList(
			@RequestParam(value="username",defaultValue="")String username,
			@RequestParam(value="rolename",defaultValue="")String rolename,
			@RequestParam(value="n",defaultValue="1")Integer n,
			@RequestParam(value="pageSize",defaultValue="5")Integer pageSize,
			Map<String,Object> map){
		//��ȡ�û��б���Ϣ
		PageInfo pageInfo = userService.getUserListPage(username,rolename,n, pageSize);
		//��ȡ��ɫ�����б���Ϣ
		List<String> roleNames = userService.getRoleNames();
		map.put("pageInfo", pageInfo);
		map.put("roleNames",roleNames);
		map.put("username",username);
		map.put("rolename",rolename);
		return "userlist";
	}
	
	@ModelAttribute
	public void modelAttributeMethod(Long id,String flag,Map<String,Object> map){
		if("update".equals(flag)){
			User user = new User();
			user = userService.getUserById(id);
			
			List<Role> rnids = userService.getRoleNameAndIds();
			map.put("user",user);
			map.put("rnids",rnids);
		}
	}
	
	/**
	 * ��ת���û�����ҳ��
	 * @return
	 */
	@GetMapping("usermodify")
	public String usermodify(){
		return "usermodify";
	}
	
	/**
	 * ��ת���û��鿴ҳ��
	 * @return
	 */
	@GetMapping("userview")
	public String userview(){
		return "userview";
	}
	
	/**
	 * ��ת���û�����ҳ��
	 * @return
	 */
	@GetMapping("useradd")
	public String useradd(
			@RequestParam(value="rolename",defaultValue="")String rolename,
			Map<String,Object> map){
		//��ȡ��ɫ�����б���Ϣ
		List<Role> roles = userService.getRoleNameAndIds();
		map.put("roles",roles);
		return "useradd";
	}
		
	/**
	 * �鿴�û���Ϣ
	 * @return
	 */
	@RequestMapping("userselect")
	@ResponseBody
	public String userselect(Long id){			
			User user = userService.getUserById(id);			
			return JSON.toJSONString(user);		
	}
	
	/**
	 * �û�ɾ������
	 * @return
	 */
	@RequestMapping("userdel")
	public String userdel(@RequestParam("id") Long id){
		userService.userDelect(id);
		return "redirect:userlist";
	}
		
	/**
	 * �����û���Ϣ
	 * @return
	 */
	@PostMapping("update")
	public String userupdate(User user,Map<String,Object> map){
		//�޸�ʱ��
		user.setModifydate(new Date());
		//�޸���
		User onlineUser = (User)session.getAttribute("user");
		if(onlineUser != null){
			user.setModifyby(onlineUser.getId());
		}
		userService.userUpdate(user);
		
		List<Role> rnids = userService.getRoleNameAndIds();
		map.put("rnids",rnids);
		return "usermodify";
	}
	
	/**
	 * �����û���Ϣ
	 * @return
	 */
	@PostMapping("add")
	public String useradd(User user,Map<String,Object> map){
	
		user.setCreationdate(new Date());		
		//�޸���
		User onlineUser = (User)session.getAttribute("user");
		if(onlineUser != null){
			user.setCreatedby(onlineUser.getId());
		}
		userService.userAdd(user);
		return "redirect:userlist";
	}
	
	
}
