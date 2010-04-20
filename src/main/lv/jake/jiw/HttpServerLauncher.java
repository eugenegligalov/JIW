package lv.jake.jiw;

import org.nanohttpd.NanoHTTPD;

import java.io.IOException;
import java.util.Properties;

/**
 * User: Eugene Gligalov
 * Date: 2010.21.4
 * Time: 00:08:49
 */
public class HttpServerLauncher extends NanoHTTPD {
    public HttpServerLauncher() throws IOException
    {
        super(8080);
    }

    public Response serve( String uri, String method, Properties header, Properties parms )
    {
        System.out.println( method + " '" + uri + "' " );
        String msg = "<html><body><h1>Hello server</h1>\n";
        if ( parms.getProperty("username") == null )
            msg +=
                "<form action='?' method='get'>\n" +
                "  <p>Your name: <input type='text' name='username'></p>\n" +
                "</form>\n";
        else
            msg += "<p>Hello, " + parms.getProperty("username") + "!</p>";

        msg += "</body></html>\n";
        return new NanoHTTPD.Response( HTTP_OK, MIME_HTML, msg );
    }

    public static void main( String[] args )
    {
        try
        {
            new HttpServerLauncher();
        }
        catch( IOException ioe )
        {
            System.err.println( "Couldn't start server:\n" + ioe );
            System.exit( -1 );
        }
        System.out.println( "Listening on port 8080. Hit Enter to stop.\n" );
        try { System.in.read(); } catch( Throwable t ) {};
    }
}
