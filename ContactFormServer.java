import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ContactFormServer {
    private static final String FILE_PATH = "contacts.txt";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/submit", new ContactFormHandler());
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server started at http://localhost:8080");
    }

    static class ContactFormHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Parse the form data
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder formData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    formData.append(line);
                }
                reader.close();

                // Decode and process the form data
                Map<String, String> data = parseFormData(formData.toString());
                String entry = String.format("Name: %s%nEmail: %s%nMessage: %s%n---%n",
                        data.get("name"), data.get("email"), data.get("message"));

                // Append to file
                appendToFile(entry);

                // Send response
                String response = "Data successfully saved!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private void appendToFile(String entry) {
            try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
                fw.write(entry);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
            Map<String, String> data = new HashMap<>();
            String[] pairs = formData.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
                data.put(key, value);
            }
            return data;
        }
    }
}
