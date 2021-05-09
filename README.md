# Staggered_ViewPager_Tab

Giphy Api를 활용한 Test Toy Project

Coding style 및 구조 잡는 것 정도만 확인할수 있는 수준정도의 가벼운 프로젝트입니다.

- Library : 
Koin injection,
MVVM, DataBinding,
Retrofit, RxJava,
LiveData, 
Room DataBase

- Extends :
abstract class BaseActivity,
abstract class BaseFragment,
abstract class BaseViewModel,
abstract class SingleUseCase

- Domain Layer : UseCase
UseCaseDbManager
UseCaseGetDetailData
UseCaseGetTrendingData
UseCaseGetSearchData
UseCaseGetGIFsByIds

- Model Layer :
Repository,
DataSource

- DI :
Koin



[Add description]
* DI :
좀 더 쉽게 쓸수있는 Koin 사용. Dagger로 마이그레이션해서 업데이트 예정

* UseCase :
UseCase로 분리해서 관림사를 더 세분화. 코드 중복 감소
DBUseCase CRUD에 따라 일관되게 사용되므로 하나의 UseCase로 묶어도 무방하다고 판단하여 하나로 통일
Api 관련 UseCase는 SingUseCase를 이용하여 즉시 disposable처리

* WorkManager
15분에 한번씩 API call을 해서 이전 데이터와 새로가져온 데이터가 다른지 검사해서
다를 경우 notification에 새로운 데이터가 나타났다고 알림을 보여줌.

* LoadMore
* 검색시 입력문자 변경에 따라 검색버튼 클리없이 실시간 체크해서 검색실행 (RxJava사용)

StaggerdGridLayout
읽어온 이미지들의 사이즈가 제각각이라 StaggeredGrid에 로딩하는데 사이즈를 모두 연산하느라 크기가 마구 변하는 현상을 방지하기 위한 
API 요청으로 받아온 이미지 Width, Height로 먼저 ImageView의 사이즈를 고정하여 안정적 로딩하도록 하였습니다.



[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/6Ur5dI7zeSo/0.jpg)](https://www.youtube.com/watch?v=6Ur5dI7zeSo)

https://youtu.be/6Ur5dI7zeSo
링크 누르시면 동영상 확인가능하십니다



[전체 구조도 요약]
![Alt text](https://github.com/CodingBot000/Staggered_ViewPager_Tab/blob/main/GiphyTestApp_v1.jpg?raw=true)

