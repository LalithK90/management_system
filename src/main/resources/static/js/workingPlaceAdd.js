$(document).ready(function () {
//Millisecond to day
    const toDayInMilliSecond = Date.parse(new Date());

//Employee working place data validation before send the db
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
});


