package Connectionclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {
    /*
     * public static void main(String[] args) {
     * try {
     * // Send data to the server
     * sendDataToServer("{\"query\": \"show databases;\"}");
     * 
     * // Receive data from the server and store it as a file
     * receiveDataFromServerAndStoreAsFile();
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     */

    public static int sendDataToServer(String jsonData) throws Exception {
        URL url = new URL("http://localhost:5000/data");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        out.write(jsonData);
        out.flush();
        out.close();

        int responseCode = conn.getResponseCode();
        // System.out.println("Response Code from Server (Sending Data): " +
        // responseCode);
        conn.disconnect();

        return responseCode;
    }

    public static String receiveDataFromServerAndStoreAsFile() throws Exception {
        URL url = new URL("http://localhost:5000/data"); // Corrected URL
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        // Store received data as a file
        String receivedData = content.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter("received_data.json"));
        writer.write(receivedData);
        writer.close();

        System.out.println("Received data has been stored as 'received_data.txt'");

        return receivedData;
    }

    public static int connectToDB(String host, String user, String pass) {
        String json_data = "{\"host\": \"" + host + "\", \"user\": \"" + user + "\", \"password\": \"" + pass + "\"}";

        try {
            int response = sendDataToServer(json_data);
            return response;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int sendQuery(String query) {
        String query_data = "{\"query\":\"" + query + "\"}";
        try {
            int response = sendDataToServer(query_data);
            return response;
        } catch (Exception e) {
            return -1;
        }
    }

    public static String retrieveResults() {
        try {
            return receiveDataFromServerAndStoreAsFile();
        } catch (Exception e) {
            return "";
        }
    }
}
