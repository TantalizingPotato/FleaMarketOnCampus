//using SimpleFactorySample;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Flea
{
    class Cart
    {
        public List<Item> items = new List<Item>();
        List<Item> Items
        {
            get { return items; }
            set { items = value; }
        }

        public void AddItem(Item item)
        {
            items.Add(item);
        }

        public void Clear()
        {
            foreach(Item item in items)
            {
                item.OnShelf = false;
            }
           // Console.WriteLine(items.Count);
            items.Clear();
        }
    }
}
