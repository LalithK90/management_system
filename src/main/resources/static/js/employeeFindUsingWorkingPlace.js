$(document).ready(function () {
    //WYSIWYG add to text area
    bkLib.onDomLoaded(function () {
        nicEditors.findEditor()
    });

    $('.input-images').imageUploader();

//According to province need to find district and station
    $(".districtShow, .stationShow, #employees, #updateStatus").hide();
// next note name set
    noteNameSet();

//if already saved employee comes to frontend, need to add they SelectedEmployeeList before check already in array
    if (document.getElementById("selectedEmployee") !== null) {
        alreadySelectedEmployeeList();
    }


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
        if (station === null || designation === null) {
            location.reload();
        }
        if (station.length !== 0 && designation.length !== 0) {
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

//remove option tag filed in selection
 let remove_options  = function (id) {
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
                console.log(data);
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
        } else {
            $("#petitionerId, #nameSinhala, #nameTamil, #nameEnglish, #email, #land, #petitionerType, #address, #mobileOne, #mobileTwo").val('');

        }

    });

//set all employee to display table Get employees from using station ad designation
    $("#stations, #designations").bind('change', function () {
        let station = $("#stations").val();
        let designation = $("#designations").val();

        //if two value is null automatically page is reload
        if (station === null || designation === null) {
            location.reload();
        }
        //if station and designation selected
        if (station.length !== 0 && designation.length !== 0) {
            let urlValue = $("#employeeUrl").val().split("?");
            let finalUrl = urlValue[0] + `?designation=${designation}&id=${station}`;
            $("#employeeShow").show();

            Promise.resolve(getData(`${finalUrl}`)).then(function (val) {
                //delete existing table data
                deleteAllTableRow(document.getElementById("myTableData"));

                for (let i = 0; i < val.length; i++) {
                    addRow(val[i], ++i);
                }
            });
        }
    });

});
//backend come employee filled to the field in table
let addRow = function (data, id) {
    let table = document.getElementById("myTableData");
    let rowCount = table.rows.length;

    let row = table.insertRow(rowCount);

    row.insertCell(0).innerHTML = id;
    row.insertCell(1).innerHTML = data.id;
    row.insertCell(2).innerHTML = data.name;
    row.insertCell(3).innerHTML = data.payRoleNumber;
    row.insertCell(4).innerHTML = data.designation;
    row.insertCell(5).innerHTML = '<button type="button" class="btn btn-primary btn-sm " onclick="showSelect(this)"> Select &nbsp;<i class="fa fa-thumbs-up"></i></button>';

};

//selected list
let SelectedEmployeeList = [];

//when update there is on the employee on selected employee list
// hence, before doing anything need to those employees add employee selected list
let alreadySelectedEmployeeList = function () {
    let tableRowCount = document.getElementById("selectedEmployee").rows.length;
    if (tableRowCount > 1) {
        for (let i = tableRowCount; i > 1; i--) {
            // GET THE CELLS COLLECTION OF THE CURRENT ROW.
            let objCells = selectedEmployee.rows.item(i - 1).cells;
            SelectedEmployeeList.push(rowDataToEmployee(objCells));
        }
    }
};

//remove selected table show list
let showSelect = function (obj) {
    let index = obj.parentNode.parentNode.rowIndex;

    // GET THE CELLS COLLECTION OF THE CURRENT ROW.
    let objCells = myTableData.rows.item(index).cells;

    // check this employee already selected or not
    checkEmployeeInArrayOrNot(objCells);
};

// row data convert to employee
let rowDataToEmployee = function (rowDetails) {
    const employee = {};
    for (let i = 0; i <= rowDetails.length; i++) {
        switch (i) {
            case 0:
                employee.index = rowDetails[i].innerHTML;
                break;
            case 1:
                employee.id = rowDetails[i].innerHTML;
                break;
            case 2:
                employee.name = rowDetails[i].innerHTML;
                break;
            case 3:
                employee.payRoleNumber = rowDetails[i].innerHTML;
                break;
            case 4:
                employee.designation = rowDetails[i].innerHTML;
                break;
            default:
                return employee;
        }
    }
};

//already in array or not
let checkEmployeeInArrayOrNot = function (rowDetails) {
    let existOrNot;
    //take an employee which was selected
    let employee = rowDataToEmployee(rowDetails);
    // no employee test in Array
    if (SelectedEmployeeList.length === 0) {
        SelectedEmployeeList.push(employee);
        addRowToSelectedTable(employee);

    } else {
        for (let i = 0; i < SelectedEmployeeList.length; i++) {
            if (SelectedEmployeeList[i].id === employee.id) {
                console.log(" im here both same");
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
            SelectedEmployeeList.push(employee);
            addRowToSelectedTable(employee);
        }
    }
};

let addRowToSelectedTable = function (employee) {
    let table = document.getElementById("selectedEmployee");

    let tableRowCount = table.rows.length;
    let row = table.insertRow(tableRowCount);
    /*let innerHtml = employee.id.split(">");*/

    row.insertCell(0).innerHTML = tableRowCount;
    row.insertCell(1).innerHTML = employee.id;
    row.insertCell(2).innerHTML = `<input type="hidden" name="detectionTeamMembers[${tableRowCount - 1}].employee" value="${employee.id}" />`;
    row.insertCell(3).innerHTML = employee.name;
    row.insertCell(4).innerHTML = `<div>
                                            <input type="radio" id="teamMemberStatusValue${tableRowCount}" name="detectionTeamMembers[${tableRowCount - 1}].detectionTeamMemberRole" value="INCHARGE" required/> 
                                            <label for="teamMemberStatusValue${tableRowCount}"> In charge</label>
                                        </div>
                                        <div>
                                            <input type="radio" id="teamMemberStatusValue1${tableRowCount}" name="detectionTeamMembers[${tableRowCount - 1}].detectionTeamMemberRole" value="MEMBER" checked/> 
                                            <label for="teamMemberStatusValue1${tableRowCount}">Member</label>    
                                        </div>             
                                        <div>
                                            <input type="radio" id="teamMemberStatusValue2${tableRowCount}" name="detectionTeamMembers[${tableRowCount - 1}].detectionTeamMemberRole" value="DRIVER"/> 
                                            <label for="teamMemberStatusValue2${tableRowCount}">Driver</label>     
                                        </div>
                                                     
`;
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

//delete all row before show objects in table
let deleteAllTableRow = function (tableName) {
    let table = tableName;
    let rowCount = table.rows.length;
    for (let x = rowCount - 1; x > 0; x--) {
        table.deleteRow(x);
    }
};

