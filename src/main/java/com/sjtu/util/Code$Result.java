package com.sjtu.util;

/**
 * Created by xiaoke on 17-10-6.
 */
public class Code$Result {

    public final boolean code;

    public final Object result;

    public static final Code$Result FAIL_NO_ERROR = new Code$Result(false, null);

    public static final Code$Result SUCC_NO_RET = new Code$Result(true, null);

    private Code$Result(boolean c, Object r) {
        this.code = c;
        this.result = r;
    }

    public static Code$Result failedWithError(Throwable throwable) {
        return new Code$Result(false, throwable);
    }

    public static Code$Result succedWithRet(Object obj) {
        return new Code$Result(true, obj);
    }
}
