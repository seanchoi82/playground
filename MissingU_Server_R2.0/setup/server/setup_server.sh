##########################
# 계정생성
##########################
useradd missingu

##########################
#패스워드 설정
##########################
userpw missingu

##########################
#소유자 변경
##########################
chown -R missingu /app/missingu

##########################
#Profile 수정 - 개발
##########################
vi /etc/profile

#java setting
#[DEV]
export JAVA_HOME=/usr/local/jdk1.6.0_32
export CATALINA_HOME=/opt/lampp/tomcat60
export CATALINA_OPTS="-Dmissingu_home=/opt/lampp -DCONSOLE_LOG_LEVEL=INFO -DFILE_LOG_LEVEL=DEBUG"
export PATH=$PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin
export CLASSPATH=$JAVA_HOME/jre/ext:$JAVA_HOME/lib/tools.jar:$CATALINA_HOME/common/lib/jsp-api.jar:$CATALINA_HOME/common/lib/servlet-api.jar


##########################
#Profile 수정 - 운영
##########################
vi /home/missing/.bash_profile

#[PROD]
export JAVA_HOME=/usr/local/jdk1.6.0_32
export CATALINA_HOME=/app/missingu/tomcat60
export CATALINA_OPTS="-Dmissingu_home=/app/missingu -DCONSOLE_LOG_LEVEL=INFO -DFILE_LOG_LEVEL=INFO"
export PATH=$PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin
export CLASSPATH=$JAVA_HOME/jre/ext:$JAVA_HOME/lib/tools.jar:$CATALINA_HOME/common/lib/jsp-api.jar:$CATALINA_HOME/common/lib/servlet-api.jar

##########################
#폴더 복사
##########################
tar cpf - /home/test --exclude=abc | tar xpf - -C /home/linux/
tar cpf - /opt/lampp/tomcat60 | tar xpf - -C /home/linux/
tar -xf missingu_tomcat.tar -C /app/missingu/tomcat60


##########################
# iptables 설정
##########################
# 80으로 들어오는 요청을 8080으로 Redirect
/sbin/iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8090
/sbin/iptables -t nat -A PREROUTING -p tcp --dport 443 -j REDIRECT --to-port 8453

iptables-save  >   /root/firewall.txt
iptables-restore   <  /root/firewall.txt

/sbin/service iptables save
/sbin/service iptables restart

#방화벽 조회
iptables -L --line-numbers
iptables -L -t nat -v


--------------------------
외부에서 PC1의 웹서버로(80번 포트)로 접속하면 PC2의 웹서버(80번 포트)에 접속되게 해보자

/etc/sysctl.conf의 net.ipv4.ip_forward항목이 0으로 되어 있으면 1로 변경한다.

변경사항 적용
# sysctl -p /etc/sysctl.conf

변경사항 확인
# cat /proc/sys/net/ipv4/ip_forward
1

PC1 iptables 설정 변경

iptables -t nat -A PREROUTING -p tcp -i eth0 -d 111.111.111.111 --dport 80 -j DNAT --to 192.168.1.2:80
iptables -A FORWARD -p tcp -i eth0 -d 192.168.1.2 --dport 80 -j ACCEPT