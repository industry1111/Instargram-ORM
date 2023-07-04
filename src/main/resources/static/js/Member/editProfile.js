import {customAjax} from "../common.js";


const namePattern = /^[a-zA-Z0-9-_]{4,20}$/;

$('#btn_update').on('click', function () {
    let name = $('#name').val();
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
    data.append("name", name);
    data.append("introduction", introduction);
    data.append("id", id);
    data.append("provider", provider);
    data.append("file", file);
    data.append("status", status);

    console.log(data.get("picture"));


    customAjax("PUT", "/member/profile/edit", data, successCallback, failCallback);
});

function successCallback(data) {
    console.log(data);
    location.href = "/member/profile";
}

function failCallback(data) {

}
