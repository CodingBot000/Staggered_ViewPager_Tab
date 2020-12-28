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

* WorkManager
15분에 한번씩 API call을 해서 이전 데이터와 새로가져온 데이터가 다른지 검사해서
다를 경우 notification에 새로운 데이터가 나타났다고 알림을 보여줌.


StaggerdGridLayout
읽어온 이미지들의 사이즈가 제각각이라 StaggeredGrid에 로딩하는데 사이즈를 모두 연산하느라 크기가 마구 변하는 현상을 방지하기 위한 
API 요청으로 받아온 이미지 Width, Height로 먼저 ImageView의 사이즈를 고정하여 안정적 로딩하도록 하였습니다.

![Alt text](/../<branch name>/path/to/image.png?raw=true "Optional Title")
[전체 구조도 요약]


//![GiphyTestApp](https://user-images.githubusercontent.com/75989725/102716120-204d9500-431d-11eb-9e1d-732343f5ca72.jpg)
