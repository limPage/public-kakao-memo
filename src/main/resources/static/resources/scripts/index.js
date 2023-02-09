const loginButton = document.getElementById('loginButton');
const text= document.getElementById('text');



text.addEventListener("input",()=>{

    if(text.value!==''){
        window.document.getElementById('send').style.cssText=`background-color:#fae100;
        color:black;
        `
    }
    if(text.value===''){
        window.document.getElementById('send').style.cssText=`background-color:rgba(0,0,0,0.1);
        color:rgba(0,0,0,0.3);
        `
    }

})



loginButton?.addEventListener("click", e => {

    e.preventDefault();
    const loginWindow = window.open('/login', '_blank', 'width=500; height=750');
    const date = new Date
    const itemHtml = `
                    <div class="admin" rel="admin" ">
                    <div class="profile-row">
                        <span class="profile">memo</span>
                    </div>

                    <div class="message-container">
                        <span class="name">admin</span>
                        <span class="message">잠시 후 로그인 확인을 위해 새로고침이 됩니다.</span>

                    </div>

                    <div class="time-row">
                        <span class="time" >${(date.getHours()===24||date.getHours()<12?"오전 ":"오후 ")+
    (date.getHours()>13?date.getHours()-12:date.getHours())+ ':'+date.getMinutes()}</span>
                    </div>
                </div>

                </div>`;
    const itemElement = new DOMParser().parseFromString(itemHtml, 'text/html').querySelector('[rel="admin"]');

    window.document.getElementById("reportContainer").append(itemElement);


    setTimeout(function (){

        window.location.reload();
    },4000)
});


window.document.getElementById('send').addEventListener("click",()=>{
    if(window.document.getElementById('login').value==='0'){
        const date = new Date
        const itemHtml = `
                    <div class="admin" rel="admin" ">
                    <div class="profile-row">
                        <span class="profile">memo</span>
                    </div>

                    <div class="message-container">
                        <span class="name">admin</span>
                        <span class="message">로그인 해주세요.</span>

                    </div>

                    <div class="time-row">
                        <span class="time" >${(date.getHours()===24||date.getHours()<12?"오전 ":"오후 ")+
        (date.getHours()>13?date.getHours()-12:date.getHours())+ ':'+date.getMinutes()}</span>
                    </div>
                </div>

                </div>`;
        const itemElement = new DOMParser().parseFromString(itemHtml, 'text/html').querySelector('[rel="admin"]');

        window.document.getElementById("reportContainer").append(itemElement);
        text.value='';
        resize(text);
        window.document.getElementById('send').style.cssText=`background-color:rgba(0,0,0,0.1);
        color:rgba(0,0,0,0.3);
        `

        setTimeout(function (){

            window.location.reload();
        },4000)

    }else {

        window.open("/authorize?scope=talk_message", "_blank", "width=600; height=900");


        text.value = text.value.replace('$', '')
            .replace('{', '(').replace('}', ')')
            .replace('[', '(').replace(']', ')')

        // if(confirm("입력한 메세지를 내 계정으로 전송하시겠습니까?")) {


            REST_Call('/message?text=' + text.value.replace('$', '')
                .replace('{', '(').replace('}', ')')
                .replace('[', '(').replace(']', ')'));

        // }
    }

})

function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = (12 + obj.scrollHeight) + 'px';
}



function REST_Call (path)
{
    $.ajax({
        type: "GET",
        url: path,
        success: (data) => {

            const date = new Date
            const itemHtml = `
                 <div class="report" rel="report">
                    <div class="time-row">
                        <span class="time">${(date.getHours()===24||date.getHours()<12?"오전 ":"오후 ")+
            (date.getHours()>13?date.getHours()-12:date.getHours())+ ':'+date.getMinutes()} </span>
                    </div>

                    <div class="message-container">
                       
                        <span class="message">${text.value.substring(0, 200)} ${text.value.length>=200?'...':''}</span>

                    </div>

                   
                </div>`;
            const itemElement = new DOMParser().parseFromString(itemHtml, 'text/html').querySelector('[rel="report"]');

            window.document.getElementById("reportContainer").append(itemElement);
           text.value='';
            resize(text);
        }




    });


}