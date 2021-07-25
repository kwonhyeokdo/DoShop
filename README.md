# DoShop

2021-07-16 
DoShop Git으로 관리 시작

2021-07-17
자동로그인, 로그인시 이메일 저장 추가

2021-07-18
기존의 session, cookie를 @Autowired로 사용했는데 RequestContextHolder를 사용하여 구현하도록 수정.
쿠키의 수정은 기존 쿠키에서 set 계열 메서드를 사용하는 것이 아닌 단순이 이름이 같은 쿠키를 다시 생성하면 됨.

2021-07-20 
쿠키는 path와 domain이 있고 상위 경로로 지정되어 있을경우 하위 경로는 접근 못한다는 것을 알게 됨.
이메일 발송 시스템을 스레드로 빼서 지연 시간 없앰.
이메일, 휴대폰 인증 시스템 수정.
Congratulations.jsp, Notice.jsp와 같이 투박했던 페이지 html, css 수정.
/DoShop/Member 안의 디렉터리 및 Controller 구조 변경.

2021-07-21
쿠키는 path와 domain이 있고 상위 경로로 지정되어 있을경우 하위 경로는 접근 못한다는 것을 알게 됨.
기존의 mail 발송 시스템에서 mail 발송 과정에 속도 지연이 심해 Thread로 따로 실행되게 만듦.

2021-07-22
회원가입, 비밀번호 찾기와 같은 보안 사이트에 대한 인증 절차를 하나 더 만듦.
cors 설정.

2021-07-26
회원 정보 수정 페이지 구현.
ajax로 json을 커맨드 객체에 넣을려면
 @RequestBody는 jsonStringfy를 사용해야 커맨드 객체에 값을 넣을 수 있다.
 @RequestBody를 사용하지 않으면 json을 바로 커맨드 객체에 넣을 수 있다.
 이때 ajax에서 data: jsonData 이와같은 형태로 data를 전송해야한다 data: {} 이 형태는 @RequestParam에 대응되는 것 같다.
 json으로 커맨드 객체를 주입할때 존재하지 않은 프로퍼티는 null값이 들어간다.
SpringMVC에서 HttpServletResponse → PrintWriter → flush() 사용후 redirect는 사용 못한다.
@Transaction이 적용된 곳에서 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 으로 강제로 트랜잭션 롤백을 실행할수 있다.
MultipartResolver은 WebMvcConfigurer 인터페이스가 적용된 곳에서 빈으로만 만들어주면 사용 가능하다.
