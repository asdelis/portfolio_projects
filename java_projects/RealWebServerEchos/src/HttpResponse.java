import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;

//Andrew Delis

//The response object does most of the heavy lifting. It sends the requested files
//based on the file name passed from the request object, and handles all the websocket
//requests and responses. This includes the handshake with the web socket and reading
//the binary data packet.

class HttpResponse {

    //member variables
    OutputStream _outputStream;
    String _filename;
    File _file;
    HashMap<String, String> _headerInfo;
    DataInputStream _dataInputStream;
    int _messageLen;

    //constructor
    HttpResponse( OutputStream outputStream,
                  String fileName,
                  HashMap<String, String> headerInfo,
                  DataInputStream dataInputStream) throws IOException {

        //save the file, output stream, header info, and the data input stream
        _outputStream = outputStream;
        _filename = fileName;
        _file = new File("/Users/andrewdelis/Desktop/CS6011/Week4/Day17/RealWebServerEchos/src/resources" + _filename);
        _headerInfo = headerInfo;
        _dataInputStream = dataInputStream;

    }

    void sendHttpHeadersAndFiles() throws Exception {

        //handle header for websocket
        boolean isWsRequest = _headerInfo.containsKey("Sec-WebSocket-Key");

        if (isWsRequest){
            //handles the web socket communication
            handleWebSocketCommunication();
        }

        else {

            System.out.println("Not a web socket request");

            //handle header for files without web sockets
            if (_file.exists()) {

                //output the header
                _outputStream.write(("HTTP/1.1 200 OK\r\n").getBytes());

                //determine what type of file it is by splitting on the period
                String[] splitFileName = _filename.split("\\.");

                //save the file type if there is one
                if (splitFileName.length > 1) {
                    String fileType = "";
                    try {
                        fileType = splitFileName[1];
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Index not in bounds");
                    }
                    //send the second line of the header depending on the type of file
                    if(Objects.equals(fileType, "html")) {
                        _outputStream.write(("Content-type: text/html\r\n").getBytes());
                    }
                    else if(Objects.equals(fileType, "css")) {
                        _outputStream.write(("Content-type: text/css\r\n").getBytes());
                    }
                    else if(Objects.equals(fileType, "jpg")) {
                        _outputStream.write(("Content-type: image/jpeg\r\n").getBytes());
                    }
                    //end the header with an extra line
                    _outputStream.write(("\n").getBytes());

                    //create a file input stream and send through the output stream
                    FileInputStream fileInputStream = new FileInputStream(_file);

                    //flush and close the stream in the recommended way
                    for( int i = 0; i < _file.length(); i++ ) {
                        _outputStream.write(fileInputStream.read());
                        _outputStream.flush();
                    }
                }
            }

            else {
                _outputStream.write(("HTTP/1.1 404 Not Found\r\n").getBytes());
                _outputStream.write(("Content-type: text/html\r\n").getBytes());
                _outputStream.write(("\r\n").getBytes());
                System.out.println(_file + " does not exist" );
                System.out.println(_filename + " does not exist" );
            }

        }
    }

    void handleWsHandshake() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        System.out.println("This is a web socket request");

        //get the given key info
        String key = _headerInfo.get("Sec-WebSocket-Key");

        //and attach the response string to it
        key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

        //encode the key
        String encodeKey = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1")
                .digest((key).getBytes("UTF-8")));

        //write out the new header
        PrintWriter printWriter = new PrintWriter( _outputStream );
        printWriter.print("HTTP/1.1 101 Switching Protocols\r\n");
        printWriter.print("Upgrade: websocket\r\n");
        printWriter.print("Connection: Upgrade\r\n");
        printWriter.print("Sec-WebSocket-Accept: " + encodeKey + "\r\n");
        printWriter.print("\r\n"); //end the header
        printWriter.flush();

    }

    String handleBinaryHeader() throws Exception {

        //read in the first two bytes after the handshake is complete
        byte b0 = _dataInputStream.readByte();
        byte b1 = _dataInputStream.readByte();

        //get the opcode and length and check if it is masked
        int opcode = b0 & 0x0F;
        _messageLen = b1 & 0x7F;
        boolean isMasked = (b1 & 0x80) != 0;

        //if it is masked throw and error
        if (!isMasked) {
            System.out.println("ERROR");
            throw new Exception("Unmasked message from client");
        }

        //read in the mask and the payload
        byte[] mask = _dataInputStream.readNBytes(4);
        byte[] payload = _dataInputStream.readNBytes(_messageLen);

        //decode the payload
        for (int i = 0; i < payload.length; i++) {
            payload[i] = (byte) (payload[i] ^ mask[i % 4]);
        }

        //convert the payload to a string
        String message = new String(payload);
        return message;

    }

    private void handleWebSocketCommunication() throws Exception {

        //do the handshake with the web socket
        handleWsHandshake();

        while (true) {

            //save the decoded message from the handle binary method
            String message = handleBinaryHeader();
            System.out.println(message);

            //get the room name, user, and type from the message
            String type = message.split("\"type\":\"")[1].split("\"")[0];
            String user = message.split("\"user\":\"")[1].split("\"")[0];
            String roomName = message.split("\"room\":\"")[1].split("\"")[0];

            if (type.equals( "join" )){
                Room theRoom = Room.getRoom( roomName );
                theRoom.addClient( user, _outputStream );
                theRoom.sendMessageToClients( _messageLen, message );
            }

            if (type.equals( "message" )){
                if ( message.equals("member") ) {

                }
                Room theRoom = Room.getRoom( roomName );
                //theRoom.addClient( user, _outputStream );
                theRoom.sendMessageToClients( _messageLen, message );
            }

            if (type.equals( "leave" )){
                Room theRoom = Room.getRoom( roomName );
                theRoom.removeClient( user );
                theRoom.sendMessageToClients( _messageLen, message );
            }

        }
    }
}
