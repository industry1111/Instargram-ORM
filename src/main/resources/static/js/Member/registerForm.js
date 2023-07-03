import {customAjax} from "../common.js";

const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const passwordPattern = /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#$%^&*()-_=+]{8,16}$/;
const namePattern = /^[a-zA-Z0-9-_]{4,20}$/;


$('#btn_register').on("click",function() {
        let email = $('#email');
        let password = $('#password');
        let name = $('#name');
        let role = "USER";
        let status = "PUBLIC";
        let provider = "LOCAL";
        let is_Auth = "N";
        let picture = "";

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
            email : email.val(),
            password : password.val(),
            name : name.val(),
            role : role,
            status : status,
            provider : provider,
            is_Auth : is_Auth,
            picture : picture

        }

        customAjax("POST", "/member/register", data, successCallback, failCallback);
    });

function successCallback(data) {
    console.log(data);
    if(data === 1){
        alert("회원가입 성공");
        location.href = "member/login";
    }else if(data === -1){
        $('.form-signup div input').val('');
        alert("이미 존재하는 이메일입니다.");
    }
}

function failCallback() {
    console.log(data);
    alert("회원가입 실패");
}
