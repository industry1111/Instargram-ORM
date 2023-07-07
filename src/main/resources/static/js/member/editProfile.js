import {customAjax} from "../common.js";


const namePattern = /^[a-zA-Z0-9-_]{4,20}$/;

$('#btn_update').on('click', function () {
    let name = $('#name');
    let introduction = $('#introduction').val();
    let id = $('#id').val();
    let provider = $('#provider').val();
    let file = $('#profile_image')[0].files[0];
    let status = $('#status').val();

    if(!namePattern.test(name.val())){
        alert("이름 형식이 올바르지 않습니다.");
        name.val('');
        return;
    }

    let data = new FormData();
    data.append('name', name.val());
    data.append('introduction', introduction);
    data.append('id', id);
    data.append('provider', provider);
    data.append('file', file);
    data.append('status', status);
    console.log(data);
    customAjax("PUT", "/member/profile/edit", data, successCallback);
});

function successCallback(data) {
    console.log(data);
    location.href = "/member/profile/"+data.id;
}

window.onload = function (){

    let lastSeenText = document.getElementById('lastSeenText')
    let modify = new Date(lastSeenText.textContent);
    let now = new Date();
    let duration = now - modify;

    let years = Math.floor(duration / (1000 * 60 * 60 * 24 * 365));
    if (years >= 1) {
        lastSeenText.textContent = years + ' years ago';
    } else {
        let months = Math.floor(duration / (1000 * 60 * 60 * 24 * 30));
        if (months >= 1) {
            lastSeenText.textContent = months + ' months ago';
        } else {
            let days = Math.floor(duration / (1000 * 60 * 60 * 24));
            if (days >= 1) {
                lastSeenText.textContent = days + ' days ago';
            } else {
                let hours = Math.floor(duration / (1000 * 60 * 60));
                if (hours >= 1) {
                    lastSeenText.textContent = hours + ' hours ago';
                } else {
                    let minutes = Math.floor(duration / (1000 * 60));
                    lastSeenText.textContent = minutes + ' minutes ago';
                }
            }
        }
    }
}