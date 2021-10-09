## [主营项目接口文档](http/http-index.md)


##  中间组件配置 ELK+logback搭建日志系统


#####  开始研究ES，当前ES的最新版本为5.6.3，版本5.0.1以上jdk为1.8,缓存服务器用的redis
#####  本篇会记录ES5.1.1 + Kibana -5.6.3 + es-head插件在Linux环境上安装操作步骤。
#####  首先，去Elastic官网 https://www.elastic.co/downloads  ，下载页有所有Elastic的产品，都是最新的版本（版本号还一致）。


 
## 一、elasticsearch安装，下载完成后，全部解压，使用unzip和tar -xvzf
 
#####   (一)、 首先尝试去启动 elasticsearch，到 elasticsearch 的bin目录下，执行./elasticseharch。如果遇到下面的错误：
    
#####   (二) 、说明是用root账户来启动的，因为ES是没有权限限制的,还可以接收用户的脚本，所有用root账户很不安全，需要新建一个账户来启动。
    
   ```
             命令执行如下
             # adduser es
             # passwd es123456
             
             用root 给新建用户赋予权限
             1、进入bin目录下执行chmod +x elasticsearch
             2、chown es.es -R /opt/elasticsearch-5.1.1
             3、切换到新建用户
             # su es
             4、修改vm.map 限制
             vi /etc/sysctl.conf vm.max_map_count=262144
             5、进入bin目录 执行./elasticsearch -d &
             然后就可以在命令行中用curl http://localhost:9200?pretty  看看输出
              此时，ES可以在本地访问，但是用浏览器远程的话，还是无法访问的，因为相应的端口没有打开

             
   ```                       
 
#####  (三)、 修改es配置文件，进到安装目录/config/elasticsearch.yml,将network.host设置为0.0.0.0并且将访问端口放开
    
  ```
     
        # ======================== Elasticsearch Configuration =========================
        
        # ---------------------------------- Cluster -----------------------------------
        `cluster.name: elk-es`
        # ------------------------------------ Node ------------------------------------
        
        node.name: node-1
        # ----------------------------------- Paths ------------------------------------
        path.data: /data/program/elk/es/data
        
        path.logs: /data/program/elk/es/logs
        #
        # ----------------------------------- Memory -----------------------------------
        #
        # Lock the memory on startup:
        #
        #bootstrap.memory_lock: true
        #
        # ---------------------------------- Network -----------------------------------
        network.host: 10.100.0.222
        http.port: 9200
        # --------------------------------- Discovery ----------------------------------

         接下来重启es 然后通过浏览器外部访问就可以了
  
  ```
 
##### (四)、启动执行ES存在问题，./elasticsearch 
    
  ```
  [1]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536] 意思是说你的进程不够用了
            解决方案： 切到root 用户：进入到security目录下的limits.conf；执行命令 vim /etc/security/limits.conf 在文件的末尾添加下面的参数值：
           * soft nofile 65536
           * hard nofile 131072
           * soft nproc 65536
           * hard nproc 65536
           前面的*符号必须带上，然后重新启动就可以了。执行完成后可以使用命令 ulimit -n 查看进程数      
  [2]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]  需要修改系统变量的最大值了 
  [3]:解决方案：切换到root用户修改配置sysctl.conf  增加配置值： vm.max_map_count=655360  执行命令 sysctl -p   这样就可以了，然后重新启动ES服务 就可以了
            
  ```

  
#####  (五)、 启动执行ES文件，进入到bin 目录下执行 ./elasticsearch 命令就可以了，执行 ./elasticesrarch -d 是后台运行   
        如果没有什么问题话，就可以安全生成了；然后执行curl 'http://自己配置的IP地址:9200/' 命令，就出现下面的结果

   
  ```
   {
         "name" : "node-1",
         "cluster_name" : "elk-es",
         "cluster_uuid" : "Q04zG6ESQjyjXvZtVrRysA",
         "version" : {
           "number" : "6.2.3",
           "build_hash" : "c59ff00",
           "build_date" : "2018-03-13T10:06:29.741383Z",
           "build_snapshot" : false,
           "lucene_version" : "7.2.1",
           "minimum_wire_compatibility_version" : "5.6.0",
           "minimum_index_compatibility_version" : "5.0.0"
         },
         "tagline" : "You Know, for Search"
       }

  
  ```       
  
#####  (六)、安装head 插件
 
 - 6.1 下载head安装包，下载地址：https://github.com/mobz/elasticsearch-head/archive/master.zip
             这是接从git 上下载下来 ，然后上传到虚拟机上的；由于head 插件不能放在elasticsearch-5.6.3 文件夹里，head 插件需要单独放，单独去执行；  
             所 以在elasticsearch-5.6.3 同级目录下解压了 head 插件；解压出来的文件名字，如图
             
```

  
         [root@redis-node1 elk]# wget https://github.com/mobz/elasticsearch-head/archive/master.zip
         --2018-07-10 11:55:41--  https://github.com/mobz/elasticsearch-head/archive/master.zip
         Resolving github.com (github.com)... 13.229.188.59, 52.74.223.119, 13.250.177.223
         Connecting to github.com (github.com)|13.229.188.59|:443... connected.
         HTTP request sent, awaiting response... 302 Found
         Location: https://codeload.github.com/mobz/elasticsearch-head/zip/master [following]
         --2018-07-10 11:55:42--  https://codeload.github.com/mobz/elasticsearch-head/zip/master
         Resolving codeload.github.com (codeload.github.com)... 54.251.140.56, 13.250.162.133, 13.229.189.0
         Connecting to codeload.github.com (codeload.github.com)|54.251.140.56|:443... connected.
         HTTP request sent, awaiting response... 200 OK
         Length: 921421 (900K) [application/zip]
         Saving to: ‘master.zip’
         
         100%[=====================================================================================>] 921,421      167KB/s   in 6.1s   
         
         2018-07-10 11:55:51 (147 KB/s) - ‘master.zip’ saved [921421/921421]

```
-  6.1.2 elasticsearch-head 解压和安装
     
```
            [root@redis-node1 es-head]# pwd
            /data/program/elk
            [root@redis-node1 elk]# unzip  master.zip -d ./
            [root@redis-node1 elk]# ll
            total 904
            drwxr-xr-x  6 root root   4096 Sep 15  2017 elasticsearch-head-master
            drwxr-xr-x  9 elk  elk     188 Jul 10 11:42 es
            drwxrwxr-x 13 elk  elk     260 Jul  6 12:47 kibana
            drwxr-xr-x 12 elk  elk     278 Jul 10 10:43 logstash
            -rw-r--r--  1 root root 921421 Jul 10 11:55 master.zip
           
            [root@redis-node1 elk]# mv elasticsearch-head-master es-head
            [root@redis-node1 elk]# ll
            total 904
            drwxr-xr-x  9 elk  elk     188 Jul 10 11:42 es
            drwxr-xr-x  6 root root   4096 Sep 15  2017 es-head
            drwxrwxr-x 13 elk  elk     260 Jul  6 12:47 kibana
            drwxr-xr-x 12 elk  elk     278 Jul 10 10:43 logstash
            -rw-r--r--  1 root root 921421 Jul 10 11:55 master.zip
            [root@redis-node1 es-head]# cd es-head
            [root@redis-node1 es-head]# pwd
            /data/program/elk/es-head
```
-  6.2 执行head  插件，需要node.js 的支持，所以，下面先安装一node.js
-  6.2.1 执行命令一：curl -sL https://rpm.nodesource.com/setup_8.x | bash -
   
    ```
              curl -sL https://rpm.nodesource.com/setup_8.x | bash -
             
             ## Installing the NodeSource Node.js 8.x LTS Carbon repo...
             ## Inspecting system...
             + rpm -q --whatprovides redhat-release || rpm -q --whatprovides centos-release || rpm -q --whatprovides cloudlinux-release || rpm -q --whatprovides sl-release
             + uname -m
             
             ## Confirming "el7-x86_64" is supported...
             
             + curl -sLf -o /dev/null 'https://rpm.nodesource.com/pub_8.x/el/7/x86_64/nodesource-release-el7-1.noarch.rpm'
             
             ## Downloading release setup RPM...
             
             + mktemp
             + curl -sL -o '/tmp/tmp.SgDKQBVM0p' 'https://rpm.nodesource.com/pub_8.x/el/7/x86_64/nodesource-release-el7-1.noarch.rpm'
             
             ## Installing release setup RPM...
             
             + rpm -i --nosignature --force '/tmp/tmp.SgDKQBVM0p'
             
             ## Cleaning up...
             
             + rm -f '/tmp/tmp.SgDKQBVM0p'
             
             ## Checking for existing installations...
             
             + rpm -qa 'node|npm' | grep -v nodesource
             
             ## Run `sudo yum install -y nodejs` to install Node.js 8.x LTS Carbon and npm.
             ## You may also need development tools to build native addons:
                  sudo yum install gcc-c++ make
             ## To install the Yarn package manager, run:
                  curl -sL https://dl.yarnpkg.com/rpm/yarn.repo | sudo tee /etc/yum.repos.d/yarn.repo
                  sudo yum install yarn
                  
    ```
         
-  6.2.2 命令二：yum install -y nodejs
      
 ```
            yum install -y nodejs
            Loaded plugins: fastestmirror
            base                                                                                                    | 3.6 kB  00:00:00     
            epel                                                                                                    | 3.2 kB  00:00:00     
            extras                                                                                                  | 3.4 kB  00:00:00     
            mysql-connectors-community                                                                              | 2.5 kB  00:00:00     
            mysql-tools-community                                                                                   | 2.5 kB  00:00:00     
            mysql57-community-dmr                                                                                   | 2.5 kB  00:00:00     
            nginx                                                                                                   | 2.9 kB  00:00:00     
            nodesource                                                                                              | 2.5 kB  00:00:00     
            updates                                                                                                 | 3.4 kB  00:00:00     
            (1/7): epel/x86_64/group_gz                                                                             |  88 kB  00:00:00     
            (2/7): epel/x86_64/updateinfo                                                                           | 926 kB  00:00:00     
            (3/7): epel/x86_64/primary                                                                              | 3.5 MB  00:00:00     
            (4/7): extras/7/x86_64/primary_db                                                                       | 150 kB  00:00:00     
            (5/7): updates/7/x86_64/primary_db                                                                      | 3.6 MB  00:00:00     
            (6/7): nodesource/x86_64/primary_db                                                                     |  37 kB  00:00:01     
            (7/7): nginx/x86_64/primary_db                                                                          |  35 kB  00:00:02     
            Determining fastest mirrors
             * base: mirrors.zju.edu.cn
             * extras: mirrors.aliyun.com
             * updates: mirrors.aliyun.com
            epel                                                                                                               12605/12605
            Resolving Dependencies
            --> Running transaction check
            ---> Package nodejs.x86_64 2:8.11.3-1nodesource will be installed
            --> Finished Dependency Resolution
            
            Dependencies Resolved
            
            ===============================================================================================================================
             Package                  Arch                     Version                                  Repository                    Size
            ===============================================================================================================================
            Installing:
             nodejs                   x86_64                   2:8.11.3-1nodesource                     nodesource                    17 M
            
            Transaction Summary
            ===============================================================================================================================
            Install  1 Package
            
            Total download size: 17 M
            Installed size: 51 M
            Downloading packages:
            nodejs-8.11.3-1nodesource.x86_64.rpm              8% [===                                    ]  38 kB/s | 1.4 MB  00:06:56 ETA 
            warning: /var/cache/yum/x86_64/7/nodesource/packages/nodejs-8.11.3-1nodesource.x86_64.rpm: Header V4 RSA/SHA256 Signature, key ID 34fa74dd: NOKEY
            Public key for nodejs-8.11.3-1nodesource.x86_64.rpm is not installed
            nodejs-8.11.3-1nodesource.x86_64.rpm                                                                    |  17 MB  00:07:52     
            Retrieving key from file:///etc/pki/rpm-gpg/NODESOURCE-GPG-SIGNING-KEY-EL
            Importing GPG key 0x34FA74DD:
             Userid     : "NodeSource <gpg-rpm@nodesource.com>"
             Fingerprint: 2e55 207a 95d9 944b 0cc9 3261 5ddb e8d4 34fa 74dd
             Package    : nodesource-release-el7-1.noarch (installed)
             From       : /etc/pki/rpm-gpg/NODESOURCE-GPG-SIGNING-KEY-EL
            Running transaction check
            Running transaction test
            Transaction test succeeded
            Running transaction
            Warning: RPMDB altered outside of yum.
              Installing : 2:nodejs-8.11.3-1nodesource.x86_64                                                                          1/1 
              Verifying  : 2:nodejs-8.11.3-1nodesource.x86_64                                                                          1/1 
            
            Installed:
              nodejs.x86_64 2:8.11.3-1nodesource                                                                                           
            
            Complete!         
```
      
- 6.2.3 OK，执行完成后，可以使用命令 node -v 验证是否安装成功，同时npm 也安装成功了；执行命令 npm -v 也是可以验证的。
      
```   
      [root@redis-node1 ~]# node -v 
      v8.11.3
      [root@redis-node1 ~]# npm -v
      5.6.0
      [root@redis-node1 ~]#     
```   

-  6.3 安装grunt ,由于head 插件的执行文件是有grunt 命令来执行的，所以这个命令必须安装
-  6.3.1  安装命令一：npm install grunt --save-dev
               命令二：npm install   
     
```
             
     [root@redis-node1 ~]# npm install grunt --save-dev
     npm WARN saveError ENOENT: no such file or directory, open '/root/package.json'
     npm notice created a lockfile as package-lock.json. You should commit this file.
     npm WARN enoent ENOENT: no such file or directory, open '/root/package.json'
     npm WARN root No description
     npm WARN root No repository field.
     npm WARN root No README data
     npm WARN root No license field.
     
     + grunt@1.0.3
     added 96 packages in 32.604s
     [root@redis-node1 ~]# npm install 
     npm WARN saveError ENOENT: no such file or directory, open '/root/package.json'
     npm WARN enoent ENOENT: no such file or directory, open '/root/package.json'
     npm WARN root No description
     npm WARN root No repository field.
     npm WARN root No README data
     npm WARN root No license field.
     
     up to date in 0.996s
     [root@redis-node1 ~]# 
                                       
```  
       
- 6.3.2 修改配置文件，cd 进入elasticsearch-head-master 文件夹下，执行命令vim Gruntfile.js文件：增加hostname属性，设置为*；如图：

```
  
connect: {
      server: {
              options: {
                      port: 9100,
                      `hostname:*`
                      base: '.',
                      keepalive: true
              }
      }
}
   
```
          
- 6.3.3  修改 vi _site/app.js 文件：修改head的连接地址:，如图所示：
    
```
    (function( app, i18n ) {
    
        var ui = app.ns("ui");
        var services = app.ns("services");
    
        app.App = ui.AbstractWidget.extend({
                defaults: {
                        base_uri: null
                },
                init: function(parent) {
                        this._super();
                        this.prefs = services.Preferences.instance();
                        this.base_uri = this.config.base_uri || this.prefs.get("app-base_uri") || **"http://10.100.0.222:9200"**;
                        if( this.base_uri.charAt( this.base_uri.length - 1 ) !== "/" ) {
                                // XHR request fails if the URL is not ending with a "/"
                                this.base_uri += "/";
                        }
   
```                                 

- 6.3.4  最后一个命令： grunt server &  执行完成后就OK了
- 6.3.5 涉及到的问题，在网页上无法正常访问；查看防火墙是否关闭
 - 6.3.5.1 执行命令service iptables status 查看状态 ；直接将防火墙关闭就好了 执行命令service iptables stop
          最后执行的结果是这样的，我没有配置集群： 注意下面使用的端口号，不在是9200 了 而是head 插件中的 9100 了
          看到上面的出现的健康值了吗，说明的连接还是有问题的，解决方案是修改 cd 命令进入到elasticsearch-5.6.3 /config 文件中 vi elasticsearch.yml
         文件下添加 ：
         
```
       http.cors.enabled: true
       http.cors.allow-origin: "*"
       
```

- 6.4 然后重新执行ES  ./elasticsearch 成功起来就可以了，执行结果就是这样的


- 7.  中文分词IK安装   https://github.com/medcl/elasticsearch-analysis-ik  

  - 7.1 下载IK 安装包 - download pre-build package from here: https://github.com/medcl/elasticsearch-analysis-ik/releases

  - 7.2 在es根目录下/plugins 的目录下创建ik ; create plugin folder cd your-es-root/plugins/ && mkdir ik

 - 7.3 解压 IK安装包到IK目录下 unzip plugin to folder your-es-root/plugins/ik
 
 - 7.4 然后重新执行ES  ./elasticsearch 成功起来就可以了，执行结果就是这样的
 
 - 7.5 ES中文件用例,创建表索引,创建表结构,导入数据,搜索结果
 
 ```
 1.创建表索引create a index
 
 curl -XPUT http://localhost:9200/index
 
 2.创建表结构create a mapping
 
 curl -XPOST http://localhost:9200/index/_mapping -H 'Content-Type:application/json' -d'
 {
         "properties": {
             "content": {
                 "type": "text",
                 "analyzer": "ik_max_word",
                 "search_analyzer": "ik_smart"
             }
         }
 
 }'
 3.导入数据index some docs
 
 curl -XPOST http://localhost:9200/index/_create/1 -H 'Content-Type:application/json' -d'
 {"content":"美国留给伊拉克的是个烂摊子吗"}
 '
 curl -XPOST http://localhost:9200/index/_create/2 -H 'Content-Type:application/json' -d'
 {"content":"公安部：各地校车将享最高路权"}
 '
 curl -XPOST http://localhost:9200/index/_create/3 -H 'Content-Type:application/json' -d'
 {"content":"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"}
 '
 curl -XPOST http://localhost:9200/index/_create/4 -H 'Content-Type:application/json' -d'
 {"content":"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"}
 '
 4.搜索结果query with highlighting
 
 curl -XPOST http://localhost:9200/index/_search  -H 'Content-Type:application/json' -d'
 {
     "query" : { "match" : { "content" : "中国" }},
     "highlight" : {
         "pre_tags" : ["<tag1>", "<tag2>"],
         "post_tags" : ["</tag1>", "</tag2>"],
         "fields" : {
             "content" : {}
         }
     }
 }
 '
 Result
 
 {
     "took": 14,
     "timed_out": false,
     "_shards": {
         "total": 5,
         "successful": 5,
         "failed": 0
     },
     "hits": {
         "total": 2,
         "max_score": 2,
         "hits": [
             {
                 "_index": "index",
                 "_type": "fulltext",
                 "_id": "4",
                 "_score": 2,
                 "_source": {
                     "content": "中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"
                 },
                 "highlight": {
                     "content": [
                         "<tag1>中国</tag1>驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首 "
                     ]
                 }
             },
             {
                 "_index": "index",
                 "_type": "fulltext",
                 "_id": "3",
                 "_score": 2,
                 "_source": {
                     "content": "中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"
                 },
                 "highlight": {
                     "content": [
                         "均每天扣1艘<tag1>中国</tag1>渔船 "
                     ]
                 }
             }
         ]
     }
 }
 
 ```


    
  
   

##  二、 第二步现在我们来安装 logstash、和相关环境配置
#### （一）、下载 logstash 安装包 logstash-6.2.3.zip
  
  ```
        wget https://artifacts.elastic.co/downloads/logstash/logstash-6.3.1.tar.gz
        tar -xzf logstash-6.3.1.tar.gz
        mv logstash-6.3.1-linux-x86_64 logstash
        ch logstash
   
   ```

####  （二）、在logback程序config 增加配置文件logback-es.config,内容如下 ；

```
  input {
    tcp {  
    ##host:port就是上面appender中的 destination，这里其实把logstash作为服务，开启9601端口接收logback发出的消息  
        host => "100.10.0.222"  
        port => 9601  
    #模式选择为server  
        mode => "server"  
        tags => ["logback_trace_id"]  
        traceId => [" logback_trace_id"]


    ##格式json  
        codec => json_lines         
    }  

} 

output {
        elasticsearch {
        #ES地址
                hosts => "100.10.0.222:9200"
        #指定索引名字，不适用默认的，用来区分各个项目
                index => "%{[serverName]}-%{+YYYY.MM.dd}"
        }
        stdout { codec => rubydebug}
}

```

#### （三）、在logback程序创建启动脚本命令 touch start.sh ; chmod 744 start.sh

```
    #! /bin/sh
    
    APP_PATH=/data/program/elk/logstash
    
    nohup sh bin/logstash -f $APP_PATH/config/logstash-es.conf > $APP_PATH/logs/logstash-log.log 2>&1 &
    
    echo logstash run

```

#### （四）、在运用程序的配置文件（logback.xml） 增加如下信息：
 -   (1)、gc.server.project.name 为项目名称，在application.properties配置；
 -   (2)、gc.server.ip.port 为Logstash 提供的TcpSocket的服务ip和端口
 
```
    <include resource="org/springframework/boot/logging/logback/defaults.xml" />
    <property resource="application.properties" />
   <!--输出到elk的LOGSTASH-->
    <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>${gc.server.ip.port}</destination>
        <encoder charset="UTF-8" class="net.logstash.logback.encoder.LogstashEncoder" >
            <customFields>{"serverName":"${gc.server.project.name}"}</customFields>
        </encoder>
    </appender>
```


## 三、  第三步现在我们来安装 kibana 程序软件安装和配置

##### （1）、下载kibana 安装包 kibana-6.2.3-linux-x86_64.tar.gz
  
  ```
        wget https://artifacts.elastic.co/downloads/kibana/kibana-6.3.1-linux-x86_64.tar.gz
        shasum -a 512 kibana-6.3.1-linux-x86_64.tar.gz 
        tar -xzf kibana-6.3.1-linux-x86_64.tar.gz
        cd kibana-6.3.1-linux-x86_64/ 
   
   ```
##### （2）、解压和安装,和配置
   
   ```
         [elk@redis-node1 program]$ tar -xvf kibana-6.2.3-linux-x86_64.tar.gz
         [elk@redis-node1 program]$ mv kibana-6.2.3-linux-x86_64 kibana

   ```
##### （3）、项目配置 config/kibana.yml
   
   ```
        [elk@redis-node1 program]$ vi config/kibana.yml 
        server.port: 5601
        server.host: "10.100.0.222"
        #elasticsearch.url: "http://localhost:9200"
        elasticsearch.url: "http://10.100.0.222:9200"
    
   ```
##### （4）、在 kibana 程序创建启动脚本命令 touch start.sh ; chmod 744 start.sh
      
   ```
          #! /bin/sh
          
          APP_PATH=/data/program/elk/kibana
          
          nohup sh bin/kibana > $APP_PATH/logs/kibana-log.log 2>&1 &
          
          echo kibana run
          
          #bin/kibana &
   ```
   
      

