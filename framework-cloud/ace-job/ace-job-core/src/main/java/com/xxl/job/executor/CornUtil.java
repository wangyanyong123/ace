package com.xxl.job.executor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CornUtil {

    public static String getCronExpression(Date runTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        return dateFormat.format(runTime);
    }
}
