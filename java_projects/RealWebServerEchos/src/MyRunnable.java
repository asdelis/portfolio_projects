import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

//Andrew Delis

//My runnable creates a handful of objects that do most of the actual processing of data.
//It creates a scanner, a data input stream, and an output stream with the client that we passed to it.
//Then it creates a http request object that will handle the initial http requests.
//It also creates a http response object that returns the requested files
//and handles all subsequent web socket requests
//also closes the socket

public class MyRunnable implements Runnable{

    //the socket that we passed
    public Socket _client;

    //constructor
    MyRunnable(Socket client){
        _client = client;
    }
    @Override
    public void run() {

        //wrap the client socket in a scanner
        Scanner scan;
        try {
            scan = new Scanner( _client.getInputStream() );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        //wrap the client socket in a data input stream
        DataInputStream dataInputStream;
        try {
            dataInputStream = new DataInputStream( _client.getInputStream() );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        //create an output stream
        OutputStream outputStream;
        try {
            outputStream = _client.getOutputStream();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        //create a request object
        //get the file name and header info
        HttpRequest request = new HttpRequest(scan);
        String fileName = request.getFileName();
        HashMap<String,String> headerInfo = request.getFullHeader();

        //create a response object and send response headers
        //get full header and pass it to the response object
        HttpResponse response;
        try {
            response = new HttpResponse(outputStream, fileName, headerInfo, dataInputStream);
            response.sendHttpHeadersAndFiles();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        //close the socket
        try {
            _client.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
