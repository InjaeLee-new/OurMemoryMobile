![a](https://user-images.githubusercontent.com/71866565/109638381-4e5b1b00-7b91-11eb-9e86-3892a663f81a.PNG)<br>
![image](https://user-images.githubusercontent.com/71866565/109779240-18796d80-7c49-11eb-834e-0d57b10bbb40.png)
![image](https://user-images.githubusercontent.com/71866565/109779339-347d0f00-7c49-11eb-8009-f741ddc0f8d5.png)
![image](https://user-images.githubusercontent.com/71866565/109779104-f253cd80-7c48-11eb-8194-408ab34646ab.png)

## 우리들의 추억 저장소 (App)
웹 & 앱 연동 추억 저장 통합 SNS  
- Oracle
- spring3

## 개요
- 국기과정 Java를 이용한 웹 & 앱 과정에서 배운 것들을 모두 넣어보기로 계획
- 클라이언트의 선택 카테고리 맞춤
- my-batis를 이용한 Database 연동 프로젝트 구현

## 구현기능
- 로그인 및 회원가입, 이벤트 페이지
- 푸쉬 알림, 구글 로그인
- 공지사항, FAQ, 1:1 문의
- 게시판 이미지 여러개 업로드 기능
- 게시글 내 댓글, 좋아요 싫어요 기능, 공유하기, 수정, 삭제 기능
- 회원 타임라인 (댓글등록, 감정표현 이벤트시 타임라인에서 리스트 출력)

## 설계중점
- Github을 통한 원활한 코드 관리
- 직관적이고 사용하기 쉬운 UI/UX 
- 실시간 데이터 처리
- 웹 & 앱 연동과 실시간 데이터 처리
- 기기 토큰을 활용하여 자동 로그인

## 차후 업데이트 및 보완점
- 팔로워 기능 미구현
- 로그인 성공 / 실패 여부 체크 추가
- 서버와의 데이터 연동시 request, response 시간 개선 필요
- 게시글 추천여부 미표현 및 게시글 사진 수정 기능 개선 필요
- 내 개인정보 수정 기능 필요
- API를 활용한 SNS 로그인 기능 미완성


## Development environment

| 종류 | 이름 |
| ------ | ------ |
| 개발 툴 | STS3, Android Studio, sql developer, firebase, postman |
| 버전 관리 | GitHub |
| 데이터베이스 | Oracle |
| 언어 | Java, Sql |

## 역할
> [InjaeLee-new (injae)](https://github.com/InjaeLee-new) ( 프로젝트 총괄 )
##### 앱
- ListView 기능, 댓글, FCM, 팝업 기능, 이벤트 페이지, 서브메뉴 2가지(memory, pet) 구현
##### 웹
- 리스트, 댓글 기능, 마이페이지, 페이징 처리, 웹 회원가입

> [aoqnwnd (seungwon)](https://github.com/aoqnwnd)
##### 앱
- 구글 로그인 API 적용, 상단 고객센터 페이지 제작, ViewPager 기능으로 여러 사진 보기 구현, 글쓰기 기능 구현
- 토탈 리스트 구현, 로그인 세션관리추천, 비추천 기능, 데이터 DB에 저장, 서브메뉴의 리스트 2개( art, health)
##### 웹
- 내가 올린 글만 보기 기능 구현

> [hongsiiiii (sungin)](https://github.com/hongsiiiii)
##### 앱
- 전반적인 디자인 관리, 회원가입 기능 추가, footer 기능(회원 타임라인, 마이페이지), 서브메뉴 2가지(Food, Music) 구현
##### 웹
- Contact us 기능 구현, 전반적인 디자인 관리

> [mysoung00 (sungbin)](https://github.com/mysoung00)
##### 앱
- 글 (수정,삭제) 기능구현,  서브메뉴 2가지(memory, pet) 구현
