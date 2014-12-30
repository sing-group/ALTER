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

package es.uvigo.ei.sing.alter.cmdLine;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Implements a handler for logging messages, printing them to the standard out stream.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */
public class CmdLineLogHandler extends Handler
{
    /**
     * Prints messages in the adequate format.
     * @param record Message to print.
     */
    @Override
    public void publish(LogRecord record)
    {
        if (record.getLevel() == Level.INFO)
            System.out.println("<INFO> : " + record.getMessage().replace("\n", "\n\t"));
        else if (record.getLevel() == Level.WARNING)
            System.out.println("<WARNING> : " + record.getMessage().replace("\n", "\n\t"));
        else
            System.err.println("<ERROR>: " + record.getMessage().replace("\n", "\n\t"));
    }

    /**
     * Calls Sytem.out.flush() and System.err.flush().
     */
    @Override
    public void flush()
    {
        System.out.flush();
        System.err.flush();
    }

    /**
     * No behaviour has been implemented for this method.
     * @throws SecurityException If a Security Manager exists and the calling object does not have permission.
     */
    @Override
    public void close() throws SecurityException
    {
    }
}
