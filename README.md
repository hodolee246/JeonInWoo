## Spring 게시판 CRUD API

Spring 을 이용하여 제작한 게시판 REST-API 입니다.    
모든 API 에는 상태코드 및 메시지가 반환됩니다.    
테스트의 경우 PostMan 과 테스트 코드를 이용하여 진행하였습니다.    

---
- DB
	- MySQL

- 기능
   - 전체 조회
   - 단일 조회
   - 생성
   - 수정
   - 삭제
- - -
![exception](https://user-images.githubusercontent.com/54667876/113023306-72c20b80-91c0-11eb-9a73-60efd9d0527e.PNG)
복구가 불가능한 예외 발생 시 언체크 예외로 포장하도록 구성    
    
    
![testCode](https://user-images.githubusercontent.com/54667876/113023624-cc2a3a80-91c0-11eb-86b7-7d732a13ab8f.PNG)
각 API에 대한 테스트 코드 및 예외 테스트 구성    
*ID값을 하드코딩으로 구성했기에 전체 테스트 실행이 아닌 개별 테스트 실행 시 테스트가 실패합니다.*
- - -
어플리케이션을 가동 후 http://localhost:8080/swagger-ui.html 에 접속하여 Swagger api 문서를 확인할 수 있습니다.    
![swagger (2)](https://user-images.githubusercontent.com/54667876/94676694-b09bbf80-0356-11eb-8d58-c18ec42e6460.PNG)


