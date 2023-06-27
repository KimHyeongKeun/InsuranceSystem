package exception;

public class InvalidCostEnterException extends Exception {
    public InvalidCostEnterException() {
        super("음수를 입력했습니다. 최대 보장 금액은 양수만 입력가능합니다.");
    }

    public InvalidCostEnterException(String message) {
        super(message);
    }

}
