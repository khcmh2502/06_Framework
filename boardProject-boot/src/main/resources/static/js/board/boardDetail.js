// 좋아요 버튼(하트) 클릭 시 비동기로 좋아요 INSERT/DELETE 

// 타임리프 코드 해석 순서
// 1. th: 코드(java) + Spring EL 
// 2. html 코드 ( + css / js)

// 1) 로그인한 회원 번호 준비
// loginMemberNo -> 현재 로그인한 사람의 memberNo
// 2) 현재 게시글 번호 준비
// boardNo  -> 현재 게시글의 번호 
// 3) 좋아요 여부 준비
// likeCheck -> 현재 이 게시글의 likeCheck 값

// 1. #boardLike 가 클릭 되었을때
document.querySelector("#boardLike").addEventListener("click", () => {

  // 2. 로그인 상태가 아닌 경우 동작 X
  if(loginMemberNo == null) {
    alert("로그인 후 이용해주세요");
    return;
  }

  // 3. 준비된 3개의 변수를 객체로 저장 (JSON 변환 예정)
  const obj = {
    "memberNo" : loginMemberNo,
    "boardNo" : boardNo,
    "likeCheck" : likeCheck
  };

  // 4. 좋아요 INSERT/DELETE 비동기 요청
  fetch("/board/like", {
    method: "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj)
  })
  .then(resp => resp.text())
  .then(count => {

   
  });

})