package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
//import org.mapstruct.Mapper;
//
//@Mapper(componentModel = "spring")

public interface CustomerMapper {

    void toCustomerPartailly(


    );

    //DTO -> Model
    //Model -> DTO
    //what is source data? (parameter)
    //what is target data? (return_type)

    CustomerResponse mapCustomerToCustomerResponse(Customer customer);

//    Customer formCreateCustomerRequest(CreateCustomerRequest customerResponse);

}

