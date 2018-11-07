var submitCode = document.querySelector('#submitCode');
var codeBlock = document.querySelector('#textbox');
var resultBlock = document.querySelector('#code_input');

submitCode.addEventListener('mousedown', function(event) {
    let code = codeBlock.value;
    console.log(code, "를 넘기자.")
    fetch(
        `/compile`, 
        {
            'method':'POST',
            'headers': {
                'Content-Type': 'application/json',
            },
            'body': JSON.stringify({
                'code': code,
                'createAuthor': 'user',
                'language' : 'js'
            }),
        }
        )
        .then(data => data.text())
        .then(compile => resultBlock.innerText = compile);
});