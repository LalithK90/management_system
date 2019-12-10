$(document).ready(function () {
    //WYSIWYG add to text area
    bkLib.onDomLoaded(function () {
        nicEditors.allTextAreas()
    });
    $('.input-images').imageUploader();


//According to province need to find district and station
    $(".districtShow, .stationShow, #employees, #updateStatus").hide();

//Get district list
    $("#provinces").bind("change", function () {
        let urlValue = $("#districtUrl").val();
        let selected = $(this).val();
        let selectedProvince = urlValue + selected;
        //workingPlaceRest given district list according to given province
        let givenDatas = [];
        $(".districtShow").show();
        $(".stationShow").hide();
        if ($('#districts > option').val() !== undefined) {
            remove_options($("#districts"));
        }
        Promise.resolve(getData(`${selectedProvince}`).then(data => givenDatas = data)).then(function (val) {
            for (let i = 0; i < val.length; i++) {
                let elementId = document.getElementById('districts');
                let opt = document.createElement('option');
                opt.innerHTML = val[i].district;
                opt.value = val[i].name;
                elementId.appendChild(opt);
            }
        });
    });

//Get lists from DB and manage it to combo box
    $("#districts").bind("change", function () {
        let urlValue = $("#stationUrl").val();
        let selected = $(this).val();
        let selectedProvince = urlValue + selected;
        //workingPlaceRest given district list according to given province
        let givenDatas = [];
        $(".stationShow").show();
        if ($('#stations > option').val() !== undefined) {
            remove_options($("#stations"));
        }
        Promise.resolve(getData(`${selectedProvince}`).then(data => givenDatas = data)).then(function (val) {
            for (let i = 0; i < val.length; i++) {
                let elementId = document.getElementById('stations');
                let opt = document.createElement('option');
                opt.value = val[i].id;
                opt.innerHTML = val[i].name;
                elementId.appendChild(opt);
            }
        });
    });

    //Get employees from using station ad designation
    $("#stations, #designation").bind('change', function () {
        let station = $("#stations").val();
        let designation = $("#designation").val();
        //if two value is null automatically page is reload
        if  (station === null || designation === null){
            location.reload();
        }

        if (station.length !== 0  && designation.length !== 0 ) {
            let urlValue = $("#employeeUrl").val().split("?");
            let finalUrl = urlValue[0] + `?designation=${designation}&id=${station}`;
            $("#employees").show();
            let stationEmployeeList = [];
            remove_options($("#employees"));
            Promise.resolve(
                getData(`${finalUrl}`)
                    .then(data => stationEmployeeList = data))
                .then(function (val) {
                    for (let i = 0; i < val.length; i++) {
                        let elementId = document.getElementById('employees');
                        let opt = document.createElement('option');
                        opt.value = val[i].id;
                        opt.innerHTML = val[i].name;
                        elementId.appendChild(opt);
                    }
                });
        }

    });

    $("#employees").bind('change', function () {
        let station = $("#stations").val();
        let designation = $("#designation").val();
        if (station.length === 0 && designation.length === 0) {
            swal({
                title: "Please check station and designation were selected or not  ",
                icon: "warning",
            });
        }
    });

    function remove_options(id) {
        $(id)
            .empty()
            .append('<option selected="selected" value="">Please select</option>');
    }

//when patiton type is change some petitioner details fill automatically
    $("#petitionType").bind('change', function () {
        let selectValue = $(this).val();
        if (selectValue === "PRESIDENT" || selectValue === "RRIMEMINISTER" || selectValue === "NDDCA") {
            $("#updateStatus").show();
            let selectURL = $("#petitionerUrl").val() + `${selectValue}`;
            console.log(selectURL);
            Promise.resolve(getData(`${selectURL}`).then(function (data) {
                $("#address").val(data.address);
                $("#petitionerId").val(data.id);
                $("#nameSinhala").val(data.nameSinhala);
                $("#nameTamil").val(data.nameTamil);
                $("#nameEnglish").val(data.nameEnglish);
                $("#email").val(data.email);
                $("#land").val(data.land);
                $("#petitionerType").val(data.petitionerType);
                $("#mobileOne").val(data.mobileOne);
                $("#mobileTwo").val(data.mobileTwo);
                $("#createdBy").val(data.createdBy);

            }));
        }else {
            $("#petitionerId, #nameSinhala, #nameTamil, #nameEnglish, #email, #land, #petitionerType, #address, #mobileOne, #mobileTwo").val('');

        }

    });
});