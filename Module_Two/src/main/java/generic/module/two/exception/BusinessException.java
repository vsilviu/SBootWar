package generic.module.two.exception;

/**
 * Exception with cod and value. If there are specialized exception then need to extend this class
 *
 * @author Mihai Plavichianu
 */
public class BusinessException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String code;

    public BusinessException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BusinessException(String code) {
        super(code);
        this.code = code;
    }


    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(code != null ? code : " unknown code");
        sb.append("   ");
        String message = super.getMessage();
        if (message != null) {
            sb.append(message.substring(message.indexOf(':') + 1));
        }
        return sb.toString();
    }

    public String getCode() {
        return code;
    }

}
