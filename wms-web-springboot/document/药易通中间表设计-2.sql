--目标是9系，8系，器械PDA程序通用，但各版本之间字段可能不通用，故建中间表
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_timestamp]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_timestamp (
p_timestamp BIGINT NOT NULL, --商品资料上次取数时最大的timestamp
e_timestamp BIGINT NOT NULL, --职员
s_timestamp BIGINT NOT NULL, --仓库
sa_timestamp BIGINT NOT NULL, --库区
sc_timestamp BIGINT NOT NULL, --区域
l_timestamp BIGINT NOT NULL, --库位
c_timestamp BIGINT NOT NULL --往来单位
) ON [PRIMARY]
END
GO

IF NOT EXISTS ( SELECT 1 FROM pda_timestamp)
BEGIN
INSERT INTO pda_timestamp
(p_timestamp, e_timestamp, s_timestamp, sa_timestamp, sc_timestamp, l_timestamp, c_timestamp)
SELECT
  0,
  0,
  0,
  0,
  0,
  0,
  0
    END
  --商品资料
-- drop table pda_Products
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_Products]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_Products (
p_id INT NOT NULL DEFAULT 0, --商品id
serial_number VARCHAR (26) NOT NULL DEFAULT '', --编码
pinyin VARCHAR (80) NOT NULL DEFAULT '', --拼音
NAME VARCHAR (80) NOT NULL DEFAULT '', --商品名称
alias VARCHAR (80) NOT NULL DEFAULT '', --通用名
standard VARCHAR (100) NOT NULL DEFAULT '', --规格
medtype VARCHAR (50) NOT NULL DEFAULT '', --剂型
permitcode VARCHAR (50) NOT NULL DEFAULT '', --批准文号
PerCodevalid DATETIME NOT NULL DEFAULT 0, --批准文号有效期
unit1Name VARCHAR (10) NOT NULL DEFAULT '', --基本单位
WholeUnitName VARCHAR (10) NOT NULL DEFAULT '', --整货单位
WholeRate NUMERIC (18, 4) NOT NULL DEFAULT '', --整货件包装数 0531
BulidNo VARCHAR (500) NOT NULL DEFAULT '', --生产许可证
RegisterNo VARCHAR (500) NOT NULL DEFAULT '', --注册证号
Registervalid DATETIME NOT NULL DEFAULT 0, --注册证号有效期
GMPNo VARCHAR (120) NOT NULL DEFAULT '', --GMP
GMPvaliddate DATETIME NOT NULL DEFAULT 0, --GMP有效期
Factory VARCHAR (80) NOT NULL DEFAULT '', --生产厂家
StorageCon VARCHAR (60) NOT NULL DEFAULT '', --温度条件
validmonth SMALLINT NOT NULL DEFAULT 0, --有效期月
validday SMALLINT NOT NULL DEFAULT 0, --有效期天
makearea VARCHAR (60) NOT NULL DEFAULT '', --产地
PackStd VARCHAR (60) NOT NULL DEFAULT '', --装箱规格
pack VARCHAR (100) NOT NULL DEFAULT '', --包装规格
locationid INT NOT NULL DEFAULT 0, --默认货位, =pda_location.l_id
WholeLoc INT NOT NULL DEFAULT 0, --默认整货货位, =pda_location.l_id
SingleLoc INT NOT NULL DEFAULT 0, --默认零货货位, =pda_location.l_id
Supplier_id INT NOT NULL DEFAULT 0, --供应商, =pda_clients.c_id
ZT INT NOT NULL DEFAULT 0, --状态，默认0 ，读取后 1， 读取出错 -1  0531
barcode VARCHAR (60) NOT NULL DEFAULT ''  --条码 取 barcode 中的barcode 0810
) ON [PRIMARY]
END
GO



--职员资料
-- drop table pda_employees
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_employees]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_employees (
e_id INT NOT NULL DEFAULT 0, --职员id
serial_number VARCHAR (26) NOT NULL DEFAULT '', --编码
password VARCHAR (30) NULL DEFAULT '', --密码
pinyin VARCHAR (80) NOT NULL DEFAULT '', --拼音
name VARCHAR (80) NOT NULL DEFAULT '', --名称
alias VARCHAR (30) NOT NULL DEFAULT '', --简名
phone VARCHAR (60) NOT NULL DEFAULT '', --联系电话
address VARCHAR (66) NOT NULL DEFAULT '', --联系地址
ZT INT NOT NULL DEFAULT 0, --状态，默认0 ，读取后 1， 读取出错 -1  0531
) ON [PRIMARY]
END
GO


--仓库资料
-- drop table pda_storages
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_storages]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_storages (
s_id INT NOT NULL DEFAULT 0, --仓库id
serial_number VARCHAR (26) NOT NULL DEFAULT '', --编码
pinyin VARCHAR (80) NOT NULL DEFAULT '', --拼音
NAME VARCHAR (80) NOT NULL DEFAULT '', --名称
alias VARCHAR (30) NOT NULL DEFAULT '', --简名
WholeFlag INT NOT NULL DEFAULT 0, --整零库类型, 0/-1未选择1整货库2零货库3零售拆零库
storeCondition INT NOT NULL DEFAULT 0, --温度条件, 0常温1阴冷2冷链
qualityFlag INT NOT NULL DEFAULT 0, --质量类型, 0合格库1不合格库2待退厂库
ZT INT NOT NULL DEFAULT 0, --状态，默认0 ，读取后 1， 读取出错 -1  0531
) ON [PRIMARY]
END
GO


--库区资料
-- drop table pda_stockArea
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_stockArea]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_stockArea (
sa_id INT NOT NULL DEFAULT 0, --库区id
s_id INT NOT NULL DEFAULT 0, --仓库id
serial_number VARCHAR (60) NOT NULL DEFAULT '', --编码
WholeFlag INT NOT NULL DEFAULT 0, --整零库类型, 0/-1未选择1整货库2零货库3零售拆零库  0531
storeCondition INT NOT NULL DEFAULT 0, --温度条件, 0常温1阴冷2冷链    0531
qualityFlag INT NOT NULL DEFAULT 0, --质量类型, 0合格库1不合格库2待退厂库  0531
NAME VARCHAR (60) NOT NULL DEFAULT '', --名称
ZT INT NOT NULL DEFAULT 0, --状态，默认0 ，读取后 1， 读取出错 -1  0531
) ON [PRIMARY]
END
GO


--区域资料
-- drop table pda_Area
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_Area]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_Area (
sc_id INT NOT NULL DEFAULT 0, --区域id
s_id INT NOT NULL DEFAULT 0, --仓库id
serial_number VARCHAR (60) NOT NULL DEFAULT '', --编码
NAME VARCHAR (60) NOT NULL DEFAULT '', --名称
ZT INT NOT NULL DEFAULT 0, --状态，默认0 ，读取后 1， 读取出错 -1  0531
) ON [PRIMARY]
END
GO


--货位资料
-- drop table pda_location
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_location]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_location (
l_id INT NOT NULL DEFAULT 0, --货位id
s_id INT NOT NULL DEFAULT 0, --仓库id
loc_code VARCHAR (30) NOT NULL DEFAULT '', --编码
loc_name VARCHAR (30) NOT NULL DEFAULT '', --名称
sa_id INT NOT NULL DEFAULT 0, --库区id, =pda_stockArea.sa_id
sc_id INT NOT NULL DEFAULT 0, --区域id, =pda_Area.sc_id
ShelfName VARCHAR (50) NOT NULL DEFAULT '', --货架
roadwayCode VARCHAR (400) NOT NULL DEFAULT '', --过道
ZT INT NOT NULL DEFAULT 0, --状态，默认0 ，读取后 1， 读取出错 -1  0531
) ON [PRIMARY]
END
GO


--往来单位资料
-- drop table pda_clients
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_clients]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_clients (
c_id INT NOT NULL DEFAULT 0, --往来单位id
serial_number VARCHAR (26) NOT NULL DEFAULT '', --编码
pinyin VARCHAR (80) NOT NULL DEFAULT '', --拼音
NAME VARCHAR (80) NOT NULL DEFAULT '', --名称
alias VARCHAR (30) NOT NULL DEFAULT '', --简名
csflag CHAR (1) NOT NULL DEFAULT '', --单位类型 0客户1供应商2两者皆是
contact_personal VARCHAR (20) NOT NULL DEFAULT '', --联系人
phone_number VARCHAR (100) NOT NULL DEFAULT '', --联系电话
address VARCHAR (128) NOT NULL DEFAULT '', --联系地址
RoadName VARCHAR (30) NOT NULL DEFAULT '', --路线
ZT INT NOT NULL DEFAULT 0, --状态，默认0 ，读取后 1， 读取出错 -1  0531
) ON [PRIMARY]
END
GO

--上架确认单
-- drop table pda_PutOnBill
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_PutOnBill]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_PutOnBill(
billid INT NOT NULL DEFAULT 0, --上架确认单id
billnumber VARCHAR (30) NOT NULL DEFAULT '', --销售订单单号
e_id INT NOT NULL DEFAULT 0, --经手人id, =pda_employees.e_id
billstates INT NOT NULL DEFAULT 10, --上架确认单状态 10未处理 13已审核
pdastates INT NOT NULL DEFAULT 0, --交换状态 0提供 1pda已读取 2pda已回写
pdaInTime DATETIME NOT NULL DEFAULT 0, --提供时间
pdaReTime DATETIME NOT NULL DEFAULT 0, --读取时间
pdaWrTime DATETIME NOT NULL DEFAULT 0, --回写时间
) ON [PRIMARY]
END
GO


--上架确认单, pda回写时需要新增数据，不能修改提供的数据，同一批次上多个仓库或货位时，pda复制数据后只能修改仓库id及货位id\上架数量
-- drop table pda_PutOnBill_D
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_PutOnBill_D]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_PutOnBill_D(
smb_id INT NOT NULL DEFAULT 0, --上架确认单明细表id
bill_id INT NOT NULL DEFAULT 0, --上架确认单id, =pda_PutOnBill.billid
p_id INT NOT NULL DEFAULT 0, --商品id, =pda_Products.p_id
MakeDate DATETIME NOT NULL DEFAULT 0, --生产日期
Validdate DATETIME NOT NULL DEFAULT 0, --效期
Batchno VARCHAR (20) NOT NULL DEFAULT '', --批号
EligibleQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --上架数量
TaxPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --含税单价
TaxTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --含税金额
CostPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本单价
CostTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本金额
S_id INT NOT NULL DEFAULT 0, --仓库id, =pda_storages.s_id
Location_id INT NOT NULL DEFAULT 0, --货位id, =pda_location.l_id
Supplier_id INT NOT NULL DEFAULT 0, --供应商id, =pda_PutOnBill.c_id
InstoreTime DATETIME NOT NULL DEFAULT 0, --入库时间
LineSort INT NOT NULL DEFAULT 0, --原始行号，复制行时，需要保留该数值不变，用于关联ERP原始明细 0531, 复制的行smb_id=0
DealStates INT NOT NULL DEFAULT 0, --处理状态，0 未处理, 1 PDA已经处理（上架） 0531
pdastates INT NOT NULL DEFAULT 0, --状态 0提供数据 1pda回写数据
) ON [PRIMARY]
END
GO



--销售订单, 生成复核单时写入数据
-- drop table pda_CheckBill
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_CheckBill]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_CheckBill(
billid INT NOT NULL DEFAULT 0, --销售订单id
billnumber VARCHAR (30) NOT NULL DEFAULT '', --销售订单单号
FirstStates VARCHAR (10) NOT NULL DEFAULT '', --优先级
c_id INT NOT NULL DEFAULT 0, --客户id, =pda_clients.c_id
TempStore VARCHAR (30) NOT NULL DEFAULT '', --暂存区--拣货单上的
billstates INT NOT NULL DEFAULT 10, --PDA回写状态, 回写至药易通销售订单上
pdastates INT NOT NULL DEFAULT 0, --交换状态 0提供 1pda已读取 2pda已回写
pdaInTime DATETIME NOT NULL DEFAULT 0, --提供时间
pdaReTime DATETIME NOT NULL DEFAULT 0, --读取时间
pdaWrTime DATETIME NOT NULL DEFAULT 0, --回写时间
remark VARCHAR (200) NOT NULL DEFAULT '', --备注 0803
diff_remark VARCHAR (200) NOT NULL DEFAULT '', --差异备注0803
receiver INT
) ON [PRIMARY]
END
GO

--复核单条码表
-- drop table pda_CheckBill_B
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_CheckBill_B]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_CheckBill_B(
smb_id INT NOT NULL DEFAULT 0, --复核单条码表id
bill_id INT NOT NULL DEFAULT 0, --复核单id, =pda_CheckBill.billid
PickType INT NOT NULL DEFAULT 0, --1整件 2拼箱 3打包
barcode VARCHAR (50) NOT NULL DEFAULT '', --条码
EligibleQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --数量
PickNo VARCHAR (30) NOT NULL DEFAULT '', --拣货单号
pickemp_id INT NOT NULL DEFAULT 0, --拣货人id, =pda_employees.e_id
checkemp_id INT NOT NULL DEFAULT 0, --复核人id, =pda_employees.e_id
DealStates INT NOT NULL DEFAULT 0, --处理状态，0 未处理, 1 PDA已扫到码 0531
pdastates INT NOT NULL DEFAULT 0, --交换状态 0提供 1pda已回写
) ON [PRIMARY]
END
GO




--配送抵达
-- drop table pda_SendBill
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_SendBill]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_SendBill(
billid INT NOT NULL DEFAULT 0, --配送单id
Gspbillid INT NOT NULL DEFAULT 0, --业务单据id
billnumber VARCHAR (30) NOT NULL DEFAULT '', --销售订单单号
sendnumber VARCHAR (30) NOT NULL DEFAULT '', --物流单号
FirstStates VARCHAR (10) NOT NULL DEFAULT '', --优先级
c_id INT NOT NULL DEFAULT 0, --客户id, =pda_clients.c_id
e_id INT NOT NULL DEFAULT 0, --业务员id, =pda_employees.c_id
contact_personal VARCHAR (20) NOT NULL DEFAULT '', --联系人
phone_number VARCHAR (100) NOT NULL DEFAULT '', --联系电话
sendemp VARCHAR (30) NOT NULL DEFAULT 0, --送货人  0531 更改
sendemp_phone VARCHAR (100) NOT NULL DEFAULT '', --送货人电话
recemp VARCHAR (30) NOT NULL DEFAULT 0, --收货人   0531 更改
recemp_phone VARCHAR (100) NOT NULL DEFAULT '', --收货人电话
Address VARCHAR (150) NOT NULL DEFAULT '', --地址  0531
ArTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --应收款  0531
Note VARCHAR (200) NOT NULL DEFAULT '', --单据备注
WholeQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --整货数量
PartQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --拼臬数量
PackQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --打包数量
wddetail VARCHAR (50) NOT NULL DEFAULT '', --温度
Returndetail VARCHAR (100) NOT NULL DEFAULT '', --回执
pdaOutTime DATETIME NOT NULL DEFAULT 0, --业务员交接完成的时间, 配送单审核时间
isSpecial INT NOT NULL DEFAULT 0, --是否含特殊药品 0不含 1包含
isCold INT NOT NULL DEFAULT 0, --是否含冷链药品 0不含 1包含
isRX INT NOT NULL DEFAULT 0, --是否含处方药 0不含 1包含
pdastates INT NOT NULL DEFAULT 0, --交换状态 0提供 1pda已读取 2pda已回写 3药易通已回写
pdaInTime DATETIME NOT NULL DEFAULT 0, --提供时间
pdaReTime DATETIME NOT NULL DEFAULT 0, --读取时间
pdaWrTime DATETIME NOT NULL DEFAULT 0--回写时间
) ON [PRIMARY]
END
GO

-- 动态盘点单
-- drop table pda_pdBill
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_pdBill]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_pdBill(
billid INT NOT NULL DEFAULT 0, --盘点单id
sa_id INT NOT NULL DEFAULT 0, --库区id   0808
pdname VARCHAR (50) NOT NULL DEFAULT '', --盘点名称
s_id INT NOT NULL DEFAULT 0, --仓库id, =pda_storages.s_id
billstates INT NOT NULL DEFAULT 10, --盘单状态 1：门店初盘 2：复盘  4:仓库初盘
pdastates INT NOT NULL DEFAULT 0, --交换状态 0提供 1pda已读取,2pda已完成,3pda已回写
pdaInTime DATETIME NOT NULL DEFAULT 0, --提供时间
pdaReTime DATETIME NOT NULL DEFAULT 0, --读取时间
pdaWrTime DATETIME NOT NULL DEFAULT 0--回写时间
) ON [PRIMARY]
END
GO
-- 2017/10/12
-- 修改pda_pdBill billstates不可改变(去除未完成状态0和完成状态3), pdastates完成变为2, 已回写改为3


--动态盘点明细表
-- drop table pda_pdBill_D
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_pdBill_D]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_pdBill_D(
smb_id INT NOT NULL DEFAULT 0, --盘点单明细表id0    0606
bill_id INT NOT NULL DEFAULT 0, --盘点单id, =pda_pdBill.billid
p_id INT NOT NULL DEFAULT 0, --商品id, =pda_Products.p_id
MakeDate DATETIME NOT NULL DEFAULT 0, --生产日期
Validdate DATETIME NOT NULL DEFAULT 0, --效期
Batchno VARCHAR (20) NOT NULL DEFAULT '', --批号
EligibleQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --库存数量
pdQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --实盘数量
S_id INT NOT NULL DEFAULT 0, --仓库id, =pda_storages.s_id
Location_id INT NOT NULL DEFAULT 0, --货位id, =pda_location.l_id
Supplier_id INT NOT NULL DEFAULT 0, --供应商id, =pda_PutOnBill.c_id
InstoreTime DATETIME NOT NULL DEFAULT 0, --入库时间
billstates INT NOT NULL DEFAULT 10, --盘单状态 1：初盘 2：复盘
DealStates INT NOT NULL DEFAULT 0, --处理状态，0 未处理, 1 PDA已盘点 0531
pdastates INT NOT NULL DEFAULT 0, --状态 0提供数据 1pda回写数据
) ON [PRIMARY]
END
GO

--收货单
-- drop table pda_RecBill
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_RecBill]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_RecBill(
billid INT NOT NULL DEFAULT 0, --收货单id
billnumber VARCHAR (30) NOT NULL DEFAULT '', --采购订单单号
c_id INT NOT NULL DEFAULT 0, --往来单位id, =pda_clients.c_id
e_id INT NOT NULL DEFAULT 0, --经手人id, =pda_employees.e_id
billstates INT NOT NULL DEFAULT 10, --状态 10未处理 13已审核
pdastates INT NOT NULL DEFAULT 0, --交换状态 0提供 1pda已读取  2pda已回写
pdaInTime DATETIME NOT NULL DEFAULT 0, --提供时间
pdaReTime DATETIME NOT NULL DEFAULT 0, --读取时间
pdaWrTime DATETIME NOT NULL DEFAULT 0, --回写时间
) ON [PRIMARY]
END
GO


--收货单, pda回写时只能修改收货数量、生产日期、批号、效期
-- drop table pda_RecBill_D
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_RecBill_D]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_RecBill_D(
smb_id INT NOT NULL DEFAULT 0, --收货单明细表id
bill_id INT NOT NULL DEFAULT 0, --收货单id, =pda_RecBill.billid
p_id INT NOT NULL DEFAULT 0, --商品id, =pda_Products.p_id
MakeDate DATETIME NOT NULL DEFAULT 0, --生产日期
Validdate DATETIME NOT NULL DEFAULT 0, --效期
Batchno VARCHAR (20) NOT NULL DEFAULT '', --批号
Yqty NUMERIC (18, 4) NOT NULL DEFAULT 0, --订单数量
EligibleQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --收货数量
TaxPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --含税单价
TaxTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --含税金额
CostPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本单价
CostTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本金额
S_id INT NOT NULL DEFAULT 0, --仓库id, =pda_storages.s_id
Location_id INT NOT NULL DEFAULT 0, --货位id, =pda_location.l_id
Supplier_id INT NOT NULL DEFAULT 0, --供应商id, =pda_clients.c_id
DealStates INT NOT NULL DEFAULT 0, --处理状态，0 未处理, 1 已经处理
pdastates INT NOT NULL DEFAULT 0, --状态 0提供数据 1pda回写数据
) ON [PRIMARY]
END
GO

--调拨单
-- drop table pda_TranBill
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_TranBill]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_TranBill(
billid INT NOT NULL DEFAULT 0, --调拨单id
billnumber VARCHAR (30) NOT NULL DEFAULT '', --调拨单单号
e_id INT NOT NULL DEFAULT 0, --经手人id, =pda_employees.e_id
sout_id INT NOT NULL DEFAULT 0, --调出仓库id, =pda_storages.s_id
sin_id INT NOT NULL DEFAULT 0, --调入仓库id, =pda_storages.s_id
sa_inid INT NOT NULL DEFAULT 0, --调入库区id   0808
sa_outid INT NOT NULL DEFAULT 0, --调出库区id   0808
billstates INT NOT NULL DEFAULT 10, --状态 2未处理 3已审核
pdastates INT NOT NULL DEFAULT 0, --交换状态 0提供 1pda已读取 2pda已回写
pdaInTime DATETIME NOT NULL DEFAULT 0, --提供时间
pdaReTime DATETIME NOT NULL DEFAULT 0, --读取时间
pdaWrTime DATETIME NOT NULL DEFAULT 0, --回写时间
) ON [PRIMARY]
END
GO


--调拨单, pda回写时只能修改调入仓库、调入货位
-- drop table pda_TranBill_D
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_TranBill_D]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_TranBill_D(
smb_id INT NOT NULL DEFAULT 0, --调拨单明细表id
bill_id INT NOT NULL DEFAULT 0, --调拨单id, =pda_TranBill.billid
p_id INT NOT NULL DEFAULT 0, --商品id, =pda_Products.p_id
MakeDate DATETIME NOT NULL DEFAULT 0, --生产日期
Validdate DATETIME NOT NULL DEFAULT 0, --效期
Batchno VARCHAR (20) NOT NULL DEFAULT '', --批号
quantity NUMERIC (18, 4) NOT NULL DEFAULT 0, --数量
TaxPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --含税单价
TaxTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --含税金额
CostPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本单价
CostTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本金额
ss_id INT NOT NULL DEFAULT 0, --调出仓库id, =pda_storages.s_id
sd_id INT NOT NULL DEFAULT 0, --调入仓库id, =pda_storages.s_id
Location_id INT NOT NULL DEFAULT 0, --调出货位id, =pda_location.l_id
location_id2 INT NOT NULL DEFAULT 0, --调入货位id, =pda_location.l_id
Supplier_id INT NOT NULL DEFAULT 0, --供应商id, =pda_clients.c_id
DealStates INT NOT NULL DEFAULT 0, --处理状态，0 未处理, 1 已经处理
pdastates INT NOT NULL DEFAULT 0, --状态 0提供数据 1pda回写数据
) ON [PRIMARY]
END
GO

--库存盘点单, pda回写时只能修改盘点数量
-- drop table pda_kcpdBill_D
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_kcpdBill_D]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_kcpdBill_D(
storehouse_id INT NOT NULL DEFAULT 0, --库存表id
p_id INT NOT NULL DEFAULT 0, --商品id, =pda_Products.p_id
MakeDate DATETIME NOT NULL DEFAULT 0, --生产日期
Validdate DATETIME NOT NULL DEFAULT 0, --效期
Batchno VARCHAR (20) NOT NULL DEFAULT '', --批号
quantity NUMERIC (18, 4) NOT NULL DEFAULT 0, --库存数量
pdqty NUMERIC (18, 4) NOT NULL DEFAULT 0, --盘点数量
CostPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本单价
CostTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本金额
ss_id INT NOT NULL DEFAULT 0, --仓库id, =pda_storages.s_id
Location_id INT NOT NULL DEFAULT 0, --货位id, =pda_location.l_id
Supplier_id INT NOT NULL DEFAULT 0, --供应商id, =pda_clients.c_id
DealStates INT NOT NULL DEFAULT 0, --处理状态，0 未处理, 1 已经处理
pdastates INT NOT NULL DEFAULT 0, --状态 0提供数据 1pda回写数据
) ON [PRIMARY]
END
GO


--门店收货单  0815
-- drop table pda_mdRecBill
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_mdRecBill]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_mdRecBill(
billid INT NOT NULL DEFAULT 0, --收货单id
billnumber VARCHAR (30) NOT NULL DEFAULT '', --采购订单单号
c_id INT NOT NULL DEFAULT 0, --往来单位id, =pda_clients.c_id
e_id INT NOT NULL DEFAULT 0, --经手人id, =pda_employees.e_id
billstates INT NOT NULL DEFAULT 10, --状态 10未处理 13已审核
pdastates INT NOT NULL DEFAULT 0, --交换状态 0提供 1pda已读取 2pda已回写
pdaInTime DATETIME NOT NULL DEFAULT 0, --提供时间
pdaReTime DATETIME NOT NULL DEFAULT 0, --读取时间
pdaWrTime DATETIME NOT NULL DEFAULT 0, --回写时间
) ON [PRIMARY]
END
GO


--收货单, pda回写时只能修改收货数量、生产日期、批号、效期 0815
-- drop table pda_mdRecBill_D
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_mdRecBill_D]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_mdRecBill_D(
smb_id INT NOT NULL DEFAULT 0, --收货单明细表id
bill_id INT NOT NULL DEFAULT 0, --收货单id, =pda_RecBill.billid
p_id INT NOT NULL DEFAULT 0, --商品id, =pda_Products.p_id
MakeDate DATETIME NOT NULL DEFAULT 0, --生产日期
Validdate DATETIME NOT NULL DEFAULT 0, --效期
Batchno VARCHAR (20) NOT NULL DEFAULT '', --批号
Yqty NUMERIC (18, 4) NOT NULL DEFAULT 0, --配货数量
EligibleQty NUMERIC (18, 4) NOT NULL DEFAULT 0, --收货数量
TaxPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --含税单价
TaxTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --含税金额
CostPrice NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本单价
CostTotal NUMERIC (18, 4) NOT NULL DEFAULT 0, --成本金额
S_id INT NOT NULL DEFAULT 0, --仓库id, =pda_storages.s_id
Location_id INT NOT NULL DEFAULT 0, --货位id, =pda_location.l_id
Supplier_id INT NOT NULL DEFAULT 0, --供应商id, =pda_clients.c_id
DealStates INT NOT NULL DEFAULT 0, --处理状态，0 未处理, 1 已经处理
pdastates INT NOT NULL DEFAULT 0, --状态 0提供数据 1pda回写数据
) ON [PRIMARY]
END
GO


--货位跟踪表
-- drop table pda_locationTrace
IF NOT EXISTS ( SELECT 1 FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[pda_locationTrace]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
BEGIN
CREATE TABLE [dbo].pda_locationTrace (
lt_id INT NOT NULL DEFAULT 0, --唯一id=locationTrace.lt_id
p_id INT NOT NULL DEFAULT 0, --商品id, =pda_Products.p_id
S_id INT NOT NULL DEFAULT 0, --仓库id, =pda_storages.s_id
Location_id INT NOT NULL DEFAULT 0, --货位id, =pda_location.l_id
rectime DATETIME NOT NULL DEFAULT 0, --日期
billtype INT NOT NULL DEFAULT 0, --单据类型
Y_ID INT NOT NULL DEFAULT 0, --分支机构id
ZT INT NOT NULL DEFAULT 0, --状态，默认0 ，读取后 1， 读取出错 -1  0531
CONSTRAINT [PK_pda_locationTrace] PRIMARY KEY CLUSTERED
(
lt_id ASC
)
) ON [PRIMARY]
END
GO


--条码采集表
-- drop table pda_Products
IF not exists (select 1 from dbo.sysobjects where id = object_id(N'[dbo].[pda_PBarcode]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
  begin
    CREATE TABLE [dbo].pda_PBarcode (
      id INT IDENTITY (1,1),
      p_id INT NOT NULL DEFAULT 0, --商品id, 关联pda_products 表
      oldbarcode VARCHAR(50) NOT NULL DEFAULT '', --去pda_products 表中的barcode 字段
      barcode VARCHAR(50) NOT NULL DEFAULT '',	--新扫描录入的条码
      createtime datetime  not null default (getdate()),  --创建时间
      ZT int not null default 0,   -- 状态 0 pda 新增，1 药易通已经处理，当药易通已经处理了，再次更新条码时需要将状态修改为0
      CONSTRAINT [PK_pda_PBarcode] PRIMARY KEY CLUSTERED
        (
          p_id ASC
        )
    ) ON [PRIMARY]
  end
GO

---新增调拨单
-- drop table pda_AddTranBill
IF not exists (select 1 from dbo.sysobjects where id = object_id(N'[dbo].[pda_AddTranBill]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
  begin
    CREATE TABLE [dbo].pda_AddTranBill(
      billid INT IDENTITY(1,1), --调拨单id，自增列
      billnumber VARCHAR(30) NOT NULL DEFAULT '',--调拨单单号，根据职员编码+时间（yymmddhhmmss） 生成
      e_id INT NOT NULL DEFAULT 0,--经手人 pda_employees.e_id， 取pda登录人员id
      billstates INT NOT NULL DEFAULT 0,--状态 0草稿，后台不采集 1 已正式提交，正式提交后，pda不允许修改，后台允许读取数据，后台不允许修改这个字段，避免pda 判断状态时出错
      pdastates INT NOT NULL DEFAULT 0,--交换状态 0, 新增， 1 后台已经成功读取数据， -1 后台读取数据出错
      createtime datetime  not null default (getdate()),--创建时间
      pdaReTime DATETIME NOT NULL DEFAULT 0,--读取时间
      CONSTRAINT [PK_pda_AddTranBill] PRIMARY KEY CLUSTERED
        (
          billid ASC
        )
    ) ON [PRIMARY]
  end

GO


--新增调拨单明细
-- drop table pda_AddTranBill_D
IF not exists (select 1 from dbo.sysobjects where id = object_id(N'[dbo].[pda_AddTranBill_D]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
  begin
    CREATE TABLE [dbo].pda_AddTranBill_D(
      smb_id INT IDENTITY(1,1),--调拨单明细表(自增列)
      bill_id INT NOT NULL DEFAULT 0,--调拨单id,=pda_AddTranBill.billid
      p_id INT NOT NULL DEFAULT 0,--商品id,=pda_Products.p_id
      storehouse_id int not null default 0, --库存表唯一id
      Batchno	VARCHAR(20) NOT NULL DEFAULT '',--批号
      createtime datetime  not null default (getdate()),--创建时间
      pdastates INT NOT NULL DEFAULT 0,--状态 0暂存数据(pda_AddTranBillLoc_D没有smb_id相同的数据)   1完成数据
      CONSTRAINT [PK_pda_AddTranBill_D] PRIMARY KEY CLUSTERED
        (
          smb_id ASC
        )

    ) ON [PRIMARY]
  end

go
-- drop table pda_AddTranBillLoc_D
IF not exists (select 1 from dbo.sysobjects where id = object_id(N'[dbo].[pda_AddTranBillLoc_D]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
  begin
    CREATE TABLE [dbo].pda_AddTranBillLoc_D(
      id INT IDENTITY (1,1),
      bill_id INT NOT NULL DEFAULT 0,--调拨单id,=pda_TranBill.billid
      smb_id INT not null default(0), --调拨单明细表id和pda_AddTranBill_D表关联
      quantity	NUMERIC(18,4) NOT NULL DEFAULT 0,--数量
      location_id2	INT NOT NULL DEFAULT 0,--调入货位id,=pda_location.l_id
      createtime datetime  not null default (getdate()),--创建时间
    ) ON [PRIMARY]
  end

GO

