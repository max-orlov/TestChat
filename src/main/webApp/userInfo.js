$('html').on('mouseover', '.userData', function () {
    var id = $.cookie("userId");
    $.ajax({
        url: "userInfo?id=" + id,
        type: "GET",
        success: [function (fromserver) {
            $('.infoList').append("<img alt='Изображение отсутствует' height='200px' width='200px' src='userImg?id=" + id + "'>"
                + "<p><strong>" + "Имя пользователя : " + "</strong>" + fromserver.name + "</p>"
                + "<p><strong>" + "Ф.И.О : " + "</strong>" + fromserver.FIO + "</p>"
                + "<p><strong>" + "Телефон : " + "</strong>" + fromserver.tel + "</p>"
                + "<p><strong>" + "E-mail : " + "</strong>" + fromserver.e_mail + "</p>").slideDown();
        }]
    });
});

$('html').on('mouseleave', '.userData', function () {
    $('.infoList').slideUp().empty();
});

$('#messages').on('mouseover', 'span', function () {
    var name = $(this).text();
    $.ajax({
        url: "userInfo?name="+name,
        type: "GET",
        success: [function (fromserver) {
            $('.infoList').append(
                "<img alt='Изображение отсутствует' height='200px' width='200px' src='userImg?id=" + fromserver.id + "'>"
                + "<p><strong>" + "Имя пользователя : " + "</strong>" + fromserver.name + "</p>"
                + "<p><strong>" + "Ф.И.О : " + "</strong>" + fromserver.FIO + "</p>"
                + "<p><strong>" + "Телефон : " + "</strong>" + fromserver.tel + "</p>"
                + "<p><strong>" + "E-mail : " + "</strong>" + fromserver.e_mail + "</p>").slideDown();
        }]
    });
});

$('#messages').on('mouseleave', 'span', function () {
    $('.infoList').slideUp().empty();
});