using System;

namespace Flea
{
    class Toy : Item
    {
        public Toy(string name)
        {
            this.Name = name;
            Console.WriteLine("上架一玩具！");
        }

        bool onShelf;

        public string PictureUrl { get; set; }
        public string Name { get; set; }
        public float Price { get; set; }
        public string Location { get; set; }
        public string ID { get; set; }
        public string Category { get; set; }
        public DateTime TimeStamp { get; set; }
        public bool OnShelf { get { return OnShelf; } set { onShelf = value; if (onShelf == false) Console.WriteLine("玩具已下架，ID: " + ID); } }

        public string ShowInfo()
        {
            Console.WriteLine("玩具信息：名称：" + Name);
            return "玩具信息：名称：" + Name;
        }

        public void AddGift(Item item)
        {
            Console.WriteLine("对不起，不支持该方法！");
        }

        public void RemoveGift(Item item)
        {
            Console.WriteLine("对不起，不支持该方法！");
        }

        public Item GetChild(int i)
        {
            Console.WriteLine("对不起，不支持该方法！");
            return null;
        }

    }

}