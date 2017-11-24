package com.sjtu.service;

import com.sjtu.dao.PointerDAO;
import com.sjtu.pojo.Pointer;
import com.sjtu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiaoke on 17-11-10.
 */
public class PointerService {

    @Autowired
    private PointerDAO pointerDAO;

    public Pointer createPointer(Pointer pointer) {
        if(pointer.getPdesc() != null) {
            pointer.setPdesc(pointer.getPdesc().trim());
        }
        String[] attrAry = pointer.getPbindattr().split(":");
        List<String> attr = new ArrayList<String>();
        for (String str:attrAry) {
            if (!str.isEmpty()) {
                attr.add(str);
            }
        }
        Collections.sort(attr);
        String hash = "";
        for (int i = 0; i < attr.size(); i++) {
            if (i != 0) {
                hash += ":" + attr.get(i);
            } else {
                hash += attr.get(i);
            }
        }
        pointer.setPbindhash(hash);
        Pointer p = pointerDAO.create(pointer);
        if (p.getPid() > 0) {
            return p;
        } else {
            return null;
        }
    }

    public Pointer getPointer(Pointer pointer) {
        Pointer p = pointerDAO.get(pointer);
        if (p.getPid() > 0) {
            return p;
        } else {
            return null;
        }
    }

    public List<Pointer> listPointer(User user, int p) {
        return pointerDAO.list(user, p);
    }

    public int cntPointer(User user) {
        int total = pointerDAO.cnt(user);
        if (total % 10 == 0) {
            return total / 10;
        } else {
            return  total / 10 + 1;
        }
    }

}
