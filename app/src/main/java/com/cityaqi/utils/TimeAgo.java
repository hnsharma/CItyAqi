package com.cityaqi.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeAgo {
    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1) );
    public static final List<String> timesString = Arrays.asList("year","month","day","hour","minute","second");
    public static final List<String> timesStringReal = Arrays.asList("y","m","d","h","m","s");

    public static String toDuration(long duration,long time) {

        StringBuffer res = new StringBuffer();
        for(int i=0;i< TimeAgo.times.size(); i++) {
            Long current = TimeAgo.times.get(i);
            long temp = duration/current;
            if(temp>0) {
                switch (i)
                {
                    case 5:
                        return "A few seconds ago";
                    case 4:
                        return temp != 1 ? res.append(temp).append(" ").append( TimeAgo.timesString.get(i) ).append(temp != 1 ? "s" : "").append(" ago").toString(): "A minute ago";
                    case 3:
                        return DateFormats.getDateFormat(DateFormats.HH_MM,new Date(time));
                    default:
                        return DateFormats.getDateFormat(DateFormats.HH_MM_DD_MM_YYYY,new Date(time));
                }

            }
        }
            return "0 seconds ago";
    }

    public static String toDurationReal(long duration,long time) {

        StringBuffer res = new StringBuffer();
        for(int i=0;i< TimeAgo.times.size(); i++) {
            Long current = TimeAgo.times.get(i);
            long temp = duration/current;
            if(temp>0) {
                return res.append(temp).append(" ").append( TimeAgo.timesStringReal.get(i) ).toString();

            }
        }
        return "0s";
    }
}
