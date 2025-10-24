package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import Base.BaseTest;
import endpoints.APIEndpoints;
import io.restassured.response.Response;
import payloads.EmployeePayload;
import pojo.Employee;

public class EmployeeCRUDTest extends BaseTest {
	
	private int empId;
	
	@Test(priority =1)
	
	public void testCreateGetUpdateDeleteEmployee()
	{
		//step:1 CREATE Employee record
		
		Employee newEmp= EmployeePayload.createEmployeePayload();
		
		Response createResp= given()
				.contentType("application/json")
				.body(newEmp)
			.when()
				.post(APIEndpoints.CREATE_EMPLOYEE)
			.then()
				.extract().response();
		
		Assert.assertEquals(createResp.statusCode(), 200, "Status should be 200 for create");
		
		String status= createResp.jsonPath().getString("status");
		Assert.assertEquals(status, "success", "Status should be success");
		
		empId= createResp.jsonPath().getInt("data.id");
		Assert.assertTrue(empId > 0 , "Employee ID should be generated");
		
        System.out.println("Created Employee ID: " + empId);

		
	}
	

}
