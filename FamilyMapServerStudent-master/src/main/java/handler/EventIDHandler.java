package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import request.EventIDRequest;
import result.EventIDResult;
import service.AuthtokenChecker;
import service.EventIDService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class EventIDHandler extends BaseHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("in EventID Handler");
        boolean success = false;

        try {
            Gson gson = new Gson();

            if(exchange.getRequestMethod().toLowerCase().equals("get")){
                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();

                //check auth
                if(reqHeaders.containsKey("Authorization")) {
                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");

                    // get id from url
                    String path = exchange.getRequestURI().toString();
                    String segments[] = path.split("/");
                    String id = segments[segments.length - 1];

                    // create request object
                    EventIDRequest req = new EventIDRequest(id, authToken);

                    // make register service & login
                    EventIDService service = new EventIDService();
                    EventIDResult result = service.eventID(req);

                    // check Result
                    if(!result.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                        gson.toJson(result, resBody);
                        resBody.close();
                        exchange.getResponseBody().close();
                        return;
                    }

                    if (result != null) {
                        // send response
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                        gson.toJson(result, resBody);
                        resBody.close();
                        success = true;

                        System.out.println("EventID Success!");
                    }
                }
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(Exception e){
            System.out.println("in catch statement");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }
}
