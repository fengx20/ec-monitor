#!/bin/bash
# #! 告诉系统这个脚本需要什么解释器来执行。
cd /opt/software                   # 文件的目录位置
count=$(ls -l | grep '^-' | wc -l) # 保存当前目录下的文件个数，这里使用管道符来进行计算
if [ $count -ne 0 ]; then # 文件个数不等于0开始进入条件
  for file in $(# 遍历所有文件
    ls
  ); do
    fileName=$file            # 将文件名进行保存，这里是方便后面截取文件类型作准备
    targzName=${fileName:0-6} # tar.gz类型的文件，这里是截取最后6位
    tarName=${fileName:0-3}   # tar类型的文件，截取最后3位
    zipName=${fileName:0-3}   # zip类型的文件，截取最后3位
    if [ "$targzName" = "tar.gz" ]; then # 判断是否是tar.gz类型
      tar -zxf /opt/software/$file -C /opt/install # 解压到/opt/install目录下
    fi
    if [ "$gzName" = "gz" ]; then # 判断是否是gz类型
      tar -xf /opt/software/$file -C /opt/install
    fi
    if [ "$zipName" = "zip" ]; then # 判断是否是zip类型
      unzip -d /opt/software/$file /opt/installl # 判断是否是zip类型
    fi
  done
else
  echo "this direction is null" # 如果没有文件，给出提示
fi
