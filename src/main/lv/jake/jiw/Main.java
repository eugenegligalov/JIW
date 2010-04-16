package lv.jake.jiw;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lv.jake.jiw.application.JiwServiceException;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 15, 2010
 * Time: 4:46:03 PM
 */
public class Main {
    public static void main(String[] args) throws JiwServiceException {
        final Injector injector = Guice.createInjector(new JiwModule(args[0]));

        final Console console = injector.getInstance(Console.class);

        console.startProcessingUserInput();
    }
}
