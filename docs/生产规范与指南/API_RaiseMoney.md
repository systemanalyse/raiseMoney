# RaiseMoney


<a name="overview"></a>
## Overview
This is a RESTful API Raise-Money written in Go(Maybe it will be JS, but... what ever). And you can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).


### Version information
*Version* : 1.0.0


### License information
*License* : Apache 2.0  
*License URL* : http://www.apache.org/licenses/LICENSE-2.0.html  
*Terms of service* : null


### URI scheme
*Host* : 118.89.20.188  
*Schemes* : HTTPS


### Tags

* Task : The task including PP and DD
* User : The user of RM system, can acccept/publish mission.




<a name="paths"></a>
## Paths

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
|**200**|Successful operation, return DD task list|[Tasks](#tasks)|
|**400**|Wrong format, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* Task


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
|**200**|Successful operation, return user privacy info|[TasksInList](#tasksinlist)|
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


<a name="getsetting"></a>
### Get overview of setting
```
GET /user/{userId}/Settings/Overview
```


#### Description
Get the user current overview(success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return User setting overview, it should be an interger|[Response 200](#getsetting-response-200)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|

<a name="getsetting-response-200"></a>
**Response 200**

|Name|Description|Schema|
|---|---|---|
|**certification**  <br>*optional*|certification or not|boolean|
|**profile**  <br>*optional*|as "80%"|string|


#### Produces

* `application/json`


#### Tags

* User


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
|**200**|Successful operation, return task list|[Tasks](#tasks)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User


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
|**200**|Successful operation, return task list|[Tasks](#tasks)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User


<a name="getusertask"></a>
### Get running task list of user Publish & publish !!!API no use!!!
```
GET /user/{userId}/tasks/Running
```


#### Description
return task list(success)/ wrong message(fail)


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**userId**  <br>*required*|user email|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Successful operation, return task list|[Tasks](#tasks)|
|**400**|Wrong format, return wrong message|string|
|**401**|Unauthorized, need to authorized, return wrong message|string|
|**403**|request refused, return wrong message|string|
|**404**|User not found, return wrong message|string|


#### Produces

* `application/json`


#### Tags

* User




<a name="definitions"></a>
## Definitions

<a name="jin"></a>
### Jin
Jin coin

*Type* : number


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


<a name="receipt"></a>
### Receipt
task Receipt


|Name|Description|Schema|
|---|---|---|
|**desc**  <br>*optional*|receipt description|string|
|**imgURL**  <br>*optional*|receipt URL|string|
|**time**  <br>*optional*|accepter finish time|string|


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


<a name="taskinlist"></a>
### TaskInList
task base infomation, only visible in list


|Name|Description|Schema|
|---|---|---|
|**beginTime**  <br>*optional*|publish time|string|
|**origin**  <br>*optional*|relattive to user? user publish(1), user accept(2), no relation(0)|integer (int64)|
|**publisherInfo**  <br>*optional*|UserinfoInTask, publish|[UserInfoInTask](#userinfointask)|
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


<a name="tasks"></a>
### Tasks
*Type* : < [Task](#task) > array


<a name="tasksinlist"></a>
### TasksInList
*Type* : < [TaskInList](#taskinlist) > array


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


<a name="userinfosintask"></a>
### UserInfosInTask
*Type* : < [UserInfoInTask](#userinfointask) > array





