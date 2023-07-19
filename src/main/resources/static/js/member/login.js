import {customAjax} from "../common.js";

$("#loginBtn").on("click", function () {

    let email = $("#emailInpt").val();
    let password = $("#passwordInpt").val();

    let data = {
        email : email,
        password : password,
        provider : "N"
    }

    customAjax("POST","/auth/login", data, function (data) {
        location.href="/main";
    });
})