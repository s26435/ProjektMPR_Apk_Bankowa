package pl.edu.s26435bank.Exceptions;

public class ValidationException extends RuntimeException{
    String Message;
    public ValidationException(String message){
        this.Message=message;
    }
}
