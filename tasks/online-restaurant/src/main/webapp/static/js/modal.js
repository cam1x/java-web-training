var dialogArr;

function openDlg(row) {
    if (dialogArr == null) {
        initDialogs()
    }
    dialogArr[row].showModal();
}

function initDialogs() {
    dialogArr = document.querySelectorAll('dialog');
    for (var i = 0; i < dialogArr.length; ++i) {
        if (!dialogArr[i].showModal) {
            dialogPolyfill.registerDialog(dialogArr[i]);
        }
    }
}

function closeDlg(row) {
    dialogArr[row].close();
}