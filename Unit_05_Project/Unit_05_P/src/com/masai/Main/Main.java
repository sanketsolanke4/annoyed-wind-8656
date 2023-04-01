package com.masai.Main;

import java.util.List;
import java.util.Scanner;

import com.masai.Bean.AccountBean;
import com.masai.Bean.TransactionBean;
import com.masai.Bean.UserBean;
import com.masai.Dao.AdminDao;
import com.masai.Dao.AdminDaoImpl;
import com.masai.Dao.UserDao;
import com.masai.Dao.UserDaoImpl;
import com.masai.Exception.AccountException;
import com.masai.Exception.AccountantException;
import com.masai.Exception.CustomerException;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while(flag) {
			System.out.println("Welcome To Bank");
			System.out.println("1.Accountant"
					+ "\n2.Customer");
			int choice=sc.nextInt();
			switch(choice) {
			case 1:
				System.out.println("Accountant Section");
				System.out.println("Enter Username here::");
				String Username = sc.next();
				System.out.print("Enter Password here:: ");
				String Password = sc.next();
				AdminDao admindao = new AdminDaoImpl();
				String msg = null;
				try {
					msg = admindao.adminLogin(Username, Password);
				} catch (AccountantException e1) {
					e1.printStackTrace();
				}
				System.out.println(msg);
				boolean flag1 = false;
				if(msg.equals("Login Successfully...")) {
					flag1 = true;
				}else {
					continue;
				}
				while(flag1) {
					System.out.println("\n"
							+ "1. Add New Customer Account\n"
							+ "2. Delete the account\n"
							+ "3. View particular account details\n"
							+ "4. View all the account details\n"
							+ "5. View Transaction History for Customer\n"
							+ "6. exit\r\n"
							+"\n");
					int s = sc.nextInt();
					if(s==1) {
						System.out.println("create new account::");
						System.out.println("Enter Customer Name::");
			 			String a2=sc.next();
			 			System.out.println("Enter Account Opening Balance::");
			 			int a3=sc.nextInt();
			 			System.out.println("Enter Email Address::");
			 			String a4=sc.next();
			 			System.out.println("Enter Password here::");
			 			String a5=sc.next();
			 			System.out.println("Enter Mobile number here::");
			 			String a6=sc.next();
			 			System.out.println("Enter Address here::");
			 			String a7=sc.next();
			 			int s1= 0;
						try {
							s1 = admindao.addCustomer(a2,a4,a5,a6,a7);
							try {
								admindao.addAccount(a3, s1);
							} catch (AccountException e) {
								e.printStackTrace();
							}
						} catch (CustomerException e) {
							e.printStackTrace();
						}
			 			if(s1!=0) {
			 				System.out.println("Account Added Successfully....");
			 			}
					}
					if(s==2) {
						System.out.println("Remove Account");
						System.out.println("Enter Account No here::");
						int ac=sc.nextInt();
						String x=null;
						try {
							x = admindao.removeAccount(ac);
						} catch (CustomerException e) {
							e.printStackTrace();
						}
						if(x!=null)
							System.out.println(x);
					}if(s==3) {
						System.out.println("Customer Details");
						System.out.println("Enter Customer Account No.");
						int ac=sc.nextInt();
						try {
							AccountBean res=admindao.getAccount(ac);
							if(res!=null) {
								System.out.println("Account No:: " + res.getAccountNo());
								System.out.println("Balance::" + res.getBalance());
								System.out.println("Customer id:: " + res.getCustomerId());	
							}else {
								System.out.println("Account not here...");
							}
						} catch (CustomerException e) {
							e.printStackTrace();
						}
					}
					if(s==4) {
						try {
							System.out.println("All Customer Details::");
							List<AccountBean> res=admindao.getAllcustomer();	
							System.out.println(res);
						} catch (AccountException e) {
							e.printStackTrace();
						}
					}if(s==5) {
						UserDao user=new UserDaoImpl();
						System.out.println("Transaction History");
						System.out.println("Enter Account No:: ");
						int acount=sc.nextInt();
						List<TransactionBean> list=null;
						try {
							list=user.viewTransaction(acount); 
							if(list.size()==0) {
								System.out.println("Transaction not Found.....");
							}else {
								System.out.println("Account No:: "+list.get(0).getAccountNo());
								list.forEach(i->{
									if(i.getDeposit()!=0)
										System.out.println("Amount Credit:: "+i.getDeposit());
									if(i.getWithdraw()!=0)
										System.out.println("Amount Debit:: "+i.getWithdraw());
								});
							}
						} catch (CustomerException e) {
							e.printStackTrace();
						}
					}
					if(s==6) {
						System.out.println("Thank you.....Visit Again!");
						flag1=false;
						break;
					}
				}
				break;
			case 2: 
				System.out.println("Login Customer::");
				System.out.println("Enter Email::");
				String username=sc.next();
				System.out.println("Enter Password::");
				String password=sc.next();
				System.out.println("Enter Account No::");
				int acno=sc.nextInt();
				UserDao user=new UserDaoImpl();
				try {
					UserBean cust = user.Login(username, password,acno);
					System.out.println("Welcome::"+cust.getName());
					boolean m=true;
					while(m) {
						System.out.println("\n"
								+ "1. View Balance\n"
								+ "2. Deposit Money\n"
								+ "3. Withdraw Money\n"
								+ "4. View Transaction History\n"
								+ "5. exit\r\n"
								+"\n");
						int s=sc.nextInt();
						if(s==1) {
							System.out.println("Balance");
							System.out.println("Current Account Balance....");
							System.out.println(user.Accountbal(acno)); 
						}
						if(s==2) {
							System.out.println("Deposite...");
							System.out.println("Enter Amount to Deposit::");
							int a=sc.nextInt();
							user.Deposit(acno, a);
							System.out.println("Your Balance after Deposit::");
							System.out.println(user.Accountbal(acno));
						}
						if(s==3) {
							System.out.println("Withdrwal...");
							System.out.println("Enter Withdrawl amount::");
							int a=sc.nextInt();
							try {
								user.Withdraw(acno, a);
								System.out.println("Your Balance after Withdrawl...");
								System.out.println(user.Accountbal(acno));
							}catch(CustomerException e) {
								System.out.println(e.getMessage());
							}
						}
						if(s==4) {
							List<TransactionBean> list=null;
							try {
								list= user.viewTransaction(acno);
							}catch(CustomerException e) {
								System.out.println(e.getMessage());
							}
							System.out.println("Transaction History...");
							System.out.println("Account No:: " + list.get(0).getAccountNo());
							list.forEach(i->{
								if(i.getDeposit()!=0)
									System.out.println("Amount Credit:: "+ i.getDeposit());
								if(i.getWithdraw()!=0)
									System.out.println("Amount Debit::"+ i.getWithdraw());
				 			});
						}
						if(s==5){
							System.out.println("Thank You...Visit Again!");
							flag1 = false;
							m=false;
							break;
						}
					}
					break;
				} catch (CustomerException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
		}
	}
}