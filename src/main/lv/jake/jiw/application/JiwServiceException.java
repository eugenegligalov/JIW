package lv.jake.jiw.application;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 16, 2010
 * Time: 2:29:07 PM
 */
public class JiwServiceException extends Exception {
    public JiwServiceException() {
    }

    public JiwServiceException(String message) {
        super(message);
    }

    public JiwServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiwServiceException(Throwable cause) {
        super(cause);
    }
}
