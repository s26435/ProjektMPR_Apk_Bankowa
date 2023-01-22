package pl.edu.s26435bank.Exceptions;

public class DataBaseException extends RuntimeException{
    String message;
    public DataBaseException(String message){
        this.message = message;
    }
}
