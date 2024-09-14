var currentdate = new Date();
document.getElementById("timer").innerText =  currentdate.getDate() + "/" +
    + (currentdate.getMonth()+1)  + "/"
    + currentdate.getFullYear() + "    "
    + currentdate.getHours() + ":"
    + currentdate.getMinutes() + ":"
    + currentdate.getSeconds();

setInterval(function () {
    var currentdate = new Date();
    document.getElementById("timer").innerText =  currentdate.getDate() + "/" +
        + (currentdate.getMonth()+1)  + "/"
        + currentdate.getFullYear() + "    "
        + currentdate.getHours() + ":"
        + currentdate.getMinutes() + ":"
        + currentdate.getSeconds();
}, 7000);