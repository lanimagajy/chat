let refresh = setInterval("window.location.reload(true)", 5000);
var elem = document.querySelector('#sendMessage');


elem.addEventListener('click', () => {
    clearInterval(refresh);
})
elem.addEventListener('blur', () => {
    refresh = setInterval("window.location.reload(true)", 5000);
})

for (var nick of document.getElementsByClassName("nickGoToMessageForm")) {
    nick.onclick = function () {
        elem.value += this.textContent.trim() + ', ';
    }
}
