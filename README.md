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

### 接下来看一下具体是如何使用的

     val configration = Confirguration.ConfirgurationBuilder()
                .setNetWorkType(NetworkType.WIFI)
                .setConfigurationKey("test")
                .build(applicationContext)
     RDownloadManager.getInstance().setConfiguration(configration)
     
     RDownloadManager 作为整个下载的管理类， 在下载之前必须要初始化Configuration,
     由于我项目里面文章的下载和音视频下载是不能放在一起下载的，所以每一个configuration必须有一个对应的configurationKey,
     并且要保证唯一。
     
---


    
