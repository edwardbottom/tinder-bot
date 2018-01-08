package tinder.bot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.djm.tinder.Tinder;
import com.djm.tinder.auth.AuthenticationException;
import com.djm.tinder.like.Like;
import com.djm.tinder.match.Match;
import com.djm.tinder.profile.Profile;
import com.djm.tinder.user.User;

public class App 
{

	//universal variables for the file that is shared across the class
	String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(
	Calendar.getInstance().getTime());
	File logFile=new File(timeLog);


	/**
	 * 
	 * @param string token
	 * @return profile
	 * @throws Exception failed to authenticate
	 */

	//token for account as sarah connors is
	//name="logger_id" value="54783C22-558A-4E54-A1EE-BB9E357CC11F"
	public Tinder authenticateAccount(String token) throws Exception{
		try {
			final Tinder tinder = Tinder.fromAccessToken(token);
			//Profile profile = tinder.getProfile();
			//System.out.println(String.format("About me: %s", profile.getName()));
			System.out.println("method was successfull ");
			return tinder;
		} catch (AuthenticationException e) {
			System.out.println("Whoops, unable to authenticate to the tinder API. Check your Facebook access token / app's permissions.");
			return null;
		}
	}
	/**
	 * 
	 * @param tinder
	 * @return an array list of users
	 * @throws Exception
	 */
	public ArrayList<User> getPeople(Tinder tinder) throws Exception{
		 try {
	            final ArrayList<User> users = tinder.getRecommendations();
	            return users;
	            
	        } catch (AuthenticationException e) {
	            System.out.println("Whoops, unable to authenticate to the tinder API. Check your Facebook access token / app's permissions.");
	            return null;
	        }
	}
	/**
	 * 
	 * @param user
	 * @param distance
	 * @return boolean based on distance
	 */
	public static boolean distanceFilter(User user, int distance){
		if(user.getDistance() > distance){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 
	 * @param user
	 * @param gender
	 * @return boolean based on gender
	 */
	public static boolean genderFilter(User user, String gender){
		gender.toLowerCase();
		int genderVal = 0;
		if(gender == "female"){
			genderVal = 1;
		}
		if(genderVal == user.getGender()){
			return true;
		}
		return false;
	}
	
	
	@SuppressWarnings("deprecation")
	public static void main( String[] args ) throws Exception
	{
		App app = new App();
		Tinder account = app.authenticateAccount("EAAGm0PX4ZCpsBADINaUB9IpbzdMtzSqJvonNRnw9z7Nb4OyPDqAH7qiJjDBRMDZAljkemyTZCJnj1oigy9kiXOksYbEkppH7UQZBLlP122sNo0clD4ViOOMkwY9mtlbkFhE6cZBZBmm1cJTBDvVTNxo9WdDCjvMqSxo0q3iiUzn4nWCk2DtoNTa885b3kop9lYxbTcPnV3zYAKAneG00HveNOEO5TNZBbBpZBadJSkTIqxl9EkLwjK4G");
		ArrayList<User> reccs = app.getPeople(account);
		for(User user: reccs){
			System.out.println();
			if(distanceFilter(user, 10)){
				if(genderFilter(user,"male")){
					Like like = account.like(user);
					System.out.println("you liked " + user.getName());
		            if (like.isMatch() == true) {
		                System.out.println(String.format("Matched with %s!", user.getName()));
		            }
				}
			}
		}
		
	}
	
}
