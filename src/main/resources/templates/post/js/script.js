const fileInput = document.getElementById('file-input');
const fileLabel = document.getElementById('file-label');
const selectedFiles = document.getElementById('selected-files');

fileInput.addEventListener('change', handleFileSelect);

function handleFileSelect(event) {
    const fileList = event.target.files;
    selectedFiles.innerHTML = '';

    for (let i = 0; i < fileList.length; i++) {
        const file = fileList[i];
        const fileName = file.name;
        const fileItem = document.createElement('div');
        fileItem.innerText = fileName;
        selectedFiles.appendChild(fileItem);
    }
    if (fileList.length > 0) {
        fileLabel.innerText = `${fileList.length} file(s) selected`;
    } else {
        fileLabel.innerText = 'Choose Files';
    }
}
