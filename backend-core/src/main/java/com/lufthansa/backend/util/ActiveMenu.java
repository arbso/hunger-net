package com.lufthansa.backend.util;

import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Component
public class ActiveMenu {

    public static boolean isActive(Date begin, Date close){

        java.sql.Time sqlTimeEnd = new Time(close.getTime());
        java.sql.Time sqlTimeOpen = new Time(begin.getTime());

        LocalTime end = sqlTimeEnd.toLocalTime();

        LocalTime open = sqlTimeOpen.toLocalTime();

        if(open.isBefore(end) && (LocalTime.now().isAfter(open) && LocalTime.now().isBefore(end))
            || end.isBefore(open) && LocalTime.now().isBefore(open) && LocalTime.now().isBefore(end)
                || end.isBefore(open) && LocalTime.now().isAfter(open)
        ){
            return true;
        }

        //AND ((m.menu_opening_time < m.menu_closing_time AND NOW() BETWEEN m.menu_opening_time AND m.menu_closing_time)\n" +
        //            "  OR\n" +
        //            "  (m.menu_closing_time < m.menu_opening_time AND NOW() < m.menu_opening_time AND NOW() < m.menu_closing_time)\n" +
        //            "  OR\n" +
        //            "  (m.menu_closing_time < m.menu_opening_time AND NOW() > m.menu_opening_time))
            return false;
    }

}
