var chatVersion = 0;
var refreshRate = 2000; //miliseconds

//users = a list of usernames
function refreshUsersList(users) {
    //clear all current users
    $("#userslist").empty();
    $.each(users || [], function(index, username) {
        console.log("Adding user #" + index + ": " + username);
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $('<li>' + username + '</li>').appendTo($("#userslist"));
    });
}

//entries = the added chat strings represnted as a single string
function appendToChatArea(entries) {
//    $("#chatarea").children(".success").removeClass("success");
    $.each(entries || [], appendChatEntry);
    var scroller = $("#chatarea");
    var height = scroller[0].scrollHeight - $(scroller).height();

    $(scroller).stop().animate({ scrollTop: height }, "slow");
}

function appendChatEntry(index, entry){
    var entryElement = createChatEntry(entry);
    $("#chatarea").append(entryElement).append("<br>");
}

function createChatEntry (entry){
    entry.chatString = entry.chatString.replace (":)", "<span class='smiley'></span>");
    return $("<span class=\"success\">").append(entry.username + "> " + entry.chatString);
}

function ajaxUsersList() {
    $.ajax({
        url: "userslist",
        success: function(users) {
            refreshUsersList(users);
        }
    });
}

//call the server and get the chat version
//we also send it the current chat version so in case there was a change
//in the chat content, we will get the new string as well
function ajaxChatContent() {
    $.ajax({
        url: "chat",
        data: "chatversion=" + chatVersion,
        dataType: 'json',
        success: function(data) {
            console.log("Server chat version: " + data.version + ", Current chat version: " + chatVersion);
            if (data.version !== chatVersion) {
                chatVersion = data.version;
                appendToChatArea(data.entries);
            }
            triggerAjaxChatContent();
        },
        error: function(error) {
            triggerAjaxChatContent();
        }
    });
}

//add a method to the button in order to make that form use AJAX
//and not actually aubmit the form
$(function() { // onload...do
    //add a function to the submit event
    $("#chatform").submit(function() {
        jQuery.ajax({
            data: $(this).serialize(),
            url: this.action,
            timeout: 2000,
            error: function() {
                console.error("Failed to submit");
            },
            success: function(r) {
                //do not add the user string to the chat area
                //since it's going to be retrieved from the server
                //$("#result h1").text(r);
            }
        });

        $("#userstring").val("");
        // by default - we'll always return false so it doesn't redirect the user.
        return false;
    });
});

function triggerAjaxChatContent() {
    setTimeout(ajaxChatContent, refreshRate);
}

//activate the timer calls after the page is loaded
$(function() {

    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);
    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxChatContent();
});