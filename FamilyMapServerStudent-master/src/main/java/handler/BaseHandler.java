package handler;

import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public abstract class BaseHandler implements HttpHandler {
    /**String StreamToString(InputStream inputBody) {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(inputBody);
        while(sc.hasNext()){

            sb.append(sc.next());
        }
        return sb.toString();
    }
     **/
    /*
		The readString method shows how to read a String from an InputStream.
	*/
    String StreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

}
