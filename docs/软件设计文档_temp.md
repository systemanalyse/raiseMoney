# 软件设计文档

## 引言

### 编写目的

本文档主要为了方便前后端开发者、数据库开发者协调对接，将各个系统模块拆解展示内部结构，使开发者对整个软件架构体系有一个清晰的认识，有利于系统开发和后面运营、维护工作的进行。

-----------------------------

# <p align = "middle">系统说明</p>

# 系统组成
本应用主要包括以下几个部分：

| 子系统 | 参与开发人员 | 目标用户 |
| ------ | ------ | ------ | 
| Jingle 安卓端 | 李杰泓、吴啸林 | 所有客户端用户群体 |
| Jingle 服务端 | 鞠擘、王永杰 | 前端开发人员 |
| Jingle 数据库 | 王培钰 | 服务端开发人员 |

# 系统结构图

![软件结构图](./images/framework.png)

----------------------------------------

<br>

# <p align = "middle">**模块设计**</p><br>

# 0 模块清单

| 模块编号 | 模块名称 | 模块标识符 |
| ------ | ------ | ------ | 
| 1 | 首次启动引导模块 | GuideActivity |
| 2 | 用户系统模块 | UserSystem |
| 3 | 鉴权服务模块 | AuthenticationService |
| 4 | 钱包充值提现模块 | WalletService |
| 5 | 校园认证模块 | CertificationActivity |
| 6 | 名片设置模块 | ProfileSetting |
| 7 | 消息通知模块 | MessageService |
| 8 | 任务发布接受模块 | Publish-AcceptActivity |
| 9 | 任务查询模块 | TaskActivity |
| 10 | 任务交互模块 | TaskInteracteActivity |
| 11 | 照片上传下载模块 | PictureActivity |

---------------------------

# 1 GuideActivity

## 1.1 概述

引导页主要用于用户安装 app 之后首次打开应用时提供一个产品介绍以及简单的使用方法说明，目的是让用户能够快速上手，并提供精美的引导UI，给用户营造良好的使用感受。

## 1.2 算法

通过 SharePreference 持久化 是否首次启动应用 的状态，在每次 app 启动时判断该状态，如果属于首次启动应用则加载该模块，否则进入应用。

## 1.3 限制条件

仅在安装 app 之后首次打开应用才会

# 2 UserSystem


## 2.1 概述

用户相关操作，在本模块，用户能够查询自己的个人信息，包括：个人注册、认证信息，参加过的任务信息（发布、接受），账户信息。

------
## 2.2 接口详情

### 2.2.1 用户接口：查看个人信息
#### 2.2.1.1 `接口：获取信息`

<a name="getprivaryinfo"></a>
### Get privacy infomation, use user id, add token in session
```
GET /user/{userId}/Privary
``` 

#### Description
Get the user detailed information, return the User infomation privacy, cann't use by others


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user id|integer (int64)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return User privacy information|[UserInfoPrivacy](#userinfoprivacy)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User

--------------------

### 2.2.2 用户接口：登陆注册流

#### 2.2.2.1 `接口：注册`

<a name="register"></a>
### user regist, need detailed UserInformation
```
POST /regist
```


#### Description
Return detailed UserInformation


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**FormData**|**UserInformation**  <br>*required*|User detailed infomation|[UserInfoPrivacy](#userinfoprivacy) string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation|[UserInfoPrivacy](#userinfoprivacy)|
|**400**|wrong format, return wrong message|string|
|**406**|User already regist, return wrong message|string|


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* Regist

#### 2.2.2.2 `接口：登陆`

<a name="login"></a>
### User log in the system, need email & password
```
POST /login
```


#### Description
Return detailed UserInformation(success)/ reason(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**userInfo**  <br>*optional*|the email & password of user|[userInfo](#login-userinfo)|

<a name="login-userinfo"></a>
**userInfo**

|Name|Schema|
|---|---|
|**email**  <br>*optional*|string|
|**password**  <br>*optional*|string (password)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, get the token of user|[UserInfoPrivacy](#userinfoprivacy)|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**406**|Already log in, return wrong message|string|


#### Tags

* Login

#### 2.2.2.3 `接口：登出`

<a name="logout"></a>
### User log out the system, need email & password
```
POST /logout
```


#### Description
return success / failure message


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**userInfo**  <br>*optional*|the email & password of user|[userInfo](#logout-userinfo)|

<a name="logout-userinfo"></a>
**userInfo**

|Name|Schema|
|---|---|
|**email**  <br>*optional*|string|
|**password**  <br>*optional*|string (password)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, get the token of user|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**406**|Already log in, return wrong message|string|


#### Tags

* Logout

-----------------------

### 2.2.3 用户接口：任务查询接口
#### 2.2.3.1 `接口：查询已发布的任务`

<a name="userpublishtask"></a>
### Get task list of user Publish !!!API changed!!!
```
GET /user/{userId}/tasks/Publish
```


#### Description
return task list(success)/ wrong message(fail)   !!!This API is replaced by task/Publish/{userId}/:Get!!!


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return task list|[TasksInList](#tasksinlist)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User

#### 2.2.3.2 `接口：查询接受的任务`

<a name="userreceivedtask"></a>
### Get task list of user accept !!!API changed!!!
```
GET /user/{userId}/tasks/Accept
```


#### Description
return task list(success)/ wrong message(fail)  !!!This API is replaced by task/Received/2/:Get!!!


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user email|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return task list|[TasksInList](#tasksinlist)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User

----------------------

### 2.2.4 用户接口：余额
#### 2.2.4.1 `接口：查询余额`

<a name="getwallet"></a>
### Get user Jin acount, need id
```
GET /user/{userId}/Wallet
```


#### Description
return user Jin account(success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user id|integer (int64)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return user Jin num|[Jin](#jin)|
|**400**|Wrong format, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User

------------------

## 2.3 数据结构

### 2.3.1 `用户信息（详细）`

<a name="userinfoprivary"></a>
### UserInfoPrivacy
User detailed information, only visible to userself


|Name|Description|Schema|
|---|---|---|
|**Jin**  <br>*optional*|account balance|number|
|**acceptNum**  <br>*optional*|accept task num|integer (int64)|
|**avatarURL**  <br>*optional*|user avatar URL|string|
|**dormitory**  <br>*optional*|location of user dormitory|string|
|**email**  <br>*optional*||string|
|**enrollment**  <br>*optional*|only year|string|
|**gender**  <br>*optional*|sexy of user, true is male, default true|boolean|
|**name**  <br>*optional*||string|
|**password**  <br>*optional*||string|
|**phone**  <br>*optional*||string|
|**publishNum**  <br>*optional*|publish task num|integer (int64)|
|**qq**  <br>*optional*||string|
|**school**  <br>*optional*||string|
|**signature**  <br>*optional*||string|
|**studentCardURL**  <br>*optional*||string|
|**userId**  <br>*optional*||integer (int64)|
|**wechart**  <br>*optional*||string|

--------------------------------

### 2.3.2 `用户信息（简略，任务中显示）`

<a name="userinfointask"></a>
### UserInfoInTask
user info in task

|Name|Description|Schema|
|---|---|---|
|**acceptNum**  <br>*optional*|accept task num|integer (int64)|
|**dormitory**  <br>*optional*|location of user dormitory|string|
|**email**  <br>*optional*|unique identify|string|
|**name**  <br>*optional*||string|
|**phone**  <br>*optional*||string|
|**publishNum**  <br>*optional*|publish task num|integer (int64)|
|**signature**  <br>*optional*||string|
|**userId**  <br>*optional*||integer (int64)|

<a name="userinfosintask"></a>
### UserInfosInTask
*Type* : < [UserInfoInTask](#userinfointask) > array

-----------------------------

### 2.3.3 `任务信息（简略）`

<a name="taskinlist"></a>
### TaskInList
task base infomation, only visible in list


|Name|Description|Schema|
|---|---|---|
|**beginTime**  <br>*optional*|publish time|string|
|**origin**  <br>*optional*|relattive to user? user publish(1), user accept(2), no relation(0)|integer (int64)|
|**publisherInfo**  <br>*optional*|UserinfoInTask, publish| [UserInfoInTask](#userinfointask) |
|**status**  <br>*optional*||[status](#taskinlist-status)|
|**taskId**  <br>*optional*|Unique identifier|string|
|**taskStatus**  <br>*optional*|task status, running(true) & finish(false)|boolean|
|**taskType**  <br>*optional*|task type, PP(true) & DD(false)|boolean|
|**title**  <br>*optional*||string|
|**value**  <br>*optional*|task bounce / people will get bounce|number|

<a name="taskinlist-status"></a>
**status**

|Name|Description|Schema|
|---|---|---|
|**desc**  <br>*optional*|discription of status|string|
|**statusCode**  <br>*optional*|...|integer (int64)|

<a name="tasksinlist"></a>
### TasksInList
*Type* : < [TaskInList](#taskinlist) > array

--------------------------

### 2.3.4 `账户信息`

<a name="jin"></a>
### Jin
Jin coin

*Type* : number

---------------------------

## 2.4 算法
### 2.4.1 获取私人信息
 - 从`session`中获取`token`，认证是否为本人，合法则可返回数据。

### 2.4.2 获取任务
 - 从关联表`Acceptor`（详情可见数据库设计文档）获取任务与人物的关联信息，从而在任务表中索引相关信息。

----------------------

## 2.5 限制条件
 - 查询接口只有经过`token`认证的本人才能够进行。

------------------------------

# 3 AuthenticationService

## 3.1 概述

通过鉴权服务判断用户行为是否合法，预防软件遭到侵害性攻击行为，保障系统稳定、安全、健壮

------------------

## 3.2 接口详情

通过`Post: api/Login`以及`Post: api/Regist`能够获得`token`，之后的每一次api访问，需要将`token`存放在`session`中。    

---------------

## 3.3 限制条件
 - 在进行操作的时候，必须在`session`中携带`token`，否则视为无效请求。
 - 通过 session 维护用户会话状态，判断服务端保持的 session 与发起请求的用户id是否一致来鉴别用户身份，同时还可以提供额外的拦截服务。

-----------

# 4 WalletService

## 4.1 概述

钱包系统是整个 app 的账号价值体系支撑，用户在该软件平台上的操作大多数需要平台金币的支持，比如赏金等。钱包系统支持用户充值、提现。人民币与平台金币的兑换比例是1：10。
    本模块支持用户充值、提现。

-----------------

## 4.2 接口详情

#### 4.2.1.1 `接口：修改余额（进账支出）`

<a name="changewallet"></a>
### recharge/ withdraw, use positive as recharge, negative as withdraw
```
PUT /user/{userId}/Wallet
```


#### Description
return wallet account(success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|id of user|integer (int64)|
|**FormData**|**Jin**  <br>*required*|Jin coin num to modify|number|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return user account balance|[Jin](#jin)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* User

-----------------------

## 4.3 算法
### 4.3.1 提现
 - 甄别用户账户余额（去除被冻结部分），若提现请求合法，则可以提现。

### 4.3.2 充值
 - 充值时，通过反复请求的方式来确定充值操作是否完成。当进行充值操作的时候，会生成一个订单号，在请求时附带订单号。后端第一次接收到请求，返回正常数据，接收到重复请求，则可确认账户已成功充值，此时返回一个特殊口令；而前端收到如特殊口令后则可确认订单完成。两方中任意一方没有确认，则订单需要同步，未能够完成充值。

---------------------------

## 4.4 限制条件
 - 提现金额 >= 未冻结余额。
 - 查看余额、账号充值、提现，都需要做严格的身份校验，确保账号安全。

------------------

# 5 CertificationActivity

## 5.1 概述

确认用户认证信息，用户完成认真信息能够更好的进行任务。

------------------

## 5.2 接口详情
### 5.2.1 用户相关：认证
#### 5.2.1.1 接口：更新信息
<a name="updateuserinfo"></a>
### nodify user information, need user information, add token in session
```
PUT /user/{userId}/Privary
```


#### Description
return user public infomation(success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|id of user|integer (int64)|
|**FormData**|**userInfo**  <br>*required*|new praviry information|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return user privacy information|[UserInfoPrivacy](#userinfoprivacy)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* User

---------------------

## 5.3 限制条件
 - 非本人不得修改。

----------------------

# 6 ProfileSetting

## 6.1 概述
每一名用户拥有一张名片，其中包含本人的基本信息，能够展示给其他人查看。

--------------

## 6.2 接口详情
### 6.2.1 用户接口：获取名片
#### 6.2.1.1 `接口：获取名片`

<a name="getpublicinfo"></a>
### Get user Public info(BusinessCard), need email
```
GET /user/{userId}/Public
```


#### Description
return user public information(success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user id|integer (int64)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return user public info|[UserInfoPublic](#userinfopublic)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User

--------------------

## 6.3 数据结构
### 6.3.1 用户信息（简略，用于名片）

<a name="userinfopublic"></a>
### UserInfoPublic
user business card, visible to everyone


|Name|Description|Schema|
|---|---|---|
|**avatarURL**  <br>*optional*|user avatar URL|string|
|**dormitory**  <br>*optional*|location of user dormitory|string|
|**name**  <br>*optional*||string|
|**phone**  <br>*optional*||string|
|**qq**  <br>*optional*||string|
|**signature**  <br>*optional*||string|
|**userId**  <br>*optional*||integer (int64)|
|**wechart**  <br>*optional*||string|

----------------

## 6.4 算法
### 6.4.1 获取名片
 - 不需要认证本人，可以直接查询数据库并返回结果。

------------------------

## 6.5 限制条件
 - 只要是注册用户就可以查询。

-------------------

# 7 MessageService

## 7.1 概述

消息通知模块提供全局消息通知功能，包括用户创建、接受任务反馈，用户发布的任务进行状态变更、用户账号余额变更、用户信息变更等功能通知。提供逐条已读和一键已读功能。

----------------------------

## 7.2 接口详情

### 7.2.1 用户接口：通知

#### 7.2.1.1 `接口：获取通知`

<a name="getnotice"></a>
### Get user Notice list
```
GET /user/{userId}/Notice
```


#### Description
return notice list(success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user id|integer (int64)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return User Notices|[Notices](#notices)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User

#### 7.2.1.2 `接口：阅读通知`

<a name="readnotice"></a>
### Set user notice status is false
```
PUT /user/{userId}/ReadNotice
```


#### Description
return nothing(success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user email|string|
|**FormData**|**noticeId**  <br>*optional*|notice id had read|integer (int64)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation|string|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* User

-----------------------

## 7.3 数据结构

### 7.3.1 `通知信息`

<a name="notice"></a>
### Notice
user Notice


|Name|Description|Schema|
|---|---|---|
|**desc**  <br>*optional*|task description|string|
|**noticeId**  <br>*optional*|notice id|integer (int64)|
|**status**  <br>*optional*|have read(true) & not(false)|boolean|
|**taskType**  <br>*optional*|task type, PP(true) & DD(false)|boolean|
|**time**  <br>*optional*|notice time (send)|string|
|**title**  <br>*optional*||string|
|**userType**  <br>*optional*|user type, acceptor(true) & publisher(false)|boolean|


<a name="notices"></a>
### Notices
*Type* : < [Notice](#notice) > array

----------------

## 7.4 算法
### 7.4.1 获取通知
 - 通知中获取接收人字段即可筛选。

### 7.4.2 阅读通知
 - 阅读后的通知即可删除。

----------------

## 7.5 限制条件
 - 非本人无法阅读本人通知。

----------------------

# 8 Publish-AcceptActivity

## 8.1 概述

发布任务模块主要负责用户需求的创建，包括两种类型的任务：跑跑、点点；根据用户设置的任务内容，发布新的任务到任务池。用户可从任务池中接受任务。

-----------------

## 8.2 接口详情

### 8.2.1 任务接口：发布-接受任务
#### 8.2.1.1 `接口：发布任务`
<a name="publishtask"></a>
### publish a task, need task infomation
```
POST /task/Publish/{userId}
```


#### Description
Return task information(success) / wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user id|integer (int64)|
|**FormData**|**TaskInfo**  <br>*required*|Task detailed infomation|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return user privacy info|[Task](#task)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* Task

#### 8.2.1.2 `接口：接受任务`

<a name="receivepp_receivedd"></a>
### accept a task, need userId & taskId !!!API changed!!!
```
GET /task/{userId}/Accepte/{taskId}
```


#### Description
return task detailed infomation (success), only need base infomation / wrong message(fail) !!!This API is replaced by task/ReceivePP/{taskId}/{userId}/ & task/ReceiveDD/{taskId}/{userId}/!!!


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**taskId**  <br>*required*|task id|string|
|**Path**|**userId**  <br>*required*|user id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return DD task list|[Task](#task)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* Task

------------------------

## 8.3 数据结构

### 8.3.1 任务信息（详细）

<a name="task"></a>
### Task
task detailed infomation


|Name|Description|Schema|
|---|---|---|
|**accepterInfo**  <br>*optional*|UserinfosInTask, accepter, PP has a accepter, DD has a accepter list|[accepterInfo](#task-accepterinfo)|
|**beginTime**  <br>*optional*|publish time|string|
|**ddl**  <br>*optional*||string|
|**desc**  <br>*optional*|task description|string|
|**endPosition**  <br>*optional*||string|
|**finishNum**  <br>*optional*|finish task num|integer (int64)|
|**origin**  <br>*optional*|relattive to user? user publish(1), user accept(2), no relation(0)|integer (int64)|
|**publisherInfo**  <br>*optional*|UserinfoInTask, publish|[publisherInfo](#task-publisherinfo)|
|**startPosition**  <br>*optional*||string|
|**status**  <br>*optional*|discription of status|string|
|**taskId**  <br>*optional*|Unique identifier|string|
|**taskStatus**  <br>*optional*|task status, running(true) & finish(false)|boolean|
|**taskType**  <br>*optional*|task type, PP(true) & DD(false)|boolean|
|**time**  <br>*optional*|notice time (send)|string|
|**title**  <br>*optional*||string|
|**totalNum**  <br>*optional*|total task num|integer (int64)|
|**value**  <br>*optional*|task bounce|number|

<a name="task-accepterinfo"></a>
**accepterInfo**

|Name|Schema|
|---|---|
|**schema**  <br>*optional*|[UserInfosInTask](#userinfosintask)|

<a name="task-publisherinfo"></a>
**publisherInfo**

|Name|Schema|
|---|---|
|**schema**  <br>*optional*|[UserInfoInTask](#userinfointask)|

------------------

## 8.4 实现

### 8.4.1 任务发布

 - 确认任务信息的准确性，若任务合法，则向数据库中的相应任务表插入记录。

 - 任务发布后，从账户扣除资金，冻结在平台。

--------------

## 8.5 限制条件

 - 任务金额必须小于等于余额。
 - 任务发布时间关系：
 - 任务结束时间 > 任务开始时间 > 当前时间

---------------------

# 9 TaskActivity
## 9.1 概述

本模块支持查询所有任务。

-----------------

## 9.2 接口详情
### 9.2.1 任务接口：查询
#### 9.2.1.1 接口：`查询所有跑跑任务`
<a name="getpp"></a>
### Get PP task list, need user email
```
GET /task/PP
```


#### Description
return two PP task list (success)/ wrong message(fail), only need base infomation


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user email|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return PP task list|[TasksInList](#tasksinlist)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* Task

#### 9.2.1.2 接口：`查询所有点点任务`

<a name="getdd"></a>
### Get DD task list
```
GET /task/DD
```


#### Description
return DD task list information(success), only need base infomation / wrong message(fail)


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return DD task list|[TasksInList](#tasksinlist)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* Task

#### 9.2.1.3 接口：`根据Id检索任务`

<a name="gettask"></a>
### Get a task detailed information (PP need receipt), need task id !!!API no use!!!
```
GET /task/{taskId}
```


#### Description
return a task (success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**taskId**  <br>*required*|taskId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return task|[Task](#task)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* Task

------------------

## 9.3 算法
### 9.3.1 任务状态
 - 任务有多个状态，这些状态由参与人熟，完成人数以及时间决定的。如果不断地轮询，开销过大，因此在每次查询请求的时候进行状态的更新。

------------------

## 9.4 限制条件
 - 注册用户即可查询。

------------------

# 10 TaskInteracteActivity

## 10.1 概述

本模块支持任务发布者与接收者之间的交互，包括接收者任务完成后上传回执，发布者查看回执，发放奖金。

----------------

## 10.2 接口详情
### 10.2.1 任务接口：任务交互-接收方
#### 10.2.1.1 接口：`上传回执`

<a name="finishpp-finishdd"></a>
### accepter send a receipt to publisher, need a receipt !!!API changed!!!
```
POST /task/{taskId}/SendReceipt/{userId}
```


#### Description
Return receipt (success) / wrong message(fail)  !!!This API is changed by task/FinishPP/{taskId}/{userId}/ & task/FinishDD/{taskId}/{userId}/ , just need photo URL!!!


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**taskId**  <br>*required*|task id|string|
|**Path**|**userId**  <br>*required*|accepter email|string|
|**FormData**|**receipt**  <br>*required*|accepter receipt|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return receipt|[Receipt](#receipt)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* Task

---------------

### 10.2.2 任务接口：任务交互-发布者
#### 10.2.2.1 接口：`查看回执`

<a name="getfeedback"></a>
### Get task receipt, need taskid and accepter email !!!API changed!!!
```
GET /task/{taskId}/Receipt/{userId}
```


#### Description
return a receipt (success)/ wrong message (fail)  !!!This API is changed by task/Feedback/{taskId}/{userId}/, need photoURL, I don't Y!!!


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**taskId**  <br>*required*|taskId|string|
|**Path**|**userId**  <br>*required*|accepter email|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return task|[Receipt](#receipt)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* Task

#### 10.2.2.2 接口：`确认回执`（发放奖励）

<a name="confirmfeedback"></a>
### Confirm a receipt from a accepter, send bounce !!!API changed!!!
```
POST /task/{taskId}/ConfirmReceipt/{userId}
```


#### Description
Return bounce num (success) / wrong message(fail)  !!!This API is changed by task/Confirm/{taskId}/{userId}/{???}/, need photoURL, I don't know Y!!!


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**taskId**  <br>*required*|task id|string|
|**Path**|**userId**  <br>*required*|accepter email|string|
|**FormData**|**receipt**  <br>*required*|accepter receipt|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return bounce num|string|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* Task

-----------

## 10.3 数据结构
### 10.3.1 回执

<a name="receipt"></a>
### Receipt
task Receipt


|Name|Description|Schema|
|---|---|---|
|**desc**  <br>*optional*|receipt description|string|
|**imgURL**  <br>*optional*|receipt URL|string|
|**time**  <br>*optional*|accepter finish time|string|

----------------------

## 10.4 限制条件
 - 只有任务发布者可以查看、确认回执。

-----------------

# 11 PictureActivity
## 11.1 概述

本模块用来上传/下载图片到服务器。

-------------

## 11.2 接口详情
### 11.2.1 照片接口
#### 11.2.1.1 接口：`上传照片`

<a name="uploadpicture"></a>
### Upload a picture, need the picture
```
POST /picture
```


#### Description
Return the picture URL (success)/ wrong message (fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**FormData**|**picture**  <br>*required*|picture, the data stream|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return picture URL|string|
|**403**|Refuse the request, return wrong message|string|


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* Picture

#### 11.2.1.2 接口：`下载照片`

<a name="downloadpicture"></a>
### get picture use URL
```
GET /picture
```


#### Description
Return the picture


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**URL**  <br>*required*|picture URL|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return the picture|string|
|**404**|URL not found, return wrong message|string|
|**414**|URL too long, cann't handle; return wrong meaasge|string|


#### Produces

* `application/json`


#### Tags

* Picture

-----------

## 11.3 算法
### 11.3.1 上传
 - 每张照片存放后返回一个URL，方便下次下载。

----------

## 11.4 限制条件
 - 只有注册用户可以上传。
