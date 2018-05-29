var path = document.location.pathname.substr(0, document.location.pathname.lastIndexOf("/"));
var wsUri = "ws://" + document.location.host + path + "/chatws";
var websocket = new WebSocket(wsUri);

var output = document.getElementById("status");
var textArea = document.getElementById("chatArea");
var sendBbutton = document.getElementById("sendButton");
var inputField = document.getElementById("userInput");

websocket.onopen = function(evt) {
    onOpen(evt);
};

function writeToScreen(message) {
    output.innerHTML += message + "<br>";
}

function onOpen() {
    writeToScreen("Connected to " + wsUri);
}

websocket.onmessage = function(evt) {
    onMessage(evt);
};

sendBbutton.onclick = function (evt) {
    sendUserInput();
};

inputField.onkeyup = function (evt) {
    var key = evt.keyCode || evt.which;
    if (key === 13){
        sendUserInput();
    }
};

function sendUserInput(){
    var message = inputField.value;
    sendText(message);
    inputField.value = "";
}

function sendText(message) {
    console.log("sending text: " + message);
    websocket.send(message);
}

function onMessage(evt) {
    console.log("received: " + evt.data);
    textArea.value += evt.data + "\n";
}

websocket.onerror = function(evt) {
    onError(evt);
};

function onError(evt) {
    console.error(evt.data);
}
