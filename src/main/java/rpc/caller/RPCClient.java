package rpc.caller;

import com.sjtu.accessor.AccessorFactory;
import com.sjtu.config.V;
import rpc.common.Args;
import rpc.common.IService;
import rpc.common.RMType;
import rpc.common.Ret;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by xiaoke on 17-10-19.
 */
public class RPCClient {

    private static final String host = AccessorFactory.getConf().getStringOrElse(V.RPC_BIND_HOST);

    private static final int port = AccessorFactory.getConf().getIntOrElse(V.RPC_BIND_PORT);

    private static final String name = AccessorFactory.getConf().getStringOrElse(V.RPC_BIND_NAME);

    public Ret rmcall(RMType type) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            String[] ss = registry.list();
            IService service = (IService) registry.lookup(name);
            Ret ret = null;
            for (int i = 0; i < 10; i++) {
                Args args = new Args(type);
                ret = service.rmcall(args);
                System.out.println(ret.getCode());
            }
            return ret;
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            String[] ss = registry.list();
            IService service = (IService) registry.lookup(name);
            Ret ret = null;
            for (int i = 0; i < 10; i++) {
                RMType type = RMType.QUERY;
                if (i % 2 == 1) {
                    type = RMType.SQL;
                }
                Args args1 = new Args(type);
                ret = service.rmcall(args1);
                System.out.println(ret.getCode());
            }
            //return ret;
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//        return null;
//        RPCClient client = new RPCClient();
//        Ret res = null;
//        if ("sql".equals(type)) {
//            res = client.rmcall(RMType.SQL);
//        } else {
//            res = client.rmcall(RMType.QUERY);
//        }
//        return res;
    }
}
