# 설계
## - DB
#### DB 정보: MYSQL 8.2.0
### 1. 테이블 정보

테이블명   | 내용  |
|---|-----|
 |employee|직원 테이블|
 |office_seat|사무실 좌석 테이블|
 |emp_ofc_use|직원, 사무실 좌석 맵핑 테이블|

### 2. 테이블 상세 정보  
#### - employee
   
이름 | type | 내용 | 조건
---------|----------|---------|---------
 employee_id | bigint | 직원 ID|PK
 name | varchar(100) | 직원 이름 | 
 vacation_yn | varchar(1) | 휴가 여부 |
 use_yn|varchar(1)|사용 여부 |
 regist_date_time|timestamp|등록 일시|
 modify_date_time|timestamp|변경 일시|

#### - office_seat

이름 | type | 내용 | 조건
---------|----------|---------|---------
 office_seat_id | bigint | 사무실 좌석 ID|PK
 office_seat_number | varchar(50) | 사무실 좌석 번호 | 
 office_seat_location | varchar(1000) | 사무실 좌석 위치 |
 use_yn|varchar(1)|사용 여부 |
 regist_date_time|timestamp|등록 일시|
 modify_date_time|timestamp|변경 일시|

 #### - emp_ofc_use

 이름 | type | 내용 | 조건
---------|----------|---------|---------
 office_seat_id | bigint | 사무실 좌석 ID|PK
 reserve_seat_date | date | 좌석 예약 날짜 |PK 
 employee_id | bigint | 직원 ID |
 use_yn|varchar(1)|사용 여부 |
 regist_date_time|timestamp|등록 일시|
 modify_date_time|timestamp|변경 일시|


 ## - API
 #### API 스펙 
 - kotlin 1.9.2 
 - java,jvm 17
 - spring boot 3.2.1
### 1. API 정보
#### API 서비스

|  | 서비스 | url|Method|설명
---------|----------|---------|---------|---------
 1 |전 직원 근무 형태 현황 조회  | /v1/api/employee| GET|- 모든 직원의 근무형태 조회 <br> - 오피스 출근 직원의 경우 좌석번호를 함께 제공 <br> - 20명씩 페이지네이션
 2 |사무실 좌석 예약 | /v1/api/office-seat |POST|- 직원은 본인이 사용할 좌석을 예약<br> - 여러 직원이 동시에 같은 좌석을 예약 불가
 3 |사무실 좌석 예약 취소 | /v1/api/office-seat |PATCH|-  예약을 취소 시 다른 직원이 해당 좌석 예약 가능 <br> - 동일 좌석은 하루 1번만 예약 가능

### 2. API 상세 명세서
#### - 공통 Response 형식

Key | value(예시) | 설명|타입
---------|----------|---------|---------
 result | "S": 성공 <br> "E": 에러 | 결과 값 | String
 resultMessage | "SUCCESS", "ERROR" | 결과 값 메시지 | String
 code| 200, 500, 404 ... | 결과 값에 대한 코드 | Number
 errorMessage| "internal server error" ...| 에러 메시지 |String
 data|Object|결과 값에 대한 데이터 |Object
 dataList|List<Object>|결과 값에 대한 데이터 리스트|Array
 itemTotalCount|20|페이지네이션 시 전체 요소 개수 |Number
pageTotalCount|10|페이지네이션 시 전체 페이지 개수|Number

#### - 전 직원 근무 형태 현황 조회 API

||||| 
---------|----------|---------|---------
 **Request URL** | http://localhost:8089/v1/api/employee/{pageIndex} | **Method**|GET
 **Content-Type** | application/json | **Header**| [X]
 **Path Variable** | *pageIndex*: 조회할 페이지 번호 | **Query Parameter**| [X]
 **Samlple Request URL**|http://localhost:8089/v1/api/employee/1||
 **Request Body**|[X]|
 **Response Body**|
 ||{<br>&emsp;  "result": "S",<br>&emsp;  "resultMessage": "SUCCESS",<br>&emsp;  "code": 200,<br>&emsp;  "dataList": [<br>&emsp;&emsp; {<br>&emsp;&emsp;    "employeeId": 직원 id,<br>&emsp;&emsp;    "name": "직원 이름",<br>&emsp;&emsp;    "workType": "근무 형태"<br>&emsp;&emsp;  },<br>&emsp;...<br>&emsp;],<br>&emsp;  "itemTotalCount": 요소 개수,<br>&emsp;  "pageTotalCount": 전체 페이지 개수<br>}<br>

#### - 사무실 좌석 예약

||||| 
---------|----------|---------|---------
 **Request URL** | http://localhost:8089/v1/api/office-seat | **Method**|POST
 **Content-Type** | application/json | **Header**| [X]
 **Path Variable** | [X] | **Query Parameter**| [X]
 **Samlple Request URL**|http://localhost:8089/v1/api/office-seat||
 **Request Body**|
 ||{<br>  "employeeId": "직원 id",<br>  "officeSeatId": "좌석 id",<br>  "reserveSeatDate": "2023-12-23"<br>}
 **Response Body**|
 ||{<br>&emsp; "result": "S",<br>&emsp;  "resultMessage": "SUCCESS",<br>  &emsp; "code": 201,<br>&emsp;  "data": {<br>&emsp;&emsp;    "employeeId": 직원 id,<br>&emsp;&emsp;    "officeSeatId": 좌석 id,<br>&emsp;&emsp;    "reserveSeatDate": "2023-12-23"<br>&emsp;  }<br>}


 #### - 사무실 좌석 예약 취소

 ||||| 
---------|----------|---------|---------
 **Request URL** | http://localhost:8089/v1/api/cancel/office-seat | **Method**|PATCH
 **Content-Type** | application/json | **Header**| [X]
 **Path Variable** | [X] | **Query Parameter**| [X]
 **Samlple Request URL**|http://localhost:8089/v1/api/cancel/office-seat||
 **Request Body**|
 ||{<br>  "employeeId": "직원 id",<br>  "officeSeatId": "좌석 id",<br>  "reserveSeatDate": "2023-12-23"<br>}
 **Response Body**|
 ||{<br>&emsp; "result": "S",<br>&emsp;  "resultMessage": "SUCCESS",<br>  &emsp; "code": 200,<br>&emsp;  "data": {<br>&emsp;&emsp;    "employeeId": 직원 id,<br>&emsp;&emsp;    "officeSeatId": 좌석 id,<br>&emsp;&emsp;    "reserveSeatDate": "2023-12-23"<br>&emsp;  }<br>}

 ---
 # 실행 방법
 ## 1. DB 구축
 - docker를 활용하여 DB를 구축 후 사용
```shell
docker run -e MYSQL_ROOT_PASSWORD=root123! -e MYSQL_USER=test -e MYSQL_PASSWORD=test123! -e MYSQL_DATABASE=test --name mysql-test -p 3306:3306 -d mysql:8.2.0
```
 - 아래 링크의 DDL SQL코드를 DB에서 실행 **or** 프로젝트 application.yaml 의 `spring.jpa.hibernate.ddl-auto = create`로 변경 후 서버 실행  
  > [DB DDL SQL link](http://github.com)


## 2. API 서버 및 테스트 환경 구축
- jar 또는 IDEA를 이용해 서버 실행
- 직원, 사무실 좌석 더미 데이터 생성을 위해  _CreateDummyData_ class 의 `createEmployee`, `createOfficeSeat`두개의 테스트 코드 실행
  >CreateDummyData class 의 경로 `~/src/test/kotlin/com/example/reserve/demo/setup`
  
## 3. API 테스트
- http://localhost:8089/swagger-ui.html swagger주소에서 테스트