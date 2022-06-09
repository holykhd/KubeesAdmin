if (publishTime == "reservation") {
    $("input:radio[id='reservation']").prop('checked', true);
    $(".noticeDatepicker").removeClass('invisible');
    $(".noticeDatetime").removeClass('invisible');
    $(".noticeDatetimeText").removeClass('invisible');
} else {
    $("input:radio[id='now']").prop('checked', true);
    $(".noticeDatepicker").addClass('invisible');
    $(".noticeDatetime").addClass('invisible');
    $(".noticeDatetimeText").addClass('invisible');
    $(".datepicker").addClass("invisible");
}


$("#now").change(function () {
    $(".noticeDatepicker").addClass('invisible');
    $(".noticeDatetime").addClass('invisible');
    $(".noticeDatetimeText").addClass('invisible');
    $(".datepicker").addClass("invisible");
});
$("#reservation").change(function () {
    $(".noticeDatepicker").removeClass('invisible');
    $(".noticeDatetime").removeClass('invisible');
    $(".noticeDatetimeText").removeClass('invisible');
    $(".datepicker").removeClass("invisible");

    if ($("#noticeDatepicker").val() == "") {
        $("#noticeDatepicker").focus();
    }
    if ($("#publishHour").val() == "") {
        $("#publishHour").focus();
    }
    if ($("#publishMinutes").val() == "") {
        $("#publishMinutes").focus();
    }
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