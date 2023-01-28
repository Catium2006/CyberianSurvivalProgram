# 接口说明  

CSP的接口建立在HTTP协议上. 除资源文件外, 接口均需要以特定方法和格式请求特定URL.  

## 数据交换格式
我们所用到的数据, 除了涉及到资源文件的, 均以JSON格式交换(称为简单数据).  

### POST

所有简单数据, 使用POST请求的时候, 均按照如下方式提交:  

> 提交一个`form-data`, 包含且仅包含一个字段, 字段名为`json`, 该字段内容为一段合法的JSON格式字符串.
> JSON字符串具体内容视接口而定.

服务端在处理请求的时候, 会匹配`form-data`中的字符串`Content-Disposition: form-data; name="json"\r\n\r\n`, 如果存在该字段则把它当做一段JSON字符串解析.  

服务端会返回`400 Bad Request`错误, 当发生如下几种情况:  
1. 不存在`Content-type..."json"\r\n\r\n`字段.  
2. 该字段内容不是合法的JSON字符串.  
3. JSON字符串包含的信息不符合接口要求.  


## 目录  
+ /
  + /api/
    + [/api/login/]()
  + /res/
