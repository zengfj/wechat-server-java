# 微信公众号服务器
> 用以微信公众号的后端，提供登录验证功能
> 
> 针对 [new-api](https://github.com/Calcium-Ion/new-api) [one-api](https://github.com/songquanpeng/one-api) 进行开发，其他项目也可以使用。
> 
> 主要是让熟悉Java开发的伙伴 把本项目集成到已经部署的接收微信服务器项目中，也可以直接进行使用。
> 
> 本项目目前没有前端页面，靠配置文件进行配置。

## 功能
+ [x] 登录验证
+ [x] 自定义回复

## 展示
[FanStars API](https://api.fanstars.cn) 的微信登录就是基于本项目构建的, 欢迎前去体验。

## 配置
```yaml
server:
  port: 8088 # 端口

wx:
  mp:
    appId: xxxxxxxxxx  # 公众号 AppID
    secret: xxxxxxxxxx # 公众号 AppSecret
    token: xxxxxxxxxx  # 公众号 消息token
    aesKey: xxxxxxxxxx # 公众号 消息aesKey
api:
  token: xxxxxxxxxx        # token, one-api/new-api 中要设置一样
  send-code-keyword: 验证码 # 发送code关键字
  code-template: "您正在登录 FanStars API, 验证码是: ${code}" # 发送验证码的模板
  code-length: 6           # 验证码长度
  code-expire-time: 300000 # 验证码过期时间，以毫秒为单位
  width: 300  # 宽
  height: 300 # 高
  margin: 1   # 边距
  qrcode-scene-id: 1008   # 二维码场景ID
  qrcode-expire-time: 600 # 二维码过期时间，以秒为单位
  custom-reply-map:       # 自定义回复键值对
    lol: "英雄联盟"
    cf: "窜越火线"
    dnf: "地下城与勇士"
```

## API
### 获取 Access Token
1. 请求方法：`GET`
2. URL：`/api/wechat/access_token`
3. 无参数，但是需要设置 HTTP 头部：`Authorization: <token>`

### 通过验证码查询用户 ID
1. 请求方法：`GET`
2. URL：`/api/wechat/user?code=<code>`
3. 需要设置 HTTP 头部：`Authorization: <token>`

### 获取场景二维码
1. 请求方法：`GET`
2. URL：`/api/wechat/qrcode`
3. 无参数

### 注意
需要将 `<token>` 和 `<code>` 替换为实际的内容。