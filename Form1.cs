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
        Item item, item_2, item_3, item_4;
        Cart cart = new Cart();
        public Form1()
        {
            InitializeComponent();
            comboBox1.Items.Add("book");
            comboBox1.Items.Add("Garment");
            comboBox1.Items.Add("Toy");
            item_2 = ItemFactory.GetItem("Garment", "秋季外套");
            cart.AddItem(item_2);
            item_3 = ItemFactory.GetItem("Toy", "巴斯光年手办");
            cart.AddItem(item_3);
            item_4 = ItemFactory.GetItem("book", "软件体系结构");
            cart.AddItem(item_4);
            InitTreeView1();

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void InitTreeView1()
        {
            treeView1.BeginUpdate();
            treeView1.Nodes.Clear();

            TreeNode Node = null;
            foreach (Item item in cart.items)
            {
                Node = new TreeNode(item.ID);
                treeView1.Nodes.Add(Node);
            }
            treeView1.EndUpdate();
        }
        private void button1_Click(object sender, EventArgs e)
        {
            if (textBox1.Text == null || comboBox1.Text == null)
                MessageBox.Show("请至少选择物品种类和名字!");
            string a = comboBox1.Text;
           // Console.WriteLine(a);
           item = ItemFactory.GetItem(a, textBox1.Text);
            item.Category = comboBox1.Text;
            MessageBox.Show("创建成功！物品名称为：" + item.ID+ " "+item.Category);
            //使用了简单工厂模式获取Toy Garment or Book
            cart.AddItem(item);
            InitTreeView1();



        }

        private void button2_Click(object sender, EventArgs e)
        {
            cart.Clear();
            InitTreeView1();
        }
    }
}
