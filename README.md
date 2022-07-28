# 유튜브 댓글 분석 서비스

## 주제 선정 배경
2021년도 기준으로 국내에서 4천319만 명의 사람이 유튜브(YouTube)를 사용한다고 집계되었다. 이는 한국 전체 인구 80%를 웃돈다. 한국인이 한 달간 가장 많이 쓴 앱 2위, 한국인이 한 달간 가장 오래 쓴 앱 1위로 유튜브가 선정되기도 하였다. 이러한 수치로 미루어 보았을 때 유튜브의 인기가 엄청나다는 것을 알 수 있다.

최근에는 유튜브로 영상을 시청하는 사람뿐만 아니라 직접 유튜브에 영상을 업로드하여 수익을 창출하는 유튜버(Youtuber) 또한 증가하고 있는 추세이다. 아래 시각 자료는 수익을 창출하는 국내 유튜브 채널수를 그래프로 나타낸 것인데, 2019년에는 5만 6359만 개였는데 2020년에는 9만 7934만 개로 1년 새에 4만 1575개가 늘어났음을 알 수 있다. 또한 지난해 열린 ‘구글 포 코리아’행사에서 유튜브 최고 경영자(CEO) 수잔 워치 스키는 “지난해 유튜브는 한국 국내 총생산(GDP)에 약 1조 5천970억 원, 일자리 8만 6천30개를 창출하는 데 기여했다”라고 설명했다.

유튜브 수익(광고 수익)은 대략적인 계산 방법으로 8분 미만의 영상은 조회 수 1회당 1원, 8분 이상의 영상은 2\~3원으로 알려져 있다. 유튜버에게 있어서 조회 수는 곧 수익으로 직결되므로 조회 수를 높이는 것은 유튜버에게 있어서 매우 중요하다. 높은 조회 수를 얻기 위해서 유튜버가 유튜브 동영상 시청자들의 니즈(needs)와 관심도(interest)를 파악하는 것은 필수적이다. 유튜브 시청자의 니즈와 관심도를 알아내기 위한 도구로 영상에 달린 댓글은 가장 좋은 수단이다. 유튜버는 유튜브 댓글을 통해 영상에 대한 반응을 확인하여 유튜브 이용자들의 관심도, 여론 등을 파악할 수 있다. 하지만 유튜버가 영상의 댓글들을 일일이 확인해 영상의 반응을 확인하는 것은 어려움이 따른다. 댓글 수가 적으면 괜찮겠지만, 댓글 수가 100개~200개 이상이 되면 이를 일일이 읽어보는 것은 한계가 있다. 이러한 불편함을 해소하기 위해 유튜브 영상의 댓글들을 분석해 영상에 대한 반응, 관심도 등을 한눈에 볼 수 있도록 하는 서비스를 만들어보았다.

<br>


## 시스템 시나리오
![시스템 시나리오](https://user-images.githubusercontent.com/70521476/178017335-996e0549-9656-4cd8-8e37-37595bbffa71.png)

<br>

## 시스템 구성도
![설계도](https://user-images.githubusercontent.com/70521476/178016709-08b950eb-2e52-4e76-ba66-d745f6f3e978.png)

<br>

## 기술 스택
### Framework
<div> 
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
  <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black">
  <img src="https://img.shields.io/badge/flask-000000?style=for-the-badge&logo=flask&logoColor=white">
</div>

<br>

### Language
<div>
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white">   <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/html-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src ="https://img.shields.io/badge/thymeleaf-006400?&style=for-the-badge&logo=thymeleaf&logoColor=white"/>
</div>

<br>

### RDBMS
<div> 
  <img src="https://img.shields.io/badge/MySQL-4479A1.svg?&style=for-the-badge&logo=MySQL&logoColor=white">
</div>

<br>

### DevOps
<div> 
  <img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=Amazon%20AWS&logoColor=white">
  <img alt="Docker" src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
</div>

<br>

## 개발 계획

  <a href="https://tothemoon2022.notion.site/tothemoon2022/32c22d27ec51429791695834cf736fe0">
    <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white">
  </a>

<br>

## 필요 기술 및 참고 문헌

### 참고 서적

- (텐서플로2와 머신러닝으로 시작하는) 자연어 처리 : 로지틱스 회기부터 BERT와 GPT2까지
- (BERT와 GPT로 배우는) 자연어 처리 : 트랜스포머 핵심 원리와 허깅 페이스 패키지 활용법
- 백견불여일타 이젠 프로젝트다! 스프링부트 쇼핑몰 프로젝트 with JPA

### 논문 자료

- 변현진. (2018). 유튜브 콘텐츠의 제작·이용 환경 특성과 인기 채널 분석 및 함의점 고찰. 조형미디어학, 21(4), 227-239.
- 이한범, 구자환, 김응모. (2020). BERT를 활용한 문장 감정 분석 연구. 한국정보처리학회 학술대회논문집, 27(2), 909-911.

### 참고 기술

- [https://github.com/SKTBrain/KoBERT](https://github.com/SKTBrain/KoBERT)(KoBERT)

- [https://github.com/lovit/soynlp](https://github.com/lovit/soynlp)(soynlp)

- [https://developers.google.com/youtube/v3](https://developers.google.com/youtube/v3)(Youtube Data Api reference)

- [https://keras.io/](https://keras.io/)(Keras)




## 팀원 구성

한승수 hanseu96@naver.com

서규범 sgb8170@naver.com

김경지 ggk1104@gmail.com

강준혁 jh83370@naver.com
