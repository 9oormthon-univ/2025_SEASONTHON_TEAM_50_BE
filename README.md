# 2025_SEASONTHON_TEAM_50_BE
[2025 kakao X goorm 시즌톤] BE 레포지토리

🤝 협업 규칙 (Google Style 기반)

브랜치 전략
- main : 데모/배포용. 완성 기능만 머지
- dev : 통합 테스트용. 각 기능 브랜치를 먼저 머지
- feat/* : 기능 단위 브랜치. 작업 후 dev로 PR → 최종 main으로 머지
- fix/* , chore/* , docs/* : 버그 수정, 설정/잡무, 문서용
- 네이밍 규칙 : 소문자, 단어 구분은 - 사용 (예: feat/be-vendors-api)

⸻

커밋 규칙
형식 : type: subject
- feat: 새로운 기능
- fix: 버그 수정
- docs: 문서 관련
- style: 포맷/세미콜론 등 비즈니스 로직 변화 없는 변경
- refactor: 리팩토링
- test: 테스트 관련
- chore: 빌드, 환경설정, 패키지 매니저 등 잡무
