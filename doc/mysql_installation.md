## Linux下载安装mysql-8.0.13-linux-glibc2.12-x86_64.tar.xz


### 1.下载安装mysql-8.0.13-linux-glibc2.12-x86_64.tar.xz
```

//下载 mysql-8.0.13-linux-glibc2.12-x86_64.tar.xz
[root@root ~]# wget https://cdn.mysql.com//Downloads/MySQL-8.0/mysql-8.0.13-linux-glibc2.12-x86_64.tar.xz
wget https://cdn.mysql.com//Downloads/MySQL-8.0/mysql-8.0.16-linux-glibc2.12-x86_64.tar.xz


```
### 2.解压安装mysql
```

1.执行解决命令
[root@root ~]# tar -xJvf mysql-8.0.13-linux-glibc2.12-x86_64.tar.xz 
2. 解压完成
mysql-8.0.13-linux-glibc2.12-x86_64/bin/myisamchk
mysql-8.0.13-linux-glibc2.12-x86_64/bin/myisamlog
mysql-8.0.13-linux-glibc2.12-x86_64/bin/myisampack
3. 重命名mysql-8.0.13-linux-glibc2.12-x86_64文件夹为mysql
[root@root ~]# mv mysql-8.0.13-linux-glibc2.12-x86_64 mysql
//将mysql移到/usr/local/ 目录下
[root@root ~]# mv /root/mysql /usr/local/
mv：是否覆盖"/usr/local/mysql"？ y

```
### 3.添加mysql用户组及MySQL用户

```

[root@root ~]# groupadd mysql
[root@root ~]# useradd -r -g mysql mysql
//设置/usr/local/mysql/文件夹的用户权限
[root@root ~]# cd /usr/local/mysql/
[root@root mysql]# chown -R mysql:mysql ./
//新建MySQL数据存储文件夹
[root@root mysql]# mkdir data


```
### 4.初始化mysql数据库，生成默认密码

```

[root@iZwz9ijcs0cia5xad0sewhZ mysql]#  bin/mysqld --initialize --user=mysql --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data/
2019-07-01T08:42:30.346859Z 0 [System] [MY-013169] [Server] /usr/local/mysql/bin/mysqld (mysqld 8.0.16) initializing of server in progress as process 15888
2019-07-01T08:42:33.616199Z 5 [Note] [MY-010454] [Server] A temporary password is generated for root@localhost: lqs-.d,fN1pB
2019-07-01T08:42:35.006735Z 0 [System] [MY-013170] [Server] /usr/local/mysql/bin/mysqld (mysqld 8.0.16) initializing of server has completed
[Server] A temporary password is generated for root@localhost: lqs-.d,fN1pB //这是初始默认密码

```
### 5.设置数据库存储文件夹的用户权限
```
[root@root mysql]# chown -R root:root ./
[root@root mysql]# chown -R mysql:mysql data

```

### 6.修改数据库对应 etc/my.cnf配置文件
```

[root@root ~]# vim /etc/my.cnf 
//修改为以下内容：
[mysqld]
server-id                      = 1
port                           = 3306
bind-address                   = 0.0.0.0 
max_connections                = 5000
mysqlx_port                    = 33060
mysqlx_socket                  = /tmp/mysqlx.sock
basedir                        = /usr/local/mysql
datadir                        = /usr/local/mysql/data
socket                         = /tmp/mysql.sock
pid-file                       = /tmp/mysqld.pid
slow_query_log                 = ON
slow_query_log_file            = /usr/local/mysql/logs/slow.log
log-error                      = /usr/local/mysql/logs/error.log
long_query_time                = 0.2
log-bin                        = bin.log
relay-log                      = relay.log
binlog_format                  = ROW
relay_log_recovery             = 1
character-set-client-handshake = FALSE
character-set-server           = utf8mb4
collation-server               = utf8mb4_unicode_ci
init_connect                   ='SET NAMES utf8mb4'
innodb_buffer_pool_size        = 1G
join_buffer_size               = 128M
sort_buffer_size               = 2M
read_rnd_buffer_size           = 2M
log_timestamps                 = SYSTEM
lower_case_table_names         = 1
default-authentication-plugin  = mysql_native_password
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION

保存退出。

[root@root mysql]# chmod 777 /etc/my.cnf 
//设置开机自启动
[root@root mysql]# cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql
[root@root mysql]# chmod +x /etc/init.d/mysql 

```

### 7.注册查看服务

```

[root@root mysql]# chkconfig --add mysql
[root@root mysql]# chkconfig --list mysql
mysql              0:关闭    1:关闭    2:启用    3:启用    4:启用    5:启用    6:关闭
//    /etc/ld.so.conf 这个文件记录了编译时使用的动态链接库的路径，告诉链接器去哪个路径下寻找链接时需要用到的库，如果找不到，就会提示链接错误。
如果我们安装了第三方的库，而没有将它放在链接器可以找到的目录，则运行使用这些第三方库的程序时，会提示找不到库。

[root@root mysql]# vim /etc/ld.so.cnf
添加如下内容：
include ld.so.conf.d/*.conf
/usr/local/mysql/lib

//配置环境变量
[root@root mysql]# vim /etc/profile
添加如下内容：
export PATH=$PATH:/usr/local/mysql/bin:/usr/local/mysql/lib
//让配置文件立马生效
[root@root mysql]# source /etc/profile
//启动MySQL服务
[root@root mysql]# service mysql start

```

### 7.使用生成的密码登陆MySQL数据库,并修改数据库账号密码

```

[root@root mysql]# mysql -u root -p
mysql: [Warning] World-writable config file '/etc/my.cnf' is ignored.
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 8
Server version: 8.0.13
Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.
Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.
Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
//修改密码
mysql> alter user 'root'@'localhost' identified by '123456';
Query OK, 0 rows affected (0.13 sec)
mysql> exit
Bye

```
### 7.重先登录mysql数据库
```

[root@root mysql]# mysql -u root -p
mysql: [Warning] World-writable config file '/etc/my.cnf' is ignored.
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 9
Server version: 8.0.13 MySQL Community Server - GPL
Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.
Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.
Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
4 rows in set (0.01 sec)
到此安装完成！

```

### 8.mysql账号角色设置
```
这里设置的 /etc/my.cnf 权限是777 ，就是任何人都可读可写，my.cnf 会被忽略掉，mysql的安全机制，也可以不要my.cnf配置文件。
 
下面设置一下MySQL的远程访问
简单写一下命令，自己写的时候忘了记录，网上也是一大堆。
登陆mysql

mysql -u root -p 

mysql> alter user 'root'@'localhost' identified by 'dev123456';
Query OK, 0 rows affected (0.02 sec)

use mysql;
select host,user from user;
 
CREATE USER 'root'@'%' IDENTIFIED BY '123456';
GRANT ALL ON *.* TO 'root'@'%';
flush privileges;
ALTER USER 'root'@'%' IDENTIFIED BY '123456' PASSWORD EXPIRE NEVER; 
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456'; 
FLUSH PRIVILEGES; 

mysql> alter user 'root'@'localhost' identified by 'dev123456';
Query OK, 0 rows affected (0.02 sec)


```

### 9.修改数据库密码
###### 方法1： 用SET PASSWORD命令

```
MySQL -u root
　　mysql> SET PASSWORD FOR 'root'@'localhost' = PASSWORD('newpass');
```

方法2：用mysqladmin


```
mysqladmin -u root password "newpass"

```

###### 如果root已经设置过密码，采用如下方法

```
mysqladmin -u root password oldpass "newpass"

```
###### 方法3： 用UPDATE直接编辑user表

```
mysql -u root
　mysql> use mysql;
　mysql> UPDATE user SET Password = PASSWORD('newpass') WHERE user = 'root';
　mysql> FLUSH PRIVILEGES;

```
###### 在丢失root密码的时候，可以这样

```

mysqld_safe --skip-grant-tables&
　　mysql -u root mysql
　　mysql> UPDATE user SET password=PASSWORD("new password") WHERE user='root';
　　mysql> FLUSH PRIVILEGES;

```

###### 以上所述是小编给大家介绍的MySQL修改root密码的多种方法，希望对大家有所帮助，如果大家有任何疑问请给我留言，小编会及时回复大家的。在此也非常感谢大家对脚本之家网站的支持！
 
###  10.下面一定要记得关闭防火墙
service iptables stop
 
设置防火墙开机不启动
chkconfig iptables off 
 
然后关机重启，连接试一试吧！
```