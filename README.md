# RDownload
针对公司的项目的离线下载抽成了一个组件库。该库包含的功能有，文件的下载，
对下载的进度以及下载的结果回调到主界面更新，通过sqlite的数据缓存。尽量做到和项目的解藕。
该库主要用到的是一些注解和反射的知识去实现的。

__该库包含了以下几个模块__

    data 模块（将所有的javabean放在了当前的目录下面）
    db 模块 （将缓存的功能放在当前的目录下面）
    download 模块 （主要放置的是下载的功能模块）
    manager 模块 （将一些单例的manager 放在了当前的目录）
    notification 模块 （主要将一些下载的进度，状态信息回调回去的代码放在了当前的module）
    util 模块 （主要旋转的是一些工具方法）

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
    
