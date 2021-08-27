package com.scheduler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.*;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.email.Constants;
import com.email.GMailServer;
import com.email.ReadPropertiesFile;
import com.jdbc.DBConnection;
import com.model.Student;

public class TestJob implements Job {

    @Override
    public void execute(final JobExecutionContext ctx) throws JobExecutionException {
    	System.out.println(" in execute : TestJob ....");
    	List<Student> list = getListOfStudents();
    	boolean isSentMail=true;
    	if (!list.isEmpty())  {
	        System.out.println("Starting  Sending mail Job....");
			try{
				ReadPropertiesFile.readConfig();
		        GMailServer sender = new GMailServer(Constants.setFrom, Constants.setPassword);
		        List<String> emailList = list.stream().map(s->s.getEmail()).collect(Collectors.toList());
		        
		        String emails=emailList.toString().replace("[", "").replace("]", "");
		        System.out.println("emails...." + emails);
		        
		       // for (Student s : list)  {
		        	sender.sendMail("Subject", "This is done by tomcate", Constants.setFrom, emails);
		       // }
			} catch (Exception e) {
				isSentMail = false;
				e.printStackTrace();
				
			} finally {
				if (isSentMail) {
					updateLastEmailSent(list);
				}
			}
			System.out.println("Email Sent Succesfully...");
			
    	} else {
    		System.out.println("No Records  to sent mail...");
    	}
    }
    
    private List<Student> getListOfStudents() {
    	List<Student> list = new ArrayList<>();
    	String SELECT_QUERY =" select * from studentdb.student where status='P' and DATEDIFF(CURDATE(), lastSentMail)>=3 ";
    	try(Connection conn = DBConnection.getConnection();
    			Statement stmt = conn.createStatement();
    	        ResultSet rs = stmt.executeQuery(SELECT_QUERY);){
        		while (rs.next()) {
        			Student s= new Student();
        			s.setName(rs.getString("name"));
        			s.setEmail(rs.getString("email"));
        			//s.setLastSentMail(rs.getString("name").to);
        			s.setStatus(rs.getString("status"));
        			list.add(s);
        		}
        	}catch (SQLException e) {
                e.printStackTrace();
            } 
    	return list;
    }
    
    private void updateLastEmailSent(List<Student> list) {
    	int batchSize = 10;
    	Connection conn = null;
    	try {
    		conn = DBConnection.getConnection();
    		conn.setAutoCommit(false);
            String sql = "UPDATE student  SET lastSentMail = CURDATE() WHERE email = ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            int count = 0;
            for (Student s : list) {
                statement.setString(1, s.getEmail());
                statement.addBatch();
                count++;
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            // execute the remaining queries
            statement.executeBatch();          
            conn.commit();
            conn.close();
        } catch (SQLException ex) {        
            ex.printStackTrace();
            try {
            	conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}