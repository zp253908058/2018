# App接口文档

#### Base URL    
* http://171.217.88.215:1111/wms/api/

#### 登陆模块    
1.服务器连接测试    
* URL      
log/test    
* 方法    
GET
* 入参   
无
* 出参    
```
{
   "result": 0,
   "msg": "测试通过!",
   "data": null
 }
```

2.登陆
* URL      
 oauth/token
* 方法    
 POST
* 入参    
   
参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
accountSet  | String | 否       |账套名|  abc
username   | String | 否       |用户名   |  CLL
password   | String | 否       |密码     |  123456

* 出参    
```json
{
   "result": 0,
   "msg": "登陆成功!",
   "data": {
             "access_token":"73c59e9d-ee16-46c0-9045-c099f93113a4",
             "token_type":"bearer",
             "refresh_token":"edf58afb-88ce-435d-8c27-177a85495738",
             "expires_in":42826,
             "scope":"SCOPE_TRUST"
           }
 }
```

3.注销    
* URL      
log/out
* 方法    
 POST
* 入参   
无
* 出参    
```
{
   "result": 0,
   "msg": "退出成功!",
   "data": null
 }
```

4.获取账套
* URL      
log/accountSets
* 方法    
 GET
* 入参   
无
* 出参    
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
      "id": 5,
      "name": "abc"
    },...
  ]
}
```

5.刷新token
* URL      
log/refreshToken
* 方法    
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
refresh_token| String | 否       |refresh_token|  888e79a3-a27f-4e6d-abcf-32d7bbc35a84    
* 出参    
```
{
  "result": 0,
  "msg":"请求成功!",
  "data":"888e79a3-a27f-4e6d-abcf-32d7bbc35a84"
}
```

#### 首页模块    
1.获取首页信息    
* URL      
 home/info
 * 方法    
  POST
* 入参    
   
参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
warehouseId | String | 是       |仓库id|  11

* 出参    
```
{
   "result": 0,
   "msg": "请求成功!",
   "data": {
             "acceptBillCount":1,
             "putawayBillCount":3,
             "reviewBillCount":4,
             "tranferBillCount":5,
           }
 }
```

2.获取仓库
* URL      
home/warehouseList
* 方法    
 GET
* 入参   
无
* 出参    
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
      "id": 5,
      "name": "abc"
    },...
  ]
}
```

#### 验收模块    
1. 获取单位
* URL    
acceptance/unit
* 方法
GET
* 入参
无
* 出参
```
{
    "result": 0,
      "msg": "请求成功!",
      "data": [
        {
          "id": 5,
          "name": "AA公司"
        },...
      ]
}
```

2. 获取订单
* URL    
acceptance/orders/{unitId}
* 方法
GET
* 入参
unitId 单位ID
* 出参
```
{
    "id":"3",
    "name":"CG-2017-02-24-0003"
}
```

3. 获取订单详情
* URL    
acceptance/detail/{orderId}
* 方法
GET
* 入参
orderId 订单ID
* 出参
```
{
  "orderId":"3",
  "status":"10",
  "buyer":"3",
  "buyerId":"陈莉莉",
  "acceptanceOrderList":[{
    "goodsName":"阿莫西林",
    "lotNo":"批号",
    "specification":"规格",
    "validityDate":"有效期",
    "retailPrice":"零售价",
    "amount":"数量",
    "manufacturer":"AAA公司"
  }],
  "onOrderList":[{}]
}
```
4. 一键完成
* URL     
acceptance/allCompete
* 入参
```
{
  "id":"订单id",
  "buyId":"采购员Id"
}
```
* 出参
无




#### 复核模块
1.出库复核
* URL    
recheck/exWarehouseReview/{billNo}
* 方法
 GET
* 入参   
billNo 销售单号
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": {
     "billNo":"CG-2017-02-24-0003",
     "priority":"一级",
     "tempArea":"ABC",
     "customer":"湛江市万邦药业有限公司",
     "status":"1"
     "documentStatus":"已上架",
     "sendRoad":"配送线路",
     "billRemark":"AAAAAAAAAAA",
     "replenishmentOrderCount":"2",
     "wholeQuantity":"10",
     "pxCount":"3",
     "packCount":"5"
    }
```
2.复核完成
* URL    
recheck/completed
* 方法
 POST
* 入参   
```
{
    "billNo":"CG-2017-02-24-0003",
    "reviewerId":"22",
    "diffRemark":"无差异"
}
```
* 出参
无


#### 上架入库模块
1.获取上架单号
* URL    
shelve/orderNoList
* 方法    
 GET
* 入参   
无
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
      {
        "id": 5,
        "name": "abc"
      },...
    ]
}
```

2.获取获取货位
* URL    
shelve/allocationList/{orderNoId}
* 方法    
 GET
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
orderNoId | int     | 否       |订单号id |  5

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
      {
        "id": 5,
        "name": "abc"
      },...
    ]
}
```

3.获取商品
* URL    
shelve/goodsList/{orderNoId}/{allocationId}
* 方法    
 GET
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
orderNoId  | int     | 否       |订单号id |  5
allocationId | int     | 是     |货位id |  3(默认0)

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
      {
        "id": 5,
        "name": "abc"
      },...
    ]
}
```

4.获取商品详情列表
* URL    
shelve/goodsDetailList/{orderNoId}
* 方法    
 GET
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
orderNoId  | int     | 否       |订单号id |  5

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
      {
        "orderNoId": 11111(这个商品所以在的订单id),
        "allocationId": 11111(这个商品所以在的订单下的货位id),
        "goodsId": 11111(这个商品id),
        "goodsDetailId": 11111(这个商品详情id),
        "articleNo": "货号",
        "status": "状态",
        "goodsName": "商品名",
        "lotNo": "批号",
        "specification": "规格",
        "productionDate": "生产日期",
        "unit": "单位",
        "amount": "数量",
        "manufacturer": "厂家"
      },...
    ]
}
```

5.全部上架
* URL    
shelve/all
* 方法    
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
orderNoId  | int     | 否       |订单号id |  5
allocationId | int     | 是     |货位id |  2(默认0)
goodsId     | int     | 是     |商品id |  3(默认0)

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": null
}
```

6.单个上架
* URL    
shelve/single
* 方法    
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
goodsDetailId | int | 否       |商品详情id|  5

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": null
}
```

7.修改
* URL    
shelve/update
* 方法    
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
goodsDetailId | int | 否       |商品详情id|  5

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": null
}
```
#### 单品盘点
1. 获取
* URL
api/productsInventroy/getList/{product}/{location}
* 方法
GET
* 入参
String product // 商品名码
String location //货位码
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": {
    "total":"总数",
    "list":[{
        "id":"ID",
        "goodsName":"商品名",
        "number":"编号",
        "location":"货位",
        "lotNo":"批号",
        "amount":"数量",
        "unit":"单位",
        "validateDate":"有效期",
        "specification":"规格",
        "manufacturer":"厂家",
        "productDate":"生产日期"
        "status":"状态"
    },...]
  }
}
```

2. 盘点确定
* URL
productsInventroy/confirm
* 方法 
POST
* 入参
```
    {
        ids:[2,3,4]
    }
```
* 出参
无
3. 商品修改
* URL 
productsInventroy/update
*方法
POST
* 入参
int id 盘点详情id
int count 盘点数量
* 出参
无 
4. 新增单品查询
* URL
productsInventroy/detail/{goodsCode}
* 方法
GET
* 入参

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
goodsCode  | String | 否      |商品码    |  

* 出参
```
    {
        "result": 0,
          "msg": "请求成功!",
          "data": [
            {
                "pId": 1,
                "goodsName":"某某商品名",
                "number":"编号"
                "number":"某某编号"
                "unit": 瓶,
                "manufacturers":"某某厂家"
            }
          ]
        }
```
10. 新增单品
* URL
productsInventroy/addProduct
* 方法
POST
* 入参

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
pid        | int | 否      |商品id    |  1
lotNo    | String | 否    | 批号| hfsdh4  |
locationCode    | String | 否    | 货位码| 5fsdf6423423  |
amount    | float | 否    | 数量| 56  |
validateDate    | String | 否  | 有效期至| 2017-4-1  |


#### 调拨单
1. 获取list
* URL
transfer/list/{billNo}/{goodsName}/{s_inid}/{s_outid}/{sa_outid}/{sa_outid}/{l_inid}/{l_outid}
* 方法
GET
* 入参

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
billNo        | String | 是      |调拨单编号    |  ddffsg1
goodsName    | String | 是    | 商品名| 某某胶囊  |
s_inid    | int | 是    | 调入仓库id| 56  |
s_outid    | int | 是    | 调出仓库id| 56  |
sa_inid    | int | 是  | 调出库区id| 24 |
sa_outid    | int | 是  | 调出库区id| 25  |
l_inid    | int | 是  | 调入区域id| 26 |
l_outid    | int | 是  | 调出区域id| 34 |

* 出参
```
{
        "result": 0,
          "msg": "请求成功!",
          "data": [
            {
                "id": 1,
                "goodsName":"商品名"
                "lotNo": "批号",
                "specification":"规格"
                "manufacturer":"某某厂家"
                "unit":"单位"
                "amount":"数量"
                "validateDate":"有效期"
                "productDate":"生产日期"
            }...
          ]
        }
```



#### 门店初盘
1.完成
* URL    
shopFirst/complete
* 方法
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
ids      | List<Integer> | 否   | id数组|  {"ids":[3,4,5]}

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```

2.新增
* URL    
shopFirst/add
* 方法
 POST
* 入参   

``
{
    "id":"id",
    [
       {
            "lotNo":"批号",
            "validateDate":"有效期",
            "count":"数量 float 类型"
        }
    ]
}
``


* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```

3.单个完成
* URL    
shopFirst/single
* 方法
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
goodsDetailId | int | 否       |商品详情id|  5

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```
4. 获取详细列表
* URL 
shopFirst/getList/{pdType}/{saId}/{areaId}
* 方法
GET
* 入参

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
pdType | string | 否       |盘点类型|  月度
saId   | int   | 否 | 库区id   | 3
areaId  | int | 否  | 区域ID  | 3

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "goodsName":"商品名",
        "location":"货位",
        "inventoryCount":"盘点数量",
        "countInBill":"账面数量",
        "unit":"单位盒",
        "manufacturer":"厂家",
        "status":"状态 0/1 未盘点/已盘点",
    }
  ]
}
```

5. 获取库区
* URL
shopFirst/saList
* 方法
GET
* 入参
无
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "name":"某某库区"
    }
  ]
}
```
6. 获取区域
* URL
shopFirst/areaList
* 方法
GET
* 入参
无
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "name":"某某区域"
    }
  ]
}
```

#### 仓库初盘
1.完成
* URL    
warehouseFirst/complete
* 方法
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
ids      | List<Integer> | 否   | id数组|  {"ids":[3,4,5]}


* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```

2.新增
* URL    
warehouseFirst/add
* 方法
 POST
* 入参   

``
{
    "id":"id",
    [
       {
            "lotNo":"批号",
            "validateDate":"有效期",
            "count":"数量 float 类型"
        }
    ]
}
``

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```

3.单个完成
* URL    
warehouseFirst/single
* 方法
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
goodsDetailId | int | 否       |商品详情id|  5

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```
4. 获取详情列表
* URL 
warehouseFirst/getList/{pdType}/{saId}/{areaId}
* 方法
GET
* 入参
参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
pdType | string | 否       |盘点类型|  月度
saId   | int   | 否 | 库区id   | 3
areaId  | int | 否  | 区域ID  | 3
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "goodsName":"商品名",
        "location":"货位",
        "lotNo":"批号",
        "inventoryCount":"盘点数量",
        "specification":"规格",
        "status":"状态 0/1 未盘点/已盘点",
    }
  ]
}
```
5. 获取库区
* URL
warehouseFirst/saList
* 方法
GET
* 入参
无
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "name":"某某库区"
    }
  ]
}
```
6. 获取区域
* URL
warehouseFirst/areaList
* 方法
GET
* 入参
无
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "name":"某某区域"
    }
  ]
}
```

#### 复盘
1.完成
* URL    
secondInventory/complete
* 方法
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
ids      | List<Integer> | 否   | id数组|  {"ids":[3,4,5]}


* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```

2.新增
* URL    
secondInventory/add
* 方法
 POST
* 入参   

``
{
    "id":"id",
    [
       {
            "lotNo":"批号",
            "validateDate":"有效期",
            "count":"数量 float 类型"
        }
    ]
}
``

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```

3.单个完成
* URL    
secondInventory/single
* 方法
 POST
* 入参   

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
goodsDetailId | int | 否       |商品详情id|  5

* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": ””
}
```
4. 获取详情列表
* URL 
secondInventory/getList/{pdType}/{saId}/{areaId}
* 方法
GET
* 入参
参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
pdType | string | 否       |盘点类型|  月度
saId   | int   | 否 | 库区id   | 3
areaId  | int | 否  | 区域ID  | 3
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "goodsName":"商品名",
        "location":"货位",
        "lotNo":"批号",
        "inventoryCount":"盘点数量",
        "specification":"规格",
        "status":"状态 0/1 未盘点/已盘点",
    }
  ]
}
```
5. 获取库区
* URL
secondInventory/saList
* 方法
GET
* 入参
无
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "name":"某某库区"
    }
  ]
}
```
6. 获取区域
* URL
secondInventory/areaList
* 方法
GET
* 入参
无
* 出参
```
{
  "result": 0,
  "msg": "请求成功!",
  "data": [
    {
        "id":"3",
        "name":"某某区域"
    }
  ]
}
```
#### 单据查询
1. 单据查询
* URL 
home/doucmentList/{type}
* 方法
GET
* 入参

参数名      | 类型   | 是否可空 | 参数说明 | 样例
-----------|--------|---------|---------|-----
type | int | 否       |0:全部,1:验收单,2:上架单,3:调拨单,4:复核单|  3

* 出参
```
{
    "result": 0,
      "msg": "请求成功!",
      "data": 
      [{
        "id":"单据id"
        "status":"状态"
        "documentNo":"单号"
        "documentDate":"单据日期"
      }...]
}
```