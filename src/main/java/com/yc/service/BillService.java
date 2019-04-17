package com.yc.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yc.po.Bill;
import com.yc.po.Provider;
import com.yc.po.User;


public interface BillService {
	
	public Bill getBillById(Long id);
	
	/**
	 * ��ȡ��Ӧ������
	 * @return
	 */
	public List<String> getProNames();
	
	/**
	 * ��ȡ��Ӧ��id������
	 * @return
	 */
	public List<Provider> getProNameAndIdByAll();
	
	/**
	 * ������ҳ��ѯ
	 * @param productname
	 * @param proname
	 * @param ispayname
	 * @param n
	 * @param pageSize
	 * @return
	 */
	public PageInfo getBillProListPage(String productname,String proname,String ispayment,Integer n,Integer pageSize);

	/**
	 * �������޸�
	 * @param bill
	 * @return
	 */
	public int billUpdate(Bill bill);
}
