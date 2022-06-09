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

$(document).ready(function() {
    $('#contents').summernote({
        height: 300,                 // set editor height
        minHeight: null,             // set minimum height of editor
        maxHeight: null,             // set maximum height of editor
        focus: true                  // set focus to editable area after initializing summernote
    });
});

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