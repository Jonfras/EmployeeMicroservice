package com.krejo.serviceBackend;

import com.krejo.serviceBackend.DTO.EmployeeDto;
import com.krejo.serviceBackend.Data.Employee;
import com.krejo.serviceBackend.Resource.EmployeeResource;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDataService {

    private List<Employee> employeeList = new ArrayList<>();



    protected void createInitialListEntries(){
        Employee employee1 = new Employee();
        employee1.setName("test1");
        employee1.setLongitude("longitute1");
        employee1.setLatitude("latitude1");

        Employee employee2 = new Employee();
        employee2.setName("test2");
        employee2.setLongitude("longitute2");
        employee2.setLatitude("latitude2");

        employeeList.add(employee1);
        employeeList.add(employee2);

    }

    public List<EmployeeResource> getEmployeeResources() {
        List<EmployeeResource> result = new ArrayList<>();

        for (Employee employee :
                employeeList) {
            EmployeeResource employeeResource = convertEmployeeToEmployeeResource(employee);
            result.add(employeeResource);

        }
        return result;
    }

    public EmployeeResource addEmployee(EmployeeDto employeeDto) {

        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setLongitude(employeeDto.getLongitude());
        employee.setLatitude(employeeDto.getLatitude());

        employeeList.add(employee);

        return convertEmployeeToEmployeeResource(employee);
    }

    private EmployeeResource convertEmployeeToEmployeeResource(Employee employee) {
        EmployeeResource employeeResource = new EmployeeResource(employee.getId());

        employeeResource.setName(employee.getName());
        employeeResource.setLongitude(employee.getLongitude());
        employeeResource.setLatitude(employee.getLatitude());

        return employeeResource;
    }
}
