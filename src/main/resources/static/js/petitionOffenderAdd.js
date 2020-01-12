//default help id was disable if user which find offender using calling name advice will show
$('#helpId, #findOffenderShow').hide();
//search button display or not
let searchButton = function () {
    if ($('#searchValue').val() === '' || $('#searchCriteria').val() === '') {
        //Check to see if there is any text entered
        // If there is no text within the input ten disable the button
        $('#btnOffenderSearch').prop('disabled', true);
    } else {
        //If there is text in the input, then enable the button
        $('#btnOffenderSearch').prop('disabled', false);
    }
};
searchButton();
//url from backend
const urlCame = $('#offenderURL').val();

// post body data
let offender = {};
//post data array to attach body
let callingName = {};
callingName.callingName = '';
//Offender list came form db
let dbOffenderList = [];
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
            //table show myTableData
            $('#findOffenderShow').show();
            //table exiting row delete myTableData
            deleteAllTableRow(document.getElementById('dbOffenderListShow'));
            if (textStatus === 'success') {
                dbOffenderListFill(data);
            }
//alert("success");
        },
        error: function (xhr, textStatus, errorThrown) {
//alert('request failed'+errorThrown);
            console.log('request failed   ' + errorThrown);
        }
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
            for (let i = 0; i < dbOffenderList.length; i++) {
                dbListToShowTable(dbOffenderList[i]);
            }
        }
    };

//offender display which came in db
    let dbListToShowTable = function (dbOffender) {
        let table = document.getElementById("dbOffenderListShow");
        let tableRowCount = table.rows.length;
        let row = table.insertRow(tableRowCount);
        row.insertCell(0).innerHTML = tableRowCount;
        row.insertCell(1).innerHTML = `<input type="hidden" value="${dbOffender.id}"/>`;
        //row.insertCell(2).innerHTML = dbOffender.offenderRegisterNumber;
        row.insertCell(2).innerHTML = dbOffender.nameEnglish;
        row.insertCell(3).innerHTML = dbOffender.nic;
        row.insertCell(4).innerHTML = dbOffender.passportNumber;
        //row.insertCell(6).innerHTML = dbOffender.age;
        row.insertCell(5).innerHTML = `<img src="${dbOffender.fileInfos[1].url}" class="rounded" style="height: 150px; width: 150px; border-radius: 10px" alt="Offender image"/>`;
        row.insertCell(6).innerHTML = `<button type="button" class="btn btn-primary btn-sm " onclick="showSelectOffender(this)"> Select &nbsp;<i class="fa fa-thumbs-up"></i></button>`;
// data-toggle="modal" data-target=".bd-example-modal-xl"
    };
    searchButton();
});
//selected Offender list
let selectedOffenderList = [];

//a selected offender would show
let showSelectOffender = function (obj) {
    $('#selectedOffenderShow').show();
    let index = obj.parentNode.parentNode.rowIndex;
    // GET THE CELLS COLLECTION OF THE CURRENT ROW.
    let objCells = dbOffenderListShow.rows.item(index).cells;
    // check this employee already selected or not
    checkOffenderInArrayOrNot(objCells);

};
//already in array or not
let checkOffenderInArrayOrNot = function (rowDetails) {
    let existOrNot;
    //take an employee which was selected
    let offender = rowDataToOffender(rowDetails);
    // no employee test in Array
    if (selectedOffenderList.length === 0) {
        selectedOffenderList.push(offender);
        addRowToSelectedOffenderTable(offender);
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
            addRowToSelectedOffenderTable(offender);
        }
    }
};
// row data convert to selected offender
let rowDataToOffender = function (rowDetails) {
    const selectedOffender = {};
    for (let i = 0; i <= rowDetails.length; i++) {
        switch (i) {
            case 0:
                break;
            case 1:
                selectedOffender.id = rowDetails[i].innerHTML;
                break;
            case 2:
                selectedOffender.nameEnglish = rowDetails[i].innerHTML;
                break;
            case 3:
                selectedOffender.nic = rowDetails[i].innerHTML;
                break;
            case 4:
                selectedOffender.passportNumber = rowDetails[i].innerHTML;
                break;
            default:
                return selectedOffender;
        }
    }
};

let addRowToSelectedOffenderTable = function (offender) {
    let selectedOffenderTable = document.getElementById("selectedOffenderTable");
    let rowCount = selectedOffenderTable.rows.length;
    let row = selectedOffenderTable.insertRow(rowCount);
    console.log("id " + offender.id + "  all ofeeender" + offender.toString());
    let offenderId = offender.id.slice(0, -1) +` name="petitionOffenders[${rowCount - 1}].id" />` ;
    console.log(" slice offender "+offenderId);
    row.insertCell(0).innerHTML = rowCount;
    row.insertCell(1).innerHTML = offenderId;
    row.insertCell(2).innerHTML = `<input type="text" name="petitionOffenders[${rowCount - 1}].nameEnglish" value="${offender.nameEnglish}" class="form-control" readonly/>`;
    row.insertCell(3).innerHTML = `<input type="text" name="petitionOffenders[${rowCount - 1}].nic" class="form-control" value="${offender.nic}" readonly/>`;
    row.insertCell(4).innerHTML = `<input type="text" name="petitionOffenders[${rowCount - 1}].passportNumber" value="${offender.passportNumber}" class="form-control" readonly/>`;
    row.insertCell(5).innerHTML = `<button type="button" class="btn btn-danger btn-sm " onclick="deletedSelectOffender(this)"> Remove &nbsp;<i class="fa fa-trash"></i></button>`;

};

let deletedSelectOffender = function (obj) {
    let index = obj.parentNode.parentNode.rowIndex;
    let table = document.getElementById("selectedOffenderTable");
    // GET THE CELLS COLLECTION OF THE CURRENT ROW.
    let rowDetails = table.rows.item(index).cells;
    //REMOVE DELETED EMPLOYEE FORM SELECTED EMPLOYEE LIST
    let deleteOffender = rowDataToOffender(rowDetails);
//if table row length is one selected offender list should be null
    let tableRowCount = table.rows.length;
    if (tableRowCount === 2) {
        selectedOffenderList = [];
        $('#selectedOffenderShow').hide();
    }

    let removeSelectedOffender;
    for (let i = 0; i < selectedOffenderList.length; i++) {
        if (selectedOffenderList[i].id === deleteOffender.id) {
            if (selectedOffenderList.length === 0) {
                selectedOffenderList = [];
            } else {
                removeSelectedOffender = selectedOffenderList.splice(i, 1);
                break;
            }
        }
    }
    table.deleteRow(index);
};
