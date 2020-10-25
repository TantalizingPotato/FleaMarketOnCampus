# 设计模式说明

<font size=30><b> 简单工厂模式 </b></font>

简单工厂模式有三个角色：工厂角色、抽象产品角色、具体产品角色。定义一个工厂类（工厂角色），他可以根据参数的不同返回不同类的实例，被创建的实例通常都具有共同的父类。

在我们的的跳蚤平台中，我们将上架商品的功能用简单工厂模式实现。我们通过工厂类产生某个物品类的新对象，下面是具体的实现思路：

创建商品的抽象类：Item

属性包括PictureUrl(商品图片的url地址), Name(名称), Price(价格), Location(位置), ID(编号), Category(类别), OnShelf(是否在架上), TimeStamp(上架时间)。

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Item_interface.png)

创建继承抽象类的实体类有：Book(书本类)、Garment(服装类)、Toy(玩具类)

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Book_class.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Garment_class.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Toy_class.png)

创建一个工厂，只需要传入一个正确的参数，就可以获取所需要的对象，而无需知道其实现过程。那么使其可以根据传递过来的参数来实例化相应的对象：

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/ItemFactory.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/button1_click.png)

<b>优点：</b>

在我们的课题中，由于各个商品继承一个统一的抽象类，层级分明，所需要创建的类的数量不是很多，因此使用静态工厂模式（工厂模式的业务逻辑不会太复杂，因此简单工厂模式适用于少量的创建对象）。静态工厂可以根据用户传入的参数来动态地实例化对象，避免一次性实例化所有对象所带来的性能浪费，同时也降低了耦合性。

如果需要额外加入对象，也可以引入配置文件，在不修改客户端代码的情况下更换和添加新的具体产品类。


<font size=30><b> 观察者模式 </b></font>

观察者模式包括四个角色，目标，具体目标，观察者，具体观察者。当一个对象的行为或者状态发生改变，它们之间会产生联动。是一种一对多的关系。

我们将跳蚤平台中一键清空购物车功能用观察者模式实现出来，cart类（购物车）触发删除的操作，导致所有的观察者（购物车中的物品全部删除）的状态改为onself=false。因为该功能较简单，没有创建抽象目标类，直接在cart类中通过函数clear()作为抽象目标；具体目标是具体观察者中的onself的get()和set()函数：

被观察者类Cart中有一个用来存放观察者对象Item的List，它是被观察者类的核心。AddItem方法是向这个List中添加观察者对象；Clear方法是从List中移除观察者对象。

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/Cart_observed.png)

观察者定义为接口，该接口声明了更新数据的方法。观察者有一个onshelf属性，在被观察者状态发生变化时，这个属性就会被触发改变。

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/OnShelf.png)

<b>优点：</b>

观察者与被观察者之间是属于轻度的关联关系，并且是抽象耦合的，这样，对于两者来说都比较容易进行扩展。观察者模式将观察者和主题（被观察者）彻底解耦，主题只知道观察者实现了某一接口。并不需要观察者的具体类是谁、做了些什么或者其他任何细节。任何时候我们都可以增加新的观察者。


<font size=30><b>程序截图：</b></font>

![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_1.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_2.png)
![Image text](https://github.com/TantalizingPotato/FleaMarketOnCampus/blob/master/pics/PrintScreen_3.png)
