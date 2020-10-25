using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Flea
{
    interface Item
    {
        string PictureUrl
        {
            get;
            set;
        }
        string Name
        {
            get;
            set;
        }
        float Price
        {
            get;
            set;
        }
        string Location
        {
            get;
            set;
        }
        string ID
        {
            get;
            set;
        }
        string Category
        {
            get;
            set;
        }
        bool OnShelf
        {
            get;
            set;
        }

        DateTime TimeStamp
        {
            get;
            set;
        }
        string ShowInfo();

        void AddGift(Item item);
        void RemoveGift(Item item);
        Item GetChild(int i);
    }
}
