package com.sjtu.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rpc.caller.RPCClient;
import rpc.common.RMType;
import rpc.common.Ret;

/**
 * Created by xiaoke on 17-9-26.
 */

// used for app
@Controller
@RequestMapping("/page")
public class PageController {

    private static  final Logger log = Logger.getLogger(PageController.class);

    @RequestMapping("/forward")
    public String pageAppList(String page) {
        return page;
    }

    @RequestMapping("/appList")
    public String pageAppList() {
        return "appList";
    }

    @RequestMapping("/app")
    public String pageApp() {
        return "app";
    }

    @RequestMapping("/device")
    public String pageDevice() {
        return "device";
    }

    @ResponseBody
    @RequestMapping("/rmcall")
    public Ret rmcall(String type) {
        RPCClient client = new RPCClient();
        Ret res = null;
        if ("sql".equals(type)) {
            res = client.rmcall(RMType.SQL);
        } else {
            res = client.rmcall(RMType.QUERY);
        }
        return res;
    }
}
