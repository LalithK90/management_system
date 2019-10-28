$(document).ready(function () {
    //WYSIWYG add to text area
    bkLib.onDomLoaded(function () {
        nicEditors.allTextAreas()
    });

//according to province need to find district and station
    $("#districtShow, #stationShow").hide();
});
//millisecond to day
const toDayInMilliSecond = Date.parse(new Date());

/*Employee working place*/
$("#selectParameter").bind("change", function () {
    btnSearchEmployeeShow();
    $("#selectParameter").css('background', '');
    //set what is the parameter will search
    $("#valueEmployee").attr('name', $(this).val());
    $("#valueEmployee").val('');
    backgroundColourChangeNothingToChange($("#valueEmployee"));
});

/*Employee Find */
$("#valueEmployee").bind("keyup", function () {
    let selectedValue = $("#valueEmployee").attr('name');
    if ($("#valueEmployee").val() !== '' && $("#selectParameter").val() === '') {
        $("#selectParameter").css('background', '#dc3545');
        swal({
            title: "Please enter select parameter value before type here",
            icon: "warning",
        });
    }
    if (selectedValue === "nic") {
        let nic = $("#valueEmployee");
        if (nicRegex.test($("#valueEmployee").val())) {
            backgroundColourChangeGood(nic);
        } else if (nic.length === 0) {
            backgroundColourChangeNothingToChange(nic);
        } else {
            backgroundColourChangeBad(nic);
        }
    }
    btnSearchEmployeeShow();
});

function btnSearchEmployeeShow() {
    if ($("#selectParameter").val() !== '' && $("#valueEmployee").val() !== '') {
        $("#btnSearchEmployee").css('display', '');
    } else {
        $("#btnSearchEmployee").css('display', 'none');
    }
}

//get district list
$("#province").bind("change", function () {
    let urlValue = $("#districtUrl").val();
    let selected = $(this).val();
    let selectedProvince = urlValue + selected;
    //workingPlaceRest given district list according to given province
    let givenData = [];
    $("#districtShow").show();
    if ($('#district > option').val() !== undefined) {
        remove_options($("#district"));
    }
    Promise.resolve(getData(`${selectedProvince}`).then(data => givenData = data)).then(function (val) {
        for (let i = 0; i < val.length; i++) {
            let elementId = document.getElementById('district');
            let opt = document.createElement('option');
            opt.innerHTML = val[i].district;
            opt.value = val[i].name;
            elementId.appendChild(opt);
        }
    });
});

//get lists from db and manage it to combo box
$("#district").bind("change", function () {
    let urlValue = $("#stationUrl").val();
    let selected = $(this).val();
    let selectedProvince = urlValue + selected;
    //workingPlaceRest given district list according to given province
    let givenData = [];
    $("#stationShow").show();
    if ($('#station > option').val() !== undefined) {
        remove_options($("#station"));
    }
    Promise.resolve(getData(`${selectedProvince}`).then(data => givenData = data)).then(function (val) {
        for (let i = 0; i < val.length; i++) {
            let elementId = document.getElementById('station')
            let opt = document.createElement('option');
            opt.value = val[i].id;
            opt.innerHTML = val[i].name;
            elementId.appendChild(opt);
        }
    });
});

function remove_options(id) {
    $(id)
        .empty()
        .append('<option selected="selected" value="">Please select</option>');
}

//employee working place data validation before send the db
$("#from_place").bind("input", function () {
    let fromValue = Date.parse($(this).val());
    let toValue = Date.parse($("#to_place").val());
    if (fromValue <= toDayInMilliSecond) {
        if (!isNaN(toValue) && fromValue <= toValue) {
            backgroundColourChangeGood($(this));
        }
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
        let alertValue = swal({
            title: "Could you please re-check date you enter ?",
            icon: "warning",
        });
        if (alertValue) {
            $(this).val('');
            alert("this is working from side");
        }
    }

    if (!isNaN(toValue) && !isNaN(fromValue)) {
        if (fromValue <= toDayInMilliSecond && toValue <= toDayInMilliSecond && fromValue <= toValue) {
            backgroundColourChangeGood($(this));
            backgroundColourChangeGood($("#to_place"));
        } else {
            backgroundColourChangeBad($("#to_place"));
            backgroundColourChangeBad($(this));
            let alertValue = swal({
                title: "Could you please re-check date - range you entered ?",
                icon: "warning",
            });
            if (alertValue) {
                $(this).val('');
                $("#to_place").val('');
            }
        }
    }

    if (isNaN(fromValue)) {
        backgroundColourChangeNothingToChange($(this));
    }
});

$("#to_place").bind("input", function () {
    let fromValue = Date.parse($("#from_place").val());
    let toValue = Date.parse($(this).val());

    if (toValue <= toDayInMilliSecond) {
        if (!isNaN(fromValue) && toValue <= fromValue) {
            backgroundColourChangeGood($(this));
        }
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
        let alertValue = swal({
            title: "Could you please re-check date you enter ?",
            icon: "warning",
        });
        if (alertValue) {
            $(this).val('');
        }
    }
    if (!isNaN(toValue) && !isNaN(fromValue)) {
        if (fromValue <= toDayInMilliSecond && toValue <= toDayInMilliSecond && fromValue <= toValue) {
            backgroundColourChangeGood($(this));
            backgroundColourChangeGood($("#from_place"));
        } else {
            backgroundColourChangeBad($("#from_place"));
            backgroundColourChangeBad($(this));
            let alertValue = swal({
                title: "Could you please re-check date - range you entered ?",
                icon: "warning",
            });
            if (alertValue) {
                $(this).val('');
                $("#from_place").val('');
            }
        }
    }
    if (isNaN(toValue)) {
        backgroundColourChangeNothingToChange($(this));
    }
});