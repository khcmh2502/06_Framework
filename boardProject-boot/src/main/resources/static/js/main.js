// 쿠키에 저장된 이메일 input창에 입력해놓기
// 로그인이 안된 경우에 수행

// 쿠키에서 매개변수로 전달받은 key와 일치하는 value 얻어와 반환하는 함수
const getCookie = (key) => {
  const cookies = document.cookie; // "K=V; K=V;..."

  // console.log(cookies);
  
  // cookies 에 저장된 문자열을 배열 형태로 변환
  const cookieList = cookies.split("; ")  // ["K=V", "K=V", ..]
  					.map(el => el.split("="));
}

getCookie();

// 이메일 작성 input 태그 요소
const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");

if(loginEmail != null) { // 로그인폼의 이메일 input태그가 화면상에 존재할 때

  // 쿠키 중 key 값이 "saveId"인 요소의 value 얻어오기
  const saveId = getCookie("saveId");  // 이메일 또는 undefiend

}