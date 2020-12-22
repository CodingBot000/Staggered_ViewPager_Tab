# Staggered_ViewPager_Tab

Giphy Api를 활용한 Test Toy Project

- Library : 
Koin injection,
MVVM, DataBinding,
Retrofit, RxJava,
LiveData, 
Room DataBase

- Extends :
abstract class BaseActivity,
abstract class BaseFragment,
abstract class BaseViewModel

- Model Layer :
Repository,
DataSource

* ForegroundService 
앱을 내리면 바로 startForeground Service . 앱을 종료해도 구동됨. 20초에 한번씩 API call을 해서 이전 데이터와 새로가져온 데이터가 다른지 검사해서
다를 notification에 새로운 데이터가 나타났다고 알림을 보여주고 Service를 종료함.
다른 데이터를 포착하기전에 앱을 열어도 서비스 종료함.

WorkManager로 IDLE상태에서만 동작하도록 추가 예정

StaggerdGridLayout
읽어온 이미지들의 사이즈가 제각각이라 StaggeredGrid에 로딩하는데 사이즈를 모두 연산하느라 크기가 마구 변하는 현상을 방지하기 위한 
API 요청으로 받아온 이미지 Width, Height로 먼저 ImageView의 사이즈를 고정하여 안정적 로딩하도록 하였습니다.

[전체 구조도 요약]
![GiphyTestApp](https://user-images.githubusercontent.com/75989725/102716120-204d9500-431d-11eb-9e1d-732343f5ca72.jpg)
