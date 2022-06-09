let userStatus = false;
let id = "";
let feedStatus = true;

$(document).on('click', '.user-default-modal', function () {
    let email = $(this).data('email');
    id = $(this).data('id');
    userStatus = $(this).data('status');
    $(".modal-body #userEmail").text(email);

    let modalTitle = userStatus == 'Y' ? '계정 사용 제한' : '계정 사용 제한 해제';
    $(".modal-header #modal-header-title").text(modalTitle);

    if (userStatus == 'Y') {
        $(".modal-body button:first")
            .removeClass('bg-blue-700 hover:bg-blue-800')
            .addClass('bg-red-700 hover:bg-red-800')
            .text("사용 제한");
        $("#textInfo").text("사용 제한");
    } else {
        $(".modal-body button:first")
            .removeClass('bg-red-700 hover:bg-red-800')
            .addClass('bg-blue-700 hover:bg-blue-800')
            .text("사용 제한 해제");
        $("#textInfo").text("사용 제한 해제");
    }
});

function allChecked(target) {
    if ($(target).is(":checked")) {
        $(".chk").prop("checked", true);
    } else {
        $(".chk").prop("checked", false);
    }
}

function changeUserStatus() {
    $.ajax({
        type: 'post',
        url: '/admin/account/changeStatus/' + id + "/" + userStatus,
        success: function (res) {
            location.reload();
        },
        error: function (error) {
            console.log(error);
        },
    });
}

/*detail*/
$(".change-status").click(function () {
    id = this.dataset.id;
    let contents = $(this).data('contents');
    feedStatus = this.dataset.feedStatus;
    // id = $(this).attr("data-id");
    feedStatus = $(this).attr("data-feed-status");
    $(".feedContents").text(contents);

    feedText = feedStatus == true ? "숨기기" : "보이기";
    $(".modal-message-status").text(feedText);

});

$("#changeFeedStatus").click(function () {
    $.ajax({
        url: "/admin/user/detail/feedStatus",
        data: {
            'id': id,
            'feedStatus': feedStatus
        },
        dataType: "json",
        type: "get",
        success: function (data) {
            setTimeout(function(){
                location.reload();
            },100);
        },
    })
});

$("#feed-status-toggle").click(function () {

    let feedStatus;
    if ($(this).prop("checked")) {
        feedStatus = false;
        location.href = "/admin/user/detail/" + email + "?searchType=" + searchType + "&keyword=" + keyword + "&feedStatus=" + feedStatus;
    } else {
        feedStatus = true;
        location.href = "/admin/user/detail/" + email + "?searchType=" + searchType + "&keyword=" + keyword + "&feedStatus=" + feedStatus;
    }
});


/*blackList*/

// 초기 차단회원 목록 UI
$("#blockListArea").append(getEmptyHtml());


function blockUserList(id) {
    let html = "";
    $.ajax({
        type: 'get',
        url: '/admin/user/blockToUserList/' + id,
        success: function (result) {
            $("#blockListArea").empty();
            if (result.data.length == 0) {
                let emptyHtml = getEmptyHtml();
                $("#blockListArea").append(emptyHtml);
            } else {
                result.data.forEach((list) => {
                    let html = getBlockToUserList(list);
                    $("#blockListArea").append(html);
                });
            }
        },
        error: function (result) {

        },

    });
}

function getEmptyHtml() {
    html = `
    <tr class="text-gray-700 dark:text-gray-400">
        <td colspan="5" class="px-4 py-20 text-center">차단한 회원 정보가 존재하지 않습니다.</td>
    </tr>
    `;
    return html;
}

function getBlockToUserList(list) {
    let html = `
                <tr class="text-gray-700 dark:text-gray-400">
                    <td class="px-4 py-3 text-sm">${list.email}</a>
                    </td>
                    <td class="px-4 py-3 text-center text-xs">${list.nickname}</td>
                    <td class="px-4 py-3 text-center text-sm">${list.name}</td>
                    <td class="px-4 py-3 text-center text-sm">${list.phone}</td>
                    <td class="px-4 py-3 text-center text-sm">${moment(list.createdAt).format('YYYY-MM-DD HH:mm:ss')}</td>
                </tr>
       `;
    return html;
}