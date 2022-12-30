let id = document.getElementById('id'); // 아이디 창
let pw = document.getElementById('pw'); // 패스워드 창
let login_btn = document.getElementById('login_btn'); // 로그인 버튼
let id_alert= document.getElementById('id_alert'); //아이디 미입력 시 경고알림 텍스트
let pw_alert= document.getElementById('pw_alert'); //패스워드 미입력 시 경고알림 텍스트
let keep = document.getElementById('keep'); // 로그인 유지하기 체크박스
let id_info = localStorage.getItem('id_info')?JSON.parse(localStorage.getItem('id_info')):[]; // setitem에서 정의한 id_info라는 키의 데이터 즉 value를 가져와 값이 존재하면 id_info의 데이터를 문자열로 가져오고 값이 존재하지않으면(초기에) 빈 배열을 형성함.
id.value = id_info[id_info.length-1]; // 로그인을 기억하기 위해 로그인유지 체크 후 입력한 id_info 배열의 마지막 값(입력한 가장 최신 값)을 가져와 input값을 가져다 놓음.

login_btn.addEventListener('click',function(){
    if(id.value.length==0){
        id_alert.innerText="필수 입력 항목입니다.";   
        id.style.borderColor="red";
        id.addEventListener('keyup',function(){
            if(id.value.length!==0){
                id_alert.innerHTML="";
                id.style.borderColor="";
            }
        })
    } 
    if(pw.value.length==0){
        pw_alert.innerText="필수 입력 항목입니다.";   
        pw.style.borderColor="red";
        pw.addEventListener('keyup',function(){
            if(pw.value.length!==0){
                pw_alert.innerHTML="";
                pw.style.borderColor="";
            } 
        })
    }  
    // 아이디 유지를 체크 했을 때
    else if(keep.checked){
        alert("로그인 성공");
        update();
    }
    else{
        alert("로그인 성공");
    }
})

// id값이 저장되어 있지 않으면(초기 입력한 값이 없을 시) id입력창에 "undefined"값이 나타나게 되어
// undefined 출력을 없애기 위해 id값을 초기화 시켜줌.
if(id.value == "undefined"){
    id.value = "";
}

// 로컬스토리지에 저장하여 아이디 정보를 저장
function update(){
    id_info.push(id.value); // 처음 빈 배열 id_info에 입력한 id값을 push로 삽입함.
    localStorage.setItem("id_info", JSON.stringify(id_info)); // id_info라는 키값과 배열의 값을 문자열로 바꾸기 위해 stringify로 id_info배열에 있는 push한 id.value값을 문자열로 값을 넣음.
}