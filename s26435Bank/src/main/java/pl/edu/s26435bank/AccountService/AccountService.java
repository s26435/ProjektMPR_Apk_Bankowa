package pl.edu.s26435bank.AccountService;


import org.springframework.stereotype.Service;
import pl.edu.s26435bank.Exceptions.DataBaseException;
import pl.edu.s26435bank.Exceptions.DeclinedException;
import pl.edu.s26435bank.Exceptions.ValidationException;
import pl.edu.s26435bank.Model.Account;
import pl.edu.s26435bank.Repositories.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class AccountService {
    AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public void createNewAccount(Account account) throws Exception {
        if(isInvalid(account.getName())){
            throw new ValidationException("Name is required!");
        }
        if(isInvalid(account.getSurname())){
            throw new ValidationException("Surname is required!");
        }
        try{
            accountRepository.createNewAccount(account);
        }catch (Exception e){
            throw new DataBaseException("Database error: " + e);
        }
    }

    public Optional<Account>findByAccountByNumber(int number){
        return accountRepository.findByAccountNumber(number);
    }

    public void decreaseBalance(int id, int amount) throws DeclinedException {
        accountRepository.decrease(id, amount);
    }

    public void increaseBalance(int id, int amount){
        accountRepository.increase(id, amount);
    }

    private boolean isInvalid(String attribute) {
        return attribute == null || attribute.isBlank();
    }
    public void deleteAll() {
        accountRepository.removeAll();
    }


}
