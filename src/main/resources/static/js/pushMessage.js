if (publishTime == "now") {
    $(".sendTimeNowArea").addClass("invisible");
}

$("#sendTimeNow").change(function () {
    $(".sendTimeNowArea").addClass("invisible");
});

$("#sendTimeReservation").change(function () {
    $(".sendTimeNowArea").removeClass('invisible');
});

$(document).ready(function () {
    if ($("input[name='btnUseFirst']").is(":checked", true)) {
        $("input.btnUseFirstGroup").attr("disabled", false);
        $("input.btnUseFirstGroup").removeClass("bg-gray-100");
    }
    if ($("input[name='btnUseSecond']").is(":checked", true)) {
        $("input.btnUseSecondGroup").attr("disabled", false);
        $("input.btnUseSecondGroup").removeClass("bg-gray-100");
    }
    if ($("input[name='btnUseThird']").is(":checked", true)) {
        $("input.btnUseThirdGroup").attr("disabled", false);
        $("input.btnUseThirdGroup").removeClass("bg-gray-100");
    }
});


$("input[name='btnUseFirst']").on({
    "change": function () {
        if ($("input[name='btnUseFirst']").is(':checked', true)) {
            $("input.btnUseFirstGroup").attr("disabled", false);
            $("input.btnUseFirstGroup").removeClass("bg-gray-100");
        } else {
            $("input.btnUseFirstGroup").attr("disabled", true);
            $("input.btnUseFirstGroup").addClass("bg-gray-100");
        }
    },
})
$("input[name='btnUseSecond']").on({
    "change": function () {
        if ($("input[name='btnUseSecond']").is(':checked', true)) {
            $("input.btnUseSecondGroup").attr("disabled", false);
            $("input.btnUseSecondGroup").removeClass("bg-gray-100");
        } else {
            $("input.btnUseSecondGroup").attr("disabled", true);
            $("input.btnUseSecondGroup").addClass("bg-gray-100");
        }
    },
})
$("input[name='btnUseThird']").on({
    "change": function () {
        if ($("input[name='btnUseThird']").is(':checked', true)) {
            $("input.btnUseThirdGroup").attr("disabled", false);
            $("input.btnUseThirdGroup").removeClass("bg-gray-100");
        } else {
            $("input.btnUseThirdGroup").attr("disabled", true);
            $("input.btnUseThirdGroup").addClass("bg-gray-100");
        }
    },
})