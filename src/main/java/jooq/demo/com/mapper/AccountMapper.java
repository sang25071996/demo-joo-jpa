package jooq.demo.com.mapper;

import jooq.demo.com.dto.AccountDTO;
import jooq.demo.com.entites.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  AccountDTO toDTO(Account account);
}
