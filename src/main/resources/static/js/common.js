//비동기 공통함수

/*
* method: 요청 방식(GET, POST, PUT, DELETE)
* url: 요청 url (/post/{id}/{fileName})
* GET 요청일 경우 데이터는 아래 형식으로
* data: {
    pahParams: {
        id: 1
        fileName : file
    }
    queryParams : {

    }
 }
* submitFunc: 성공시 콜백 함수
* errorFunc: 실패시 콜백 함수
* */
function customAjax(method, url, data, submitFunc, errorFunc) {
    const ajaxOptions = {
        url: url,
        method: method,
        success: function(result, status, xhr) {
            if (submitFunc != null) {
                submitFunc(result, xhr);
            }
        },
        error: function(request, status, error) {
            if (errorFunc != null) {
                errorFunc(request, status, error);
            } else {
                console.log("에러");
            }
        },
        complete: function() {
            console.log("무조건 실행");
        }
    };

    if (method === "GET") {
        if (data.pathParams) {
            // 경로 변수를 사용하는 경우
            url = url.replace(/{(\w+)}/g, function(match, pathParam) {
                return data.pathParams[pathParam];
            });
        }
        ajaxOptions.data = data.queryParams;
    } else {
        // 파일 업로드인 경우
        if (data instanceof FormData) {
            ajaxOptions.data = data;
            ajaxOptions.processData = false;
            ajaxOptions.contentType = false;
        } else {
            ajaxOptions.data = JSON.stringify(data);
            ajaxOptions.contentType = "application/json";
        }
    }

    ajaxOptions.url = url;

    if (url.includes("download")) {
        ajaxOptions.xhrFields = {
            responseType: 'blob' // 파일을 받아오기 위해 responseType을 'blob'으로 설정합니다.
        }
    }

    $.ajax(ajaxOptions);
}


export {customAjax}; //ajax 함수를 외부에서 사용할 수 있도록 export