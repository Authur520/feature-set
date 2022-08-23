package com.authur.template.batchSync;

import com.sun.xml.internal.ws.spi.db.DatabindingException;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/8/23 11:45
 */
@Service
public class UserService {

    public List<Map<String, Object>> getUser(List<Map<String,Object>> users){
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String creteTime = format.format(date);
            map.put("creteTime", creteTime);
            double random = Math.random()*1000;
            long id = new Double(random).longValue();
            map.put("userId", id);
            list.add(map);
        }
        return list;
    }

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format1 = format.format(date);
        System.out.println(format1);
    }
}
