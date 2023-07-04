import {customAjax} from "../common.js";

$('#btn_update').on('click', function () {
    let name = $('#name').val();
    let bio = $('#bio').val();
    let id = $('#id').val();
    let provider = $('#provider').val();

    let data = {
        name: name,
        bio: bio,
        id: id,
        provider: provider
    };
    console.log(data);


    customAjax("PUT", "/member/profile/edit", data, successCallback, failCallback);
});

function successCallback(data) {
    console.log(data);
    location.href = "/member/profile";
}

function failCallback(data) {

}
