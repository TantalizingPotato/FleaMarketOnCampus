using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Flea
{
    public partial class Form1 : Form
    {
        Item item_1, item_2, item_3, item_4, item_5;
        ItemsOnShelf myItems = new ItemsOnShelf();
        public Form1()
        {
            Item tempItem;
            InitializeComponent();
            comboBox1.Items.Add("Book");
            comboBox1.Items.Add("Garment");
            comboBox1.Items.Add("Toy");
            comboBox1.Items.Add("GiftPackage");
            item_1 = ItemFactory.GetItem("GiftPackage", "冬季大礼包");
            myItems.AddItem(item_1);
            item_2 = ItemFactory.GetItem("Garment", "秋季外套");
            myItems.AddItem(item_2);
            item_3 = ItemFactory.GetItem("Toy", "巴斯光年手办");
            myItems.AddItem(item_3);
            item_4 = ItemFactory.GetItem("Book", "软件体系结构");
            myItems.AddItem(item_4);
            tempItem = ItemFactory.GetItem("Book", "围炉夜话");
            item_1.AddGift(tempItem);
            tempItem = ItemFactory.GetItem("Garment", "百搭围巾");
            item_1.AddGift(tempItem);
            tempItem = ItemFactory.GetItem("Toy", "时尚暖宝宝");
            item_1.AddGift(tempItem);
            InitTreeView1();

        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (textBox1.Text == null || comboBox1.Text == null)
                MessageBox.Show("请至少选择物品种类和名字!");
            string a = comboBox1.Text;
            //使用了简单工厂模式获取Toy, Garment, Book or GiftPackage
            Item item = ItemFactory.GetItem(a, textBox1.Text);
            item.Category = comboBox1.Text;
            item_1.AddGift(item);
            MessageBox.Show("加入冬季大礼包成功！物品名称为：" + item.Name + " " + item.Category);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            if(treeView1.SelectedNode != null)
            {
                foreach(Item item in myItems.items)
                {
                    if(item.Name.Equals(treeView1.SelectedNode.Text))
                    {
                        label6.Text = item.ShowInfo();
                    }
                }
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void InitTreeView1()
        {
            treeView1.BeginUpdate();
            treeView1.Nodes.Clear();

            TreeNode Node = null;
            foreach (Item item in myItems.items)
            {
                Node = new TreeNode(item.Name);
                treeView1.Nodes.Add(Node);
            }
            treeView1.EndUpdate();
        }
        private void button1_Click(object sender, EventArgs e)
        {
            if (textBox1.Text == null || comboBox1.Text == null)
                MessageBox.Show("请至少选择物品种类和名字!");
            string a = comboBox1.Text;
            //使用了简单工厂模式获取Toy, Garment, Book or GiftPackage
            Item item = ItemFactory.GetItem(a, textBox1.Text);
            item.Category = comboBox1.Text;
            MessageBox.Show("创建成功！物品名称为：" + item.Name+ " "+item.Category);
            myItems.AddItem(item);
            InitTreeView1();

        }

        private void button2_Click(object sender, EventArgs e)
        {
            myItems.Clear();
            InitTreeView1();
        }
    }
}
