/**
 * Created by user on 18.04.2016.
 */

function displayMessages(frame) {
    console.log(frame);
    var message = JSON.parse(frame.body);
    var vkUserID = $('#userInfo').attr('data-id');
    if (vkUserID == message.author.userID) {
        $('#msg_container').append(
            '<div class="row msg_container base_sent">' +
            '<div class="col-xs-8">' +
            '<div class="messages msg_sent">' +
            '<p>' + message.message + '</p>' +
            '<time>' + message.dateFormatted + '</time>' +
            '</div>' +
            '</div>' +
            '<div class="row col-md-4 avatar">' +
            '<a href="' + message.author.pageURL + '"><img src="' + message.author.photoURL + '" class="img-circle"/>' +
            '<span>' + message.author.name + ' ' + message.author.lastName + '</span>' +
            '</a>' +
            '</div>' +
            '</div>'
        );
    } else {
        $('#msg_container').append(
            '<div class="row msg_container base_sent">' +
            '<div class="col-md-10 col-xs-10">' +
            '<div class="messages msg_receive">' +
            '<p>' + message.message + '</p>' +
            '<time>' + message.dateFormatted + '</time>' +
            '</div>' +
            '</div>' +
            '<div class="col-md-2 col-xs-2 avatar">' +
            '<a href="' + message.author.pageURL + '"><img src="' + message.author.photoURL + '" class="img-circle img-responsive "/>' +
            '<span>' + message.author.name + ' ' + message.author.lastName + '</span>' +
            '</a>' +
            '</div>' +
            '</div>'
        );
    }
}


var connectCallback = function () {
    stomp.subscribe('/topic/message', displayMessages);
};

var errorCallback = function (error) {
    alert(error.headers.message);
};


function registrationVK() {
    window.location = "https://oauth.vk.com/authorize?client_id=5422686&redirect_uri=" + window.location.origin + "/login&display=page&response_type=code&v=5.50&state=abc";
}

