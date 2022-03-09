package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * Handler for Log-in
 * Handlers (usually):
 * validates authtoken, deserialize Json req to Java, Calls service class, Receive result from service,
 * Serialize Java result to JSON, Send HTTP Response according to result
 */
public class LoginHandler extends BaseHandler {
    /**
     * login handle
     * @param exchange
     * @throws IOException
     */
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        // try to log-in
        try {
            Gson gson = new Gson();
            //post method?
            if(exchange.getRequestMethod().toLowerCase().equals("post")){
                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();
                InputStream inputBody = exchange.getRequestBody();
                String reqData = StreamToString(inputBody);

                // parse req body from json
                LoginRequest request = gson.fromJson(reqData.toString(), LoginRequest.class);

                // make login service & login
                LoginService service = new LoginService();
                LoginResult result = service.login(request);
                if(!result.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                    return;
                }

                // send response
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                success = true;
            }
            // if something failed
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
                return;
            }
            // error handling
        } catch(IOException ioe){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ioe.printStackTrace();
        }
        catch(DataAccessException dae){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            dae.printStackTrace();
        }
    }
}
