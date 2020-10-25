using System;

namespace Flea
{
    class ItemFactory
    {
        //静态工厂方法
        public static Item GetItem(string type, string ID) 
        {
		    Item item = null;
		    if (type.Equals("book")) 
            {
			    Console.WriteLine("准备上架一本书");
                item = new Book(ID);
            }
		    else if (type.Equals("Toy")) 
            {
			    Console.WriteLine("准备上架一个玩具！");
                item = new Toy(ID);
            }
		    else if (type.Equals("Garment")) 
            {
			    Console.WriteLine("准备上架一件衣服！");
                item = new Garment(ID);
            }
            return item;
	    }
    }
}
