### 一. redis 安装包下载
http://117.128.6.22/cache/download.redis.io/releases/redis-5.0.4.tar.gz

### 二.redis linux 环境下安装
``` 
序号 项目 值
1 OS版本 Red Hat Enterprise Linux Server release 7.1 (Maipo)
2 内核版本 3.10.0-229.el7.x86_64
3 Redis redis-5.0.4
4 Ruby ruby 2.3.4p301 (2017-03-30 revision 58214) [x86_64-linux]
 
节点信息规划
本来只准备部署7001-7006 6个实例，但是redis集群提示最少需要3个主节点，6个节点才能完成启动，所以后面2个是后来加的。
注意：这里因为是测试环境，所以将所有实例都放在了一台机器上，生产建议主备节点不要放在一台机器上
序号 IP地址 端口 主备 节点配置文件
1 192.168.9.216 7001 主 /data/program/redis/etc/redis-7001.conf
2 192.168.9.216 7002 主 /data/program/redis/etc/redis-7002.conf
3 192.168.9.216 7003 主 /data/program/redis/etc/redis-7003.conf
4 192.168.9.216 7004 备 /data/program/redis/etc/redis-7004.conf
5 192.168.9.216 7005 备 /data/program/redis/etc/redis-7005.conf
6 192.168.9.216 7006 备 /data/program/redis/etc/redis-7006.conf
``` 


### 三、下载对应的redis 安装包，并且解压和安装

``` 

[root@elk01 opt]# wget http://download.redis.io/releases/redis-5.0.4.tar.gz
--2017-10-26 09:33:23--  http://download.redis.io/releases/redis-5.0.4.tar.gz
Resolving download.redis.io (download.redis.io)... 109.74.203.151
Connecting to download.redis.io (download.redis.io)|109.74.203.151|:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 1711660 (1.6M) [application/x-gzip]
Saving to: ‘redis-5.0.4.tar.gz’

100%[======================================================>] 1,711,660    426KB/s   in 3.9s

2017-10-26 09:33:28 (426 KB/s) - ‘redis-5.0.4.tar.gz’ saved [1711660/1711660]

[root@elk01 program]# ll redis-5.0.4.tar.gz
-rw-r--r-- 1 root root 1711660 Jul 24 21:59 redis-5.0.4.tar.gz
[root@CentOS program]# mv redis-5.0.4 redis

``` 

### 四、创建对应的需要目录；
``` 

[root@elk01 program]# cd redis
[suven@CentOS redis]$ ll
total 268
-rw-rw-r--.  1 suven suven 99445 Mar 18 12:21 00-RELEASENOTES
-rw-rw-r--.  1 suven suven    53 Mar 18 12:21 BUGS
-rw-rw-r--.  1 suven suven  1894 Mar 18 12:21 CONTRIBUTING
-rw-rw-r--.  1 suven suven  1487 Mar 18 12:21 COPYING
drwxr-xr-x.  8 root  root   4096 Mar 26 00:01 data
drwxrwxr-x.  6 suven suven  4096 Mar 25 23:12 deps
drwxr-xr-x.  2 root  root   4096 Mar 25 23:57 etc
-rw-rw-r--.  1 suven suven    11 Mar 18 12:21 INSTALL
drwxr-xr-x.  2 root  root   4096 Mar 26 00:02 logs
-rw-rw-r--.  1 suven suven   151 Mar 18 12:21 Makefile
-rw-rw-r--.  1 suven suven  4223 Mar 18 12:21 MANIFESTO
-rw-rw-r--.  1 suven suven 20555 Mar 18 12:21 README.md
-rw-rw-r--.  1 suven suven 62155 Mar 18 12:21 redis.conf
-rwxrwxr-x.  1 suven suven   275 Mar 18 12:21 runtest
-rwxrwxr-x.  1 suven suven   280 Mar 18 12:21 runtest-cluster
-rwxrwxr-x.  1 suven suven   281 Mar 18 12:21 runtest-sentinel
-rw-rw-r--.  1 suven suven  9710 Mar 18 12:21 sentinel.conf
drwxrwxr-x.  3 suven suven  4096 Mar 25 23:45 src
-rw-r--r--.  1 root  root      0 Mar 25 23:59 stdout
drwxrwxr-x. 10 suven suven  4096 Mar 18 12:21 tests
drwxrwxr-x.  8 suven suven  4096 Mar 18 12:21 utils

``` 

### 五、创建目录脚本如下：
``` 

[root@CentOS redis]# mkdir -p /data/program/redis/etc
[root@CentOS redis]# mkdir -p /data/program/redis/logs
[root@CentOS redis]# mkdir -p /data/program/redis/run
[root@CentOS redis]# mkdir -p /data/program/redis/data
[root@CentOS redis]# mkdir -p /data/program/redis/data/{7001,7002,7003,7004,7005,7006}

``` 

### 六、上传配置文件到创建的etc目录上，执行修改脚本：

``` 
（1）.修改配置ip:将配置文件大写的IP,转换成对应的服务器ip地址（127.0.0.1），具体到把个配置文件
sed -i "s/IP/ 127.0.0.1" /data/program/redis/etc/redis-7001.conf
或（linux 正规匹配所有文件）
sed -i "s/IP/ 127.0.0.1" /data/program/redis/etc/redis-700*.conf
(2).修改配置文件PORT：将配置文件大写的PORT,转换成对应的redis启动服务端口7001，具体到把个配置文件
sed -i "s/PORT/ 7001" /data/program/redis/etc/redis-7001.conf
sed -i "s/PORT/ 7002" /data/program/redis/etc/redis-7002.conf
sed -i "s/PORT/ 7003" /data/program/redis/etc/redis-7003.conf
sed -i "s/PORT/ 7004" /data/program/redis/etc/redis-7004.conf
sed -i "s/PORT/ 7005" /data/program/redis/etc/redis-7005.conf
sed -i "s/PORT/ 7006" /data/program/redis/etc/redis-7006.conf

``` 

### 七、执行编译安装命令： make test
``` 

[root@localhost redis]#  make test
cd src && make test
make[1]: Entering directory `/data/program/redis/src

最后看到的结果：
Hint: It's a good idea to run 'make test' ;)

    INSTALL install
    INSTALL install
    INSTALL install
    INSTALL install
    INSTALL install
make[1]: Leaving directory `/data/program/redis/src'

备注:分分钟搞定Redis编译安装
1.  依赖包安装
yum -y install cpp binutils glibc glibc-kernheaders glibc-common glibc-devel gcc make gcc-c++ libstdc++-devel tcl

``` 
### 八、启动redis结点服务命令：

``` 

[root@CentOS redis] cd /data/program/redis/
[root@CentOS redis] src/redis-server  etc/redis-7001.conf
[root@CentOS redis] src/redis-server  etc/redis-7002.conf
[root@CentOS redis] src/redis-server  etc/redis-7003.conf
[root@CentOS redis] src/redis-server  etc/redis-7004.conf
[root@CentOS redis] src/redis-server  etc/redis-7005.conf
[root@CentOS redis] src/redis-server  etc/redis-7006.conf


``` 

### 九、查看redis结点服务是否正常启动：
``` 
[suven@CentOS etc]$ ps -ef | grep redis
root      7470     1  0 00:01 ?        00:00:14 /data/program/redis/src/redis-server 10.211.55.5:7001 [cluster]
root      8377     1  0 00:01 ?        00:00:14 /data/program/redis/src/redis-server 10.211.55.5:7002 [cluster]
root      8591     1  0 00:01 ?        00:00:14 /data/program/redis/src/redis-server 10.211.55.5:7003 [cluster]
root      9597     1  0 00:02 ?        00:00:14 /data/program/redis/src/redis-server 10.211.55.5:7004 [cluster]
root      9816     1  0 00:02 ?        00:00:14 /data/program/redis/src/redis-server 10.211.55.5:7005 [cluster]
root     10031     1  0 00:02 ?        00:00:14 /data/program/redis/src/redis-server 10.211.55.5:7006 [cluster]
suven    19158  9380  0 04:04 pts/0    00:00:00 grep --color=auto redis

  注：如果没有看到进程信息；请查看
[root@CentOS redis]$ tail -200f logs/redis7001.log 
910:C 25 Mar 2019 23:59:35.959 # Can't chdir to '/data/program/redis/data/7001': No such file or directory
7469:C 26 Mar 2019 00:01:32.452 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
7469:C 26 Mar 2019 00:01:32.452 # Redis version=5.0.4, bits=64, commit=00000000, modified=0, pid=7469, just started
7469:C 26 Mar 2019 00:01:32.452 # Configuration loaded
7470:M 26 Mar 2019 00:01:32.454 * No cluster configuration found, I'm 63243f121eb2e90ea3da9d6c7931b577c313e1a0
7470:M 26 Mar 2019 00:01:32.456 * Running mode=cluster, port=7001.
7470:M 26 Mar 2019 00:01:32.456 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
7470:M 26 Mar 2019 00:01:32.456 # Server initialized

``` 

### 十、执行结点实现聚群脚本：(redis 4.x版本依懒 ruby包,5.x版本不需要执行)

``` 
[root@CentOS redis] src/redis-trib.rb  create --replicas 1 10.211.55.5:7001 10.211.55.5:7002 10.211.55.5:7003 10.211.55.5:7004 10.211.55.5:7005 10.211.55.5:7006
src/redis-cli --cluster create 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 127.0.0.1:7006 --cluster-replicas 1
（1）.注： 需要安装ruby2.2.2以上版本，卸载老版本ruby2.0.0
[root@elk01 ~]# ruby -v
ruby 2.0.0p648 (2015-12-16) [x86_64-linux]
[root@elk01 ~]# rpm -qa | grep ruby
ruby-libs-2.0.0.648-29.el7.x86_64
rubygem-bigdecimal-1.2.0-29.el7.x86_64
rubygems-2.0.14.1-29.el7.noarch
rubygem-psych-2.0.0-29.el7.x86_64
rubygem-json-1.7.7-29.el7.x86_64
ruby-irb-2.0.0.648-29.el7.noarch
rubygem-io-console-0.4.2-29.el7.x86_64
ruby-2.0.0.648-29.el7.x86_64
rubygem-rdoc-4.0.0-29.el7.noarch
[root@elk01 ~]# yum erase ruby
注：如果还是旧片本，请参与centos7 rhel7安装较高版本ruby2.2、2.3、2.4笔记执行


（2）.查看ruby软件版本号，如果低于ruby2.2，执行面删除
ruby -v
 rpm -qa | grep ruby

（3）.重新安装ruby2.4版本脚本 
  yum install -y gcc* openssl* wget
  yum install centos-release-scl-rh
  yum install rh-ruby24  -y
  scl  enable  rh-ruby24 bash


（4）.查看ruby软件版本号
[root@localhost redis]# ruby -v
ruby 2.4.5p335 (2018-10-18 revision 65137) [x86_64-linux]
[root@localhost redis]# yum install rubygems
[root@localhost redis]# gem install redis

``` 


### 十一、执行redis 结点聚群脚本；结果如下：其中会提示输入（yes/no）,输入yes 即可；

``` 
[root@CentOS redis]# redis-cli --cluster create 10.211.55.5:7001 10.211.55.5:7002 10.211.55.5:7003 10.211.55.5:7004 10.211.55.5:7005 10.211.55.5:7006 --cluster-replicas 1
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 10.211.55.5:7005 to 10.211.55.5:7001
Adding replica 10.211.55.5:7006 to 10.211.55.5:7002
Adding replica 10.211.55.5:7004 to 10.211.55.5:7003
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 63243f121eb2e90ea3da9d6c7931b577c313e1a0 10.211.55.5:7001
   slots:[0-5460] (5461 slots) master
M: 53692efbc07d2279f267dbba441a06121ea1042f 10.211.55.5:7002
   slots:[5461-10922] (5462 slots) master
M: 4665614f463a77ee12f9afd680e96b7af69c9889 10.211.55.5:7003
   slots:[10923-16383] (5461 slots) master
S: baa658b4068c5125057e885ead90c985c308b9ae 10.211.55.5:7004
   replicates 53692efbc07d2279f267dbba441a06121ea1042f
S: 1a30085728c4d20c7528be2a2faa8d769b5456d1 10.211.55.5:7005
   replicates 4665614f463a77ee12f9afd680e96b7af69c9889
S: e3e9c6e39f9969cc202cd90f045c2acb6b12b427 10.211.55.5:7006
   replicates 63243f121eb2e90ea3da9d6c7931b577c313e1a0
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
...
>>> Performing Cluster Check (using node 10.211.55.5:7001)
M: 63243f121eb2e90ea3da9d6c7931b577c313e1a0 10.211.55.5:7001
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: e3e9c6e39f9969cc202cd90f045c2acb6b12b427 10.211.55.5:7006
   slots: (0 slots) slave
   replicates 63243f121eb2e90ea3da9d6c7931b577c313e1a0
M: 4665614f463a77ee12f9afd680e96b7af69c9889 10.211.55.5:7003
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
M: 53692efbc07d2279f267dbba441a06121ea1042f 10.211.55.5:7002
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: 1a30085728c4d20c7528be2a2faa8d769b5456d1 10.211.55.5:7005
   slots: (0 slots) slave
   replicates 4665614f463a77ee12f9afd680e96b7af69c9889
S: baa658b4068c5125057e885ead90c985c308b9ae 10.211.55.5:7004
   slots: (0 slots) slave
   replicates 53692efbc07d2279f267dbba441a06121ea1042f
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.

``` 

### 十二、连接到对应的redis 聚群脚本：查看聚群是否可用, 一定要 -c
``` 

[root@CentOS redis]# src/redis-cli -c -h 10.211.55.5 -p 7001 
10.211.55.5:7001> 

``` 

### 十三、查看redis聚群信息命令：CLUSTER info
``` 
10.211.55.5:7001> CLUSTER info
cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:3
cluster_current_epoch:6
cluster_my_epoch:1
cluster_stats_messages_ping_sent:385
cluster_stats_messages_pong_sent:385
cluster_stats_messages_sent:770
cluster_stats_messages_ping_received:380
cluster_stats_messages_pong_received:385
cluster_stats_messages_meet_received:5
cluster_stats_messages_received:770

``` 

### 十四、查看redis聚群结点分配信息命令（CLUSTER 区分大小写 ）：CLUSTER nodes 

``` 

10.211.55.5:7001> CLUSTER nodes
e3e9c6e39f9969cc202cd90f045c2acb6b12b427 10.211.55.5:7006@17006 slave 63243f121eb2e90ea3da9d6c7931b577c313e1a0 0 1553588727578 6 connected
4665614f463a77ee12f9afd680e96b7af69c9889 10.211.55.5:7003@17003 master - 0 1553588728081 3 connected 10923-16383
53692efbc07d2279f267dbba441a06121ea1042f 10.211.55.5:7002@17002 master - 0 1553588726573 2 connected 5461-10922
1a30085728c4d20c7528be2a2faa8d769b5456d1 10.211.55.5:7005@17005 slave 4665614f463a77ee12f9afd680e96b7af69c9889 0 1553588726000 5 connected
baa658b4068c5125057e885ead90c985c308b9ae 10.211.55.5:7004@17004 slave 53692efbc07d2279f267dbba441a06121ea1042f 0 1553588728000 4 connected
63243f121eb2e90ea3da9d6c7931b577c313e1a0 10.211.55.5:7001@17001 myself,master - 0 1553588727000 1 connected 0-5460
10.211.55.5:7001> 

``` 

### 十五、验证是否可用，测试用例

``` 
10.211.55.5:7001> set keytest testvalue
OK
10.211.55.5:7001> get keytest
"testvalue"
10.211.55.5:7001> 


CLUSTER INFO 打印集群的信息
CLUSTER NODES 列出集群当前已知的所有节点（node），以及这些节点的相关信息。 
//节点
CLUSTER MEET <ip> <port> 将 ip 和 port 所指定的节点添加到集群当中，让它成为集群的一份子。
CLUSTER FORGET <node_id> 从集群中移除 node_id 指定的节点。
CLUSTER REPLICATE <node_id> 将当前节点设置为 node_id 指定的节点的从节点。
CLUSTER SAVECONFIG 将节点的配置文件保存到硬盘里面。
CLUSTER ADDSLOTS <slot> [slot ...] 将一个或多个槽（slot）指派（assign）给当前节点。
CLUSTER DELSLOTS <slot> [slot ...] 移除一个或多个槽对当前节点的指派。
CLUSTER FLUSHSLOTS 移除指派给当前节点的所有槽，让当前节点变成一个没有指派任何槽的节点。
CLUSTER SETSLOT <slot> NODE <node_id> 将槽 slot 指派给 node_id 指定的节点。
CLUSTER SETSLOT <slot> MIGRATING <node_id> 将本节点的槽 slot 迁移到 node_id 指定的节点中。
CLUSTER SETSLOT <slot> IMPORTING <node_id> 从 node_id 指定的节点中导入槽 slot 到本节点。
CLUSTER SETSLOT <slot> STABLE 取消对槽 slot 的导入（import）或者迁移（migrate）。 
//键
CLUSTER KEYSLOT <key> 计算键 key 应该被放置在哪个槽上。
CLUSTER COUNTKEYSINSLOT <slot> 返回槽 slot 目前包含的键值对数量。
CLUSTER GETKEYSINSLOT <slot> <count> 返回 count 个 slot 槽中的键。 
//新增
CLUSTER SLAVES node-id 返回一个master节点的slaves 列表

```

