$(document).ready(function () {
        //data show content show and hide - start
        contentHide(document.getElementById("medicalPackageDetails"));
        contentHide(document.getElementById("labTestShowTable"));
        contentHide(document.getElementById("patientContent"));
        contentHide(document.getElementById("card"));
        contentHide(document.getElementById("patientListDisplay"));
        //data show content show and hide - end
    }
);

/*// Create new table selected lab test table - start//*/
class LabTest {
    constructor(id, code, name, price) {
        this._id = id;
        this._code = code;
        this._name = name;
        this._price = price;
    }
}

function rowDataToLabTest(rowDetails) {
    const labTest = new LabTest();
    for (let i = 0; i <= rowDetails.length; i++) {
        switch (i) {
            case 0:
                labTest.id = rowDetails[i].innerHTML;
                break;
            case 1:
                labTest.code = rowDetails[i].innerHTML;
                break;
            case 2:
                labTest.name = rowDetails[i].innerHTML;
                break;
            case 3:
                labTest.price = rowDetails[i].innerHTML;
                break;
            default:
                return labTest;
        }
    }
}

//SELECTED LAB TEST
let selectedLabTestArray = [];
//SELECTED MEDICAL PACKAGE
let selectedMedicalPackageId;
//SELECTED LAB TEST TOTAL PRICE
let totalLabTestPrice;
//selected medical package price
let selectedMedicalPackageNameAndPrice;
// get current url
let currentURL = window.location.href;
// regex
let creditVisaCardRegex = /^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/;

//Patient list from taken database
let patientList = [];

function selectedLabTestShowAndHide() {
    let content = document.getElementById("labTestShowTable");
    if (selectedLabTestArray.length === 0) {
        contentHide(content);
    } else {
        contentShow(content);
    }
}

function checkLabTestInArrayOrNot(rowDetails) {

    let existOrNot;
    //take lab test which was selected
    let labTest = rowDataToLabTest(rowDetails);
    // no lab test in Array
    if (selectedLabTestArray.length === 0) {
        selectedLabTestArray.push(labTest);
        addRow(labTest);
        selectedLabTestShowAndHide();
    } else {
        for (let i = 0; i < selectedLabTestArray.length; i++) {
            if (selectedLabTestArray[i]._id === labTest.id) {
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
            selectedLabTestArray.push(labTest);
            addRow(labTest);
        }
    }
}

function addRow(labTest) {
    let table = document.getElementById("myTableData");
    let rowCount = table.rows.length;

    let row = table.insertRow(rowCount);

    updateTotalPrice(labTest.price);

    row.insertCell(0).innerHTML = labTest.id;
    row.insertCell(1).innerHTML = `<input type="text" name="labTests" class="form-control" value="${labTest.id}" readonly>`;
    row.insertCell(2).innerHTML = labTest.code;
    row.insertCell(3).innerHTML = labTest.name;
    row.insertCell(4).innerHTML = '<input type="button" value = "Remove" onClick="deleteRow(this)" class="btn btn-danger">';

}

function deleteRow(obj) {
    let index = obj.parentNode.parentNode.rowIndex;
    let table = document.getElementById("myTableData");
    // GET THE CELLS COLLECTION OF THE CURRENT ROW.
    let objCells = myTableData.rows.item(index).cells;
    //REMOVE DELETED LAB TEST FORM selectedLabTestArray
    let removedLabTest;
    for (let i = 0; i < selectedLabTestArray.length; i++) {
        if (selectedLabTestArray[i]._id === rowDataToLabTest(Object.values(objCells)).id) {
            removedLabTest = selectedLabTestArray[i];
            {
                selectedLabTestArray.splice(i, 1);
                break;
            }
        }
    }
    // remove colour form original lab test array
    let mainTable = document.getElementById("myTable");
    for (let i = 0; i < mainTable.rows.length; i++) {
        let removedLabTestFromArray = rowDataToLabTest(mainTable.rows.item(i).cells);
        if (removedLabTestFromArray.id === removedLabTest._id) {
            mainTable.rows[i].setAttribute("class", "removeLabTest");
        }
    }
    updateTotalPrice();
    table.deleteRow(index);
    selectedLabTestShowAndHide();
}

//remove all lab test in table
function removeLabTestDetail() {
    let table = document.getElementById("myTableData");
    let rowCount = table.rows.length;
    for (let x = rowCount - 1; x > 0; x--) {
        table.deleteRow(x);
    }
}

//update total price medical package and lab test
function updateTotalPrice() {

    totalLabTestPrice = 0;
    selectedLabTestArray.forEach(function (element) {
        totalLabTestPrice += parseFloat(element.price);
    });

    document.getElementById("selectedLabTestCount").innerText = "Selected Lab Test Count = " + selectedLabTestArray.length;
    document.getElementById("totalPrice1").innerText = "Total Price = " + totalLabTestPrice;


    if (isNaN(selectedMedicalPackageNameAndPrice)) {
        $("#totalPrice").val(totalLabTestPrice);
        $("#amount").val(totalLabTestPrice);
    } else {

        document.getElementById("totalPrice").value = totalLabTestPrice + selectedMedicalPackageNameAndPrice;
        $("#amount").val($("#totalPrice").val());
    }
}

/*// Create new table selected lab test table - end//*/

/*//medical package details show and manage - start //*/
$('#cmbMedicalPackage').on("change", function cmbMedicalPackageDetailGet() {
    contentShow(document.getElementById("medicalPackageDetails"));
    // get selected medical package id
    selectedMedicalPackageId = document.getElementById('cmbMedicalPackage').value;
    // ajax response
    let givenData = getData(`${currentURL}/medicalPackageLabTestGet/${selectedMedicalPackageId}`).then(data => givenData = data);

    selectedMedicalPackageNameAndPrice = parseFloat($("#cmbMedicalPackage option:selected").text());

    Promise.resolve(givenData).then(function (val) {
        medicalPackageLabTestSet(val);
    });
    updateTotalPrice();
});

//fill the medical package class details
function medicalPackageLabTestSet(includeLabTest) {
    removeMedicalPackageDetail();
    document.getElementById("includedLabTestCount").innerHTML = includeLabTest.length;
    for (let i = 0; i < includeLabTest.length; i++) {
        fillMedicalPackageDetail(includeLabTest[i]);
    }
}

//access and fill medical package details table
function fillMedicalPackageDetail(medicalPackageLabTest) {
    let table = document.getElementById("myMedicalPackageData");
    let rowCount = table.rows.length;
    let row = table.insertRow(rowCount);

    row.insertCell(0).innerHTML = medicalPackageLabTest.code;
    row.insertCell(1).innerHTML = medicalPackageLabTest.name;
    row.insertCell(2).innerHTML = medicalPackageLabTest.labtestDoneHere;
}

function removeMedicalPackageDetail() {
    let table = document.getElementById("myMedicalPackageData");
    let rowCount = table.rows.length;
    for (let x = rowCount - 1; x > 0; x--) {
        table.deleteRow(x);
    }
}

/*//medical package details show and manage - end //*/

/*//-----------------> Information selection ------ start <----------------------------//*/

/*Patient details taken - start*/
$("#btnNewPatient").on("click", function () {
    contentHide(document.getElementById("patientSearchContent"));
    contentHide(document.getElementById("newPatientID"));
    contentShow(document.getElementById("patientContent"));
    contentShow(document.getElementById("previousNumber"));
    //set value to new patient's number
    let previousPatientNumber = document.getElementById("previousPatientNumber").innerHTML;
    document.getElementById("patientNumber").value = "EHS".concat(Number(previousPatientNumber.slice(3)) + 1);
    //set all new patient value to empty
    $("#id,#number,#patientName,#nic,#dateOfBirth,#email,#mobile,#land").val("").css('background-color', '');
    //$("#id,#number,#patientName,#nic,#dateOfBirth,#email,#mobile,#land").val(""); //this and below ones is the previous one
    //$("#id,#number,#patientName,#nic,#dateOfBirth,#email,#mobile,#land");
});
// language=JQuery-CSS
$("#btnRegisteredPatient").on("click", function () {
    contentShow(document.getElementById("patientSearchContent"));
    contentHide(document.getElementById("patientContent"));
});
//when patient find combo box value change
$("#patientFind").on("change", function () {
    document.getElementById("patientFindValue").value = '';
    document.getElementById("patientFindValue").style.setProperty('background-color', '#ffffff', 'important');
});
//patient search filed validation
$("#patientFindValue").on("keyup", function () {
    let selectedValue = document.getElementById("patientFindValue").value;

    switch (document.getElementById("patientFind").value) {
        case ("name"):
            if (nameRegex.test(selectedValue)) {
                document.getElementById("patientFindValue").style.setProperty('background-color', '#7ae899', 'important');
            } else {
                document.getElementById("patientFindValue").style.setProperty('background-color', '#ff88b3', 'important');
            }
            break;
        case ("nic"):
            if (nicRegex.test(selectedValue)) {
                document.getElementById("patientFindValue").style.setProperty('background-color', '#7ae899', 'important');
            } else {
                document.getElementById("patientFindValue").style.setProperty('background-color', '#ff88b3', 'important');
            }
            break;
        case ("number"):
            if (numberRegex.test(selectedValue)) {
                document.getElementById("patientFindValue").style.setProperty('background-color', '#7ae899', 'important');
            } else {
                document.getElementById("patientFindValue").style.setProperty('background-color', '#ff88b3', 'important');
            }
            break;
        case ("mobile"):
            if (mobileRegex.test(selectedValue)) {
                document.getElementById("patientFindValue").style.setProperty('background-color', '#7ae899', 'important');
            } else {
                document.getElementById("patientFindValue").style.setProperty('background-color', '#ff88b3', 'important');
            }
            break;

    }
});
//search button function
$("#btnSearchPatient").on("click", function () {
    contentShow(document.getElementById("patientListDisplay"));
    //get data from selected parameter
    let selectedParameter = document.getElementById("patientFind").value;
    let selectedValue = document.getElementById("patientFindValue").value;

    switch (selectedParameter) {
        case ("name"):
            if (!nameRegex.test(selectedValue.replace(/\s/g, ''))) {
                swal({
                    title: "Please enter letters ",
                    icon: "warning",
                });
            }
            break;
        case ("nic"):
            if (!nicRegex.test(selectedValue)) {
                swal({
                    title: "Please enter valid NIC ",
                    icon: "warning",
                });
            }
            break;
        case ("number"):
            if (!numberRegex.test(selectedValue)) {
                swal({
                    title: "Please enter valid register number",
                    icon: "warning",
                });
            }
            break;
        case ("mobile"):
            if (!mobileRegex.test(selectedValue)) {
                swal({
                    title: "Please enter valid mobile number",
                    icon: "warning",
                });
            }
            break;
        default :
            swal({
                title: "Please enter select parameter value",
                icon: "warning",
            });
            break;
    }
    let url = currentURL + `/patientFind?${selectedParameter}=${selectedValue}`;

    if (patientList.length !== 0) {
        // make patient list to empty
        patientList = [];
        //delete all row in the lab test show table
    }
    Promise.resolve(getData(url)).then(function (patient) {
        for (let i = 0; i < patient.length; i++) {
            creatNewPatientList(patient[i]);
        }
        sendDataToDetailsForm();
    });
});

// show search details according to patient details
function creatNewPatientList(patient) {
    if (patient === null || patient === undefined) {
        let selectedParameter = document.getElementById("patientFind").value;
        let selectedValue = document.getElementById("patientFindValue").value;
        let message = `System was not able to find patient 
                        according to you provided details
                        Patient's ${selectedParameter} as ${selectedValue} `;
        swal({
            title: message,
            icon: "warning",
        });
    }
    patientList.push(Object.values(patient));
}

function sendDataToDetailsForm() {

    contentHide(document.getElementById("patientSearchContent"));

    if (patientList.length === 1) {
        contentHide(document.getElementById("previousNumber"));
        contentShow(document.getElementById("patientContent"));
        contentHide(document.getElementById("patientListDisplay"));

        fillPatientDetailsForm(patientList[0]);
    }

    if (1 < patientList.length) {
        contentHide(document.getElementById("previousNumber"));
        if (document.getElementById("patientShowTable")) {
            $("#patientShowTable").remove();
        }
        $("#patientListDisplay").append("<table id=\"patientShowTable\" class=\" table table-striped table-condensed \">\n" +
            "<tr>\n" +
            "<th>Register Number</th>\n" +
            "<th>Name</th>\n" +
            "<th>Date of Birth</th>\n" +
            "<th>NIC No</th>\n" +
            "<th>Mobile Number</th>\n" +
            "<th>Add</th>\n" +
            "</tr>\n" +
            "</table>");
        for (let i = 0; i < patientList.length; i++) {
            showPatientList(patientList[i]);
        }
    }
}

function showPatientList(patient) {

    contentShow(document.getElementById("patientListDisplay"));

    //$("#patientShowTable").addClass("");

    contentHide(document.getElementById("patientContent"));

    let table = document.getElementById("patientShowTable");
    let rowCount = table.rows.length;

    let row = table.insertRow(rowCount);

    row.insertCell(0).innerHTML = patient[1];
    row.insertCell(1).innerHTML = patient[2] + " " + patient[3];
    row.insertCell(2).innerHTML = patient[6];
    row.insertCell(3).innerHTML = patient[5];
    row.insertCell(4).innerHTML = patient[8];

    row.insertCell(5).innerHTML = '<input type="button" value = "Select" onClick="fillToForm(this)" class="btn btn-success">';

}

function fillToForm(obj) {
    contentShow(document.getElementById("patientContent"));
    contentHide(document.getElementById("patientListDisplay"));

    $("#id,#number,#title,#patientName,#gender,#nic,#dateOfBirth,#email,#mobile,#land").val("");
    $("#id,#number,#patientName,#nic,#dateOfBirth,#email,#mobile,#land").css('background-color', '');

    let index = obj.parentNode.parentNode.rowIndex;
    console.log(index);
    fillPatientDetailsForm(patientList[index - 1]);

}

function fillPatientDetailsForm(patientInArray) {
    for (let i = 0; i < patientInArray.length; i++) {
        switch (i) {
            case 0:
                $("#id").val(patientInArray[i]);
                break;
            case 1:
                $("#patientNumber").val(patientInArray[i]);
                break;
            case 2:
                $("#title").val(patientInArray[i]);
                break;
            case 3:
                $("#patientName").val(patientInArray[i]);
                break;
            case 4:
                $("#gender").val(patientInArray[i]);
                break;
            case 5:
                $("#nic").val(patientInArray[i]);
                $("#dateOfBirth").val(calculateDateOfBirth(patientInArray[i]));
                break;
            case 6:
                break;
            case 7:
                $("#email").val(patientInArray[i]);
                break;
            case 8:
                $("#mobile").val(patientInArray[i]);
                break;
            case 9:
                $("#land").val(patientInArray[i]);
                break;

        }
    }


}

/*Patient details taken - end*/


//discount ratio apply or not
$("#cmbDiscountRatio").on("change", function () {
    $("#amount").val($("#totalPrice").val() - ($("#totalPrice").val() * (parseFloat($("#cmbDiscountRatio option:selected").text()) / 100)));
});
//payment method show and hide
$("#cmbPaymentMethod").on("change", function () {
    $("#cardNumber, #bankName").val("");
    if ($("#cmbPaymentMethod").val() === "CREDITCARD" || $("#cmbPaymentMethod").val() === "CHEQUE") {
        contentHide(document.getElementById("cash"));
        contentShow(document.getElementById("card"));
    } else {
        contentHide(document.getElementById("card"));
        contentShow(document.getElementById("cash"));

    }
});
//card number validate
$("#cardNumber").on("keyup", function () {
    if ($("#cmbPaymentMethod").val() === "CREDITCARD") {
        $("#typedLength").html($("#cardNumber").val().length);

        document.getElementById("cardNumber").style.setProperty('background-color', '#ff88b3', 'important');

        if ($("#cardNumber").val().length === 15 && creditVisaCardRegex.test($("#cardNumber").val())) {
            document.getElementById("cardNumber").style.setProperty('background-color', '#7ae899', 'important');
            $("#cardType").html("American Express");
        }

        if ($("#cardNumber").val().length === 16 && creditVisaCardRegex.test($("#cardNumber").val())) {
            document.getElementById("cardNumber").style.setProperty('background-color', '#7ae899', 'important');
            $("#cardType").html("Visa, Master, Discover or JCB");
        }
    }

});
//balance settlement
$("#amountTendered").on("keyup", function () {
    $("#balance").val($("#amountTendered").val() - $("#amount").val());

    if ($("#balance").val() < 0) {
        backgroundColourChangeBad($(this));
        contentHide(document.getElementById("btnSubmitInvoice"));
    } else {
        backgroundColourChangeGood($(this));
        contentShow(document.getElementById("btnSubmitInvoice"));
        $("#btnSubmitInvoice").attr('class', 'btn btn-success');

    }

});

/*//-----------------> Information selection ------ end <----------------------------//*/

/*When click the reset button */
$("#reset").on("click", function () {
    removeMedicalPackageDetail();
    removeLabTestDetail();
    totalLabTestPrice = 0;
    selectedLabTestArray = [];
    contentHide(document.getElementById("labTestShowTable"));
    contentHide(document.getElementById("medicalPackageDetails"));

});


