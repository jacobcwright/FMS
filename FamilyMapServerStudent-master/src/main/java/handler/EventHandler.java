package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dao.DataAccessException;
import request.EventRequest;
import result.EventResult;
import service.EventService;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class EventHandler extends BaseHandler {
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("in Event Handler");
        boolean success = false;

        try{
            Gson gson = new Gson();

            if(exchange.getRequestMethod().toLowerCase().equals("get")){
                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();

                // Extract the auth token from the "Authorization" header
                String authToken = reqHeaders.getFirst("Authorization");

                // make and run service
                EventRequest req = new EventRequest(authToken);
                EventService service = new EventService();
                EventResult result = service.event(req);

                // send response
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                success = true;

                System.out.println("Event Success!");
            }
            else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
        } catch (DataAccessException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
        }
    }
}
