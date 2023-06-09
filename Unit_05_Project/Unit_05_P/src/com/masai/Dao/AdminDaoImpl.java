package com.masai.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.masai.Bean.AccountBean;
import com.masai.DBUtil.DBUtil;
import com.masai.Exception.AccountException;
import com.masai.Exception.CustomerException;

public class AdminDaoImpl implements AdminDao {
	@Override
	public String adminLogin(String Username, String Password) {
		String msg = "Invalid Username or password";
		if(username.equals(Username) && password.equals(Password)) {
			msg = "Login Successfull";
		}
		return msg;
	}

	@Override
	public String removeAccount(int AccountNumber) {
		
		String sms=null;
		try(Connection conn= DBUtil.provideConnection()) {
		 PreparedStatement ps=conn.prepareStatement("delete c from customer c inner join account a on c.custid=a.custid where a.custACno=?;");
		 ps.setInt(1, AccountNumber);
		int x=ps.executeUpdate();
		 if(x>0) {
			 sms = "Account deleted sucessfully....";
		 }else {
			 sms = "Account Not Present"; 
		 }
		 System.out.println("-----------------");
		}catch(SQLException e) {
			e.printStackTrace();
			sms=e.getMessage();
		}
		return sms;
	}
	@Override
	public String addAccount(int custbal, int custid) throws AccountException {	
		String message=null;
		try(Connection conn= DBUtil.provideConnection()) {
		 PreparedStatement ps=conn.prepareStatement("insert into Account(custbal,custid) values(?,?)");
		 ps.setInt(1,custbal);
	     ps.setInt(2,custid);
		int x=ps.executeUpdate();
		 if(x>0) {
			 message = "Account added sucessfully...";
		 }else
			 message = "data is Wrong...";
		}catch(SQLException e) {
			e.printStackTrace();
			message=e.getMessage();
		}
		return message;
	}
	@Override
	public AccountBean getAccount(int AccountNumber) throws CustomerException {	
		AccountBean acc = null;
		try(Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from Customer c inner join Account a on c.custid=a.custid where custACno = ?");
			ps.setInt(1, AccountNumber);
			ResultSet res = ps.executeQuery();
			if(res.next()) {
				String Accountnum = res.getString("custACno");
				long balance =  res.getLong("custbal");
				String customerId = res.getString("custid");
				acc = new AccountBean();
				acc.setAccountNo(Accountnum);
				acc.setBalance(balance);
				acc.setCustomerId(customerId);
			}
		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}
		return acc;
	}
	@Override
	public List<AccountBean> getAllcustomer() {	
		List<AccountBean> list = new ArrayList<>();
		try(Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from Account");
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				String Accountnum = res.getString("custACno");
				long balance =  res.getLong("custbal");
				String customerId = res.getString("custid");
				AccountBean acc = new AccountBean();
				acc.setAccountNo(Accountnum);
				acc.setBalance(balance);
				acc.setCustomerId(customerId);
				list.add(acc);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int addCustomer(String name, String email, String pass, String add, String mob) throws CustomerException {	
		int id = 0;
		try(Connection conn= DBUtil.provideConnection()) {
			 PreparedStatement ps=conn.prepareStatement("insert into Customer(custname,custmail,custpass,custmob,custadd) values(?,?,?,?,?)");
		     ps.setString(1,name);
		     ps.setString(2,email);
		     ps.setString(3,pass);
		     ps.setString(4,mob);
		     ps.setString(5,add);
			int x=ps.executeUpdate();
			if(x>0) {
				PreparedStatement ps2=conn.prepareStatement("select custid from Customer where custmail=? AND custmob=?");
				 ps2.setString(1, email);
				 ps2.setString(2, mob);
				 ResultSet rs=ps2.executeQuery();
				 if(rs.next()) {
					 id=rs.getInt("custid");
				 }
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return id;
	}
}