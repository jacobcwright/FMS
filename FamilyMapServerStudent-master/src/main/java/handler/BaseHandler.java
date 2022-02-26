package handler;

import com.sun.net.httpserver.HttpHandler;

import java.io.InputStream;
import java.util.Scanner;

public abstract class BaseHandler implements HttpHandler {
    String StreamToString(InputStream inputBody) {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(inputBody);
        while(sc.hasNext()){
            sb.append(sc.next());
        }
        return sb.toString();
    }
}
