/**
 * HBaseAPI.java created at 2017年5月27日 下午5:21:59
 */
package org.monuo.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * @author saxon
 */
public class HBaseAPI {
	//公共连接代码
	private static Configuration conf = null;
	static {
		Configuration HBASE_CONFIG = new Configuration();
		// 与hbase/conf/hbase-site.xml 中 hbase.zookeeper.quorum 配置的值相同
		HBASE_CONFIG.set("hbase.zookeeper.quorum", "master,slave1,slave2");
		// 与hbase/conf/hbase-site.xml 中 hbase.zookeeper.property.clientPort 配置的值相同
		HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "9000");
		conf = HBaseConfiguration.create(HBASE_CONFIG);
	}
	
	public static void createTable(String tableName, String[] familys, boolean force) throws Exception{
		//建立连接
		Connection connection = ConnectionFactory.createConnection(conf);
		//表管理类
		Admin admin = connection.getAdmin();
		if(admin.tableExists(TableName.valueOf(tableName))){
			if(force){
				//禁用表
				admin.disableTable(TableName.valueOf(tableName));
				//删除表
				admin.deleteTable(TableName.valueOf(tableName));
				System.out.println("begin create table");
			}else{
				System.out.println("table is exists!");
				admin.close();
				connection.close();
				return;
			}
		}
		//定义表名
		HTableDescriptor tblDesc = HTableDescriptor.metaTableDescriptor(conf);
		for(int i = 0; i < familys.length; i ++){
			//定义列族
			HColumnDescriptor columnDesc = new HColumnDescriptor(familys[i]);
			//将列添加到table中
			tblDesc.addFamily(columnDesc);
		}
		//建表
		admin.createTable(tblDesc);
		System.out.println("create table: " + tableName + " success!");
		
		//关闭管理
		admin.close();
		connection.close();
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		createTable("userHbase", new String[]{"name", "age"}, true);
	}

}
