package pl.edu.s26435bank.AccountService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.edu.s26435bank.Exceptions.ValidationException;
import pl.edu.s26435bank.Model.Account;
import pl.edu.s26435bank.Repositories.AccountRepository;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AccountServiceTest {
    AccountRepository accountRepository = mock(AccountRepository.class);
    AccountService accountService = new AccountService(accountRepository);

    @Test
    public void shouldCreateNewAcc(){
        Account account = new Account("Jan", "Wolski", 2137,10000);
        assertDoesNotThrow(()->accountService.createNewAccount(account));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidAccount")
    void shouldThrowValidationException(Account account) {
        assertThrows(ValidationException.class, () -> accountService.createNewAccount(account));
    }

    @Test
    void shouldFindAccountById() throws Exception {
        Account account = new Account("Jan", "Wolski", 2137,10000);
        accountService.createNewAccount(account);
        assertNotNull(accountService.findByAccountByNumber(2137));
    }

    private static Stream<Arguments> provideInvalidAccount() {
        return Stream.of(
                Arguments.of(new Account("Jan", null, 2137, 1)),
                Arguments.of(new Account(null, "Brzechwa", 2222, 3))
        );
    }

}