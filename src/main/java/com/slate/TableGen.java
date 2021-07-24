package com.slate;
//under construction. supposed to use all the data gathered to generate a form, probably gonna have another servlet to show table after this.
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class TableGen extends HttpServlet
{  
	
	TableVal tableVal[][]= new TableVal[6][6];
	SubTeachVal subTv[]=new SubTeachVal[30];
	SubHoursVal subHours[]=new SubHoursVal[20];
	RelTableVal relVals[]= new RelTableVal[500];
	int PK;
	
	public void service(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException{
		
		try {
			int i, j, nRel=0;
			//initalize table wiht day and hour vals
			for(i=0;i<6;i++) 
			{
				for(j=0;j<6;j++)
				{
					tableVal[i][j]= new TableVal(i, j, "null");
				}
			}
			
			
			String Dept=(String) req.getSession().getAttribute("Dept");
			String Sec=(String) req.getSession().getAttribute("Sec");
			int Sem=(int) req.getSession().getAttribute("Sem");
			PrintWriter out= res.getWriter();
			res.setContentType("text/html");
			Connection con= DatabaseConnection.initializeDatabase();
			Statement statement=con.createStatement();
			String sql ="select t.SubID, t.TeachID, t.Batch, s.HoursPerWeek  "
					+ "from teacherallo t, subjects s  "
					+ "where (s.Department='"+Dept+"'or s.Department='Math')and Sem="+Sem+" and (Section='"+Sec+"')"
					+ " and s.SubID=t.SubID "
					+ "ORDER BY s.SubID"; 
			
			ResultSet resultSet=null;
			resultSet= statement.executeQuery(sql);
			
			int m=0;
			int n=0;
			String SubID="null";
			//setting results of query inside our array of objects
			while(resultSet.next()){
				String SubID1=resultSet.getString("SubID");
				String TeachID=resultSet.getString("TeachID");
				int HoursPerWeek=resultSet.getInt("HoursPerWeek");
				subTv[m]= new SubTeachVal(SubID1, TeachID);
				if(!SubID.equals(SubID1))
				{	
					SubID=SubID1;
					subHours[n]=new SubHoursVal(SubID, HoursPerWeek);
					n++;
					
				}
				
				m++;
				
			}
			int TableK=0;
			statement=con.createStatement();
			sql ="select TableAssign "
					+ "from classallo   "
					+ "where Semester='"+Sem+"' and Department='"+Dept+"' and Section='"+Sec+" '";
			resultSet=statement.executeQuery(sql);
			while(resultSet.next()){
				TableK=resultSet.getInt("TableAssign");
				out.println(TableK);
			}
			
			statement=con.createStatement();
			sql ="select PEAssign "
					+ "from classallo   "
					+ "where Semester='"+Sem+"' and Department='"+Dept+"' ";
			resultSet=statement.executeQuery(sql);
			while(resultSet.next()){
				PK=resultSet.getInt("PEAssign");
				out.println(TableK);
			}
			
			out.println(TableK);
			if(TableK==0) {
					statement=con.createStatement();
					sql ="select t.SubID, t.TeachID, t.Batch, s.HoursPerWeek  "
							+ "from teacherallo t, subjects s  "
							+ "where (s.Department='"+Dept+"'or s.Department='Math')and Sem="+Sem+" and (Section='Z')"
							+ " and s.SubID=t.SubID "
							+ "ORDER BY s.SubID"; 
					
					resultSet=null;
					resultSet=statement.executeQuery(sql);
					
					
					//setting results of query inside our array of objects
					while(resultSet.next()){
						String TeachID=resultSet.getString("TeachID");
						subTv[m]= new SubTeachVal("18CSE46", TeachID);
						m++;
					}
					
					
					
					statement=con.createStatement();
					sql ="select TeachID, Hour, Day, Section, Batch "
							+ "from timeslots "
							+ "ORDER BY Day, Hour"; 
					resultSet=statement.executeQuery(sql);
					
					while(resultSet.next()){
						String TeachID=resultSet.getString("TeachID");
						int Hour=resultSet.getInt("Hour");
						int Day=resultSet.getInt("Day");
						String Sect=resultSet.getString("Section");
						int batch=resultSet.getInt("Batch");
						relVals[nRel]= new RelTableVal(TeachID, Day, Hour, Sect, batch);
						nRel++;
						
					}
					
					
					
					out.println("nrel"+nRel);
					int PEkey=0;
					statement=con.createStatement();
					sql ="select PEAssign  "
							+ "from classallo   "
							+ "where Semester="+Sem+" and Department='"+Dept+"' ";
					
					resultSet=null;
					resultSet=statement.executeQuery(sql);
					while(resultSet.next()){
						int key=resultSet.getInt("PEAssign");
						if(key==1)
						{
							PEkey=1;
							break;
						}
					}
					if(PEkey==1) {
						for(int x=0;x<nRel;x++)
						{
							if(relVals[x].batch==-1)
							{
								tableVal[relVals[x].day][relVals[x].hour].SubID="18CSE46";
								tableVal[relVals[x].day][relVals[x].hour].flag=1;
							}
						}
						subHours[n]= new SubHoursVal("18CSE46", 0);
						n++;
					}
					else {
						subHours[n]= new SubHoursVal("18CSE46", 3);
						n++;
					}
					
				for(i=0;i<6;i++)
				{
					for(j=0;j<4;j++) 
					{
						if(tableVal[i][j].flag==0) {
							if(i%2==0) {
								int k=0;
								int l=0;
								int key=1;
								int lab=0;
								while(true)
								{
									int key1=0;
									
									if(k==m)
									{
										if(key==1&&subHours[l].hoursPW>0)
										{
											
											tableVal[i][j].SubID=subHours[l].SubID;
											tableVal[i][j].flag=1;
											subHours[l].hoursPW--;
										}
										break;
									}
									if(!subTv[k].SubID.equals(subHours[l].SubID))
									{
										
										if(key==1&&subHours[l].hoursPW>0)
										{
											if(subHours[l].SubID.substring(4, 5).equals("L")) 
											{
												tableVal[i][j].SubID=subHours[l].SubID;
												tableVal[i][j].flag=1;
												subHours[l].hoursPW--;
												tableVal[i][j+1].SubID=subHours[l].SubID;
												tableVal[i][j+1].flag=1;
												subHours[l].hoursPW--;
												j++;
											}
											else
											{
												tableVal[i][j].SubID=subHours[l].SubID;
												tableVal[i][j].flag=1;
												subHours[l].hoursPW--;
											}
											break;
										}
										l++;
										key=1;
									}
									if(subHours[l].SubID.substring(4, 5).equals("L"))
									{
										if(j%2==0)
										{
											key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j, nRel);
											if(key1==0)
												key=0;
											key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j+1, nRel);
											if(key1==0)
												key=0;
										}
										else
											key=0;
									}
									else 
									{
										key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j, nRel);
										if(key1==0)
											key=0;
									}
									k++;
								}
							}
								else {
									int k=m-1;
									int l=n-1;
									int key=1;
									int lab=0;
									while(true)
									{
										int key1=0;
										
										if(k==0)
										{
											if(key==1&&subHours[l].hoursPW>0)
											{
												
												tableVal[i][j].SubID=subHours[l].SubID;
												tableVal[i][j].flag=1;
												subHours[l].hoursPW--;
											}
											break;
										}
										if(!subTv[k].SubID.equals(subHours[l].SubID))
										{
											
											if(key==1&&subHours[l].hoursPW>0)
											{
												if(subHours[l].SubID.substring(4, 5).equals("L")) 
												{
													tableVal[i][j].SubID=subHours[l].SubID;
													tableVal[i][j].flag=1;
													subHours[l].hoursPW--;
													tableVal[i][j+1].SubID=subHours[l].SubID;
													tableVal[i][j+1].flag=1;
													subHours[l].hoursPW--;
													j++;
												}
												else
												{
													tableVal[i][j].SubID=subHours[l].SubID;
													tableVal[i][j].flag=1;
													subHours[l].hoursPW--;
												}
												break;
											}
											l--;
											key=1;
										}
										if(subHours[l].SubID.substring(4, 5).equals("L"))
										{
											if(j%2==0)
											{
												key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j, nRel);
												if(key1==0)
													key=0;
												key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j+1, nRel);
												if(key1==0)
													key=0;
											}
											else
												key=0;
										}
										else 
										{
											key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j, nRel);
											if(key1==0)
												key=0;
										}
										k--;
									}
								}
							}
						}
					}
				
				
				for(i=0;i<5;i++)
				{
					for(j=4;j<6;j++) 
					{
						if(tableVal[i][j].flag==0) {
							int k=0;
							int l=0;
							int key=1;
							int lab=0;
							while(true)
							{
								int key1=0;
								
								if(k==m)
								{
									if(key==1&&subHours[l].hoursPW>0)
									{
										
										tableVal[i][j].SubID=subHours[l].SubID;
										tableVal[i][j].flag=1;
										subHours[l].hoursPW--;
									}
									break;
								}
								if(!subTv[k].SubID.equals(subHours[l].SubID))
								{
									
									if(key==1&&subHours[l].hoursPW>0)
									{
										if(subHours[l].SubID.substring(4, 5).equals("L")) 
										{
											tableVal[i][j].SubID=subHours[l].SubID;
											tableVal[i][j].flag=1;
											subHours[l].hoursPW--;
											tableVal[i][j+1].SubID=subHours[l].SubID;
											tableVal[i][j+1].flag=1;
											subHours[l].hoursPW--;
											j++;
										}
										else
										{
											tableVal[i][j].SubID=subHours[l].SubID;
											tableVal[i][j].flag=1;
											subHours[l].hoursPW--;
										}
										break;
									}
									l++;
									key=1;
								}
								if(subHours[l].SubID.substring(4, 5).equals("L"))
								{
									if(j%2==0)
									{
										key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j, nRel);
										if(key1==0)
											key=0;
										key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j+1, nRel);
										if(key1==0)
											key=0;
									}
									else
										key=0;
								}
								else 
								{
									key1=checkA(subTv[k].SubID, subTv[k].TeachID, i, j, nRel);
									if(key1==0)
										key=0;
								}
								k++;
							}
						}
					}
				}
				
				out.println("<table>");
				for(i=0;i<6;i++)
				{
					out.println("<tr>");
					for(j=0;j<6;j++)
					{
						out.println("<td>"+tableVal[i][j].SubID+"</td>");
					}
					out.println("</tr>");
				}
				out.println("</table>");
					
				insertVals(con, m, Dept, Sem, Sec);
				
			}
			res.sendRedirect(req.getContextPath() + "/TableShow");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	// function to check if the timeslot is available to be alloted
	public int checkA(String SubID, String TeachID, int i, int j, int nRel) 
	{
		int key=1;
		
		//checks if subject is being repeated in the same day
		for(int k=j-1;k>=0;k--)
		{
			if(tableVal[i][k].SubID.equals(SubID))
				key=0;
		}
		
		//checks all the relational values obtained from timeslot dbs
		//checks if the teacher is busy during those hours teaching some other class
		for(int k=0;k<nRel;k++)
		{
			if(relVals[k].day==i&&relVals[k].hour==j&&relVals[k].TeachID.equals(TeachID))
				key=0;
			
		}
		
		//returns 1 if timeslot is available to be allocated, 0 otherwise
		return key;
	}
	
	
	
	public void insertVals(Connection con, int m, String Dept, int Sem, String Sec) throws SQLException {
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<6;j++)
			{
				int batch=0;
				if(tableVal[i][j].flag==1) {
					for(int k=0;k<m;k++)
					{
						if(tableVal[i][j].SubID.equals(subTv[k].SubID))
						{
							if(tableVal[i][j].SubID.substring(4, 5).equals("L")) {
								batch++;
							}
							PreparedStatement ps=con.prepareStatement("insert into timeslots values(?,?,?,?,?, ?, ?, ?)");
								String TeachID=subTv[k].TeachID;
									if((tableVal[i][j].SubID.substring(4, 5).equals("E")&&PK==0)){
									ps.setString(1,TeachID);
									ps.setString(2,tableVal[i][j].SubID);
									ps.setInt(3,j);
									ps.setInt(4,i); 
									ps.setString(5,Dept);
									ps.setInt(6,Sem);
									if(tableVal[i][j].SubID.substring(4, 5).equals("E"))
									{
										ps.setString(7,Sec);
										ps.setInt(8,-1);
									}
									else {
										ps.setString(7,Sec);
										ps.setInt(8,batch);
									}
									}
									else {
										ps.setString(1,TeachID);
										ps.setString(2,tableVal[i][j].SubID);
										ps.setInt(3,j);
										ps.setInt(4,i); 
										ps.setString(5,Dept);
										ps.setInt(6,Sem);
										
											ps.setString(7,Sec);
											ps.setInt(8,batch);
									}
									int y=ps.executeUpdate();
								
						}
					}
					
					batch=0;
				}
			}
		}
		PreparedStatement ps=con.prepareStatement("Update classallo "
				+ "set PEAssign=1 "
				+ "where Department='"+Dept+"'"); 
		int k=ps.executeUpdate();
		ps=con.prepareStatement("Update classallo "
				+ "set PEAssign=1 , TableAssign=1 "
				+ "where Department='"+Dept+"' and Section='"+Sec+"' "); 
		 k=ps.executeUpdate();
	}
}