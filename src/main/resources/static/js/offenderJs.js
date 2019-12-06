
$(document).ready(function () {
    //WYSIWYG add to text area
    bkLib.onDomLoaded(function () {
        nicEditors.allTextAreas()
    });
    $('.input-images').imageUploader();

    $('#callingNameButton').bind('click', function () {
        addCallingNameRow();
    });

    let addCallingNameRow = function () {
        let listName = 'offenderCallingNames'; //List name in an offender.class
        let fieldsNames = ['callingName']; //Field names from callingName.class
        let rowIndex = document.querySelectorAll('.item').length; //we can add mock class to each calling-name-row

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
            input.setAttribute('name', listName + '[' + rowIndex + '].' + fieldName);
            input.setAttribute('placeholder', 'Enter Calling Name');
            input.setAttribute('required', 'required');

            col.appendChild(input);
            row.appendChild(col);
        });

        document.getElementById('callingNameList').appendChild(row);
    };

    $(this).load(firstCallingNameAdd());

    function firstCallingNameAdd() {
        addCallingNameRow();
    }
});
