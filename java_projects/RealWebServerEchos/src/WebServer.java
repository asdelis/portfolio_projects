import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//Andrew Delis

//This is my server class. It holds my main function. It only does three things.
//  1. It creates the server socket,
//  2. listens to requests from the server,
//  3. and creates/starts a thread that takes the client socket

//My extra feature is adding a timestamp to all messages

class WebServer {

    public static void main(String[] args) throws IOException {

        //create the server socket
        ServerSocket server = new ServerSocket(8080);

        while (true) {

            //create the client socket and initialize it by having it
            //always listen to the server (using a while loop)
            Socket client = server.accept();

            //create a new thread for every request and run the runnable
            Thread thread = new Thread(new MyRunnable(client));
            thread.start();
        }
    }
}