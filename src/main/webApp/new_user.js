$('#user_name').blur(function () {
    var tryName = $('#user_name').val();
    $.ajax({
        type: "GET",
        url: "userInfo?name=" + tryName,
        success: [function (fromserver) {
            if(fromserver != null){
                if(fromserver.message !== undefined){
                    alert(fromserver.message);
                    return;
                }
                alert("User already exists");
                $('#user_name').val("").focus();
            }
        }]
    });
});
