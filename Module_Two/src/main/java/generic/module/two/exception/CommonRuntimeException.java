package generic.module.two.exception;

/**
 * Created by Silviu on 8/25/16.
 */
public class CommonRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommonRuntimeException(String message) {
        super(message);
    }

    public CommonRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
