import {customAjax} from "./common.js";
import {timeToString} from "./common.js";
import {downloadProfileImage} from "./common.js";
import {downloadPostFile} from "./common.js";

// 이미지 URL을 관리하기 위한 맵 객체
const imageCache = new Map();

window.onload = function () {
    const sessionId = parseInt($("#sessionId").val());

    let page = 1;
    let size = 3;
    let totalPage;

    let files;

    //게시글 가져오기
    getPosts();

    //추천 유저 가져오기
    getSuggestMembers();

    const modal = document.getElementById("modal_add_feed");
    const btnCreatePost = document.getElementById("btn-create_post");

    btnCreatePost.addEventListener("click", e => {

        modal.style.display = "flex";
        document.body.style.overflowY = "hidden"; // 스크롤 없애기

    });

    const btnCloseModal = document.getElementById("close_modal");
    btnCloseModal.addEventListener("click", e => {
        closeModal();
    });

    const btnCloseModal2 = document.getElementById("close_modal2");
    btnCloseModal2.addEventListener("click", e => {
        closeModal();
    });

    function closeModal(e) {
        //첫번째 모달창 안보이게
        $('#modal_add_feed').css({
            display: 'none'
        });

        //2번째 모달창 안보이게
        $('#modal_add_feed_content').css({
            display: 'none'
        });

        document.body.style.overflowY = "scroll"; // 스크롤 보이게

        if (typeof  e == "number") {
            $(".post-grid").html("");
            page = 1;
            getPosts();
        }
    }

    $('.modal_image_upload')
        .on("dragover", dragOver)
        .on("dragleave", dragOver)
        .on("drop", uploadFiles);

    function dragOver(e) {
        e.stopPropagation();
        e.preventDefault();

        if (e.type == "dragover") {
            $(e.target).css({
                "background-color": "black",
                "outline-offset": "-20px"
            });
        } else {
            $(e.target).css({
                "background-color": "white",
                "outline-offset": "-10px"
            });
        }
    }


    function uploadFiles(e) {
        e.stopPropagation();
        e.preventDefault();
        e.dataTransfer = e.originalEvent.dataTransfer;

        files = e.dataTransfer.files;
        if (files.length > 1) {
            alert('하나만 올려라.');
            return;
        }

        if (files[0].type.match(/image.*/)) {
            $('#modal_add_feed_content').css({
                display: 'flex'
            });
            $('.modal_image_upload_content').css({
                "background-image": "url(" + window.URL.createObjectURL(files[0]) + ")",
                "outline": "none",
                "background-size": "contain",
                "background-repeat": "no-repeat",
                "background-position": "center"
            });
            $('#modal_add_feed').css({
                display: 'none'
            })
        } else {
            alert('이미지가 아닙니다.');
            return;
        }
    };

    $('#button_write_feed').on('click', () => {
        const image = $('#input_image').css("background-image").replace(/^url\(['"](.+)['"]\)/, '$1');
        const content = $('#input_content').val();
        const profile_image = $('#input_profile_image').attr('src');
        const user_id = $('#input_user_id').text();

        const file = files[0];

        let fd = new FormData();

        fd.append('file', file);
        fd.append('image', image);
        fd.append('content', content);
        fd.append('profile_image', profile_image);
        fd.append('user_id', user_id);

        if (image.length <= 0) {
            alert("이미지가 비어있습니다.");
        } else if (content.length <= 0) {
            alert("설명을 입력하세요");
        } else if (profile_image.length <= 0) {
            alert("프로필 이미지가 비어있습니다.");
        } else if (user_id.length <= 0) {
            alert("사용자 id가 없습니다.");
        } else {
            customAjax("POST", "/post/create", fd, closeModal);
        }
    });

    function addProfileImgToCache(imageUrl) {
        return new Promise((resolve, reject) => {
            if (imageUrl == null) {
                resolve();
                return;
            }

            // 이미지 URL이 이미 캐시에 있는지 확인
            if (imageCache.has(imageUrl)) {
                resolve();
            } else {
                if (!imageUrl.startsWith("http")) {
                    downloadProfileImage(imageUrl, function (result) {
                        let blob = new Blob([result]);
                        let url = URL.createObjectURL(blob);
                        imageCache.set(imageUrl, url); // 이미지 URL을 캐시에 저장
                        resolve();
                    })
                } else {
                    imageCache.set(imageUrl, imageUrl); // 이미지 URL을 캐시에 저장
                    resolve();
                }
            }
        });
    }

    //스크롤 페이징
    if ($(window).scroll(function () {
        if ($(window).scrollTop() === $(document).height() - $(window).height()) {
            if (page <= totalPage) {
                getPosts();
            }
        }
    })) ;

    function getPosts() {
        let data = {
            pathParams: {
                id: sessionId
            },
            queryParams: {
                page: page,
                size: size
            }
        };
        customAjax("GET", "/post/list/{id}", data, createPostGrid);
    }

    function createPostGrid(result) {
        totalPage = result.totalPage;

        result.dtoList.forEach((postDTO) => {
            let fileStoreName = postDTO.fileStoreNames[0];

            addProfileImgToCache(postDTO.picture)
                .then(() => {
                    $(".post-grid").append(appendPost(postDTO));

                    downloadPostFile(fileStoreName, function (responseData) {
                        const img = document.getElementById(fileStoreName);
                        let blob = new Blob([responseData]);
                        img.src = URL.createObjectURL(blob);
                    });
                })
                .catch((error) => {

                });


        });
        page++;
    }

    function appendPost(data) {
        let postId = data.id; // 게시물 고유 아이디
        let innerHtml = `
        <div class="post">
            <div class="info">
                <div class="user">
                    <div class="profile-pic"> <img src="${imageCache.get(data.picture)}" alt=""> </div>
                    <p class="username">${data.name}</p>
                </div>
    `;
        if (data.memberId === sessionId) {
            innerHtml += '<img src="/img/main/option.png" name="removePost" class="options" alt="">\n';
        }
        innerHtml += `
            </div>
            <img src="" class="post-image" id="${data.fileStoreNames[0]}" alt="">
            <div class="post-content" id="postContent${postId}">
                <div class="reaction-wrapper">
                    <input type="hidden" name="post" value="${postId}">
                    <img src="/img/main/like.png" class="icon like" alt="">
                    <img src="/img/main/comment.png" class="icon comment" alt="">
                    <img src="/img/main/send.png" class="icon" alt="">
                    <img src="/img/main/save.png" class="save icon" alt="">
                </div>
                <p class="likes">1,012 likes</p>
                <p class="description"><span>${data.name}</span>${data.content}</p>
                <p class="post-time" >${timeToString.call(this, data.createdDate)}</p> 
               `;
        innerHtml += `
            </div>
            <div class="comment-wrapper">
                <img src="/img/main/smile.png" class="icon" alt="">
                <input type="text" class="comment-box"  placeholder="댓글을 입력하세요">
                <button class="comment-btn" value="${postId}" >게시</button>
            </div>
        </div>
    `;
        return innerHtml;
    }


    $(Document).on("click", "img[name='removePost']", function () {
        let result = confirm("정말로 삭제하시겠습니까?");
        let postId = $(this).parent(".info").siblings(".post-content").find("input[type='hidden']").val();
        console.log(postId);
        if (result) {
            let data = {
                pathParams: {
                    postId: postId
                },
                queryParams: {}
            }
            customAjax("DELETE", '/post/delete/{postId}', data, function () {
                location.href="/main";
            });
        }
    })

    function getSuggestMembers(membersIds) {
        let data = {
            pathParams: {},
            queryParams: {
                membersIds: membersIds,
            },
        };
        customAjax("GET", "/member/suggest/members", data, createSuggestMemberGrid);
    }


    function createSuggestMemberGrid(data) {
        $(".suggest-member-grid").html("");
        data.forEach((memberDTO) => {
            let imageUrl = memberDTO.picture;
            addProfileImgToCache(imageUrl)
                .then(() => {
                    appendProfileCard(memberDTO, imageCache.get(imageUrl));
                })
                .catch((error) => {
                    // 에러 처리
                });
        });
    }

    function appendProfileCard(memberDTO, imageUrl) {
        let innerHtml = `
            <div class="profile-card">
                <div class="profile-pic">
                    <img src="${imageUrl}"  alt="">
                </div>
                <div>
                    <a href="/member/profile/${memberDTO.id}">
                        <p class="username">${memberDTO.name}</p>
                    </a>
                    <p class="sub-text">${memberDTO.introduction}</p>
                </div>
                <div><button class="action-btn" name="followBtn" value="${memberDTO.id}">follow</button></div>
            </div>
            `;

        $(".suggest-member-grid").append(innerHtml);
    }

    $(Document).on("click", "button[name='followBtn']", function () {
        let toMemberId = this.value;
        let data = {
            pathParams : {
                toMemberId : toMemberId
            }
        }
        customAjax("GET","/addFollow/{toMemberId}", data, function () {
            getSuggestMembers();
            $(".post-grid").html("");
            page = 1;
            size = 3;
            getPosts();
        });
    })

    function showDetailPost(postId) {
        const detailModal = document.getElementById("detail_modal_feed_content");
        const btnCloseModal = document.getElementById("close_detail_modal");

        detailModal.style.display = "flex";
        document.body.style.overflowY = "hidden";

        btnCloseModal.addEventListener("click", e => {
            detailModal.style.display = "none";
            document.body.style.overflowY = "scroll";
            $('.detail_modal_image_upload_content').css({
                "background-image": ""
            });
        });
    }

    /*
    * 게시글의 말풍선 이미지 클릭시 상세보기 화면을 띄우기 위한 함수
    * */
    $(Document).on("click", "img[class='icon comment']", function () {

        let postId = parseInt($(this).parent().children(0).first().val());

        createDetailPost(postId);
    });


    function getComments(postId) {

        let data = {
            pathParams : {
                postId : postId
            },
            queryParams : {}
        }

        customAjax("GET","/comment/{postId}", data, appendComment);

    }

    /*
    * [게시] 버튼을 눌렀을 때
    * */
    $(Document).on("click", "button[class='comment-btn']", function () {

        let postId = $(this).val();
        let element = $(this).prev();
        let content = element.val();

        let screenFlag = $(this).attr('id');

        let data = {
            postId : postId,
            memberId: sessionId,
            content: content
        }
        customAjax("POST","/comment/add", data, function () {
            element.val("");
            if (screenFlag === "detailBtn") {
                getComments(postId);
            }
        });
    });

    function appendComment(commentDTOS) {
        let commentGrid = $(".post-comment");
        let innerHTML = '';
        commentDTOS.forEach(commentDTO => {
            addProfileImgToCache(commentDTO.memberProfileImg)
                .then(() => {
                    innerHTML += `<div class="feed_name">
                                        <div class="profile_box">
                                            <img class="detail_profile_img"  src="${imageCache.get(commentDTO.memberProfileImg)}" alt="">
                                            <span   class="feed_name_txt" > ${commentDTO.memberName} </span>
                                            <span>${commentDTO.content}</span>
                                        </div>
                                    </div>`;

                    commentGrid.html(innerHTML);
                })
                .catch((error) => {
                });
        });

        showDetailPost();
    }

    function createDetailPost(postId) {

        let data = {
            pathParams : {
                postId : postId
            }
        }

        customAjax("GET","/post/{postId}", data, function (data) {

            const postDTO = data;

            postDTO.fileStoreNames.forEach(fileStoreName => {
               downloadPostFile(fileStoreName, function (result) {
                   $('.detail_modal_image_upload_content').css({
                       "background-image": "url(" + window.URL.createObjectURL(result) + ")",
                       "outline": "none",
                       "background-size": "contain",
                       "background-repeat": "no-repeat",
                       "background-position": "center"
                   });
               }) ;
            });



            let detailPost = $(".detail_modal_content_write").html("");

            let innerHTML = ` <div class="feed_name">
                                    <div class="profile_box">
                                        <img class="detail_profile_img" src="${imageCache.get(postDTO.picture)}" alt="">
                                        <span class="feed_name_txt" >${postDTO.name}</span>
                                        <span>${postDTO.content}</span>
                                    </div>
                                </div>
                                
                                <div style="height: 400px" class="post-comment">
                                    
                                </div>
            
                                <div class="comment-wrapper ">
                                    <img src="/img/main/smile.png" class="icon" alt="">
                                    <input type="text" class="comment-box" placeholder="댓글을 입력하세요">
                                    <button class="comment-btn" value="${postDTO.id}" id="detailBtn">게시</button>
                                </div>`;

            detailPost.append(innerHTML);
            appendComment(postDTO.commentDTOS);
        });
    }
}