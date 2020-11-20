using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Flea
{
    class GiftPackage : Item
    {
        bool onShelf;
        List<Item> gifts = new List<Item>();

        public GiftPackage(string name)
        {
            this.Name = name;
            Console.WriteLine("上架一份礼包！");
        }

        public string PictureUrl { get; set; }
        public string Name { get; set; }
        public float Price { get; set; }
        public string Location { get; set; }
        public string ID { get; set; }
        public string Category { get; set; }
        public DateTime TimeStamp { get; set; }
        public bool OnShelf { get { return OnShelf; } set { onShelf = value; if (onShelf == false) Console.WriteLine("礼包已下架，ID: " + ID); } }

        public void AddGift(Item item)
        {
            gifts.Add(item);
        }

        public Item GetChild(int i)
        {
            return (Item)gifts[i];
        }

        public void RemoveGift(Item item)
        {
            gifts.Remove(item);
        }

        public string ShowInfo()
        {
            string infoStr;
            Console.WriteLine("礼包信息：名称：" + Name);
            infoStr = "礼包信息：名称：" + Name + "\n包含：";
            foreach (Item item in gifts)
            {
                infoStr += "\n  " + item.ShowInfo();
            }
            return infoStr;
        }
    }
}
