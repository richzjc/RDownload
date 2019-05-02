# RDownload
针对公司的项目的离线下载抽成了一个组件， 支持图片和音视频同时混合下载

### rx包下面的eventbus详解

    手撸eventBus类其目的主要是用于解藕，降低项目的偶合度。其里面添加了注册和注销的方法，
    同时将调用注册方法的当前类，对于添加了ProgressSubscribe, SizeChangeSubscribe注解的方法，
    保存起来，以供后面，当数据有更新时，可以通过反射去调用该方法。