const id = document.querySelector('#id');
const pw1 = document.querySelector('#pw1');
const pw2 = document.querySelector('#pw2');
const names = document.querySelector('#name');
const phone = document.querySelector('#phone');
const birth = document.querySelector('#birth');
const male = document.querySelector('#male');
const female = document.querySelector('#female');
const male_check = document.querySelector('.male_check');
const female_check = document.querySelector('.female_check');
const gender_check_icon = document.getElementsByClassName('xi-check');
const agree = document.getElementsByClassName('agree');

const id_alert = document.querySelector('#id_alert');
const pw1_alert = document.querySelector('#pw1_alert');
const pw2_alert = document.querySelector('#pw2_alert');
const name_alert = document.querySelector('#name_alert');
const phone_alert = document.querySelector('#phone_alert');
const birth_alert = document.querySelector('#birth_alert');
const gender_alert = document.querySelector('#gender_alert');
const SignIn_Btn = document.querySelector('.SignIn_Btn');
// input 의 id값들

// 정규표현식을 리터럴 방식으로 사용
const ID_CHECK =  /^[A-Za-z0-9]{5,15}$/g; // 영문 대문자 또는 소문자 또는 숫자로 시작, 길이 5~15자
// ^문자열 : 특정 문자열로 시작
// [ ] : 괄호안의 문자들 중 하나. or 처리라고 보면 된다. ex) /[abc]/ a 또는 b 또는 c
// [A-Za-z0-9] : A부터 Z까지 대문자 또는 a부터 z까지 소문자 또는 0부터9까지 숫자 
// { } : ex) {n,m} 앞에 입력된 문자가 n개 이상 m개 미만 , {n,} 뒤의 인자를 비워두면 n개 이상을 의미
// g (global): 모든 문자와 여러 줄 일치(전역 검색)

const PW1_CHECK = /(?=.*[a-zA-Z])(?=.*\d)(?=.*[&!@#$%^*+=_()-]).{8,16}/;
// 8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합
// (?=) : 앞쪽 문자열을 기준으로 그룹 조건을 만족하는지를 비교한다.
// . : 모든 텍스트 하나하나(문자, 공백, 특수문자 등의 모든 텍스트)
// * : 0개 이상 (없거나 or 있거나)
// .* : 모든 문자의 0개 이상을 표현한다.
// a-z까지 소문자 또는 A-Z까지 대문자
// 모든 숫자
// 특수문자
// .{8,16} 모든 텍스트 문자길이 8~16자

const NAME_CHECK = /^[가-힣]{2,5}$/;
//이름은 한글만 가능, 가 부터 힣까지 (2~5글자 사이)
// 문자열$: 특정 문자열로 끝남

const PHONENUMBER_CHECK = /\d{2,3}-\d{3,4}-\d{4}$/;
// \d: 숫자
// { } : ex) {n,m} 앞에 입력된 문자가 n개 이상 m개 미만 , {n,} 뒤의 인자를 비워두면 n개 이상을 의미
//첫자리는 숫자 2~3자리 뒤 하이픈
//그 뒤엔 3~4자리 숫자. 그 뒤엔 하이픈 이후 4자리 숫자

const BIRTH_CHECK = /(19\d{2}|20\d{2})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])/;
//19xx-xx-xx형식
// 년도 :19로 시작하고 19뒤에 숫자2개 또는 20으로 시작하고 20뒤에 숫자2개
// 그 다음 하이픈 -입력
// 그 다음 생일이 몇월인지: 01~09 월(10미만인 월) 또는 10월부터 12월까지(10이상인 월)
// 그 다음 하이픈 -입력
// 그 다음 생일이 며칠인지: 01일부터~09일까지(10일 미만) 또는 10일부터~29일까지 또는 30일부터 31일까지 

SignIn_Btn.addEventListener('click',function(){
    // 아이디 검사
    if(!id.value.match(ID_CHECK)){
        id_alert.style.fontSize= '15px';
        id_alert.style.display= 'display';
        id_alert.style.color = 'red';
        id.style.borderColor="red";
        id_alert.innerHTML="아이디를 다시입력해주세요";   
        id.focus();
        id.addEventListener('keyup',function(){
            if(id.value.match(ID_CHECK)){
                id_alert.style.fontSize= '';
                id_alert.style.display= '';
                id_alert.style.color = '';
                id.style.borderColor="";
                id_alert.innerHTML="";
            }
        })
    } 
    else if(!pw1.value.match(PW1_CHECK)){
        // 패스워드 검사
        pw1_alert.style.fontSize= '15px';
        pw1_alert.style.display= 'display';
        pw1_alert.style.color = 'red';
        pw1.style.borderColor="red";
        pw1_alert.innerHTML="비밀번호를 다시입력해주세요";   
        pw1.focus();
        pw1.addEventListener('keyup',function(){
            if(pw1.value.match(PW1_CHECK)){
                pw1_alert.style.fontSize= '';
                pw1_alert.style.display= '';
                pw1_alert.style.color = '';
                pw1.style.borderColor="";
                pw1_alert.innerHTML="";
            }
        })
    }

    else if(!(pw2.value==pw1.value)){
        // 비밀번호 재입력 검사
        pw2_alert.style.fontSize= '15px';
        pw2_alert.style.display= 'display';
        pw2_alert.style.color = 'red';
        pw2.style.borderColor="red";
        pw2_alert.innerHTML="비밀번호를 다시입력해주세요";   
        pw2.focus();
        pw2.addEventListener('keyup',function(){
            if(pw1.value==pw2.value){
                pw2_alert.style.fontSize= '';
                pw2_alert.style.display= '';
                pw2_alert.style.color = '';
                pw2.style.borderColor="";
                pw2_alert.innerHTML="";
            }
        })
    } 
    else if(!names.value.match(NAME_CHECK)){
        name_alert.style.fontSize= '15px';
        name_alert.style.display= 'display';
        name_alert.style.color = 'red';
        names.style.borderColor="red";
        name_alert.innerHTML="이름을 다시입력해주세요";   
        names.focus();
        names.addEventListener('keyup',function(){
            if(names.value.match(NAME_CHECK)){
                name_alert.style.fontSize= '';
                name_alert.style.display= '';
                name_alert.style.color = '';
                names.style.borderColor="";
                name_alert.innerHTML="";
            }
        })
    } 
    else if(!phone.value.match(PHONENUMBER_CHECK)){
        // 전화번호 검사
        phone_alert.style.fontSize= '15px';
        phone_alert.style.display= 'display';
        phone_alert.style.color = 'red';
        phone.style.borderColor="red";
        phone_alert.innerHTML="핸드폰 번호를 다시입력해주세요";   
        phone.focus();
        phone.addEventListener('keyup',function(){
            if(phone.value.match(PHONENUMBER_CHECK)){
                phone_alert.style.fontSize= '';
                phone_alert.style.display= '';
                phone_alert.style.color = '';
                phone.style.borderColor="";
                phone_alert.innerHTML="";
            }
        })
    } 
    else if(!birth.value.match(BIRTH_CHECK)){
        birth_alert.style.fontSize= '15px';
        birth_alert.style.display= 'display';
        birth_alert.style.color = 'red';
        birth.style.borderColor="red";
        birth_alert.innerHTML="생일을 다시입력해주세요";   
        birth.focus();
        birth.addEventListener('keyup',function(){
            if(birth.value.match(BIRTH_CHECK)){
                birth_alert.style.fontSize= '';
                birth_alert.style.display= '';
                birth_alert.style.color = '';
                birth.style.borderColor="";
                birth_alert.innerHTML="";
            }
        })
    } 
    else if(!(male.checked || female.checked)){
        male_check.style.borderColor = 'red';
        gender_alert.style.color = 'red';
        female_check.style.borderColor = 'red';
        gender_alert.style.fontSize= '15px';
        gender_alert.style.display= 'block';
        gender_alert.innerHTML = "필수 입력 항목입니다.";
        male_check.scrollIntoView();
    }
    else if(!(agree[0].checked && agree[1].checked)){
        alert("약관 및 개인정보 동의해주세요");
    }
    else{
        alert("회원가입 완료.");
        location.href = 'login.html'; // 로그인 페이지로 이동
    } 
})

// 성별 라디오 버튼 미체크 시 코드는 정상적이지만
// 체크 시 코드는 미작동하여 따로 함수를 지정하고 onclick으로 함수를 불러와 적용했음. 
function gender_check(){
    if(male.checked){
        male_check.style.borderColor = 'gray';
        female_check.style.borderColor = '';
        gender_check_icon[0].style.display = 'block';
        gender_check_icon[1].style.display = '';
        gender_alert.style.display= '';
        gender_alert.innerHTML = "";
    } else if(female.checked){
        male_check.style.borderColor = '';
        female_check.style.borderColor = 'gray';
        gender_check_icon[1].style.display = 'block';
        gender_check_icon[0].style.display = '';
        gender_alert.style.display= '';
        gender_alert.innerHTML = "";
    }
}

// 정규표현식 : 슬래시 "/"는 자바스크립트에게 정규표현식을 
// 생성하고 있다는 것을 알려준다. (문자열에 따옴표를 쓰는 것과 동일한 역할)
// g (global): 모든 문자와 여러 줄 일치(전역 검색)
// \d: 숫자
// 문자열$: 특정 문자열로 끝남 /com$/
// [^문자]: 괄호안의 문자를 제외한 것 ex) [^lgEn] : "l", "g", "E", "N" 4개 문자를 제외
// ^문자열 : 특정 문자열로 시작 ex) /^www/ 
// [ ] : 괄호안의 문자들 중 하나. or 처리라고 보면 된다. ex) /[abc]/ a 또는 b 또는 c
// ? : ?앞에 입력된 문자가 없거나 하나인 경우(최대 하나)
// * : 없거나 or 있거나
// *? : 없거나,있거나 && 없거나,최대한개 = 없음
// 백슬래시 \ : ^,$ 처럼 특수한 기능을 가진 문자를 일반 문자로
// 포인트 . : 문자, 공백, 특수문자 등의 모든 텍스트 
// + : +앞에 입력된 문자가 하나에서 여러개 (최소 하나)
// { } : ex) {n,m} 앞에 입력된 문자가 n개 이상 m개 미만 , {n,} 뒤의 인자를 비워두면 n개 이상을 의미
// ( ) : 그룹을 표현하며, 소괄호 안의 문자를 하나의 문자로 인식한다.
// $` : 문자 대체 시 일치한 문자 이전 값 참조
// (?=) : 앞쪽 문자열을 기준으로 그룹 조건을 만족하는 지를 비교한다.
// 전방 탐색(lookahead)이란 작성한 패턴에 일치하는 영역이 존재하여도 그 값이 제외되어서 나오는 패턴입니다. 
// 전방 탐색 기호는 ?= 이며, = 다음에 오는 문자가 일치하는 영역에서 제외됩니다. 
// 또한, 전방 탐색은 하위 표현식과 같이 소괄호로 감싸주어야만 합니다. 














