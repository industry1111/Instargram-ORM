import {customAjax} from "../common.js";

let page = 1;
let size = 9;
let totalPage;
window.onload = function () {
    findMemberPost();

    if ($(window).scroll(function () {
            let $window = $(this);
            let scrollTop = $window.scrollTop();
            let windowHeight = $window.height();
            let documentHeight = $(document).height();

            console.log("documentHeight:" + documentHeight + " | scrollTop:" + scrollTop + " | windowHeight: " + windowHeight );

            // scrollbar의 thumb가 바닥 전 30px까지 도달 하면 리스트를 가져온다.
            if( scrollTop + windowHeight + 30 > documentHeight ){
                if(page <= totalPage){
                    page++
                    console.log("page : " + page)
                    findMemberPost();
                }

            }


    })) ;

    function findMemberPost() {
        let memberId = $("#memberId").val();
        console.log(memberId)
        let data = {
            pathParams: {
                id: memberId
            },
            queryParams: {
                page: page,
                size: size
            }
        }
        customAjax("GET", "/post/member/postList/"+memberId, data, findMemberPostCallBack);


    }

    function findMemberPostCallBack(result) {
        totalPage = result.totalPage;
        result.dtoList.forEach((data) => {
            if (data.id != null) {
                $(".gallery").append(createdGrid(data));
            }
        })
        downloadPostFile(result);
    }

    function downloadPostFile(result) {
        result.dtoList.forEach((data) => {
            data.fileStoreNames.forEach((fileStoreName) => {
                let data = {
                    pathParams: {
                        fileStoreName: fileStoreName
                    },
                    queryParams: {}
                };

                customAjax("GET", "/post/download/{fileStoreName}", data, function (data) {
                    const img = document.getElementById(fileStoreName);

                    let blob = new Blob([data]);

                    URL.createObjectURL(blob);
                    img.src = URL.createObjectURL(blob);
                });
            });
        });
    }

    function createdGrid(data) {
        const innerHtml =
            '           <div class="gallery-item" tabindex="0">\n' +
            '               <img id="'+data.fileStoreNames[0]+'"  class="gallery-image" alt="">\n' +
            '               <div class="gallery-item-infro">\n' +
            '                   <ul>\n' +
            '                       <li class="gallery-item-likes"><span class="visually-hidden">Likes:</span><i class="fas fa-heart" aria-hidden="true"></i> 56</li>\n' +
            '                       <li class="gallery-item-comments"><span class="visually-hidden">Comments:</span><i class="fas fa-comment" aria-hidden="true"></i> 2</li>\n' +
            '                   </ul>\n' +
            '               </div>\n' +
            '           </div>'
        return innerHtml;
    }
}