
官方的地址下载实在太慢，安装失败，使用了下面的方式，aliyun的镜像

curl -sSL http://acs-public-mirror.oss-cn-hangzhou.aliyuncs.com/docker-engine/internet | sh -

来自：http://mirrors.aliyun.com/help/docker-engine

对镜像仓库同样做了优化，下面的例子

echo "DOCKER_OPTS=\"\$DOCKER_OPTS --registry-mirror=https://3stby6lp.mirror.aliyuncs.com\"" | sudo tee -a /etc/default/docker 
sudo service docker restart

来自：https://cr.console.aliyun.com/#/accelerator
