package ru.yandex.practicum.mybank.service.accounts.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountDto;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountFullDataDto;
import ru.yandex.practicum.mybank.service.accounts.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.accounts.model.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "name", source = "fullName")
    AccountDto toDto(Account account);

    List<AccountDto> toDto(List<Account> account);

    @Mapping(target = "name", source = "account.fullName")
    AccountFullDataDto toDto(Account account, List<Account> contacts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "balanceAmount", ignore = true)
    void updateFromDto(@MappingTarget Account account, UpdateAccountRequest dto);
}
