# MVVMScaffoldKt
整体分为6个模块
- core-base：封装基础类，baseActivity、baseFragment、util等
- core-network:封装retrofit、网络相关类
- core-repository：存放repository
- core-database:存放数据库相关类
- core-model：存放业务Beans类
- app：业务实现模块

主要使用到的技术点、三方库有：Flow、Retrofit、Hilt、Room、Navigation、DataBinding、LiveData、Moshi等。

## 模块、依赖管理
使用buildSrc统一管理模块，各模块Android配置信息相同，均使用Configuration中所配置的。  
各模块依赖方面也采取了统一版本管理，各三方库的配置信息在gradle/libs.versions.toml中。

## core-base介绍
提供了BaseVmActivity、BaseVmDbActivity、BaseVmVbActivity可根据具体的情况选择使用。  
Notice：Vm = ViewModel、Vb = ViewBinding、Db = DataBinding

### BaseVmActivity
使用时传入ViewModel泛型即可，会自动创建ViewModel，内部使用mViewModel即可；  
内部还创建了UiLoadingChange的监听，在ViewModel中触发UiChange会调用showToast和showLoading。  

### BaseVmDbActivity、BaseVmVbActivity
都是继承自BaseVmActivity，会自动绑定DataBinding和ViewBinding。

另外也提供了BaseVmFragment、BaseVmVbFragment、BaseVmDbFragment功能类似。

## core-network介绍
封装retrofit、网络相关类。  
使用时需要在service/NetworkService类中声明接口方法，NetworkClient调用，
使用Hilt注入NetworkClient到相应的Repository中。    
另外添加了LogInterceptor，TAG = HttpLog方便调试。

## 其他
编写代码时应遵循以下原则：
- 通过网络、数据库返回的都是数据流，在repository中对数据进行相应的处理，最后流到ViewModel中。    
- ViewModel通过collect或asLiveData对流进行收集处理
- UI层通过监听LiveData对页面进行处理

全局事件可以用eventViewModel，该ViewModel是APP级别的；  
相同的还有appViewModel，里面存放全局信息。  
尽量少使用全局事件，不易维护，耦合严重。






