package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dao.DataAccessException;
import request.PersonIDRequest;
import result.PersonIDResult;
import service.PersonIDService;
import service.PersonService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class PersonIDHandler extends BaseHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("in PersonID Handler");
        boolean success = false;

        try{
            Gson gson = new Gson();
            if(exchange.getRequestMethod().toLowerCase().equals("get")) {
                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();

                // Extract the auth token from the "Authorization" header
                String authToken = reqHeaders.getFirst("Authorization");

                // get id from url
                String path = exchange.getRequestURI().toString();
                String segments[] = path.split("/");
                String id = segments[segments.length - 1];

                // make and run service
                PersonIDRequest request = new PersonIDRequest(id, authToken);
                PersonIDService service = new PersonIDService();
                PersonIDResult result = service.personID(request);

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

                    System.out.println("PersonID Success!");
                }
                return;
            }
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
        }
        catch (IOException | DataAccessException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
        }
    }
}
