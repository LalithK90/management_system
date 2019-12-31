$(document).ready(function () {
    const url = $('#offenderURL').val();

// post body data
    let offender = {};
//post data array to attach body
    let callingName = {};
    callingName.callingName = '';

//Offender list came form db
    let dbOffenderList = [];

//default help id was disable if user which find offender using calling name advice will show
    $('#helpId, #selectedOffenderShow, #findOffenderShow').hide();

//searching criteria was selected need to add value add filed to it name
    $('#searchCriteria').bind('change', function () {
        searchButton();
        let searchValue = $('#searchValue');
        let helpId = $('#helpId');

        searchValue.val('');
        let selectedParameter = $(this).val();

        if (selectedParameter === "NAME") {
            searchValue.attr("name", "nameEnglish");
            helpId.hide();
        }
        if (selectedParameter === "NIC") {
            searchValue.attr({"name": "nic", "class": "nic form-control col-md-9"});
            helpId.hide();
        }
        if (selectedParameter === "PASSPORT") {
            searchValue.attr("name", "passportNumber");
            helpId.hide();
        }
        if (selectedParameter === "MOBILE") {
            searchValue.attr({"name": "mobileOne", "class": "mobile form-control col-md-9"});
            helpId.hide();
        }
        if (selectedParameter === "CALLING") {
            searchValue.attr("name", "callingName");
            helpId.show();
        }
    });
//if there is nothing on search filed search button will disable
    $('#searchValue').keyup(function () {
        searchButton();
    });
    let searchButton = function () {
        if ($('#searchValue').val() === '') {
            //Check to see if there is any text entered
            // If there is no text within the input ten disable the button
            $('#btnOffenderSearch').prop('disabled', true);
        } else {
            //If there is text in the input, then enable the button
            $('#btnOffenderSearch').prop('disabled', false);
        }
    };

//when search button click
    $('#btnOffenderSearch').bind('click', function () {
        let searchValue = $('#searchValue');
        let attributeName = searchValue.attr('name');
        if (attributeName === "nameEnglish") {
            offender.nameSinhala = searchValue.val();
        }
        if (attributeName === "nic") {
            offender.nic = searchValue.val();
        }
        if (attributeName === "passportNumber") {
            offender.passportNumber = searchValue.val();
        }
        if (attributeName === "mobileOne") {
            offender.mobileOne = searchValue.val();
        }
        if (attributeName === "callingName") {
            offender.offenderCallingNames = [];
            let callingNames = searchValue.val().split(',');
            for (let i = 0; i < callingNames.length; i++) {
                callingName.callingName = callingNames[i];
                offender.offenderCallingNames.push(callingName);
            }
        }

        $.ajax({

            type: "post",
            dataType: 'json',
            contentType: 'application/json',
            url: url,
            data: JSON.stringify(offender),

            success: function (data, textStatus) {
                $('#findOffenderShow').show();

                if (textStatus === 'success') {
                    dbOffenderListFill(data);
                }
                console.log("text status " + textStatus);
//alert("success");
            },
            error: function (xhr, textStatus, errorThrown) {
//alert('request failed'+errorThrown);
                console.log('request failed   ' + errorThrown);
            }
        });


    });
    $('#selectedOffenderShow').show();
//received offender set dbOffenderList array
    let dbOffenderListFill = function (data) {
        console.log(data.length + " data array length ");
        for (let i = 0; i < data.length; i++) {
            //received data filled object
            let receivedOffender = {};
            receivedOffender.id = data[i].id;
            receivedOffender.offenderRegisterNumber = data[i].offenderRegisterNumber;
            receivedOffender.nameEnglish = data[i].nameEnglish;
            receivedOffender.nic = data[i].nic;
            receivedOffender.passportNumber = data[i].passportNumber;
            receivedOffender.age = data[i].age;
            receivedOffender.fileInfo = data[i].fileInfo;
            dbOffenderList.push(receivedOffender);
        }
        console.log("came db offender " + dbOffenderList[0].nameEnglish);
    };

    $("#btnModelClose").bind('click', function () {
        let checkField = $(".contraveneCheckBox:checked");
        console.log(checkField.length);
       for (let i = 0; i <checkField.length; i++){
           console.log("value "+ checkField[i].id + " field id "+ $(`.${checkField[i].id}`).html());
       }
    });


});