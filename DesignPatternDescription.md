# 设计模式说明

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

<b>优点:</b>

采用组合模式使客户端可以统一对待单个对象和组合对象。具体到我们的项目，即对于程序的其他部分而言，GiftPackage类和Toy, Garment, Book类几乎是没有区别的，可以统一作为Item接口的子类来对待。调用叶子类和容器类的方法也可以实现对应的“单一功能”和“组合功能”，而不必将“单个对象”和“组合对象”区别对待，这降低了程序的耦合性。


#### 程序截图：

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_1.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_2.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_3.png)
