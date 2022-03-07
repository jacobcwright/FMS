package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;
import service.LoginService;
import service.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * Handler for Log-in
 * Handlers (usually):
 * validates authtoken, deserialize Json req to Java, Calls service class, Receive result from service,
 * Serialize Java result to JSON, Send HTTP Response according to result
 */
public class RegisterHandler extends BaseHandler {

    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("in RegisterHandler");
        boolean success = false;

        try {
            Gson gson = new Gson();

            if(exchange.getRequestMethod().toLowerCase().equals("post")){
                System.out.println("in if statement");

                // get req headers & req body
                Headers reqHeaders = exchange.getRequestHeaders();
                InputStream inputBody = exchange.getRequestBody();
                String reqData = StreamToString(inputBody);

                System.out.println(reqData);
                if(reqData.isEmpty()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
                // parse req body from json
                RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);

                // make register service & login
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);

                // send response
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                success = true;

                System.out.println("Register Success!");
            }
            // if something failed
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }

        } catch(Exception e){
            System.out.println("in catch statement");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
