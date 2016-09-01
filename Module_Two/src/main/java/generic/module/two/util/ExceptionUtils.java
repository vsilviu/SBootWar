package generic.module.two.util;

import generic.module.two.exception.BusinessException;
import generic.module.two.exception.EntityNotFoundException;
import generic.module.two.exception.KommunityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Silviu on 8/23/16.
 */
public class ExceptionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    private ExceptionUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate!");
    }

    public static String getStackTraceMessageFromException(Throwable t) {
        try {
            int index = 0;
            String newLine = System.getProperty("line.separator");
            StringBuilder result = new StringBuilder("Eroare: ");
            result.append(t.toString());
            result.append(newLine);
            //add each element of the stack trace
            for (StackTraceElement element : t.getStackTrace()) {
                if (index < 15 && (result.length() + (element != null ? element.toString().length() : 0) < 2000)) {
                    result.append(element != null ? element.toString() : "");
                    result.append(newLine);
                }
                index++;
            }
            if (result.length() > 1999) {
                result = new StringBuilder(result.substring(0, 1998));
            }
            return result.toString();
        } catch (Exception e) {
            logger.error(getMessage(e), e);
            return t.getMessage();
        }
    }

    public static String getMessage(Throwable t) {
        if (t.getMessage() == null) {
            if (t instanceof EntityNotFoundException) {
                return "Entity not found exception: ";
            } else if (t instanceof BusinessException) {
                return "Business exception: ";
            } else if (t instanceof KommunityException) {
                return "Kommunity exception: ";
            } else {
                return "Generic exception: ";
            }
        } else {
            return t.getMessage();
        }
    }

}

