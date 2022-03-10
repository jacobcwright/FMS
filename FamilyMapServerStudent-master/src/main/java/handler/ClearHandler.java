package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import result.ClearResult;
import service.ClearService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * Handles the clear service
 */
public class ClearHandler extends BaseHandler {
    /**
     * clears everything from database
     * @param exchange
     * @throws IOException
     */
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("in clear handler");
        boolean success = false;

        try{
            Gson gson = new Gson();

            if(exchange.getRequestMethod().toLowerCase().equals("post")){
                System.out.println("in if statement");

                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();

                // make clear service & result
                ClearService service = new ClearService();
                ClearResult result = service.clear();

                // send response
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                success = true;

                System.out.println("Clear Success!");

            }
            // if something failed
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (Exception e) {
            System.out.println("in catch statement");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
