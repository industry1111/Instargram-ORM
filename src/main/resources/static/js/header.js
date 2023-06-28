import {customAjax} from "./common.js";

window.onload = function () {

    const modal = document.getElementById("modal_add_feed");
    const btnCreatePost = document.getElementById("btn-create_post");
    let files;

    btnCreatePost.addEventListener("click", e => {

        modal.style.top = window.scrollY + "px"; // 스크롤 위치에 따라 모달 위치 변경
        modal.style.display = "flex";
        document.body.style.overflowY = "hidden"; // 스크롤 없애기


    });


    const buttonCloseModal = document.getElementById("close_modal");
    buttonCloseModal.addEventListener("click", e => {
        closeModal("modal");
    });

    const buttonCloseModal2 = document.getElementById("close_modal2");
    buttonCloseModal2.addEventListener("click", e => {
        closeModal("modal2");
    });

    function closeModal(e){
        //첫번째 모달창 안보이게
        $('#modal_add_feed').css({
            display: 'none'
        });

        //2번째 모달창 안보이게
        if (e === "modal2") {
            $('#modal_add_feed_content').css({
                display : 'none'
            });
        }


    }

    $('.modal_image_upload')
        .on("dragover", dragOver)
        .on("dragleave", dragOver)
        .on("drop", uploadFiles);

    function dragOver(e){
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

    function uploadFiles(e){
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
                display : 'flex'
            });
            $('.modal_image_upload_content').css({
                "background-image": "url(" + window.URL.createObjectURL(files[0]) + ")",
                "outline": "none",
                "background-size": "contain",
                "background-repeat" : "no-repeat",
                "background-position" : "center"
            });
            $('#modal_add_feed').css({
                display: 'none'
            })
        }else{
            alert('이미지가 아닙니다.');
            return;
        }
    };

    $('#button_write_feed').on('click', ()=>{
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

        if(image.length <= 0)
        {
            alert("이미지가 비어있습니다.");
        }
        else if(content.length <= 0)
        {
            alert("설명을 입력하세요");
        }
        else if(profile_image.length <= 0)
        {
            alert("프로필 이미지가 비어있습니다.");
        }
        else if(user_id.length <= 0)
        {
            alert("사용자 id가 없습니다.");
        }
        else{
            aa(fd);
            console.log(files[0]);
        }
    });

    function aa(fd) {
        customAjax("GET", "/post/1", fd,);
    }





}


