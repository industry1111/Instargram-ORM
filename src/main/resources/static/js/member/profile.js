import {customAjax, downloadProfileImage} from "../common.js";

const sessionId = parseInt($("#sessionId").val());
const profileId = parseInt($("#profileId").val());
let page = 1;
let size = 9;
let totalPage;

let imageCache = new Map();
window.onload = function () {
    findMemberPost();

    if ($(window).scroll(function () {
            let $window = $(this);
            let scrollTop = $window.scrollTop();
            let windowHeight = $window.height();
            let documentHeight = $(document).height();

            // scrollbar의 thumb가 바닥 전 30px까지 도달 하면 리스트를 가져온다.
            if( scrollTop + windowHeight + 30 > documentHeight ){
                if(page <= totalPage){
                    page++
                    findMemberPost();
                }

            }


    })) ;

    function findMemberPost() {
        let memberId = $("#memberId").val();

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



    const followModal = document.getElementById('modal_follow');
    const btnFollower = document.getElementById('follower');
    const btnFollowing = document.getElementById('following');
    btnFollower.addEventListener("click", function() {
        getFollowList(this.id,this.value);
    });
    btnFollowing.addEventListener("click", function(){
        getFollowList(this.id,this.value);
    });

    const btnCloseModal = document.getElementById("close_modal");
    btnCloseModal.addEventListener("click", e => {
        $('#modal_follow').css({
            display: 'none'
        });
    });

    function getFollowList(title, memberId) {

        $('#modalTitle').text(title+ " List");

        let url = "";
        let data = {
            pathParams : {
                memberId: memberId
            }
        }
        if(title === "follower") {
            url = "/followerList/{memberId}";
        } else {
            url = "/followingList/{memberId}";
        }

        customAjax("GET",url,data, function (result) {
            createFollowGrid(result);
        })
    }

    function createFollowGrid(data) {
        $(".suggest-member-grid").html("");
        data.forEach((memberDTO) => {
            let imageUrl = memberDTO.picture;
            addProfileImgToCache(imageUrl)
                .then(() => {
                    appendFollowCard(memberDTO, imageCache.get(imageUrl));
                    followModal.style.display = "flex";
                })
                .catch((error) => {
                    // 에러 처리
                });
        });
    }

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


    function appendFollowCard(memberDTO, url) {
        let innerHtml = `
                 <div class="profile-card">
                    <div class="profile-pic">
                        <img src="${url}"  alt="">
                    </div>
                    <div>
                        <a href="/member/profile/${memberDTO.id}">
                            <p class="username">${memberDTO.name}</p>
                        </a>
                        <p class="sub-text">${memberDTO.introduction}</p>
                    </div>`;
        if (profileId === sessionId) {
            if (!memberDTO.followStatus) {
                innerHtml +=`
                    <div><button class="action-btn" name="followBtn" value="${memberDTO.id}">follow</button></div>`;
            } else {
                innerHtml +=`
                    <div><button class="action-btn" name="unfollowBtn" value="${memberDTO.id}">unfollow</button></div>`;
            }
        }
        innerHtml +=`</div>`;

        $(".suggest-member-grid").append(innerHtml);
    }


    $(Document).on("click", "button[name='unfollowBtn']", function () {

        let title = $('#modalTitle').html().split(" ")[0];
        let toMemberId = this.value;
        let data = {
            pathParams : {
                toMemberId : toMemberId
            }
        };

        customAjax("DELETE","/deleteFollow/{toMemberId}", data, function (){
            getFollowList(title,sessionId);
        });
    });

    $(Document).on("click", "button[name='followBtn']", function () {

        let title = $('#modalTitle').html().split(" ")[0];
        let toMemberId = this.value;
        let data = {
            pathParams : {
                toMemberId : toMemberId
            }
        };

        customAjax("GET","/addFollow/{toMemberId}", data, function (){
            getFollowList(title,sessionId);
        });
    });

}