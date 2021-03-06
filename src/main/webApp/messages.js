function sendMessage() {
    var text = $('#new_message').val().replace(/&/g, '&amp;').replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;').replace(/</g, '&lt;')
        .replace(/>/g, '&gt;');
    if (text === "") {
        return;
    }
    var userId = $.cookie("userId");

    $.ajax({
        url: "message",
        type: "POST",
        data: {"text": text, "userId": userId},
        success: [function (fromServerJson) {
            if(fromServerJson != null && fromServerJson.message !== undefined){
                alert(fromServerJson.message);
                return;
            }
            $("#new_message").val("");
        }],
        error: [function (e) {
            alert("Error" + JSON.stringify(e));
        }]
    });
}

$('#send').click(sendMessage);

$('#new_message').on('keypress',function(e) {
    if(e.which === 10 && e.ctrlKey) {
        sendMessage();
    }
});

function getMessages() {
    $.ajax({
        url: "message",
        type: "GET",
        success: [function (fromserver) {
            if(fromserver == null)
                return;
            if(fromserver.message !== undefined){
                alert(fromserver.message);
                return;
            }
            for (var i = fromserver.length -1; i >= 0; i--) {
                $('#messages').append("<p id='" + fromserver[i].user.id + "'>" + fromserver[i].date + " "
                    + "<span id='" + fromserver[i].user.name + "'>" +  fromserver[i].user.name + "</span>" + ": "
                    + fromserver[i].text + "</p>").scrollTop($('#messages')[0].scrollHeight);
            }
        }]
    });
}





