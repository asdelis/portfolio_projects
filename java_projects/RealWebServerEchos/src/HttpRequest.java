import java.util.HashMap;
import java.util.Scanner;

//Andrew Delis

//The http request objects handle the initial http requests from the client.
//It takes the scanner and has two methods. One returns the requested file name from the
//first line of the header that the http response object will return. It also returns
//the full header that the http response object parses to see if the request is from a web socket.

class HttpRequest {
    Scanner _scan;
    String[] _splitRequest;
    HashMap<String, String> _headerInfo;

    //constructor
    HttpRequest(Scanner scan) {
        _scan = scan;
        _headerInfo = new HashMap<>();
    }

    //return the file name as a string
    String getFileName() {

        if (_scan.hasNext()) {
            String request = _scan.nextLine();
            _splitRequest = request.split("\\s+");
        }

        //return the file name if there is one
        String fileName = "";
        try {
             fileName = _splitRequest[1];
        }

        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Index not in bounds");
        }

        return fileName;

    }

    //function to get the full header
    HashMap<String, String> getFullHeader(){
        //create a while loop to read in the full header
        while (true) {

            //read all lines until there is an empty line
            String line = _scan.nextLine();
            if (line.isEmpty()) {
                break;
            }

            //parse the client request
            String[] parts = line.split(": ");

            //store parts in the hash map
            _headerInfo.put(parts[0], parts[1]);

        }
        return _headerInfo;
    }

}
