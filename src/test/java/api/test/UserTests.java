package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;//for logs
	
	@BeforeClass
	public void setupData()
	{
		faker = new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger=LogManager.getLogger(this.getClass());
		logger.debug("debugging......");
	}
	
	@Test(priority=1)
	
		public void testPostUser()
		{
		   logger.info("*****************   Creating User **********************");
			Response response=UserEndpoints.createUser(userPayload);
			response.then().log().all();
			
			Assert.assertEquals(response.getStatusCode(),200);
			
			 logger.info("***************** User is created **********************");
		}
	
	@Test(priority=2)
	
	public void testGetUserByName()
	{
		 logger.info("*****************  Reading  User Info  **********************");
		Response response=UserEndpoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		//response.statusCode();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		 logger.info("***************** User Info is displayed **********************");
	}
	
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		 logger.info("*****************   Updating User **********************");
		Response response=UserEndpoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		//response.statusCode();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		 logger.info("*****************   User updated **********************");
		
		//Checking the data after update
		
		Response responseAfterupdate=UserEndpoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		 logger.info("*****************   Deleting User **********************");
		Response response=UserEndpoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
		
		 logger.info("*****************   User Deleted **********************");
	}
	
	
	}


