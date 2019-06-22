/*
 * auther:Xie Peiliang
 * create date:2019.4.4
 * discribe:对mysql数据库的操作的方法集合
 */
package pers.xpl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.tools.JavaCompiler;

public class FunctionsOfMysql {
	private Connection conn = null;
	private int ConnState = 0;
	
	public FunctionsOfMysql() {}
	
	public void getConn() {         //数据库连接
		String url = "jdbc:mysql://localhost:3306/carousel"+"?serverTimezone=GMT%2B8";//后面不加时区会报错
		String user = "root";
		String password = "Xpl3141592654";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ConnState = 1;
	}
	
	/*
	 * 测试用
	 */
	public ArrayList getInfoOfCargo() throws SQLException{
		String sql = "select * from Cargo";
		ArrayList info = new ArrayList();
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement stat = conn.createStatement();
		ResultSet result = stat.executeQuery(sql);
		while(result.next()) {
			info.add(result.getString(1));
			info.add(result.getString(2));
			info.add(result.getString(3));
			info.add(result.getString(4));
		}
		return info;
	}
	
	/*
	 *返回货物概览表格数据：
	 *货物批号、货物编号、所在货斗、所在货位、货位内编号、货物名、单位体积、数量 
	 */
	public Object[][] getDataToShowInTable1() throws SQLException{
		String sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo order by cid";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object info[][] = new Object[resultSet.getRow()][8];
		int i = 0, j = 0;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][j++] = resultSet.getObject(1);
			info[i][j++] = resultSet.getObject(2);
			info[i][j++] = resultSet.getObject(3);
			info[i][j++] = resultSet.getObject(4);
			info[i][j++] = resultSet.getObject(5);
			info[i][j++] = resultSet.getObject(6);
			info[i][j++] = resultSet.getObject(7);
			info[i][j++] = resultSet.getObject(8);
			i++;
			j=0;
		}
		return info;
	}
	
	/*
	 * 返回货位概览表格数据
	 * 货位ID、所在货斗、货斗内编号、货位种类、总容量、可用容量、状态
	 */
	public Object[][] getDataToshowInTable2() throws SQLException{
		String sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting order by label_1,label_2";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object info[][] = new Object[resultSet.getRow()][7];
		int i = 0, j = 0;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][j++] = resultSet.getObject(1);
			info[i][j++] = resultSet.getObject(2);
			info[i][j++] = resultSet.getObject(3);
			info[i][j++] = resultSet.getObject(4);
			info[i][j++] = resultSet.getObject(5);
			info[i][j++] = resultSet.getObject(6);
			info[i][j++] = resultSet.getObject(7);
			i++;
			j = 0;
		}
		return info;
	}
	
	/*
	 * 返回货位概览表格数据（按照可用容量的升序排列）
	 * 货位ID、所在货斗、货斗内编号、货位种类、总容量、可用容量
	 */
	public Object[][] getDataToshowInTable2OrderByAvailable() throws SQLException{
		String sql = "select sid,label_1,label_2,type,capacity,capacity_available"
				+ " from slotting order by capacity_available";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object info[][] = new Object[resultSet.getRow()][6];
		int i = 0, j = 0;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][j++] = resultSet.getObject(1);
			info[i][j++] = resultSet.getObject(2);
			info[i][j++] = resultSet.getObject(3);
			info[i][j++] = resultSet.getObject(4);
			info[i][j++] = resultSet.getObject(5);
			info[i][j++] = resultSet.getObject(6);
			i++;
			j = 0;
		}
		return info;
	}
	
	/*
	 * 返回货位种类信息
	 */
	public Object[][] getSlottingMessage() throws SQLException{
		String sql = "select type, cargo_volume, slotting_capacity from cargo_slotting_mess";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object[][] info = new Object[resultSet.getRow()][3];
		int i = 0 ;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][0] = resultSet.getObject(1);
			info[i][1] = resultSet.getObject(2);
			info[i][2] = resultSet.getObject(3);
			i++;
		}
		return info;
	}
	
	/*
	 * 返回货物在回转库中的名称、数量
	 */
	public Object[][] getTheCountOfCargos() throws SQLException{
		String sql = "select type,sum(count) from cargo group by type";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement= conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object[][] info = new Object[resultSet.getRow()][2];
		resultSet.beforeFirst();
		int i=0;
		while(resultSet.next()) {
			info[i][0] = resultSet.getObject(1);
			info[i][1] = resultSet.getObject(2);
			i++;
		}
		return info;
		
	}
	
	/*
	 * 在slotting_type表中添加一条新纪录,成功返回1，否则返回0
	 */
	public int addInfoToSlottingType(String type, String volume,String capacity) throws SQLException{
		String sql = "insert into cargo_slotting_mess(type,cargo_volume,slotting_capacity) values ('"+type+"',"+volume+","+capacity+")";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		int result = statement.executeUpdate(sql);
		return result;
	}
	
	/*
	 * 由货位的id查出该货位中的货物的最大编号
	 */
	public int getTheBiggestIndexOfASlotting(String id) throws SQLException{
		String sql = "select max(label_3) from slotting,cargo where slotting.sid = cargo.sid and slotting.sid = " + id;
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.next();
		int result = resultSet.getInt(1);
		return result;
		
	}
	
	/*
	 * 入库更新cargo表
	 */
	public void UpdateCargo(Cargo cargo) throws SQLException{
		if(ConnState == 0) {
			getConn();
		}
		
		String sql = String.format("insert into cargo (cid,clocation,type,volume,count,sid,label_1,label_2,label_3) values (\"%s\",\"%s\",\"%s\",%.1f,%d,\"%s\",%d,%d,%d)", cargo.getID(),cargo.getLocation(),cargo.getName(),cargo.getVolume(),cargo.getCount(),cargo.getSid(),cargo.getLabel_1(),cargo.getLabel_2(),cargo.getLabel_3());
		java.sql.Statement statement = conn.createStatement();
		int a = statement.executeUpdate(sql);
	}
	
	/*
	 * 入库（货物放入已存在的货位），更新slotting的capacity_available
	 */
	public void UpdateSlotting(String id ,float nc) throws SQLException{
		if(ConnState == 0) {
			getConn();
		}
		String sql = String.format("update slotting set capacity_available = %.1f where sid = %s", nc, id);
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	/*
	 * 入库（新建一个货位）,并且将可用容量更新
	 */
	public void AddNewSlotting(Slotting s, float x) throws SQLException{
		if(ConnState == 0) {
			getConn();
		}
		String sql = String.format("insert into slotting (sid,type,label_1,label_2,capacity,capacity_available) values (\"%s\",\"%s\",%d,%d,%.1f,%.1f)",s.getId(),s.getName(),s.getLabel_1(),s.getLabel_2(),s.getCapacity(),x);
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	/*
	 * 将空闲区域的二级标签和可用容量在创建新货位后更新
	 */
	public void UpdateTheFreeSlotting(String id, int label_2, float freespace) throws SQLException{
		if(ConnState == 0) {
			getConn();
		}
		String sql = String.format("update slotting set label_2 = %d,capacity = %.1f,capacity_available = %.1f where sid = \"%s\"", label_2, freespace, freespace, id);
		java.sql.Statement statement  = conn.createStatement();
		statement.executeUpdate(sql);
		
	}
	public void UpdateTheFreeSlotting(String id, String name, float freespace) throws SQLException{
		if(ConnState == 0)
			getConn();
		String sql = String.format("update slotting set type = \"%s\",capacity_available = %.1f where id = \"%s\"", name, freespace, id);
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	//返回某种货物能够存储在回转库中的最大的容量
	public float getBiggistVolumeOfCargo(String name) throws SQLException{
		String sql = "select sum(capacity_available) from slotting where type = '"+name+"' or type = 'empty'";
		if(ConnState == 0)
			getConn();
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.next();
		float re = resultSet.getFloat(1);
		return re;
		
	}
	
	//更改货物表中某种货物的数量
	public void ChangeTheCountOfCargo(String clocation, int count) throws SQLException{
		String sql = String.format("update cargo set count = %d where clocation = \'%s\'",count, clocation);
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);	
	}
	
	//由货物的货物编号查找该货物所在的货位，再改变该货位的可用容量
	public void ChangeTheAvilCapacity(String clocation, float avail) throws SQLException{
		String sql = String.format("update slotting set capacity_available = capacity_available+%.1f where sid = (select sid from cargo where clocation = \'%s\')", avail, clocation);
		if(ConnState == 0)
			getConn();
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	//删除cargo表中count为0的记录
	public void DelUseless_Cargo() throws SQLException{
		String sql = "delete from cargo where count = 0";
		if(ConnState == 0)
			getConn();
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	/*
	 *按照label顺序排列cargo数据。整理label_3用
	 *货物批号、货物编号、所在货斗、所在货位、货位内编号、货物名、单位体积、数量 
	 */
	public Object[][] getDataOfCargoOrderByLabel() throws SQLException{
		String sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo order by clocation";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object info[][] = new Object[resultSet.getRow()][8];
		int i = 0, j = 0;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][j++] = resultSet.getObject(1);
			info[i][j++] = resultSet.getObject(2);
			info[i][j++] = resultSet.getObject(3);
			info[i][j++] = resultSet.getObject(4);
			info[i][j++] = resultSet.getObject(5);
			info[i][j++] = resultSet.getObject(6);
			info[i][j++] = resultSet.getObject(7);
			info[i][j++] = resultSet.getObject(8);
			i++;
			j=0;
		}
		return info;
	}
	
	//设置cargo中货物的三级标签
	public void UpdateCargoLabel_3(int label, String newlocation, String location) throws SQLException{
		String sql = "update cargo set label_3 = "+label+", clocation = '"+newlocation+"' where clocation = '"+location+"'";
		if(ConnState == 0)
			getConn();
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	//设置cargo中货物的二级标签
	public void UpdateCargoLabel_2(int label, String newlocation, String location) throws SQLException{
		String sql = "update cargo set label_2 = "+label+", clocation = '"+newlocation+"' where clocation = '"+location+"'";
		if(ConnState == 0)
			getConn();
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	/*
	 * 按照label顺序排列slotting数据，整理label_2用
	 * 其中不包括可用容量为2000的empty货斗
	 */
	public Object[][] getDataOfSlottingOrderByLabel() throws SQLException{
		String sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting where sid not in (select sid from slotting where type = 'empty' and capacity = 2000) order by label_1,label_2";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object info[][] = new Object[resultSet.getRow()][7];
		int i = 0, j = 0;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][j++] = resultSet.getObject(1);
			info[i][j++] = resultSet.getObject(2);
			info[i][j++] = resultSet.getObject(3);
			info[i][j++] = resultSet.getObject(4);
			info[i][j++] = resultSet.getObject(5);
			info[i][j++] = resultSet.getObject(6);
			info[i][j++] = resultSet.getObject(7);
			i++;
			j = 0;
		}
		return info;
	} 
	
	//将一个货位改为empty货位
	public void Set_Empty_Slotting(String sid) throws SQLException{
		String sql = "update slotting set type = 'empty' where sid = '"+sid+"'";
		if(ConnState == 0)
			getConn();
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	//将slotting中的非empty且可用容量等于总容量的货位从表中删除，将empty且容量小于2000的未分配空间删除
	public void Del_Slotting() throws SQLException{
		String sql = "delete from slotting where (type != 'empty' and capacity = capacity_available) or (type='empty' and capacity<2000)";
		if(ConnState == 0)
			getConn();
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	//更新slotting中货物的二级标签
	public void UpdateSlottingLabel2(int label2, String sid) throws SQLException{
		String sql = "update slotting set label_2 ="+label2+" where sid = '"+sid+"'";
		if(ConnState == 0)
			getConn();
		java.sql.Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}
	
	//在Update_Cargo_label2()方法中调用，查询cargo数据，和该货物所在货斗的二级标签
	public Object[][] getDataForUpdateCargoLabel2() throws SQLException{
		String sql = "select c.cid,c.clocation,c.label_1,c.label_2,c.label_3,c.type,c.volume,c.count,s.label_2 from cargo c,slotting s where c.sid = s.sid order by c.clocation";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object info[][] = new Object[resultSet.getRow()][9];
		int i = 0, j = 0;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][j++] = resultSet.getObject(1);
			info[i][j++] = resultSet.getObject(2);
			info[i][j++] = resultSet.getObject(3);
			info[i][j++] = resultSet.getObject(4);
			info[i][j++] = resultSet.getObject(5);
			info[i][j++] = resultSet.getObject(6);
			info[i][j++] = resultSet.getObject(7);
			info[i][j++] = resultSet.getObject(8);
			info[i][j++] = resultSet.getObject(9);
			i++;
			j=0;
		}
		return info;
	}

	//登录检查，返回1登录成功，返回0密码错误，返回-1无该账号
	public int check_login(String name, String password) throws SQLException{
		if(ConnState == 0)
			getConn();
		String sql = "select * from user where id = '"+ name +"'";
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if(resultSet.next() == false){
			return -1;
		}
		if(password.equals(resultSet.getString(2))){
			return 1;
		}
		return 0;
	}
	/*
	 *返回货物概览条件筛选表格数据：
	 *货物批号、货物编号、所在货斗、所在货位、货位内编号、货物名、单位体积、数量
	 */
	public Object[][] getDataToShowInTable1ByFactors(String sql) throws SQLException{
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object info[][] = new Object[resultSet.getRow()][8];
		int i = 0, j = 0;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][j++] = resultSet.getObject(1);
			info[i][j++] = resultSet.getObject(2);
			info[i][j++] = resultSet.getObject(3);
			info[i][j++] = resultSet.getObject(4);
			info[i][j++] = resultSet.getObject(5);
			info[i][j++] = resultSet.getObject(6);
			info[i][j++] = resultSet.getObject(7);
			info[i][j++] = resultSet.getObject(8);
			i++;
			j=0;
		}
		return info;
	}
	/*
	 * 返回货位概览条件筛选表格数据
	 * 货位ID、所在货斗、货斗内编号、货位种类、总容量、可用容量、状态
	 */
	public Object[][] getDataToshowInTable2ByFactorys(String sql) throws SQLException{
		//String sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting order by label_1,label_2";
		if(ConnState == 0) {
			getConn();
		}
		java.sql.Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		resultSet.last();
		Object info[][] = new Object[resultSet.getRow()][7];
		int i = 0, j = 0;
		resultSet.beforeFirst();
		while(resultSet.next()) {
			info[i][j++] = resultSet.getObject(1);
			info[i][j++] = resultSet.getObject(2);
			info[i][j++] = resultSet.getObject(3);
			info[i][j++] = resultSet.getObject(4);
			info[i][j++] = resultSet.getObject(5);
			info[i][j++] = resultSet.getObject(6);
			info[i][j++] = resultSet.getObject(7);
			i++;
			j = 0;
		}
		return info;
	}
}




















