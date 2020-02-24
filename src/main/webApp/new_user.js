$('#user_name').blur(function () {
    var tryName = $('#user_name').val();
    $.ajax({
        type: "GET",
        url: "userInfo?name=" + tryName,
        success: [function (fromserver) {
            if(fromserver != null){
                alert("User already exists");
                $('#user_name').val("").focus();
            }
        }]
    });
});
