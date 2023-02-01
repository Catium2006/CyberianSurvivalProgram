# 数据结构

## MessageNode

[Class](../src/main/java/top/catium/csp/game/message/MessageNode.java)

包含:

+ 字符串 `type`
+ 字符串 `content`

`type`可选:

+ `TEXT`
+ `IMG_URL`

## Message

[Class](../src/main/java/top/catium/csp/game/message/Message.java)

包含一个名为`nodes`的ArrayList<[MessageNode](#messagenode)>

对于一条自然意义上(人发出来的, 就像在QQ)的消息, 应该按照如下方式划分为`MessageNode`  

+ 文本有空格的, 空格两侧划分为两个节点, 每个节点均不含空格  
+ 特殊意义的消息单独一个节点(比如图片转为`IMG_URL`类型节点)  
+ 节点按照原文顺序排列