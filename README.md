## Spring 게시판 CRUD API

Spring 프레임워크를 이용하여 제작한 게시판 백앤드 이며 각 API 테스트는 포스트맨으로 진행하였습니다.     
모든 API 에는 상태코드가 필수로 반환되며, 에러가 발생한 경우 추가 반환값으로 에러의 메시지를 반환해 줍니다.    

---
- 작업환경
	- IDE: Intellij
	- D B: MySQL
	- Mapper: JPA
- 주요기능
	- 전체 조회
    - 단일 조회
    - 생성
    - 수정
    - 삭제

어플리케이션을 가동 후 http://localhost:8080/swagger-ui.html 에 접속하여 Swagger api 문서를 확인할 수 있습니다.    
![swagger (2)](https://user-images.githubusercontent.com/54667876/94676694-b09bbf80-0356-11eb-8d58-c18ec42e6460.PNG)

** **Swagger를 이용한 실행화면은 하나의 사진으로 담을 수 없어 Postman으로 대체하여 캡처 하였습니다.**    

---
## 전체 조회
입력한 카테고리 및 검색내용으로 삭제되지 않은 게시물을 조회하여 반환해준다.    
![전체 조회 2020-09-22 031432](https://user-images.githubusercontent.com/54667876/93809587-68aad780-fc88-11ea-8fb0-6e700db83280.png)
## 단일 조회
선택한 게시물을 조회하여 반환해준다.    
삭제된 게시물을 조회할 경우 404코드와 지정된 메시지를 반환해준다.
![단일 조회 2020-09-22 035537](https://user-images.githubusercontent.com/54667876/93809562-6183c980-fc88-11ea-9db8-b43a654ee777.png)
## 생성
입력한 정보로 새로운 게시물을 생성한다.    
![생성 2020-09-22 035613](https://user-images.githubusercontent.com/54667876/93809576-66487d80-fc88-11ea-874c-c3bb3b1b9246.png)
## 수정
입력한 정보로 기존의 게시물을 수정한다.    
삭제된 게시물을 수정할 경우 404코드와 지정된 메시지를 반환해준다.
![수정 2020-09-22 035643](https://user-images.githubusercontent.com/54667876/93809581-6779aa80-fc88-11ea-9295-126f101d8928.png)
## 삭제
선택한 게시물을 삭제한다.    
게시물을 삭제할 경우 DB에서 로우를 삭제하는게 아닌 게시물의 상태값을 0으로 변경한다.    
![삭제 2020-09-22 143426](https://user-images.githubusercontent.com/54667876/93847030-c2d78700-fce0-11ea-8a21-5e7dfe57fd63.png)

