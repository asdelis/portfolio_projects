import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

//Andrew Delis

//My room class holds the name of the room, and a map of users in the room. That map also
//holds the output stream to the users' clients. "Get room" uses the constructor to make a room
//if a room of that name doesn't already exist. There are methods for adding and removing
//users and their clients from the map of users. Lastly, there is a method that sends messages
//to all members of a room.

public class Room {

    //my list of rooms
    static Map < String, Room > _rooms = new HashMap<>();

    //list of users in a room
    Map< String, OutputStream > _users = new HashMap<>();

    //Constructor
    String _roomName;
    private Room ( String roomName) {
        _roomName = roomName;
    }

    public synchronized static Room getRoom (String roomName) throws Exception {

        //create a variable called room and see if it is already in _rooms
        Room room = _rooms.get( roomName );

        //if roomName does not exist yet
        if( room == null ) {

            //create room
            room = new Room( roomName );

            //add room to rooms
            _rooms.put( roomName, room );

        }
        return room;
    }

    public void addClient(String userName, OutputStream _outputstream) {
        //add a user to the room
        _users.put( userName, _outputstream);
    }

    public void sendMessageToClients( int len, String message ) throws IOException {

        for ( Map.Entry<String, OutputStream> entry : _users.entrySet() ) {

            OutputStream outputStream = entry.getValue();

            //output the message we received from the data input stream in the response class
            DataOutputStream dataOutputStream = new DataOutputStream( outputStream );

            //write out the binary header to show that this is the last message,
            //there is no mask, and that our message will be text ( opcode )
            dataOutputStream.writeByte( 0x81 );

            //write out the length
            dataOutputStream.writeByte( len );

            //echo the message
            dataOutputStream.writeBytes( message );
            dataOutputStream.flush();

        }
    }

    public void removeClient( String userName ) {
        //remove the user from the member variable _users
        _users.remove( userName );
    }

}
