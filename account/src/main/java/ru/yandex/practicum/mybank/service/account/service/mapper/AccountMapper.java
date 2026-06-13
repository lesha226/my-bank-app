package ru.yandex.practicum.mybank.service.account.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.mybank.service.account.dto.AccountLiteResponse;
import ru.yandex.practicum.mybank.service.account.dto.AccountDetailResponse;
import ru.yandex.practicum.mybank.service.account.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.account.model.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

    AccountLiteResponse toDto(Account account);

    List<AccountLiteResponse> toDto(List<Account> contacts);

    AccountDetailResponse toDto(Account account, List<Account> contacts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "balanceAmount", ignore = true)
    void updateFromDto(@MappingTarget Account account, UpdateAccountRequest dto);
}
