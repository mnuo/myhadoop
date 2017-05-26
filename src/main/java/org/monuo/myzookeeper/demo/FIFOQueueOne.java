/**
 * FIFOQueueOne.java created at 2017年5月26日 下午12:17:28
 */
package org.monuo.myzookeeper.demo;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author saxon
 */
public class FIFOQueueOne {
	public static void doOne() throws Exception{
		String host = "192.168.159.132:2181";
		ZooKeeper zk = connection(host);
		initQueue(zk);
		produce(zk, 1);
		produce(zk, 2);
		
		cosume(zk);
		cosume(zk);
		cosume(zk);
		
		zk.close();
	}
	public static ZooKeeper connection(String host) throws Exception{
		 return new ZooKeeper(host, 60000, new Watcher() {
	            public void process(WatchedEvent event) {
	            }
	      });
	}
	public static void initQueue(ZooKeeper zk) throws Exception {
		if(zk.exists("/queue-fifo", false) == null){
			System.out.println("create /queue-fifo task-queue-fifo");
			zk.create("/queue-fifo", "task-queue-fifo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}else{
			System.out.println("/queue-fifo is exist");
		}
	}
	public static void produce(ZooKeeper zk, int x) throws Exception {
		System.out.println("create /queue-fifo/x" + x + " x" + x);
		zk.create("/queue-fifo/x"+x, ("x"+x).getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}
	public static void cosume(ZooKeeper zk) throws Exception {
		List<String> list = zk.getChildren("/queue-fifo", true);
		if(list.size() > 0){
			long min = Long.MAX_VALUE;
			for(String num : list){
				if(min > Long.parseLong(num.substring(1))){
					min = Long.parseLong(num.substring(1));
				}
			}
			System.out.println("delete /queue-fifo/x" + min);
			zk.delete("/queue-fifo/x"+min, 0);
		} else {
			System.out.println("No node to cosume");
		}
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		doOne();
	}

}
