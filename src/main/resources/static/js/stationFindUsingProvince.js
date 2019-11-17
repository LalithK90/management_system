$(document).ready(function () {
    //WYSIWYG add to text area
    bkLib.onDomLoaded(function () {
        nicEditors.allTextAreas()
    });

//According to province need to find district and station
    $("#districtShow, #stationShow").hide();

//Get district list
    $("#province").bind("change", function () {
        let urlValue = $("#districtUrl").val();
        let selected = $(this).val();
        let selectedProvince = urlValue + selected;
        //workingPlaceRest given district list according to given province
        let givenData = [];
        $("#districtShow").show();
        $("#stationShow").hide();
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

//Get lists from DB and manage it to combo box
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

});



