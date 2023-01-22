package pl.edu.s26435bank.Repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.s26435bank.Exceptions.DataBaseException;
import pl.edu.s26435bank.Exceptions.DeclinedException;
import pl.edu.s26435bank.Model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Repository
public class AccountRepository {
    private List<Account> accountList = new ArrayList<>();


    public void createNewAccount(Account account) throws Exception {
        if(accountList.contains(account)) throw new Exception();
        accountList.add(account);
    }

    public Optional<Account> findByAccountNumber(int number) {
        return accountList.stream().filter(it -> it.getAccountNumber() ==   number).findFirst();
    }
    public void removeAll(){
        accountList = new ArrayList<Account>();
    }

    public Optional<Account> findById(int id) {
        return accountList.stream().filter(it -> it.getAccountNumber() == id).findFirst();
    }
    public List<Account> findAll(){return accountList;}

    public void decrease(int id, int amount) throws DeclinedException {
        try {
            Account temp = accountList.get(id);
            if(temp.getBalance()<amount) throw new DeclinedException("Not enough money on account");
            accountList.set(id, new Account(temp.getName(), temp.getSurname(), temp.getAccountNumber(), temp.getBalance() - amount));
        }catch (DeclinedException e){
         throw new DeclinedException(e.getMessage());
        }
        catch (Exception e){
            throw new DataBaseException("Account did not exist.");
        }
    }

    public void increase(int id, int amount){
        Account temp = accountList.get(id);
        accountList.set(id,new Account(temp.getName(), temp.getSurname(),temp.getAccountNumber(),temp.getBalance()+amount));
    }

}
