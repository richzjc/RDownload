# RDownload
针对公司的项目的离线下载抽成了一个组件， 支持图片和音视频混合下载

### rx包下面的eventbus详解

    手撸eventBus类其目的主要是用于解藕，降低项目的偶合度。其里面添加了注册和注销的方法，
    同时将调用注册方法的当前类，对于添加了ProgressSubscribe, SizeChangeSubscribe注解的方法，
    保存起来，以供后面，当数据有更新时，可以通过反射去调用该方法。
    
### task包详解

    task包下面的类是纯种池里面执行的任务单元。
    AudioVideoTask: 音视频下载的任务单元
    ImageTask: 图片下载的任务单元
 
### wraper包的详解

    wraper主要是一个包装库。对下载队列的增删改查，都在DataHandleWrapper类里面具体实现。
    
### builder包详解

    builder包，主要是运用构造者模式对configuration进行配置初始化
    
