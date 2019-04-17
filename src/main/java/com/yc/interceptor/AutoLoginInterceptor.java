package com.yc.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yc.dao.UserMapper;
import com.yc.po.User;
/**
 * �Զ���¼��������
 * @author Administrator
 *
 */
public class AutoLoginInterceptor extends HandlerInterceptorAdapter{
	
	//ҵ�����
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * ��ִ��Ŀ�귽��֮ǰִ��
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//��ȡcookie��Ϣ
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if("usercode".equals(cookie.getName())){
					//��ȡ�û����
					String usercode = cookie.getValue();
					System.out.println(usercode);
					//��ѯ�û���Ϣ
					User user = userMapper.SelectByUserCode(usercode);
					System.out.println(user);
					if(user != null){
						request.getSession(true).setAttribute("user",user);
						System.out.println(3);
						response.sendRedirect(request.getContextPath()+"/user/frame");
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * ���������DispatcherServlet��ȫ���������ִ�У���������һ����Դ����Ĳ���
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	
	/**
	 * ��ִ��Ŀ�귽��֮������Ⱦ��ͼ֮ǰִ��
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
}
