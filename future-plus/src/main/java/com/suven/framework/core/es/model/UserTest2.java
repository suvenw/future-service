package com.suven.framework.core.es.model;

import com.suven.framework.core.es.EsIk;
import com.suven.framework.core.es.IEsMapping;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author dongxie
 * @Description: es 公共api代码例子  操作的实体对象
 * @CreateDate 2019-09-11  16:29
 **/
public class UserTest2 implements Serializable {

    private String id;
    private String userId;
    @EsIk
    private String name;
    @EsIk
    private String context;
    private int age;
    
    private Rpc rpc = new Rpc();



    public Rpc getRpc() {
        return rpc;
    }

    public void setRpc(Rpc rpc) {
        this.rpc = rpc;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }




    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public class  Rto implements IEsMapping {
        @EsIk(type = "date", format = "yyyy-MM-dd HH:mm:ss")
        private Date date;
        @EsIk
        private String title;
        @EsIk
        private String content;


        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


    }

    public class  Rpc implements IEsMapping {
        @EsIk
        private String user;
        @EsIk
        private String oauth ;
        private short resource  = 29030;
        private char media = 29180;
        private byte statistic = 1;

        private float assets  = 29050;
        private Integer video  = 29060;
        private long order  = 29070;
        private float pay  = 29080;
        private double config  = 29090;
        private int log  = 29150;
        private Rto rto = new Rto();

        public Rto getRto() {
            return rto;
        }

        public void setRto(Rto rto) {
            this.rto = rto;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getOauth() {
            return oauth;
        }

        public void setOauth(String oauth) {
            this.oauth = oauth;
        }

        public short getResource() {
            return resource;
        }

        public void setResource(short resource) {
            this.resource = resource;
        }

        public char getMedia() {
            return media;
        }

        public void setMedia(char media) {
            this.media = media;
        }

        public byte getStatistic() {
            return statistic;
        }

        public void setStatistic(byte statistic) {
            this.statistic = statistic;
        }

        public float getAssets() {
            return assets;
        }

        public void setAssets(float assets) {
            this.assets = assets;
        }

        public Integer getVideo() {
            return video;
        }

        public void setVideo(Integer video) {
            this.video = video;
        }

        public long getOrder() {
            return order;
        }

        public void setOrder(long order) {
            this.order = order;
        }

        public float getPay() {
            return pay;
        }

        public void setPay(float pay) {
            this.pay = pay;
        }

        public double getConfig() {
            return config;
        }

        public void setConfig(double config) {
            this.config = config;
        }

        public int getLog() {
            return log;
        }

        public void setLog(int log) {
            this.log = log;
        }
    }



}
