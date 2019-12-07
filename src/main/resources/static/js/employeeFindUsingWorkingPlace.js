$(document).ready(function () {
    //WYSIWYG add to text area
    bkLib.onDomLoaded(function () {
        nicEditors.allTextAreas()
    });
    $('.input-images').imageUploader();


//According to province need to find district and station
    $(".districtShow, .stationShow, #employees").hide();

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
    //database given employee List
    let stationEmployeeList = [];
    //Get employees from using station ad designation
    $("#stations").bind('change', function () {
        let station = $(this).val();
        if (station !== undefined) {
            let urlValue = $("#employeeUrl").val() + `${station}`;
            $("#employees").show();
            stationEmployeeList = [];
            remove_options($("#employees"));
            Promise.resolve(
                getData(`${urlValue}`)
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
//when designation was changed
    $("#designation").bind('change', function () {
        let designation = $(this).val();
        let newEmployeeArray = [];

        for (let k = 0; k <= stationEmployeeList;) {
            if (stationEmployeeList[k].designation === designation) {
                newEmployeeArray.push(stationEmployeeList[k]);
            }
            k++;
        }
        filterEmployee(newEmployeeArray);
    });

    let filterEmployee = function (arrayValue) {
        for (let i = 0; i < arrayValue.length; i++) {
            let elementId = document.getElementById('employees');
            let opt = document.createElement('option');
            opt.value = arrayValue[i].id;
            opt.innerHTML = arrayValue[i].name;
            elementId.appendChild(opt);
        }
    };
    function remove_options(id) {
        $(id)
            .empty()
            .append('<option selected="selected" value="">Please select</option>');
    }


});