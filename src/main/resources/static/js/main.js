import {customAjax} from "./common.js";


// 이미지 URL을 관리하기 위한 맵 객체
const imageCache = new Map();
const filePath = "/Users/gohyeong-gyu/Downloads/upload/";

window.onload = function () {

    let page = 1;
    let size = 3;
    let totalPage;


    const modal = document.getElementById("modal_add_feed");
    const btnCreatePost = document.getElementById("btn-create_post");
    let files;

    findAllPost();

    if ($(window).scroll(function () {
        if ($(window).scrollTop() === $(document).height() - $(window).height()) {
            if (page <= totalPage) {

                findAllPost();

            }
        }
    })) ;


    btnCreatePost.addEventListener("click", e => {

        modal.style.top = window.scrollY + "px"; // 스크롤 위치에 따라 모달 위치 변경
        modal.style.display = "flex";
        // document.body.style.overflowY = "hidden"; // 스크롤 없애기


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
        console.log(e.dataTransfer)
        console.log(e.originalEvent.dataTransfer)

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

    const btnHome = document.getElementById("btn-home");
    btnHome.addEventListener("click", e => {
        findAllPost();
    });


    function findAllPost() {
        let data = {
            pathParams: {
                id: 1
            },
            queryParams: {
                page: page,
                size: size
            }
        };
        customAjax("GET", "/post/list/{id}", data, findAllPostCallBack);
    }

    function findAllPostCallBack(result) {
        totalPage = result.totalPage;
        result.dtoList.forEach((postDTO) => {
            $(".post-grid").append(createPosGrid(postDTO));
        });
        page++;
        downloadPostFile(result);
        downloadProfile(result);
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
                customAjax("GET", "/post/download/{fileStoreName}", data, function (responseData) {
                    const img = document.getElementById(fileStoreName);
                    let blob = new Blob([responseData]);
                    img.src = URL.createObjectURL(blob);
                });
            });
        });
    }

    function downloadProfile(result) {
        result.dtoList.forEach((dto) => {
            let imageUrl = dto.picture;
            let data = {
                pathParams: {
                    memberId: dto.memberId,
                },
                queryParams: {}
            };
            const profileImg = document.getElementById(`profile-${dto.id}`);
            if (!imageUrl.startsWith("http")) {
                // 이미지 URL이 이미 캐시에 있는지 확인
                if (imageCache.has(imageUrl)) {
                    // 이미지가 캐시에 있는 경우, 캐시에서 가져옴
                    profileImg.src = imageCache.get(imageUrl);
                } else {
                    customAjax("GET", "/member/download/profile/{memberId}", data, function (responseData) {
                        let blob = new Blob([responseData]);
                        let url = URL.createObjectURL(blob);
                        profileImg.src = url;
                        imageCache.set(imageUrl,url);
                    });
                }

            } else {
                profileImg.src = imageUrl;
            }
        });
    }

    function createPosGrid(data) {
        let postId = data.id; // 게시물 고유 아이디
        let innerHtml = `
        <div class="post">
            <div class="info">
                <div class="user">
                    <div class="profile-pic"> <img src="" id="profile-${postId}" alt=""> </div>
                    <p class="username">${data.name}</p>
                </div>
    `;
        if (data.memberId === sessionId) {
            innerHtml += '<img src="/img/main/option.png" name="removePost" class="options" alt="">\n';
        }
        innerHtml += `
            </div>
            <img src="" class="post-image" id="${data.fileStoreNames[0]}" alt="">
            <div class="post-content">
                <div class="reaction-wrapper">
                    <img src="/img/main/like.png" name="post" class="icon" alt="">
                    <input type="hidden" name="post" value="${postId}">
                    <img src="/img/main/comment.png" class="icon" alt="">
                    <img src="/img/main/send.png" class="icon" alt="">
                    <img src="/img/main/save.png" class="save icon" alt="">
                </div>
                <p class="likes">1,012 likes</p>
                <p class="description"><span>${data.name}</span>${data.content}</p>
                <p class="post-time">2 minutes ago</p>
            </div>
            <div class="comment-wrapper">
                <img src="/img/main/smile.png" class="icon" alt="">
                <input type="text" class="comment-box" placeholder="댓글을 입력하세요">
                <button class="comment-btn">게시</button>
            </div>
        </div>
    `;
        return innerHtml;
    }


    $(Document).on("click", "img[name='post']", function () {
        console.log($(this).next().val());

        customAjax("get", "/post/api/addLike/{memberId}",)
    });

    $(Document).on("click", "img[name='removePost']", function () {
        let result = confirm("정말로 삭제하시겠습니까?");
        let postId = $(this).parent(".info").siblings(".post-content").find("input[type='hidden']").val();
        console.log(postId);
        if (result) {
            let data = {
                pathParams: {},
                queryParams: {}
            }
            customAjax("get", '/post/delete/' + postId, postId, function () {
                alert("삭제가 완료되었습니다.");
            });
        } else {
            alert(result);
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
        data.forEach((memberDTO) => {
            let imageUrl = memberDTO.picture; // 이미지 URL 가져오기

            // 이미지 URL이 이미 캐시에 있는지 확인
            if (imageCache.has(imageUrl)) {
                // 이미지가 캐시에 있는 경우, 캐시에서 가져옴
                appendProfileCard(memberDTO, imageCache.get(imageUrl));
            } else {
                // 이미지가 캐시에 없는 경우, 다운로드하여 캐시에 저장
                downloadProfileImage(memberDTO.id, imageUrl, function (url) {
                    imageCache.set(imageUrl, url); // 이미지 URL을 캐시에 저장
                    appendProfileCard(memberDTO, url);
                });
            }
        });
    }

    function downloadProfileImage(memberId, imageUrl, callback) {
        if (imageUrl == null) {
            callback('');
            return;
        }

        let data = {
            pathParams: {
                memberId: memberId,
            },
            queryParams: {},
        };

        if (!imageUrl.startsWith("http")) {
            customAjax("GET", "/member/download/profile/{memberId}", data, function (data) {
                let blob = new Blob([data]);
                let url = URL.createObjectURL(blob);
                callback(url);
            });
        } else {
            callback(imageUrl);
        }
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
                <div><button class="action-btn">follow</button></div>
            </div>
            `;

        $(".suggest-member-grid").append(innerHtml);
    }

    getSuggestMembers();

}