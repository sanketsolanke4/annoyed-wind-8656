package com.masai.Dao;

import java.util.List;

import com.masai.Bean.AccountBean;
import com.masai.Exception.AccountException;
import com.masai.Exception.AccountantException;
import com.masai.Exception.CustomerException;

public interface AdminDao {
	
	public final String username = "admin";
	public final String password = "admin";
	public String adminLogin(String Username,String Password) throws AccountantException;
	public int addCustomer(String name,String email,String pass,String add,String mob) throws CustomerException;
	public String removeAccount(int AccountNumber) throws CustomerException;
	public AccountBean getAccount(int AccountNumber) throws CustomerException;
	public List<AccountBean> getAllcustomer() throws AccountException;
	public String addAccount(int custbal, int custid) throws AccountException;

}