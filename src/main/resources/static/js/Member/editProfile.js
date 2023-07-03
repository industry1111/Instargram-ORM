import {customAjax} from "../common";

$('btn-update').on('click', function () {
    var name = $('#name').val();
    var Introduction = $('#Introduction').val();
    var id = $('#id').val();
    var provider = $('#provider').val();

    let fd = new FormData();
    fd.append('name', name);
    fd.append('Introduction', Introduction);
    fd.append('id', id);
    fd.append('provider', provider);

    customAjax("POST", "/member/edit", fd, successCallback, failCallback);
});

