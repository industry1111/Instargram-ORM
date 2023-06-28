import {customAjax} from "../common.js";

alert("123123");
function register(fd) {
    customAjax("POST", "/member/register", fd, function (data) {
        console.log(data)
        location.href = "/member/login";
    }, function (request, status, error) {
        alert("회원가입 실패");
        location.href = "/member/register";
    });
}

    $('#btn_register').on("click", function () {
        const email = $('#email').val();
        const password = $('#password').val();
        const name = $('#name').val();
        const phone = $('#phone').val();
        const role = "USER";
        const status = "PUBLIC";

        let fd = new FormData();
        fd.append("email", email);
        fd.append("password", password);
        fd.append("name", name);
        fd.append("phone", phone);
        fd.append("role", role);
        fd.append("status", status);

        register(fd);
    });
