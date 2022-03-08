package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import request.EventRequest;
import request.PersonRequest;
import result.EventResult;
import result.PersonResult;
import service.EventService;
import service.PersonService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class PersonHandler extends BaseHandler {
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("in Person Handler");
        boolean success = false;

        try {
          Gson gson = new Gson();

            if(exchange.getRequestMethod().toLowerCase().equals("get")){
                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();

                // Extract the auth token from the "Authorization" header
                String authtoken = reqHeaders.getFirst("Authorization");

                // make and run service
                PersonRequest req = new PersonRequest(authtoken);
                PersonService service = new PersonService();
                PersonResult result = service.person(req);

                // send response
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                success = true;

                System.out.println("Person Success!");
                return;
            }
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();

        } catch (IOException | DataAccessException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
        }
    }
}
