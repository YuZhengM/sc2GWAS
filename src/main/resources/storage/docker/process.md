# Docker 搭建流程

## 1. 搭建 MySQL 容器

> 拉镜像

```shell
docker pull mysql:8.0.32
```

> 建立共享文件

```shell
touch ${my.cof_file}
```

> 添加内容

```shell
vim ${my.cof_file}
```

```shell
# For advice on how to change settings please see
# http://dev.mysql.com/doc/refman/8.0/en/server-configuration-defaults.html

[mysqld]
#
# Remove leading # and set to the amount of RAM for the most important data
# cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
# innodb_buffer_pool_size = 128M
#
# Remove leading # to turn on a very important data integrity option: logging
# changes to the binary log between backups.
# log_bin
#
# Remove leading # to set options mainly useful for reporting servers.
# The server defaults are faster for transactions and fast SELECTs.
# Adjust sizes as needed, experiment to find the optimal values.
# join_buffer_size = 128M
# sort_buffer_size = 2M
# read_rnd_buffer_size = 2M

# Remove leading # to revert to previous value for default_authentication_plugin,
# this will increase compatibility with older clients. For background, see:
# https://dev.mysql.com/doc/refman/8.0/en/server-system-variables.html#sysvar_default_authentication_plugin
# default-authentication-plugin=mysql_native_password
skip-host-cache
skip-name-resolve
datadir=/var/lib/mysql
socket=/var/run/mysqld/mysqld.sock
secure-file-priv=/var/lib/mysql-files
user=root

pid-file=/var/run/mysqld/mysqld.pid
[client]
socket=/var/run/mysqld/mysqld.sock

!includedir /etc/mysql/conf.d/
```

> 运行 MySQL 容器

```shell
docker run -d -it \
           -p ${database_port}:3306 \
           -v ${my.cof_file}:/etc/mysql/my.cnf \
           -v ${mysql_data}:/var/lib/mysql \
           -v ${mysql_mysqlfile}:/root \
           -e MYSQL_ROOT_PASSWORD=${datasource_password} \
           --privileged --name=sc2gwas_mysql mysql:8.0.32
```

> 下面是一些容器操作
> 详细看：[https://blog.csdn.net/YKenan/article/details/91396670](https://blog.csdn.net/YKenan/article/details/91396670)

```shell
docker logs sc2gwas_mysql;
docker start sc2gwas_mysql;
docker stop sc2gwas_mysql;
docker rm sc2gwas_mysql;
docker restart sc2gwas_mysql;
```

> 进入容器

```shell
docker exec -it sc2gwas_mysql bash
```

> 登录

```shell
mysql -uroot -p
${datasource_password}
```

> 开启远程连接权限

```mysql
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
CREATE USER 'root'@'0.0.0.0' IDENTIFIED BY '${datasource_password}';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'0.0.0.0' WITH GRANT OPTION;
FLUSH PRIVILEGES;
use mysql;
select host, user
from user;
```
