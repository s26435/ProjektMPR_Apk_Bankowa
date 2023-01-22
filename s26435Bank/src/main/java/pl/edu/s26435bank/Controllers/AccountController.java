package pl.edu.s26435bank.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.s26435bank.AccountService.AccountService;
import pl.edu.s26435bank.Exceptions.DataBaseException;
import pl.edu.s26435bank.Exceptions.DeclinedException;
import pl.edu.s26435bank.Exceptions.ValidationException;
import pl.edu.s26435bank.Model.Account;
import pl.edu.s26435bank.Repositories.AccountRepository;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping();
public class AccountController {
    AccountRepository repository = new AccountRepository();
    AccountService service = new AccountService(repository);

    @PostMapping("/create")//załuz konto
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            service.createNewAccount(account);
        } catch (DataBaseException dataBaseException) {
            return ResponseEntity.unprocessableEntity().build();
        }catch (ValidationException validationException){
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(account);
    }
    @PostMapping//wypłać pieniążki
    public ResponseEntity<Object> getMoneyOut(@RequestParam(name = "accountNumber") int accountNumber, @RequestParam(name = "amount")  int amount){
        try {
            service.decreaseBalance(accountNumber,amount);
        } catch (DeclinedException e) {
            return ResponseEntity.notFound().build();
        }catch (DataBaseException e){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping//wpłać pieniążki
    public ResponseEntity putMoneyIn(@RequestParam(name = "accountNumber") int accountNumber, @RequestParam(name = "amount")  int amount){
        try {
            service.increaseBalance(accountNumber, amount);
        }catch (DataBaseException e){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")//znajdz konto po id
    public ResponseEntity<Account> getAccount(@PathVariable int id) {
        Optional<Account> acc = service.findByAccountByNumber(id);
        return acc.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping//usuń
    public ResponseEntity deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

}
