package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.*;

public class FileHandler extends BaseHandler {

    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if(exchange.getRequestMethod().toLowerCase().equals("get")){
                Headers reqHeaders = exchange.getRequestHeaders();
                String url = exchange.getRequestURI().toString();
                String filePathStr = "web" + url;
                if(filePathStr.equals("web/")){
                    filePathStr = "web/index.html";
                }
                System.out.println("File Path is: " + filePathStr);
                Path filePath = FileSystems.getDefault().getPath(filePathStr);
                if(!Files.exists(filePath)){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    filePath = FileSystems.getDefault().getPath("web/HTML/404.html");
                    Files.copy(filePath, respBody);
                    respBody.close();
                    return;
                }
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(filePath, respBody);
                respBody.close();
                success = true;
            }
            else {
                // not using get
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getResponseBody().close();
            }

            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }


    }

}
