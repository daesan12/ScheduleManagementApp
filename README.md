﻿# Schedule-Management-App
# 일정 관리 API 문서

일정을 생성, 조회, 수정, 삭제할 수 있는 API입니다.

## API 사양

### 기본 URL

---

### 1. 일정 생성

- **URL**: `/schedules`
- **Method**: `POST`
- **Body**:
    ```json
    {
        "id": "1",
        "userId": "qwe123",
        "scheduleDate": "2024-10-29 18:30:42",
        "work": "회식"
    }
    ```

- **응답**
  - 성공 시 (상태 코드 200):
      ```json
      {
          "code": "200",
          "message": "등록완료"
      }
      ```
  - 실패 시 (상태 코드 400):
      ```json
      {
          "code": "400",
          "message": "잘못된 요청입니다."
      }
      ```

---

### 2. 일정 단건 조회

- **URL**: `/schedules/{scheduleId}`
- **Method**: `GET`
- **Params**: `id`

- **응답**
  - 성공 시 (상태 코드 200):
      ```json
      {
          "id": "1",
          "userId": "qwe123",
          "scheduleDate": "2024-10-29 20:30:41",
          "work": "회식"
      }
      ```
  - 실패 시 (상태 코드 400):
      ```json
      {
          "code": "400",
          "message": "잘못된 요청입니다."
      }
      ```

---

### 3. 일정 전체 목록 조회

- **URL**: `/schedules/?revdate=2024-10-29&username=홍길동`
- **Method**: `GET`

- **응답**
  - 성공 시 (상태 코드 200):
      ```json
      {
          "schedules": [
              {
                  "id": "1",
                  "userId": "qwe123",
                  "scheduleDate": "2024-10-29 20:30:41",
                  "work": "회식"
              },
              {
                  "id": "2",
                  "userId": "qwe321",
                  "scheduleDate": "2024-11-29 20:30:43",
                  "work": "외식"
              },
              {
                  "id": "3",
                  "userId": "ewq321",
                  "scheduleDate": "2024-12-29 20:30:49",
                  "work": "금식"
              }
          ]
      }
      ```
  - 실패 시 (상태 코드 400):
      ```json
      {
          "code": "400",
          "message": "잘못된 요청입니다."
      }
      ```

---

### 4. 일정 수정

- **URL**: `/schedules/{id}`
- **Method**: `PUT`
- **Body**:
    ```json
    {
        "pwd": 123,
        "id": "4",
        "userId": "eqw231",
        "work": "약속"
    }
    ```

- **응답**
  - 성공 시 (상태 코드 200):
      ```json
      {
          "message": "수정완료"
      }
      ```
  - 실패 시
    - 400 (잘못된 요청):
        ```json
        {
            "code": "400",
            "message": "잘못된 요청입니다."
        }
        ```
    - 404 (존재하지 않는 ID):
        ```json
        {
            "code": "404",
            "message": "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요."
        }
        ```

---

### 5. 일정 삭제

- **URL**: `/schedules/{id}`
- **Method**: `DELETE`
- **Body**:
    ```json
    {
        "pwd": 123,
        "id": "4"
    }
    ```

- **응답**
  - 성공 시 (상태 코드 200):
      ```json
      {
          "message": "삭제완료"
      }
      ```
  - 실패 시
    - 400 (잘못된 요청):
        ```json
        {
            "code": "400",
            "message": "잘못된 요청입니다."
        }
        ```
    - 404 (존재하지 않는 ID):
        ```json
        {
            "code": "404",
            "message": "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요."
        }
        ```

---

## 데이터베이스 테이블 생성 쿼리

### 일정 테이블
```
sqlCREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 일정을 구분하는 아이디
    password VARCHAR(30),
    work VARCHAR(30),
    user_id VARCHAR(10),
    user_name VARCHAR(10) NOT NULL,
    schedules_date DATE, -- 일정 날짜
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- 작성일
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 마지막 수정일, 자동 입력
);
```

### 유저 테이블
```
sCREATE TABLE userinfo (
    user_id VARCHAR(10) PRIMARY KEY,
    email VARCHAR(50),
    create_date DATE DEFAULT CURRENT_DATE,
    user_name VARCHAR(10) NOT NULL
);
```
