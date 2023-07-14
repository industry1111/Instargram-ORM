import {customAjax} from "../common.js";

const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const passwordPattern = /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#$%^&*()-_=+]{8,16}$/;
const namePattern = /^[a-zA-Z0-9-_ㄱ-힣]{4,20}$/;



$('#btn_register').on("click",function() {

        let email = $('#email');
        let password = $('#password');
        let name = $('#name');

        if (!emailPattern.test(email.val())) {
            alert("이메일 형식이 올바르지 않습니다.");
            email.val('')
            return;
        }
        if (!passwordPattern.test(password.val())) {
            alert("비밀번호 형식이 올바르지 않습니다.");
            password.val('');
            return;
        }
        if(!namePattern.test(name.val())){
            alert("이름 형식이 올바르지 않습니다.");
            name.val('');
            return;
        }

        let data = {
            email: email.val(),
            password: password.val(),
            name: name.val()
        }

        customAjax("POST", "/member/register", data, successCallback);
    });

function successCallback(data) {

    if(data != -1){
        location.href = "/member/login";
    }else {
        $('.form-signup div input').val('');
        alert("이미 존재하는 이메일입니다.");
    }
}
