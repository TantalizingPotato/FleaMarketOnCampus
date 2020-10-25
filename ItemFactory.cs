using System;

namespace Flea
{
    class ItemFactory
    {
        //静态工厂方法
        public static Item GetItem(string type, string name) 
        {
		    Item item = null;
		    if (type.Equals("Book")) 
            {
			    Console.WriteLine("准备上架一本书");
                item = new Book(name);
            }
		    else if (type.Equals("Toy")) 
            {
			    Console.WriteLine("准备上架一个玩具！");
                item = new Toy(name);
            }
		    else if (type.Equals("Garment")) 
            {
			    Console.WriteLine("准备上架一件衣服！");
                item = new Garment(name);
            }
            else if (type.Equals("GiftPackage"))
            {
                Console.WriteLine("准备上架一份礼包！");
                item = new GiftPackage(name);
            }
            return item;
	    }
    }
}
