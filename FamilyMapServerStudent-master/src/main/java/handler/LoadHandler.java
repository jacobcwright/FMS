package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * Handler for load service
 */
public class LoadHandler extends BaseHandler {
    /**
     * Handles the load service
     * Clears all data from the database (just like the /clear API)
     * Loads the user, person, and event data from the request body into the database.
     * @param exchange
     * @throws IOException
     */
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("in Load Handler");
        boolean success = false;

        try{
            Gson gson = new Gson();

            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();
                InputStream inputBody = exchange.getRequestBody();
                String reqData = StreamToString(inputBody);

                // check if req data is empty
                if(reqData.isEmpty()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                    return;
                }

                // make request
                LoadRequest request = gson.fromJson(reqData, LoadRequest.class);

                // make and call service
                LoadService service = new LoadService();
                LoadResult result = service.load(request);

                // send response
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                success = true;

                System.out.println("Load Success!");
                if(!success){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (Exception e){
            System.out.println("in catch statement");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
