- **사용 스택** : Android(Java), Arduino(BLE 가속도/자이로 센서), Bluetooth Connection, AWS EC2(Ubuntu), MySQL, Node.js, Ajax, Github
- **국제 협력 팀 프로젝트**
    - **1학기** : 한국 학생 4명 (기획 및 개발)
    - **2학기** : 한국 학생 3명 (개발) + 네덜란드 학생 5명 (피드백)
- **개발 기간** : 2020년 3월 29일 ~ 12월 13일
- **Github**
    - 안드로이드 : [https://github.com/JasonYoo1995/Posture-Correction-Application-](https://github.com/JasonYoo1995/Posture-Correction-Application-)
    - 백엔드 : [https://github.com/JasonYoo1995/Posture-Correction-Application-Back-end-](https://github.com/JasonYoo1995/Posture-Correction-Application-Back-end-)
    - 아두이노 : [https://github.com/JasonYoo1995/Posture-Correction-Application-Arduino](https://github.com/JasonYoo1995/Posture-Correction-Application-Arduino)
- **시연 영상**
    - 제안서 및 간단한 시연 (영어 발표) : [https://youtu.be/auDByXR1yKo](https://youtu.be/auDByXR1yKo)
    - 최종 완성 제품 시연 : [https://youtu.be/BStMUy58ykQ](https://youtu.be/BStMUy58ykQ)
- **프로젝트 내용 요약**
    - 제품 기능
        - 몸 위에 센서를 고정시키고 블루투스를 통해 실시간으로 전송된 좌우 각도와 전후 각도 데이터를 서버 내 DB에 축적시키며, 이를 안드로이드 앱 상에 가공 및 시각화합니다.
        - 이 외에 자세 데이터를 가지고 만들어낼 수 있는 부가적인 서비스들을 구현합니다.
    - 기획 단계
        - 아이디어 제안
        - 시장 규모 및 전망 조사
        - 경쟁 제품 조사 및 벤치 마킹
        - 제품 세부 설계 및 프로토타입 제작
        - UI 및 인터랙션 디자인 설계
        - 페르소나 작성 및 타겟 고객 인터뷰
    - 구현 단계
        - 3D 프린팅을 통해 맞춤 제작된 케이스에 센서 내장
        - 안드로이드와 아두이노 간에 블루투스 연결
        - 자세 데이터 압축 및 전송 방식 결정
        - 회원 가입, 로그인, 자세 기록 측정 및 조회, 친구 맺기 및 친구와의 기록 비교 기능 구현
        - 네덜란드 Fontys, Windsheim 대학 학생들로부터 프로토타입에 대한 피드백 수용 및 반영
        → 코로나19로 인해 온라인 Meeting 진행
        → Joint Team Meeting, Joint Class에서 실시간 영어 회의 및 영어 발표
- **제작 문서**
    
    [자세 교정 앱 문서](https://www.notion.so/42b5c21c6af0473790978be3eca71ffb)
    
- **어려웠던 점**
    - 블루투스를 통해 SW와 HW를 연결하는 샘플 코드를 우리 앱에 맞게 수정해서 구동시키는 것이 굉장히 어려웠습니다.
    - 안드로이드의 메뉴 틀 구성에서 '하단 메뉴'만 적용하거나, '사이드 메뉴'만 적용하는 것은 쉬운데, '하단 메뉴'와 '사이드 메뉴'를 동시에 적용하는 것이 굉장히 어려웠습니다.
- **느낀 점**
    - 주요 회의 내용에 대한 사전 준비만 철저히 하면, 저도 외국인과 실시간으로 영어 회의를 할 수 있다는 걸 경험해서 뿌듯했습니다.
    - 팀원 간의 소통은 제때 제때 해야한다는 교훈을 얻었습니다.
    - 센서를 통해 실시간으로 누적되는 데이터를 1분/1시간/1일/1달/1년/10년 단위로 계산해보면서, 사용자 1인당 필요한 스토리지의 크기를 가늠할 수 있었고, 사용자가 훨씬 많아졌을 때는 더욱 대용량의 스토리지가 필요하다는 것을 느낄 수 있었습니다. 이에 따라 가공 등을 통해 필요한 데이터만 남길 필요성도 알게 되었고, 안정적이고 성능이 좋은 대용량 데이터의 처리 및 유지를 위해 DB에 대한 전문적인 설계/구현/관리 지식이 필수적이라는 점을 알게 되었습니다. 그리고 이를 위해 필요한 재정적 비용도 엄청나다는 걸 실감할 수 있었습니다.
