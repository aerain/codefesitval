var submitCode = document.querySelector('#submitCode');
var codeBlock = document.querySelector('#textbox');
var resultBlock = document.querySelector('#code_input');

submitCode.addEventListener('mousedown', function(event) {
    let code = codeBlock.value;
    console.log(code, "를 넘기자.")
    fetch(
        `/compile?code=${code}`, 
        // {
            // 'method':'POST',
            // 'mode': 'no-cors',
            // 'headers': {
            //     'Content-Type': 'application/json',
            //     'X-XSS-Protecton': '1',
            // },
            // 'body': JSON.stringify({'code': code})
        // }
        )
        .then(data => data.text())
        .then(compile => resultBlock.value = compile);
});