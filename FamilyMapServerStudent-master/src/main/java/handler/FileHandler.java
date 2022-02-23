package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.*;

public class FileHandler implements HttpHandler {

//    String filePathStr = "web" + FileSystems.getDefault().getSeparator();
//    Path filePath = FileSystems.getDefault().getPath(filePathStr);
//    Files.copy(filePath, httpExch.getResponseBody());
    String prefix;

    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if(exchange.getRequestMethod().toLowerCase().equals("get")){
                Headers reqHeaders = exchange.getRequestHeaders();
                String filePathStr = "web" + FileSystems.getDefault().getSeparator() + "index.html";
                System.out.println("File Path is: " + filePathStr);
                Path filePath = FileSystems.getDefault().getPath(filePathStr);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(filePath, respBody);
                respBody.close();
                success = true;
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
