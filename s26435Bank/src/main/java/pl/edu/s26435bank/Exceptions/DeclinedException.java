package pl.edu.s26435bank.Exceptions;

public class DeclinedException extends Exception{
    String message;
    public DeclinedException(String message){
        this.message = message;
    }

}
