//비동기 공통함수

/*
* method: 요청 방식(GET, POST, PUT, DELETE)
* url: 요청 url
* data: 요청 데이터
* submitFunc: 성공시 콜백 함수
* errorFunc: 실패시 콜백 함수
* */
function customAjax(method, url, data, submitFunc, errorFunc) {
    $.ajax({
        url: url,
        data: data,
        method: method,
        processData: false,
        contentType: false,
        success: function (result) {
            if (submitFunc != null) {
                submitFunc(data);
            }
        },
        error: function (request, status, error) {
            if (errorFunc != null) {
                errorFunc(request, status, error);
            } else {
                console.log("에러");
            }
        },
        complete: function () {
            console.log("무조건실행");
        }
    })
};

export {customAjax}; //ajax 함수를 외부에서 사용할 수 있도록 export