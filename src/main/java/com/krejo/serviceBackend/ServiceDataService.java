package com.krejo.serviceBackend;

import com.krejo.serviceBackend.DTO.ServiceDto;
import com.krejo.serviceBackend.Data.Employee;
import com.krejo.serviceBackend.Data.Service;
import com.krejo.serviceBackend.Resource.ServiceResource;

import java.util.ArrayList;
import java.util.List;

public class ServiceDataService {

    List<Service> serviceList = new ArrayList<>();

    public void createInitialListEntries() {
        Service s1 = new Service();
        s1.setName("test1");
        s1.setEmployeeId(1);
        s1.setDate("01.01.2001");
        s1.setLongitude("longitute1");
        s1.setLatitude("latitude1");

        Service s2 = new Service();
        s2.setName("test2");
        s2.setEmployeeId(2);
        s2.setDate("02.01.2002");
        s2.setLongitude("longitute2");
        s2.setLatitude("latitude2");


        serviceList.add(s1);
        serviceList.add(s2);

    }

    public List<ServiceResource> getServiceResources() {
        List<ServiceResource> result = new ArrayList<>();

        for (Service service :
                serviceList) {
            ServiceResource serviceResource = convertServiceToServiceResource(service);

            result.add(serviceResource);
        }

        return result;
    }

    private ServiceResource convertServiceToServiceResource(Service service) {
        ServiceResource serviceResource = new ServiceResource(service.getId());
        serviceResource.setName(service.getName());
        serviceResource.setEmployeeId(service.getEmployeeId());
        serviceResource.setDate(service.getDate());
        serviceResource.setLongitude(service.getLongitude());
        serviceResource.setLatitude(service.getLatitude());

        return serviceResource;
    }

    public ServiceResource addService(ServiceDto serviceDto) {
        Service service = new Service();
        service.setName(serviceDto.getName());
        service.setDate(serviceDto.getDate());
        //Todo: Longitude und Latitude vom entsprechenden Employee getten und don in serviceResource speichern

        service.setEmployeeId(serviceDto.getEmployeeId());

        service.setLongitude();

    }
}
