/**
 * RClasQueryAction.java created at 2017年6月13日 上午11:10:53
 */
package org.monuo.sshredis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.monuo.sshredis.common.StringUtils;
import org.monuo.sshredis.entity.Clas;
import org.monuo.sshredis.redisclient.RedisService;
import org.monuo.sshredis.redisclient.RedisTool;
import org.monuo.sshredis.service.ClasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author saxon
 */
@Controller
@RequestMapping("/")
public class RClasQueryAction {
	@Autowired
	private ClasService clasService;
	
	RedisService rs = RedisTool.getRedisService();
    List<Clas> claslist = new ArrayList<Clas>();
    Clas c;
    @RequestMapping("initclass")
    public String execute(Model model){
        if (rs != null){
            System.out.println("RedisService : " + rs);
            getAllClas();
        }
        model.addAttribute("claslist", claslist);
        return "/index.jsp";
    }
    
    private void getAllClas(){
        claslist = new ArrayList<Clas>();        
        int num = Integer.parseInt(rs.get("clas:count"));
        for (int i=0; i<num; i++){
            String cid = "clas:" + (i+1);
            c = new Clas();
            int id = Integer.parseInt(StringUtils.replaceBlank(String.valueOf(rs.getHash(cid, "ID"))));
            c.setId(id);
            System.out.println("ID:" + id);
            String name = StringUtils.replaceBlank((String) rs.getHash(cid, "NAME"));
            c.setName(name);
            System.out.println("NAME:" + name);
            String comment = StringUtils.replaceBlank((String) rs.getHash(cid, "COMMENT"));
            c.setComment(comment);
            System.out.println("COMMENT:" + comment);
            claslist.add(c);
        }
    }
    @RequestMapping("addClass")
    public String saveClass(Clas entity){
    	List<String> ids = rs.getList("clas:id");
    	// String idStr = rs.getListPop("clas:id");
    	 System.out.println("idStr ->" + ids);
         // clas:id
         int id = Integer.parseInt(StringUtils.replaceBlank(ids.get(0))) + 1;
         rs.addList("clas:id", String.valueOf(id));
         // clas:count
         int count = Integer.parseInt(StringUtils.replaceBlank(rs.get("clas:count")));
         rs.set("clas:count", String.valueOf(count+1), -1);
         // 增加
         HashMap<String, String> hashmap = new HashMap<String, String>();
         hashmap.put("ID", String.valueOf(id));
         hashmap.put("NAME", entity.getName());
         hashmap.put("COMMENT", entity.getComment());
         rs.addHash("clas:" + id, hashmap);
         // 同步到MySQL
         clasService.save(entity);
    	 return "/index.jsp";
    }
    @RequestMapping("delClass")
    public String deleteClass(String id){
    	 rs.del("clas:" + id);
         // clas:count
         int count = Integer.parseInt(rs.get("clas:count"));
         rs.set("clas:count", String.valueOf(count-1), -1);
         // clas:id
         rs.deleteListItem("clas:id", String.valueOf(id));
         clasService.delelte(Integer.valueOf(id));
         return "/index.jsp";
    }
    @RequestMapping("editClass")
    public String editClass(Clas entity){
    	 HashMap<String, String> hashmap = new HashMap<String, String>();
         hashmap.put("ID", String.valueOf(entity.getId()));
         hashmap.put("NAME", entity.getName());
         hashmap.put("COMMENT", entity.getComment());
         rs.putHash("clas:" + entity.getId(), hashmap);
         // 同步到MySQL
         clasService.update(entity);
         return "/index.jsp";
    }

	public ClasService getClasService() {
		return clasService;
	}
	public void setClasService(ClasService clasService) {
		this.clasService = clasService;
	}
}
