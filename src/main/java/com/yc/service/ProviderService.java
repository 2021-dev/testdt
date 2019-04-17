package com.yc.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yc.po.Provider;
import com.yc.po.User;

public interface ProviderService {
	
	public PageInfo getProviderListPage(String proname, String procontact, Integer n,Integer pageSize);
	
	/**
	 * ͨ��id��ѯ��Ӧ����Ϣ
	 * @param id
	 * @return
	 */
	public Provider getProById(Long id);
	
	/**
	 * ��Ӧ����Ϣ�޸�
	 * @param provider
	 * @return
	 */
	public int proUpdate(Provider provider);
	
	/**
	 * ��Ӧ����Ϣɾ��
	 * @param id
	 * @return
	 */
	public int proDelect(Long id);
	
	/**
	 * ��Ӧ�̵�����
	 * @param provider
	 * @return
	 */
	public int proAdd(Provider provider);

}
