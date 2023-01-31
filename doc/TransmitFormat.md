# 接口说明

CSP的接口建立在HTTP协议上. 除资源文件外, 接口均需要以特定方法和格式请求特定URL.

## 数据交换格式

我们所用到的数据, 除了涉及到资源文件的, 均以JSON格式交换(称为简单数据).

### POST

所有简单数据, 使用POST请求的时候, 均按照如下方式提交:

> 提交一个`form-data`, 包含且仅包含一个字段, 字段名为`json`, 该字段内容为一段合法的JSON格式字符串.
> JSON字符串具体内容视接口而定.

服务端在处理请求的时候, 会匹配`form-data`中的字符串`Content-Disposition: form-data; name="json"\r\n\r\n`,
如果存在该字段则把它当做一段JSON字符串解析.

服务端会返回`400 Bad Request`错误, 当发生如下情况:

1. 不存在`Content-type..."json"\r\n\r\n`字段.
2. 该字段内容不是合法的JSON字符串.
3. JSON字符串包含的信息不符合接口要求.

返回`404 Not Found`错误, 当发生如下情况:

1. 接口不存在
2. 资源不存在
3. 访问地址有歧义

## 目录

+ /
    + /api/
        + [/api/login](#api-login)
    + /res/



### api-login

URL:`/api/login`

请求方法:`POST`

请求JSON结构:

| key        | type   | 解释                 |
|------------|--------|--------------------|
| type       | String | "login"或"register" |
| playerName | String | 玩家名                |
| password   | String | 密码(明文)             |

响应JSON结构:

| key     | type    | 解释                                   |
|---------|---------|--------------------------------------|
| success | boolean | 操作是否成功                               |
| token   | String  | `"success":false`时为空, 否则为身份凭据, 请妥善保存 |

示例:  
```javascript
var formdata = new FormData();
formdata.append("json", "{\"type\":\"login\",\"playerName\":\"Catium\",\"password\":\"abc\"}");

var requestOptions = {
  method: 'POST',
  body: formdata,
  redirect: 'follow'
};

fetch("localhost/api/login", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));
```