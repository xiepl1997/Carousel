/*
 * auther:xie peiliang
 * create time:2019.2.17
 * describe:货位信息类
 */
package pers.xpl.util;

public class Slotting {
    private String Id;//货位id
    private String Name;//货位种类
    private int Label_1;//所在货斗
    private int Label_2;//所在货斗中的编号
    private float Capacity;//货位容量
    private float Capacity_available;//货位可用容量

    public Slotting(String i, String n, int l1, int l2, float c, float ca) {
        Id = i;
        Name = n;
        Label_1 = l1;
        Label_2 = l2;
        Capacity = c;
        Capacity_available = ca;
    }

    public Slotting(String n, int l1, int l2, float c, float ca) {
        Name = n;
        Label_1 = l1;
        Label_2 = l2;
        Capacity = c;
        Capacity_available = ca;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getLabel_1() {
        return Label_1;
    }

    public int getLabel_2() {
        return Label_2;
    }

    public float getCapacity() {
        return Capacity;
    }

    public float getCapacity_available() {
        return Capacity_available;
    }

}
