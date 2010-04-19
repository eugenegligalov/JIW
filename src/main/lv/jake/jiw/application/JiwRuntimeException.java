package lv.jake.jiw.application;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 3:12:22 PM
 */
public class JiwRuntimeException extends RuntimeException {
    public JiwRuntimeException() {
    }

    public JiwRuntimeException(String message) {
        super(message);
    }

    public JiwRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiwRuntimeException(Throwable cause) {
        super(cause);
    }
}
