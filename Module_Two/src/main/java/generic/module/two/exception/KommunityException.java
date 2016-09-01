package generic.module.two.exception;

/**
 * Created by mitziuro on 8/17/16.
 */
public class KommunityException extends Exception {

    private final KommunityExceptionCode code;

    public KommunityException(KommunityExceptionCode code) {
        this.code = code;
    }

    public KommunityExceptionCode getCode() {
        return code;
    }

}
