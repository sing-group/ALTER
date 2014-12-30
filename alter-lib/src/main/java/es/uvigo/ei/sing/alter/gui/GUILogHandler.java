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

package es.uvigo.ei.sing.alter.gui;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Implements a handler for logging messages, printing them in a JTextArea
 * @author Daniel Gomez Blanco
 * @version 1.0
 */
public class GUILogHandler extends Handler
{
    /**
     * JTextArea where messages will be printed.
     */
    private JTextArea log;

    /**
     * Class constructor.
     * @param log JTextArea where messages will be printed.
     */
    public GUILogHandler(JTextArea log)
    {
        this.log = log;
    }

    /**
     * Prints messages in the JTextArea
     * @param record Message to print.
     */
    @Override
    public void publish(final LogRecord record)
    {
        if (record.getLevel() == Level.INFO)
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    log.append("<INFO> : " + record.getMessage().replace("\n", "\n\t") + "\n");
                }
            });
        else if (record.getLevel() == Level.WARNING)
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    log.append("<WARNING> : " + record.getMessage().replace("\n", "\n\t") + "\n");
                }
            });
        else
            SwingUtilities.invokeLater(new Runnable()
            {

                public void run()
                {
                    log.append("<ERROR>: " + record.getMessage().replace("\n", "\n\t") + "\n");
                }
            });
    }

    /**
     * No implemented behaviour.
     */
    @Override
    public void flush()
    {}

    /**
     * No implemented behaviour.
     * @throws SecurityException If a Security Manager exists and the calling object does not have permission.
     */
    @Override
    public void close() throws SecurityException
    {
    }
}
