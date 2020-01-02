$(document).ready(function () {
    const urlCame = $('#offenderURL').val();

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
            url: urlCame,
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

//received offender set dbOffenderList array
    let dbOffenderListFill = function (data) {
        for (let i = 0; i < data.length; i++) {
            //received data filled object
            let receivedOffender = {};
            receivedOffender.id = data[i].id;
            receivedOffender.offenderRegisterNumber = data[i].offenderRegisterNumber;
            receivedOffender.nameEnglish = data[i].nameEnglish;
            receivedOffender.nic = data[i].nic;
            receivedOffender.passportNumber = data[i].passportNumber;
            receivedOffender.fileInfos = [];
            for (let j = 0; j < data[i].fileInfos.length; j++) {
                let imageURL = {};
                imageURL.filename = data[i].fileInfos[j].filename;
                imageURL.createAt = data[i].fileInfos[j].createAt;
                imageURL.url = data[i].fileInfos[j].url;
                receivedOffender.fileInfos.push(imageURL);
            }
            receivedOffender.age = data[i].age;
            dbOffenderList.push(receivedOffender);
        }
        if (dbOffenderList.length !== 0) {
            dbListToShowTable();
        }
    };
//offender display which came in db
    let dbListToShowTable = function () {
        let table = document.getElementById("myTableData");
        let tableRowCount = table.rows.length;
        let row = table.insertRow(tableRowCount);

        for (let i = 0; i < dbOffenderList.length; i++) {
            let index = i + 1;
            row.insertCell(0).innerHTML = index;
            row.insertCell(1).innerHTML = dbOffenderList[i].id;
            row.insertCell(2).innerHTML = dbOffenderList[i].offenderRegisterNumber;
            row.insertCell(3).innerHTML = dbOffenderList[i].nameEnglish;
            row.insertCell(4).innerHTML = dbOffenderList[i].nic;
            row.insertCell(5).innerHTML = dbOffenderList[i].passportNumber;
            row.insertCell(6).innerHTML = dbOffenderList[i].age;
            row.insertCell(7).innerHTML = `<img src="${dbOffenderList[i].fileInfos[1].url}" class="rounded" style="height: 150px; width: 150px; border-radius: 10px" alt="Offender image"/>`;
            row.insertCell(8).innerHTML = `<button type="button" class="btn btn-primary btn-sm " onclick="showSelectOffender(this)" data-toggle="modal" data-target=".bd-example-modal-xl"> Select &nbsp;<i class="fa fa-thumbs-up"></i></button>`;

        }
        dbOffenderList = [];
    };


});

//selected Offender list
let selectedOffenderList = [];

let showSelectOffender = function (obj) {
    $('#selectedOffenderShow').show();
    let index = obj.parentNode.parentNode.rowIndex;
    // GET THE CELLS COLLECTION OF THE CURRENT ROW.
    let objCells = myTableData.rows.item(index).cells;

    // check this employee already selected or not
    checkOffenderInArrayOrNot(objCells);
};

// row data convert to employee
let rowDataToOffender = function (rowDetails) {
    const offender = {};
    for (let i = 0; i <= rowDetails.length; i++) {
        switch (i) {
            case 0:
                break;
            case 1:
                offender.id = rowDetails[i].innerHTML;
                break;
            case 2:
                offender.offenderRegisterNumber = rowDetails[i].innerHTML;
                break;
            case 3:
                offender.nameEnglish = rowDetails[i].innerHTML;
                break;
            case 7:
                offender.imageUrl = rowDetails[i].innerHTML;
                /* let checkField = [];
                 offender.contravenes = [];
                 //when model close
                 $('#Modal').modal('show');
                 $("#btnModelClose").bind('click', function () {
                     checkField = $(".contraveneCheckBox:checked");
                     console.log(checkField.length+" leangth ");
                     const contravene = {};

                     for (let i = 0; i < checkField.length; i++) {
                         contravene.id = checkField[i].id;
                         contravene.detail = $(`.${checkField[i].id}`).html();
                         offender.contravenes.push(contravene);
                     }
                 });*/
                break;
            default:
                break;
        }
        return offender;
    }
};

//already in array or not
let checkOffenderInArrayOrNot = function (rowDetails) {
    let existOrNot;
    //take an employee which was selected
    let offender = rowDataToOffender(rowDetails);
    // no employee test in Array
    if (selectedOffenderList.length === 0) {
        selectedOffenderList.push(offender);
        //when model close
        $('#Modal').modal('show');
        // addRowToSelectedOffenderTable(offender);

    } else {
        for (let i = 0; i < selectedOffenderList.length; i++) {
            if (selectedOffenderList[i].id === offender.id) {
                existOrNot = true;
                break;
            }
        }
        if (existOrNot) {
            swal({
                title: "Already selected one ",
                icon: "warning",
            });
        } else {
            selectedOffenderList.push(offender);
            //when model close
            $('#Modal').modal('show');
            // addRowToSelectedOffenderTable(offender);
        }
    }
};
//model is closed
$("#btnModelClose").bind('click', function () {
    const offender = {};
    let checkField;
    offender.contravenes = [];
    checkField = $(".contraveneCheckBox:checked");
    console.log(checkField.length + " leangth ");
    const contravene = {};

    for (let i = 0; i < checkField.length; i++) {
        contravene.id = checkField[i].id;
        contravene.detail = $(`.${checkField[i].id}`).html();
        offender.contravenes.push(contravene);
    }
});


let addRowToSelectedOffenderTable = function (offender) {
    let selectedOffenderTable = document.getElementById("selectedOffenderTable");
    let rowCount = selectedOffenderTable.rows.length;
    let row = selectedOffenderTable.insertRow(rowCount);
    /*let innerHtml = employee.id.split(">");*/

    row.insertCell(0).innerHTML = rowCount;
    row.insertCell(1).innerHTML = `<input type="text" name="offenders[${rowCount - 1}].offender" value="${offender.id}" readonly/>`;
    row.insertCell(2).innerHTML = `<input type="text" name="offenders[${rowCount - 1}].offenderRegisterNumber" value="${offender.offenderRegisterNumber}" readonly/>`;
    row.insertCell(3).innerHTML = `<input type="text" name="offenders[${rowCount - 1}].offender" value="${offender.nameEnglish}" readonly/>`;
    let innerHtmlList = "";
    for (let i = 0; i < offender.contravenes.length; i++) {
        innerHtmlList += `<ul class="list-group list-group-flush">
                            <li class="list-group-item"><input type="hidden" name="offenders[${rowCount - 1}].offender.contravenes[${i}].id" value="${offender.contravenes[i].id}" /> <input type="text" name="offenders[${rowCount - 1}].offender.contravenes[${i}].detail" value="${offender.contravenes[i].detail}"  readonly/></li>
                          </ul>`
    }

    row.insertCell(4).innerHTML = innerHtmlList;
    row.insertCell(5).innerHTML = `<button type="button" class="btn btn-danger btn-sm " onclick="deletedSelect(this)"> Remove &nbsp;<i class="fa fa-trash"></i></button>`;

};

let deletedSelect = function (obj) {
    let index = obj.parentNode.parentNode.rowIndex;
    let table = document.getElementById("selectedEmployee");
    // GET THE CELLS COLLECTION OF THE CURRENT ROW.
    let rowDetails = selectedEmployee.rows.item(index).cells;
    //REMOVE DELETED EMPLOYEE FORM SELECTED EMPLOYEE LIST
    let employee = rowDataToEmployee(rowDetails);

    let removeSelectedEmployee;
    for (let i = 0; i < SelectedEmployeeList.length; i++) {
        if (SelectedEmployeeList[i].id === employee.id) {
            if (SelectedEmployeeList.length === 0) {
                SelectedEmployeeList = [];
            } else {
                removeSelectedEmployee = SelectedEmployeeList.splice(i, 1);
                break;
            }
        }
    }
    table.deleteRow(index);
};

//get a final name and set it to next note name
let noteNameSet = function () {
    let countFiledLength = $(".detectionTeamNoteHide").length;
    let fieldName = `detectionTeamNotes[${countFiledLength}].note`;
    $("#detectionTeamNote").attr("name", fieldName);
};


