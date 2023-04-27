package com.krejo.serviceBackend;

import com.krejo.serviceBackend.DTO.EmployeeDto;
import com.krejo.serviceBackend.DTO.ServiceDto;
import com.krejo.serviceBackend.Resource.EmployeeResource;
import com.krejo.serviceBackend.Resource.ServiceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/serviceBackend")
public class RESTController {

    @Autowired
    private EmployeeDataService employeeDataService;
    @Autowired
    private ServiceDataService serviceDataService;


    //Schnittstelle für getEmployee(singular) wäre möglich
    @GetMapping("/employees/{employeeId}")
    public EmployeeResource getEmployee(@PathVariable int employeeId) {
        return employeeDataService.getEmployeeResource(employeeId);
    }
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<EmployeeResource> getAllEmployees(){
        List<EmployeeResource> employeeResourceList = employeeDataService.getEmployeeResources();

        return employeeResourceList;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public EmployeeResource addEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeDataService.addEmployee(employeeDto);
    }

    @GetMapping("/services/{serviceId}")
    public ServiceResource getService(@PathVariable int serviceId) {
        return serviceDataService.getServiceResource(serviceId);
    }

    @GetMapping("/services")
    public List<ServiceResource> getAllServices() {
        List<ServiceResource> serviceResourceList = serviceDataService.getServiceResources();

        return serviceResourceList;
    }

    @PostMapping("/services")
    public ServiceResource addService(@RequestBody ServiceDto serviceDto) throws IOException {
            return serviceDataService.addService(serviceDto);
    }

    @DeleteMapping("/services/{serviceId}")
    public ServiceResource deleteService(@PathVariable int serviceId) {
        return serviceDataService.deleteService(serviceId);
    }

    @PutMapping("services/{serviceId}")
    public ServiceResource editService(@PathVariable int serviceId, @RequestBody ServiceDto serviceDto) throws IOException {
        return serviceDataService.editService(serviceId, serviceDto);
    }





}
