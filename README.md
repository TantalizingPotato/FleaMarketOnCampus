# FleaMarketOnCampus
An online Flea Market platform intended for students on campus, where you can buy and sell second-hand goods.

#### RESTful Web API doc:
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui.png)

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryItemByItemName.png)

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_postItem.png)

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_queryUserByUsername.png)

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/swagger-ui_deleteUserById.png)


<font size=30><b> Use case diagram: </b></font>
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/raw/master/FleaMarketUseCase.png)

#### 用例图说明：

从用户视角来看，用户主要接触三大功能模块：购买，管理摊位及管理个人信息。

对于购买行为，在我们平台上是从“逛商场”开始的。用户对于感兴趣的商品可以查看详细信息，商品详情页面有“立即购买”的选项。一旦购买，则必须申明收货地址及付款。

管理摊位功能分为上架商品和下架商品两种操作。上架商品必须提供商品信息并申明联系方式。

在管理个人信息模块，则提供各种账号信息管理功能。

<font size=30><b> Class diagram: </b></font>
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/raw/master/ClassUML_FleaMarket.png)

#### 类图说明：

每个用户拥有一个“收件箱”Message对象和一个“已发送的信件箱”Message对象。用户还拥有众多Item对象（拥有的Item数也可以为0），表示其上架的商品。用户还拥有一个购物车，一个已购买列表，一个已售出列表和一个正在架上的商品列表，它们都继承了ItenList这个父类。ItemList是Item的聚合。

<font size=30><b> Sequence diagram: </b></font>

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/%E9%A1%BA%E5%BA%8F%E5%9B%BE_1.png)

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/%E9%A1%BA%E5%BA%8F%E5%9B%BE_2.jpg)

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/%E9%A1%BA%E5%BA%8F%E5%9B%BE_3.jpg)

#### 时序图说明：

我们抽象出来基本的用户行为，分别是登录、搜索、购买、添加购物车、联系、上架物品。根据类图中划分的类以及属性和方法，我们定义了用户（买方和卖方）、ItemList（用来执行业务逻辑，比如通过ID查找物品、增删物品等）、Item（抽象的物品对象）、cart（抽象的购物车对象）、Manage(执行系统操作，如登录功能)


<b>搜索：</b>用户（包括买方和卖方）把搜索指令传给ItemList对象中的QueryByname方法查到对应条件的物品，同步返回给用户。

<b>上架：</b>卖家通过Additem方法新建一个Item对象，通过同步返回的true/false判断是否成功。

<b>登录：</b>用户（包括卖家或卖家）通过enter函数传递密码给Manage，Manage对象判断是否登录成功。

<b>购买：</b>买家通过buy函数传递给ItemList对象执行购买的动作，ItemList通过SetBpught函数将购买的物品状态改为“已卖出”，同步传递信息说明购买操作是否已经成功。若购买已经成功，买家向卖家传递“购买”的信息。

<b>加入购物车：</b>买家通过AddItem函数传递给购物车对象“加入一条某ID的物品信息”，购物车传递ID给Itemlist搜索返回该物品，同步返回给买家“是否添加成功”
Message和用户一一对应的。我们规定收件箱在卖家方，因此买家通过sendmessage发送消息给买家，同步返回消息是否发送成功。

### 设计模式说明：

#### 简单工厂模式 

简单工厂模式有三个角色：工厂角色、抽象产品角色、具体产品角色。定义一个工厂类（工厂角色），它有一个产生产品对象的方法，该方法可以根据参数的不同返回不同类的实例。被创建的实例通常都具有共同的父类。

在我们的校园跳蚤市场平台中，我们用简单工厂模式实现上架商品的功能。我们通过工厂类的静态方法来产生某个物品类的新对象。下面是我们的具体实现思路：

商品接口：Item（抽象产品角色）
该接口包含以下属性：PictureUrl(商品图片的url地址), Name(名称), Price(价格), Location(位置), ID(编号), Category(类别), OnShelf(是否在架上), TimeStamp(上架时间)。
Item接口还包含若干接口方法
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Item_interface.png)

继承Item抽象接口的实体类有：Book(书本类)、Garment(服装类)、Toy(玩具类)

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Book_Item_class.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Garment_Item_class.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Toy_Item_class.png)

定义一个工厂类ItemFactory。该工厂含一个静态方法Item GetItem(string,string)。只需要为该方法传入正确的参数，就可以获取所需的对象，程序的其他部分无需了解工厂的内部实现过程。该静态方法根据传递过来的参数来实例化相应的类，产生一个所需的对象：

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/ItemFactory.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/button1_click.png)

<b>优点：</b>

在我们的课题中，由于各个商品继承一个统一的抽象类，层级分明，所需要定义的具体类的数量不是很多，因此使用静态工厂模式（工厂模式的业务逻辑不会太复杂，因此简单工厂模式适用于少量的创建对象）。静态工厂可以根据用户传入的参数来动态地实例化对象，避免一次性实例化所有对象所带来的性能浪费，同时也降低了耦合性。

如果需要额外加入对象，也可以引入配置文件，在不修改客户端代码的情况下更换和添加新的具体产品类。


#### 观察者模式

观察者模式包括四个角色，目标，具体目标，观察者，具体观察者。当一个对象的行为或者状态发生改变，它们之间会产生联动。是一种一对多的关系。

我们将跳蚤平台中一键清空上架商品列表的功能用观察者模式实现出来，ItemsOnShelf类(上架商品列表)触发删除的操作，导致所有的观察者（上架商品列表中的物品全部删除）的OnShelf属性转为false。因为该功能较简单，没有创建抽象目标类，直接在ItemsOnShelf类作为具体目标。

具体目标ItemsOnShelf类中有一个用来存放观察者对象的List，它是被观察目标的核心。AddItem方法向这个List中添加观察者对象；Clear方法会从List中移除所有观察者对象。

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Cart_Observed.png)

观察者定义为Item接口，它有一个OnShelf属性。在观察目标的Clear()方法被调用时，这个属性就会被触发改变。

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/OnShelf.png)

<b>优点：</b>

观察者与观察目标之间是属于轻度的关联关系，并且是抽象耦合的，这样，对于两者来说都比较容易进行扩展。观察者模式将观察者和观察目标解耦，观察目标只知道观察者实现了某一接口。并不需要观察者的具体类是谁、做了些什么或者其他任何细节。任何时候我们都可以增加新的观察者。


#### 组合模式

组合模式包含三个角色：抽象构件(component)、叶子构件(leaf)、容器构件(composite).它使客户端可以统一对待单个对象和组合对象，可以形成一种树形结构以表示具有部分-整体关系的层次结构。当容器对象的某一个方法被调用时，将遍历自身包含的叶子对象并调用执行叶子对象的对应方法。

在我们的项目中，Item(抽象产品)接口充当抽象构件，Book类,Garment类和Toy类继承Item接口，充当叶子构件，GiftPackage(礼包)类同样继承Item接口，充当容器构件。Item接口除了定义了一些属性外，还定义了四个方法： string ShowInfo(), void AddGift(Item), void RemoveGift(Item)和 Item GetChild(int). 所有继承Item接口的类（包括叶子类和容器类）都要实现这四个方法。对于叶子类而言， AddGift(Item)，RemoveGift(Item) 和 GetChild(int)方法并不完成实际功能，ShowInfo()方法则返回一个包含商品信息的字符串。对于容器类GiftPackage,AddGift(Item)，RemoveGift(Item) 和 GetChild(int)方法完成实际功能。并且，容器类GiftPackage的ShowInfo()方法会遍历自身包含的叶子对象，逐个调用它们的ShowInfo()方法；最终返回的字符串包含自身信息以及所有属于自身的叶子对象的信息。

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Toy_Item_class_2.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/GiftPackage_Item_class.png)

<b>优点:</b>

采用组合模式使客户端可以统一对待单个对象和组合对象。具体到我们的项目，即对于程序的其他部分而言，GiftPackage类和Toy, Garment, Book类几乎是没有区别的，可以统一作为Item接口的子类来对待。调用叶子类和容器类的方法也可以实现对应的“单一功能”和“组合功能”，而不必将“单个对象”和“组合对象”区别对待，这降低了程序的耦合性。


### 程序截图：

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_1.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_2.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_3.png)
