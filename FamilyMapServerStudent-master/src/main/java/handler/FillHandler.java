package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import request.FillRequest;
import result.FillResult;
import service.FillService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class FillHandler extends BaseHandler {
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("in Fill Handler");
        boolean success = false;

        try{
            Gson gson = new Gson();

            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();

                // get username and generations from url
                String path = exchange.getRequestURI().toString();
                String segments[] = path.split("/");
                String username = "";
                int generations = 4;

                //if generations was provided
                if (segments.length == 4) {
                    username = segments[segments.length - 2];
                    generations = Integer.parseInt(segments[segments.length - 1]);

                }
                //if generations was not provided
                else if (segments.length == 3) {
                    username = segments[segments.length - 1];
                    generations = 4;
                }

                assert (username != "");
                FillRequest req = new FillRequest(username, generations);

                FillService service = new FillService();
                FillResult result = service.fill(req);
                if (result.getSuccess()) {
                    // send response
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(result, resBody);
                    resBody.close();
                    success = true;
                    System.out.println("Fill Success!");
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
        } catch (Exception e) {
            System.out.println("in catch statement");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
