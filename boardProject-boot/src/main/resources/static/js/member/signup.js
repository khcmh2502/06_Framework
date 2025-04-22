//--------------------------------------
// ********* 회원 가입 유효성 검사 *************

// 필수 입력 항목의 유효성 검사 여부를 체크하기 위한 JS객체
// - false : 해당 항목은 유효하지 않은 형식으로 작성됨
// - true  : 해당 항목은 유효한 형식으로 작성됨
const checkObj = {
  "memberEmail"     : false,
  "memberPw"        : false,
  "memberPwConfirm" : false,
  "memberNickname"  : false,
  "memberTel"       : false,
  "authKey"         : false
};

// 인증번호 받기 버튼 요소 
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");

// 인증번호 입력 input 요소
const authKey = document.querySelector("#authKey");

// 인증번호 입력 후 확인 버튼 요소
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");

// 인증번호 관련 메시지 출력 span 요소
const authKeyMessage = document.querySelector("#authKeyMessage");

let authTimer; // 타이머 역할을 할 setInterval 함수를 저장할 변수

const initMin = 4; // 타이머 초기값 (분)
const initSec = 59; // 타이머 초기값 (초)
const initTime = "05:00";

// 실제 줄어드는 시간을 저장할 변수
let min = initMin;
let sec = initSec;

// ------------------------

/* 이메일 유효성 검사 */
// 1) 이메일 유효성 검사에 사용될 요소 얻어오기
const memberEmail = document.querySelector("#memberEmail"); // input
const emailMessage = document.querySelector("#emailMessage"); // span

// 2) 이메일이 입력(input)될 때 마다 유효성 검사 수행
memberEmail.addEventListener("input", (e) => {

  // 작성된 이메일 값 얻어오기
  const inputEmail = e.target.value;

  // 3) 입력된 이메일이 없을 경우
  if(inputEmail.trim().length === 0) {
    emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";

    // 메시지에 색상을 추가하는 클래스 모두 제거
    emailMessage.classList.remove('confirm', 'error');

    // 이메일 유효성 검사 여부를 false 변경
    checkObj.memberEmail = false;

    // 잘못 입력한 띄어쓰기가 있을 경우 없앰
    memberEmail.value = "";

    return;
  }

  // 4) 입력된 이메일이 있을 경우 정규식 검사
  //    (알맞은 형태로 이메일을 작성했는가 검사)
  const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  // 영어, 숫자, 특수문자(. _ % + -) 를 허용
  // 이메일 형식으로 작성
  // 도메인 부분은 최소 2개의 문자로 끝나야함 (.com, .net, .org, .kr 등)

  // 입력받은 이메일이 정규식과 일치하지 않는 경우
  // == 알맞은 이메일 형태가 아닌 경우
  if( !regExp.test(inputEmail) ) {
    emailMessage.innerText = "알맞은 이메일 형식으로 작성해주세요.";
    emailMessage.classList.add('error'); // 글자를 빨간색으로 변경
    emailMessage.classList.remove('confirm'); // 초록색 글씨 제거
    checkObj.memberEmail = false; // 유효하지 않은 이메일임을 기록
    return;
  }

  // 5) 유효한 이메일 형식은 경우 중복 검사 수행
  // 비동기 요청(ajax)
  fetch("/member/checkEmail?memberEmail=" + inputEmail)
  .then(resp => resp.text())
  .then(count => {
    // count : 1이면 중복, 0이면 중복 X
    // ==  : 값이 같은지          ex) "1" == 1 -> true
    // === : 값과 타입까지 같은지  ex) "1" === 1 -> false
    if(count == 1) { // 중복 O
      emailMessage.innerText = "이미 사용중인 이메일입니다.";
      emailMessage.classList.add("error"); 
      emailMessage.classList.remove("confirm");
      checkObj.memberEmail = false; // 중복은 유효하지 않은 상태!
      return;
    }

    // 중복 X 경우
    emailMessage.innerText = "사용 가능한 이메일입니다.";
    emailMessage.classList.add('confirm');
    emailMessage.classList.remove("error"); 
    checkObj.memberEmail = true; // 유효한 이메일

  });

});

// 인증번호 받기 버튼 클릭 시
sendAuthKeyBtn.addEventListener("click", () => {

  // 중복되지 않은 유효한 이메일을 입력한 경우가 아니면
  if( !checkObj.memberEmail ) {
    alert("유효한 이메일 작성 후 클릭해주세요");
    return;
  }

  // 클릭 시 타이머 숫자 초기화
  min = initMin;
  sec = initSec;

  // 이전 동작중인 인터벌 클리어(없애기)
  clearInterval(authTimer);

  // ****************************************
  // 비동기로 서버에서 메일 보내기

  // ****************************************
  // 메일은 비동기로 서버에서 보내라고 놔두고
  // 화면에서는 타이머 시작하기

});
