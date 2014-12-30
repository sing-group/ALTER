package es.uvigo.ei.sing.alter.web;

/*
 *  This file is part of ALTER.
 *
 *  ALTER is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ALTER is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ALTER.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.zkoss.zul.Textbox;

public class WebLogHandler extends Handler
{
    Textbox error;
    Textbox info;
    Textbox warning;

    public WebLogHandler(Textbox e, Textbox i, Textbox w)
    {
        error = e;
        info = i;
        warning = w;
    }
    
    @Override
    public void publish(LogRecord record)
    {
        try
        {
            if (record.getLevel() == Level.INFO)
            {
                info.setRawValue(info.getValue()
                        + record.getMessage().replaceAll("\n", "\n\t") + "\n");
                info.setFocus(true);
            }
            else if (record.getLevel() == Level.WARNING)
            {
                warning.setFocus(true);
                warning.setRawValue(warning.getValue()
                        + record.getMessage().replaceAll("\n", "\n\t") + "\n");
            }
            else
            {
                error.setRawValue(error.getValue()
                        + record.getMessage().replaceAll("\n", "\n\t") + "\n");
                error.setFocus(true);
            }
        }
        catch(NullPointerException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void flush()
    {
    }

    @Override
    public void close() throws SecurityException
    {
    }
}
