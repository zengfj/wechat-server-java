# 基础镜像
FROM openjdk:8-jre
# author
MAINTAINER Fan

# 挂载目录
VOLUME /fan
# 创建目录
RUN mkdir -p /fan
# 指定路径
WORKDIR /fan
# 暴露容器运行时的 8080 监听端口给外部
EXPOSE 8080
COPY ./target/wechat-server.jar /fan/wechat-server.jar
# 启动服务
ENTRYPOINT ["java","-jar","/fan/wechat-server.jar"]