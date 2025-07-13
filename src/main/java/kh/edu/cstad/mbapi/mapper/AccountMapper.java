package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account fromCreateAccountRequest(CreateAccountRequest createAccountRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(UpdateAccountRequest updateAccountRequest, @MappingTarget Account account);

    @Mapping(target = "customerName", source = "customer.fullName")
    AccountResponse toAccountResponse(Account account);
}
