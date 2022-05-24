$(".changeStatus").on('click', function () {
    let button = $(this);
    let email = this.dataset.email;
    let userStatus = this.dataset.userStatus;
    let message = this.dataset.userStatus == 'Y' ? '선택하신 회원을 사용 제한 하시겠습니까?' : '선택하신 회원의 사용제한을 해제하시겠습니까?';
    if(confirm(message)){
        $.ajax({
            type: 'post',
            url: '/admin/sns/changeUserStatus/' + email + "/" + userStatus,
            success: function (result) {
                if (result.data.userStatus == 'Y') {
                    button
                        .addClass('bg-red-700 hover:bg-red-800')
                        .removeClass('bg-gray-700 hover:bg-gray-800')
                        .text('사용 제한')
                        .attr('data-user-status', result.data.userStatus)
                } else if(result.data.userStatus == 'D') {
                    button
                        .addClass('bg-gray-700 hover:bg-gray-800')
                        .removeClass('bg-red-700 hover:bg-red-800')
                        .text('제한 해제')
                        .attr('data-user-status', result.data.userStatus)
                }
            },
            error: function (error) {
                console.log(error);
            },
        })
    }
});

$('.changeBlockStatus').on('click', function () {
    let button = $(this);
    let id = this.dataset.id;
    let fromUserId = this.dataset.fromUserId;
    let toUserId = this.dataset.toUserId;
    let message = "선택하신 회원을 차단 해제하시겠습니까?";
    if(confirm(message)){
        $.ajax({
            type: 'post',
            url: '/admin/sns/changeBlockFlagStatus/' + fromUserId + '/' + toUserId,
            success: function (result) {
                pageReload();
            },
            error: function (error) {
                console.log(error);
            }
        })
    }

    function pageReload() {
        // $("#testt").load(location.href + " #testt");
        location.reload();
    }
});