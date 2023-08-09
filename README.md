# Instargram- 구현

## 소개
- Springbbot, Jpa, QueryDSL을 학습하면서 진행

## 기간
- 2023.06.17 ~ 2023.07.27
### 데모

<details>
<summary><b>펼쳐 보기</b></summary>
<div markdown="1">

|회원가입|로그인|
|:--:|:--:|
|<img src="https://github.com/industry1111/Instargram-ORM/assets/98158673/af5b3195-ade0-45e7-823d-75297ecffde6" width="370" height="300">|<img src="https://github.com/industry1111/Instargram-ORM/assets/98158673/4cf29afd-af0e-41a7-a530-c4935a06fc7e" width="370" height="300">|

|게시물 더보기 및 등록|게시물 삭제|
|:--:|:--:|
|<img src="https://github.com/industry1111/Instargram-ORM/assets/98158673/38845e2b-078c-4cac-9dc5-83492e08e92c" width="370" height="300">|<img src="https://github.com/industry1111/Instargram-ORM/assets/98158673/d1cdabac-1ad5-4c67-a699-75de20b54261" width="370" height="300">|

|프로필 화면 및 수정|댓글 등록|
|:--:|:--:|
|<img src="https://github.com/industry1111/Instargram-ORM/assets/98158673/41865184-f55a-4438-bf85-1a24f92ef518" width="370" height="300">|<img src="https://github.com/industry1111/Instargram-ORM/assets/98158673/75444d96-2783-4a4b-a05b-064d16b0ec42" width="370" height="300">|

|팔로잉 게시글|팔로워 팔로잉 목록|
|:--:|:--:|
|<img src="https://github.com/industry1111/Instargram-ORM/assets/98158673/edb27a45-35f7-49b9-bd84-719236ce9b36" width="370" height="300">|<img src="https://github.com/industry1111/Instargram-ORM/assets/98158673/ebd2058c-50c2-4fc2-b1dd-7020aa64926d" width="370" height="300">|

</div>
</details>

## 사용기술
#### 📚 Backend

<div> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> 
  <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white">
  <img src="https://img.shields.io/badge/QueryDSL-007396?style=for-the-badge&logo=java&logoColor=white"> 
</div>

#### 📚 Frontend
<div>
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
</div>

#### 📚 DevOps
<div>
  <img src="https://img.shields.io/badge/oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white">
  <img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
  <img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
  <img src="https://img.shields.io/badge/travisci-3EAAAF?style=for-the-badge&logo=travisci&logoColor=white">
  <img src="https://img.shields.io/badge/code deploy-6DB33F?style=for-the-badge&logo=java&logoColor=white"> 
</div>

----

## ERD 설계

<img width="811" alt="스크린샷 2023-07-15 오후 4 26 38" src="https://github.com/industry1111/Instargram-ORM/assets/98158673/e9aceb87-25bb-4fc4-ba92-a66eda63f31e">

## Travis CI + CodeDeploy
<img width="811" alt="스크린샷 2023-07-15 오후 4 26 38" src="https://github.com/industry1111/Instargram-ORM/assets/98158673/3d5ca621-8ac6-4471-85f6-c3c21278fda8">


## 기능목록

- Member
  - 회원가입
  - 로그인
  - 프로필 정보 조회
  - 프로필 업데이트
- Post
  - 게시물 등록
  - 게시물 조회
  - 게시물 목록 조회
  - 게시물 삭제
- Comment
  - 댓글 등록
  - 댓글 목록 조회
- Follow
  - 팔로우
  - 언팔로우
- Like

## API
[API DOC](https://documenter.getpostman.com/view/28541536/2s946fcsGD)

<img width="811" alt="스크린샷 2023-07-15 오후 4 26 38" src="https://github.com/industry1111/Instargram-ORM/assets/98158673/63e9a95d-0068-4aa5-8edf-39adfec78098">

## 이슈
[aws서버에서 build 시 `Attemp recreate ...Q* ` 에러 발생](https://github.com/industry1111/Instargram-ORM/issues/1)

[AWS gradle 멈춤현상](https://github.com/industry1111/Instargram-ORM/issues/2)
