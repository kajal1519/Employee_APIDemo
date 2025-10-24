package payloads;

import pojo.Employee;

public class EmployeePayload {
	public static Employee createEmployeePayload() {
        return new Employee("KajalQA", "50000", "29");
    }

    public static Employee updateEmployeePayload() {
        return new Employee("KajalUpdated", "65000", "30");
    }

}
