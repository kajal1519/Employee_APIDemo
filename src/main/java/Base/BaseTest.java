package Base;

import org.testng.annotations.BeforeClass;

import constants.APIConstants;
import io.restassured.RestAssured;

public class BaseTest {
	
	@BeforeClass
	
	public void setup()
	{
		RestAssured.baseURI=APIConstants.BASE_URL;
	}

}
