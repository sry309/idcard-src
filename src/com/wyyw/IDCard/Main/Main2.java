package com.wyyw.IDCard.Main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;

import com.wyyw.IDCard.Bean.CardAreaBean;
import com.wyyw.IDCard.Bean.DateBean;
import com.wyyw.IDCard.Config.Config;
import com.wyyw.IDCard.Core.IDCard;
import com.wyyw.IDCard.DAO.DbDao;

public class Main2 extends JFrame {

	private JPanel contentPane;
	private JTextField idcardnum;

	private JComboBox shenglist = new JComboBox();
	private JComboBox<Item> citylist = new JComboBox();
	private JComboBox qulist = new JComboBox();
	private JTextArea textPane = new JTextArea();
	private JComboBox year1 = new JComboBox();
	private JComboBox year2 = new JComboBox();
	private JComboBox month = new JComboBox();
	private JComboBox day = new JComboBox();
	private JPanel panel = new JPanel();
	boolean boolflag = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main2 frame = new Main2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main2() {
		setTitle("IDCard计算器V1.1__By_凸(@_@)凸");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 814, 701);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		panel.setBounds(0, 0, 798, 653);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("身份证号");
		lblNewLabel.setBounds(35, 22, 78, 40);
		panel.add(lblNewLabel);

		idcardnum = new JTextField();
		idcardnum.setBounds(147, 22, 571, 40);
		panel.add(idcardnum);
		idcardnum.setColumns(10);

		JLabel sheng = new JLabel("省");
		sheng.setBounds(35, 91, 43, 40);

		panel.add(sheng);

		shenglist.setBounds(88, 101, 96, 30);

		try {
//			ResultSet rs = null;
			//rs = DbDao.getInstance().getStmt().executeQuery("SELECT * FROM T_CARDAREA WHERE CODE LIKE '__0000';");
			String sql = "SELECT * FROM T_CARDAREA WHERE CODE LIKE '__0000';";
			List<CardAreaBean> list = DbDao.getInstance().querySql(sql);
			
			
			if(list.size()>0){
				Item tem = new Item("000000", "不知道");
				shenglist.addItem(tem);
				for(int x = 0;x<list.size();x++){
					CardAreaBean cab = list.get(x);
					Item item = new Item(cab.getCode(),cab.getArea());
					shenglist.addItem(item);
				}
			}
			

		} catch (Exception ex) {

			System.out.println("97 " + String.valueOf(ex));
		}

		// 添加一个监听事件
		shenglist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// 根据获取的省份找到它下面的级别的市
				Item item = (Item) shenglist.getSelectedItem();
				System.out.println(item.getKey());
				System.out.println(item.getValue());
				String stridcard = idcardnum.getText();
				// 如果长度大于2
				if (stridcard.length() > 2) {
					stridcard.substring(0, 2);
					idcardnum.setText(item.getKey().substring(0, 2) + stridcard.substring(2, stridcard.length()));
				} else { // 如果长度小于2 那么
					idcardnum.setText(item.getKey().substring(0, 2));
				}
				changecitylist(item.getKey());

			}
		});

		panel.add(shenglist);

		JLabel city = new JLabel("市");
		city.setBounds(194, 91, 43, 40);
		panel.add(city);

		citylist.setBounds(247, 101, 125, 30);
		// 市单击事件
		citylist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Item item = (Item) citylist.getSelectedItem();
				System.out.println(item.getKey());
				System.out.println(item.getValue());
				// 如果选择的不是000000 ------ 不知道
				if(!item.getKey().equals("000000")){
					String stridcard = idcardnum.getText();
					// 如果长度大于4
					if (stridcard.length() > 4) {
						stridcard.substring(0, 4);
						idcardnum.setText(item.getKey().substring(2, 4) + stridcard.substring(4, stridcard.length()));
					} else { // 如果长度小于2 那么
						idcardnum.setText(item.getKey().substring(2, 4));
					}
				}else{
					
				}
				
				//String stridcard = idcardnum.getText();
			}
		});

		panel.add(citylist);

		JLabel qu = new JLabel("县(区)");
		qu.setBounds(382, 91, 43, 40);
		panel.add(qu);
		qulist.setEnabled(false);

		qulist.setBounds(435, 101, 283, 30);
		qulist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("---------------------");
			}
		});
		panel.add(qulist);

		JLabel label_3 = new JLabel("性别");
		label_3.setBounds(35, 176, 43, 40);

		panel.add(label_3);

		JComboBox sexlist = new JComboBox();
		sexlist.setEnabled(false);
		sexlist.setBounds(88, 186, 96, 30);

		Item sexitemunknow = new Item("0123456789", "未知");
		Item sexitemman = new Item("13579", "男");
		Item sexitemwoman = new Item("24680", "女");

		sexlist.addItem(sexitemunknow);
		sexlist.addItem(sexitemman);
		sexlist.addItem(sexitemwoman);
		panel.add(sexlist);
		year1.setEnabled(false);

		year1.setBounds(251, 186, 71, 30);

		Item ageyunknow = new Item("00", "未知");
		Item it18 = new Item("18", "18");
		Item it19 = new Item("19", "19");
		Item it20 = new Item("20", "20");

		year1.addItem(ageyunknow);
		year1.addItem(it18);
		year1.addItem(it19);
		year1.addItem(it20);

		panel.add(year1);
		year2.setEnabled(false);

		year2.setBounds(354, 186, 71, 30);

		Item age2yunknow = new Item("--", "未知");
		year2.addItem(age2yunknow);
		for (int i = 0; i < 100; i++) {
			String str = String.valueOf(i);
			str = str.length() > 1 ? "" + str : "0" + str;
			Item itemage = new Item(str, str);
			year2.addItem(itemage);
		}

		panel.add(year2);

		Item itemmonth = new Item("--", "未知");
		month.setEnabled(false);
		month.addItem(itemmonth);
		month.setBounds(490, 186, 78, 30);
		for (int i = 1; i < 13; i++) {
			String str = String.valueOf(i);
			str = str.length() > 1 ? "" + str : "0" + str;
			month.addItem(str);
		}
		panel.add(month);

		Item itemday = new Item("--", "未知");
		month.addItem(itemday);
		day.setEnabled(false);
		day.setBounds(619, 186, 71, 30);
		for (int i = 1; i < 32; i++) {
			String str = String.valueOf(i);
			str = str.length() > 1 ? "" + str : "0" + str;
			day.addItem(str);
		}
		panel.add(day);

		JLabel label = new JLabel("年");
		label.setBounds(448, 181, 20, 40);
		panel.add(label);

		JLabel label_1 = new JLabel("月");
		label_1.setBounds(578, 176, 20, 40);
		panel.add(label_1);

		JLabel label_2 = new JLabel("日");
		label_2.setBounds(700, 176, 20, 40);
		panel.add(label_2);
		JButton stopbtn = new JButton("暂停");
		stopbtn.setEnabled(false);
		stopbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.runsw = Config.runsw * -1;
				System.out.println(Config.runsw);
				if (Config.runsw > 0) {
					stopbtn.setText("暂停");
				} else {
					stopbtn.setText("继续");
				}

			}
		});
		stopbtn.setBounds(625, 243, 93, 23);
		panel.add(stopbtn);
		// JScrollPane scrollPane = new JScrollPane(textPane);
		// panel.add(scrollPane);
		JButton runbtn = new JButton("计算");
		runbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// String idcardnum = idcardnum
				textPane.setText(""); // 清空内容
				String idcardnumstr = idcardnum.getText();
				Config.boolflag = true;
				Config.runsw = 1;
				stopbtn.setEnabled(true);
				stopbtn.setText("暂停");
				runbtnClick(idcardnumstr);
			}
		});
		runbtn.setBounds(517, 243, 93, 23);
		panel.add(runbtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 336, 690, 286);
		// scrollPane.setLayout(null);
		panel.add(scrollPane);

		// textPane = new JTextPane();
		scrollPane.setViewportView(textPane);

		JLabel lblNewLabel_1 = new JLabel("仅可用于学习,不可用于非法用途");
		lblNewLabel_1.setBounds(55, 247, 398, 30);
		panel.add(lblNewLabel_1);

		// JScrollPane scrollPane = new JScrollPane(textPane);
		// panel.add(scrollPane);
	}

	/**
	 * 判断字符串是否为* 如果是* 那么就将所有的可能性都放进去 如果不是* 那么就将这个字符串放到里面去
	 * 
	 * @param str
	 * @param args
	 * @return
	 */
	public List getNum2String(String str, String[] args) {
		List list = new ArrayList();

		if (str.contentEquals("*")) {
			for (int i = 0; i < args.length; i++) {
				list.add(args[i]);
			}
		} else {
			list.add(str);
		}
		return list;
	}

	// 运行按钮单击事件
	public void runbtnClick(String idcardnum) {
		Config.runsw = 1;
		// 先获取前6位地区编码
		String area = idcardnum.substring(0, 6);
		area = area.replace("*", "_");
		// 获取所有的地区编码
		String sql = "SELECT * FROM T_CARDAREA WHERE CODE LIKE '" + area + "' and CODE not like '____00';";
		List<CardAreaBean> list = new ArrayList<CardAreaBean>();
		try {
			list = DbDao.getInstance().querySql(sql);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			// 如果报错 那么就直接退出
			return;
		}

		String date = idcardnum.substring(6, 14);
		date = date.replace("*", "_");

		String sqldate = "SELECT * FROM T_DATE WHERE DATE LIKE '" + date + "';";
		List<CardAreaBean> listdate = new ArrayList<CardAreaBean>();
		try {
			listdate = DbDao.getInstance().querySql_date(sqldate);
		} catch (Exception ex) {
			ex.printStackTrace();
			// 如果报错 那么就直接退出
			return;
		}

		String area1 = idcardnum.substring(14, 15);
		List listarea1 = new ArrayList();
		String[] area1s = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		listarea1 = getNum2String(area1, area1s);

		String area2 = idcardnum.substring(15, 16);
		List listarea2 = new ArrayList();
		String[] area2s = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		listarea2 = getNum2String(area2, area2s);

		String area3 = idcardnum.substring(16, 17);
		List listarea3 = new ArrayList();
		String[] area3s = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		listarea3 = getNum2String(area3, area3s);

		String v = idcardnum.substring(17, 18);
		v = v.toUpperCase();
		List listv = new ArrayList();
		String[] vs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X" };
		listv = getNum2String(v, vs);
		Thread thread = new MyThread(textPane, list, listarea1, listarea2, listarea3, v, listdate);

		Config.EXITTHREAD = false;
		try {
			// 修改1秒以后 将退出标示修改文true
			// 这一步是为了退出上一个线程
			thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Config.EXITTHREAD = true;
		thread.start();

		int count = 0;

	}

	public void inserDoc(String msg) throws Exception {
		Document doc = textPane.getDocument();
		doc.insertString(doc.getLength(), msg, null);

	}

	// 准备市下拉框修改内容
	public void changecitylist(String code) {
		String shengcode = code.substring(0, 2);
		ResultSet rs = null;
		Statement stmt = null;
		try {
			System.out.println("根据省选择市");
			String sql = "SELECT * FROM T_CARDAREA WHERE CODE LIKE '" + shengcode + "__00' and CODE  not like '"
					+ shengcode + "0000';";

			System.out.println(sql);
			stmt = DbDao.getInstance().getStmt();
			rs = stmt.executeQuery(sql);

			if (null != rs) {
				try {
					System.out.println("下拉框大小 " + citylist.getItemCount());
					if (citylist.getItemCount() == 0) {

					} else {
						citylist.removeAllItems();

					}

				} catch (Exception ex) {
					System.out.println(ex);
				}

				Item tem = new Item("000000", "不知道");
				citylist.addItem(tem);

				while (rs.next()) {
					String province = rs.getString("area");
					Item item = new Item(rs.getString("code"), rs.getString("area"));
					citylist.addItem(item);

				}
				cityAction();
				// cityListener();

			}

		} catch (Exception ex) {
			System.out.println(ex);

			System.out.println("255 " + String.valueOf(ex));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void cityAction() {
		System.out.println("添加city   action事件");
		citylist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 根据获取的省份找到它下面的级别的市
				Item item = (Item) citylist.getSelectedItem();
				if (item != null) {
					changequlist(item.getKey());
					String stridcard = idcardnum.getText();
					
				}

			}
		});
	}

	//
	public void cityListener() {
		citylist.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				switch (event.getStateChange()) {
				case ItemEvent.SELECTED:
					Item item = (Item) citylist.getSelectedItem();
					System.out.println(item.getKey());
					System.out.println("选中" + event.getItem());
					// changequlist(item.getKey());
					break;
				case ItemEvent.DESELECTED:
					System.out.println("取消选中" + event.getItem());
					break;
				}
			}
		});
	}

	// 准备市修改内容
	public void changequlist(String code) {
		String citycode = code.substring(0, 4);
		try {
			ResultSet rs = null;
			System.out.println("根据市从数据库查找县");
			String sql = "SELECT * FROM T_CARDAREA WHERE CODE LIKE '" + citycode + "__' and CODE  not like '" + citycode
					+ "00';";
			System.out.println(this.getClass().getName() + " 256 " + sql);
			qulist.removeAllItems();
			System.out.println(sql);
			rs = DbDao.getInstance().getStmt().executeQuery(sql);
			if (null != rs) {
				Item tem = new Item("000000", "不知道");
				qulist.addItem(tem);

				while (rs.next()) {
					String province = rs.getString("area");
					Item item = new Item(rs.getString("code"), rs.getString("area"));
					qulist.addItem(item);

				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
			System.out.println("321 " + String.valueOf(ex));
		}

	}

	class MyThread extends Thread {
		JTextArea jTextPane;
		String msg;
		List<CardAreaBean> list;

		List listarea1;
		List listarea2;
		List listarea3;
		String v;
		List<DateBean> listdate;

		public MyThread(JTextArea jTextPane, List<CardAreaBean> list, List listarea1, List listarea2, List listarea3,
				String v, List listdate) {
			this.jTextPane = jTextPane;
			this.msg = msg;
			this.list = list;
			this.listarea1 = listarea1;
			this.listarea2 = listarea2;
			this.listarea3 = listarea3;
			this.v = v;
			this.listdate = listdate;
		}

		public int getrandomInt() {
			// 随机休眠时间
			// 问:这样写是不是效率更高
			// 答:为了装B
			Random random = new Random();
			int reint = random.nextInt(500);

			return reint;
		}

		@Override
		public void run() {
			int count = 0;
			String idcardareastr = "";
			String diqu = "";
			Document docs = jTextPane.getDocument();
			for (int idcardarea = 0; idcardarea < list.size(); idcardarea++) { // 地区
				for (int idcarddate = 0; idcarddate < listdate.size(); idcarddate++) { // 年1
					for (int idarea1 = 0; idarea1 < listarea1.size(); idarea1++) { // 地区1
						for (int idarea2 = 0; idarea2 < listarea2.size(); idarea2++) { // 地区2
							for (int idarea3 = 0; idarea3 < listarea3.size(); idarea3++) { // 地区3
								if (Config.EXITTHREAD) {
									if (Config.runsw > 0) {
										// 如果两个不一致
										if (!list.get(idcardarea).getCode().equals(idcardareastr)) {

											idcardareastr = list.get(idcardarea).getArea();
										} else {
										}
										String cardnum = list.get(idcardarea).getCode()
												+ listdate.get(idcarddate).getDate() + listarea1.get(idarea1)
												+ listarea2.get(idarea2) + listarea3.get(idarea3);
										String sex = (Integer.valueOf((String) listarea3.get(idarea3))) % 2 == 0
												? " 女  " : " 男  ";
										String vv = IDCard.getV(cardnum); // 获取最后一位
										if (vv.equals("10")) {
											vv = "X";
										}
										cardnum = cardnum + vv;
										if (v.equals("*")) { // 如果是*
																// 那么将所有的信息打印
											try {
												count++;
												docs.insertString(docs.getLength(),
														cardnum + "   " + idcardareastr + " " + "  " + sex + " \n",
														null);
												jTextPane.setCaretPosition(jTextPane.getDocument().getLength());
												sleep(getrandomInt());
											} catch (Exception e) {
												e.printStackTrace();
											}
										} else {
											if (v.toUpperCase().equals(vv)) {
												try {
													count++;
													docs.insertString(docs.getLength(),
															cardnum + "   " + idcardareastr + " " + "  " + sex + " \n",
															null);
													jTextPane.setCaretPosition(jTextPane.getDocument().getLength());
													sleep(getrandomInt());
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										}

									} else {
										try {
											sleep(getrandomInt());
											// this.interrupt();
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								} else {
									stop();
								}

							}
						}
					}

				}
			}
			try {
				docs.insertString(docs.getLength(), "------------------------------------------\n", null);
				docs.insertString(docs.getLength(), "共 " + String.valueOf(count) + " 条", null);
				jTextPane.setCaretPosition(jTextPane.getDocument().getLength());
				sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
