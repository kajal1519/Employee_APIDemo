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
	
	public void testCreateGetUpdateDeleteEmployee() throws InterruptedException
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
        
        Thread.sleep(4000);

        
        
        //GET Employee details
        Response getResp= given()
        				.pathParam("id", empId)
        			.when()
        				.get(APIEndpoints.GET_EMPLOYEE)
        			.then()
        				.extract().response();
        
        Assert.assertEquals(getResp.statusCode(),200,"Get employee status should be 200" );
        String empName= getResp.jsonPath().getString("data.employee_name");
        Assert.assertEquals(empName, newEmp.getName(), "Employee name should match");
        
        Thread.sleep(4000);

        
        //update employee
        
        newEmp.setName("Jhon updated");
        newEmp.setSalary("45666");
        newEmp.setAge("29");
        
        Response updatedResp= given()
        						.contentType("application/json")
        						.pathParam("id", empId)
        						.body(newEmp)
        					.when()
        						.put(APIEndpoints.UPDATE_EMPLOYEE)
							.then()
								.extract().response();
        
        Assert.assertEquals(updatedResp.statusCode(), 200, "Update employee status should be 200");
        String updateStatus = updatedResp.jsonPath().getString("status");
        Assert.assertEquals(updateStatus, "success", "Update status should be success");
        
        
        Thread.sleep(5000);

        
     // 4️⃣ VERIFY Updated Details (GET again)
        Response verifyResp = given()
                .pathParam("id", empId)
        .when()
                .get(APIEndpoints.GET_EMPLOYEE)
        .then()
                .extract().response();

        Assert.assertEquals(verifyResp.statusCode(), 200);
        String updatedName = verifyResp.jsonPath().getString("data.employee_name");
        Assert.assertTrue(updatedName.contains("Updated"), "Employee name should be updated");
        Thread.sleep(7000);

        
        // 5️⃣ DELETE Employee
        Response deleteResp = given()
                .pathParam("id", empId)
        .when()
                .delete(APIEndpoints.DELETE_EMPLOYEE)
        .then()
                .extract().response();

        Assert.assertEquals(deleteResp.statusCode(), 200);
        String deleteStatus = deleteResp.jsonPath().getString("status");
        Assert.assertEquals(deleteStatus, "success", "Delete should be success");

        System.out.println("Employee Deleted Successfully ID: " + empId);
        

		
	}
	

}
