# DoShop

2021-07-16 DoShop Git으로 관리 시작
2021-07-17 자동로그인, 로그인시 이메일 저장 추가
2021-07-18 기존의 session, cookie를 @Autowired로 사용했는데 RequestContextHolder를 사용하여 구현하도록 수정.
           쿠키의 수정은 기존 쿠키에서 set 계열 메서드를 사용하는 것이 아닌 단순이 이름이 같은 쿠키를 다시 생성하면 됨.
2021-07-20 쿠키는 path와 domain이 있고 상위 경로로 지정되어 있을경우 하위 경로는 접근 못한다는 것을 알게 됨.
           이메일 발송 시스템을 스레드로 빼서 지연 시간 없앰.
           이메일, 휴대폰 인증 시스템 수정.
           Congratulations.jsp, Notice.jsp와 같이 투박했던 페이지 html, css 수정
           /DoShop/Member 안의 디렉터리 및 Controller 구조 변경
