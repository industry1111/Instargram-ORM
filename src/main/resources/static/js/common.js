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
* errorFunc: 실패시 콜백 함수 ==> 공통함수로 처리 예정
* customParam : 콜백함수에 넘겨줄 데이터
* */
function customAjax(method, url, data, submitFunc) {
        const ajaxOptions = {
            url: url,
            method: method,
            success: function(result) {
                if (submitFunc != null) {
                    submitFunc(result);
                }
            },
            error: function(request, status, error) {
                console.log("error \n" , request);
            },
            complete: function() {
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
                responseType: 'blob' // 파일을 받아오기 위해 responseType을 'blob'으로 설정
            }
        }

        $.ajax(ajaxOptions);
}


/* 시간 문자열 변환 함수 ( 분/시간/일/월/년 기준)
* data : 변경할 부분의 HTML 태그 요소 및 시간 전달
* */
function timeToString(data) {
    const textRegex = /^[A-Za-z]+$/;

    let element;
    let modify;
    if (textRegex.test(data)){
        element = document.getElementById(data);
        modify = new Date(element.textContent);
    } else {
        modify = new Date(data);
    }
    let now = new Date();
    let duration = now - modify;
    let str = "";

    let years = Math.floor(duration / (1000 * 60 * 60 * 24 * 365));
    if (years >= 1) {
        str = years + ' years ago';
    } else {
        let months = Math.floor(duration / (1000 * 60 * 60 * 24 * 30));
        if (months >= 1) {
            str = months + ' months ago';
        } else {
            let days = Math.floor(duration / (1000 * 60 * 60 * 24));
            if (days >= 1) {
                str = days + ' days ago';
            } else {
                let hours = Math.floor(duration / (1000 * 60 * 60));
                if (hours >= 1) {
                    str = hours + ' hours ago';
                } else {
                    let minutes = Math.floor(duration / (1000 * 60));
                    str = minutes + ' minutes ago';
                }
            }
        }
    }

    if (element instanceof HTMLElement) {
        element.textContent = str;
    } else {
        return str;
    }
}

/*
* 프로필 이미지 다운로드 함수
* imageUrl   : 프로필 이미지 경로
* submitFunc : 성공시 콜백 함수
* */
function downloadProfileImage(imageUrl,submitFunc) {
    let data = {
        pathParams: {
            profileStoreName: imageUrl,
        },
        queryParams: {},
    };
    customAjax("GET", "/member/download/profile/{profileStoreName}", data, submitFunc);
}

/*
* 게시글 첨부파일 다운로드 함수
* imageUrl   : 첨부파일 경로
* submitFunc : 성공시 콜백 함수
* */
function downloadPostFile(fileStoreName, submitFunc) {
    let data = {
        pathParams: {
            fileStoreName: fileStoreName
        },
        queryParams: {}
    };
    customAjax("GET", "/post/download/{fileStoreName}", data, submitFunc);
}

export {customAjax}; //ajax 함수를 외부에서 사용할 수 있도록 export
export {timeToString};
export {downloadProfileImage};
export {downloadPostFile};