# Android14系统Settings应用修改为Gradle配置  
Android14 FrameWork中的Settings应用，默认是使用android.bp脚本实现编译的，一般情况下需要在Ubtuntu环境下执行编译，对其进行开发则需要专门的ASFP(Android Studio For Platform)进行开发，限制条件较多，而如果将项目的编译脚本修改为Gradle方式，则可以像开发普通App一样开发系统的Settings应用。

## 如何使用  
1. 先下载对应的[android.jar](http://bj.xiaoyan159.space:5000/sharing/SXNhAxbN1)，替换本地SDK中platform文件夹下Android34的同名文件

该jar包融合了编译Android14源码后生成的framework.jar文件的内容，因为系统应用需要调用系统隐藏接口，或者引用系统的私有资源，这些数据都是预制在Android系统中的framwork.jar中，开发时需要使用这些接口，网上很多方案提到使用修改bootstrap的参数，让程序编译时优先采用Framework.jar的接口，但是在JDK11之后，已经不支持使用此方案直接修改编译时jar包的加载顺序。如果降低编译的JDK版本为JDK8，又会发现很多代码使用的JDK11以后的语法特性，需要对代码做部分修改，因此在Android14上直接替换android.jar的方案会更方便。  

2. clone本仓库后，使用Android Studio导入该项目即可