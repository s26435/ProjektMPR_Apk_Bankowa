package pl.edu.s26435bank.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

@AllArgsConstructor
@Data
@With
public class Account {
    String name;
    String surname;
    int accountNumber;
    int balance;
}
