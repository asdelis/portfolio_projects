"use strict";

//VARIABLES
//set up web socket
let ws = new WebSocket('ws://localhost:8080');

//create variables for the inputs
let userNameInput = document.getElementById('userName');
let roomNameInput = document.getElementById('roomName');
let messageInput = document.getElementById('messageInput');

//create variables for the two main divs
let nameBoxDiv = document.getElementById('nameBox');
let messageBoxDiv = document.getElementById('messageBox');

//create variables for the buttons
let sendMessageBtn = document.getElementById('sendMessageBtn');
let leaveBtn = document.getElementById('leaveBtn');

//create a boolean for the room
let inRoom = false;

//create timestamp object
const localDate = new Date();
const timestamp =  localDate.toLocaleTimeString();

//LISTENERS
//set up a listener on the room name input
//if enter is pressed then send the join message
//ws.onopen = handleOpen;
roomNameInput.addEventListener('keyup', (e) => {
    if (e.key === 'Enter'){
        joinMessage();
    }
});

//set up a listener for when the server receives a message
ws.onmessage = handleMessage;

//set up a listener for the send message button
sendMessageBtn.onclick = messageMessage;

//set up a listener for the leave button
leaveBtn.onclick = leaveMessage;

//MESSAGE FUNCTIONS
//Functions for the different messages
function joinMessage() {
    if (roomNameInput.value === String(roomNameInput.value).toLowerCase()) {

        let jsonMessage = {
            "type"    : "join",
            "user"    : userNameInput.value,
            "room"    : roomNameInput.value,
            "time"    : timestamp,
        }
        ws.send(JSON.stringify(jsonMessage));

        inRoom = true;
    }
    else {
        alert('Room name must be all lower-case');
    }
}

function messageMessage() {
    if (inRoom) {

        let jsonMessage = {
            "type"    : "message",
            "user"    : userNameInput.value,
            "room"    : roomNameInput.value,
            "message" : messageInput.value,
            "time"    : timestamp,
        }
        ws.send(JSON.stringify(jsonMessage));

    }
    else {
        alert('must be in room to send a message');
    }
}

function leaveMessage() {

    let jsonMessage = {
        "type"    : "leave",
        "user"    : userNameInput.value,
        "room"    : roomNameInput.value,
        "time"    : timestamp,
    }
    ws.send(JSON.stringify(jsonMessage));

}

//HANDLE FUNCTIONS
function handleMessage(event) {

    //get the data from the message
    let msgString = JSON.parse(event.data);

    //break out its contents
    let msgRoom = msgString.room;
    let msgUser = msgString.user;
    let msgMessage = msgString.message;

    //add elements to the page based on the message's type
    if (msgString.type === 'join'){

        //create message and name paragraphs
        let msgPara = document.createElement('p');
        let namePara = document.createElement('p');

        //add the message
        msgPara.innerHTML = `${msgUser} has joined room: ${msgRoom}`;
        messageBoxDiv.append(msgPara);
        messageBoxDiv.append(timestamp);

        //and the name
        namePara.innerHTML = msgUser;
        nameBoxDiv.append(namePara);
    }

    else if (msgString.type === 'message'){

        //create message and name paragraphs
        let msgPara = document.createElement('p');

        //add the message
        msgPara.innerHTML = msgMessage;
        messageBoxDiv.append(msgPara);
        messageBoxDiv.append(timestamp);
    }

    else if (msgString.type === 'leave'){

        //create message and name paragraphs
        let msgPara = document.createElement('p');

        //add the message
        msgPara.innerHTML = `${msgUser} has left room: ${msgRoom}`;
        messageBoxDiv.append(msgPara);
        messageBoxDiv.append(timestamp);
    }

}



