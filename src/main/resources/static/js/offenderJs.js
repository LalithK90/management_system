$(document).ready(function () {
    $('.input-images').imageUploader();

    $('#callingNameButton').bind('click', function () {
        addCallingNameRow();
    });
    //offender details enter from is hide
    $("#offenderDetailShow,#contraveneShow,#callingNameShow,#guardianShow").hide()
    let addCallingNameRow = function () {
        let listName = 'offenderCallingNames'; //List name in an offender.class
        let fieldsNames = ['callingName']; //Field names from callingName.class
        let rowIndex = document.querySelectorAll('.item').length; //we can add mock class to each calling-name-row
        let callingNameId;
        if (rowIndex === 0) {
            callingNameId = rowIndex;
        } else {
            callingNameId = rowIndex - 1;
        }
        let row = document.createElement('div');
        row.classList.add('form-row', 'item', 'col-md-3');

        fieldsNames.forEach((fieldName) => {
            let col = document.createElement('div');
            col.classList.add('col');
            if (fieldName === 'id') {
                col.classList.add('d-none');
                //Field with an id - hidden
            }

            let input = document.createElement('input');
            input.type = 'text';
            input.classList.add('form-control');
            input.id = listName + rowIndex + '.' + fieldName;
            input.setAttribute('name', listName + '[' + callingNameId + '].' + fieldName);
            input.setAttribute('placeholder', 'Enter Calling Name');

            col.appendChild(input);
            row.appendChild(col);
        });
        document.getElementById('callingNameList').appendChild(row);
    };
    /*
        $(this).load(firstCallingNameAdd());

        function firstCallingNameAdd() {
            addCallingNameRow();
        }*/
// guardian details
    $("#guardianButton").bind('click', function () {
        $("#guardianShow").show();
        let table = document.getElementById("guardianTable");
        let rowCount = table.rows.length;

        let row = table.insertRow(rowCount);

        row.insertCell(0).innerHTML = ` <label class="control-label" for="guardian_Nic">NIC No :</label>`;
        row.insertCell(1).innerHTML = `<input id="guardian_Nic" class="form-control" type="text" name="guardians[${rowCount}].nic" maxlength="12" minlength="10"/>`;
        row.insertCell(2).innerHTML = `<label class="control-label" for="guardian_Name">Guardian Name :</label>`;
        row.insertCell(3).innerHTML = `<input id="guardian_Name" class="form-control" name="guardians[${rowCount}].name" type="text"/>`;
    });
    //offender details
    $("#offenderButton").bind('click', function () {
        $("#offenderDetailShow").toggle();
    });
    //contravene details
    $("#contraveneButton").bind('click', function () {
        $("#contraveneShow").toggle();
    });
    //guardian details
    $("#callingNameButton").bind('click', function () {
        $("#callingNameShow").show();
    });
});