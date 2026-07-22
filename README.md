# Meerkatgram

사용자가 이미지 게시글을 올리고 소통하는 **커뮤니티형 웹 애플리케이션**입니다.  
백엔드(Spring Boot 3)와 프론트엔드(Vue 3)는 분리된 프로젝트로 관리하며 HTTP API로 통신합니다.

---

## 화면 디자인

![Design](https://github.com/user-attachments/assets/36813f6a-a155-4ffe-a341-8f4413fa4520)

---

## ERD

![ERD](https://github.com/user-attachments/assets/4ae34858-7936-415a-9081-d7b3435343d3)

---

## 기술 스택

| 영역 | 기술 |
|------|------|
| Language | Java 17 |
| Backend | Spring Boot 3.5, Spring Security, MyBatis 3.0.5 |
| Database | MySQL 8.4 |
| Auth | JWT (Access / Refresh Token) |
| Frontend | Vue 3 (Composition API), Pinia, Vue Router 4, Axios, Vite |
| Build | Gradle |

---

## 주요 기능

| 분류 | 기능 |
|------|------|
| 인증 | 회원가입, 로그인, 로그아웃, Access Token 재발급 |
| 유저 | 유저 정보 조회 |
| 게시글 | 목록(페이지네이션), 상세 조회, 작성, 삭제 |
| 파일 | 게시글 이미지 업로드, 프로필 이미지 업로드 |

---

## 상세 문서

| 문서 | 설명 |
|------|------|
| [프로젝트 개요](./meerkatgram-doc/1st-doc/01-project-overview.md) | 기능 목록, 기술 스택, 요청 처리 흐름 |
| [ERD & 데이터베이스](./meerkatgram-doc/1st-doc/02-erd-and-database.md) | 테이블 스키마, 관계, 소프트 삭제 패턴 |
| [백엔드 아키텍처](./meerkatgram-doc/1st-doc/03-backend-architecture.md) | 레이어 구조, 패키지 설계 |
| [API 명세](./meerkatgram-doc/1st-doc/04-api-specification.md) | 전체 엔드포인트, 요청/응답 예시 |
| [JWT 인증 가이드](./meerkatgram-doc/1st-doc/05-auth-jwt-guide.md) | 토큰 발급·갱신·무효화 흐름 |
| [주요 기능 가이드](./meerkatgram-doc/1st-doc/06-key-features-guide.md) | 게시글, 파일 업로드 등 구현 상세 |
| [개발 환경 세팅](./meerkatgram-doc/1st-doc/07-setup-guide.md) | 로컬 실행 방법 |

---

## 실행 방법

### 요구 환경

- Java 17
- MySQL 8.4
- Gradle Wrapper

### 1. DB와 설정 준비

```sql
CREATE DATABASE meerkatgram
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;
```

`src/main/resources/schema/v000`의 SQL을 순서대로 실행합니다. 이후 `src/main/resources/application.yaml`에 DB 접속 정보, JWT Secret, CORS Origin과 파일 저장 경로를 설정합니다.

### 2. 서버 실행

```powershell
.\gradlew.bat build
.\gradlew.bat bootRun
```

기본 API 주소는 `http://localhost:8080`입니다. Vue 클라이언트는 [msa4-meerkatgram-client-main](https://github.com/jeon950523/msa4-meerkatgram-client-main)을 별도로 실행합니다.

## 사용 방법

1. 프로필 이미지를 업로드하고 반환 URL로 회원가입합니다.
2. 로그인한 뒤 Access Token으로 인증 API를 호출합니다.
3. 게시글 이미지를 먼저 업로드하고 게시글 내용과 이미지 URL을 저장합니다.
4. 전체 게시글, 상세 게시글과 내 게시글을 조회합니다.
5. 본인 게시글을 삭제하면 실제 행 대신 `deleted_at`이 기록됩니다.
6. Access Token 만료 시 Refresh Token 쿠키로 재발급하고, 로그아웃 시 DB와 쿠키의 토큰을 제거합니다.

## 커밋 이력

| 순서 | 커밋 | 이전 단계에서 변경한 내용 |
| ---: | --- | --- |
| 1 | `8756c3b` 초기 등록 | 백엔드 전체 소스와 문서를 처음 등록했습니다. |
| 2 | `9110d88` 파일 폴더 이동 | 기존 파일을 새 디렉터리로 옮기기 위해 대규모 삭제·이동을 수행했습니다. |
| 3 | `3e3e70d` 폴더 이동 보완 | 이전 이동에서 빠진 파일을 다시 배치해 실행 가능한 구조로 복구했습니다. |
| 4 | `f73e024` 작성·삭제 | 게시글 작성과 작성자 조건 기반 소프트 삭제를 추가했습니다. |
| 5 | `6e02349` 내 글·이메일 | 내 게시글 조회와 사용자 이메일 변경 기능을 추가했습니다. |
| 6 | `65b34fc` 기능 PR 병합 | post-store 기능 브랜치를 병합했습니다. |
| 7 | `994b380` 개발 PR 병합 | 개발 브랜치의 통합 결과를 기본 브랜치에 반영했습니다. |
| 8 | `73f3e4e` 기능 파일 추가 | 내 글 조회·이메일 변경 관련 산출물과 파일을 다시 추가했습니다. |
| 9 | `df66eb0` 중복 구조 정리 | 앞 단계에서 추가된 대규모 파일을 정리하고 필요한 파일 중심으로 재배치했습니다. |
| 10 | `bc0aac5` post-store 병합 | 정리한 게시글 Store 관련 변경을 병합했습니다. |
| 11 | `b98ba9f` 전체 파일 재등록 | 백엔드 전체본을 다시 반영해 현재 저장소 상태를 만들었습니다. |

### 첫 번째 커밋에서 두 번째 커밋으로

기능을 추가한 것이 아니라 루트에 있던 전체 소스를 하위 폴더 구조로 이동했습니다. 세 번째 커밋에서 이동 과정의 누락을 보완했기 때문에 1~3번은 하나의 저장소 구조 정리 작업으로 보는 것이 정확합니다.

## 성능 개선 기록

커밋 이력은 기능 구현과 파일 이동 중심이며 성능 벤치마크가 없습니다. 페이지네이션과 소프트 삭제 조건은 데이터 처리 범위를 제한하지만, 측정 자료가 없어 응답 시간이 몇 퍼센트 개선됐다고 표현하지 않습니다.

> 이 저장소는 동일 파일이 중첩된 이력이 있어, 향후 대표 백엔드 저장소 하나로 통합하고 기능 단위 커밋을 유지하는 편이 이력 가독성에 유리합니다.
