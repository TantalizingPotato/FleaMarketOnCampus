# FleaMarketOnCampus
An online Flea Market platform intended for students on campus, where you can buy and sell second-hand goods.

<font size=30><b> Use case diagram: </b></font>
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/raw/master/FleaMarketUseCase.png)

<font size=30><b>用例图说明：</b></font>

从用户视角来看，用户主要接触三大功能模块：购买，管理摊位及管理个人信息。

对于购买行为，在我们平台上是从“逛商场”开始的。用户对于感兴趣的商品可以查看详细信息，商品详情页面有“立即购买”的选项。一旦购买，则必须申明收货地址及付款。

管理摊位功能分为上架商品和下架商品两种操作。上架商品必须提供商品信息并申明联系方式。

在管理个人信息模块，则提供各种账号信息管理功能。

<font size=30><b> Class diagram: </b></font>
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/raw/master/ClassUML_FleaMarket.png)

<font size=30><b> 类图说明：</b></font>

每个用户拥有一个“收件箱”Message对象和一个“已发送的信件箱”Message对象。用户还拥有众多Item对象（拥有的Item数也可以为0），表示其上架的商品。用户还拥有一个购物车，一个已购买列表，一个已售出列表和一个正在架上的商品列表，它们都继承了ItenList这个父类。ItemList是Item的聚合。

<font size=30><b> 时序图说明： </b></font>

我们抽象出来基本的用户行为，分别是登录、搜索、购买、添加购物车、联系、上架物品。根据类图中划分的类以及属性和方法，我们定义了用户（买方和卖方）、ItemList（用来执行业务逻辑，比如通过ID查找物品、增删物品等）、Item（抽象的物品对象）、cart（抽象的购物车对象）、Manage(执行系统操作，如登录功能)


<b>搜索：</b>用户（包括买方和卖方）把搜索指令传给ItemList对象中的QueryByname方法查到对应条件的物品，同步返回给用户。

<b>上架：</b>卖家通过Additem方法新建一个Item对象，通过同步返回的true/false判断是否成功。

<b>登录：</b>用户（包括卖家或卖家）通过enter函数传递密码给Manage，Manage对象判断是否登录成功。

<b>购买：</b>买家通过buy函数传递给ItemList对象执行购买的动作，ItemList通过SetBpught函数将购买的物品状态改为“已卖出”，同步传递信息说明购买操作是否已经成功。若购买已经成功，买家向卖家传递“购买”的信息。

<b>加入购物车：</b>买家通过AddItem函数传递给购物车对象“加入一条某ID的物品信息”，购物车传递ID给Itemlist搜索返回该物品，同步返回给买家“是否添加成功”
Message和用户一一对应的。我们规定收件箱在卖家方，因此买家通过sendmessage发送消息给买家，同步返回消息是否发送成功。
