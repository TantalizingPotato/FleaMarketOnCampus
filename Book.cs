using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Flea
{
    class Book : Item
    {
        public Book(string ID)
        {
            this.ID = ID;
            Console.WriteLine("已上架一本书！");
        }

        bool onShelf;

        public string PictureUrl { get; set; }
        public string Name { get; set; }
        public float Price { get; set; }
        public string Location { get; set; }
        public string ID { get; set; }
        public string Category { get; set; }
        public DateTime TimeStamp { get; set; }
        public bool OnShelf { get { return OnShelf; } set { onShelf = value; if (onShelf == false) Console.WriteLine("书已下架，ID: " + ID); } }

        public void ShowInfo()
        {
            Console.WriteLine("书信息：\nID：" + ID + "\n名称：" + Name);
        }

    }
}
