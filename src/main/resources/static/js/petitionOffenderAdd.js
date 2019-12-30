$(document).ready(function () {
    const url = $("#offenderURL").val();

// post body data
    const sendOffender = {};
    sendOffender.nameSinhala = '';
    sendOffender.nic = '900200226V';
    sendOffender.passportNumber = '';
    sendOffender.mobileOne = '';
    sendOffender.offenderCallingNames = [];
    sendOffender.guardians = [];
    sendOffender.contravenes = [];


// request options
    const options = {
        method: 'POST',
        body: JSON.stringify(sendOffender),
        headers: {
            'Content-Type': 'application/json'
        }
    };

// send POST request
    fetch(url, options)
        .then(res => res.json())
        .then(res => console.log(res));

    //received data filled object
    let receivedOffender = function (id, offenderRegisterNumber, nameEnglish, nic,passportNumber, age, fileInfos) {
        this.id = id;
        this.offenderRegisterNumber = offenderRegisterNumber;
        this.nameEnglish = nameEnglish;
        this.nic = nic;
        this.passportNumber = passportNumber;
        this.age = age;
        this.fileInfos = fileInfos;
    };
});