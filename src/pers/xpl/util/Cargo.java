/*
 * auther:xie peiliang
 * create time:2019.4.14
 * describe:货物(批)类
 */
package pers.xpl.util;

public class Cargo {
	//批次编号
	private String Id;
	//位置编号
	private String Clocation;
	//名称
	private String Name;
	//单个货物大小
	private float CargoVolume;
	//该类货物的货位容量
	private float SlottingCapacity;
	//货物数量
	private int Count;
	private int Label_1, Label_2, Label_3;
	//所在的货位ID
	private String Sid;
	
	public Cargo(String id, String location, String name, float cargovolume, float slottingcapacity, int count, int l1,int l2,int l3) {
		Id = id;
		Clocation = location;
		Name = name;
		CargoVolume = cargovolume;
		SlottingCapacity = slottingcapacity;
		Count = count;
		Label_1 = l1;
		Label_2 = l2;
		Label_3 = l3;
	}
	
	public Cargo(String name, float cargovolume, float slottingcapacity, int count) {
		Name = name;
		CargoVolume = cargovolume;
		SlottingCapacity = slottingcapacity;
		Count = count;
	}
	
	public void setID(String str) {
		Id = str;
	}
	public String getID() {
		return Id;
	}
	
	public void setLocation(String location) {
		Clocation = location;
	}

	public String getLocation() {
		return Clocation;
	}
	
	public String getName() {
		return Name;
	}
	
	public float getSlottingCapacity() {
		return SlottingCapacity;
	}
	
	public float getVolume() {
		return  CargoVolume;
	}
	
	//返回该批货物总体积
	public float getTheSumOfVolume() {
		return Count * CargoVolume;
	}
	
	public int getCount() {
		return Count;
	}
	
	public int getLabel_1() {
		return Label_1;
	}
	public void setLabel_1(int l) {
		Label_1 = l;
	}
	public int getLabel_2() {
		return Label_2;
	}
	public void setLabel_2(int l) {
		Label_2 = l;
	}
	public int getLabel_3() {
		return Label_3;
	}
	public void setLabel_3(int l) {
		Label_3 = l;
	}
	public String getSid() {
		return Sid;
	}
	public void setSid(String x) {
		Sid = x;
	}
}










