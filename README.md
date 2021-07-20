# DoShop

2021-07-16 DoShop Git으로 관리 시작
2021-07-17 자동로그인, 로그인시 이메일 저장 추가
2021-07-18 기존의 session, cookie를 @Autowired로 사용했는데 RequestContextHolder를 사용하여 구현하도록 수정.
           쿠키의 수정은 기존 쿠키에서 set 계열 메서드를 사용하는 것이 아닌 단순이 이름이 같은 쿠키를 다시 생성하면 됨.
