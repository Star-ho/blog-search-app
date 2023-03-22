# 블로그 검색 서비스

## 기능

- 카카오와 네이버의 블로그 검색 API를 활용하여 블로그 검색 기능을 제공합니다.
- 최신순, 정확도순으로 정렬이 가능합니다.
- 카카오 검색 API 에러 발생 시, 네이버 블로그 검색 API로 대체하여 검색합니다.
- 검색 결과는 페이지네이션으로 제공되며, 전체 검색 결과 수, 시작 인덱스, 검색 결과 개수를 응답해줍니다.
- 인기 검색어 TOP 10과 검색어별 검색 횟수를 제공합니다.

## 기술 스택

- Java 17
- Spring Boot 3.0.3
- Gradle
- H2 데이터베이스
- JPA
- Spring WebFlux(WebClient 사용을 위해 추가)
- Kotest(테스트 프레임워크)
- MockK(코틀린 mock 라이브러리)

## 실행방법(사용방법)
- jar 파일 다운로드 링크입니다 [링크](https://github.com/Sung-ho94/blog-search-app/raw/master/blog-app.jar)
- 다운로드 받은 `blog-app.jar` 파일을 아래의 명령어로 실행합니다.

```
java -jar blog-app.jar
```

## 프로젝트 구조 설명

해당 프로젝트는 총 5개의 모듈로 이루어져 있습니다.

### blog-app-domain

- 순수 도메인 객체, 레포지토리의 인터페이스, 도메인 로직을 가지고 있는 모듈입니다
- 테스트를 위한 최소한의 라이브러리를 가지며, 순수한 코틀린으로 작성하였습니다

- blog-app-service
- blog-app-domain에서 작성한 도메인을 사용하여 서비스 로직을 구현하였습니다
- 순수 코틀린으로 작성한것이 아닌 spring 라이브러리를 추가하였습니다
- blog-app-domain를 의존합니다

### blog-app-data

- blog-app-domain에서 작성한 레포지토리의 인터페이스를 구현한 모듈입니다
- jpa를 사용하여 구현하였습니다
- blog-app-domain를 의존합니다

### blog-app-external-api

- 외부 api(카카오, 네이버)와 통신하는 모듈입니다
- blog-app-domain를 의존합니다

### blog-app

- 스프링 부트 및 설정파일, 에러 핸들러를 가지고 있습니다
- blog-app-service
- blog-app-data
- blog-app-external-api
- blog-app-domain
- 4개의 모듈에 의존합니다

## 동시성 해결

```text
데이터베이스에 lock을 걸어 해결하는 방법과 조회 로그를 쌓아 스케줄을 돌리는 방법을 2가지를 고려하여 동시성 문제 해결을 진행했습니다. 명확한 비즈니스 요구사항이 없었으나, 정확한 시간에 검색 조회수를 구하는 것보다 정확한 데이터를 가지는 것이 더 중요하다고 판단했습니다.

데이터베이스에 lock을 걸어 해결하면 짧은 시간에 많은 요청이 들어올 때 에러가 발생하고, 처리 속도에 문제가 생길 수 있어 정확한 데이터를 확인하기 어렵습니다. 따라서 두 번째 방법인 조회 로그를 쌓아 스케줄을 사용하여 동시성 문제를 해결하는 방식을 선택했습니다.

```

## api 명세

### 블로그 조회

#### Request

``
GET /blog/search
``

| Parameters | Type   | Required | descriptions                                       |
|------------|--------|----------|----------------------------------------------------|
| query      | String | O        | 검색어                                                |
| sort       | String | X        | 정렬기준 accuracy(정확도순)/recency(최신순)<br/>기본값  accuracy |
| page       | Int    | X        | 페이지 (min:1, max:100)                               |
| size       | Int    | X        | 페이지당 결과 수 (min:1, max:100)                         |

- 카카오에서는 page,와 size가 최대 이므로 50이 넘어가면 네이버 블로그 검색 api로 요청

예시

```text
localhost:8080/blog/search?query=test&sort=accuracy&page=3&size=2
```

#### response

- meta

| Parameters | type | descriptions  
|------------|------|---------------|
| total      | int  | 총 검색 결과 개수    
| start      | int  | 현재 결과의 시작 인덱스 
| size       | int  | 페이지의 검색 결과 개수 

- document

| Parameters | type   | descriptions  |
|------------|--------|---------------|
| title      | String | 블로그명          |
| contents   | String | 현재 결과의 시작 인덱스 | 
| url        | String | 블로그링크         |
| blogname   | String | 블로그명          |
| datetime   | String | 작성일           |

```json
{
  "meta": {
    "total": 800,
    "start": 5,
    "size": 2
  },
  "documents": [
    {
      "title": "NestJS — <b>Test</b> Driven Development (2)",
      "contents": "NestJS — <b>Test</b> Driven Development (1) 이전에 쓰던 To Do List를 폐기하고, NestJS MVC 환경에서 TDD를 수행하는 법을 작성하려 한다. 크게 Unit <b>Test</b>와 Integration <b>Test</b>로 나누어서 연재할 예정이다. 흔히 서비스의 프론트엔드에서 발생하는 요청 dev-whoan.xyz 계속해서 User 정보를 관리하는 API 서버를 설계하고...",
      "url": "http://dev-whoan.xyz/104",
      "blogname": "짧은머리 개발자",
      "datetime": "2023-03-19"
    },
    ...
  ]
}
```

### 인기 검색어 조회

```text
GET /popular-keyword
```

- 예시
  ```localhost:8080/popular-keyword```

#### response

| Parameters | type   | descriptions 
|------------|--------|--------------|
| keyword    | String | 검색어          
| hitCount   | int    | 검색 횟수        

```json
[
  {
    "keyword": "test",
    "hitCount": "12.00"
  },
  {
    "keyword": "test1",
    "hitCount": "7.00"
  }
]
```