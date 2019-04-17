package com.yc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.dao.RoleMapper;
import com.yc.dao.UserMapper;
import com.yc.po.Role;
import com.yc.po.User;
import com.yc.po.UserRole;
import com.yc.util.ResultData;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	//���ݿ��������
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * ��¼ҵ����
	 */
	@Override
	public ResultData goLogin(String userCode,String password) {
		User user = userMapper.SelectByUserCode(userCode);
		ResultData rd = new ResultData();
		if(user == null){//�ж��û������Ƿ����
			rd.setMsg("�û��������!");
			rd.setFlag(1);
			return rd;
		}
		if(!password.equals(user.getUserpassword())){ //�ж������Ƿ���ȷ
			rd.setMsg("�û��������!");
			rd.setFlag(2);
			return rd;
		}
		rd.setMsg("��¼�ɹ�");
		rd.setFlag(0);
		rd.setData(user);
		return rd;
	}
	
	/**
	 * ��֤�������Ƿ���ȷ
	 */
	@Override
	public ResultData isPwd(String pwd, Long id) {
		User user = userMapper.selectByPrimaryKey(id);
		ResultData rd = new ResultData();
		if(user != null){
			if(pwd.equals(user.getUserpassword())){
				rd.setFlag(0);
				rd.setMsg("������ȷ!");
			}else{
				rd.setFlag(1);
				rd.setMsg("�������!");
			}
		}else{
			rd.setFlag(2);
			rd.setMsg("δ֪����!");
		}
		return rd;
	}
	
	/**
	 * ��ҳ��ѯ�û���Ϣ
	 */
	@Override
	public PageInfo getUserListPage(String username,String rolename,Integer n, Integer pageSize) {
		//PageHelper page = new PageHelper();
		//����ҳ���ÿҳ��¼������䣬���������ϲ�ѯ��䣬�м䲻�ܼ��κδ���
		PageHelper.startPage(n,pageSize);
		List<UserRole> userRoles = userMapper.selectByPageAll(username,rolename);
		PageInfo<UserRole> pageInfo = new PageInfo<UserRole>(userRoles,3);
		return pageInfo;
	}
	
	/**
	 * ��ѯ��ɫ�����б�
	 */
	@Override
	public List<String> getRoleNames() {
		return roleMapper.selectRoleNameByAll();
	}
	
	/**
	 * ����ID�����û�
	 */
	@Override
	public User getUserById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> getRoleNameAndIds() {
		return roleMapper.selectRoleNameAndIdByAll();
	}

	@Override
	public int userUpdate(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int userDelect(Long id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	/**
	 * �û�����
	 */
	@Override
	public int userAdd(User user) {		
		return userMapper.insertSelective(user);
	}

	@Override
	public int pwdupdate(String userpassword, Long id) { 
		return userMapper.updatepwd(userpassword, id);
	}

}
