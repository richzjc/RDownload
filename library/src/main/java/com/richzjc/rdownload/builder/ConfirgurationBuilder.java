package com.richzjc.rdownload.builder;

import com.richzjc.rdownload.config.Confirguration;
import com.richzjc.rdownload.constant.NetworkType;

public class ConfirgurationBuilder {

    private NetworkType networkType = NetworkType.WIFI;
    private String configurationKey;
   public ConfirgurationBuilder setNetWorkType(NetworkType workType){
       this.networkType = workType;
       return this;
   }

   public ConfirgurationBuilder setConfigurationKey(String configurationKey){
       this.configurationKey = configurationKey;
       return this;
   }


   public Confirguration build(){
       return new Confirguration(configurationKey, networkType);
   }
}
